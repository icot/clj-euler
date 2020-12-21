(ns p97)

(def exp 7830457)
(def k 28433)
(def modulo 10000000000)

; Compute last 10 digits of k*2^exp +1

(defn pow-mod [base exp modulo]
  "Computes exponentional accumulating result modulo [modulo]"
  (loop [e 0 acc 1]
    (if (< e exp)
      (recur (inc e) (mod (* base acc) modulo))
      acc
      )
    )
  )

;(prn (pow-mod 2 4 1000))
;(prn (pow-mod 2 8 1000))
;(prn (pow-mod 2 16 1000))
;(prn (pow-mod 2 32 1000))

(time
 (prn
  (mod
   (inc
    (mod
     (*' k (pow-mod 2 exp modulo))
     modulo))
   modulo)))
