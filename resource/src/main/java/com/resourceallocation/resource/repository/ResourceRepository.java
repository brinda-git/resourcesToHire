package com.resourceallocation.resource.repository;

import com.resourceallocation.resource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}
