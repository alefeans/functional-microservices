(ns auth.wire.out.user
  (:require [schema.core :as s]))

(s/defschema User
  {:id    s/Uuid
   :email s/Str
   :roles [s/Str]})
