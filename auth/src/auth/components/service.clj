(ns auth.components.service
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor.helpers :as i]))

(defn components-in-request [components]
  (i/before
   (fn [context]
     (assoc-in context [:request :components] components))))

(defn base-service [{:keys [config routes] :as components}]
  (-> {::http/type   :jetty
       ::http/routes (:routes routes)
       ::http/port   (get-in config [:config :http :port])}
      http/default-interceptors
      (update ::http/interceptors conj (components-in-request components))))

(defn dev-service [service-map]
  (-> (merge service-map {::http/host "0.0.0.0" ::http/join? false})
      http/dev-interceptors))

(defn build-service [{:keys [config] :as components}]
  (let [base (base-service components)]
    (if (= (get-in config [:config :env]) :prod)
      base
      (dev-service base))))

(defrecord Service [config routes database service]
  component/Lifecycle

  (start [this]
    (if service
      this
      (assoc this :service (build-service this))))

  (stop [this]
    (assoc this :service nil)))

(defn new-service []
  (map->Service {}))
