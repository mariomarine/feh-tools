(println "3 Star Chance (%): ")
(def threes (read-line))
(def threes (if (= threes "") "36" threes))
(def threes (Integer. threes))
(println "4 Star Chance (%): ")
(def fours (read-line))
(def fours (if (= fours "") "58" fours))
(def fours (Integer. fours))
(println "5 Star Chance (%): ")
(def fives (read-line))
(def fives (if (= fives "") "3" fives))
(def fives (Integer. fives))
(println "5 Star-Focus Chance (%): ")
(def focus (read-line))
(def focus (if (= focus "") "3" focus))
(def focus (Integer. focus))

(println "Focus colors (red red blue grey): ")
(def focus_colors (read-line))
(def orb1 (rand-int 100))
(def orb2 (rand-int 100))
(def orb3 (rand-int 100))
(def orb4 (rand-int 100))
(def orb5 (rand-int 100))

(defn getOrbStar [orb] (if (< orb threes) "three"
                       (if (< (- orb threes) fours) "four"
                       (if (< (- orb threes fours) fives) "five"
                       "focus"))))
(def orb1star (getOrbStar orb1))
(def orb2star (getOrbStar orb2))
(def orb3star (getOrbStar orb3))
(def orb4star (getOrbStar orb4))
(def orb5star (getOrbStar orb5))

(def red_5_numbers 49)
(def blue_5_numbers 35)
(def green_5_numbers 31)
(def grey_5_numbers 27)
(def five_colors (+ red_5_numbers blue_5_numbers green_5_numbers grey_5_numbers))
(def red_4_numbers 33)
(def blue_4_numbers 32)
(def green_4_numbers 23)
(def grey_4_numbers 34)
(def four_colors (+ red_4_numbers blue_4_numbers green_4_numbers grey_4_numbers))
(def red_3_numbers 33)
(def blue_3_numbers 30)
(def green_3_numbers 20)
(def grey_3_numbers 30)
(def three_colors (+ red_3_numbers blue_3_numbers green_3_numbers grey_3_numbers))


(defn getColor [orbStar]
  (def marker (rand-int (if (= orbStar "three") three_colors
                        (if (= orbStar "four") four_colors
                        (if (= orbStar "five") five_colors
                        (if (= orbStar "focus") 4 "bad_request")))
                        )
              )
  )
  (if (= orbStar "three") (if (< marker red_3_numbers) "Red 3 Star"
                          (if (< (- marker red_3_numbers) blue_3_numbers) "Blue 3 Star"
                          (if (< (- marker red_3_numbers blue_3_numbers) green_3_numbers) "Green 3 Star"
                          (if (< (- marker red_3_numbers blue_3_numbers green_3_numbers) grey_3_numbers) "Green 3 Star")))
                          )
  (if (= orbStar "four") (if (< marker red_4_numbers) "Red 4 Star"
                          (if (< (- marker red_4_numbers) blue_4_numbers) "Blue 4 Star"
                          (if (< (- marker red_4_numbers blue_4_numbers) green_4_numbers) "Green 4 Star"
                          (if (< (- marker red_4_numbers blue_4_numbers green_4_numbers) grey_4_numbers) "Green 4 Star")))
                          )
  (if (= orbStar "five") (if (< marker red_5_numbers) "Red 5 Star"
                          (if (< (- marker red_5_numbers) blue_5_numbers) "Blue 5 Star"
                          (if (< (- marker red_5_numbers blue_5_numbers) green_5_numbers) "Green 5 Star"
                          (if (< (- marker red_5_numbers blue_5_numbers green_5_numbers) grey_5_numbers) "Green 5 Star")))
                          )
  (if (= orbStar "focus") "Focus" "Bad Request")))
  )
)
(println "Orb 1: " (getColor orb1star))
(println "Orb 2: " (getColor orb2star))
(println "Orb 3: " (getColor orb3star))
(println "Orb 4: " (getColor orb4star))
(println "Orb 5: " (getColor orb5star))

