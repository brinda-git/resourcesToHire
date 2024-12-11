package com.resourceallocation.resource.controller;

import com.resourceallocation.resource.model.Resource;
import com.resourceallocation.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    private static final List<Resource> resources = new ArrayList<>();

    static {
        resources.add(new Resource(1, "Dennis", "4", "java,spring,jms,mysql,angular,react,web services,nodejs"));
        resources.add(new Resource(2, "Thompson", "7", "java,oracle,react,angular,javascript,restapi"));
        resources.add(new Resource(3, "Kim", "12", "java,jsp,spring,oracle,mysql,postgresql,mongo,restapi,web services,docker,redis"));
        resources.add(new Resource(4, "Aisha", "9", "angular,javascript,nodejs,restapi,web services,docker,sql server,postgresql"));
        resources.add(new Resource(5, "Maya", "5", "spring,spring boot,hibernate,mysql,postgresql,nodejs"));
        resources.add(new Resource(6, "Kumar", "3", "java,redis,mysql,javascript"));
    }

    @GetMapping("/names/{skills}")
    public List<String> getResourceNamesBySkills(
            @PathVariable String skills) {

        // Split the skills from the path parameter (comma-separated string) into a List
        List<String> ski = Arrays.asList(skills.split(","));

        // Filter resources where all skills match
        return resources.stream()
                .filter(u -> u.getSkills() != null &&
                        ski.stream().allMatch(skill -> Arrays.asList(u.getSkills().split(",")).contains(skill))) // Check if all skills match
                .map(Resource::getResourcename) // Map to resource name
                .collect(Collectors.toList());
    }

    @GetMapping("/names/skills-experience")
    public List<String> getResourceNamesWithSkillsAndExperience() {
        List<String> requiredSkills = Arrays.asList("mysql", "docker", "spring", "react");
        int experienceLimit = 10;

        // Filter resources where skills match all required skills and experience is less than 10 years
        return resources.stream()
                .filter(u -> u.getSkills() != null &&
                        requiredSkills.stream().allMatch(skill -> Arrays.asList(u.getSkills().split(",")).contains(skill)) &&  // All required skills should be in the resource
                        Integer.parseInt(u.getExperience()) < experienceLimit)  // Experience less than 10 years
                .map(Resource::getResourcename)  // Map to resource name
                .collect(Collectors.toList());
    }

    @GetMapping("/names")
    public List<String> getResourceNamesBySkillsAndExperience(
            @RequestParam String skills,
            @RequestParam int experience) {

        // Convert the comma-separated skills from the query parameter to a list
        List<String> desiredSkills = Arrays.asList(skills.split(","));

        // Filter resources based on skills and experience
        return resources.stream()
                .filter(u -> u.getSkills() != null &&
                        desiredSkills.stream().allMatch(skill -> Arrays.asList(u.getSkills().split(",")).contains(skill)) &&  // Check if all desired skills are in the resource's skills
                        Integer.parseInt(u.getExperience()) <= experience)  // Check if the resource's experience is less than or equal to the provided value
                .map(Resource::getResourcename)  // Map to resource name
                .collect(Collectors.toList());
    }

    @GetMapping("{resourceid}")
    public Resource getResourceById(@PathVariable int resourceid) {
        return resources.stream()
                .filter(r -> r.getResourceid() == resourceid)
                .findFirst()
                .orElse(null);  // Return null if not found
    }

    @GetMapping()
    public List<Resource> getAllResources() {
        return resourceService.getAllResources();
    }

    @PostMapping
    public String createResource(@RequestBody Resource resource) {
        resourceService.createResource(resource);
        return "Resource created successfully";
    }

    @PutMapping
    public String updateResource(@RequestBody Resource resource) {
        resourceService.updateResource(resource);
        return "Resource updated successfully";
    }

    @DeleteMapping("{resourceid}")
    public String deleteResource(@PathVariable int resourceid) {
        resourceService.deleteResource(resourceid);
        return "Resource deleted successfully";
    }
}
