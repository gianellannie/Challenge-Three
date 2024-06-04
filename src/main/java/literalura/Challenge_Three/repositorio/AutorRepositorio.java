package literalura.Challenge_Three.repositorio;

import literalura.Challenge_Three.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepositorio extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombre(String nombre);
    Optional<List<Autor>> findByFallecimientoGreaterThanAndNacimientoLessThanEqual(Integer fecha1,Integer fecha2);
}
