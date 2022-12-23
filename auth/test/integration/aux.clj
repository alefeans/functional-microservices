(ns integration.aux
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :refer [response-for]]
            [com.stuartsierra.component :as component]
            [clojure.test :refer :all]
            [auth.diplomat.http-server :as d-http]
            [cheshire.core :as json]))

(def url-for (route/url-for-routes d-http/routes))

(defn service-fn [system]
  (get-in system [:server :server ::http/service-fn]))

(defmacro with-test-system [[sut system] & body]
  `(let [~sut (component/start (~system :test))]
     (try
       ~@body
       (finally
         (component/stop ~sut)))))

(defn json-response-for [system verb url & options]
  (-> (apply response-for (service-fn system) verb url options)
      (update :body (fn [body] (json/parse-string body true)))))
