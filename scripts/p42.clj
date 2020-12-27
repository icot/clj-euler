(ns p42
  (:require [clojure.string :as str])
  (:require [euler.core :as ec]))

(def words (str/split (slurp "scripts/resources/p042_words.txt") #","))

(def triangle (into (sorted-map) (zipmap (take 1000 (ec/triangle-gen)) (repeat true))))

(defn word-to-value [w]
  (let [s (filter #(not= % \") (seq w))
        ref (dec (int \A))]
    (reduce + (map #(- (int %) ref) s))))

(newline)

(prn (count (filter #(triangle % false) (map word-to-value words))))
