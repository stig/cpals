(ns cpals.coding.hex-test
  (:require [clojure.test :as t]
            [cpals.coding.hex :refer :all]))

(t/deftest encode-test
  (t/testing "vectors"
    (t/are [in out] (= out (encode in))
      [0] "00"
      [255] "FF"
      [254 1 15] "FE010F"))
  (t/testing "byte arrays"
    (t/is (= "FE010F" (encode (byte-array [254 1 15]))))))
