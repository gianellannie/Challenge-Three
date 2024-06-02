package literalura.Challenge_Three.modelo;

import java.util.List;
import java.util.Optional;

public class Libro {
    private String titulo;
    private Idioma idioma;
    private Integer numeroDeDescargas;
    private Autor autor;

    public Libro(DatosLibro datosLibro) {
        this.titulo=datosLibro.titulo();
        this.idioma=Idioma.fromString(datosLibro.idioma().get(0));
        this.numeroDeDescargas=datosLibro.numeroDeDescargas();
        this.autor=evaluarAutor(datosLibro.autor());
    }

    private Autor evaluarAutor(List<DatosAutor> datosAutor){
        Optional<DatosAutor> autor = datosAutor.stream()
                .filter(a->a.nacimiento()!=null)
                .findFirst();
        return autor.map(Autor::new).orElse(null);
    }

    public String getTitulo() {
        return titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo +
                ", Idioma: " + idioma +
                ", Número de descargas: " + numeroDeDescargas +
                ", Datos del autor (" + autor + ")";
    }
}
