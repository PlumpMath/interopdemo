(ns interopdemo.core

  (:import
   ;; import the classes you want to use from a package into this namespace
   [com.jentex.logging LoggerFactory LogMessageFactory]
   ;; just import one class
           java.util.Date))

;; Creating instances

(new LoggerFactory "")

;; or

(LoggerFactory. "")

;; Expands to the same thing


;; Member access

;; Static methods

(System/getProperty "java.vm.version")

;; Instance methods

(.toUpperCase "fred")

;; Passing parameters:

;; Clojure users java types for the main part, strings, longs, chars are ok

;; Parameters that require arrays:

(int-array [1 2 5 10]) ;; converts to int[]

;; Specific types of map, if your API is not completely happy with java.util.Map

(java.util.HashMap. {1 "1" 2 "2"})


;; These are enough to do basic interop calling java from clojure... probably 80% of what you need to can be achieved this way

(defn log-oil-levels [levels tanker-id ]
  (let [factory (new LoggerFactory "depot")
        messageFactory (new LogMessageFactory)
        logger (.build factory)
        message (.oilLevelsMessage messageFactory (int-array levels) tanker-id (new Date))]
    (.logMessage logger message)))


;;; But it looks pretty awkward