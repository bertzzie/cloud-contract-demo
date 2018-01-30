package com.gdn.demo.cloudcontract.consumer.model.response;

import com.gdn.demo.cloudcontract.consumer.model.Item;
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
public class SetDoneResponse {
  private String status;

  private Item data;
}
