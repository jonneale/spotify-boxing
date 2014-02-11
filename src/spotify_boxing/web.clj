(ns spotify-boxing.web
  (:use [hiccup.core]
        [compojure.handler])
  (:require [compojure.core     :as compojure :refer [GET POST]]
            [ring.adapter.jetty :as jetty]
            [spotify-boxing.core :as core]))


(compojure/defroutes main-routes
  ;;http://localhost:8080
  (GET "/"
       []
       (html [:form {:action "/playlist" :method "POST"}
              [:input {:type :text :id "query" :name "query" :placeholder "query"}]
              [:input {:type :text :id "max-length" :name "max-length" :placeholder "max length in seconds"}]
              [:input {:type "submit"}]]))

  (POST "/playlist"
        [query max-length]
        (println query)
        (println max-length)
        (let [length (Integer/parseInt max-length)
              playlist (core/create-playlists query length)
              total           (reduce + (map :length playlist))]
          (html [:div
                 [:h2 (str "Total length: " total)]
                 (for [song playlist]
                   [:div
                    [:p "Title " [:a {:href (:href song)} (:name song)]]
                    [:p (str "Length: " (:length song))]
                    [:p (str "Album: " (:name (:album song)))]])
                 ]))))

(def app
  (api main-routes))

(defn -main [& args]
  (println (str "Starting the switch-api on port 8080...."))
  (jetty/run-jetty app {:port 8080}))
