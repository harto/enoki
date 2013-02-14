^:clj (ns game.main
        (:require [clojure.string :as str]
                  [enoki.engine :as enoki]
                  [enoki.event :as event]
                  [enoki.graphics :as gfx]))

^:cljs (ns game.main
         (:require [clojure.string :as str]
                   [enoki.engine :as enoki]
                   [enoki.event :as event]
                   [enoki.graphics :as gfx])
         (:use-macros [enoki.cljs-macros :only [double]]))

(defn print-fps [ctx fps]
  (gfx/draw-text! ctx (format "%03.1ffps" (double fps)) 10 20))

(defn print-pressed-keys [ctx keys]
  (gfx/draw-text! ctx (str/join ", " keys) 10 40))

(defn render [state ctx]
  (-> ctx
      (gfx/clear!)
      (print-fps (enoki/fps state))
      (print-pressed-keys (:pressed-keys state))))

(defn initial-state []
  {})

(defn start [env]
  (event/subscribe! :render (fn [state _ ctx] (render state ctx)))
  (enoki/start (assoc env :state (initial-state))))
