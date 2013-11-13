;; Components are the state-holding parts of the
;; [component-entity-system architecture][1].
;;
;;  [1]: http://www.chris-granger.com/2012/12/11/anatomy-of-a-knockout/

(ns enoki.component-macros)

(defmacro defcomponent
  "Define a component-creation function, named by `type`, that accepts `params`
  and returns a map consisting of the remaining key-value pairs. E.g.

    (defcomponent position [x y z]
      :x x
      :y y
      :z (or z -1)

    (position 2 3 4)
    ;; => {:enoki.component/type :position
           :x 2
           :y 3
           :z 4}

  Components are normally grouped into entities using functions in
  `enoki.entity`."
  [type params & kvs]
  `(defn ~type ~(vec params)
     (into {:enoki.component/type ~(keyword type)}
           (hash-map ~@kvs))))
