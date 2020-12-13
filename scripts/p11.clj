(ns p11
  (:require [clojure.string :as str]))

(def filename "./p011_matrix.txt")

(def N 20)

(def data (map #(str/split % #" ") (str/split (slurp filename) #"\n")))

(def int-data (into [] (doall (map #(map (fn [s] (Integer/parseInt s)) %) (doall data)))))
(def flat-data (flatten int-data))

(def mh (into (sorted-map) (map vector (range (count flat-data)) flat-data)))

(defn process [l acc]
  (do
;    (println (take 4 l) acc)
    (let
        [[a b c d & ls] l
         p (* a b c d)]
      (if (empty? ls) acc
        (if (> p acc)
          (recur (rest l) p)
          (recur (rest l) acc)
          )
        )
    )
    )
  )

(def max-by-rows (process (flatten int-data) 0))
(time (println max-by-rows))

(defn get-col [h i]
  (let [is (range i (count h) N)]
    (do
;      (println is)
      (map #(get h %) is)
      )
    )
  )

(defn max-by-cols [h col acc]
  (let [mc (process (get-col h col) 0)]
    (if (= col 20)
      acc
      (if (> mc acc)
        (recur h (inc col) mc)
        (recur h (inc col) acc)
        )
      )
    )
  )


(time (println (max-by-cols mh 0 0)))
