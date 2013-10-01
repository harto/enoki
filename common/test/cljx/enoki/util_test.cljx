#+clj
(ns enoki.engine-test
  (:require [enoki.util :as util])
  (:use [clojure.test :only [deftest testing is]]))

#+cljs
(ns enoki.engine-test
  (:require [cemerick.cljs.test :as test]
            [enoki.util :as util])
  (:use-macros [cemerick.cljs.test :only [deftest testing is]]))

(deftest test-queue-creation
  ;; XXX: do we need better tests here?
  (is (not (nil? (util/queue))))
  (is (not (nil? (util/queue :foo)))))
