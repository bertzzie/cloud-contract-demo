package com.gdn.demo.cloudcontract.consumer.model;

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
public class Item {
  private Long id;

  private String content;

  private Boolean done;
}
