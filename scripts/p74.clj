(ns p74
  (:require [euler.core :as ec]))


(defn factorial "Returns factorial of argument n"
  [n]
  (reduce * (range 1 (inc n)))
  )


(defn factorial-long "Returns factorial of argument n"
  [n]
  (reduce *' (range 1 (inc n)))
  )

(def factorial-table {
                      0 1
                      1 1
                      2 2
                      3 6
                      4 24
                      5 120
                      6 720
                      7 5040
                      8 40320
                      9 362880
                     })

(defn factorial-digit-chain [n]
  (let [f (fn [n acc]
            (let [s (reduce + (map factorial-table (ec/digits n)))
                  seen? (get acc s false)]
              (if seen?
                (count acc)
                (recur s (conj acc (vector s true))))))]
    (f n (hash-map n true))))

(newline)

(time
 (loop [n 1 counter 0]
  (if (> n 1000000)
    (prn (format "Total: %d" counter))
    (if (= 60 (factorial-digit-chain n))
      (recur (inc n) (inc counter))
      (recur (inc n) counter)))))

; NOTE

; Return Hints
; Total: 402"
; "Elapsed time: 41735.80012 msecs"

; Using hash-map acc intead of set> 39s
; "Total: 402"
; "Elapsed time: 39598.352878 msecs"

; Some notes from micro-optimizations
; - Using factorial without bigint support doesn't affect
; - Using (memoize factorial) actually raises running time to 81s,
;   I'm assuming I'm not placing the call in the right scope
; - Using factorial-table -> 35s
; - Using acc as vector, passing type hint and check with .indexOf -> Unusable...
