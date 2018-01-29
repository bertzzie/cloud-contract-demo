package com.gdn.demo.cloudcontract.producer.service.impl;

import com.gdn.demo.cloudcontract.producer.entity.Item;
import com.gdn.demo.cloudcontract.producer.repository.ItemRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Alex Xandra Albert Sim
 */
public class ItemServiceImplTest {
  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  private ItemRepository itemRepository;

  @InjectMocks
  private ItemServiceImpl service;

  private Long ITEM_ID = 10L;
  private String ITEM_CONTENT = "ITEM_CONTENT";
  private Boolean DEFAULT_DONE_STATUS = false;

  private Item DEFAULT_ITEM = Item.builder()
          .id(ITEM_ID)
          .content(ITEM_CONTENT)
          .done(DEFAULT_DONE_STATUS)
          .build();

  @Before
  public void setup() {
    when(itemRepository.findOne(ITEM_ID)).thenReturn(DEFAULT_ITEM);
    when(itemRepository.findAll()).thenReturn(Collections.singletonList(DEFAULT_ITEM));
  }

  @After
  public void cleanup() {
    verifyNoMoreInteractions(itemRepository);
  }

  @Test
  public void testSaveItem() {
    String CONTENT = "CONTENT";
    Long ID = 11L;
    Boolean DONE = false;

    Item ITEM = Item.builder()
            .id(ID)
            .content(CONTENT)
            .done(DONE)
            .build();

    when(itemRepository.save(isA(Item.class))).thenReturn(ITEM);

    Item item = service.saveItem(CONTENT);

    verify(itemRepository, times(1)).save(isA(Item.class));

    assertEquals(ID, item.getId());
    assertEquals(CONTENT, item.getContent());
    assertEquals(DONE, item.getDone());
  }

  @Test
  public void testGetItems() {
    List<Item> items = service.getItems();

    verify(itemRepository).findAll();

    assertEquals(1, items.size());
    items.forEach(item -> {
      assertEquals(ITEM_ID, item.getId());
      assertEquals(ITEM_CONTENT, item.getContent());
      assertEquals(DEFAULT_DONE_STATUS, item.getDone());
    });
  }

  @Test
  public void testSetDone() {
    Item done = DEFAULT_ITEM;
    done.setDone(true);

    when(itemRepository.save(isA(Item.class))).thenReturn(done);

    Item result = service.setDone(ITEM_ID);

    verify(itemRepository).findOne(eq(ITEM_ID));
    verify(itemRepository).save(isA(Item.class));

    assertEquals(ITEM_ID, result.getId());
    assertEquals(ITEM_CONTENT, result.getContent());
    assertTrue(result.getDone());

    DEFAULT_ITEM.setDone(DEFAULT_DONE_STATUS);
  }
}
