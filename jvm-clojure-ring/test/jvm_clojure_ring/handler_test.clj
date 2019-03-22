(ns jvm-clojure-ring.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [jvm-clojure-ring.handler :refer :all]))

(deftest hello-world-challenge
  (testing "greeting Adam"
    (let [response (app (mock/request :post "/say-hello?name=Adam"))]
      (is (= (:body response) "Hello Adam!"))))
  (testing "greeting Eve"
    (let [response (app (mock/request :post "/say-hello?name=Eve"))]
      (is (= (:body response) "Hello Eve!"))))
  (testing "default response"
    (let [response (app (mock/request :get "/say-hello"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World!"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
