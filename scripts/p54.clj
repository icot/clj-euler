(ns p54
  (:require [clojure.string :as str]))

(def deals (str/split-lines (slurp "scripts/resources/p054_poker.txt")))

(def card-value {\2 2 \3 3 \4 4 \5 5 \6 6 \7 7 \8 8 \9 9 \T 10 \J 11 \Q 12 \K 13 \A 14})

; read-hands
(defn read-hands [deal]
  (let [raw-cards (str/split deal #" ")
        cards (for [card raw-cards] { :value (card-value (first card)) :suit (last card) })]
    (partition 5 cards)))

; eval-hands

(defn my-sort-hash [h]
  (into (sorted-map-by (fn [key1 key2]
                         (compare [(get h key2) key2]
                                  [(get h key1) key1])))))

; TODO: Resolve ties taking into account the cards present in special hands
(defn eval-hand [hand]
  (letfn [(pair? [hand] (= (count (set (map :value hand))) 4))
          (two-pairs? [hand] (and
                              (= (count (set (map :value hand))) 3)
                              (= (apply max (vals (frequencies (map :value hand)))) 2)))
          (three-of-a-kind? [hand] (and
                                    (= (count (set (map :value hand))) 3)
                                    (= (apply max (vals (frequencies (map :value hand)))) 3)))
          (straight? [hand]
            (let [values (-> (map :value hand) sort reverse)]
              (every? #(= 1 %)  (map - values (rest values)))))
          (flush? [hand] (= (count (set (map :suit hand))) 1))
          (full-house? [hand] (and
                               (= (count (set (map :value hand))) 2)
                               (= (apply max (vals (frequencies (map :value hand)))) 3)))
          (poker? [hand] (and
                          (= (count (set (map :value hand))) 2)
                          (= (max (apply vals (frequencies (map :value hand)))) 4)))
          (royal-flush? [hand] (and (straight? hand) (flush? hand)))
          ]
    (do
;      (newline)
;      (prn (sort-by :value hand))
      (cond
        (pair? hand) (* 1 )
        (two-pairs? hand) (* 10 )
        (three-of-a-kind? hand) (* 100 )
        (straight? hand) (* 1000 (apply max (map :value hand)))
        (flush? hand) (* 10000 )
        (full-house? hand) (* 100000 )
        (royal-flush? hand) (* 1000000 (apply max (map :value hand)))
        :else 0))))

(defn compare-hand [h1 h2]
  (do
;    (prn h1 h2)
  (let [c1 (first h1)
        c2 (first h2)]
    (if (empty (rest h1))
      (if (> c1 c2) 1 0)
      (if (> c1 c2)
        (compare-hand (rest h1) (rest h2))
        0)))))

;(def f (read-hands (first deals)))
;(def h1 (first f))
;(def h2 (last f))
;(prn (compare-hand (sort #(compare %2 %1) (map :value h1)) (sort #(compare %2 %1) (map :value h2))))

(newline)
(prn "Start")
(time
(loop [ds deals acc 0]
  (let [hands (read-hands (first ds))
        p1 (first hands)
        p2 (last hands)
        e1 (eval-hand p1)
        e2 (eval-hand p2)]
    (do
      (if (empty? (rest ds))
        (prn "P1 wins: " acc)
        (cond
          (> e1 e2) (recur (rest ds) (inc acc))
          (< e1 e2) (recur (rest ds) acc)
          :else (recur (rest ds) (+ acc (compare-hand
                                         (sort #(compare %2 %1) (map :value p1))
                                         (sort #(compare %2 %1) (map :value p2)))))))))))
