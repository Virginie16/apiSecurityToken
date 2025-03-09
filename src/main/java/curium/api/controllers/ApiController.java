package curium.api.controllers;

import curium.api.services.ProduitService;
import curium.api.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
	private SiteService siteService;
	private ProduitService produitService;

	@Autowired
	public ApiController(SiteService siteService, ProduitService produitService) {
		this.siteService = siteService;
		this.produitService = produitService;
	}

	@GetMapping("/sites")
	public ResponseEntity<List<String>> getSites() {
		return ResponseEntity.ok(siteService.getAllSites());
	}

	@GetMapping("/produits")
	public ResponseEntity<List<String>> getProduits() {
		return ResponseEntity.ok(produitService.getAllProduits());
	}
}
