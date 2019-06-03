package br.edu.ifsul.testes.junit;

import br.edu.ifsul.modelo.Cidade;
import br.edu.ifsul.modelo.PessoaFisica;
import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jorge
 */
public class TestePersistirPessoaFisica {
    
    EntityManagerFactory emf;
    EntityManager em;
    
    public TestePersistirPessoaFisica() {
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
        PessoaFisica obj = new PessoaFisica();
        obj.setEmail("joao@gmail.com");
        obj.setNome("joao");
        obj.setNomeUsuario("teste");
        obj.setSenha("123456");
        obj.setTelefoneAlternativo("(54)99987-4584");
        obj.setTelefonePrincipal("(54)99876-4956");
        obj.setCpf("568.679.120-10");
        obj.setRg("8584934587");
        obj.setNascimento(Calendar.getInstance());
        obj.setEndereco("Av Brasil");
        obj.setNumero("15");
        obj.setReferencia("Perto posto ipiranga");
        obj.setCep("99854-000");
        obj.setBairro("Centro");
        obj.setDataCadastro(Calendar.getInstance());
        obj.setCidade(em.find(Cidade.class, 1));
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();        
    }
    
}
