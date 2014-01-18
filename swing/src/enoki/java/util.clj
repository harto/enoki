;; JVM helpers

(ns enoki.java.util
  (:require [clojure.string :as s]))

(defn camelise
  "Convert a hyphen-separated-string into a CamelCasedString."
  [s]
  (->> (s/split (name s) #"-")
       (mapcat s/capitalize)
       (s/join)))
