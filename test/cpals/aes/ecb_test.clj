(ns cpals.aes.ecb-test
  (:require [clojure.test :as t]
            [cpals.aes.ecb :refer :all]
            [cpals.util :refer [utf8]]))

(t/deftest encrypt-decrypt-test
  (let [key "YELLOW SUBMARINE"
        msg "0123456789abcdef"]
    (t/is (= msg (String.
                  (decrypt
                   (encrypt (utf8 msg) (utf8 key))
                   (utf8 key)))))))
