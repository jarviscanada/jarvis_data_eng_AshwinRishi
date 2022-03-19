package ca.jrvs.apps.Twitter.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(value = "ca.jrvs.apps.Twitter")
public class TwitterCliAppSpringBoot implements CommandLineRunner {

	private TwitterCliApp app;

	@Autowired
	public TwitterCliAppSpringBoot(TwitterCliApp app) {
		this.app = app;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TwitterCliAppSpringBoot.class);

		app.setWebApplicationType(WebApplicationType.NONE);
		try {
			app.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(String... args) throws Exception {
		app.run(args);
	}
}
