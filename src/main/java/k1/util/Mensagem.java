package k1.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class Mensagem {

    public static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public static void mensagemInformacao(String mensagem) {
        addMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
    }

    public static void mensagemAlerta(String mensagem) {
        addMessage(FacesMessage.SEVERITY_WARN, mensagem, mensagem);
    }

    public static void mensagemPerigo(String mensagem) {
        addMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
    }
}
