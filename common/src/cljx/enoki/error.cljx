(ns enoki.error)

^:clj (defn error [message]
        (Exception. message))

^:cljs (defn error [message]
         (js/Error. message))
