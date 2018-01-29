package com.gdn.demo.cloudcontract.producer.service.impl;

import com.gdn.demo.cloudcontract.producer.entity.Item;
import com.gdn.demo.cloudcontract.producer.repository.ItemRepository;
import com.gdn.demo.cloudcontract.producer.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alex Xandra Albert Sim
 */
@Service
public class ItemServiceImpl implements ItemService {
  @Autowired
  private ItemRepository itemRepository;

  @Override
  public Item saveItem(String content) {
    Item newItem = Item.builder()
            .content(content)
            .done(false)
            .build();

    return itemRepository.save(newItem);
  }

  @Override
  public List<Item> getItems() {
    return itemRepository.findAll();
  }

  @Override
  public Item setDone(Long id) {
    Item item = itemRepository.findOne(id);
    item.setDone(true);

    return itemRepository.save(item);
  }
}
