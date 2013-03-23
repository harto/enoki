^:clj (ns enoki.engine-test
        (:require [enoki.engine :as engine]
                  [enoki.event :as e]
                  [enoki.graphics :as g])
        (:use [clojure.test :only [deftest testing is]]))

^:cljs (ns enoki.engine-test
         (:require [cemerick.cljs.test :as test]
                   [enoki.engine :as engine]
                   [enoki.event :as e]
                   [enoki.graphics :as g])
         (:use-macros [cemerick.cljs.test :only [deftest testing is]]))

(deftest test-fire-key-events
  (testing "handlers return updated state"
    (e/subscribe! :key-pressed (fn [state type key]
                                 (update-in state [:keys-pressed] conj key)))
    (e/subscribe! :key-released (fn [state type key]
                                  (update-in state [:keys-released] conj key)))
    (let [initial-state {:keys-pressed #{}
                         :keys-released #{}}
          events [[:key-pressed :a] [:key-pressed :b] [:key-released :b] [:key-released :a]]
          updated-state (engine/fire-key-events initial-state events)]
      (is (= #{:a :b} (:keys-pressed updated-state)))
      (is (= #{:a :b} (:keys-released updated-state))))))

(defrecord DummyDisplay []
  g/Display
  (init-display! [_])
  (display-width [_])
  (display-height [_])
  (render [this f] (f :dummy-context)))

(deftest test-render
  (let [state {}
        renderer-args (atom nil)
        renderer (fn [& args] (reset! renderer-args args))]
    (e/subscribe! :render renderer)
    (engine/render state (->DummyDisplay))
    (is (= [state :render :dummy-context] @renderer-args))))
