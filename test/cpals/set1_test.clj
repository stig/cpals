(ns cpals.set1-test
  (:require [clojure.test :as t]
            [cpals.aes :as aes]
            [cpals.b64 :as b64]
            [cpals.hex :as hex]
            [cpals.util :refer [read-file read-lines utf8]]
            [cpals.xor :refer :all]))

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
      (t/is (= expected (hex/encode (xor-buffers a b)))))))

(t/deftest challenge3
  "http://cryptopals.com/sets/1/challenges/3"
  (t/testing "decode text scrambled with single-byte xor"
    (t/is
     (= "Cooking MC's like a pound of bacon"
        (:text
         (break-single-byte-key-cipher
          (hex/decode
           "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736")))))))

(t/deftest challenge4
  "http://cryptopals.com/sets/1/challenges/4"
  (t/testing "detecting single-char xor cipher text"
    (t/is (= "Now that the party is jumping\n"
             (:text
              (->> (read-lines "4.txt")
                   (map hex/decode)
                   detect-single-byte-key-cipher))))))

(t/deftest challenge5
  "http://cryptopals.com/sets/1/challenges/5"
  (t/testing "xor repeating key cipher"
    (let [cleartext "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal"
          key "ICE"
          ciphertext "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"]
      (t/is (= (hex/encode (xor-buffer-with-key (utf8 cleartext) key))
               ciphertext)))))

(t/deftest challenge6
  "http://cryptopals.com/sets/1/challenges/6"
  (t/testing "break repeating-key XOR cipher"
    (let [guess (break-repeating-key-cipher
                 (b64/decode
                  (read-file "6.txt")))]
      (t/is (= (:key guess) "Terminator X: Bring the noise"))
      (t/is (.startsWith (:text guess) "I'm back and I'm ringin' the bell")))))


(t/deftest challenge7
  "http://cryptopals.com/sets/1/challenges/7"
  (let [secret "YELLOW SUBMARINE"
        encrypted (byte-array (b64/decode (read-file "7.txt")))
        decrypted (aes/decrypt encrypted secret)
        first-line "I'm back and I'm ringin' the bell"]
    (t/is (.startsWith (String. decrypted) first-line))))

(t/deftest challenge8
  "http://cryptopals.com/sets/1/challenges/8"
  (t/is (= 132 (aes/detect-ecb-encrypted-cipher (->> (read-lines "8.txt")
                                                     (map hex/decode))))))
