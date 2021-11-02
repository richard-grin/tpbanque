package fr.grin.tpbanque.jsf;

import fr.grin.tpbanque.ejb.GestionnaireCompte;
import fr.grin.tpbanque.entities.CompteBancaire;
import fr.grin.tpbanque.entities.OperationBancaire;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 * Backing bean pour la page JSF operations.xhtml.
 *
 * @author grin
 */
@Named(value = "listeOperations")
@RequestScoped
public class ListeOperations implements Serializable {

  @EJB
  private GestionnaireCompte gestionnaireCompte;

  private Long idCompteBancaire;
  private CompteBancaire compte;

  public ListeOperations() {
  }

  public Long getIdCompteBancaire() {
    return idCompteBancaire;
  }

  public void setIdCompteBancaire(Long idCompteBancaire) {
    this.idCompteBancaire = idCompteBancaire;
    System.out.println("**** idCompteBancaire = " + this.idCompteBancaire);
  }

  public List<OperationBancaire> getOperations() {
    return compte.getOperations();
  }

  public void setCompte() {
    compte = gestionnaireCompte.findById(idCompteBancaire);
  }

  public CompteBancaire getCompte() {
    return compte;
  }

}
