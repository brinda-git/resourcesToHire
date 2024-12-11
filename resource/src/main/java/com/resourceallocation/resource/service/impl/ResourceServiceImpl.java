package com.resourceallocation.resource.service.impl;

import com.resourceallocation.resource.model.Resource;
import com.resourceallocation.resource.repository.ResourceRepository;
import com.resourceallocation.resource.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {



    //to talk to the db repo layer only does
    ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }


    @Override
    public String createResource(Resource resource) {
        //to save Resource object to db
        //more business logic
        Resource savedResource = resourceRepository.save(resource);
        return "Success";
    }

    @Override
    public String updateResource(Resource resource) {
        //updating in db
        //more b logic
        Resource updatedResource = resourceRepository.save(resource);
        return "Update success";
    }

    @Override
    public String deleteResource(Integer resourceid) {
        //more b logic
        resourceRepository.deleteById(resourceid);
        return "Succeeded";
    }

    @Override
    public Resource getResource(Integer resourceid) {
        //more b logic
        return resourceRepository.findById(resourceid).get();
    }





    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
}
