^:clj (ns enoki.event-test
        (:require [enoki.event :as e])
        (:use [clojure.test :only [deftest testing is]]))

^:cljs (ns enoki.event-test
         (:require [cemerick.cljs.test :as _]
                   [enoki.event :as e])
         (:use-macros [cemerick.cljs.test :only [deftest testing is]]))

(deftest test-subscribe!
  (let [handler (constantly :updated-state)]
    (e/subscribe! :event-type handler)
    (is (= :updated-state (e/broadcast {} :event-type)))))

(deftest test-multiple-subscribers
  (e/subscribe! :foo (fn [state type & args] (update-in state [type] conj :baz)))
  (e/subscribe! :foo (fn [state type & args] (update-in state [type] conj :bar)))
  (is (= {:foo '(:baz :bar)} (e/broadcast {} :foo))))
