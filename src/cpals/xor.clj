(ns cpals.xor
  (:require [cpals.core :refer [score-buffer-english]]))

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

(defn decode-single-char-xor-cipher
  "Decode a singe-char xor cipher text"
  [bytes]
  (let [bytevals (range Byte/MIN_VALUE Byte/MAX_VALUE)
        scores (map #(score-buffer-english (xor-buffer-with-byte bytes %)) bytevals)
        guess (->> (zipmap bytevals scores)
                   (sort-by second)
                   last)]
    {:key (char (first guess))
     :score (second guess)
     :text (->> (xor-buffer-with-byte bytes (first guess))
                (map #(bit-and 0xff %))
                (map char)
                (apply str))}))

(defn detect-single-char-xor-cipher
  "Given list of cipher texts, detect the one most likely to be encoded by single-char xor cipher"
  [byte-arrays]
  (->> byte-arrays
       (map decode-single-char-xor-cipher)
       (sort-by :score)
       last))

(defn decode-repeating-key-xor-cipher
  "Decode a repeating key xor ciper"
  [bytes]
  {})
