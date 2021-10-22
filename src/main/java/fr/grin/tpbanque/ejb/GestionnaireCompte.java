package fr.grin.tpbanque.ejb;

import fr.grin.tpbanque.entities.CompteBancaire;
import java.util.List;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Gère les comptes bancaires.
 *
 * @author grin
 */
@DataSourceDefinition(
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        name = "java:app/jdbc/banque",
        serverName = "localhost",
        portNumber = 3306,
        user = "root", // nom et
        password = "ric088", // mot de passe que vous avez donnés lors de la création de la base de données
        databaseName = "banque",
        properties = {
          // "zeroDateTimeBehavior=CONVERT_TO_NULL",
          "useSSL=false"
        }
)
@Stateless
public class GestionnaireCompte {

  @PersistenceContext
  private EntityManager em;

  public void creerCompte(CompteBancaire compte) {
    em.persist(compte);
  }

  public List<CompteBancaire> getAllComptes() {
    TypedQuery query = 
            em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
    return query.getResultList();
  }

  public long nbComptes() {
    TypedQuery<Long> query = 
            em.createQuery("select count(c) from CompteBancaire c", Long.class);
    return query.getSingleResult();
  }

  public void transferer(CompteBancaire source, CompteBancaire destination,
          int montant) {
    source.retirer(montant);
    destination.deposer(montant);
    update(source);
    update(destination);
  }

  public CompteBancaire update(CompteBancaire compteBancaire) {
    return em.merge(compteBancaire);
  }
  
  public CompteBancaire findById(long id) {
    return em.find(CompteBancaire.class, id);
  }
  
      /**
     * Dépôt d'argent sur un compte bancaire.
     * @param compteBancaire
     * @param montant 
     */
    public void deposer(CompteBancaire compteBancaire, int montant) {
      compteBancaire.deposer(montant);
      update(compteBancaire);
    }
    
    /**
     * Retrait d'argent sur un compte bancaire.
     * @param compteBancaire
     * @param montant 
     */
    public void retirer(CompteBancaire compteBancaire, int montant) {
      compteBancaire.retirer(montant);
      update(compteBancaire);
    }
  
}
