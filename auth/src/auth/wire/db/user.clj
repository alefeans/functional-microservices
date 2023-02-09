(ns auth.wire.db.user
  (:require [schema.core :as s]))

(s/defschema User
  {:id         s/Uuid
   :email      s/Str
   :password   s/Str
  ;;  :created_at s/Str
  ;;  :updated_at s/Str
   :roles      [s/Str]})
