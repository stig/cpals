(ns cpals.core)

(defn transpose
  "Transpose a 'matrix'"
  [matrix]
  (apply map vector matrix))
