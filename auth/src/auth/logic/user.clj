(ns auth.logic.user
  (:require [schema.core :as s]
            [auth.models.user :as m.user]
            [crypto.password.bcrypt :as bcrypt]))

(s/defn valid-password? :- s/Bool
  [password :- s/Str]
  (<= 8 (count password)))

(s/defn add-user-id :- m.user/User
  [user :- m.user/User
   uuid :- s/Uuid]
  (assoc user :id uuid))

(s/defn hash-password :- m.user/User
  [user :- m.user/User]
  (update user :password bcrypt/encrypt))

(s/defn add-default-role :- m.user/User
  [user :- m.user/User]
  (update user :roles conj "user"))

(s/defn new-user :- m.user/User
  [user :- m.user/User
   uuid :- s/Uuid]
  (-> (add-user-id user uuid)
      hash-password
      add-default-role))
