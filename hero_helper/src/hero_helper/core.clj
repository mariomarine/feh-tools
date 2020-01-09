(ns hero-helper.core
  (:gen-class) (:require [clojure.data.json :as json]))

(defn transformSkills [skill]
  {:name (skill "name")
   :rarity (skill "rarity")}
)

(defn transformData [entry]
  {:name (entry "name")
   :skills (map transformSkills (entry "skills"))}
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def data (json/read-str (slurp "new-hero-data.json")))
  (def newData (map transformData data))
  (println newData)
)
