(ns auth.diplomat.http-server
  (:require [io.pedestal.http.route :as route]
            [auth.common.interceptors :as i]
            [auth.adapters.user :as a.user]
            [auth.controllers.user :as c.user]))

(defn hello-world [{query-params :query-params}]
  {:status 200
   :body {:hello (str "Hello " (get query-params :name "World"))}})

(defn echo-post [{data :data}]
  {:status 200
   :body {:data data}})

(defn create-user [{data :data {:keys [database]} :components}]
  {:status 201
   :body (-> (a.user/wire-create-user->internal data)
             (c.user/create-user! database)
             (a.user/internal->wire))})

(def routes
  (route/expand-routes
   #{["/" :get (i/add-handler hello-world) :route-name :hello-world]
     ["/echo" :post (i/add-handler echo-post) :route-name :echo]
     ["/users" :post (i/add-handler create-user) :route-name :create-user]}))
