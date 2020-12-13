(ns euler.14)

(load-file "../src/euler/core.clj")
(load-file "../src/euler/primes.clj")

(defn collatz-sequence
  ([n]
   (collatz-sequence n '()))
  ([n acc]
   (if (even? n)
     (recur (/ n 2) (cons n acc))
     (if (= n 1)
       (reverse (cons n acc))
       (recur (+ (* n 3) 1) (cons n acc))))))

(defn find-max
  [pairs max-pair]
  (let [[head & tail] pairs]
    (do
  ;    (println pairs max-pair)
      (if (nil? head)
        max-pair
        (if (> (last head) (last max-pair))
          (recur tail head)
          (recur tail max-pair)
          )
        )
      )
    )
  )

(let [
      numbers (range 1 1000000)
      ls (map #(count (collatz-sequence %)) numbers)
      pairs (map vector numbers ls)
      ]
  (time (println (find-max pairs (first pairs))))
 )
