(ns auth.common.interceptors
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as bp]
            [io.pedestal.interceptor.helpers :as i]))

(def default-json-content-type
  (i/before
   (fn [{request :request :as context}]
     (if-not (= "application/json" (get request :content-type))
       (assoc-in context [:request :content-type] "application/json")
       context))))

(def default-request-data
  (i/before
   (fn [{request :request :as context}]
     (assoc-in context [:request :data] (:json-params request)))))

(def common-interceptors [default-json-content-type
                          http/json-body
                          (bp/body-params)
                          default-request-data])

(def add-handler (partial conj common-interceptors))
