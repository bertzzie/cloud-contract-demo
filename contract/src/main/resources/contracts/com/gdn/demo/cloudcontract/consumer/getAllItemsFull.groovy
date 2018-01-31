package contracts.com.gdn.demo.cloudcontract.consumer

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

/**
 * @author Alex Xandra Albert Sim
 */
org.springframework.cloud.contract.spec.Contract.make {
    request {
        description("""
Represents getting all list items

given:
    There are 3 items in database
when:
    User request for all items
then:
    We'll show them all items we have.
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
                    "data": [
                        {
                            "id": 10,
                            "content": "test 1243",
                            "done": false
                        },
                        {
                            "id": 11,
                            "content": "test",
                            "done": false
                        },
                        {
                            "id": 12,
                            "content": "Monado",
                            "done": false
                        }
                    ]
                }
            """
        }
    }
}
