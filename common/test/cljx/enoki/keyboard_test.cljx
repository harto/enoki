(ns enoki.keyboard-test
  (:use [clojure.test]
        [enoki.keyboard]))

(deftest test-discard-duplicate-events
  (enqueue-event! :key-pressed :a)
  (enqueue-event! :key-pressed :b)
  (enqueue-event! :key-pressed :a)
  (enqueue-event! :key-pressed :a)
  (is (= [[:key-pressed :a] [:key-pressed :b] [:key-pressed :a]] @event-queue)))

(deftest test-consume-events!
  (enqueue-event! :key-pressed :a)
  (enqueue-event! :key-released :a)
  (is (= [[:key-pressed :a] [:key-released :a]] (consume-events!)))
  (is (= [] (consume-events!))))

(deftest test-currently-pressed-keys
  (let [events [[:key-pressed :foo] [:key-released :foo] [:key-pressed :bar] [:key-pressed :foo]]]
    (is (= #{:foo :bar} (currently-pressed-keys nil events)))))
