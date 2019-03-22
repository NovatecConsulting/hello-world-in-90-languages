(ns jvm-clojure-ring.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :refer [parse-string]]
            [jvm-clojure-ring.handler :refer :all]))

(deftest hello-world-challenge
  (testing "default response"
    (let [response (app (mock/request :get "/say-hello"))]
      (is (= (:status response) 200))
      (is (= "{\"message\":\"Hello World!\"}" (:body response)))))
  (letfn [(message [r] (:message (parse-string (:body r) true)))]
    (testing "greeting Adam"
      (let [response (app (mock/request :post "/say-hello?name=Adam"))]
        (is (= "Hello Adam!" (message response)))))
    (testing "greeting Eve"
      (let [response (app (mock/request :post "/say-hello?name=Eve"))]
        (is (= "Hello Eve!" (message response))))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
