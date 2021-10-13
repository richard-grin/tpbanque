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
        databaseName = "banque"
)
@Stateless
public class GestionnaireCompte {

  @PersistenceContext
  private EntityManager em;

  public void creerCompte(CompteBancaire compte) {
    em.persist(compte);
  }

  public List<CompteBancaire> getAllCustomers() {
    TypedQuery query = em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
    return query.getResultList();
  }
}
