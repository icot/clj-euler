(ns p92)

(def N 10000000)

(defn fchain [n]
  (reduce + (map #(* % %) (map (fn [d] (Character/digit (identity d) 10)) (str n)))))

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

(defn digits2
  ([^Integer n] (digits n '[]))
  ([^Integer n ^java.util.List acc]
   (let [rem (mod n 10)
         next (quot (- n rem) 10)]
     (if (< n 10)
       (cons n acc)
       (recur next (cons rem acc))
       )
     )
   )
  )

(defn chain [n]
  (cond
    (= n 0) nil
    (= n 1) false
    (= n 89) true
    :else (recur ((memoize fchain) n))
    )
  )

(defn chain2 [n]
  (let [c (reduce + (map #(* % %) (digits n)))]
    (cond
        (= n 0) nil
        (= c 1) false
        (= c 89) true
        :else (recur c)
        )
      )
  )

(defn chain3 [^Integer n]
  (let [c (reduce + (map #(* % %) (digits2 n)))]
    (cond
        (= n 0) false
        (= c 1) false
        (= c 89) true
        :else (recur c)
        )
      )
  )

(defn chain4 [^Integer n]
  (let [c (reduce + (map #(* % %) (digits2 n)))]
    (cond
        (= n 0) 0
        (= c 1) 0
        (= c 89) 1
        :else (recur c)
        )
      )
  )

; (time (println (count (filter identity (map (memoize chain) (range 1 N))))))
;(time (map (memoize chain2) (range 1 N)))
;(time (println (count (filter identity (map (memoize chain2) (range 1 N))))))
;(time (map chain2 (range 1 N)))
;(time (println (count (filter identity (map chain2 (range 1 N))))))
;(time (map chain3 (range 1 N)))
;(time (println (count (filter identity (map chain3 (range 1 N))))))

;(time (println (reduce + (for [n (range 1 N) :when (chain3 n)] 1))))

(time (loop [n 1 acc 0 v false]
        (if (<= n 100)
          (if v
            (recur (inc n) (inc acc) (chain3 n))
            (recur (inc n) acc (chain3 n))
            )
          (println acc)
          )
        )
      )

(require '[clojure.core.reducers :as r])
;(time (println (r/fold + (for [n (range 1 N) :when (chain3 n)] 1))))

(time (loop [i 1 acc 0]
        (if (<= i N)
          (recur (inc i) (+ acc (chain4 i)))
          (println acc)
          )
        )
      )
