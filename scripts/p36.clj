(ns p36)

(load-file "../src/euler/core.clj")

(defn binary-digits
  ([n] (binary-digits n '[]))
  ([n acc]
   (if (> n 0)
     (recur (int (/ n 2)) (cons (mod n 2) acc))
     acc
     )
   )
  )

(defn binary-palindrome? [n] (= (binary-digits n) (reverse (binary-digits n))))

(def candidates
  (for [n (range 1 1000000) :when (and (euler.core/palindrome? n) (binary-palindrome? n))] n))

(time (prn (reduce + candidates)))


