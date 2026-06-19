package me.nebu.minehost_webhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class MinehostWebhookApplication {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		System.out.println("Loading application");

		if (args.length == 0) {
			System.err.println("The work directory must be provided.");
			return;
		}

		File workDir = new File(args[0]);
		if (!workDir.exists()) {
			System.err.println("The provided work directory does not exist.");
			return;
		}

		File signature = new File(workDir, "secret");
		if (!signature.exists()) {
			System.err.println("The secret file was not found. Please create a 'secret' file and paste the token used for the Github webhook.");
			return;
		}

		boolean status = Signature.load(signature);

		if (!status) {
			System.err.println("An error occurred while attempting to load signature.");
			return;
		}

		SpringApplication.run(MinehostWebhookApplication.class, args);

		System.out.println("Finished loading in " + (System.currentTimeMillis() - time) + "ms. Listening for webhook.");
	}

}
