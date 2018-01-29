package com.gdn.demo.cloudcontract.producer.web.model.request;

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
public class NewItemRequest {
  private String content;
}
