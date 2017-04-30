(ns cpals.xor-test
  (:require [clojure.test :as t :refer :all]
            [cpals.xor :refer :all]))

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

(t/deftest xor-buffer-with-key-test
  (t/are [a b c]
      (= (xor-buffer-with-key a b) c)
    [255 255 255 255 255] [1 2 4] '(254 253 251 254 253)
    [255 255 255 255 255] [1 2] '(254 253 254 253 254)))
