(ns cpals.core-test
  (:require [clojure.test :as t]
            [cpals.core :refer [transpose]]))

(t/deftest transpose-test
  (t/testing "transposing a matrix"
    (t/is (= [[1 2 3]
              [4 5 6]
              [7 8 9]]
             (transpose
              [[1 4 7]
               [2 5 8]
               [3 6 9]])))))
