package k1.conversor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import k1.modelo.Livro;
import k1.service.LivroServico;

@Named
@ApplicationScoped
@FacesConverter(value = "livroConverter", managed = true)
public class LivroConversor implements Converter<Livro> {

    @Inject
    private LivroServico livroServico;

    public Livro getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long id = Long.valueOf(value);
            return livroServico.find(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getAsString(FacesContext context, UIComponent component, Livro livro) {
       return (livro != null && livro.getId() != null) ? livro.getId().toString() : "";
    }

}
