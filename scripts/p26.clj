(ns p26)

; Check for cycles in the stream of modules
(defn lazy-unit-fraction [d]
  (iterate (fn [n] (mod (* 10 n) d)) 1))


; TODO: Implement find-sequence

(defn cycle-length[n L]
  (letfn [(find-cycle [l acc cl]
            (do
;              (prn acc cl (first l))
            (let [f (first l)
                  r (rest l)]
              (cond
                (zero? f) cl
                (= f (first acc)) cl
                :else (recur r (conj acc f) (inc cl)))
              )
            )
            )]
    (find-cycle (take L (drop 20 (lazy-unit-fraction n))) '[] 0)
    )
  )

(time
 (loop [n 1 m '(0 0)]
   (let [l (cycle-length n 2000)]
     (if (= n 999)
       m
       (if (> l (last m))
         (do
           (prn n l)
           (recur (inc n) (list l l))
           )
         (recur (inc n) m)
         )
       )
     )
   )
 )
