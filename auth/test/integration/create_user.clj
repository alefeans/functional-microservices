(ns integration.create-user
  (:require [clojure.test :refer :all]
            [schema-generators.generators :as g]
            [auth.wire.in.user :as wire-in]
            [auth.system :as system]
            [cheshire.core :as json]
            [integration.aux :refer [with-test-system json-response-for url-for]]))

(deftest creating-user
  (testing "If creates user properly"
    (with-test-system [sut system/system]
      (let [payload               (json/encode {:username "test", :password "pass"})
            {:keys [status body]} (json-response-for sut :post (url-for :create-user) :body payload)]
        (is (= 201 status))
        (is (= {:username "test"} (:data body)))))))
