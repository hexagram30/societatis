(ns hxgm30.soc.network
  (:require
   [clojure.java.io :as io]
   [clojure.java.shell :as shell]
   [clojure.string :as string]
   [rhizome.img :as img]
   [rhizome.viz :as viz])
  (:import
   (java.io ByteArrayOutputStream)
   (java.util.function Supplier)
   (javax.imageio ImageIO)
   (org.jgrapht.generate BarabasiAlbertGraphGenerator)
   (org.jgrapht.graph DefaultEdge SimpleGraph)
   (org.jgrapht.io DOTExporter)
   (org.jgrapht.util SupplierUtil)))

(def default-edge-supp (SupplierUtil/createDefaultEdgeSupplier))
(def default-vertext-count (atom 0))

(def default-vertex-supp
  (reify Supplier
    (get [this]
      (swap! default-vertext-count inc))))

(defn empty-graph
  ""
  ([]
    (empty-graph default-vertex-supp default-edge-supp
     false))
  ([vertex-supp edge-supp weighted?]
    (new SimpleGraph vertex-supp edge-supp weighted?)))

(defn node-graph
  ""
  ([]
    (node-graph default-vertex-supp default-edge-supp false))
  ([vertex-supp edge-supp weighted?]
    (let [graph (empty-graph vertex-supp edge-supp weighted?)]
      (.addVertex graph)
      graph)))

(defn seed-graph
  ""
  ([total-node-count partition-count]
    (seed-graph
     total-node-count partition-count default-vertex-supp default-edge-supp
     false))
  ([total-node-count partition-count vertex-supp edge-supp weighted?]
    (let [graph (empty-graph vertex-supp edge-supp weighted?)
          nodes (partition partition-count
                           (map (fn [_] (.addVertex graph))
                                (range total-node-count)))]
      (mapv (fn [[v1 v2]] (.addEdge graph v1 v2)) nodes)
      graph)))

(defn random
  ""
  ([m n]
    (random 1 m n))
  ([m0 m n]
    (random m0 m n (long (rand-int (Math/pow 2 31)))))
  ([m0 m n random-seed]
    (let [generator (new BarabasiAlbertGraphGenerator m0 m n random-seed)
          graph (if (= m0 1) (empty-graph) (seed-graph m0 2))]
      (.generateGraph generator graph nil)
      graph)))

(defn write
  "Write the graph data to a .dot file."
  [graph file-path]
  (let [exporter (new DOTExporter)]
    (with-open [w (io/writer file-path)]
      (.exportGraph exporter graph w))))

(defn write-image
  "Save the graph data as an image using GraphViz's `dot` program."
  [graph img-file-path]
  (let [split (string/split img-file-path #"\.")
        file-type (last split)
        dot-file-path (str (string/join "." (butlast split)) ".dot")]
    (write graph dot-file-path)
    (let [dot-str (slurp dot-file-path)
          img (img/dot->image dot-str)]
      (img/save-image img img-file-path))))

(defn graph->dot
  ""
  [graph & [opts]]
  (let [w (new ByteArrayOutputStream)]
    (.exportGraph (new DOTExporter) graph w)
    (if (:as-string opts)
      (str w)
      w)))

(defn display-image
  ""
  [graph & [opts]]
  (-> graph
      (graph->dot {:as-string true})
      (img/dot->image opts)
      (viz/view-image)))
