package curium.api.controllers;

import curium.api.models.Biocharge;
import curium.api.models.ChangeControl;
import curium.api.services.BiochargeService;
import curium.api.services.ChangeControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lot")
public class LotController {

    @Autowired
    private BiochargeService biochargeService;

    @Autowired
    private ChangeControlService changeControlService;

    @GetMapping("/biocharge")
    public ResponseEntity<List<Biocharge>> getLotsFiltrés(
            @RequestParam(name = "site", required = false) String site,
            @RequestParam(name = "produit", required = false) String produit) {

        List<Biocharge> biocharges = biochargeService.getLotsFiltrés(site, produit);
        return ResponseEntity.ok(biocharges);
    }

    @GetMapping("/change-control")
    public ResponseEntity<List<ChangeControl>> getLotsFiltresChange(
            @RequestParam(name = "site", required = false) String site,
            @RequestParam(name = "produit", required = false) String produit) {

        List<ChangeControl> changeControls = changeControlService.getLotsFiltresChange(site, produit);
        return ResponseEntity.ok(changeControls);
    }
}
