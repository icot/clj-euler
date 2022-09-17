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
                   8 60,
                   9 70,
                   10 80})

(defn next-railway [pos]
  (let [ nr {7 15, 22 25, 36 5}]
    (nr pos)))

(defn next-utility [pos]
  (let [ nu {7 12, 22 28, 36 12}]
    (nu pos)))

(defn go-back-3 [pos] (mod (+ 40 pos -3) 40))
  
(defn roll-dice []
  (let [d1 (inc (rand-int 4))
        d2 (inc (rand-int 4))]
    (do
      (if (= d1 d2)
        (swap! streak inc)
        (reset! streak 0))
      (+ d1 d2))))

(defn rotate [coll]
  (concat (rest coll) (list (first coll))))

(defn draw-from-community-chest [next]
  (let [card (first @community-chest)
        draw (cc-moves card next)]
    (do
      (swap! community-chest rotate)
      draw)))

(defn draw-from-chance-chest [next]
  (let [card (first @chance-chest)
        draw (chance-moves card next)]
    (do
      (swap! chance-chest rotate)
      (cond
        (or (= draw 50) (= draw 60)) (next-railway next)
        (= draw 70) (next-utility next)
        (= draw 80) (go-back-3 next)
        :else next))))

(defn update-stats [stats pos]
  (if (and (@stats pos) (not (nil? pos)))
    (let [v (@stats pos)]
      (swap! stats assoc pos (inc v)))
    (swap! stats assoc pos 1)))

(defn move [pos streak]
  (let [next-square (mod (+ pos (roll-dice)) 40)]
    (do
      (update-stats stats pos)
      (cond
        (compare-and-set! streak 3 0) 10
        (= next-square 30) 10; G2J
        (some #(= next-square %) '(2 17 33)) (draw-from-community-chest next-square)
        (some #(= next-square %) '(7 22 36)) (draw-from-chance-chest next-square)
        :else next-square))))


;; https://clojuredocs.org/clojure.core/sorted-map-by
(defn display-stats [stats]
  (let [sorted-stats (into (sorted-map-by
                            (fn [key1 key2]
                              (compare [(get @stats key2) key2]
                                       [(get @stats key1) key1]))) @stats)
        modal (for [p (take 40 sorted-stats)]
                (vector (first p) (/ (last p) N)))]
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


            
