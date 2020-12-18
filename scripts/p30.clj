(ns p30)

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

(defn valid? [n]
  (= n
     (reduce (comp int +) (for [d (digits n)] (Math/pow d 5)))))

(load-file "../src/euler/core.clj")

(defn valid-p34? [n]
  (= n
     (reduce + (for [d (digits n)] (euler.core/factorial d)))))

(prn "Problem p30")
(time
 (prn
  (reduce + (filter valid? (range 1 1000000)))
  ))

(prn "Problem p34")
(time
 (prn
  (reduce + (filter valid-p34? (range 3 1000000)))
  )
 )
