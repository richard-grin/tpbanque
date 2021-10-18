package fr.grin.tpbanque.jsf;

import fr.grin.tpbanque.ejb.GestionnaireCompte;
import fr.grin.tpbanque.entities.CompteBancaire;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 * Backing bean pour la page transfert.xhtml.
 *
 * @author grin
 */
@Named(value = "transfert")
@RequestScoped
public class Transfert {

  @EJB
  private GestionnaireCompte gestionnaireCompte;

  private long idSource;
  private long idDestination;
  private int montant;

  public long getIdSource() {
    return idSource;
  }

  public void setIdSource(long id) {
    this.idSource = id;
  }

  public long getIdDestination() {
    return idDestination;
  }

  public void setIdDestination(long id) {
    this.idDestination = id;
  }

  public int getMontant() {
    return montant;
  }

  public void setMontant(int montant) {
    this.montant = montant;
  }

  public String enregistrer() {
    boolean erreur = false;
    
    CompteBancaire source = gestionnaireCompte.findCompte(idSource);
    if (source == null) {
      // Message d'erreur associé au composant source ; form:source est l'id client
      // si l'id du formulaire est "form" et l'id du champ de saisie de l'id de la source est "source"
      // dans la page JSF qui lance le transfert.
      Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:source");
      erreur = true;
    } else {
      if (source.getSolde() < montant) {
        Util.messageErreur("Solde de " + source.getNom() + " insuffisant", "Solde insuffisant", "form:montant");
        erreur = true;
      }
    }

    CompteBancaire destination = gestionnaireCompte.findCompte(idDestination);
    if (destination == null) {
      // Message d'erreur associé au composant source ; form:source est l'id client
      // si l'id du formulaire est "form" et l'id du champ de saisie de l'id de la source est "source"
      // dans la page JSF qui lance le transfert.
      Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:destination");
      erreur = true;
    }
    
    if (erreur) {
      return null;
    }
    
    // Pas d'erreur de saisie
    gestionnaireCompte.transferer(source, destination, montant);
    Util.addFlashInfoMessage("Transfert de " + source.getNom() + " vers " 
            + destination.getNom() 
            + " pour un montant de " + montant + " correctement effectué");
    return "listeComptes?faces-redirect=true";
  }
}
