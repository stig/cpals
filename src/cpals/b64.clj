(ns cpals.b64)


(def ^:private alphabet
  "The alphabet of characters for Base64 encoding."
  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/")

(defn- b64-combine-bytes
  "1-3 bytes that will be combined into an int"
  [xs]
  (loop [xs (take 3 (concat xs (repeat 0)))
         acc 0]
    (if (seq xs)
      (recur (rest xs)
             (+ (bit-shift-left acc 8)
                (bit-and 0xff (first xs))))
      acc)))

(defn- b64-encode-triplet
  [xs]
  (let [combined (b64-combine-bytes xs)
        want-bytes (inc (count xs))
        want-bits (take want-bytes [18 12 6 0])
        quad (for [i want-bits]
               (.charAt alphabet (bit-and 2r111111 (bit-shift-right combined i))))]
    (take 4 (concat quad [\= \=]))))

(defn encode
  "Encode a sequence of bytes to a base64-encoded string"
  [bytes]
  (->> bytes
       (partition-all 3)
       (map b64-encode-triplet)
       flatten
       (apply str)))

(def ^:private decode-char
  (conj (zipmap alphabet (iterate inc 0))
        [\= 0]))

(defn- b64-combine-chars
  "Quad of chars that will be combined into an int"
  [xs]
  (loop [xs xs acc 0]
    (if (seq xs)
      (recur (rest xs)
             (+ (bit-shift-left acc 6)
                (decode-char (first xs))))
      acc)))

(defn- b64-decode-quad
  [quad]
  ;; Feels very inefficcient; but it'll do, for now.
  (let [combined (b64-combine-chars quad)
        want-bytes (dec (count (filter (comp not #{\=}) quad)))
        want-bits (take want-bytes [16 8 0])]
    (for [i want-bits]
      (bit-and 0xff (bit-shift-right combined i)))))

(defn decode
  "Decode a base64-encoded string"
  [encoded]
  (->> encoded
       (partition 4)
       (map b64-decode-quad)
       flatten))
