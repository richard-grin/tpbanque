package fr.grin.tpbanque.jsf;

import fr.grin.tpbanque.ejb.GestionnaireCompte;
import fr.grin.tpbanque.entities.CompteBancaire;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * Backing bean pour la page mouvement.xhtml.
 * @author grin
 */
@Named(value = "mouvement")
@ViewScoped
public class Mouvement implements Serializable {

  /**
   * Creates a new instance of Mouvement
   */
  public Mouvement() {
  }

  @EJB
  private GestionnaireCompte gestionnaireCompte;

  private Long id;
  private CompteBancaire compte;
  private String typeMouvement;
  private int montant;

  public int getMontant() {
    return montant;
  }

  public void setMontant(int montant) {
    this.montant = montant;
  }

  public String getTypeMouvement() {
    return typeMouvement;
  }

  public void setTypeMouvement(String typeMouvement) {
    this.typeMouvement = typeMouvement;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CompteBancaire getCompte() {
    return compte;
  }

  public void loadCompte() {
    compte = gestionnaireCompte.findById(id);
  }

  // La méthode doit avoir cette signature.
  public void validateSolde(FacesContext fc, UIComponent composant, Object valeur) {
    UIInput composantTypeMouvement = (UIInput) composant.findComponent("typeMouvement");
    // Sans entrer dans les détails, il faut parfois utiliser
    // getSubmittedValue() à la place de getLocalValue.
    // typeMouvement n'a pas encore reçu de valeur tant que la validation n'est pas finie.
    String valeurTypeMouvement = (String) composantTypeMouvement.getLocalValue();
    if (valeurTypeMouvement.equals("retrait")) {
      int retrait = (int) valeur;
      if (compte.getSolde() < retrait) {
        FacesMessage message
                = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Le retrait doit être inférieur au solde du compte",
                        "Le retrait doit être inférieur au solde du compte");
        throw new ValidatorException(message);
      }
    }
  }

  public String enregistrerMouvement() {
    if (typeMouvement.equals("ajout")) {
      gestionnaireCompte.deposer(compte, montant);
    } else {
      gestionnaireCompte.retirer(compte, montant);
    }
    Util.addFlashInfoMessage("Mouvement enregistré sur compte de " + compte.getNom());
    return "listeComptes?faces-redirect=true";
  }

}
