(ns p11
  (:require [clojure.string :as str]))

(def filename "resources/p011_matrix.txt")

(def N 20)

(def data (map #(str/split % #" ") (str/split (slurp filename) #"\n")))

(def int-data (into [] (doall (map #(map (fn [s] (Integer/parseInt s)) %) (doall data)))))
(def flat-data (flatten int-data))

(def matrix (partition 20 flat-data))


(def mh (into (sorted-map) (zipmap (range (count flat-data)) flat-data)))

(defn process [l acc]
  (do
;    (println (take 4 l) acc)
    (let
        [[a b c d & ls] l
         p (* a b c d)]
      (if (empty? ls) acc
        (if (> p acc)
          (recur (rest l) p)
          (recur (rest l) acc)
          )
        )
    )
    )
  )

(defn get-col [h i]
  (let [idxs (range i (count h) N)]
    (do
;      (println is)
      (map #(get h %) idxs)
      )
    )
  )

(defn get-row [h i]
  (let [start-pos (* N i)
        stop-pos (+ start-pos N)
        idxs (range start-pos stop-pos)]
    (do
;      (println is)
      (map #(get h %) idxs)
      )
    )
  )

(defn diag-right [m i j N]
  (cond
    (and (zero? i) (zero? j)) (let [idxs (map + (range 0 (* N N) N) (range N))]
              (map nth (repeat m) idxs))
    (= j 0) (let [idxs (map + (range (* N i) (* N N) N) (range N))]
              (map nth (repeat m) idxs))
    (= i 0) (let [idxs (filter #(< % (- (* N N) (* N j))) (map + (range j (* N N) N) (range N)))]
              (map nth (repeat m) idxs))
    )
  )

; BUG:  off-by-one last branch missing last diagonal (1 element) and second branch missing first element
(defn diag-left [m i j N]
  (cond
    (and (= (dec N) i) (= (dec N) j)) (let [idxs (map - (range (dec N) (* N N) N) (range N))]
              (map nth (repeat m) idxs))
    (= j (dec N)) (let [idxs (map (comp dec -) (range (- N i) (- (* N N) (* N i)) N) (range N))]
              (map nth (repeat m) idxs))
              ;idxs)
    (= i (dec N)) (let [idxs (map - (range (dec (* N (inc j))) (* N N) N) (range N))]
              (map nth (repeat m) idxs))
    )
  )

(def items (filter #(> (count %) 4)
                   (concat
                    (for [k (range 20)] (diag-right flat-data k 0 N))
                    (for [k (range 20)] (diag-right flat-data 0 k N))
                    (partition N flat-data)
                    (for [k (range 20)] (get-col mh k))
                    )
            )
  )

; BUG: Need revision. Result incorrect
(println (count items))
(println (reduce max (map (fn [item] (process item 0)) items)))
