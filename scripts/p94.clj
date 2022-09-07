(ns p94
  [:require [euler.primes :as ep]])

;; Massage Pythagoras until getting to a Pell's equation reflecting the relation between
;; the a and b (b => a +- 1)

;; ((3a+-1)/2)^2 -3h2 = 1 --> x^2 - 3y^2 = 1

;; Pell's
;;
;;  https://mathworld.wolfram.com/PellEquation.html
;;  https://en.wikipedia.org/wiki/Pell%27s_equation

;; Base solution for D = 3 -> x=2, y = 1

;;  x_k+1 = x_1*x_k + n * y_1*y_k
;;  y_k_1 = x_1*y_k + y_1*x_k

(def x1 2)
(def y1 1)
(def D 3)

(def LIMIT 1000000000)
;(def LIMIT 100)

(println "Factors" (distinct (ep/factorize-sieve 16)))

(defn natural-square? [x]
  (if (= (count (distinct (ep/factorize-sieve x))) 2)
    true
    false))

(defn test-triangle? [^long a ^long b]
  (natural-square? (- (* a a) (* b b))))

(defn perimeter [^long x]
  (let [a1 (/ (inc (* 2 x)) 3)
        a2 (/ (dec (* 2 x)) 3)
        s (first (filter int? (list a1 a2)))
        p (inc (* 3 s))]
    (if (test-triangle? s (/ (inc s) 2))
      (inc (* 3 s))
      (dec (* 3 s)))))

(time (loop [xk (long x1) yk (long y1) p (long 0) P (long 0)]
        (when (> LIMIT p)
          (println "x_k: " xk "y_k: " yk "P:" P "p: " p)
          (let [xk1 (+ (* x1 xk) (* 3 y1 yk))
                yk1 (+ (* x1 yk) (* y1 xk))
                p (perimeter xk1)]
            (recur xk1 yk1 p (+ P p))))))
           
;; BUG: The logic for the loop is OK, but the test-triangle? function and logic branch in
;;      the perimeter function starts to fail mid-way accumulating off-by-one errors
        




