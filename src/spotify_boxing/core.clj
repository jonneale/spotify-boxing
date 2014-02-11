(ns spotify-boxing.core
  (:require [clj-http.client :as client]
            [clojure.math.combinatorics :as combo]))

(def url "https://ws.spotify.com/search/1/track.json")

(defn get-tracks
  [category]
  (client/get url {:query-params {:q category}
                   :accept :json
                   :as     :json}))

(defn fits?
  [song-length max-length]
  (fn [bin]
    (> max-length (+ song-length (reduce + (map :length bin))))))

(defn find-first-bin
  [bins song-length max-length]
  (count
   (take-while
    (complement (fits? song-length max-length))
    bins)))

(defn add-to-bin
  [bin song]
  (conj bin song))

(defn populate-bins
  [max-length n songs]
  (loop [bins [] queue songs x 0]
    (if (and (seq queue) (< x n))
      (let [{:keys [length]} (first queue)
            first-free-bin (find-first-bin bins length max-length)
            bin-to-add-to  (if (<= (count bins) first-free-bin) [] (nth bins first-free-bin))]
        (recur (concat (take first-free-bin bins)
                       [(add-to-bin bin-to-add-to (first queue))]
                       (drop (inc first-free-bin) bins))
               (rest queue)
               (inc x)))
      bins)))

(defn measure-length
  [songs]
  (reduce + (map :length songs)))

(def create-playlists
  (memoize
   (fn
     [category max-length]
     (->> category
          get-tracks
          :body
          :tracks
          (populate-bins max-length 1000)
          (sort-by measure-length)
          reverse
          first))))
