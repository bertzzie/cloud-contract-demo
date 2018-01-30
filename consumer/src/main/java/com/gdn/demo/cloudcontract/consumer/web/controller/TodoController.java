package com.gdn.demo.cloudcontract.consumer.web.controller;

import com.gdn.demo.cloudcontract.consumer.model.Item;
import com.gdn.demo.cloudcontract.consumer.web.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Alex Xandra Albert Sim
 */
@RestController
public class TodoController {
  @Autowired
  private TodoService service;

  @RequestMapping(
      name = "CreateItems",
      path = "/items",
      method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public List<Item> createItem(@RequestBody List<String> request) {
    return service.addItems(request);
  }

  @RequestMapping(
      name = "CreateItems",
      path = "/items",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public List<Item> getItems() {
    return service.getItems();
  }
}
