package literalura.Challenge_Three.modelo;

public enum Idioma {
    INGLES("en","inglés"),
    FRANCES("fr","francés"),
    FINLANDES("fi","finlandés"),
    ESPANOL("es","español"),
    PORTUGUES("pt","portugués"),
    ITALIANO("it","italiano"),
    ALEMAN("de","alemán"),
    RUSO("ru","ruso");

    private String idiomaJSON;
    private String idiomaBuscar;

    Idioma(String idiomaJSON,String idiomaBuscar){
        this.idiomaJSON=idiomaJSON;
        this.idiomaBuscar=idiomaBuscar;
    }

    public static Idioma fromString(String texto) {
        for (Idioma i : Idioma.values()) {
            if (i.idiomaJSON.equalsIgnoreCase(texto)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + texto);
    }

    public static Idioma fromSpanish(String texto) {
        for (Idioma i : Idioma.values()) {
            if (i.idiomaBuscar.equalsIgnoreCase(texto)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + texto);
    }
}
