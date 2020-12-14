(ns p96
  (:require [clojure.string :as str])
  )

;; Following Peter Norvig's sudoku solver
;; http://norvig.com/sudoku.html

;; load input
(def grids
  (partition 9 (filter #(not (str/includes? % "Grid"))
                       (str/split-lines (slurp "resources/p096_sudoku.txt")))))

(def f (flatten (map #(map identity %) (first grids))))

(defn cross [as bs] (for [a as b bs] (str/join "" (vector a b))))

(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))


(def digits '[\1 \2 \3 \4 \5 \6 \7 \8 \9])
(def cols digits)
(def rows '[\a \b \c \d \e \f \g \h \i])

(def squares (cross rows cols))

(def unitlist (concat
               (for [c cols] (for [r rows] (str/join "" (vector r c)))) ; Columns
               (for [r rows] (for [c cols] (str/join "" (vector r c)))) ; Rows
               (for [rs '((\a \b \c) (\d \e \f) (\g \h \i)) cs '((\1 \2 \3) (\4 \5 \6) (\7 \8 \9))] (cross rs cs)) ; Boxes
               )
  )

(def units (into (sorted-map) (for [s squares] (vector s (for [u unitlist :when (in? u s)] u)))))

(def peers (into (sorted-map) (for [s squares] (vector s (set (flatten (get units s)))))))

;; Validations
(assert (= (count squares) 81))
(assert (= (count unitlist) 27))

(defn grid-values [grid]
  (do
    (assert (= (count grid) 81))
    (into (sorted-map) (map vector squares grid))
    )
  )

(defn parse-grid [grid]
  (let [buf (into (sorted-map) (map vector squares (repeat digits)))
        values (grid-values grid)]
    buf )
  )

(println (parse-grid f))
