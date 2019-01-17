(ns hxgm30.soc.config
	"Configuration library for project."
  (:require
   [hxgm30.common.config :as config]
   [hxgm30.common.file :as common]))

(def config-file "hexagram30-config/soc.edn")

(defn data
  ([]
    (data config-file))
  ([filename]
    (config/data filename)))
