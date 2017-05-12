(ns cpals.aes.ecb-test
  (:require [clojure.test :as t]
            [cpals.aes.ecb :refer :all]))

(t/deftest encrypt-decrypt-test
  (let [key "YELLOW SUBMARINE"
        msg "Hello World"]
    (t/is (= msg (String. (decrypt (encrypt msg key) key))))))
