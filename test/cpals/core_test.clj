(ns cpals.core-test
  (:require [clojure.test :as t]
            [cpals.core :refer [rank-keysizes transpose]]
            [cpals.util :refer [utf8]]
            [cpals.xor :refer [xor-buffer-with-key]]))

(t/deftest rank-keysizes-test
  (t/testing "actual keysize should be in first 5 guesses"
    (let [bytes (utf8 "This challenge isn't conceptually hard, but it involves actual error-prone coding.")]
      (t/are [x]
          (let [ciphertext (xor-buffer-with-key bytes x)
                keysizes (take 5 (map first (rank-keysizes ciphertext)))]
            (some #{(count x)} keysizes))
        "ICE"
        "abcdefGHIJ"
        "Abcdefg"))))

(t/deftest transpose-test
  (t/testing "transposing a matrix"
    (t/is (= [[1 2 3]
              [4 5 6]
              [7 8 9]]
             (transpose
              [[1 4 7]
               [2 5 8]
               [3 6 9]])))))
