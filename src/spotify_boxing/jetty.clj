(ns spotify-boxing.jetty
  (:require [spotify-boxing.web          :as web]
            [ring.adapter.jetty          :as ring])
  (:gen-class))

(defn -main [& args]
  (println (str "Starting the energy-switch-recon-api on port 8082...."))
  (ring/run-jetty #'web/app {:port 8082}))
