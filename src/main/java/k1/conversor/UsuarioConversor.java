package k1.conversor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import k1.modelo.Usuario;
import k1.service.UsuarioServico;

@Named
@ApplicationScoped
@FacesConverter(value="usuarioConverter", managed = true)
public class UsuarioConversor implements Converter<Usuario>{

    
    @Inject
    private UsuarioServico usuarioServico;

    public Usuario getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long id = Long.valueOf(value);
            return usuarioServico.find(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getAsString(FacesContext context, UIComponent component, Usuario usuario) {
        return (usuario != null && usuario.getId() != null) ? usuario.getId().toString() : "";
    }
    
}
