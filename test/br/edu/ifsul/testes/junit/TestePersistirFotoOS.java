package br.edu.ifsul.testes.junit;

import br.edu.ifsul.modelo.Foto;
import br.edu.ifsul.modelo.OrdemServico;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class TestePersistirFotoOS {

    EntityManagerFactory emf;
    EntityManager em;

    public TestePersistirFotoOS() {
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
    public void teste() throws IOException {
        OrdemServico os = em.find(OrdemServico.class, 3);
        Foto foto = new Foto();
        foto.setNomeFoto("mouse_quebrado.jpg");
        foto.setDescricao("Foto do mouse quebrado");
        foto.setOrdemServico(os);
        Path path = Paths.get("C:\\Users\\deces\\Pictures\\prestes.JPG");
        foto.setArquivo(Files.readAllBytes(path));             
        em.getTransaction().begin();
        em.persist(foto);
        em.getTransaction().commit();
    }

}
