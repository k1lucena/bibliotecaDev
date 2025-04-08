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
import k1.modelo.Usuario;
import k1.service.UsuarioServico;
import k1.util.Mensagem;

@Named
@ViewScoped
public class ManagerUsuario implements Serializable {

    @EJB
    private UsuarioServico usuarioService;

    private Usuario usuario;
    private List<Usuario> usuarios;

    private Boolean btSalvar;
    private String btNome;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        usuarios = new ArrayList<>();
        carregarParametro();
        pesquisar();
    }

    public void pesquisar() {
        usuarios = usuarioService.findUsuarios(usuario);
    }

    public void carregarParametro() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();

        String visualizar = params.get("visualizar");
        String editar = params.get("editar");

        if (visualizar != null) {
            usuario = usuarioService.find(Long.valueOf(visualizar));
            btSalvar = false;
        } else if (editar != null) {
            usuario = usuarioService.find(Long.valueOf(editar));
            btSalvar = true;
            btNome = "Editar";
        } else {
            btSalvar = true;
            btNome = "Salvar";
        }
    }

    public void salvar() {
        if (usuario.getId() == null) {
            usuarioService.salvar(usuario);
            Mensagem.mensagemInformacao("Usuário salvo com sucesso.");
            init();
        } else {
            usuarioService.atualizar(usuario);
            Mensagem.mensagemInformacao("Usuário atualizado com sucesso.");
        }
        init();
    }

    public void deletar() {
        usuario.setAtivo(false);
        usuarioService.atualizar(usuario);
        Mensagem.mensagemInformacao("Usuário desativado com sucesso.");
        pesquisar();
    }

    // Getters e Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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
}
