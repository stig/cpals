(ns cpals.core-test
  (:require [clojure.test :refer :all]
            [cpals.core :refer :all]
            [clojure.test :as t]))

(t/deftest xor-bytes-test
  (t/testing "xor-bytes"
    (t/are [a b c]
        (java.util.Arrays/equals (byte-array c)
                                 (xor-bytes a b))
      [0 0 0] [0xff 0xff 0xff] [0xff 0xff 0xff]
      [1 2 3] [0xff 0xff 0xff] [-2 -3 -4])))
