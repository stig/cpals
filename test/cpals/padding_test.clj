(ns cpals.padding-test
  (:require [cpals.padding :as sut]
            [clojure.test :as t]))

(t/deftest pkcs7-test
  (t/testing "simple padding strings"
    (t/are [in n out]
        (= out (sut/pkcs7 n in))
      (vec "hello world") 11 [\h \e \l \l \o \space \w \o \r \l \d]
      (vec "hello world") 12 [\h \e \l \l \o \space \w \o \r \l \d 1]
      (vec "hello world") 16 [\h \e \l \l \o \space \w \o \r \l \d 5 5 5 5 5]
      (vec "hello world") 20 [\h \e \l \l \o \space \w \o \r \l \d 9 9 9 9 9 9 9 9 9]))
  (t/testing "input string too long"
    (t/is (thrown? Exception (sut/pkcs7 3 [1 2 3 4])))))
