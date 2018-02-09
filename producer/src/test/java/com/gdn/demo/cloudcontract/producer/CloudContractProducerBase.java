package com.gdn.demo.cloudcontract.producer;

import com.gdn.demo.cloudcontract.producer.web.controller.ItemController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Alex Xandra Albert Sim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = ProducerApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class CloudContractProducerBase {
  @InjectMocks
  private ItemController itemController;

  @Before
  public void setup() {
    RestAssuredMockMvc.standaloneSetup(itemController);
  }
}
