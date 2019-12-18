(require '[clojure.string :as str])
(defn getChance []
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
  (list threes fours fives focus)
)
(def focus_color_wheel "green red blue grey")
(def focus_color_wheel (str/split focus_color_wheel #" "))

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

(defn getOrbStar [threes_chance fours_chance fives_chance focuses_chance] 
  (def pick (wrand [threes_chance fours_chance fives_chance focuses_chance]))
  (if (= pick 0) "three"
  (if (= pick 1) "four"
  (if (= pick 2) "five"
  "focus"))
  )
)

(def pool_numbers (hash-map "red" (hash-map "focus" 0 "five" 49 "four" 33 "three" 33)
                            "blue" (hash-map "focus" 0 "five" 35 "four" 32 "three" 30)
                            "green" (hash-map "focus" 0 "five" 31 "four" 23 "three" 20)
                            "grey" (hash-map "focus" 0 "five" 27 "four" 34 "three" 30)
                  )
)

(doseq [col focus_color_wheel] (def pool_numbers
                                 (assoc-in pool_numbers [col "focus"]
                                   (inc (get-in pool_numbers [col "focus"]))
                                 )
                               )
)



(defn getColor [orbStar]
  (def group (wrand [(get-in pool_numbers ["red" orbStar])
                     (get-in pool_numbers ["blue" orbStar])
                     (get-in pool_numbers ["green" orbStar])
                     (get-in pool_numbers ["grey" orbStar])]
             )
  )
  (if (= group 0) "Red"
             (if (= group 1) "Blue"
             (if (= group 2) "Green"
             (if (= group 3) "Grey")))
  )
)

(defn Summon [three_chance four_chance five_chance focus_chance]
  (def orbs [])
  (dotimes [i 5] (def orbs (conj orbs (hash-map "stars" (getOrbStar three_chance four_chance five_chance focus_chance)))))
  (for [orb orbs] (assoc orb "color" (getColor (get orb "stars"))))
)

;; Get Initial Chance
;; Determine strategy (snipe or bulk) (start with Snipe, ask for color)
;; Call Summom, open orbs of chosen color
;; Track Rate Increase Threshold (when 5 orbs have been opened without a 5* unit, increase chances of getting a 5* by .5% (watch for 120 times raises chances to 100%)

;; (def session (apply Summon (getChance)))

;; (println session)

(defn snipes [session] (for [orb session :when (= (get orb "color") "Green")] orb))

(defn get-orbs-spent [finalsession] (case (count finalsession) 0 0 1 5 2 9 3 13 4 17 5 20))

(defn get-sniped-fives [stars, snipes] (count (for [orb snipes :when (= (get orb "stars") stars)] 1)))

(def base-chances (hash-map :three 36 :four 58 :five 3 :focus 3))

(defn snipes-2 [orbsSpent summedNoFives numFocusSummed chances] 
  ;; values to track: #focus summed, chance of fives, #summed-no-fives
  ;; if #focus-summed >= 11 stop and print stats, otherwise pass in #focus summed
  ;; if #summed-no-fives >= 5, increase chance-of-fives by 0.5%
  ;; else pass in #summed-no-fives plus count-new-snipes
  (if (> numFocusSummed 10)
    ;; then
    (do orbsSpent)
    ;; else
    (do
      (if (< summedNoFives 5)
        ;; then
        (def session (Summon (chances :three) (chances :four) (chances :five) (chances :focus)))
        ;; else
        (def session (Summon (- (chances :three) 0.25) (- (chances :four) 0.25) (+ (chances :five) 0.25) (+ (chances :focus) 0.25))) ;; Plus 0.5%
      )
      (if (<
            (+ (get-sniped-fives "five" (snipes session)) (get-sniped-fives "focus" (snipes session)) summedNoFives)
            5
          )
        (snipes-2 (+ orbsSpent (get-orbs-spent (snipes session)))
                  (+ summedNoFives (count (snipes session)))
                  (+ numFocusSummed (get-sniped-fives "focus" (snipes session)))
                  (update-in (update-in (update-in (update-in chances [:three] - 0.25) [:four] - 0.25) [:five] + 0.25) [:focus] + 0.25)
                )
        (snipes-2 (+ orbsSpent (get-orbs-spent (snipes session)))
                  0
                  (+ numFocusSummed (get-sniped-fives "focus" (snipes session)))
                  base-chances
        )
      )
    )
  )
)

(defn print-average! [data] (println "Average:" (float (/ (apply + data) (count data)))))
(defn print-min-max! [data] (println "Minimum" (first (sort data)))(println "Maximum" (last (sort data))))
(let [data (take 1000 (repeatedly #(snipes-2 0 0 0 base-chances)))]
  (print-average! data)
  (print-min-max! data)
)

