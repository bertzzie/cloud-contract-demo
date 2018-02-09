package com.gdn.demo.cloudcontract.producer.web.controller;

import com.gdn.demo.cloudcontract.producer.entity.Item;
import com.gdn.demo.cloudcontract.producer.service.ItemService;
import com.gdn.demo.cloudcontract.producer.web.model.request.NewItemRequest;
import com.gdn.demo.cloudcontract.producer.web.model.response.GetAllItemsResponse;
import com.gdn.demo.cloudcontract.producer.web.model.response.NewItemResponse;
import com.gdn.demo.cloudcontract.producer.web.model.response.SetDoneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Alex Xandra Albert Sim
 */
@RestController
public class ItemController {
  @Autowired
  private ItemService itemService;

  @RequestMapping(
      name = "CreateItem",
      path = "/item",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public NewItemResponse createItem(@RequestBody NewItemRequest request) {
    Item item = itemService.saveItem(request.getContent());

    return toNewItemResponse(item);
  }

  @RequestMapping(
      name = "GetAllItems",
      path = "/items",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public GetAllItemsResponse getAllItems() {
    List<Item> items = itemService.getItems();

    return toGetAllItemsResponse(items);
  }

  @RequestMapping(
      name = "SetItemDone",
      path = "/item/{id}/done",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public SetDoneResponse setDone(@PathVariable(value = "id") Long id) {
    Item data = itemService.setDone(id);

    return toSetDoneResponse(data);
  }

  private SetDoneResponse toSetDoneResponse(Item item) {
    return SetDoneResponse.builder()
            .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
            .data(item)
            .build();
  }

  private GetAllItemsResponse toGetAllItemsResponse(List<Item> items) {
    return GetAllItemsResponse.builder()
            .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
            .data(items)
            .build();
  }

  private NewItemResponse toNewItemResponse(Item item) {
    return NewItemResponse.builder()
            .status(HttpStatus.CREATED.getReasonPhrase().toUpperCase())
            .data(item)
            .build();
  }
}
