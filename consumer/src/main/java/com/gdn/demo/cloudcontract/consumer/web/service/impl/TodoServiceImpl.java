package com.gdn.demo.cloudcontract.consumer.web.service.impl;

import com.gdn.demo.cloudcontract.consumer.client.ProducerFeign;
import com.gdn.demo.cloudcontract.consumer.model.Item;
import com.gdn.demo.cloudcontract.consumer.model.request.NewItemRequest;
import com.gdn.demo.cloudcontract.consumer.model.response.NewItemResponse;
import com.gdn.demo.cloudcontract.consumer.web.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alex Xandra Albert Sim
 */
@Service
public class TodoServiceImpl implements TodoService {
  @Autowired
  private ProducerFeign feign;

  @Override
  public List<Item> getItems() {
    return feign.getAllItems().getData();
  }

  @Override
  public List<Item> addItems(List<String> contents) {
    return contents.stream()
            .map(content -> feign.createItem(NewItemRequest.builder().content(content).build()))
            .map(NewItemResponse::getData)
            .collect(Collectors.toList());
  }
}
