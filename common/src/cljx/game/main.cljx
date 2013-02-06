^:clj (ns game.main
        (:require [clojure.string :as str]
                  [enoki.engine :as enoki]
                  [enoki.event :as e]
                  [enoki.graphics :as g]))

^:cljs (ns game.main
         (:require [clojure.string :as str]
                   [enoki.engine :as enoki]
                   [enoki.event :as e]
                   [enoki.graphics :as g])
         (:use-macros [enoki.cljs-macros :only [double]]))

(defn print-fps [ctx fps]
  (g/draw-text! ctx (format "%03.1ffps" (double fps)) 10 20))

(defn print-pressed-keys [ctx keys]
  (g/draw-text! ctx (str/join ", " keys) 10 40))

(defn render [state ctx]
  (-> ctx
      (g/clear!)
      (print-fps (enoki/fps state))
      (print-pressed-keys (:pressed-keys state))))

(defn initial-state []
  {})

(defn start [env]
  (e/subscribe! :render (fn [state _ ctx] (render state ctx)))
  (enoki/start (assoc env :state (initial-state))))
