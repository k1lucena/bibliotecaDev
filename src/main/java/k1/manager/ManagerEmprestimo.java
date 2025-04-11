package k1.manager;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import k1.enums.Status;
import k1.modelo.Emprestimo;
import k1.service.EmprestimoServico;
import k1.service.LivroServico;
import k1.util.Mensagem;

@Named
@ViewScoped
public class ManagerEmprestimo implements Serializable {

    @EJB
    private EmprestimoServico emprestimoServico;
    @EJB
    private LivroServico livroServico;
    
    private Emprestimo emprestimo;
    private List<Emprestimo> emprestimos;

    private List<Emprestimo> emprestimosFiltrados;

    private String btNome;

    @PostConstruct
    public void init() {
        emprestimo = new Emprestimo();
        emprestimos = new ArrayList<>();
        carregarParametro();
        pesquisar();
        verificarAtraso();
    }

    public void pesquisar() {
        emprestimos = emprestimoServico.findEmprestimos(emprestimo);
    }

    public void verificarAtraso() {
        pesquisar();
        for (Emprestimo emp : emprestimos) {
            if (emp.getStatus() == Status.EM_ANDAMENTO
                    && emp.getDataPrevistaDevolucao() != null
                    && emp.getDataPrevistaDevolucao().before(Date.from(Instant.now()))) {

                emp.setStatus(Status.ATRASADO);
                emprestimoServico.atualizar(emp);
            }
        }
    }

    public void carregarParametro() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();

        String editar = params.get("editar");

        if (editar != null) {
            emprestimo = emprestimoServico.find(Long.valueOf(editar));
            btNome = "Editar";
        } else {
            btNome = "Salvar";
        }
    }

    public void salvar() {

        if (emprestimo.getId() == null) {
            if (emprestimo.getLivro().getDisponivel()) {
                emprestimo.setDataEmprestimo(LocalDate.now());
                emprestimo.setStatus(Status.EM_ANDAMENTO);
                emprestimo.getLivro().setDisponivel(Boolean.FALSE);
                livroServico.atualizar(emprestimo.getLivro());
                emprestimoServico.salvar(emprestimo);
                Mensagem.mensagemInformacao("Empréstimo realizado com sucesso.");
            } else {
                Mensagem.mensagemAlerta("Livro indisponível para empréstimo.");
                return;
            }
        } else {
            emprestimoServico.atualizar(emprestimo);
            Mensagem.mensagemInformacao("Empréstimo atualizado com sucesso.");
        }

        init();
    }

    public void devolver(Emprestimo e) {
        e.setStatus(Status.CONCLUIDO);
        e.getLivro().setDisponivel(Boolean.TRUE);
        livroServico.atualizar(e.getLivro());
        emprestimoServico.atualizar(e);
        Mensagem.mensagemInformacao("Emprestimo desativado com sucesso.");
        pesquisar();
    }

    public EmprestimoServico getEmprestimoServico() {
        return emprestimoServico;
    }

    public void setEmprestimoServico(EmprestimoServico emprestimoServico) {
        this.emprestimoServico = emprestimoServico;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public String getBtNome() {
        return btNome;
    }

    public void setBtNome(String btNome) {
        this.btNome = btNome;
    }

    public List<Emprestimo> getEmprestimosFiltrados() {
        return emprestimosFiltrados;
    }

    public void setEmprestimosFiltrados(List<Emprestimo> emprestimosFiltrados) {
        this.emprestimosFiltrados = emprestimosFiltrados;
    }

    public Status[] getStatusDisponiveis() {
        return Status.values();
    }

}
