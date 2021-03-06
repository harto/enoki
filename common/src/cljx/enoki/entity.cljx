;; Functions for initialising and managing entities.
;;
;; Entities are simple groupings of components, represented as maps of component
;; types (keywords) to components (also maps).

(ns enoki.entity)

(let [next-id (atom 0)]
  (defn uid
    "Reserve and return a unique numeric ID."
    []
    (swap! next-id inc)))

(defn new
  "Initialise an entity with some collection of components. A unique ID is
   assigned and accessible via `:id`. Components are accessible by their
   name as a keyword; i.e. use `:foo` to fetch components defined using
   `(defcomponent foo ...)`."
  [& components]
  (into {:id (uid)}
        (map (fn [comp] [(:enoki.component/type comp) comp]) components)))

(defn has-component? [entity component-type]
  (contains? entity component-type))

(defn filter-component
  "Filter `entities` that include a component of a given type."
  [entities component-type]
  (filter #(has-component? % component-type) entities))
