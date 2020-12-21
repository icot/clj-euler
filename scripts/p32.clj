(ns p32)

(load-file "../src/euler/core.clj")

(defn test-product? [a b]
  (let [s (set (range 1 10))
        cs (concat (euler.core/digits a) (euler.core/digits b) (euler.core/digits (* a b)))]
    (and (= s (set cs)) (= (count cs) 9))
    )
  )

; Can be made faster by avoiding testing when number lengths don't support for a valid
; solution
;
(time
    (let [products
        (set (flatten
            (for [a (range 1 2000)]
            (for [b (range 1 2000) :when (test-product? a b)] (* a b)))))]
    (do
        (prn products)
        (prn (reduce + (seq products)))
        )
    )
    )
