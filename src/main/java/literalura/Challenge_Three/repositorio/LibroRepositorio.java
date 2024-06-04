package literalura.Challenge_Three.repositorio;

import literalura.Challenge_Three.modelo.Idioma;
import literalura.Challenge_Three.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepositorio extends JpaRepository<Libro,Long> {
    Optional<List<Libro>> findByIdioma(Idioma idioma);
}
