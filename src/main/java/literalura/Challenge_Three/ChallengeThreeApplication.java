package literalura.Challenge_Three;

import literalura.Challenge_Three.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeThreeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeThreeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.mostrarMenu();
	}
}
