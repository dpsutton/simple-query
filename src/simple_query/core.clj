(ns simple-query.core
  (:require [clojure.java.jdbc :as jdbc]))

(defn sql-server-conn [conn]
  {:classname "com.microsoft.jdbc.sqlserver.SQLServerDriver"
   :subprotocol "sqlserver"
   :subname conn})

(defmacro make-query [name db query & params]
  `(defn ~(symbol name) [~@params]
     (jdbc/with-db-connection [connection# ~db]
       (jdbc/query connection# [~query ~@params]))))

(defmacro make-sinqle-query [name db query & params]
  `(defn ~(symbol name) [~@params]
     (when-let [results# (jdbc/with-db-connection [connection# ~db]
                           (jdbc/query connection# [~query ~@params]))]
       (first results#))))
