(require '[clojure.string :as str])
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
(def focus_color_wheel (read-line))
(def focus_color_wheel (if (= focus_color_wheel "") "red red blue grey" focus_color_wheel))
(def focus_color_wheel (str/split focus_color_wheel #" "))

(def orb1 (rand-int 100))
(def orb2 (rand-int 100))
(def orb3 (rand-int 100))
(def orb4 (rand-int 100))
(def orb5 (rand-int 100))

;; Taken from https://stackoverflow.com/questions/14464011/idiomatic-clojure-for-picking-between-random-weighted-choices
;; Credit to Rich Hickey (solution) and dripnet (stackoverflow answer)
(defn wrand 
  "given a vector of slice sizes, returns the index of a slice given a
  random spin of a roulette wheel with compartments proportional to
  slices."
  [slices]
  (let [total (reduce + slices)
        r (rand total)]
    (loop [i 0 sum 0]
      (if (< r (+ (slices i) sum))
        i
        (recur (inc i) (+ (slices i) sum))))))

(defn getOrbStar [] 
  (def pick (wrand [threes fours fives focus]))
  (if (= pick 0) "three"
  (if (= pick 1) "four"
  (if (= pick 2) "five"
  "focus"))
  )
)

(def orb1star (getOrbStar))
(def orb2star (getOrbStar))
(def orb3star (getOrbStar))
(def orb4star (getOrbStar))
(def orb5star (getOrbStar))

(def red_focus_numbers 0)
(def blue_focus_numbers 0)
(def green_focus_numbers 0)
(def grey_focus_numbers 0)
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

(doseq [col focus_color_wheel] (if (= col "red") (def red_focus_numbers (inc red_focus_numbers))
                          (if (= col "blue") (def blue_focus_numbers (inc blue_focus_numbers))
                          (if (= col "green") (def green_focus_numbers (inc green_focus_numbers))
                          (if (= col "grey") (def grey_focus_numbers (inc grey_focus_numbers)) "Bad data")))
                          )
)
(def focus_colors (+ red_focus_numbers blue_focus_numbers green_focus_numbers grey_focus_numbers))

(defn getColor [orbStar]
  (def group (if (= orbStar "three") (wrand [red_3_numbers blue_3_numbers green_3_numbers grey_3_numbers])
             (if (= orbStar "four") (wrand [red_4_numbers blue_4_numbers green_4_numbers grey_4_numbers])
             (if (= orbStar "five") (wrand [red_5_numbers blue_5_numbers green_5_numbers grey_5_numbers])
             (if (= orbStar "focus") (wrand [red_focus_numbers blue_focus_numbers green_focus_numbers grey_focus_numbers]))))
             )
  )
  (def color (if (= group 0) "Red"
             (if (= group 1) "Blue"
             (if (= group 2) "Green"
             (if (= group 3) "Grey")))
             )
  )
  (clojure.string/join " " [color orbStar "star"])
)

(println "Orb 1: " (getColor orb1star))
(println "Orb 2: " (getColor orb2star))
(println "Orb 3: " (getColor orb3star))
(println "Orb 4: " (getColor orb4star))
(println "Orb 5: " (getColor orb5star))

