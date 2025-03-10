package curium.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import curium.api.services.ProduitService;
import curium.api.services.SiteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import curium.api.controllers.ApiController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.naming.ServiceUnavailableException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiApplicationTests {

	@MockitoBean
	private SiteService siteService;
	@MockitoBean
	private ProduitService produitService;

	@Autowired
	private ApiController controller;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	private String token;

	@Test
	void contextLoads() throws Exception {
		assert controller != null;
	}

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
	public void testGetSites() throws Exception {

		// 1️⃣ Définir le comportement du mock
		List<String> fakeSites = List.of("Site1", "Site2", "Site3");
		Mockito.when(siteService.getAllSites()).thenReturn(fakeSites);

		// 2️⃣ Exécuter la requête GET et vérifier la réponse
		mockMvc.perform(get("/api/sites").header(HttpHeaders.AUTHORIZATION, "Bearer " +token))
				.andExpect(status().isOk()) // Vérifie que le statut est 200
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(3)) // Vérifie qu'il y a 3 sites
				.andExpect(jsonPath("$[0]").value("Site1")) // Vérifie le premier élément
				.andExpect(jsonPath("$[1]").value("Site2"))
				.andExpect(jsonPath("$[2]").value("Site3"));
	}
	@Test
	public void testGetProduits() throws Exception {

		// 1️⃣ Définir le comportement du mock
		List<String> fakeProduits = List.of("Produit1", "Produit2", "Produit3");
		Mockito.when(produitService.getAllProduits()).thenReturn(fakeProduits);

		// 2️⃣ Exécuter la requête GET et vérifier la réponse
		mockMvc.perform(get("/api/produits").header(HttpHeaders.AUTHORIZATION, "Bearer " +token))
				.andExpect(status().isOk()) // Vérifie que le statut est 200
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(3)) // Vérifie qu'il y a 3 produits
				.andExpect(jsonPath("$[0]").value("Produit1")) // Vérifie le premier élément
				.andExpect(jsonPath("$[1]").value("Produit2"))
				.andExpect(jsonPath("$[2]").value("Produit3"));
	}
	@Test
	public void testGetProduitsEmpty() throws Exception {
		// 1️⃣ Simuler un service qui retourne une liste vide
		Mockito.when(produitService.getAllProduits()).thenReturn(Collections.emptyList());

		// 2️⃣ Effectuer la requête GET et vérifier la réponse
		mockMvc.perform(get("/api/produits").header(HttpHeaders.AUTHORIZATION, "Bearer " +token))
				.andExpect(status().isOk()) // Vérifie que le statut est 200
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(0)); // Vérifie qu'il n'y a pas de produits
	}
	@Test
	public void testGetSitesEmpty() throws Exception {
		// 1️⃣ Simuler un service qui retourne une liste vide
		Mockito.when(siteService.getAllSites()).thenReturn(Collections.emptyList());

		// 2️⃣ Effectuer la requête GET et vérifier la réponse
		mockMvc.perform(get("/api/sites").header(HttpHeaders.AUTHORIZATION, "Bearer " +token))
				.andExpect(status().isOk()) // Vérifie que le statut est 200
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(0)); // Vérifie qu'il n'y a pas de sites
	}








}


