(ns auth.components.database
  (:require [com.stuartsierra.component :as component]
            [next.jdbc :as jdbc]
            [next.jdbc.connection :as connection])
  (:import (com.zaxxer.hikari HikariDataSource)))

(defrecord Database [config connection]
  component/Lifecycle

  (start [this]
         (if connection
           this
           (let [conn (connection/->pool HikariDataSource (get-in config [:config :db]))]
             (.close (jdbc/get-connection conn)) ;; initializes the pool and performs a validation check
             (assoc this :connection conn))))

  (stop [this]
        (when connection
          (.close connection))
        (assoc this :connection nil)))

(defn new-database []
  (map->Database {}))
