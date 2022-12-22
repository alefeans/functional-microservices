(ns auth.components.routes
  (:require [com.stuartsierra.component :as component]))

(defrecord Routes [routes]
  component/Lifecycle

  (start [this]
    (assoc this :routes routes))
  
  (stop [this]
    (assoc this :routes nil)))

(defn new-routes [routes]
  (map->Routes {:routes routes}))
 