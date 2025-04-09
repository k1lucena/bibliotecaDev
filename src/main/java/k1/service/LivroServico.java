package k1.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import k1.modelo.Autor;
import k1.modelo.Livro;

@Stateless
public class LivroServico {

    @PersistenceContext
    EntityManager em;

    public void salvar(Livro livro) {
        em.persist(livro);
    }

    public void atualizar(Livro livro) {
        em.merge(livro);
        em.flush();
    }

    public List<Livro> findLivros() {
        String jpql = "SELECT l FROM Livro l";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    public List<Livro> findLivros(Livro livro) {
        String jpql = "SELECT DISTINCT l FROM Livro l JOIN FETCH l.autor a WHERE l.ativo = true AND a.ativo = true";

        if (livro.getTitulo() != null && !livro.getTitulo().isEmpty()) {
            jpql += " and l.titulo LIKE :titulo";
        }
        

        Query query = em.createQuery(jpql);

        if (livro.getTitulo() != null && !livro.getTitulo().isEmpty()) {
            query.setParameter("titulo", "%" + livro.getTitulo() + "%");
        }

        return query.getResultList();
    }



    
    public Livro find(Long id) {
        return em.find(Livro.class, id);
    }

}
