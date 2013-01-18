(ns enoki.event-test
  (:use [clojure.test]
        [enoki.event]))

(deftest test-subscribe!
  (let [handler (fn [state type & args] :updated-state)]
    (subscribe! :event-type handler)
    (is (= :updated-state (broadcast {} :event-type)))))

(deftest test-multiple-subscribers
  (subscribe! :foo (fn [state type & args] (update-in state [type] conj :baz)))
  (subscribe! :foo (fn [state type & args] (update-in state [type] conj :bar)))
  (is (= {:foo '(:baz :bar)} (broadcast {} :foo))))
