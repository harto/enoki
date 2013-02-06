;; ClojureScript compatibility hacks

(ns enoki.cljs-macros
  (:refer-clojure :exclude [double]))

(defmacro double [x] x)
