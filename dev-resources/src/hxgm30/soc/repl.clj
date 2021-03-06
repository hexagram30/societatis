(ns hxgm30.soc.repl
  "Project development namespace."
  (:require
   [clojure.java.io :as io]
   [clojure.pprint :refer [pprint]]
   [clojure.string :as string]
   [clojure.tools.namespace.repl :as repl]
   [clojusc.system-manager.core :refer :all]
   [clojusc.twig :as logger]
   [com.stuartsierra.component :as component]
   [hxgm30.soc.components.config :as config]
   [hxgm30.soc.components.core]
   [hxgm30.soc.network.jgrapht :as jgrapht]
   [rhizome.img :as img]
   [rhizome.viz :as viz]
   [taoensso.timbre :as log]
   [trifl.java :refer [show-methods]])
  (:import
   (java.awt GraphicsEnvironment)
   (java.io ByteArrayOutputStream)
   (java.net URI)
   (java.nio.file Paths)
   (java.security SecureRandom)
   (org.jgrapht.io DOTExporter)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Initial Setup & Utility Functions   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def setup-options {
  :init 'hxgm30.soc.components.core/init
  :after-refresh 'hxgm30.soc.repl/init-and-startup
  :throw-errors false})

(defn init
  "This is used to set the options and any other global data.

  This is defined in a function for re-use. For instance, when a REPL is
  reloaded, the options will be lost and need to be re-applied."
  []
  (logger/set-level! '[hxgm30] :debug)
  (setup-manager setup-options))

(defn init-and-startup
  "This is used as the 'after-refresh' function by the REPL tools library.
  Not only do the options (and other global operations) need to be re-applied,
  the system also needs to be started up, once these options have be set up."
  []
  (init)
  (startup))

(defn display-image
  ""
  [graph & [opts]]
  (if (GraphicsEnvironment/isHeadless)
    (log/warn "No graphical environment; can't display image")
    (do
      (-> graph
          (jgrapht->dot {:as-string true})
          (img/dot->image opts)
          (viz/view-image)))))

(defn view-network
  ([]
    (view-network 25))
  ([node-count]
    (view-network node-count {:layout "twopi"}))
  ([node-count opts]
    (if (GraphicsEnvironment/isHeadless)
      (log/warn "No graphical environment; can't view network")
      (display-image (jgrapht/random 1 node-count) opts)  )))

;; It is not always desired that a system be started up upon REPL loading.
;; Thus, we set the options and perform any global operations with init,
;; and let the user determine when then want to bring up (a potentially
;; computationally intensive) system.
(init)

(defn banner
  []
  (println (slurp (io/resource "text/banner.txt")))
  :ok)
