package k1.manager;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import k1.modelo.Autor;
import k1.modelo.Livro;
import k1.service.LivroServico;
import k1.util.Mensagem;

@Named
@ViewScoped
public class ManagerLivro implements Serializable {
    
    @EJB
    private LivroServico livroServico;
    
    private Livro livro;
    private List<Livro> livros;
    
    private Boolean btSalvar;
    private String btNome;
    
    private List<Autor> autoresSelecionados;
    
    @PostConstruct
    public void init(){
        livro = new Livro();
        livros = new ArrayList<>();
        autoresSelecionados = new ArrayList<>();
        carregarParametro();
        pesquisar();
    }
    
    public void pesquisar(){
        livros = livroServico.findLivros(livro);
    }
    
    public void carregarParametro(){
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        
        String visualizar = params.get("visualizar");
        String editar = params.get("editar");
        
        if (visualizar != null) {
            livro = livroServico.find(Long.valueOf(visualizar));
            btSalvar = false;
        } else if (editar != null) {
            livro = livroServico.find(Long.valueOf(editar));
            autoresSelecionados = new ArrayList<>(livro.getAutor());
            btSalvar = true;
            btNome = "Editar";
        } else {
            btSalvar = true;
            btNome = "Salvar";
        }
    }
    
    public void salvar(){
        if (livro.getId() == null) {
            livro.getAutor().addAll(autoresSelecionados);
            livroServico.salvar(livro);
            Mensagem.mensagemInformacao("Autor salvo com sucesso.");
            init();
        } else {
            livro.getAutor().clear();
            livro.getAutor().addAll(autoresSelecionados);
            livroServico.atualizar(livro);
            Mensagem.mensagemInformacao("Usuário atualizado com sucesso.");
        }
        init();
    }
    
    public void deletar() {
        livro.setAtivo(false);
        livroServico.atualizar(livro);
        Mensagem.mensagemInformacao("Usuário desativado com sucesso.");
        pesquisar();
    }

    public LivroServico getLivroServico() {
        return livroServico;
    }

    public void setLivroServico(LivroServico livroServico) {
        this.livroServico = livroServico;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public Boolean getBtSalvar() {
        return btSalvar;
    }

    public void setBtSalvar(Boolean btSalvar) {
        this.btSalvar = btSalvar;
    }

    public String getBtNome() {
        return btNome;
    }

    public void setBtNome(String btNome) {
        this.btNome = btNome;
    }

    public List<Autor> getAutoresSelecionados() {
        return autoresSelecionados;
    }

    public void setAutoresSelecionados(List<Autor> autoresSelecionados) {
        this.autoresSelecionados = autoresSelecionados;
    }
    
    

    
    
}
