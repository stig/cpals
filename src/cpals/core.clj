(ns cpals.core)

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
    (merge
     (zipmap (map byte lowers) scores)
     (zipmap (map byte uppers) scores))))

(defn score-buffer-english
  "Score a buffer of bytes based on its likelyhood of being English text"
  [text]
  (->> text
       (map byte-freqs)
       (reduce (fnil + 0 0))))

(defn transpose
  "Transpose a 'matrix'"
  [matrix]
  (apply map vector matrix))

(defn detect-aes-ecb-encrypted-cipher
  "Find a needle in a haystack. (But don't break it.)"
  [byte-seqs]
  (->> byte-seqs
       (map #(partition 16 %))
       (map #(into #{} %))
       (map count)
       (map-indexed vector)
       (apply min-key second)
       first))
