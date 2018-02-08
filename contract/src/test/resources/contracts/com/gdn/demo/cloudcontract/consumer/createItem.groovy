package contracts.com.gdn.demo.cloudcontract.consumer

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

/**
 * @author Alex Xandra Albert Sim
 */
Contract.make {
    request {
        description("""
Creating new list.

given:
    The user wanted to create a new list
when:
    User sent the data they wanted to be added
then:
    We returned the data that's just created
""")
        method PUT()
        url '/item'
        headers {
            header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        }
        body (
                content: "ITEM_CONTENT"
        )
    }

    response {
        status 200
        headers {
            header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        }
        body (
        """
            {
                "status": "CREATED",
                "data": {
                    "id": 10,
                    "content": "ITEM_CONTENT",
                    "done": false
                }
            }
        """
        )
    }
}
