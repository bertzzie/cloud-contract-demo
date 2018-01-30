package com.gdn.demo.cloudcontract.consumer.web.service;

import com.gdn.demo.cloudcontract.consumer.model.Item;

import java.util.List;

/**
 * @author Alex Xandra Albert Sim
 */
public interface TodoService {
  List<Item> getItems();

  List<Item> addItems(List<String> contents);
}
