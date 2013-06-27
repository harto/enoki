(ns enoki.asset-impl
  (:require [clojure.java.io :as io])
  (:import [javax.imageio ImageIO]))

(defn load-image [path on-load on-error]
  (try
    (->> (io/resource path)
         (ImageIO/read)
         (on-load path))
    (catch Exception e
      (on-error path e))))
