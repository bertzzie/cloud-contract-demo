package com.gdn.demo.cloudcontract.producer.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.demo.cloudcontract.producer.entity.Item;
import com.gdn.demo.cloudcontract.producer.service.ItemService;
import com.gdn.demo.cloudcontract.producer.web.model.request.NewItemRequest;
import com.gdn.demo.cloudcontract.producer.web.model.response.GetAllItemsResponse;
import com.gdn.demo.cloudcontract.producer.web.model.response.NewItemResponse;
import com.gdn.demo.cloudcontract.producer.web.model.response.SetDoneResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Alex Xandra Albert Sim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ItemService itemService;

  @LocalServerPort
  private Integer port;

  private String ITEM_CONTENT = "ITEM_CONTENT";
  private Long ITEM_ID = 10L;
  private Boolean DONE_STATUS = false;

  private Item ITEM = Item.builder()
      .id(ITEM_ID)
      .content(ITEM_CONTENT)
      .done(DONE_STATUS)
      .build();

  @After
  public void cleanup() {
    verifyNoMoreInteractions(itemService);
  }

  @Test
  public void testCreateItem() {
    when(itemService.saveItem(eq(ITEM_CONTENT))).thenReturn(ITEM);

    NewItemRequest request = NewItemRequest.builder().content(ITEM_CONTENT).build();
    NewItemResponse response = NewItemResponse.builder()
        .status(HttpStatus.CREATED.getReasonPhrase().toUpperCase())
        .data(ITEM)
        .build();

    RestAssured.given()
        .port(port)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(request)
        .put("/item")
        .then()
        .body(equalTo(toJsonString(response)));

    verify(itemService).saveItem(eq(ITEM_CONTENT));
  }

  @Test
  public void testGetAllItems() {
    List<Item> result = Collections.singletonList(ITEM);
    when(itemService.getItems()).thenReturn(result);

    GetAllItemsResponse response = GetAllItemsResponse.builder()
        .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
        .data(result)
        .build();

    RestAssured.given()
        .port(port)
        .accept(ContentType.JSON)
        .get("/items")
        .then()
        .body(equalTo(toJsonString(response)));

    verify(itemService).getItems();
  }

  @Test
  public void testSetDone() {
    when(itemService.setDone(eq(ITEM_ID))).thenReturn(ITEM);

    SetDoneResponse response = SetDoneResponse.builder()
        .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
        .data(ITEM)
        .build();

    RestAssured.given()
        .port(port)
        .accept(ContentType.JSON)
        .post(String.format("/item/%d/done", ITEM_ID))
        .then()
        .body(equalTo(toJsonString(response)));

    verify(itemService).setDone(eq(ITEM_ID));
  }

  private String toJsonString(Object o) {
    try {
      return objectMapper.writeValueAsString(o).replaceAll("\\\\", "");
    } catch (JsonProcessingException ex) {
      return "";
    }
  }
}
