(defproject hero_helper "0.1.0"
  :description "Fire Emblem: Heroes - Hero Skills Helper"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.jsoup/jsoup "1.12.1"]]
  :main ^:skip-aot hero-helper.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
