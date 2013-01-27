(ns enoki.engine-test
  (:use [clojure.test]
        [enoki.engine])
  (:require [enoki.event :as e]
            [enoki.graphics :as g]))

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
