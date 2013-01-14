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

(defn render [display state]
  (-> (g/context display)
      (g/clear!)
      (g/draw-text! "Hello, world" 10 20)))

(defn tick [state]
  (render g/*display* state))

(defn start []
  (log/info "Hello, world")
  ;; FIXME: obviously this has to happen more than once
  (tick nil))
