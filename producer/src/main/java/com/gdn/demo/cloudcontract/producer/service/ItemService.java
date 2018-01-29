package com.gdn.demo.cloudcontract.producer.service;

import com.gdn.demo.cloudcontract.producer.entity.Item;

import java.util.List;

/**
 * @author Alex Xandra Albert Sim
 */
public interface ItemService {
  Item saveItem(String content);

  List<Item> getItems();

  Item setDone(Long id);
}
