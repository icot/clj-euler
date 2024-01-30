(ns p81
  (:require [clojure.string :as str])
  (:require [ubergraph.core :as uber])
  (:require [ubergraph.alg :as uberalgs]))
;; Load file as list of integers

(def lines (-> (slurp "scripts/resources/p081_matrix.txt")
;;(def lines (-> (slurp "scripts/resources/p081_sample.txt")
              (str/split #"\n")))

(def cells (map #(Integer/parseInt %)
                (-> (map #(str/split % #",") lines)
                    (flatten))))
(def N 80)

(defn ind [i j]
  (+ (* i N) j))

(defn neighbours81 [i j]
  (cond
    (and (= i (dec N)) (= j (dec N))) '()
    (= (dec N) j) (list (ind (inc i) j))
    (= (dec N) i) (list (ind i (inc j)))
    :else (list (ind (inc i) j) (ind i (inc j)))))



(def edges81 (apply concat (for [ i (range N) j (range N)]
                             (map #(list (ind i j) % (nth cells (ind i j))) (neighbours81 i j)))))




(def g81 (uber/add-directed-edges* (uber/graph) edges81))
(def path-81 (uberalgs/shortest-path g81 {:start-node 0 :end-node 6399 :cost-attr :weight}))
;; Need to add last cell
;;(uberalgs/pprint-path path-81)
(println (+ (nth cells (ind (dec N) (dec N))) (.cost path-81)))

;; Problem 82
(defn neighbours82 [i j]
  (cond
    (and (= i (dec N)) (= j (dec N))) '()
    (= (dec N) j) (list (ind (inc i) j) (ind (dec i) j))
    (= (dec N) i) (list (ind i (inc j)) (ind (dec i) j))
    :else (list (ind (inc i) j) (ind (dec i) j) (ind i (inc j)))))

(def edges82 (apply concat (for [ i (range N) j (range N)]
                             (map #(list (ind i j) % (nth cells (ind i j))) (neighbours82 i j)))))

(def g82 (uber/add-directed-edges* (uber/graph) edges82))

(def costs (for [i (range N)]
             (for [j (range N)]
               (+ (.cost (uberalgs/shortest-path g82
                                                 {:start-node (ind i 0) :end-node (ind j (dec N)) :cost-attr :weight}))
                  (nth cells (ind j (dec N)))))))

(println (apply min (flatten costs))) ;; 260324

;; P83

(defn neighbours83 [i j]
  (cond
    (and (= i (dec N)) (= j (dec N))) '()
    (and (zero? i) (zero? j)) (list (ind i (inc j)) (ind (inc i) j))
    (and (zero? i) (= (dec N) j)) (list (ind i (dec j)) (ind (inc i) j))
    (and (= i (dec N)) (zero? j)) (list (ind i (inc j)) (ind (dec i) j))    
    (= (dec N) j) (list (ind (inc i) j) (ind (dec i) j) (ind i (dec j)))
    (zero? j) (list (ind (inc i) j) (ind (dec i) j) (ind i (inc j)))
    (= (dec N) i) (list (ind i (inc j)) (ind i (dec j)) (ind (dec i) j))
    (zero? i) (list (ind i (inc j)) (ind i (dec j)) (ind (inc i) j))
    :else (list (ind (inc i) j) (ind (dec i) j) (ind i (inc j)) (ind i (dec j)))))

(def edges83 (apply concat (for [ i (range N) j (range N)]
                             (map #(list (ind i j) % (nth cells (ind i j))) (neighbours83 i j)))))

(def g83 (uber/add-directed-edges* (uber/graph) edges83))
(uber/pprint g83)

(def path-83 (uberalgs/shortest-path g83 {:start-node 0 :end-node 6399 :cost-attr :weight}))
(println (+ (nth cells (ind (dec N) (dec N))) (.cost path-83)))

(uberalgs/pprint-path path-83)
