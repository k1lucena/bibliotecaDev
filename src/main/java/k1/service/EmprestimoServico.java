package k1.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import k1.enums.Status;
import k1.modelo.Emprestimo;

@Stateless
public class EmprestimoServico {

    @PersistenceContext
    EntityManager em;

    public void salvar(Emprestimo emprestimo) {
        em.persist(emprestimo);
    }

    public void atualizar(Emprestimo emprestimo) {
        em.merge(emprestimo);
        em.flush();
    }

    public List<Emprestimo> findEmprestimos() {
           String jpql = "SELECT e FROM Emprestimo e "
            + "ORDER BY CASE e.status "
            + "WHEN k1.model.Status.EM_ANDAMENTO THEN 0 "
            + "WHEN k1.model.Status.ATRASADO THEN 1 "
            + "ELSE 2 END";

        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    public List<Emprestimo> findEmprestimos(Emprestimo emprestimo) {
        String jpql = "SELECT e FROM Emprestimo e";
        if (emprestimo.getId() != null) {
            jpql += " WHERE e.id = :id";
        }
        
        jpql += " ORDER BY CASE WHEN e.status = :statusEmAndamento THEN 0 "
                + "WHEN e.status = :statusAtrasado THEN 1 "
                + "ELSE 2 END, "
                + "e.dataDevolucao DESC NULLS LAST,"
                + "e.dataEmprestimo DESC";
        
        Query query = em.createQuery(jpql);
        query.setParameter("statusEmAndamento", Status.EM_ANDAMENTO);
        query.setParameter("statusAtrasado", Status.ATRASADO);
        if (emprestimo.getId() != null) {
            query.setParameter("id", emprestimo.getId());
        }
        return query.getResultList();
    }

    public Emprestimo find(Long id) {
        return em.find(Emprestimo.class, id);
    }

}
