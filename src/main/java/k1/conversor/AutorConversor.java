package k1.conversor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import k1.modelo.Autor;
import k1.service.AutorServico;

@Named
@ApplicationScoped
@FacesConverter(value = "autorConverter", managed = true)
public class AutorConversor implements Converter<Autor>{

    @Inject
    private AutorServico autorServico;

    public Autor getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long id = Long.valueOf(value);
            return autorServico.find(id); // usa o serviço para buscar o autor
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getAsString(FacesContext context, UIComponent component, Autor autor) {
        return (autor != null && autor.getId() != null) ? autor.getId().toString() : "";
    }
}