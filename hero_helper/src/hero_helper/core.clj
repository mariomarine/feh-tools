(ns hero-helper.core
  (:gen-class)
  (:import [org.jsoup Jsoup]))

(def ADDRESS "https://feheroes.gamepedia.com/Roy:_Brave_Lion")

(defn jsoup-connect [] (.get (Jsoup/connect ADDRESS)))

(defn get-table [] (.select (.child (.nextElementSibling (.parent (.first (.select (.select (jsoup-connect) ".mw-parser-output") "#Passives")))) 0) "tr"))

(defn get-address-passives []
  (rest (map #(.text (.child % 1)) (get-table)))
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (get-address-passives))
)

