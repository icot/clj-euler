(ns p33
  (:require [clojure.set :as cs]))

(load-file "../src/euler/core.clj")

(defn simplify [n d]
  (let [n11 (quot n 10)
        n12 (mod n 10)
        d11 (quot d 10)
        d12 (mod d 10)]
    (do
;      (prn n11 n12 d11 d12)
      (cond
        (= n12 0) 0
        (and (= n11 d11) (> d12 1)) (/ n12 d12)
        (and (= n11 d12) (> d11 1)) (/ n12 d11)
        (and (= n12 d11) (> d12 1)) (/ n11 d12)
        (and (= n12 d12) (> d11 1)) (/ n11 d11)
        :else 0))))

(def fs (for [a (range 11 100) b (range (inc a) 100)]
          (let [q (simplify a b)
                valid? (= (/ a b) q)]
            (do
;              (prn a b q valid?)
              (if valid? (vector a b) nil)
              )
            )
          )
  )

(prn (reduce * (map #(/ (first %) (last %)) (filter seq fs))))
