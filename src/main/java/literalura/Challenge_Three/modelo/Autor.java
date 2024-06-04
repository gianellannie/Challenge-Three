package literalura.Challenge_Three.modelo;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre=datosAutor.nombre();
        this.nacimiento=datosAutor.nacimiento();
        this.fallecimiento=Objects.nonNull(datosAutor.fallecimiento()) ? datosAutor.fallecimiento() : 0;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre +
                ", Año de nacimiento: " + nacimiento +
                ", Año de fallecimiento: " + fallecimiento;
    }
}
