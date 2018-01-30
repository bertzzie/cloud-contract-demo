package com.gdn.demo.cloudcontract.consumer.properties;

import feign.Logger;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * @author Alex Xandra Albert Sim
 */
@Data
@ConfigurationProperties("feign.producer")
public class ProducerFeignProperties {

  private Long connectTimeout = 2000L;

  private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

  private Long readTimeout = 2000L;

  private Logger.Level level = Logger.Level.BASIC;
}
