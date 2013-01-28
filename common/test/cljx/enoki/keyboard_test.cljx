(ns enoki.keyboard-test
  (:use [clojure.test]
        [enoki.keyboard]))

(deftest test-consume-events!
  (enqueue-event! :key-pressed :a)
  (enqueue-event! :key-released :a)
  (is (= [[:key-pressed :a] [:key-released :a]] (consume-events!)))
  (is (= [] (consume-events!))))
