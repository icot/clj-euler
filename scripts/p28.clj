(ns p28)

(def order 1001)

(time
 (println
    (loop [r 1 total 1 cont 1]
    (if (< r order)
        (let [buf (range (int (Math/pow r 2)) (int (inc (Math/pow (+ r 2) 2))) 2)
            pos (range cont (count buf) cont)
            inc-total (reduce + (for [p pos] (nth buf p)))
              ]
          (do
;            (println "buf: " buf "selection: " (for [p pos] (nth buf p)))
            (recur (+ r 2) (+ total inc-total) (inc cont))
            )
        )
        total
        )
    )
 )
)
