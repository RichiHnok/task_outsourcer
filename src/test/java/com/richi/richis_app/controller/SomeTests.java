package com.richi.richis_app.controller;

// import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.richi.richis_app.entity.User;

// @WebMvcTest(controllers = TaskSampleEditorController.class)
@ComponentScan(basePackages = {"com.richi.richis_app"})
@SpringBootTest
@AutoConfigureMockMvc
public class SomeTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void taskSamplesEditorPage() throws Exception{
		mockMvc.perform(get("/editor/taskSamples")).andExpect(status().isOk());
	}

	@Test
	public void usersEditorPage() throws Exception{
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("currentUser", new User("Test", "Testovich"));

		mockMvc.perform(MockMvcRequestBuilders.get("/editor/users").sessionAttrs(sessionattr))
          .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void startPage() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	public void editorPage() throws Exception{
		mockMvc.perform(get("/editor")).andExpect(status().isOk());
	}

	@Test
	public void taskToProcEditorPage() throws Exception{
		mockMvc.perform(get("/editor/tasksToProc")).andExpect(status().isOk());
	}
}
