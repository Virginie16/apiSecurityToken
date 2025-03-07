package curium.api.controllers;

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

	@Autowired
	public ApiController(SiteService siteService) {
		this.siteService = siteService;
	}

	@GetMapping("/sites")
	public ResponseEntity<List<String>> getSites() {
		return ResponseEntity.ok(siteService.getAllSites());
	}
}
