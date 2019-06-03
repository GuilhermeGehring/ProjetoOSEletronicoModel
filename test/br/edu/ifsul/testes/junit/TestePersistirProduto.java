
package br.edu.ifsul.testes.junit;

import br.edu.ifsul.modelo.Marca;
import br.edu.ifsul.modelo.Produto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Telmo
 */
public class TestePersistirProduto {
    
    EntityManagerFactory emf;
    EntityManager em;
    
    @Before
    public void before(){
        
        emf = Persistence.createEntityManagerFactory("OSEletronicosModelPU");
        em = emf.createEntityManager();    
        
    }
    
    @After
    public void after(){
        em.close();
        emf.close();
    }
    
    @Test
    public void test(){
        
        Produto obj = new Produto();
        obj.setNome("Mouse Laser HP");
        obj.setDescricao("Mouse HP com alta precisão");
        obj.setPreco(120.00);
        obj.setMarca(em.find(Marca.class, 1));
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();   
    }
    
}
