(ns dynamo-example.core
  (:require [clojure.walk :refer [keywordize-keys stringify-keys]]
            [cognitect.aws.client.api :as aws]))

(gen-class
  :name dynamo-example
  :methods [^:static [main [java.util.Map] java.util.Map]])

(def client (aws/client {:api :dynamodb}))

(defn save-stuff [name]
  (println :saving-stuff)
  (aws/invoke client {:op :BatchWriteItem,
                      :request
                          {:RequestItems
                           {"example-db"
                            [{"PutRequest"
                              {"Item"
                               {"name-id" {:S "82834"}
                                "name" {:S (str name 1)}}}}
                             {"PutRequest"
                              {"Item"
                               {"name-id" {:S "182393"}
                                "name" {:S (str name 2)}}}}]}}}))

(defn -main [s]
  (let [{:keys [name]} (keywordize-keys (into {} s))]
    (save-stuff name)
    (println :meta-data)
    (clojure.pprint/pprint (meta (save-stuff name)))
    (stringify-keys {:finished 1})))

