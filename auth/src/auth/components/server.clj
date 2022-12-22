(ns auth.components.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defn- test-env? [{config :config}]
  (= (:env config) :test))

(defn build-server! [config {service :service}]
  (let [server (http/create-server service)]
    (if (test-env? config)
      server
      (http/start server))))

(defrecord Server [config service server]
  component/Lifecycle

  (start [this]
         (if server
           this
           (assoc this :server (build-server! config service))))
  
  (stop [this]
    (when (and server (not (test-env? config)))
      (http/stop server))
    (assoc this :server nil)))

(defn new-server []
  (map->Server {}))
