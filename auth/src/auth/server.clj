(ns auth.server
  (:gen-class)
  (:require [auth.system :as system]
            [com.stuartsierra.component :as component]))

(defn start! [env] (component/start (system/system env)))

(defn -main [& _]
  (start! :prod))
