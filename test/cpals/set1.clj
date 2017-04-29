(ns cpals.set1-test
  (:require [clojure.test :as t]
            [cpals.coding
             [b64 :as b64]
             [hex :as hex]]
            [cpals.core :refer [xor-bytes decode-single-byte-xor-cipher]]))

(t/deftest challenge1
  "http://cryptopals.com/sets/1/challenges/1"
  (t/testing "decode hex & encode base64"
    (let [hex-val "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
          b64-val "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"]
      (t/is (= (b64/encode (hex/decode hex-val)) b64-val)))))

(t/deftest challenge2
  "http://cryptopals.com/sets/1/challenges/2"
  (t/testing "xor byte arrays"
    (let [a (hex/decode "1c0111001f010100061a024b53535009181c")
          b (hex/decode "686974207468652062756c6c277320657965")
          expected "746865206b696420646f6e277420706c6179"]
      (t/is (= expected (hex/encode (xor-bytes a b)))))))

(t/deftest challenge3
  "http://cryptopals.com/sets/1/challenges/3"
  (t/testing "decode text scrambled with single-byte xor"
    (t/is
     (= (decode-single-byte-xor-cipher
         (hex/decode
          "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"))
        {:key \X, :text "Cooking MC's like a pound of bacon"}))))
