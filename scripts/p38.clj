(ns p38
  (:require [clojure.string :as str]))

(load-file "../src/euler/core.clj")


(defn concatenated-product
  ([n] (concatenated-product n 1 '()))
  ([n c acc]
   (let [nacc (concat acc (euler.core/digits (* n c)))
         l (count nacc)]
     (do
;     (prn l nacc)
     (cond
       (= l 9) nacc
       (< l 9) (recur n (inc  c) nacc)
       :else '()
       )
     )
     )
   )
  )

(time
 (let [ns (for [n (range 1 10000)]
            (let [cp (concatenated-product n)
                  p? (= (set cp) (set (range 1 10)))]
              (if p? cp nil)
              ))
       fns (doall (filter (complement nil?) ns))
       ]
   (do
     (prn (last (sort (map #(str/join #"" %) fns))))
     )
   )
 )
