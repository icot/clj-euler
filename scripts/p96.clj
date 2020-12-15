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
;;

(defn grid-values [grid]
  (do
    (assert (= (count grid) 81))
    (into (sorted-map) (map vector squares grid))
    )
  )

(defn prune-peers
  ([values s d] (prune-peers values s d (get peers s)))
  ([values s d peers]
   (if (empty? peers) values
       (let [p (first peers)
             new-vs (filter #(not= % d) (get values p))
             new-values (assoc values p new-vs)]
         (prune-peers new-values s d (rest peers))
         )
       )
   )
  )

(defn assign-units [s d]
  (for [u (get units s)]
    (let [dplaces (for [su u] )]
      (if (empty? dplaces)
        false
        (if (= (count dplaces) 1)
        )
      )
    )
  )

(defn eliminate [values s d]
  (let [vs (get values s)
        new-vs (filter #(not= % d) vs)]
    (cond
      (= vs new-vs) values ; Value already removed. Nothing to do
      (empty? new-vs) false ; Error condition
      (= (count new-vs) 1) (let [new-values (assoc values s new-vs)]
                             (prune-peers new-values s d))
      :else (assign-units s d)
      )
    )
  )

(defn assign [values s d]
  nil
  )

(defn parse-grid [grid]
  (let [buf (into (sorted-map) (map vector squares (repeat digits)))
        values (grid-values grid)]
    buf )
  )

(defn display [values]
  (let [
        width (inc (apply max (for [s squares] (count (get values s)))))
        line (str/join "+" (repeat 3 (str/join "" (repeat (* 3 width) "-"))))
        ]
    (for [r rows]
      (let [vline (str/join " " (for [c cols]
                                  (let [elements (str/join "" (get values (str/join "" (vector r c))))]
                                    (if (or (= c \3) (= c \6))
                                        (str/join "" (str elements " |"))
                                        (str/join "" elements)))))]
        (if (or (= r \c) (= r \f))
          (do (println vline) (println line))
          (println vline)
          )
        )
      )
    )
  )
