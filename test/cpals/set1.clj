(ns cpals.set1-test
  (:require [clojure.test :as t]
            [cpals.coding
             [b64 :as b64]
             [hex :as hex]]))

(t/deftest challenge1
  "http://cryptopals.com/sets/1/challenges/1"
  (let [hex-val "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
        b64-val "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"]
    (t/is (= (b64/encode (hex/decode hex-val)) b64-val))))
