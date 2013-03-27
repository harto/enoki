^:clj (ns game.main
        (:require [clojure.string :as str]
                  [enoki.asset :as asset]
                  [enoki.engine :as enoki]
                  [enoki.event :as event]
                  [enoki.graphics :as gfx]
                  [enoki.logging]
                  [enoki.logging-macros :as log])
        (:use [enoki.core :only [now]]))

^:cljs (ns game.main
         (:require [clojure.string :as str]
                   [enoki.asset :as asset]
                   [enoki.engine :as enoki]
                   [enoki.event :as event]
                   [enoki.graphics :as gfx]
                   [enoki.logging :as _])
         (:require-macros [enoki.logging-macros :as log])
         (:use [enoki.core :only [now]])
         (:use-macros [enoki.cljs-macros :only [double]]))

;; ## Update

(defn initial-state []
  {:alien {:position {:x 10 :y 60}}})

(defn movement [pressed-keys]
  (let [x-offsets {:left -1 :right 1}
        y-offsets {:up -1 :down 1}]
    (reduce (fn [offset pressed-key]
              (update-in offset [x] + (get x-offsets pressed-key 0))
              (update-in offset [y] + (get y-offsets pressed-key 0)))
            {:x 0 :y 0}
            pressed-keys)))

(defn update [state]
  (let [{:keys [x y]} (movement (:pressed-keys state))]
    ))

;; ## Draw

(defn print-fps [ctx fps]
  (gfx/draw-text! ctx (format "%03.1ffps" (double fps)) 10 20))

(defn print-pressed-keys [ctx keys]
  (gfx/draw-text! ctx (format "keys: %s" (str/join ", " keys)) 10 40))

(defn render [state ctx]
  (-> ctx
      (gfx/clear!)
      (print-fps (enoki/fps state))
      (print-pressed-keys (:pressed-keys state))
      (gfx/draw-image! (get-in state [:assets "images/alien.png"])
                       (get-in state [:alien :x])
                       (get-in state [:alien :y]))))

;; ## Loop

(defn enter-loop [env]
  (event/subscribe! :update (fn [state _] (update state)))
  (event/subscribe! :render (fn [state _ ctx] (render state ctx)))
  (enoki/start (assoc env :state
                      ;; FIXME: stupid hack to get assets into :state
                      (merge (initial-state)
                             {:assets (:assets env)}))))

(defn start [env]
  (let [start-time (now)]
    (asset/load-images
     ["images/alien.png"
      "images/hamburger.gif"
      "images/stars.jpg"]
     :after-asset (fn [path _]
                    (log/info (format "Loaded %s" path)))
     :on-load (fn [images]
                (log/debug (format "Loaded %s images in %01.3f seconds"
                                   (count images)
                                   (double (/ (- (now) start-time) 1000))))
                (enter-loop (assoc-in env [:assets] images))))))
