(ns cpals.aes.cbc-test
  (:require [clojure.test :as t]
            [cpals.aes.cbc :refer [decrypt encrypt]]
            [cpals.util :refer [utf8]]))

(t/deftest encode-decode-test
  (t/testing "aes cbc mode"
    (t/are [key cleartext]
        (let [keybytes (utf8 key)
              bytes (utf8 cleartext)
              decoded (String.
                       (decrypt (encrypt bytes keybytes) keybytes))]
          (= cleartext decoded))
      "YELLOW SUBMARINE" "yellow submarine"
      "YELLOW SUBMARINE" "Yellow SubmarineyELLOW sUBMARINE")))
