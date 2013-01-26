(ns game.main
  (:require [enoki.engine :as enoki]
            [enoki.event :as e]
            [enoki.graphics :as g]))

(defn render [state ctx]
  (-> ctx
      (g/clear!)
      (g/draw-text! (:ticks state) 10 20)))

(defn initial-state []
  {})

(defn start [env]
  (e/subscribe! :render (fn [_ state ctx] (render state ctx)))
  (enoki/start (assoc env :state (initial-state))))
