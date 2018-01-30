package com.gdn.demo.cloudcontract.consumer.client.fallback;

import com.gdn.demo.cloudcontract.consumer.client.ProducerFeign;
import com.gdn.demo.cloudcontract.consumer.model.request.NewItemRequest;
import com.gdn.demo.cloudcontract.consumer.model.response.GetAllItemsResponse;
import com.gdn.demo.cloudcontract.consumer.model.response.NewItemResponse;
import com.gdn.demo.cloudcontract.consumer.model.response.SetDoneResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Alex Xandra Albert Sim
 */
@Component
public class ProducerFeignFallback implements ProducerFeign {
  @Override
  public NewItemResponse createItem(NewItemRequest request) {
    return NewItemResponse.builder()
            .status(HttpStatus.BAD_GATEWAY.getReasonPhrase().toUpperCase())
            .build();
  }

  @Override
  public GetAllItemsResponse getAllItems() {
    return GetAllItemsResponse.builder()
            .status(HttpStatus.BAD_GATEWAY.getReasonPhrase().toUpperCase())
            .data(Collections.emptyList())
            .build();
  }

  @Override
  public SetDoneResponse setDone(Long id) {
    return SetDoneResponse.builder()
            .status(HttpStatus.BAD_GATEWAY.getReasonPhrase().toUpperCase())
            .build();
  }
}
