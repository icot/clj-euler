(ns p96
  (:require [clojure.string :as str])
  )

;; load input
(def sudokus
  (partition 9 (filter #(not (str/includes? % "Grid"))
                       (str/split-lines (slurp "resources/p096_sudoku.txt")))))

(println (first sudokus))

;; Following Peter Norvig's sudoku solver
;; http://norvig.com/sudoku.html

(def digits '[\1 \2 \3 \4 \5 \6 \7 \8 \9])
(def cols digits)
(def rows '[\a \b \c \d \e \f \g \h \i])

(def squares (for [r rows c cols] (vector r c)))


(println squares)

;; display method
;;
