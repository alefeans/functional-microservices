(ns auth.adapters.user
  (:require [schema.core :as s]
            [auth.wire.in.user :as in.user]
            [auth.wire.db.user :as db.user]
            [auth.wire.out.user :as out.user]
            [auth.models.user :as m.user]))

(s/defn wire-create-user->internal :- m.user/User
  [user :- in.user/CreateUser]
  (assoc user :roles []))

(s/defn internal->wire :- out.user/User
  [user :- m.user/User]
  (dissoc user :password))

(s/defn internal->db :- db.user/User
  [user :- m.user/User]
  user)
