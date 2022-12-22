(ns integration.hello-world
  (:require [clojure.test :refer :all]
            [auth.system :as system]
            [cheshire.core :as json]
            [integration.aux :refer [with-test-system json-response-for url-for]]))

(deftest hello-world-test
  (testing "Simple hello world response"
    (with-test-system [sut system/system]
      (let [{:keys [status body]} (json-response-for sut :get (url-for :hello-world))]
        (is (= 200 status))
        (is (= "Hello World" (:hello body))))))

  (testing "Hello world with query params"
    (with-test-system [sut system/system]
      (let [{:keys [status body]} (json-response-for
                                   sut
                                   :get
                                   (url-for :hello-world :query-params {:name "Alefe"}))]
        (is (= 200 status))
        (is (= "Hello Alefe" (:hello body)))))))

(deftest echo-test
  (testing "Testing echo response"
    (with-test-system [sut system/system]
      (let [payload               (json/encode {:username "alefe"})
            {:keys [status body]} (json-response-for sut :post (url-for :echo) :body payload)]
        (is (= 200 status))
        (is (= {:username "alefe"} (:data body)))))))
