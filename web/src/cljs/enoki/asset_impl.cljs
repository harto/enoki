(ns enoki.asset-impl)

(def ^:dynamic *asset-path*
  "The base URL from which assets are fetched"
  "/assets/")

(defn load-image [path on-load on-error]
  (let [img (js/Image.)]
    (doto img
      (aset "onload" (fn [] (on-load path img)))
      (aset "onerror" (fn [] (on-error path)))
      (aset "src" (str *asset-path* path)))))
