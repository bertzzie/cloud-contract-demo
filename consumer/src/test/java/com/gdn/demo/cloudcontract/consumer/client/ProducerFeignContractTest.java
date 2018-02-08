package com.gdn.demo.cloudcontract.consumer.client;

import com.gdn.demo.cloudcontract.consumer.model.request.NewItemRequest;
import com.gdn.demo.cloudcontract.consumer.model.response.GetAllItemsResponse;
import com.gdn.demo.cloudcontract.consumer.model.response.NewItemResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Alex Xandra Albert Sim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(workOffline = true, ids = "com.gdn.demo.cloudcontract:contract:+:stubs:8080")
@DirtiesContext
public class ProducerFeignContractTest {

  @Autowired
  private ProducerFeign feign;

  private final Long ITEM_ID = 10L;
  private final String ITEM_CONTENT = "ITEM_CONTENT";
  private final Boolean ITEM_DONE = false;

  private final NewItemRequest REQUEST = NewItemRequest.builder()
          .content(ITEM_CONTENT)
          .build();

  @Test
  public void testCreateItem() {
    NewItemResponse response = feign.createItem(REQUEST);

    assertEquals(HttpStatus.CREATED.getReasonPhrase().toUpperCase(), response.getStatus());
    assertEquals(ITEM_ID, response.getData().getId());
    assertEquals(ITEM_CONTENT, response.getData().getContent());
    assertEquals(ITEM_DONE, response.getData().getDone());
  }

  @Test
  public void testGetAllItems() {
    GetAllItemsResponse response = feign.getAllItems();

    assertEquals(HttpStatus.OK.getReasonPhrase().toUpperCase(), response.getStatus());
    assertEquals(3, response.getData().size());
    assertEquals(ITEM_ID, response.getData().get(0).getId());
    assertEquals(ITEM_CONTENT, response.getData().get(0).getContent());
    assertEquals(ITEM_DONE, response.getData().get(0).getDone());
  }
}
