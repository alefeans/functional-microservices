(ns integration.aux
  (:require [auth.diplomat.http-server :as d-http]
            [cheshire.core :as json]
            [clojure.test :refer :all]
            [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :refer [response-for]]
            [schema.core :as s]))

(defmacro with-test-system [[sut system] & body]
  `(let [~sut (component/start (~system :test))]
     (try
       (s/with-fn-validation ~@body)
       (finally
         (component/stop ~sut)))))

(def url-for (route/url-for-routes d-http/routes))

(defn service-fn [system]
  (get-in system [:server :server ::http/service-fn]))

(defn json-response-for [system verb url & options]
  (-> (apply response-for (service-fn system) verb url options)
      (update :body (fn [body] (json/parse-string body true)))))
