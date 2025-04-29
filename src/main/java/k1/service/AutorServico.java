package k1.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import k1.modelo.Autor;

@Stateless
public class AutorServico {

    @PersistenceContext
    EntityManager em;
    
    public void salvar(Autor autor){
        em.persist(autor);
    }
    
    public void atualizar(Autor autor) {
        em.merge(autor);
        em.flush();
    }
    
    public List<Autor> findAutor(){
        String jpql = "SELECT a FROM Autor a WHERE a.ativo = true ORDER BY a.nome";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }
    
    public List<Autor> findAutor(Autor autor){
        String jpql = "SELECT a FROM Autor a WHERE a.ativo = true";
        if (autor.getNome() != null && !autor.getNome().isEmpty()){
            jpql += " AND a.nome Like :nome";
        }
        
        Query query = em.createQuery(jpql);
        
        if (autor.getNome() != null && !autor.getNome().isEmpty()){
            query.setParameter("nome", "%" + autor.getNome() + "%");
        }
        
        return query.getResultList();
    }
    
    public Autor find(Long id){
        return em.find(Autor.class, id);
    }
    
}
