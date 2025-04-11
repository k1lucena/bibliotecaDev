package k1.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import k1.modelo.Emprestimo;

@Stateless
public class EmprestimoServico {

    @PersistenceContext
    EntityManager em;
    
    public void salvar(Emprestimo emprestimo){
        em.persist(emprestimo);
    }
    
    public void atualizar(Emprestimo emprestimo) {
        em.merge(emprestimo);
        em.flush();
    }
    
    public List<Emprestimo> findEmprestimos(){
        String jpql = "SELECT e FROM Emprestimo e";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }
    
    public List<Emprestimo> findEmprestimos(Emprestimo emprestimo) {
        String jpql = "SELECT e FROM Emprestimo e";
        
        if (emprestimo.getStatus() != null && !emprestimo.getId().equals(null)){
            jpql += " WHERE e.id = :id";
        }
        
        Query query = em.createQuery(jpql);
        
        if (emprestimo.getStatus() != null && !emprestimo.getId().equals(null)) {
            query.setParameter("id", emprestimo.getId());
        }
        
        return query.getResultList();
    }
    
    
    public Emprestimo find(Long id){
        return em.find(Emprestimo.class, id);
    }
    
    
}
