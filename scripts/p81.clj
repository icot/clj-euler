(ns p81
  (:require [clojure.string :as str]))

(defn loadfile [filename] nil)

(defrecord NodeType1 [left right])
(defrecord NodeTYpe2 [left right down])
(defrecord NodeType3 [left right down up])

;; Load Node List into Graph representation
;; Grahp -> { :n1 (NodeTypeX [n2 ...] }} 


(defn a* [graph start end] nil)

(defn dikjstra [graph start] nil)

