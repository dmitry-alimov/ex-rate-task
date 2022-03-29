package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return currency of pair RUB/USD")
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
        status OK()
        body(
                "base": "RUB",
                "to": "USD",
                "exchangeRate": 103.1618
        )
        headers {
            contentType('application/json')
        }
    }
}