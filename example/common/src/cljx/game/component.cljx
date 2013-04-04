;; Game-specific components

^:clj (ns game.component
        (:use [enoki.component-macros :only [defcomponent]]))

^:cljs (ns game.component
         (:use-macros [enoki.component-macros :only [defcomponent]]))

(defcomponent position [x y]
  :x x
  :y y)

(defcomponent sprite [image-id]
  :image-id image-id)
