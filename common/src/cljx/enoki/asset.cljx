;; Asset management

^:clj (ns enoki.asset
        (:require [enoki.asset-impl :as impl]
                  [enoki.error])
        (:use [enoki.error-macros :only [signal-error]]))

^:cljs (ns enoki.asset
         (:require [enoki.asset-impl :as impl]
                   [enoki.error])
         (:use-macros [enoki.error-macros :only [signal-error]]))

;; ## Loading

;; FIXME: assets should be loaded in parallel
(defn- load-assets*
  [assets paths load-asset & callbacks]
  (let [[path & remaining-paths] paths
        {:keys [before-asset after-asset on-load on-error]} (apply hash-map callbacks)
        asset-loaded (fn [path asset]
                       (if after-asset (after-asset path asset))
                       (let [assets (assoc assets path asset)]
                         (if remaining-paths
                           (apply load-assets* assets remaining-paths load-asset callbacks)
                           (if on-load (on-load assets)))))
        asset-errored (or on-error
                          (fn [path e]
                            (signal-error (format "Failed to load image `%s`" path) e)))]
    (if before-asset (before-asset path))
    (load-asset path asset-loaded asset-errored)))

(defn load-assets
  "Attempts to load a seq of asset paths using a given asset-loading function.
   `callbacks` is a set of key-value pairs that defines any or all of the
   following:

     * `:before-asset` - a function `(fn [path])` that is called immediately
                         prior to loading an asset.

     * `:after-asset`  - a function `(fn [path asset])` that is called
                         immediately after an asset is successfully loaded.

     * `:on-load`      - a function `(fn [assets])` that is called when all
                         assets are successfully loaded. `assets` is a map of
                         paths to assets.

     * `:on-error`     - a function `(fn [path e])` that is called when an asset
                         fails to load. If this function is not provided, an
                         error is signalled for each asset that fails to load."
  [paths load-asset & callbacks]
  (apply load-assets* {} paths load-asset callbacks))

(defn load-image [path on-load on-error]
  (impl/load-image path on-load on-error))

(defn load-images [paths & callbacks]
  (apply load-assets paths load-image callbacks))

;; ## Retrieval

(defn image [state id]
  (if-let [image (get-in state [:assets id])]
    image
    (signal-error (format "Unknown image %s" id))))
