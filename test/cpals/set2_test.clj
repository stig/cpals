(ns cpals.set2-test
  (:require  [clojure.test :as t]
             [cpals.padding :as padding]))

(t/deftest challenge9
  (t/is (= (padding/pkcs7 20 (vec "YELLOW SUBMARINE"))
           [\Y \E \L \L \O \W \space \S \U \B \M \A \R \I \N \E 4 4 4 4])))
