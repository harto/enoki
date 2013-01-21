(ns game.main
  (:require [enoki.main :as enoki]
            [enoki.event :as e]
            [enoki.graphics :as g]))

(defn render [state ctx]
  (-> ctx
      (g/clear!)
      (g/draw-text! "Hello again, world" 10 20)))

(defn start [env]
  (e/subscribe! :render (fn [_ state ctx] (render state ctx)))
  (enoki/start env))
