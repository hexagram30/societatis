(defn get-banner
  []
  (try
    (str
      (slurp "resources/text/banner.txt")
      ;(slurp "resources/text/loading.txt")
      )
    ;; If another project can't find the banner, just skip it;
    ;; this function is really only meant to be used by Dragon itself.
    (catch Exception _ "")))

(defn get-prompt
  [ns]
  (str "\u001B[35m[\u001B[34m"
       ns
       "\u001B[35m]\u001B[33m\u001B[m=> "))

(defproject hexagram30/societatis "0.1.0-SNAPSHOT"
  :description "Society generation and evolution for use by hexagram30 projects"
  :url "https://github.com/hexagram30/societatis"
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :exclusions [
    ;; JAR file conflicts
    [org.antlr/antlr4-runtime]
    [org.apache.commons/commons-lang3]
    [org.clojure/clojure]
    [org.clojure/clojurescript]
    [org.clojure/core.rrb-vector]
    [com.stuartsierra/component]]
  :dependencies [
    ;; JAR file conflicts
    [org.antlr/antlr4-runtime "4.7.2"]
    [org.apache.commons/commons-lang3 "3.9"]
    [org.clojure/core.rrb-vector "0.0.14"]
    ;; Regular deps
    [clojusc/rhizome "0.3.0-SNAPSHOT"]
    [clojusc/system-manager "0.3.0"]
    [clojusc/twig "0.4.1"]
    [com.stuartsierra/component "0.4.0"]
    [hexagram30/common "0.2.0-SNAPSHOT"]
    [org.clojure/clojure "1.10.1"]
    [org.gephi/graphstore "0.5.9"]
    [org.jgrapht/jgrapht-core "1.3.1"]
    [org.jgrapht/jgrapht-io "1.3.1"]]
  :plugins [
    [org.clojure/core.rrb-vector "0.0.14"]]
  :profiles {
    :ubercompile {
      :aot :all}
    :dev {
      :dependencies [
        [clojusc/trifl "0.4.2"]
        [org.clojure/tools.namespace "0.3.1"]]
      :plugins [
        [lein-shell "0.5.0"]
        [venantius/ultra "0.6.0" :exclusions [org.clojure/clojure]]]
      :main hxgm30.soc.core}
    :gui {
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns hxgm30.soc.repl
        :prompt ~get-prompt
        :init ~(println (get-banner))}}
    :lint {
      :source-paths ^:replace ["src"]
      :test-paths ^:replace []
      :plugins [
        [jonase/eastwood "0.3.6" :exclusions [org.clojure/clojure]]
        [lein-ancient "0.6.15"]
        [lein-kibit "0.1.7" :exclusions [org.clojure/clojure]]]}
    :test {
      :plugins [
        [lein-ltest "0.3.0"]]}
    :docs {
      :dependencies [
        [hexagram30/codox-theme "0.1.0-SNAPSHOT"]]
      :plugins [
        [lein-codox "0.10.7"]]
      :codox {
        :project {
          :name "hexagram30/societatis"}
        :themes [
          :hexagram30]
        :output-path "docs/current"
        :doc-paths ["resources/markdown"]
        :namespaces [#"^hxgm30\..*"]
        :metadata {
          :doc/format :markdown}}}}
  :aliases {
    ;; Dev Aliases
    "repl" ["do"
      ["clean"]
      ["with-profile" "+gui" "repl"]]
    "ubercompile" ["do"
      ["clean"]
      ["with-profile" "+ubercompile" "compile"]]
    "check-vers" ["with-profile" "+lint" "ancient" "check" ":all"]
    "check-jars" ["with-profile" "+lint" "do"
      ["deps" ":tree"]
      ["deps" ":plugin-tree"]]
    "check-deps" ["do"
      ["check-jars"]
      ["check-vers"]]
    "kibit" ["with-profile" "+lint" "kibit"]
    "eastwood" ["with-profile" "+lint" "eastwood" "{:namespaces [:source-paths]}"]
    "lint" ["do"
      ["kibit"]
      ; ["eastwood"]
      ]
    "ltest" ["with-profile" "+test" "ltest"]
    "ltest-clean" ["do"
      ["clean"]
      ["ltest"]]
    "docs" ["do"
      ["clean"]
      ["with-profile" "+docs" "codox"]]
    "build" ["do"
      ["clean"]
      ["check-vers"]
      ["ubercompile"]
      ["lint"]
      ["clean"]
      ["ltest" ":all"]
      ["docs"]
      ["uberjar"]]
    "publish-docs" ["do"
      ["docs"]
      ["shell" "resources/scripts/publish-docs.sh"]]})

