(ns hxgm30.soc.config
  (:require
   [hxgm30.common.config :as config]
   [hxgm30.common.file :as common]))

(def config-file "hexagram30-config/soc.edn")

(defn data
  ([]
    (data config-file))
  ([filename]
    (config/data filename)))
