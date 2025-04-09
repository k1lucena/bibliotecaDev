package k1.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import k1.enums.Status;

@Entity
public class Emprestimo implements Serializable {
    
    @Id
    @GeneratedValue(generator = "seq_emprestimo", strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Usuario usuario;
    
    @OneToOne
    private Livro livro;
    
    private LocalDate dataEmprestimo;

    @Temporal(TemporalType.DATE)    
    private Date dataPrevistaDevolucao;

    private LocalDate dataDevolucao;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    public Emprestimo() {
    }

    public Emprestimo(Long id, Usuario usuario, Livro livro, LocalDate dataEmprestimo, Date dataPrevistaDevolucao, LocalDate dataDevolucao, Status status) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(Date dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.usuario);
        hash = 59 * hash + Objects.hashCode(this.livro);
        hash = 59 * hash + Objects.hashCode(this.dataEmprestimo);
        hash = 59 * hash + Objects.hashCode(this.dataPrevistaDevolucao);
        hash = 59 * hash + Objects.hashCode(this.dataDevolucao);
        hash = 59 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Emprestimo other = (Emprestimo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.livro, other.livro)) {
            return false;
        }
        if (!Objects.equals(this.dataEmprestimo, other.dataEmprestimo)) {
            return false;
        }
        if (!Objects.equals(this.dataPrevistaDevolucao, other.dataPrevistaDevolucao)) {
            return false;
        }
        if (!Objects.equals(this.dataDevolucao, other.dataDevolucao)) {
            return false;
        }
        return this.status == other.status;
    }

    @Override
    public String toString() {
        return "Emprestimo{" + "id=" + id + ", \nusuario=" + usuario.getNome() + ", \nlivro=" + livro.getTitulo() + "\nisbn=" + livro.getIsbn() + ", \ndataEmprestimo=" + dataEmprestimo + ", \ndataPrevistaDevolucao=" + dataPrevistaDevolucao + ", \ndataDevolucao=" + dataDevolucao + ", \nstatus=" + status + '}' + "\n-=-=-=-=-=-\n";
    }

   
    
}
