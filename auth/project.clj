(defproject auth "0.1.0-SNAPSHOT"

  :description "Auth service"

  :url "https://github.com/alefeans/functional-microservices"

  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :plugins [[lein-ancient "1.0.0-RC3"]
            [lein-environ "1.2.0"]]

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [environ "1.2.0"]
                 [prismatic/schema "1.4.1"]
                 [org.slf4j/slf4j-simple "2.0.6"]
                 [com.stuartsierra/component "1.1.0"]
                 [io.pedestal/pedestal.route "0.5.10"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [hikari-cp "2.14.0"]
                 [org.postgresql/postgresql "42.5.1"]
                 [com.github.seancorfield/next.jdbc "1.3.847"]
                 [crypto-password "0.3.0"]]

  :profiles {:uberjar {:aot :all}
             :dev
             {:dependencies [[com.stuartsierra/component.repl "1.0.0"]
                             [prismatic/schema-generators "0.1.5"]]
              :source-paths   ["dev"]
              :env {:http-port   "8080"
                    :db-type     "postgresql"
                    :db-name     "auth"
                    :db-host     "localhost"
                    :db-port     "55000"
                    :db-user     "postgres"
                    :db-password "postgrespw"}
              :repl-options   {:init-ns user}}}
  
  :aliases {"dev" ["run" "-m" "auth.server/start!" :dev]}

  :test-paths ["test/"]

  :target-path "target/%s"

  :main ^{:skip-aot false} auth.server)
