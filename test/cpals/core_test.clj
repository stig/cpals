(ns cpals.core-test
  (:require [clojure.test :refer :all]
            [cpals.core :refer :all]
            [clojure.test :as t]))

(t/deftest xor-buffers-test
  (t/are [a b c]
      (= (xor-buffers a b) c)
    [0 0 0] [0xff 0xff 0xff] '(0xff 0xff 0xff)
    [1 2 3] [0xff 0xff 0xff] '(0xfe 0xfd 0xfc)))

(t/deftest xor-buffer-with-byte-test
  (t/are [a b c]
      (= (xor-buffer-with-byte a b) c)
    [0 0 0] 0xff '(0xff 0xff 0xff)
    [1 2 3] 0xff '(0xfe 0xfd 0xfc)))
