(ns p27)

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(def N 1000)

(def primes (euler.primes/sieve N))

(def bes (concat
          (reverse (map - primes))
          primes))


(defn generator
  ([a b] (generator a b 0))
  ([a b n]
   (letfn [(f [n] (+ (* n n) (* a n) b))]
     (do
;       (prn n (f n))
           (if (euler.primes/prime? (f n))
             (recur a b (inc n))
             n
             )
           )
     )
   )
  )

;(prn bes)

(def items (partition 3
                     (flatten
                      (for [a (range (- N) (inc N))]
                        (for [b bes] (list a b (generator a b)))))))

(time
 (prn
  (loop [xs items m '(0 0 0)]
    (do
;      (prn (first xs) m)
      (if (empty? (rest xs))
        m
        (let [[a b l] (first xs)]
          (if (> l (last m))
            (recur (rest xs) (list a b l))
            (recur (rest xs) m)
            )
          )
        )
      )
    )
  )
 )

(prn (reduce * '(-61 971)))
