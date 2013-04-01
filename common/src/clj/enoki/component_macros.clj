(ns enoki.component-macros)

(defmacro defcomponent
  "Define a component-creation function, named by `type`, that accepts `params`
   and returns a map consisting of the remaining key-value pairs. E.g.

       (defcomponent foo [bar baz]
         :bar bar
         :baz (inc baz))

       (foo :quux 2) ; => {:enoki.component/type :foo, :bar :quux, :baz 3}"
  [type params & kvs]
  `(defn ~type ~(vec params)
     (into {:enoki.component/type ~(keyword type)}
           (hash-map ~@kvs))))
