package com.bridgeit.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.RegistrationException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.bridgeit.model.TradeUser;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.bridgeit" })
public class JavaConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver viewResolver() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
						.allowedHeaders("*");
			}
		};
	}

	@Bean
	public static ComboPooledDataSource getDataSource() throws PropertyVetoException {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/TradeFinanceLogin?useSSL=false");
		cpds.setUser("root");
		cpds.setPassword("arpana");

		return cpds;
	}

	static final String exchangeName = "exchange";

	protected static final String queueName = "queue";

	@Bean
	ConnectionFactory connFactory() {
		CachingConnectionFactory connection = new CachingConnectionFactory("localhost");
		connection.setUsername("guest");
		connection.setPassword("guest");
		return connection;
	}

	@Bean
	DirectExchange rubeExchange() {
		return new DirectExchange(exchangeName);
	}

	@Bean
	public Queue rubeQueue() {
		return new Queue(queueName, false);
	}

	@Bean
	Binding rubeExchangeBinding() {
		return BindingBuilder.bind(rubeQueue()).to(rubeExchange()).with("rabbitkey");
	}

	@Bean
	public RabbitTemplate rubeExchangeTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate();
		rabbitTemplate.setConnectionFactory(connFactory());
		return rabbitTemplate;
	}

	@Bean
	RabbitAdmin rabbitAdmin() {
		RabbitAdmin rabbitadmin = new RabbitAdmin(connFactory());
		rabbitadmin.declareExchange(rubeExchange());

		rabbitadmin.declareQueue(rubeQueue());
		rabbitadmin.declareBinding(rubeExchangeBinding());
		return rabbitadmin;

	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				System.out.println(message);
			}
		});
		return container;
	}

	@Bean
	JavaMailSender mailSend() {
		JavaMailSenderImpl mailImplement = new JavaMailSenderImpl();
		mailImplement.setDefaultEncoding("utf-8");
		mailImplement.setHost("smtp.gmail.com");
		mailImplement.setUsername("tradefinancebridgelabz@gmail.com");
		mailImplement.setPassword("tradefinance2018");
		mailImplement.setPort(587);

		Properties properties = mailImplement.getJavaMailProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.debug", "true");
		return mailImplement;
	}

	// fabric sdk
	
	@Bean
	 HFCAClient getHfCaClient(String caUrl, Properties caClientProperties) throws IllegalAccessException, InstantiationException, ClassNotFoundException, CryptoException, InvalidArgumentException, NoSuchMethodException, InvocationTargetException, MalformedURLException {
		CryptoSuite cryptoSuite;
			cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
			HFCAClient caClient = HFCAClient.createNewInstance("http://localhost:7054", null);
			caClient.setCryptoSuite(cryptoSuite);
			//client.setUserContext(admin);
		
		return caClient;
	}

	// TradeUser admin = getAdmin(caClient);
	
	
	 HFCAClient getAdmin(HFCAClient caClient) {
		 TradeUser admin = tryDeserialize("admin");
			if (admin == null) {
				Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
				admin = new TradeUser("admin", "importer", "ImporterMSP", adminEnrollment);
				serialize(admin);

			}
			return admin;
	}
	
	static TradeUser tryDeserialize(String name) throws Exception {
		if (Files.exists(Paths.get(name + ".jso"))) {
			return deserialize(name);
		}
		return null;
	}

	static TradeUser deserialize(String name) throws Exception {
		try (ObjectInputStream decoder = new ObjectInputStream(Files.newInputStream(Paths.get(name + ".jso")))) {
			return (TradeUser) decoder.readObject();
		}
	}
	
	static void serialize(TradeUser appUser) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				Files.newOutputStream(Paths.get(appUser.getName() + ".jso")))) {
			oos.writeObject(appUser);
		}
	
	}

}
