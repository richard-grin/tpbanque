package fr.grin.tpbanque.jsf;

import fr.grin.tpbanque.ejb.GestionnaireCompte;
import fr.grin.tpbanque.entities.CompteBancaire;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;

/**
 * Backing bean pour la page qui liste tous les comptes.
 * @author grin
 */
@Named
@ViewScoped
public class ListeComptes implements Serializable {

  @EJB
  private GestionnaireCompte gestionnaireCompte;
  
  private List<CompteBancaire> listeComptes;

  /**
   * Creates a new instance of GestionComptesBean
   */
  public ListeComptes() {
  }
  
  public List<CompteBancaire> getAllComptes() {
    if (listeComptes == null) {
      listeComptes = gestionnaireCompte.getAllComptes();
    }
    return listeComptes;
  }
  
   /**
   * Retourne si la ligne doit être sélectionnée. Elle le sera si la valeur de
   * son solde est supérieure ou égale à la valeur saisie dans le filtre.
   *
   * @param valeurColonne
   * @param valeurFiltre
   * @param locale
   * @return true si la ligne est sélectionnée.
   */
  public boolean filterBySolde(Object valeurColonne, Object valeurFiltre, Locale locale) {
    String valeurFiltreString = (String) valeurFiltre;
    if (valeurFiltreString.equals("")) {
      return true;
    }
    try {
      return (Integer) valeurColonne >= Integer.valueOf(valeurFiltreString);
    } catch (NumberFormatException e) {
      // On ne fait pas de sélection si le filtre ne contient pas un nombre
      return true;
    }
  }
  
  public String supprimerCompte(CompteBancaire compte) {
    gestionnaireCompte.supprimer(compte);
    // Reste sur la même page
    return null;
  }
  
}
