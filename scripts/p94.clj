(ns p94)

;; Massage Pythagoras until getting to a Pell's equation reflecting the relation between
;; the a and b (b => a +- 1)

;; ((3a+-1)/2)^2 -3h2 = 1 --> x^2 - 3y^2 = 1

;; Pell's
;;
;;  https://mathworld.wolfram.com/PellEquation.html
;;  https://en.wikipedia.org/wiki/Pell%27s_equation

;; Base solution for D = 3 -> x=2, y = 1

;;  x_k+1 = x_1*x_k + n * y_1*y_k
;;  y_k_1 = x_1*y_k + y_1*x_k

(def x1 2)
(def y1 1)
(def D 3)

(def LIMIT 1000000000)

(defn pell [x y] (- (* x x) (* 3 y y)))

(defn perimeters [x]
  (let [a1 (/ (inc (* 2 x)) 3)
        a2 (/ (dec (* 2 x)) 3)]
    (filter int? (list (inc (* 3 a1)) (dec (dec (* 3 a2)))))))

(time (loop [xk x1 yk y1 P 6 p '() mp 0]
        (when (> LIMIT mp)
          (println "x_k: " xk "y_k: " yk "P:" P "p: " p "mp: " mp)
          (let [xk1 (+ (* x1 xk) (* 3 y1 yk))
                yk1 (+ (* x1 yk) (* y1 xk))
                p (perimeters xk1)
                sp (apply + p)
                mp (apply min p)]
            (recur xk1 yk1 (+ P sp) p mp)))))
           
;; BUG: Code returns:518408339, Solution is off by 7
;;      Degenerate triangles (1,1,0) and (1,1,2) add 6 to P if counted, still, off by 1

