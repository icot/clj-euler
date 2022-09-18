(ns p84
  (:require [clojure.set :as cs]))

(def N 10000000)

(def stats (atom {}))
(def community-chest (atom (shuffle (range 1 17))))
(def chance-chest (atom (shuffle (range 1 17))))
(def streak (atom 0))
(def cc-moves {1 0, 2 10})
(def chance-moves {1 0, ; G0
                   2 10, ; Jail
                   3 11, ; C1
                   4 24, ; E3
                   5 39, ; H2
                   6 5,  ; R1
                   7 50,
                   8 50,
                   9 70,
                   10 80})

(defn next-railway [pos]
  (let [ nr {7 15, 22 25, 36 5}]
    (nr pos)))

(defn next-utility [pos]
  (let [ nu {7 12, 22 28, 36 12}]
    (nu pos)))

(defn go-back-3 [pos]
  "If we are in CC3, G2J directly after backing 3 positions"
  (if (= pos 33)
    10; G2J 
    (mod (- pos 3) 40)))
  
(defn roll-dice []
  (let [d1 (inc (rand-int 6))
        d2 (inc (rand-int 6))]
    (do
      (if (= d1 d2)
        (swap! streak inc)
        (reset! streak 0))
      (+ d1 d2))))

(defn rotate [coll]
  (concat (rest coll) (list (first coll))))

(defn draw-from-community-chest [next]
  (let [card (inc (rand-int 16))
        draw (cc-moves card next)]
    (do
      draw)))

(defn draw-from-chance-chest [next]
  (let [card (inc (rand-int 16))
        draw (chance-moves card next)]
    (do
      (cond
       (= draw 50) (next-railway next)
       (= draw 70) (next-utility next)
       (= draw 80) (go-back-3 next)
       :else next))))

(defn update-stats [stats pos]
  "Keep count of visited positions"
  (let [v (or (@stats pos) 1)]
    (swap! stats assoc pos (inc v))))
  
(defn move [pos streak]
  "Move starting from 'pos'"
  (let [next-square (mod (+ pos (roll-dice)) 40)] ; Next position 
    (do
      (update-stats stats pos) ; Compute statis for 'pos'
      (cond
        (= next-square 30) 10; G2J
        (compare-and-set! streak 3 0) 10 ; G2J if 3 doubles in a row
        (some #(= next-square %) '(7 22 36)) (draw-from-chance-chest next-square)
        (some #(= next-square %) '(2 17 33)) (draw-from-community-chest next-square)
        :else next-square)))) 


;; https://clojuredocs.org/clojure.core/sorted-map-by
(defn display-stats [stats]
  (let [sorted-stats (into (sorted-map-by
                            (fn [key1 key2]
                              (compare [(get @stats key2) key2]
                                       [(get @stats key1) key1]))) @stats)
        modal (for [p (take 40 sorted-stats)]
                (vector (first p) (* 100 (double (/ (last p) N)))))]
    (doseq [item  modal]
      (println item))))

(println ">>>>>>>>>") 
(time (loop [n 0 pos 0]
;        (println ">>> n: " n "Streak: " @streak "pos: " pos)
;        (display-stats stats)
        (when (< n N)
          (let [next-pos (move pos streak)]
            (recur (inc n) next-pos)))))

(display-stats stats)

;; Debug/Asserts
(println)
(println "G2J :" (@stats 30))
(println "Missing Keys: ")
(doseq [item (into (sorted-set)
                   (cs/difference (set (range 40)) (set (keys @stats))))]
  (println item))


            
