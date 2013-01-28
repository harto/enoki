(ns enoki.engine-test
  (:use [clojure.test]
        [enoki.engine])
  (:require [enoki.event :as e]
            [enoki.graphics :as g]))

(deftest test-fire-key-events
  (testing "handlers return updated state"
    (e/subscribe! :key-pressed (fn [state type key]
                                 (update-in state [:keys-pressed] conj key)))
    (e/subscribe! :key-released (fn [state type key]
                                  (update-in state [:keys-released] conj key)))
    (let [initial-state {:keys-pressed #{}
                         :keys-released #{}}
          events [[:key-pressed :a] [:key-pressed :b] [:key-released :b] [:key-released :a]]
          updated-state (fire-key-events initial-state events)]
      (is (= #{:a :b} (:keys-pressed updated-state)))
      (is (= #{:a :b} (:keys-released updated-state))))))

(defrecord DummyDisplay []
  g/Display
  (g/init-display! [_])
  (g/display-width [_])
  (g/display-height [_])
  (g/render [this f] (f :dummy-context)))

(deftest test-render
  (let [state {}
        renderer-args (atom nil)
        renderer (fn [& args] (reset! renderer-args args))]
    (e/subscribe! :render renderer)
    (render state (->DummyDisplay))
    (is (= [state :render :dummy-context] @renderer-args))))
