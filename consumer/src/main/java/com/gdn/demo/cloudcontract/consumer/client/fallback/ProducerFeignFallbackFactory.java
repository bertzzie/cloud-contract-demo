package com.gdn.demo.cloudcontract.consumer.client.fallback;

import com.gdn.demo.cloudcontract.consumer.client.ProducerFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alex Xandra Albert Sim
 */
@Component
public class ProducerFeignFallbackFactory implements FallbackFactory<ProducerFeign> {
  @Autowired
  private ProducerFeignFallback fallback;

  @Override
  public ProducerFeign create(Throwable throwable) {
    return fallback;
  }
}
