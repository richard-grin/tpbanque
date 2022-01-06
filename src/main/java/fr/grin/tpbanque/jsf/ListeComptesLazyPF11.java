package fr.grin.tpbanque.jsf;

import fr.grin.tpbanque.entities.CompteBancaire;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.JpaLazyDataModel;
import org.primefaces.model.LazyDataModel;

/**
 * Backing bean pour la page qui liste tous les comptes.
 * Pour PrimeFaces version 11, avec utilisation de JpaLazyDataModel.
 *
 * @author grin
 */
@Named
@ViewScoped
public class ListeComptesLazyPF11 implements Serializable {
  
  @PersistenceContext
  private EntityManager em;

  private LazyDataModel<CompteBancaire> model;

  public ListeComptesLazyPF11() {
  }

  @PostConstruct
  public void init() {
    this.model = new JpaLazyDataModel<>(CompteBancaire.class, () -> em, "id");
  }

  public LazyDataModel<CompteBancaire> getModel() {
    return model;
  }

}
