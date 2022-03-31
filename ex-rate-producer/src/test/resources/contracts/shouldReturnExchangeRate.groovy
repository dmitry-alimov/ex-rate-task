package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

Contract.make {
    description "should return exchange rate"
    request {
        headers {
            header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
        }
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
        body("{\"base\":\"RUB\",\"to\":\"USD\",\"exchangeRate\":103.1618}")
    }
}
