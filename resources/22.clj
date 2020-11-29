(ns euler.22
  (:require [clojure.string :as str]))


(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(defn alphabetical-value "Compute the alphabetical value of a word"
  [word]
  ; Filter out quotes (\34)
  (reduce + (map #(- % 64) (filter #(not (= % 34)) (map int word))))
  )

(let [
      names (sort (str/split (slurp "./p022_names.txt") #","))
      name-values (map #(alphabetical-value %) names)
      positions (range 1 (inc (count names)))
      pos-val (map vector positions name-values)
      ]
  (println (take 10 names))
  (println (take 10 pos-val))
  (println (take 10 (map #(* (first %) (last %)) pos-val)))
  (println (reduce + (map #(* (first %) (last %)) pos-val)))
  )
