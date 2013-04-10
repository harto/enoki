^:clj (ns game.main
        (:require [clojure.string :as str]
                  [enoki.asset :as asset]
                  [enoki.engine :as enoki]
                  [enoki.entity :as entity]
                  [enoki.event :as event]
                  [enoki.graphics :as gfx]
                  [enoki.logging]
                  [enoki.logging-macros :as log]
                  [game.component :as comp])
        (:use [enoki.core :only [now]]))

^:cljs (ns game.main
         (:require [clojure.string :as str]
                   [enoki.asset :as asset]
                   [enoki.engine :as enoki]
                   [enoki.entity :as entity]
                   [enoki.event :as event]
                   [enoki.graphics :as gfx]
                   [enoki.logging]
                   [game.component :as comp])
         (:require-macros [enoki.logging-macros :as log])
         (:use [enoki.core :only [now]])
         (:use-macros [enoki.cljs-macros :only [double]]))

(defn initial-state []
  {:entities [(entity/new (comp/sprite "images/alien.png")
                          (comp/position 10 60))]})

;; ## Update

(defn movement [pressed-keys]
  (let [x-offsets {:left -1 :right 1}
        y-offsets {:up -1 :down 1}]
    (reduce (fn [offset pressed-key]
              (-> offset
                  (update-in [:x] + (get x-offsets pressed-key 0))
                  (update-in [:y] + (get y-offsets pressed-key 0))))
            {:x 0 :y 0}
            pressed-keys)))

(defn update [state]
  (update-in state [:entities 0 :position]
             (fn [position {:keys [x y]}]
               (-> position
                   (update-in [:x] + x)
                   (update-in [:y] + y)))
             (movement (:pressed-keys state))))

;; ## Draw

(defn print-fps! [ctx fps]
  (gfx/draw-text! ctx (format "%03.1ffps" (double fps)) 10 20))

(defn print-pressed-keys! [ctx keys]
  (gfx/draw-text! ctx (format "keys: %s" (str/join ", " keys)) 10 40))

(defn draw-sprite! [ctx state entity]
  (let [image-id (get-in entity [:sprite :image-id])
        {:keys [x y]} (get entity :position)]
    (gfx/draw-image! ctx (asset/image state image-id) x y)))

(defn draw-sprites! [ctx state]
  (doseq [e (entity/with-component (:entities state) :sprite)]
    (draw-sprite! ctx state e)))

(defn render [state ctx]
  (-> ctx
      (gfx/clear!)
      (print-fps! (enoki/fps state))
      (print-pressed-keys! (:pressed-keys state))
      (draw-sprites! state)))

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
