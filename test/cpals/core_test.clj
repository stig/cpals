(ns cpals.core-test
  (:require [clojure.test :as t]
            [cpals
             [core :refer [rank-keysizes]]
             [util :refer [utf8]]
             [xor :refer [xor-buffer-with-key]]]))

(t/deftest rank-keysizes-test
  (t/testing "actual keysize should be in first 5 guesses"
    (let [bytes (utf8 "The quick brown fox jumped over the lazy dog")]
      (t/are [x]
          (let [ciphertext (xor-buffer-with-key bytes x)
                keysizes (map first (rank-keysizes bytes))
                best-keysizes (take 5 keysizes)]
            (some #{(count x)} best-keysizes))
        "ICE"
        "hello"
        "world"
        "Stig"
        "XY"))))
