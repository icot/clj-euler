(ns euler.continued-fractions
  (:require [euler.core :as ec]))

(defn unitary-numerators [ds k]
  "Compute approximation for the degenerate type where Ni = 1"
  (reduce #(+ %2 (/ %1)) (reverse (take k ds))))

(defn cont-frac [ns ds k]
  "Compute continued fraction for the N and D vectors provided"
  (let [xs (flatten (map vector (take k ns) (take k ds)))]
    (reduce #(+ %2 (/ %1)) (reverse xs))))

(defn r->cfrac [r limit]
  (letfn [(cfraq [r acc counter]
            (let [a0 (int r)
                  a1 (- r a0)]
              (cond
                (zero? counter) acc
                (zero? a1 ) (conj a0 acc)
                :else (recur (/ a1) (conj acc a0) (dec counter)))))]
    (cfraq r '[] limit)))



; https://github.com/atlytle/continued-fraction/blob/master/continued-fraction.clj

(defn continued-fraction
  "Continued fraction representation of x."
  ([a b]
    (if (not (zero? b))
        (cons (int (quot a b)) (lazy-seq (continued-fraction b (mod a b))))))
  ([x]
   (continued-fraction (rationalize x) 1)))

(defn rational-approx
  "nth order rational approximation to x."
  ([cf-rep]
    (reduce #(+ %2 (/ 1 (+ %1))) (reverse cf-rep)))
  ([n x]
    (rational-approx (take (inc n) (continued-fraction x)))))


(defn take-until
  "Variation of (take-while (complement pred) coll),
  retains the first element to fail (complement pred)."
  [pred coll]
  (let [[before after] (split-with (complement pred) coll)]
    (conj (vec before) (first after))))


(defn successive-approx
  "Successive rational approximations to x."
  [x]
  (take-until (partial == (rationalize x))
              (map #(rational-approx % x) (range))))

(defn frac->dec
  "Convert a rational number to decimal. Returns a seq of the digits."
  ([frac]
    (map int (frac->dec (numerator frac) (denominator frac))))
  ([n d]
    (if (zero? (mod n d))
        (cons (quot n d) [])
        (cons (quot n d) (lazy-seq (frac->dec (* 10 (mod n d)) d))))))


(prn (r->cfrac (Math/sqrt 23) 50))

(prn (take 50 (continued-fraction (Math/sqrt 23))))

(prn (numerator (last (take 100 (successive-approx (Math/sqrt 2))))))

(prn (as-> (successive-approx (Math/exp 1)) x
       (take 100 x)
       (last x)
       (numerator x)
       (ec/digits x)
       (reduce + x)))
