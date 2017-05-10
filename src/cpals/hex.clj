(ns cpals.hex)

(defn encode
  "Encode a sequence of bytes to a hex string"
  [bytes]
  (->> bytes
       (map #(format "%02x" %))
       (apply str)))

(def ^:private alphabet "0123456789abcdef")

(def ^:private hex-to-int
  (merge
   (zipmap alphabet (iterate inc 0))
   (zipmap (.toUpperCase alphabet) (iterate inc 0))))

(defn- hex-combine
  [hi lo]
  (bit-or (bit-shift-left hi 4) lo))

(defn decode
  "Decode a hex string to a sequence of bytes"
  [hex]
  (->> hex
       (map hex-to-int)
       (partition-all 2)
       (map #(apply hex-combine %))))
