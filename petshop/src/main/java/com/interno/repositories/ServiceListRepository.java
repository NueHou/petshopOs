package com.interno.repositories;

import com.interno.domains.ServiceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceListRepository extends JpaRepository<ServiceList, Integer> {
}
