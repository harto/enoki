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

(def get-display
  "Function that returns the graphics display"
  (atom nil))

(defn- init-platform-bindings!
  "Populates the environment with various platform-specific functions."
  [{:keys [display]}]
  (log/info "Establishing platform-specific bindings")
  (reset! get-display (constantly display)))

(defn render [display state]
  (-> (g/context display)
      (g/clear!)
      (g/draw-text! "Hello, world" 10 20)))

(defn tick [state]
  (render (@get-display) state))

(defn start
  "Enters the game loop. This function might return immediately or once the game
   loop is exited, depending on the implementation of loop-fn."
  [env initial-state]
  (init-platform-bindings! env)
  (log/info "Entering game loop")
  (let [loop-forever (:loop-fn env)]
    (loop-forever tick initial-state)))
