(ns cpals.xor
  (:require [cpals.core :refer [transpose]]
            [cpals.english :refer [score-buffer]]
            [cpals.hamming :refer [rank-keysizes]]))

(defn xor-buffers
  "XOR two equal-length buffers, returning the result"
  [xs ys]
  (map bit-xor xs ys))

(defn xor-buffer-with-key
  "XOR a buffer with a repeating key"
  [buffer key]
  (xor-buffers buffer
               (take (count buffer)
                     (cycle (map byte key)))))

(defn xor-buffer-with-byte
  "XOR `bytes` against `c`"
  [bytes c]
  (map #(bit-xor c %) bytes))

(defn break-single-byte-key-cipher
  "Decode a singe-char xor cipher text"
  [bytes]
  (let [bytevals (range Byte/MIN_VALUE Byte/MAX_VALUE)
        scores (map #(score-buffer (xor-buffer-with-byte bytes %)) bytevals)
        guess (->> (zipmap bytevals scores)
                   (sort-by second)
                   last)]
    {:key (first guess)
     :score (second guess)
     :text (->> (xor-buffer-with-byte bytes (first guess))
                byte-array
                String.)}))

(defn detect-single-byte-key-cipher
  "Given list of cipher texts, detect the one most likely to be
  encoded by single-char xor cipher"
  [byte-arrays]
  (->> byte-arrays
       (map break-single-byte-key-cipher)
       (sort-by :score)
       last))

(defn- guess-repeating-key
  [bytes keylen]
  (->> (partition keylen bytes)
       transpose
       (map break-single-byte-key-cipher)
       (map :key)))

(defn- score-repeating-key
  [bytes key]
  [key (->> (xor-buffer-with-key bytes key)
            score-buffer)])

(defn break-repeating-key-cipher
  "Decode a repeating key xor cipher"
  [bytes]
  (let [key (->> (rank-keysizes bytes)
                 (map first)
                 (take 3)
                 (map #(guess-repeating-key bytes %))
                 (map #(score-repeating-key bytes %))
                 (group-by last)
                 (sort-by first)
                 last ;; get highest scoring group
                 last ;; get just values

                 (map first) ;; extract keys
                 (sort-by count) ;; sort keys by length
                 first)] ;; grab shortest key

    {:key (String. (byte-array key))
     :text (->> (xor-buffer-with-key bytes key)
                byte-array
                String.)}))
