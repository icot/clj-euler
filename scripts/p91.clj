(ns p91
  (:require [clojure.pprint :as pp]
            [clojure.math.combinatorics]))

;; Apply pythagoras theorem to test for valid tirangle with OPQ, where
;; O is (0,0) and Q is on the X axis (0, Xn)

(defn valid-triangle? [t]
  (let [[o p q] t
        [xp yp] p
        [xq yq] q]
    (if (or (and (= xp xq) (= yq 0))
            (and (= yp yq) (= xq 0))
            (and (< xq xp)(not (= xq 0)))
            (= p q)
            (= o q)
            (= o p)) false
        (let [op (+ (* xp xp) (* yp yp))
              oq (+ (* xq xq) (* yq yq))
              pq (+ (* (- xp xq) (- xp xq)) (* (- yp yq) (- yp yq)))]
         (or (= op (+ oq pq))
             (= oq (+ op pq))
             (= pq (+ oq op)))))))

;; Generates too many candidates. All non-pythagoras related conditions
;; in valid-triangle? should be accounted for during generation, which
;; would result in a reduced search space

(defn candidates [N]
  (let [ps (cartesian-product (range 1 (inc N)) (range 1 (inc N)))
        qs (cartesian-product (range 0 (inc N)) (range 0 (inc N)))]
    (for [point (cartesian-product ps qs)]
      (conj point '(0 0)))))
  
(def N 50)

(time (def sols (count (for [c (candidates N) :when (valid-triangle? c)] c))))

(println "Size: " N)
(println "Base count: " (* N N 3) "opqs: " sols)
(println "Total: " (+ (* N N 3) sols))

;; Solved. Takes ~11s TODO: Optimize generation of search space



    

    
