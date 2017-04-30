(ns cpals.core-test
  (:require [clojure.test :as t]
            [cpals
             [core :refer [rank-keysizes]]
             [util :refer [utf8]]
             [xor :refer [xor-buffer-with-key]]]))

(t/deftest rank-keysizes-test
  (t/testing "actual keysize should be in first 5 guesses"
    (let [bytes (utf8 "This challenge isn't conceptually hard, but it involves actual error-prone coding. The other challenges in this set are there to bring you up to speed. This one is there to qualify you. If you can do this one, you're probably just fine up to Set 6. ")]
      (t/are [x]
          (let [ciphertext (xor-buffer-with-key bytes x)
                keysizes (take 3 (map first (rank-keysizes ciphertext)))]
            (some #{(count x)} keysizes))
        "ICE"
        "CIE"
        "ECI"))))
