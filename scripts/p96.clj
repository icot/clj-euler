(ns p96
  (:require [clojure.string :as str])
  )

;; Following Peter Norvig's sudoku solver
;; http://norvig.com/sudoku.html

;; load input
(def grids
  (partition 9 (filter #(not (str/includes? % "Grid"))
                       (str/split-lines (slurp "resources/p096_sudoku.txt")))))

(def f (flatten (map #(map identity %) (first (rest grids)))))
(def f0 (flatten (map #(map identity %) (first grids))))

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

(defn solved? [values]
  (= (count (filter #(= (count (last %)) 1) (seq values))) 81)
  )

(defn grid-values [grid]
  (do
    (assert (= (count grid) 81))
    (into (sorted-map)
          (filter #(not= (last %) [\0]) (map vector squares (map vector grid))))
    )
  )


(defn prune-peers
  ([values s d] (prune-peers values s d (get peers s)))
  ([values s d peers]
   (if (empty? peers) values
       (let [p (first peers)
             new-vs (filter #(not= % d) (get values p))
             new-values (assoc values p new-vs)]
         (if (= p s)
           (recur values s d (rest peers))
           (recur new-values s d (rest peers))
           )
         )
       )
   )
  )


(defn prune
  ([values]
   (let [ss (keys (into {} (filter (fn [e] (= 1 (count (last e)))) (seq values))))]
     (prune values ss ss)))
  ([values sso ss]
   (let [ssd (keys (into {} (filter (fn [e] (= 1 (count (last e)))) (seq values))))]
     (if (empty? ss)
       (if (= (count sso) (count ssd))
         values
         (prune values)
         )
       (recur (prune-peers values (first ss) (first (get values (first ss)))) sso (rest ss))
       )
    )
   )
  )

(defn parse-grid [grid]
  (let [buf (into (sorted-map) (map vector squares (repeat digits)))
        start-grid (grid-values grid)
        values (merge buf start-grid)]
    (prune values)
    )
  )

(defn assign-by-unit
  ([values s d] (assign-by-unit values s d (get units s)))
  ([values s d units]
   ;(do
   ;  (println s d units)
     (if (empty? units)
       values
       (for [un units]
         (let [dplaces (for [su un :when (in? (get values su) d)] su)]
           (if (empty? (rest dplaces))
             (assoc values (first dplaces) (vector d))
             (assign-by-unit values s d (rest units)))
           )
         )
       )
     ;)
   )
  )

; TODO: The previous bug is fixed, but on some grids the search blocks. Still proper
; units verification missing

(defn valid? [values]
  (let [
        m (apply min (for [s squares] (count (get values s))))
        ]
    (if (and (>= m 1) true)
      true
      false
      )
    )
  )

(defn assign [values s v]
  (let [
        candidates (get values s)
        new-values (prune (if (in? candidates v) (assoc values s (list v)) false))
        ]
    (if (valid? new-values)
      new-values
      false)
    )
  )

; Re-implementation in proper Clojure of Norvig's search method
; https://github.com/neolee/sudoku-norvig/blob/master/sudoku.clj
(defn search
  "Using depth-first search and propagation, try all possible values"
  [values]
  (when values
    (let [scount (comp count values)] ;digits remaining
      (if (every? #(= 1 (scount %)) squares)
        values ;solved!
        (let [s (apply min-key scount (filter #(< 1 (scount %)) squares))]
          (some identity (for [d (values s)]
                           (search (assign values s d)))))))))

;; Display
(defn display [values]
  (let [
        width (inc (apply max (for [s squares] (count (get values s)))))
        line (str/join "+" (repeat 3 (str/join "" (repeat (* 3 width) "-"))))
        ls (flatten (for [r rows]
                      (let [vline (str/join " "
                                            (for [c cols]
                                              (let [elements (str/join "" (get values (str/join "" (vector r c))))]
                                                (if (or (= c \3) (= c \6))
                                                  (str/join "" (str elements " |"))
                                                  (str/join "" elements)))))]
                        (if (or (= r \c) (= r \f))
                          (vector vline line)
                          vline))))]
    (doseq [l ls] (println l))
    )
  )
;; Main loop
(loop [g grids c 1 acc 0]
  (if (<= c 50)
    (let [f (flatten (map #(map identity %) (nth grids (dec c))))
          v (parse-grid f)
          s (search v)
          h (reduce +
                    (map *
                         '(100 10 1)
                         (map #(Character/digit (first %) 10) (vector (get s "a1") (get s "a2") (get s "a3")))))
          ]
      (do
        (newline)
        (println "Grid " c "acc: " acc)
        ;(try (display (solve (parse-grid f))) (catch Exception e))
        (display s)
        (newline)
        (recur (rest g) (inc c) (+ acc h))
        )
      )
    count
    )
  )


; TODO: Execution time is too slow, most probably caused by bad clojure idioms
; BUG: The final sudoku line is not added to the total correctly!
