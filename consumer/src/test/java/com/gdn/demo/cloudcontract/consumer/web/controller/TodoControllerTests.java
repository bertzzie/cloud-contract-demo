package com.gdn.demo.cloudcontract.consumer.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.demo.cloudcontract.consumer.model.Item;
import com.gdn.demo.cloudcontract.consumer.web.service.TodoService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * @author Alex Xandra Albert Sim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTests {

  @LocalServerPort
  private Integer port;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TodoService todoService;

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
    verifyNoMoreInteractions(todoService);
  }

  @Test
  public void testGetItems() {
    List<Item> result = Collections.singletonList(ITEM);
    when(todoService.getItems()).thenReturn(result);

    RestAssured.given()
        .port(port)
        .accept(ContentType.JSON)
        .get("/items")
        .then()
        .body(equalTo(toJsonString(result)));

    verify(todoService).getItems();
  }

  @Test
  public void testCreateItem() {
    List<Item> items = createItems();
    List<String> req = createRequest();
    when(todoService.addItems(req)).thenReturn(items);

    RestAssured.given()
        .port(port)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(req)
        .put("/items")
        .then()
        .body(equalTo(toJsonString(items)));

    verify(todoService).addItems(req);
  }

  private List<String> createRequest() {
    List<String> request = new ArrayList<>();
    request.add("ITEM1");
    request.add("ITEM2");

    return request;
  }

  private List<Item> createItems() {
    Long ITEM1_ID = 10L;
    String ITEM1_CONTENT = "ITEM1";
    Item ITEM1 = Item.builder()
            .id(ITEM1_ID)
            .content(ITEM1_CONTENT)
            .done(DONE_STATUS)
            .build();

    Long ITEM2_ID = 11L;
    String ITEM2_CONTENT = "ITEM2";
    Item ITEM2 = Item.builder()
            .id(ITEM2_ID)
            .content(ITEM2_CONTENT)
            .done(DONE_STATUS)
            .build();

    List<Item> result = new ArrayList<>();
    result.add(ITEM1);
    result.add(ITEM2);

    return result;
  }

  private String toJsonString(Object o) {
    try {
      return objectMapper.writeValueAsString(o).replaceAll("\\\\", "");
    } catch (JsonProcessingException ex) {
      return "";
    }
  }
}
