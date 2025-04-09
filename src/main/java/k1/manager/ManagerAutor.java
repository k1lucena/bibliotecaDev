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
import k1.service.AutorServico;
import k1.util.Mensagem;

@Named
@ViewScoped
public class ManagerAutor implements Serializable {

    @EJB
    private AutorServico autorServico;
    
    private Autor autor;
    private List<Autor> autores;
    
    private Boolean btSalvar;
    private String btNome;
    
    private List<Autor> autoresSelecionados;
    
    @PostConstruct
    public void init(){
        autor = new Autor();
        autores = new ArrayList<>();
        autoresSelecionados = new ArrayList<>();
        carregarParametro();
        pesquisar();
    }
    
    public void pesquisar(){
        autores = autorServico.findAutor(autor);
    }
    
    public void carregarParametro(){
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        
        String visualizar = params.get("visualizar");
        String editar = params.get("editar");
        
        if (visualizar != null){
            autor = autorServico.find(Long.valueOf(visualizar));
            btSalvar = false;
        } else if (editar != null) {
            autor = autorServico.find(Long.valueOf(editar));
            btSalvar = true;
            btNome = "Editar";
        } else {
            btSalvar = true;
            btNome = "Salvar";
        }
    }
    
    
    public void salvar(){
        if (autor.getId() == null){
            autorServico.salvar(autor);
            Mensagem.mensagemInformacao("Autor salvo com sucesso.");
            init();
        } else {
            autorServico.atualizar(autor);
            Mensagem.mensagemInformacao("Usuário atualizado com sucesso.");
        }
        init();
    }
    
    public void deletar() {
        autor.setAtivo(false);
        autorServico.atualizar(autor);
        Mensagem.mensagemInformacao("Usuário desativado com sucesso.");
        pesquisar();
    }

    public AutorServico getAutorServico() {
        return autorServico;
    }

    public void setAutorServico(AutorServico autorServico) {
        this.autorServico = autorServico;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
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
