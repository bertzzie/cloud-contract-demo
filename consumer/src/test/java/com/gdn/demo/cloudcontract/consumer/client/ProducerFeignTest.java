package com.gdn.demo.cloudcontract.consumer.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.demo.cloudcontract.consumer.model.Item;
import com.gdn.demo.cloudcontract.consumer.model.request.NewItemRequest;
import com.gdn.demo.cloudcontract.consumer.model.response.GetAllItemsResponse;
import com.gdn.demo.cloudcontract.consumer.model.response.NewItemResponse;
import com.gdn.demo.cloudcontract.consumer.model.response.SetDoneResponse;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Alex Xandra Albert Sim
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProducerFeignTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8080);

  @Autowired
  private ProducerFeign feign;

  @Autowired
  private ObjectMapper objectMapper;

  private final Long ITEM_ID = 10L;
  private final String ITEM_CONTENT = "ITEM_CONTENT";
  private final Boolean ITEM_DONE = false;

  private final Item ITEM = Item.builder()
          .id(ITEM_ID)
          .content(ITEM_CONTENT)
          .done(ITEM_DONE)
          .build();

  private final NewItemRequest REQUEST = NewItemRequest.builder()
          .content(ITEM_CONTENT)
          .build();

  @Test
  public void testCreateItem() throws JsonProcessingException {
    stubCreateItem();

    NewItemResponse response = feign.createItem(REQUEST);

    assertEquals(HttpStatus.CREATED.getReasonPhrase().toUpperCase(), response.getStatus());
    assertEquals(ITEM_ID, response.getData().getId());
    assertEquals(ITEM_CONTENT, response.getData().getContent());
    assertEquals(ITEM_DONE, response.getData().getDone());
  }

  @Test
  public void testGetAllItems() throws JsonProcessingException {
    stubGetAllItems();

    GetAllItemsResponse response = feign.getAllItems();

    assertEquals(HttpStatus.OK.getReasonPhrase().toUpperCase(), response.getStatus());
    assertEquals(1, response.getData().size());
    assertEquals(ITEM_ID, response.getData().get(0).getId());
    assertEquals(ITEM_CONTENT, response.getData().get(0).getContent());
    assertEquals(ITEM_DONE, response.getData().get(0).getDone());
  }

  @Test
  public void testSetItemDone() throws JsonProcessingException {
    stubSetItemDone(ITEM_ID);

    SetDoneResponse response = feign.setDone(ITEM_ID);

    assertEquals(HttpStatus.OK.getReasonPhrase().toUpperCase(), response.getStatus());
    assertEquals(ITEM_ID, response.getData().getId());
    assertEquals(ITEM_CONTENT, response.getData().getContent());
    assertEquals(ITEM_DONE, response.getData().getDone());
  }

  private void stubSetItemDone(Long id) throws JsonProcessingException {
    SetDoneResponse response = SetDoneResponse.builder()
            .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
            .data(ITEM)
            .build();

    stubFor(
      post(urlPathEqualTo(String.format("/item/%d/done", id)))
        .withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON_VALUE))
        .willReturn(
          aResponse()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectMapper.writeValueAsString(response))
        )
    );
  }

  private void stubGetAllItems() throws JsonProcessingException {
    GetAllItemsResponse response = GetAllItemsResponse.builder()
            .status(HttpStatus.OK.getReasonPhrase().toUpperCase())
            .data(Collections.singletonList(ITEM))
            .build();

    stubFor(
      get(urlPathEqualTo("/items"))
        .withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON_VALUE))
        .willReturn(
          aResponse()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectMapper.writeValueAsString(response))
        )
    );
  }

  private void stubCreateItem() throws JsonProcessingException {
    NewItemResponse response = NewItemResponse.builder()
            .status(HttpStatus.CREATED.getReasonPhrase().toUpperCase())
            .data(ITEM)
            .build();

    stubFor(
      post(urlPathEqualTo("/item"))
        .withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON_VALUE))
        .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
        .withRequestBody(equalToJson(objectMapper.writeValueAsString(REQUEST)))
        .willReturn(
          aResponse()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectMapper.writeValueAsString(response))
        )
    );
  }
}
