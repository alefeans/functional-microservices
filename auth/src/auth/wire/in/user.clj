(ns auth.wire.in.user
  (:require [schema.core :as s]))

(s/defschema CreateUser
  {:email    s/Str
   :password s/Str})
