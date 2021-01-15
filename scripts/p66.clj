(ns p66)

; https://en.wikipedia.org/wiki/Periodic_continued_fraction#cite_note-7
(defn sqrt->cf [n]
  "Compute shortened representation of sqrt(n) as continued fraction"
  (letfn [(exact-floor [n] (int (Math/floor n)))
          (cfraq [S m d a acc]
            (let [a0 (first acc)
                  next-m (- (* a d) m)
                  next-d (/ (- S (* next-m next-m)) d)
                  next-a (exact-floor (/ (+ (Math/sqrt S) next-m) next-d))]
              (cond
                (= (* a0 2) next-a) (conj acc next-a)
                :else (cfraq S next-m next-d next-a (conj acc next-a)))))]
    (let [sqrt-n (Math/sqrt n)
          a0 (exact-floor sqrt-n)]
      (if (zero? (- (int sqrt-n) sqrt-n)) ; Need to refactor this check for Clojure
        (list sqrt-n)
        (cfraq n 0 1 a0 (vector a0))))))

(defn expanded-sqrt->cf [n]
  "Generate infinity lazy continus fraction representation of sqrt(n)"
  (let [[a0 & sqrt-cycle] (sqrt->cf n)]
    (cons a0 (cycle sqrt-cycle))))

(defn scf-convergents [ds k]
  "Compute convergents for the degenerate type where Ni = 1"
  (reduce #(+ %2 (/ %1)) (reverse (take k ds))))


(defn pells-fundamental-solution [n]
  "Find x^2 -Dy^2 = 1 fundamental solution"
  (let [square (fn [x] (* x x))
        cf (expanded-sqrt->cf n)
        convergents (map #(scf-convergents cf %) (iterate inc 2))]
    (vector n (first (filter #(if (integer? %) false (= (- (square (numerator %)) (* n (square (denominator %)))) 1)) convergents)))))


(newline)

(def squares (set (map #(* % %) (range 2 34))))

(loop [n 2 maxd 1 maxx 0]
  (if (> n 1000)
    (prn maxd maxx)
    (if (squares n)
      (recur (inc n) maxd maxx)
      (let [xi (numerator (last (pells-fundamental-solution n)))]
        (if (> xi maxx)
          (recur (inc n) n xi)
          (recur (inc n) maxd maxx))))))
