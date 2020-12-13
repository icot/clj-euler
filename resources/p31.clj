(ns p31)

;;(defn first-denomination [kinds-of-coins]
;;  (let [face-values '(1 2 5 10 20 50 100 200)]
;;    (nth face-values (dec kinds-of-coins))))
;;
;;(defn cc [amount kinds-of-coins]
;;  (cond (= amount 0) 1
;;        (or (< amount 0) (= kinds-of-coins 0)) 0
;;        :else (+ (cc amount (dec kinds-of-coins))
;;                 (cc (- amount
;;                        (first-denomination kinds-of-coins))
;;                     kinds-of-coins))))

;; Naive implementation translated from racket: Can't use TCO, performance degrades quick
;; f(400) -> 15s
;; f(500) -> 58s
;; f_m(500) -> 75s

;; Switching face-values from list to vector:
;; f_m(500) -> 10.1s
;; f(500) -> 10.1s

;; TODO, possible improvements
;;   - Eliminate fist-denomination function
;;   - Change call orders to benefit from memoization?

(defn count-change [amount face-values]
  (let [types-of-coins (count face-values)
        first-denomination (fn [kinds-of-coins face-values] (nth face-values (dec kinds-of-coins)))
        cc (fn cc [amount kinds-of-coins face-values]
            (cond (= amount 0) 1
                  (or (< amount 0) (= kinds-of-coins 0)) 0
                  :else (+ (cc amount (dec kinds-of-coins) face-values)
                           (cc (- amount
                                  (first-denomination kinds-of-coins face-values))
                               kinds-of-coins
                               face-values))))
        ]
    (cc amount types-of-coins face-values))
  )


;(time (println (count-change 500 '[1 2 5 10 20 50 100 200])))

;; Eliminating first-denominations function -> minimal gains
;; Picked up _JAVA_OPTIONS: -Dawt.useSystemAAFontSettings=gasp
;; 6295434
;; "Elapsed time: 10185.075043 msecs"
;; 6295434
;; "Elapsed time: 9853.826697 msecs"

(defn count-change1 [amount face-values]
  (let [types-of-coins (count face-values)
        cc (fn cc [amount kinds-of-coins face-values]
            (cond (= amount 0) 1
                  (or (< amount 0) (= kinds-of-coins 0)) 0
                  :else (+ (cc amount (dec kinds-of-coins) face-values)
                           (cc (- amount
                                  (nth face-values (dec kinds-of-coins)))
                               kinds-of-coins
                               face-values))))
        ]
    (cc amount types-of-coins face-values))
  )

;(time (println (count-change1 500 '[1 2 5 10 20 50 100 200])))

;; Elimiate helper function and optimize accesses to face-values
;; by using peek and pruning between recursion calls

(defn count-change2 [amount face-values]
  (cond (= amount 0) 1
        (or (< amount 0) (= (count face-values) 0)) 0
        :else (+ (count-change2 amount (pop face-values))
                 (count-change2 (- amount (peek face-values)) face-values))))

;; (time (println ((memoize count-change2) 500 '[1 2 5 10 20 50 100 200])))

;; https://gist.github.com/gdevanla/8677505
(let
    [coins (map read-string (clojure.string/split "1,2,5,10,20,50,100,200" #","))
     amt 5
     change3 (fn [rec amt coins]
               (cond
                (= amt 0) 1
                (empty? coins) 0
                :true (let [c (peek coins)
                            ways (for [x (range 0 (inc (quot amt c)))]
                                   (rec rec (- amt (* x c) ) (pop coins)))]
                        (do
                          (newline)
                          (println "amt: " amt "c" c "coins: " coins "ways: " ways)
                          (reduce + ways)))
                )
               )
               ]
;  (time (println (change3 (memoize change3) amt (vec coins)))))
  (time (println (change3 change3 amt (vec coins)))))
