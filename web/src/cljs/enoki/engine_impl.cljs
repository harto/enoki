(ns enoki.engine-impl
  (:require [goog.Timer :as timer]))

(defn loop-forever [tick env]
  (timer/callOnce #(loop-forever tick (tick env)) 1))
