(ns auth.system
  (:require [com.stuartsierra.component :as component]
            [auth.components.config :as cfg]
            [auth.components.database :as db]
            [auth.components.routes :as r]
            [auth.diplomat.http-server :as d-http]
            [auth.components.service :as svc]
            [auth.components.server :as srv]))

(defn system [env]
  (component/system-map
   :config    (cfg/new-config env)
   :routes   (r/new-routes d-http/routes)
   :database (component/using (db/new-database) [:config]) 
   :service  (component/using (svc/new-service) [:config :routes :database])
   :server   (component/using (srv/new-server) [:config :service])))
