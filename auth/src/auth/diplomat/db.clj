(ns auth.diplomat.db
  (:require [schema.core :as s]
            [auth.models.user :as m.user]
            [auth.adapters.user :as a.user]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [next.jdbc.result-set :as rs]))

(s/defn user-exists? :- s/Bool
  [{conn :connection}
   {email :email} :- m.user/User]
  (:exists (jdbc/execute-one! conn ["select exists(select 1 from test where email = ?)" email])))

(s/defn create-user! :- m.user/User
  [{conn :connection}
   user :- m.user/User]
  (sql/insert! conn :test (a.user/internal->db user))
  user)

;; TODO adapt input/output from these DIPLOMATs and LOG the jdbc execution outputs
;; (s/defn get-user! :- (s/maybe m.user/User)
;;   [{conn :connection}
;;    user :- m.user/User]
;;   (jdbc/execute-one! conn ["select * from test where email = ?" (:email user)] {:builder-fn rs/as-unqualified-maps}))
