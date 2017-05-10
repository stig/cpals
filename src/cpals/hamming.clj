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
