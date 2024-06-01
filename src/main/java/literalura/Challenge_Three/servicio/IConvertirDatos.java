package literalura.Challenge_Three.servicio;

public interface IConvertirDatos {
    <T> T convertirDatos(String json,Class<T> clase);
}
