package k1.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import k1.modelo.Usuario;

@Stateless
public class UsuarioServico {
    @PersistenceContext
    EntityManager em;
    
    public void salvar(Usuario usuario) {
        em.persist(usuario);
    }
        
    public void atualizar(Usuario usuario) {
        em.merge(usuario);
        em.flush();
    }
    
    public List<Usuario> findUsuarios() {
        String jpql = "SELECT u FROM Usuario u WHERE u.ativo = true ORDER BY u.nome";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }
 
    public List<Usuario> findUsuarios(Usuario usuario) {
        String jpql = "SELECT u FROM Usuario u WHERE u.ativo = true";
        
        if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
            jpql += " and u.nome LIKE :nome";
        }
        
        Query query = em.createQuery(jpql);
        
        if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
            query.setParameter("nome", "%" + usuario.getNome() + "%");
        }
        
        return query.getResultList();
    }
    
    public Usuario find(Long id) {
        return em.find(Usuario.class, id);
    }
}
