(ns cpals.coding.hex)

(defn encode
  "Encode a sequence of bytes to a hex string"
  [bytes]
  (->> bytes
       (map #(format "%02X" %))
       (apply str)))
