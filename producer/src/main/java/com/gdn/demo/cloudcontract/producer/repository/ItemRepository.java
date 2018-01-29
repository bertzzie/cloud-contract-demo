package com.gdn.demo.cloudcontract.producer.repository;

import com.gdn.demo.cloudcontract.producer.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alex Xandra Albert Sim
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
}
