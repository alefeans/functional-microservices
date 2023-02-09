(ns auth.models.user
  (:require [schema.core :as s]))

(s/defschema User
  {:email               s/Str
   :password            s/Str
   :roles               [s/Str]
   (s/optional-key :id) s/Uuid})
