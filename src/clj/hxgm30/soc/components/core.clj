(ns hxgm30.soc.components.core
  (:require
    [com.stuartsierra.component :as component]
    [hxgm30.common.components.config :as common-config]
    [hxgm30.common.components.logging :as logging]
    [hxgm30.soc.components.config :as config]
    [hxgm30.soc.config :as config-lib]
    [taoensso.timbre :as log]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Common Configuration Components   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn cfg
  [cfg-data]
  {:config (common-config/create-component cfg-data)})

(def log
  {:logging (component/using
             (logging/create-component)
             [:config])})

(defn basic
  [cfg-data]
  (merge (cfg cfg-data)
         log))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Component Initializations   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn initialize-bare-bones
  []
  (-> (config-lib/data)
      basic
      component/map->SystemMap))

(defn initialize-config-only
  []
  (-> (config-lib/data)
      cfg
      component/map->SystemMap))=

(def init-lookup
  {:basic #'initialize-bare-bones
   :testing #'initialize-config-only})

(defn init
  ([]
    (init :basic))
  ([mode]
    ((mode init-lookup))))
