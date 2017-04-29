(ns cpals.core)

(defn xor-bytes
  "Given two equal-length buffers, return the result of xor-ing them"
  [xs ys]
  (byte-array
   (map bit-xor xs ys)))
