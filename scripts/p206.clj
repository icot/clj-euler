;; From square properties:
;; The last two ciphers are 00
;; The pre-root (root of number after removing 00s is odd)

(defn test-number [n]
  (= (take-nth 2 (str n))
     '(\1 \2 \3 \4 \5 \6 \7 \8 \9)))
  
;; (math/exact-integer-sqrt 19293949596979899) -> [138902662 86293655]
;; Start from 138902663

(time (loop [r 138902663]
        (let [T (*' r r)]
          (if (test-number T)
            (println (format "Target: %s" (* 10 r)))
            (recur (- r 2))))))

;; 1389019170
