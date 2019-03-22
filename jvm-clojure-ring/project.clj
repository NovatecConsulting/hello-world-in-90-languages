(defproject jvm-clojure-ring "0.1.0-SNAPSHOT"
  :description "Hello World via HTTP"
  :url "https://github.com/nt-ca-aqe/hello-world-in-90-languages"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring-json-response "0.2.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler jvm-clojure-ring.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
