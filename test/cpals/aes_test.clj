(ns cpals.aes-test
  (:require [cpals.aes :refer :all]
            [clojure.test :as t]))

(t/deftest encrypt-decrypt-test
  (let [key "YELLOW SUBMARINE"
        msg "Hello World"]
    (t/is (= msg (String. (decrypt (encrypt msg key) key))))))
