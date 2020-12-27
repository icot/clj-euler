(ns p44)

(defn pent [n] (/ (* (dec (* n 3)) n) 2))

(defn pentagonal? [n]
  (let [p (/ (+ (Math/sqrt (+ (* n 24) 1)) 1) 6)]
    (zero? (- p (int p)))))

(def ps (map pent (range 1 5000)))
(def pns (into (sorted-map) (zipmap ps (repeat true))))

(loop [n 1000]
  (let [s (pent n)
        sol (for [p ps :when (and (get pns (- s p) false)
                                  (get pns (- (* 2 s) p) false))] (- (* 2 s) p))]
    (if (seq sol)
      (prn n s (first sol) (- (* 2 s) (first sol)))
      (recur (inc n))
    )
    )
  )
