(ns cpals.core)

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
