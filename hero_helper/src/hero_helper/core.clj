(ns hero-helper.core
  (:gen-class)
  (:import [org.jsoup Jsoup]))

(def ADDRESS "https://feheroes.gamepedia.com/Roy:_Brave_Lion")

(defn get-address-passives []
  (rest (map #(.text (.child % 1)) (.select (.child (.nextElementSibling (.parent (.first (.select (.select (.get (Jsoup/connect ADDRESS)) ".mw-parser-output") "#Passives")))) 0) "tr")))
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (get-address-passives))
)

