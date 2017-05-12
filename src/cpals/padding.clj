(ns cpals.padding)

(defn pkcs7
  "Pads a block to a specified block size"
  [n buffer]
  (let [buflen (count buffer)]
    (if (<= buflen n)
      (let [remainder (rem n buflen)]
        (into buffer (repeat remainder remainder)))
      (throw (Exception. "Buffer is longer than requested padsize")))))
