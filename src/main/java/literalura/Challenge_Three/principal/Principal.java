package literalura.Challenge_Three.principal;

import literalura.Challenge_Three.modelo.*;
import literalura.Challenge_Three.repositorio.AutorRepositorio;
import literalura.Challenge_Three.repositorio.LibroRepositorio;
import literalura.Challenge_Three.servicio.ConsumirApi;
import literalura.Challenge_Three.servicio.ConvertirDatos;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private List<Libro> listaDeLibros = new ArrayList<>();
    private List<Autor> listaDeAutores = new ArrayList<>();
    private ConsumirApi consumir = new ConsumirApi();
    private ConvertirDatos convertir = new ConvertirDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private AutorRepositorio autorRepositorio;
    private LibroRepositorio libroRepositorio;

    public Principal(
            AutorRepositorio autorRepositorio,
            LibroRepositorio libroRepositorio
    ) {
        this.autorRepositorio = autorRepositorio;
        this.libroRepositorio = libroRepositorio;
    }

    public void mostrarMenu(){
        int opcion = -1;
        while (opcion != 0){
            String menu = """
                    ********************* MENÚ *********************
                    1. Buscar libro por su título
                    2. Mostrar libros buscados
                    3. Mostrar autores
                    4. Buscar autores vivos en un determinado año
                    5. Cantidad de libros en un determinado idioma
                    6. Estadísticas de las descargas
                    7. Top 10 libros más descargados
                    8. Buscar autor por su nombre
                    0. Salir
                    ************************************************
                    """;
            System.out.print(menu);
            System.out.print("Ingresar opción: ");
            opcion = lectura.nextInt();
            lectura.nextLine();
            System.out.println();
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
                case 5:
                    cantidadLibros();
                    break;
                case 6:
                    estadisticas();
                    break;
                case 7:
                    mostrarTop();
                    break;
                case 8:
                    buscarAutor();
                    break;
            }
            System.out.println();
        }

    }

    private void listaLibrosBD(){
        listaDeLibros = libroRepositorio.findAll();
    }

    private void listaAutoresBD(){
        listaDeAutores = autorRepositorio.findAll();
    }

    private void buscarLibro(){
        listaLibrosBD();
        System.out.print("Ingresar el título del libro: ");
        String titulo = lectura.nextLine();
        String json = consumir.obtenerDatos(URL_BASE+"?search="+titulo.replace(" ","+"));
        Datos datos = convertir.convertirDatos(json, Datos.class);
        Optional<DatosLibro> datosLibro = datos.libro().stream()
                .findFirst();
        if(datosLibro.isPresent()){
            System.out.println("\n>> Libro encontrado:");
            Autor autor = Libro.evaluarAutor(datosLibro.get().autor());
            Libro libro = new Libro(datosLibro.get());
            Optional<Libro> buscarLibro = listaDeLibros.stream()
                    .filter(l->l.getTitulo().contains(libro.getTitulo()))
                    .findFirst();
            if (buscarLibro.isEmpty()){
                Optional<Autor> buscarAutor = autorRepositorio.findByNombre(autor.getNombre());
                Autor autorBD;
                if (buscarAutor.isPresent()){
                    autorBD = buscarAutor.get();
                }else{
                    autorBD = autorRepositorio.save(autor);
                }
                libro.setAutor(autorBD);
                libroRepositorio.save(libro);
            }
            libro.setAutor(autor);
            System.out.println(libro);
        }else{
            System.out.println("\n>> Libro no encontrado.");
        }
    }

    private void mostrarLibros(){
        listaLibrosBD();
        System.out.println(">> Lista de libros registrados:");
        mostrar(listaDeLibros);
    }

    private void mostrarAutores(){
        listaAutoresBD();
        System.out.println(">> Lista de autores registrados:");
        mostrar(listaDeAutores);
    }

    private void buscarAutoresVivos(){
        int fechaHoy = LocalDate.now().getYear();
        Integer fecha;
        do{
            System.out.print("Ingresar año: ");
            fecha = lectura.nextInt();
        }while(fecha>fechaHoy||fecha<-600);
        lectura.nextLine();
        Optional<List<Autor>> buscarAutores = autorRepositorio.findByFallecimientoGreaterThanAndNacimientoLessThanEqual(fecha,fecha);
        if(buscarAutores.isPresent()){
            List<Autor> autoresBD = buscarAutores.get();
            autoresBD.forEach(System.out::println);
            if(autoresBD.isEmpty()){
                System.out.println("\n>> No hay autores vivos en el año "+fecha+".");
            }
        }
    }

    private void cantidadLibros() {
        System.out.print("Ingresar idioma: ");
        Idioma buscarIdioma = Idioma.fromSpanish(lectura.nextLine());
        Optional<List<Libro>> buscarLibros = libroRepositorio.findByIdioma(buscarIdioma);
        if (buscarLibros.isPresent()){
            List<Libro> librosBD = buscarLibros.get();
            int cantidad = librosBD.size();
            if(cantidad!=0){
                String texto = (cantidad==1)?" libro registrado":" libros registrados";
                System.out.println("\n>> Hay "+cantidad+texto+":");
                librosBD.forEach(System.out::println);
            }else{
                System.out.println("\n>> Hay 0 libros registrados.");
            }
        }
    }

    private void  estadisticas(){
        listaLibrosBD();
        DoubleSummaryStatistics estadistica = listaDeLibros.stream()
                .filter(l->l.getNumeroDeDescargas()>0)
                .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));
        System.out.println(">> Media de las descargas es: " + estadistica.getAverage());
        System.out.println(">> Libro más descargado tiene: " + (int) estadistica.getMax());
        System.out.println(">> Libro menos descargado tiene: " + (int) estadistica.getMin());
    }

    private void mostrarTop() {
        List<Libro> librosBD = libroRepositorio.findTop10ByOrderByNumeroDeDescargasDesc();
        System.out.println(">> Top 10 libros más descargados:");
        mostrar(librosBD);
    }

    private void buscarAutor(){
        listaAutoresBD();
        System.out.print("Ingresar el nombre del autor: ");
        String nombre = lectura.nextLine();
        Optional<Autor> buscarAutor = listaDeAutores.stream()
                .filter(a->a.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .findFirst();
        if(buscarAutor.isPresent()){
            System.out.println("\n>> Autor encontrado:");
            System.out.println(buscarAutor.get());
        }else{
            System.out.println("\n>> Autor no encontrado.");
        }
    }

    private <T> void mostrar(List<T> lista){
        if (!lista.isEmpty()){
            lista.forEach(System.out::println);
        }else {
            System.out.println("\n>> Aún no se han buscado libros.");
        }
    }
}
