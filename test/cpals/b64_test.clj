(ns cpals.b64-test
  (:require [clojure.test :as t]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [cpals.b64 :refer [decode encode]]))

(t/deftest encode-test
  (t/testing "simple cases"
    (t/are [x y] (= x (encode y))
      "AA==" [0]
      "AAA=" [0 0]
      "AAAA" [0 0 0]))

  (t/testing "en.wikipedia.org/wiki/Base64#Padding"
    (t/are [x y] (= (encode (.getBytes x "UTF-8")) y)
      "pleasure." "cGxlYXN1cmUu"
      "leasure." "bGVhc3VyZS4="
      "easure." "ZWFzdXJlLg=="
      "asure." "YXN1cmUu"
      "sure." "c3VyZS4=")))

(t/deftest decode-test
  (t/testing "simplest cases"
    (t/are [x y] (= x (vec (decode y)))
      [0] "AA=="
      [0 0] "AAA="
      [0 0 0] "AAAA"))

  (t/testing "en.wikipedia.org/wiki/Base64#Padding"
    (t/are [x y] (= (encode (.getBytes x "UTF-8")) y)
      "pleasure." "cGxlYXN1cmUu"
      "leasure." "bGVhc3VyZS4="
      "easure." "ZWFzdXJlLg=="
      "asure." "YXN1cmUu"
      "sure." "c3VyZS4=")))

(defspec roundtrip-test
  100
  (prop/for-all [v (gen/not-empty gen/bytes)]
                (let [encoded (encode v)]
                  (= encoded (encode (decode encoded))))))
