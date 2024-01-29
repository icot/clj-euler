(ns p81
  (:require [clojure.string :as str])
  (:require [ubergraph.core :as uber]))

;; Load file as list of integers

(def N 80)

(def lines (-> (slurp "scripts/resources/p081_matrix.txt")
               (str/split #"\n")))

(def cells (map #(Integer/parseInt %)
                (-> (map #(str/split % #",") lines)
                    (flatten))))




(def graph1
  (uber/graph [:a :b] [:a :c] [:b :d]))

(uber/pprint graph1)


