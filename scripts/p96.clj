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
  (empty? (filter #(> (count (last %)) 1) (seq values))))

(defn grid-values [grid]
  (do
    (assert (= (count grid) 81))
    (into (sorted-map)
          (filter #(not= (last %) [\0]) (map vector squares (map vector grid))))
    )
  )

; Same in spirit, but short circuits the return?
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

(defn assign [values s v]
  (let [
        candidates (get values s)
        new-values (if (in? candidates v) (assoc values s (list v)) false)
        ]
    (prune new-values)
    )
  )


(defn solve [values]
  (if (solved? values) values
      ; Choose alternative and recur
      (let [min  (apply min (for [s squares :when (> (count (get values s)) 1)] (count (get values s))))
            ss (for [s squares :when (= (count (get values s)) min)] s)
            s (first ss)
            candidates (for [d (get values s)] (assign values s d))]
        (do
;          (println s (get values s))
          (solve (first (filter map? candidates)))
        )
      )
    )
  )

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
  (if (<= c 2)
    (let [f (flatten (map #(map identity %) (first g)))
          v (parse-grid f)
          h (reduce + (map #(Character/digit (first %) 10) (vector (get v "a1") (get v "a2") (get v "a3"))))
          ]
      (do
        (newline)
        (println "Grid " c "acc: " acc)
        (display (parse-grid f))
        (newline)
        (recur (rest g) (inc c) (+ acc h))
        )
      )
    count
    )
  )
