^:clj (ns enoki.keyboard-test
        (:require [enoki.keyboard :as k])
        (:use [clojure.test :only [deftest testing is]]))

^:cljs (ns enoki.keyboard-test
        (:require [cemerick.cljs.test :as _]
                  [enoki.keyboard :as k])
        (:use-macros [cemerick.cljs.test :only [deftest testing is]]))

(deftest test-consume-events!
  (k/consume-events!)
  (k/enqueue-event! :key-pressed :a)
  (k/enqueue-event! :key-released :a)
  (is (= [[:key-pressed :a] [:key-released :a]] (k/consume-events!)))
  (is (= [] (k/consume-events!))))

(deftest test-currently-pressed-keys
  (let [events [[:key-pressed :foo]
                [:key-released :foo]
                [:key-pressed :bar]
                [:key-pressed :foo]]]
    (is (= #{:foo :bar} (k/currently-pressed-keys nil events)))))

(deftest test-persists-currently-pressed-keys
  (let [events [[:key-pressed :foo]]]
    (is (= #{:foo :bar} (k/currently-pressed-keys #{:bar} events)))))
