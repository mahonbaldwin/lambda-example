(ns dynamo-example.core
  (:require [clojure.walk :refer [keywordize-keys stringify-keys]]
            [cognitect.aws.client.api :as aws]))

(gen-class
  :name dynamo-example
  :methods [^:static [main [java.util.Map] java.util.Map]])

(def client (aws/client {:api :dynamodb}))

(defn save-stuff [table name]
  (println :saving-stuff table)
  (aws/invoke client {:op :BatchWriteItem,
                      :request
                          {:RequestItems
                           {table
                            [{"PutRequest"
                              {"Item"
                               {"name-id" {:S "82834"}
                                "name" {:S (str name 1)}}}}
                             {"PutRequest"
                              {"Item"
                               {"name-id" {:S "182393"}
                                "name" {:S (str name 2)}}}}]}}}))

(defn describe [table]
  (aws/invoke client {:op :DescribeTable
                      :request {:TableName table}}))

(defn -main [s]
  (let [{:keys [name table]} (keywordize-keys (into {} s))]
    (save-stuff table name)
    (println)
    (println :meta-data)
    (clojure.pprint/pprint (meta (save-stuff table name)))

    (println)
    (println :table-description)
    (clojure.pprint/pprint (describe table))
    (stringify-keys {:finished 1})))

