(ns cpals.aes.ecb
  (:require [cpals.util :refer [utf8]])
  (:import javax.crypto.Cipher
           javax.crypto.spec.SecretKeySpec))

(def ^:private AES "AES")

(defn- secret-key [raw-key]
  (SecretKeySpec. (utf8 raw-key) AES))

(def ^:private aes-cipher (Cipher/getInstance AES))

(defn encrypt [plaintext rawkey]
  "Encrypt plaintext using rawkey as the key. rawkey has to be a multiple of
  16 characters. A byte-array is returned."
  (-> (doto aes-cipher
        (.init Cipher/ENCRYPT_MODE (secret-key rawkey)))
      (.doFinal (utf8 plaintext))))

(defn decrypt [ciphertext rawkey]
  "Decrypt ciphertext using the rawkey. A byte-array is returned."
  (-> (doto aes-cipher
        (.init Cipher/DECRYPT_MODE (secret-key rawkey)))
      (.doFinal ciphertext)))

(defn detect-ecb-encrypted-cipher
  "Find a needle in a haystack. (But don't break it.)"
  [byte-seqs]
  (->> byte-seqs
       (map #(partition 16 %))
       (map #(into #{} %))
       (map count)
       (map-indexed vector)
       (apply min-key second)
       first))
