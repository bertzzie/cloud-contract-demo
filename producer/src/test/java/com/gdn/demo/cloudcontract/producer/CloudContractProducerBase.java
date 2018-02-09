package com.gdn.demo.cloudcontract.producer;

import com.gdn.demo.cloudcontract.producer.entity.Item;
import com.gdn.demo.cloudcontract.producer.service.ItemService;
import com.gdn.demo.cloudcontract.producer.web.controller.ItemController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;

/**
 * @author Alex Xandra Albert Sim
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class CloudContractProducerBase {

  @Mock
  private ItemService itemService;

  @InjectMocks
  private ItemController itemController;

  private final Item ITEM_CONTENT = new Item(10L, "ITEM_CONTENT", false);
  private final List<Item> items = initItems();

  @Before
  public void setup() {
    given(itemService.getItems()).willReturn(items);
    given(itemService.saveItem(eq("ITEM_CONTENT"))).willReturn(ITEM_CONTENT);

    RestAssuredMockMvc.standaloneSetup(itemController);
  }

  private List<Item> initItems() {
    ArrayList<Item> items = new ArrayList<>();

    items.add(ITEM_CONTENT);
    items.add(new Item(11L, "test", false));
    items.add(new Item(12L, "Monado", false));

    return items;
  }
}
