;;
;; Clojure interface for Kyoto Cabinet.
;;    based on a similiar interface for Tokyo Cabinet
;; Copyright (c) 2011 by Casey Link. Licensed under Eclipse Public License 1.0.
;; Copyright (c) 2009 by Tim Lopez. Licensed under Eclipse Public License 1.0.
;;
(ns kyoto-cabinet.core)

(import '(kyotocabinet))

(declare *kyoto*)                      ;; current cabinet
(declare *query*)                      ;; current table query (if any)

(def OREADER kyotocabinet.DB/OREADER)
(def OWRITER kyotocabinet.DB/OWRITER)
(def OCREATE kyotocabinet.DB/OCREATE)

(defn open-cabinet
  "Open a kyoto cabinet.  Is given a hashmap of connection parameters,
   of which only :filename is required. :type specifies the type of
   cabinet (legal values are :hash, :table, :fixed and :bplus, with
   the default being :hash).  :mode specifies the :open mode, and can
   be any of the flags OCREAT, OREADER, OWRITER, and OTRUNC. Usually
   you'll use with-cabinet, but if you use this you'll also need to
   use with and close-cabinet."
  [options]
  (let [fn (options :filename)
        kyoto-mode (options :mode OREADER)
        db (.newInstance kyotocabinet.DB)]
    (.open db fn kyoto-mode)
    { :connection db :mode kyoto-mode }
))

(defn close-cabinet
  "Closes a kyoto cabinet."
  [cabinet]
  (.close (cabinet :connection)))

(defmacro with-cabinet
  "Opens a cabinet and makes it available for the scope of body."
  [ options & body ]
  `(binding [*kyoto* (open-cabinet ~options)]
     (with-open [db# (*kyoto* :connection)]
       (do ~@body))))

(defn- check-type
  "Utility function.  Make sure that the cabinet is the right type."
  [type]
  (if (not (= type (*kyoto* :type)))
    (throw (IllegalArgumentException. (format "Expected cabinet type of %s but had %s" type (*kyoto* :type))))))

 (defn genuid "Get a unique primary key for a table." []
  (check-type :table)
  (.genuid (*kyoto* :connection)))

(defn put-value
  "Puts a value into the cabinet. With tables, you can use nil for the
  key to use an arbitrary unique key."
  [key val]
  (when (nil? key)
    (check-type :table))
  (let [putkey (if (nil? key) (genuid) key)]
    (.set (*kyoto* :connection) (str putkey) val)))

(defn get-value "Gets a value from the cabinet."
  [key]
  (.get (*kyoto* :connection) (str key)))

(defn increment
  "Add a number to the numeric integer value of a record.
   If the key does not exist, then it is added with the initial value of num.
   TODO: make a double version"
  ([key] (increment key 1))
  ([key num] (.increment (*kyoto* :connection) key (long num))))

