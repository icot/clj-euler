(ns p39)

; Done via dirty brute-forcy method,
; For lager Ps should do something like generate only valid triplets
;
; i.e. https://en.wikipedia.org/wiki/Formulas_for_generating_Pythagorean_triples

(defn check-triangle [a b c]
  (= (* c c) (+ (* b b) (* a a))))

(defn validate-c [c]
  (let [th 1e-6]
    (<= (Math/abs (- c (int c))) th)))

(defn sol-by-p [p]
  (filter seq
          (for [a (range 1 p) b (range 1 p)]
            (let [c (- p (+ a b))]
              (if (check-triangle a b c)
                (vector a b c)
                nil)))))

(time
 (loop [p 3 m 0]
  (let [s (count (sol-by-p p))]
    (do
    ;  (prn p m (sol-by-p p))
    (if (>= p 1000) (prn m)
        (if (> s m)
          (do
            (prn p s)
            (recur (inc p) s))
          (recur (inc p) m)
          )
        )
    )
    )
  )
)
