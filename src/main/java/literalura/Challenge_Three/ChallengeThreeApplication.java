package literalura.Challenge_Three;

import literalura.Challenge_Three.principal.Principal;
import literalura.Challenge_Three.repositorio.AutorRepositorio;
import literalura.Challenge_Three.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeThreeApplication implements CommandLineRunner {

	@Autowired
	private AutorRepositorio autorRepositorio;

	@Autowired
	private LibroRepositorio libroRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeThreeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepositorio,libroRepositorio);
		principal.mostrarMenu();
	}
}
