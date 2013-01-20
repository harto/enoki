;; Core engine functionality

^:clj (ns enoki.engine
        (:require [enoki.graphics :as g]
                  [enoki.util.logging] ; required for dependency resolution
                  [enoki.util.logging-macros :as log]
                  ))

^:cljs (ns enoki.engine
         (:require [enoki.graphics :as g]
                   [enoki.util.logging] ; required for dependency resolution
                   )
         (:require-macros [enoki.util.logging-macros :as log]))

(defn render [ctx state]
  (-> ctx
      (g/clear!)
      (g/draw-text! "Hello, world" 10 20)))

(defn update [state]
  state)

(defn tick [{:keys [state display] :as env}]
  (g/render display (fn [ctx] (render ctx state)))
  (assoc-in env [:state] (update state)))

(defn start
  "Enters the game loop. This function might return immediately or once the game
   loop is exited, depending on the implementation of loop-fn."
  [env]
  (g/init-display! (:display env))
  (log/info "Entering game loop")
  (let [loop-forever (:loop-fn env)]
    (loop-forever tick env)))
