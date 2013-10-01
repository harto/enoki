^:clj (ns enoki.keyboard-test
        (:require [enoki.keyboard :as k])
        (:use [clojure.test :only [deftest testing is]]))

^:cljs (ns enoki.keyboard-test
        (:require [cemerick.cljs.test :as _]
                  [enoki.keyboard :as k])
        (:use-macros [cemerick.cljs.test :only [deftest testing is]]))

(deftest test-updated-pressed-keys
  (let [events [[:key-pressed :foo]
                [:key-released :foo]
                [:key-pressed :bar]
                [:key-pressed :foo]]]
    (is (= #{:foo :bar} (k/update-pressed-keys nil events)))))

(deftest test-persists-currently-pressed-keys
  (let [events [[:key-pressed :foo]]]
    (is (= #{:foo :bar} (k/update-pressed-keys #{:bar} events)))))
