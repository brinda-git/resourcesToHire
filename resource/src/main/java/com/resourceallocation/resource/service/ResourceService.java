package com.resourceallocation.resource.service;

import com.resourceallocation.resource.model.Resource;

import java.util.List;

public interface ResourceService {
    //req own methods
    public String createResource(Resource resource);
    public String updateResource(Resource resource);
    public String deleteResource(Integer resourceid);
    public Resource getResource(Integer resourceid);
    public List<Resource> getAllResources(); //to get all resources or fetch all dat so no args

}
