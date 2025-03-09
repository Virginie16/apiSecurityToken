package curium.api.services;

import curium.api.models.Produit;
import curium.api.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public List<String> getAllProduits() {
        return produitRepository.findAll()
                .stream()
                .map(Produit::getNomProduit)
                .collect(Collectors.toList());
    }
}