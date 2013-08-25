(ns game.sprite)

(defn path [key direction id]
  (format "images/%s/%s-%s.png" (name key) (name direction) id))
