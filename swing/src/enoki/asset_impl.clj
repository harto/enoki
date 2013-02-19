(ns enoki.asset-impl
  (:require [clojure.java.io :as io])
  (:import [javax.imageio ImageIO]))

(defn load-image [path on-load on-error]
  (->> (io/resource path)
       (ImageIO/read)
       (on-load path)))
