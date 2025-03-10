package curium.api.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "change_control")
public class ChangeControl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "avancement")
    private String avancement;

    @Column(name = "date_lancement")
    private LocalDate dateLancement;

    @Column(name = "reference")
    private String reference;

    @Column(name = "titre")
    private String titre;

    @Column(name = "site")
    private String site;

    @Column(name = "produit")
    private String produit;

    @Column(name = "type_cc")
    private String typeCc;

    @Column(name = "classification")
    private String classification;

    @Column(name = "date_cloture")
    private LocalDate dateCloture;

    @ManyToOne
    @JoinColumn(name = "site", referencedColumnName = "trigramme_site", insertable = false, updatable = false)
    private Site siteEntity;

    private String message;

    public ChangeControl(String message) {
        this.message = message;
    }

    // Getter et Setter pour message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Constructeur par défaut
    public ChangeControl() {}

    public ChangeControl(UUID id, String avancement, LocalDate dateLancement, String reference, String titre, String site, String produit, String typeCc, String classification, LocalDate dateCloture) {
        this.id = id;
        this.avancement = avancement;
        this.dateLancement = dateLancement;
        this.reference = reference;
        this.titre = titre;
        this.site = site;
        this.produit = produit;
        this.typeCc = typeCc;
        this.classification = classification;
        this.dateCloture = dateCloture;
    }

    // Getters et Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getAvancement() { return avancement; }
    public void setAvancement(String avancement) { this.avancement = avancement; }

    public LocalDate getDateLancement() { return dateLancement; }
    public void setDateLancement(LocalDate dateLancement) { this.dateLancement = dateLancement; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    public String getProduit() { return produit; }
    public void setProduit(String produit) { this.produit = produit; }

    public String getTypeCc() { return typeCc; }
    public void setTypeCc(String typeCc) { this.typeCc = typeCc; }

    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }

    public LocalDate getDateCloture() { return dateCloture; }
    public void setDateCloture(LocalDate dateCloture) { this.dateCloture = dateCloture; }
}

