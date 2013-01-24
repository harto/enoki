(ns enoki.keyboard-test
  (:use [clojure.test]
        [enoki.keyboard]))

(deftest test-pressing?
  (enqueue-event! :key-pressed :a)
  (enqueue-event! :key-pressed :b)
  (is (pressing? :a))
  (is (pressing? :a :b))
  (is (not (pressing? :a :b :c)))
  (enqueue-event! :key-released :a)
  (is (not (pressing? :a))))

(deftest test-consume-events!
  (enqueue-event! :key-pressed :a)
  (enqueue-event! :key-released :a)
  (is (= [[:key-pressed :a] [:key-released :a]] (consume-events!)))
  (is (= [] (consume-events!))))
