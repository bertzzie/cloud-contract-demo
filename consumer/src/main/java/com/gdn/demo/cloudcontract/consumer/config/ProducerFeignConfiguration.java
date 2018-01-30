package com.gdn.demo.cloudcontract.consumer.config;

import com.gdn.demo.cloudcontract.consumer.properties.ProducerFeignProperties;
import feign.Logger;
import feign.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alex Xandra Albert Sim
 */
@Configuration
public class ProducerFeignConfiguration {
  @Autowired
  private ProducerFeignProperties properties;

  @Bean
  public Request.Options options() {
      return new Request.Options(
          (int) properties.getTimeUnit().toMillis(properties.getConnectTimeout()),
          (int) properties.getTimeUnit().toMillis(properties.getReadTimeout())
      );
  }

  @Bean
  public Logger.Level level() {
      return properties.getLevel();
  }
}
