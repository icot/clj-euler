(ns p84
  (:require [clojure.set :as cs]))

(def N 1000000)

(def stats (atom {}))
(def community-chest (atom (shuffle (range 1 17))))
(def chance-chest (atom (shuffle (range 1 17))))
(def streak (atom 0))
(def cc-moves {1 0, 2 10})
(def chance-moves {1 0,
                   2 10,
                   3 11,
                   4 24
                   5 39,
                   6 5,
                   7 #'next-railway
                   8 #'next-railway
                   9 #'next-utility
                   10 #'go-back-3})

(defn next-railway [pos]
  (cond
    (and (>= pos 0) (< pos 5)) 5
    (and (>= pos 5) (< pos 15)) 15
    (and (>= pos 15) (< pos 25)) 25
    (and (>= pos 25) (< pos 35)) 35
    :else 5))
    
(defn next-utility [pos] pos
  (if (and (>= pos 12) (<= pos 28))
    28
    12))

(defn go-back-3 [pos] (mod (+ 40 pos -3) 40))
  
(defn roll-dice []
  (let [d1 (inc (rand-int 6))
        d2 (inc (rand-int 6))]
    (do
      (if (= d1 d2)
        (swap! streak inc)
        (swap! streak (fn [n] (* n 0))))
      (+ d1 d2))))


(defn rotate [coll]
  (concat (rest coll) (list (first coll))))

(defn draw-from-community-chest [pos next]
  (let [card (first @community-chest)
        draw (cc-moves card next)]
    (do
      (swap! community-chest rotate)
      (if (fn? card)
        (apply card pos)
        draw))))

(defn draw-from-chance-chest [pos next]
  (let [card (first @chance-chest)
        draw (cc-moves card next)]
    (do
      (swap! chance-chest rotate)
      (if (fn? card)
        (apply card pos)
        draw))))

(defn move [pos streak]
  (let [next-square (mod (+ pos (roll-dice)) 40)]
    (do
      (update-stats stats pos)
      (cond
        (= @streak 3) (do
                        (swap! streak (fn [n] (* n 0))) ; Reset streak
                        10) ; And go to Jail
        (= next-square 30) 10; G2J
        (some #(= next-square %) '(2 17 33)) (draw-from-community-chest pos next-square)
        (some #(= next-square %) '(7 22 36)) (draw-from-chance-chest pos next-square)
        :else next-square))))

(defn update-stats [stats pos]
  (if (and (@stats pos) (not (nil? pos)))
    (let [v (@stats pos)]
      (swap! stats assoc pos (inc v)))
    (swap! stats assoc pos 1)))

;; https://clojuredocs.org/clojure.core/sorted-map-by
(defn display-stats [stats]
  (let [sorted-stats (into (sorted-map-by (fn [key1 key2]
                                            (compare [(get @stats key2) key2]
                                                     [(get @stats key1) key1]))) @stats)
        modal (for [p (take 40 sorted-stats)] (vector (first p) (* 100 (double (/ (last p) N)))))]
    (println modal (count sorted-stats))))

(println ">>>>>>>>>") 
(time (loop [n 0 pos 0]
;        (display-stats stats)
;        (println @streak)
        (when (< n N)
          (let [next-pos (move pos streak)]
            (recur (inc n) next-pos)))))

(display-stats stats)

;; Debug/Asserts
(println)
(println "G2J :" (@stats 30))
(println "Missing Keys: ")
(println (into (sorted-set) (cs/difference (set (range 40)) (set (keys @stats)))))


            
