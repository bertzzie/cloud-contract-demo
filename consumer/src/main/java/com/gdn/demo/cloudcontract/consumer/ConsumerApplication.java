package com.gdn.demo.cloudcontract.consumer;

import com.gdn.demo.cloudcontract.consumer.properties.ProducerFeignProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author Alex Xandra Albert Sim
 */
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({
    ProducerFeignProperties.class
})
public class ConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }
}
