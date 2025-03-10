package curium.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import curium.api.controllers.LotController;
import curium.api.models.Biocharge;
import curium.api.services.BiochargeService;
import curium.api.services.ValidationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
//import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LotControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	private String token;

	@Autowired
	private LotController lotController;

	@MockitoBean
	private ValidationService validationService;

	@MockitoBean
	private BiochargeService biochargeService;

	@BeforeEach
	void initToken() throws Exception {
		//1️⃣ Authentifier l'utilisateur pour obtenir un token
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
		token = JsonPath.read(responseJson, "$.accessToken");
	}

	@Test
	void contextLoads() {
		assertThat(lotController).isNotNull();
	}

	//Test de validation échouée
	@Test
	void shouldReturnBadRequestWhenValidationFails() throws Exception {
		// Mock du service de validation pour simuler une erreur
		Mockito.when(validationService.validerParamètres( "invalidSite", "produit"))
				.thenReturn(ResponseEntity.badRequest().body(Collections.singletonList("Invalid site or product")));

		// Effectuer l'appel GET
		mockMvc.perform(MockMvcRequestBuilders.get("/api/lot/biocharge").header(HttpHeaders.AUTHORIZATION, "Bearer " +token)
						.param("site", "invalidSite")
						.param("produit", "produit")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$[0]").value("Invalid site or product"));
	}
	//Test de données non trouvées
	@Test
	void shouldReturnNotFoundWhenNoBiochargesFound() throws Exception {
		// Mock de la validation
		Mockito.when(validationService.validerParamètres("validSite", "validProduit")).thenReturn(null);

		// Mock du service pour simuler une liste vide
		Mockito.when(biochargeService.getLotsFiltres("validSite", "validProduit"))
				.thenReturn(Collections.emptyList());

		// Effectuer l'appel GET
		mockMvc.perform(MockMvcRequestBuilders.get("/api/lot/biocharge").header(HttpHeaders.AUTHORIZATION, "Bearer " +token)
						.param("site", "validSite")
						.param("produit", "validProduit")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$[0]").value("No lots found for the provided site and product"));
	}

	//Test de réussite avec des données fictives
//	@Test
//	void shouldReturnBiochargesWhenValidRequest() throws Exception {
//		// Mock de la validation
//		Mockito.when(validationService.validerParamètres("validSite", "validProduit")).thenReturn(null);
//
//		// Mock du service pour renvoyer des données
//		List<Biocharge> biocharges = Arrays.asList(new Biocharge("Lot1"), new Biocharge("Lot2"));
//		Mockito.when(biochargeService.getLotsFiltres("validSite", "validProduit"))
//				.thenReturn(biocharges);
//
//		// Effectuer l'appel GET
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/lot/biocharge")
//						.param("site", "validSite")
//						.param("produit", "validProduit")
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].name").value("Lot1"))
//				.andExpect(jsonPath("$[1].name").value("Lot2"));
//	}



}
