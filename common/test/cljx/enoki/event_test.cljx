^:clj (ns enoki.event-test
        (:require [enoki.event :as e])
        (:use [clojure.test :only [deftest testing is]]
              [enoki.util :only [queue]]))

^:cljs (ns enoki.event-test
         (:require [cemerick.cljs.test :as _]
                   [enoki.event :as e])
         (:use [enoki.util :only [queue]])
         (:use-macros [cemerick.cljs.test :only [deftest testing is]]))

(deftest test-subscribe!
  (let [handler (constantly :updated-state)]
    (e/subscribe! :event-type handler)
    (is (= :updated-state (e/broadcast {} :event-type)))))

(deftest test-multiple-subscribers
  (e/subscribe! :foo (fn [state type & args] (update-in state [type] conj :baz)))
  (e/subscribe! :foo (fn [state type & args] (update-in state [type] conj :bar)))
  (is (= {:foo '(:baz :bar)} (e/broadcast {} :foo))))

(deftest broadcast-all
  (let [received (atom [])
        handler (fn [state type & args]
                  (swap! received conj [state type args])
                  state)]
    (e/subscribe! :type-a handler)
    (e/subscribe! :type-b handler)
    (e/broadcast-all :state '((:type-a (:foo :bar))
                              (:type-b (:baz :bla))))
    (is (= ['(:state :type-a (:foo :bar))
            '(:state :type-b (:baz :bla))]
           @received))))

(deftest test-enqueue!
  (e/enqueue! :some-event :bar :baz)
  (is (= @e/pending (queue '(:some-event (:bar :baz))))))

(deftest test-drain!
  (e/drain!)
  (is (empty? @e/pending))
  (e/enqueue! :some-event :bar :baz)
  (is (= (queue '(:some-event (:bar :baz))) (e/drain!)))
  (is (empty? @e/pending)))
