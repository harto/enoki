#+clj
(ns game.main
  (:require [clojure.string :as str]
            [enoki.asset :as asset]
            [enoki.engine :as enoki]
            [enoki.entity :as entity]
            [enoki.event :as event]
            [enoki.graphics :as gfx]
            [enoki.logging]
            [enoki.logging-macros :as log]
            [game.component :as comp]
            [game.sprite :as sprite])
  (:use [enoki.core :only [now]]))

#+cljs
(ns game.main
  (:require [clojure.string :as str]
            [enoki.asset :as asset]
            [enoki.engine :as enoki]
            [enoki.entity :as entity]
            [enoki.event :as event]
            [enoki.graphics :as gfx]
            [enoki.logging]
            [game.component :as comp]
            [game.sprite :as sprite])
  (:require-macros [enoki.logging-macros :as log])
  (:use [enoki.core :only [now]])
  (:use-macros [enoki.cljs-macros :only [double]]))

(def slinky-frames 17)

(defn slinky-animation-image [state]
  (let [frame (mod (quot (now) 50) slinky-frames)]
    (asset/image state (sprite/path :slinky :east frame))))

(defn slinky-position [state]
  {:x 10 :y 60})

(defn draw-slinky [ctx state entity]
  (let [{:keys [x y]} (slinky-position state)]
    (gfx/draw-image! ctx (slinky-animation-image state) x y)))

(defn initial-state []
  {:entities [(entity/new (comp/renderable draw-slinky))]})

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
  state
  #_(update-in state [:entities 0 :position]
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

(defn draw-entity! [ctx state entity]
  (let [f (get-in entity [:renderable :render])]
    (f ctx state entity)))

(defn draw-entities! [ctx state]
  (doseq [e (entity/filter-component (:entities state) :renderable)]
    (draw-entity! ctx state e)))

(defn render [state ctx]
  (-> ctx
      (gfx/clear! "#000")
      (print-fps! (enoki/fps state))
      (print-pressed-keys! (:pressed-keys state))
      (draw-entities! state)))

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
     (for [frame (range slinky-frames)]
       (sprite/path :slinky :east frame))
     :after-asset (fn [path _]
                    (log/info (format "Loaded %s" path)))
     :on-load (fn [images]
                (log/debug (format "Loaded %s images in %01.3f seconds"
                                   (count images)
                                   (double (/ (- (now) start-time) 1000))))
                (enter-loop (assoc-in env [:assets] images))))))
