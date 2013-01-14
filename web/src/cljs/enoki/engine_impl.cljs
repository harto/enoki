(ns enoki.engine-impl
  (:require [goog.Timer :as timer]))

(defn loop-forever [tick state]
  (timer/callOnce #(loop-forever tick (tick state)) 1))
