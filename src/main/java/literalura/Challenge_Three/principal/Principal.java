package literalura.Challenge_Three.principal;

import literalura.Challenge_Three.modelo.Autor;
import literalura.Challenge_Three.modelo.Datos;
import literalura.Challenge_Three.modelo.DatosLibro;
import literalura.Challenge_Three.modelo.Libro;
import literalura.Challenge_Three.servicio.ConsumirApi;
import literalura.Challenge_Three.servicio.ConvertirDatos;

import java.util.*;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private List<Libro> listaDeLibros = new ArrayList<>();
    private List<Autor> listaDeAutores = new ArrayList<>();
    private ConsumirApi consumir = new ConsumirApi();
    private ConvertirDatos convertir = new ConvertirDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    public void mostrarMenu(){
        int opcion = -1;
        while (opcion != 0){
            String menu = """
                    1. Buscar libro por su título
                    2. Mostrar libros buscados
                    3. Mostrar autores
                    4. Buscar autores vivos en un determinado año
                    
                    0. Salir
                    """;
            System.out.print(menu);
            System.out.print("Ingresar opción: ");
            opcion = lectura.nextInt();
            lectura.nextLine();
            switch (opcion){
                case 0:
                    System.out.println(">> Salió exitosamente.");
                    break;
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    buscarAutoresVivos();
                    break;
            }
        }

    }

    private void buscarLibro(){
        System.out.print("Ingresar el título del libro: ");
        String titulo = lectura.nextLine();
        String json = consumir.obtenerDatos(URL_BASE+"?search="+titulo.replace(" ","+"));
        Datos datos = convertir.convertirDatos(json, Datos.class);
        Optional<DatosLibro> datosLibro = datos.libro().stream()
                .findFirst();
        if(datosLibro.isPresent()){
            System.out.println(">> Libro encontrado:");
            Libro libro = new Libro(datosLibro.get());
            listaDeLibros.add(libro);
            listaDeAutores.add(libro.getAutor());
            System.out.println(libro);
        }else{
            System.out.println(">> Libro no encontrado.");
        }
    }

    private void mostrarLibros(){
        if (!listaDeLibros.isEmpty()){
            listaDeLibros.forEach(System.out::println);
        }else {
            System.out.println(">> Aún no se han buscado libros.");
        }
    }

    private void mostrarAutores(){
        if (!listaDeAutores.isEmpty()){
            listaDeAutores.forEach(System.out::println);
        }else {
            System.out.println(">> Aún no se han buscado libros.");
        }
    }

    private void buscarAutoresVivos(){
        System.out.print("Ingresar año: ");
        Integer fecha = lectura.nextInt();
        lectura.nextLine();
        listaDeAutores.stream()
                .filter(a->a.getFallecimiento()>=fecha||a.getFallecimiento()==0)
                .forEach(System.out::println);
    }
}
