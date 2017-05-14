(ns cpals.aes.cbc
  (:require
   [cpals.xor :as xor])
  (:import javax.crypto.Cipher
           javax.crypto.spec.SecretKeySpec))

(def ^:private iv
  "Initialization vector"
  (repeat 16 0))

(def ^:private AES
  "AES/ECB/NOPADDING")

(defn encrypt
  "Encrypt cleartext with key in cbc mode. Uses an IV of all zeros."
  [cleartext secret]
  (let [cipher (doto (Cipher/getInstance AES)
                 (.init Cipher/ENCRYPT_MODE (SecretKeySpec. secret "AES")))]
    (loop [blocks (partition-all 16 cleartext)
           prev iv
           acc []]
      (if (seq blocks)
        (let [cur (first blocks)
              xored (xor/xor-buffers prev cur)
              encrypted (.update cipher (byte-array xored))]
          (recur (rest blocks) encrypted (concat acc encrypted)))
        (->> (.doFinal cipher)
             (concat acc)
             byte-array)))))

(defn decrypt
  "Decrypt cbc-encoded ciphertext. Assumes an IV of all zeros has been used."
  [ciphertext secret]
  (-> (doto (Cipher/getInstance AES)
        (.init Cipher/DECRYPT_MODE (SecretKeySpec. secret "AES")))
      (.doFinal ciphertext)
      (xor/xor-buffers (concat iv ciphertext))
      byte-array))
