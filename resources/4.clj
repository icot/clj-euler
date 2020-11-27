(last (sort (filter palindromes? (for [x (range 100 999) y (range 100 999)] (* x y)))))
