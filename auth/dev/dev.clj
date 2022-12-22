#_:clj-kondo/ignore
(ns dev
  (:require [auth.system :as s]
            [com.stuartsierra.component.repl :refer [reset set-init start stop]]))

(set-init (fn [_] (s/system :dev)))

(comment
  (stop)
  (start)
  (reset)
  )