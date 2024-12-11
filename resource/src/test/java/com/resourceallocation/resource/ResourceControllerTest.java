package com.resourceallocation.resource;

import com.resourceallocation.resource.controller.ResourceController;
import com.resourceallocation.resource.model.Resource;
import com.resourceallocation.resource.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)  // JUnit 5 extension to support Mockito
public class ResourceControllerTest {

	@Mock
	private ResourceService resourceService;

	@InjectMocks
	private ResourceController resourceController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(resourceController).build();
	}

	@Test
	public void testGetAllResources() throws Exception {
		// Mock service response
		List<Resource> resources = Arrays.asList(
				new Resource(1, "Dennis", "4", "java,spring,jms,mysql,angular,react,web services,nodejs"),
				new Resource(2, "Thompson", "7", "java,oracle,react,angular,javascript,restapi")
		);

		when(resourceService.getAllResources()).thenReturn(resources);

		// Perform the GET request and verify the response
		mockMvc.perform(get("/resources"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(resources.size()))
				.andExpect(jsonPath("$[0].resourcename").value("Dennis"));
	}

	@Test
	public void testGetResourceById() throws Exception {
		// Mock service response
		Resource resource = new Resource(1, "Dennis", "4", "java,spring,jms,mysql,angular,react,web services,nodejs");

		when(resourceService.getAllResources()).thenReturn(Arrays.asList(resource));

		// Perform GET request to fetch resource by ID and verify
		mockMvc.perform(get("/resources/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.resourcename").value("Dennis"))
				.andExpect(jsonPath("$.experience").value("4"));
	}

	@Test
	public void testGetResourceNamesBySkills() throws Exception {
		// Mock skills input
		String skills = "java,spring";
		List<String> resourceNames = Arrays.asList("Dennis", "Thompson");

		// Mock service response
		when(resourceService.getAllResources()).thenReturn(Arrays.asList(
				new Resource(1, "Dennis", "4", "java,spring,jms,mysql,angular"),
				new Resource(2, "Thompson", "7", "java,oracle,react,angular,javascript")
		));

		// Perform GET request with skills parameter and check response
		mockMvc.perform(get("/resources/names")
						.param("skills", skills)
						.param("experience", "10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value("Dennis"))
				.andExpect(jsonPath("$[1]").value("Thompson"));
	}

	@Test
	public void testCreateResource() throws Exception {
		// Sample resource to be created
		Resource resource = new Resource(7, "John", "5", "java,spring,react");

		// Perform POST request to create the resource
		mockMvc.perform(post("/resources")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"resourceid\":7,\"resourcename\":\"John\",\"experience\":\"5\",\"skills\":\"java,spring,react\"}"))
				.andExpect(status().isOk())
				.andExpect(content().string("Resource created successfully"));

		// Verify that the resourceService's createResource method is called
		verify(resourceService, times(1)).createResource(any(Resource.class));
	}

	@Test
	public void testUpdateResource() throws Exception {
		// Sample resource to update
		Resource resource = new Resource(7, "John", "5", "java,spring,react");

		// Perform PUT request to update the resource
		mockMvc.perform(put("/resources")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"resourceid\":7,\"resourcename\":\"John\",\"experience\":\"5\",\"skills\":\"java,spring,react\"}"))
				.andExpect(status().isOk())
				.andExpect(content().string("Resource updated successfully"));

		// Verify that the resourceService's updateResource method is called
		verify(resourceService, times(1)).updateResource(any(Resource.class));
	}

	@Test
	public void testDeleteResource() throws Exception {
		// Perform DELETE request to delete a resource by ID
		mockMvc.perform(delete("/resources/7"))
				.andExpect(status().isOk())
				.andExpect(content().string("Resource deleted successfully"));

		// Verify that the resourceService's deleteResource method is called
		verify(resourceService, times(1)).deleteResource(7);
	}
}
