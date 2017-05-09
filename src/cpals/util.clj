(ns cpals.util
  (:require [clojure.java.io :as io]))

(defn utf8
  "Return the UTF-8 bytes of a string"
  [str]
  (.getBytes str "UTF-8"))

(defn read-lines
  "Read a file from `resources/` folder and return it as a sequence of lines"
  [filename]
  (with-open [rdr (io/reader (io/resource filename))]
    (doall (line-seq rdr))))

(defn read-file
  "Read in a file from `resoures/` folder and return its content as a string"
  [filename]
  (apply str (read-lines filename)))
