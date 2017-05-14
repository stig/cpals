(ns cpals.set2-test
  (:require  [clojure.test :as t]
             [cpals.padding :as padding]
             [cpals.aes.cbc :as cbc]
             [cpals.b64 :as b64]
             [cpals.util :refer [utf8 read-file]]))

(t/deftest challenge9
  (t/is (= (padding/pkcs7 20 (vec "YELLOW SUBMARINE"))
           [\Y \E \L \L \O \W \space \S \U \B \M \A \R \I \N \E 4 4 4 4])))

(t/deftest challenge10
  (t/is (.startsWith
         (String.
          (cbc/decrypt
           (byte-array (b64/decode (read-file "10.txt")))
           (utf8 "YELLOW SUBMARINE")))
         "I'm back and I'm ringin' the bell")))
