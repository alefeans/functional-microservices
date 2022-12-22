(ns auth.components.config
  (:require [com.stuartsierra.component :as component]
            [environ.core :as e]))

(defrecord Config [env]
  component/Lifecycle

  (start [this]
  (let [config-map {:env   (if env env :dev)
                    :db   {:host     (e/env :db-host)
                           :port     (e/env :db-port)
                           :dbtype   (e/env :db-type)
                           :dbname   (e/env :db-name)
                           :username (e/env :db-user)
                           :password (e/env :db-password)}
                    :http {:port     (Integer. (e/env :http-port))}}]
    (assoc this :config config-map)))

  (stop [this] (assoc this :config nil)))

(defn new-config [env]
  (map->Config {:env env}))
