package controller;

/** Resultado explícito de una operación que modifica el catálogo. */
public record ResultadoOperacion(EstadoOperacion estado, String mensaje) {
    public boolean fueExitosa() {
        return estado == EstadoOperacion.EXITO;
    }
}
