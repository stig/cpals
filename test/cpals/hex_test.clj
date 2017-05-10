(ns cpals.hex-test
  (:require [clojure.test :as t]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [cpals.hex :refer :all]))

(t/deftest encode-test
  (t/testing "vectors"
    (t/are [in out] (= out (encode in))
      [0] "00"
      [255] "ff"
      [254 1 15] "fe010f"))
  (t/testing "byte arrays"
    (t/is (= "fe010f" (encode [254 1 15])))))

(t/deftest decode-test
  (t/are [in out]
      (= out (decode in))
    "00" [0]
    "FF" [255]
    "FE010F" [254 1 15]
    "fe010f" [254 1 15]))


(defspec roundtrip-test
  100
  (prop/for-all [v (gen/not-empty gen/bytes)]
                (let [encoded (encode v)]
                  (= encoded (encode (decode encoded))))))
