package literalura.Challenge_Three.modelo;

import java.util.Objects;

public class Autor {
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

    public Autor(DatosAutor datosAutor) {
        this.nombre=datosAutor.nombre();
        this.nacimiento=datosAutor.nacimiento();
        this.fallecimiento=evaluarFecha(datosAutor.fallecimiento());
    }

    private Integer evaluarFecha(Integer fecha){
        return Objects.nonNull(fecha) ? fecha : 0;
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
