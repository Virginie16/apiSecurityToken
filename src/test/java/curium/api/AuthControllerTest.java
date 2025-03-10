package curium.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testLoginSuccess() throws Exception {
		String requestBody = "{\"username\": \"admin\", \"password\": \"admin\"}";

		mockMvc.perform(post("/api/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.accessToken").exists());
	}
	@Test
	public void testAccessProtectedEndpointWithoutToken() throws Exception {
		mockMvc.perform(get("/api/sites"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void testAccessProtectedEndpointWithToken() throws Exception {



		// 1️⃣ Authentifier l'utilisateur pour obtenir un token
		String requestBody = objectMapper.writeValueAsString(Map.of(
				"username", "admin",
				"password", "admin"
		));

		MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andReturn();

		// 2️⃣ Extraire le token de la réponse JSON
		String responseJson = loginResult.getResponse().getContentAsString();
		String token = JsonPath.read(responseJson, "$.accessToken"); // Adapte si nécessaire

		// 3️⃣ Utiliser le token pour appeler l’endpoint sécurisé
		mockMvc.perform(get("/api/sites")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(status().isOk());
	}
}
