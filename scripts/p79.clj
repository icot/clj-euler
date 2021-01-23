(ns p79
  (:require [euler.core :as ec]
            [clojure.set :as cs]))

(def keylog '(319 680 180 690 129 620 762 689 762 318 368 710 720
              710 629 168 160 689 716 731 736 729 316 729 729 710
              769 290 719 680 318 389 162 289 162 718 729 319 790
              680 890 362 319 760 316 729 380 319 728 716))

(def keypresses (for [key keylog] (ec/digits key)))

(def first-keys (into (sorted-set) (map first keypresses)))
(def last-keys (into (sorted-set) (map last keypresses)))

(newline)

(prn (sort (set keylog)))

(prn first-keys)
(prn last-keys)

(prn (cs/difference first-keys last-keys))
(prn (cs/intersection first-keys last-keys))
(prn (cs/difference last-keys first-keys))

; - From inspecting the keylog and the set differences
;  * First two keys must be 7 3
;  * Last two musdt be 9 0
;
; - So we have
;  * 73{1268}90
;
; - For the intersection set, by further inspection:
                                        ;  * Examples of 1 before all the other members
;  * Examples of 6 before 2 and 8
;  * Examples of 2 before 8
;  * Only one example of 8 before 8, which comes from the last's set
;
;- 73162890
