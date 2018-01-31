package contracts.com.gdn.demo.cloudcontract.consumer

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

/**
 * @author Alex Xandra Albert Sim
 */
org.springframework.cloud.contract.spec.Contract.make {
    request {
        description("""
Represents getting all list items, when the data is empty

given:
    There is no item in database
when:
    User request for all items
then:
    We'll show them an empty array.
""")
        method 'GET'
        url '/items'
        headers {
            header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        }
    }

    response {
        status 200
        headers {
            header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        }
        body {
            """
                {
                    "status": "OK",
                    "data": []
                }
            """
        }
    }
}
