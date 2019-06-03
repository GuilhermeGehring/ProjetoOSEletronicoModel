
package br.edu.ifsul.testes.junit;

import br.edu.ifsul.modelo.Estado;
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
public class TestePersistirEstado {
    
    EntityManagerFactory emf;
    EntityManager em;
    
    public TestePersistirEstado() {
    }
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("OSEletronicosModelPU");
        em = emf.createEntityManager();        
    }
    
    @After
    public void tearDown() {
        em.close();
        emf.close();
    }
    
    @Test
    public void teste(){
        
        Estado e = new Estado();//nesse momento o e está no estado: New
        e.setNome("Santa Catarina");
        e.setUf("SC");
        try{
        
            em.getTransaction().begin();
            em.persist(e);//o status do objeto e passa para: Managed
            em.getTransaction().commit();
        }catch(Exception ex){
            
            ex.printStackTrace();
        }
        
        
    }
    
}
