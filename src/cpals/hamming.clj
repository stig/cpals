(ns cpals.hamming)

(defn- count-set-bits
  "Count set bits"
  [thing]
  (loop [bits thing
         acc 0]
    (if (zero? bits)
      acc
      (recur (bit-shift-right bits 1)
             (+ acc (bit-and bits 0x1))))))

(defn hamming-distance
  "Count the bit difference between two sequences"
  [xs ys]
  (reduce + (map #(count-set-bits (bit-xor %1 %2))
                 xs ys)))

(defn score-keysize
  [bytes n]
  ;; Hamming distance is only defined for strings of equal length.
  ;; Return nil if keysize is undefined.
  (when-let [hams (seq (->> (partition n bytes)
                            (partition 2)
                            (map #(apply hamming-distance %))))]
    (/ (reduce + hams)
       (count hams)
       n)))

(defn rank-keysizes
  "Rank likely keysizes by calculating hamming distance between substrings"
  ([bytes] (rank-keysizes bytes (range 2 40)))
  ([bytes keysizes]
   (->> keysizes
        (map (fn [x] [x (score-keysize bytes x)]))
        (remove (fn [[_ x]] (nil? x)))
        (sort-by last))))
