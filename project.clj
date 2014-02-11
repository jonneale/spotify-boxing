(defproject spotify-boxing "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http            "0.7.7"]
                 [org.clojure/math.combinatorics "0.0.7"]
                 [compojure                  "1.1.6"]
                 [ring "1.2.0"]
                 [hiccup "1.0.5"]]
  :ring {:handler spotify-boxing.web/app}
  :main spotify-boxing.jetty
  :plugins [[lein-ring "0.8.5"]])
