(ns auth.diplomat.http-server
  (:require [io.pedestal.http.route :as route]
            [auth.diplomat.interceptors :as i]))

(defn hello-world [{query-params :query-params}]
  {:status 200
   :body {:hello (str "Hello " (get query-params :name "World"))}})

(defn echo-post [{data :data}]
  {:status 200
   :body {:data data}})

(def routes
  (route/expand-routes
   #{["/" :get (i/add-handler hello-world) :route-name :hello-world]
     ["/echo" :post (i/add-handler echo-post) :route-name :echo]}))
