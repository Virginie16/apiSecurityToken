package curium.api.services;

import curium.api.models.Site;
import curium.api.repositories.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {

	@Autowired
	private SiteRepository siteRepository;

	// Vérifie si le site existe dans la BDD
	public boolean isSiteConforme(String site) {
		return siteRepository.existsByNomSite(site); // Hypothèse : méthode personnalisée dans le repository
	}

	public List<String> getAllSites() {
		return siteRepository.findAll()
				.stream()
				.map(Site::getNomSite)
				.collect(Collectors.toList());
	}
}
