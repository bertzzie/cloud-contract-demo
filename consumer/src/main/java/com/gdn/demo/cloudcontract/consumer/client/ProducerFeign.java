package com.gdn.demo.cloudcontract.consumer.client;

import com.gdn.demo.cloudcontract.consumer.client.fallback.ProducerFeignFallbackFactory;
import com.gdn.demo.cloudcontract.consumer.config.ProducerFeignConfiguration;
import com.gdn.demo.cloudcontract.consumer.model.request.NewItemRequest;
import com.gdn.demo.cloudcontract.consumer.model.response.GetAllItemsResponse;
import com.gdn.demo.cloudcontract.consumer.model.response.NewItemResponse;
import com.gdn.demo.cloudcontract.consumer.model.response.SetDoneResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alex Xandra Albert Sim
 */
@FeignClient(
    name = "producerFeign",
    url = "${service.producer.endpoint}",
    fallbackFactory = ProducerFeignFallbackFactory.class,
    configuration = ProducerFeignConfiguration.class
)
public interface ProducerFeign {

  @RequestMapping(
      name = "CreateItem",
      path = "/item",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  NewItemResponse createItem(@RequestBody NewItemRequest request);

  @RequestMapping(
      name = "GetAllItems",
      path = "/items",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  GetAllItemsResponse getAllItems();

  @RequestMapping(
      name = "SetItemDone",
      path = "/item/{id}/done",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  SetDoneResponse setDone(@PathVariable(value = "id") Long id);
}
