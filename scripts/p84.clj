(ns p84
  [:require [euler.core :as ec]])


(defn next-railway [pos]
  (let [railways (list 5 15 25 35)]
    (first (filter (fn [p] (> p pos)) railways))))

(defn next-utility [pos] pos
  (let [utilities (list 12 28)]
    (first (filter (fn [p] (> p pos)) utilities))))

(defn go-back-3 [pos] (- pos 3))

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
  
(defn roll-dice [] (+ (inc (rand-int 6)) (inc (rand-int 6))))

;(defn roll-dice [] 1)

(def community-chest (atom (shuffle (range 1 17))))
(def chance-chest (atom (shuffle (range 1 17))))

(defn rotate [coll]
  (concat (rest coll) (list (first coll))))

(defn draw-from-community-chest [pos]
  (let [card (first @community-chest)
        draw (cc-moves card pos)]
    (do
      (swap! community-chest rotate)
      (if (fn? card)
        (apply card pos)
        draw))))

(defn draw-from-chance-chest [pos]
  (let [card (first @chance-chest)
        draw (cc-moves card pos)]
    (do
      (swap! chance-chest rotate)
      (if (fn? card)
        (apply card pos)
        draw))))

(defn move [pos]
  (let [next-square (mod (+ pos (roll-dice)) 40)]
    (do
      (update-stats stats pos)
      (update-stats stats next-square)
      (cond
        (some #(= next-square %) '(2 17 33)) (draw-from-community-chest pos)
        (some #(= next-square %) '(7 22 36)) (draw-from-chance-chest pos)
        :else next-square))))

(defn update-stats [stats pos]
  (if (and (@stats pos) (not (nil? pos)))
    (let [v (@stats pos)]
      (swap! stats assoc pos (inc v)))
    (swap! stats assoc pos 1)))

;; https://clojuredocs.org/clojure.core/sorted-map-by

(def N 1000000)

(def stats (atom {}))

(defn display-stats [stats]
  (let [sorted-stats (into (sorted-map-by (fn [key1 key2]
                                            (compare [(get @stats key2) key2]
                                                     [(get @stats key1) key1]))) @stats)
        modal (for [p (take 3 sorted-stats)] (vector (first p) (* 100 (double (/ (last p) N)))))]
    (println modal (count sorted-stats))))
 
(time (loop [n 0 pos 0]
        (when (< n N)
          (let [next-pos (move pos)]
            (recur (inc n) next-pos)))))

(display-stats stats)

;; BUG: For 6 sides dice getting 001035

            
