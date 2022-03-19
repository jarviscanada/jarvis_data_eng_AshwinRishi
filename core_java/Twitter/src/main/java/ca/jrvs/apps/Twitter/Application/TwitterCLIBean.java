package ca.jrvs.apps.Twitter.Application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import ca.jrvs.apps.Twitter.Service.Service;
import ca.jrvs.apps.Twitter.Service.ServiceImpl;
import ca.jrvs.apps.Twitter.controller.Controller;
import ca.jrvs.apps.Twitter.controller.ControllerImpl;
import ca.jrvs.apps.Twitter.dao.CrdDao;
import ca.jrvs.apps.Twitter.dao.TwitterDao;
import ca.jrvs.apps.Twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.Twitter.dao.helper.TwitterHttpHelper;

@Component
public class TwitterCLIBean {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(TwitterCLIBean.class);
		TwitterCliApp app = context.getBean(TwitterCliApp.class);
		app.run(args);
	}

	@Bean
	public TwitterCliApp TwitterCliApp(Controller controller) {
		return new TwitterCliApp(controller);
	}

	@Bean
	public Controller controller(Service service) {
		return new ControllerImpl(service);
	}

	@Bean
	public Service service(CrdDao dao) {
		return new ServiceImpl(dao);
	}

	@Bean
	public CrdDao crdDao(HttpHelper httpHelper) {
		return new TwitterDao(httpHelper);
	}

	@Bean
	HttpHelper helper() {
		String consumerKey = System.getenv("consumerKey");
		String consumerSecret = System.getenv("consumerSecret");
		String accessToken = System.getenv("accessToken");
		String tokenSecret = System.getenv("tokenSecret");
		return new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
	}
}