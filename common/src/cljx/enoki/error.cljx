(ns enoki.error)

^:clj (defn error
        ([message]
           (error message nil))
        ([message throwable]
           (if throwable
             (Exception. message throwable)
             (Exception. message))))

^:cljs (defn error
         ([message]
            (error message nil))
         ([message throwable]
            (if throwable
              ;; FIXME: is there a nicer way to display nested exceptions?
              (js/Error. (format "%s: %s" message (.-message throwable)))
              (js/Error. message))))
