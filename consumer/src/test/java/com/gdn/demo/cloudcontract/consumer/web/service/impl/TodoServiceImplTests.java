package com.gdn.demo.cloudcontract.consumer.web.service.impl;

import com.gdn.demo.cloudcontract.consumer.client.ProducerFeign;
import com.gdn.demo.cloudcontract.consumer.model.Item;
import com.gdn.demo.cloudcontract.consumer.model.request.NewItemRequest;
import com.gdn.demo.cloudcontract.consumer.model.response.GetAllItemsResponse;
import com.gdn.demo.cloudcontract.consumer.model.response.NewItemResponse;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Alex Xandra Albert Sim
 */
public class TodoServiceImplTests {
  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  private ProducerFeign feign;

  @InjectMocks
  private TodoServiceImpl service;

  private final String ITEM_CONTENT = "ITEM_CONTENT";
  private final Long ITEM_ID = 10L;
  private final Boolean DONE_STATUS = false;

  private final Item ITEM = Item.builder()
          .id(ITEM_ID)
          .content(ITEM_CONTENT)
          .done(DONE_STATUS)
          .build();

  @After
  public void cleanup() {
    verifyNoMoreInteractions(feign);
  }

  @Test
  public void testGetItems() {
    GetAllItemsResponse response = GetAllItemsResponse.builder()
        .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
        .data(Collections.singletonList(ITEM))
        .build();

    when(feign.getAllItems()).thenReturn(response);

    List<Item> items = service.getItems();

    verify(feign).getAllItems();

    assertEquals(1, items.size());
    items.forEach(item -> {
      assertEquals(ITEM_ID, item.getId());
      assertEquals(ITEM_CONTENT, item.getContent());
      assertEquals(DONE_STATUS, item.getDone());
    });
  }

  @Test
  public void testAddItems() {
    List<String> requests = createRequest();

    when(feign.createItem(any(NewItemRequest.class))).then(i -> {
      NewItemRequest req = i.getArgumentAt(0, NewItemRequest.class);
      Item item = Item.builder().content(req.getContent()).build();

      return NewItemResponse.builder().data(item).build();
    });

    List<Item> responses = service.addItems(requests);

    verify(feign, times(responses.size())).createItem(any(NewItemRequest.class));

    assertEquals(requests.size(), responses.size());
    IntStream.range(0, responses.size())
        .forEach(i -> {
             assertEquals(requests.get(i), responses.get(i).getContent());
        });
  }

  private List<String> createRequest() {
    List<String> result = new ArrayList<>();
    result.add("ITEM1");
    result.add("ITEM2");
    result.add("ITEM3");

    return result;
  }
}
