package com.gdn.demo.cloudcontract.producer.web.model.response;

import com.gdn.demo.cloudcontract.producer.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Alex Xandra Albert Sim
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllItemsResponse {
  private String status;

  private List<Item> data;
}
