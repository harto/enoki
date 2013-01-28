(ns enoki.web
  (:require [clojure.browser.repl :as repl]
            [enoki.graphics.canvas :as gfx]
            [enoki.keyboard :as kbd]
            [enoki.util.logging :as logging])
  (:require-macros [enoki.util.logging-macros :as log])
            )

(defn- key-pressed [e]
  (log/info "key-pressed: %s" (.-keyCode e))
  (.preventDefault e)
  )

(defn- key-released [e]
  (log/info "key-released: %s" (.-keyCode e))
  (.preventDefault e)
  )

(defn capture-key-events! [element]
  (doto element
    (.addEventListener "keypress" key-pressed)
    (.addEventListener "keyup" key-released)))

(defn init! []
  (repl/connect "http://localhost:9000/repl")
  (logging/init!)
  (logging/set-level! :info))

(defn env
  "Initialise and return a web-based environment."
  [canvas-element]
  (capture-key-events! js/window)
  {:display (gfx/->CanvasDisplay canvas-element)})
