(ns auth.controllers.user
  (:require [schema.core :as s]
            [io.pedestal.log :as log]
            [auth.diplomat.db :as db]
            [auth.logic.user :as l.user]
            [auth.models.user :as m.user])
  (:import [java.util UUID]))

;; TODO catch-and-handle interceptor

(s/defn create-user! :- m.user/User
  [{:keys [password] :as user} :- m.user/User
   database]
  (when (db/user-exists? database user)
    (log/warn :message (str "User " (:email user) " already exists")))
  (if (l.user/valid-password? password)
    (db/create-user! database (l.user/new-user user (UUID/randomUUID)))
    (log/warn :message (str "Password must contain more than 8 characters"))))
