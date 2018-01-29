package com.gdn.demo.cloudcontract.producer.web.model.response;

import com.gdn.demo.cloudcontract.producer.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex Xandra Albert Sim
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewItemResponse {
  private String status;

  private Item data;
}
