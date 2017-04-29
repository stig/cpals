(ns cpals.core)

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

(def ^:private char-freqs
  "Taken from: https://en.wikipedia.org/wiki/Letter_frequency"
  {\space 15
   \e 12.702
   \t 9.056
   \a 8.167
   \o 7.507
   \i 6.966
   \n 6.749
   \s 6.327
   \h 6.094
   \r 5.987
   \d 4.253
   \l 4.025
   \c 2.782
   \u 2.758
   \m 2.406
   \w 2.360
   \f 2.228
   \g 2.015
   \y 1.974
   \p 1.929
   \b 1.492
   \v 0.978
   \k 0.772
   \j 0.153
   \x 0.150
   \q 0.095
   \z 0.074})

(def ^:private byte-freqs
  "A map of upper + lower case byte values to frequency in English"
  (let [lowers (map first char-freqs)
        uppers (seq (.toUpperCase (apply str lowers)))
        scores (map second char-freqs)]
    (println lowers uppers scores)
    (merge
     (zipmap (map byte lowers) scores)
     (zipmap (map byte uppers) scores))))

(defn score-buffer-english
  "Score a buffer of bytes based on its likelyhood of being English text"
  [text]
  (->> text
       (map byte-freqs)
       (reduce (fnil + 0 0))))

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
