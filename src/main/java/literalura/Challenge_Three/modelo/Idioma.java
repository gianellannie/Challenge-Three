package literalura.Challenge_Three.modelo;

public enum Idioma {
    INGLES("en"),
    FRANCES("fr"),
    FINLANDES("fi"),
    ESPANOL("es"),
    PORTUGUES("pt"),
    ITALIANO("it"),
    ALEMAN("de"),
    RUSO("ru");

    private String idioma;

    Idioma(String idioma){
        this.idioma=idioma;
    }

    public static Idioma fromString(String texto) {
        for (Idioma i : Idioma.values()) {
            if (i.idioma.equalsIgnoreCase(texto)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + texto);
    }
}
