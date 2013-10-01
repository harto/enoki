#+clj
(ns enoki.engine-test
  (:require [enoki.engine :as engine]
            [enoki.event :as e]
            [enoki.graphics :as g])
  (:use [clojure.test :only [deftest testing is]]))

#+cljs
(ns enoki.engine-test
  (:require [cemerick.cljs.test :as test]
            [enoki.engine :as engine]
            [enoki.event :as e]
            [enoki.graphics :as g])
  (:use-macros [cemerick.cljs.test :only [deftest testing is]]))

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
