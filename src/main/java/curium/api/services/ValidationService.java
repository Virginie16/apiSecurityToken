package curium.api.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ValidationService {

	private SiteService siteService;
	private ProduitService produitService;

	public ValidationService(SiteService siteService, ProduitService produitService) {
		this.siteService = siteService;
		this.produitService = produitService;
	}

	public ResponseEntity<Object> validerParamètres(String site, String produit) {
		// Vérifier si les paramètres sont valides
		if (site.isEmpty() || produit.isEmpty()) {
			return ResponseEntity.badRequest()
					.body(Collections.singletonList("Invalid site or product"));
		}

		// Vérifier si le site est conforme
		if (!siteService.isSiteConforme(site)) {
			return ResponseEntity.badRequest()
					.body(Collections.singletonList("Invalid site: not found in the database"));
		}

		// Vérifier si le produit est conforme
		if (!produitService.isProduitConforme(produit)) {
			return ResponseEntity.badRequest()
					.body(Collections.singletonList("Invalid produit: not found in the database"));
		}

		return null; // Tout est valide
	}

}
