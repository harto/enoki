(ns game.main
  (:require [clojure.string :as str]
            [enoki.engine :as enoki]
            [enoki.event :as e]
            [enoki.graphics :as g]))

(defn print-ticks [ctx ticks]
  (g/draw-text! ctx ticks 10 20))

(defn print-pressed-keys [ctx keys]
  (g/draw-text! ctx (str/join ", " keys) 10 40))

(defn render [state ctx]
  (-> ctx
      (g/clear!)
      (print-ticks (:ticks state))
      (print-pressed-keys (:pressed-keys state))))

(defn initial-state []
  {})

(defn start [env]
  (e/subscribe! :render (fn [state _ ctx] (render state ctx)))
  (enoki/start (assoc env :state (initial-state))))
