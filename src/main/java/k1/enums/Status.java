package k1.enums;


public enum Status {
    EM_ANDAMENTO, CONCLUIDO, ATRASADO;
    
        public String getLabel() {
        switch (this) {
            case EM_ANDAMENTO:
                return "Em andamento";
            case CONCLUIDO:
                return "Concluído";
            case ATRASADO:
                return "Atrasado";
            default:
                return this.name();
        }
    }
}
