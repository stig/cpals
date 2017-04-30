(ns cpals.hamming-test
  (:require [clojure.test :as t]
            [cpals.hamming :refer [count-set-bits hamming-distance]]))

(t/deftest count-set-bits-test
  (t/are [bits cnt]
      (= cnt (count-set-bits bits))
    ;; single bit set
    0x1  1
    0x2  1
    0x4  1
    0x8  1
    0x10 1
    0x20 1

    ;; multi bits set
    0xff 8
    0xf0 4
    0x0f 4
    0xf  4))

(t/deftest hamming-distance-test
  (t/are [x y distance]
      (= (hamming-distance (map byte x) (map byte y)))
    "this is a test" "wokka wokka!!!" 37))
