(ns p55
  (:require [euler.core :as ec]))

(defn is-palindrome? [n] (= (digits n) (reverse (digits n))))

(defn ->digits [s]
  (let [ps (reverse (map #(bigdec (Math/pow 10 %)) (range (count s))))]
    (reduce +' (map *' s ps))
  ))

(defn digits
  ([n] (digits n '[]))
  ([n acc]
   (let [rem (mod n 10)
         next (quot (- n rem) 10)]
     (if (< n 10)
       (cons n acc)
       (recur next (cons rem acc))
       )
     )
   )
  )

(defn lychrel?
  ([n] (lychrel? n 50))
  ([n c]
     (let [
           candidate (+' n (->digits (reverse (digits n))))]
       (if (zero? c)
         true
         (if (is-palindrome? candidate)
           false
           (recur candidate (dec c)))))))

;(time (prn (count (filter lychrel? (range 1 10000)))))
; "Elapsed time: 7747.291978 msecs"

(time
 (prn
  (loop [n 10000 acc 0]
    (if (> n 0)
      (if (lychrel? n)
        (recur (dec n) (inc acc))
        (recur (dec n) acc))))))

;"Elapsed time: 7745.00459 msecs"
;
; TODO Much slower than python implementation
