package literalura.Challenge_Three.modelo;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer numeroDeDescargas;
    @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo=datosLibro.titulo();
        this.idioma=Idioma.fromString(datosLibro.idioma().get(0));
        this.numeroDeDescargas=datosLibro.numeroDeDescargas();
    }

    public static Autor evaluarAutor(List<DatosAutor> datosAutor){
        Optional<DatosAutor> autor = datosAutor.stream()
                .filter(a->a.nacimiento()!=null)
                .findFirst();
        return autor.map(Autor::new).orElse(null);
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
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
