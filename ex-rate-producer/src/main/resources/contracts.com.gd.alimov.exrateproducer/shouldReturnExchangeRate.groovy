package com.gd.alimov.exrateproducer;

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return exchange rate"
    request {
        method GET()
        url("/api/get") {
            queryParameters {
                parameter("base", "RUB")
                parameter("to", "USD")
            }
        }
    }
    response {
        body("{\"base\":\"RUB\",\"to\":\"USD\",\"exchangeRate\":103.1618}")
        status 200
    }
}
