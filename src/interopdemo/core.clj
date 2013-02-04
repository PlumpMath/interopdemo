(ns interopdemo.core
  (:import
   ;; import the classes you want to use from a package into this namespace
   [com.jentex.logging LoggerFactory LogMessageFactory LogDestination Configuration]
   ;; just import one class
           java.util.Date))

(set! *warn-on-reflection* true)

;; Creating instances



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

;; You can implement interfaces via reify

(defn create-debug-destination []
  (reify LogDestination
    (send [this context message]
      (println {:context context :message message}))))

;; These are enough to do basic interop calling java from clojure... probably 80% of what you need to can be achieved this way

(defn make-logger []
  (let [factory (.withConfig
                 (.withOutBoundTo
                  (new LoggerFactory "RAMSGATE-DEPO") *out*)
                 (new java.util.HashMap {"allow-foo" true}) )]
    (.build factory)))

(defn log-oil-levels [levels tanker-id ]
  (let [
        messageFactory  (new LogMessageFactory)
        message (.oilLevelsMessage messageFactory (int-array levels) tanker-id (new Date))
        logger (make-logger)]
    (.logMessage logger message)
    (.setLogDestination logger (create-debug-destination))
    (.logArbitrary logger (java.util.HashMap. {"oil-levels-check" 1 "debug" "Hello"}))))


;;; Works, but it looks pretty awkward. Can we make it better

;; There are some useful macros that can make interop less painful



;; Builder and factory patterns don't work too well in clojure
(.withConfig
                 (.withOutBoundTo
                  (new LoggerFactory "RAMSGATE-DEPO") *out*)
                 (new java.util.HashMap {"allow-foo" true}) )
;; The double-dot macro sorts this out

(.. (new LoggerFactory "RAMSGATE-DEPO")
    (withOutBoundTo *out*)
    (withConfig (new java.util.HashMap {"allow-foo" true})))
;; Similarly if you need to do a couple of things to one object (like mutating state)

(def tanker-id "tanker1234")
(def levels [100 401 202 101])

(let [
      messageFactory  (new LogMessageFactory)
      message (.oilLevelsMessage messageFactory (int-array levels) tanker-id (new Date))
      logger ( make-logger)]
  (.logMessage logger message)
  (.setLogDestination logger (create-debug-destination))
  (.logArbitrary logger (java.util.HashMap. {"oil-levels-check" 1 "debug" "Hello"})))


;; Here you can use the doto macro to make things simpler

(let [
      messageFactory  (new LogMessageFactory)
      message (.oilLevelsMessage messageFactory (int-array levels) tanker-id (new Date))]
  (doto (make-logger)
    (.logMessage message)
    (.setLogDestination (create-debug-destination))
    (.logArbitrary  (java.util.HashMap. {"oil-levels-check" 1 "debug" "Hello"}))))



;; Other things: Setting fields

;; It was really hard to find an example of this (because you probably shouldn't have public fields!)

(def config (new Configuration))

(set! (.debug config) true)
(set! (.maxBuffer config) 2920)


;; Under the hood

;; Everything relates back to the single dot, set! and new special forms

(macroexpand-1 '(.bar foo)) ;; (.foo bar)

;; We are a dynamic language... so how does it know what type to call?

;; The short answer is reflection (suprise suprise)

;; Exactly when it does the reflection depends on what kind of information the compiler has

;; If it knows the type at read time, it resolves the method at read time (erroring if it can't find it)

;; Otherwise it does it at eval time

;; Obviously all that eval-time reflection is really rather slow:

(defn uppercase-no-reflect [^String foo] (.toUpperCase foo))
(defn uppercase-standard [foo] (.toUpperCase foo))

(time (dotimes [n 100000] (dosomething-no-reflect "hello"))) ;; "Elapsed time: 259.726 msecs"

(time (dotimes [n 100000] (dosomething-no-reflect "hello"))) ;; "Elapsed time: 14.189 msecs"

;; If you set the warn-on-reflect flag, clojure will emit read-time warnings that let you see all the places it couldn't figure out what the type is

;; Should you fix? Maybe if it becomes a performance bottleneck. It's something to have as a trick if you find that something is slow