(ns p48)

;; Using  BigInt types
(time (println  (mod (reduce +' (for [i (range 1 1001)] (reduce *' (repeat i i)))) 10000000000)))


;; Using module arithmetic
;;
;;


(time
 ( println
  (mod (reduce
        (fn [acc i] (mod (+ acc i) 10000000000))
        (for [i (range 1 1001)]
          (reduce (fn [acc i] (mod (* acc i) 10000000000)) (repeat i i))))
       10000000000)
  )
 )
