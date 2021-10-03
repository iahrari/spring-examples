package com.github.iahrari.springjms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsApplication.class, args);
		// try {
		// 	ActiveMQServer server = ActiveMQServers
		// 			.newActiveMQServer(
		// 				new ConfigurationImpl()
		// 					.setPersistenceEnabled(false)
		// 					.setJournalDirectory("target/data/journal")
		// 					.setSecurityEnabled(false)
		// 					.addAcceptorConfiguration("invm", "vm://0"));

		// 	server.start();
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// }
	}

/*
docker run -d -p 61616:61616 -p 8161:8161 \
           -v /home/iahrari/container/activemq/conf:/var/lib/artemis-instance/etc-overrides \
           -v /home/iahrari/container/activemq/data:/var/lib/artemis-instance/data \
           calincerchez/activemq-artemis
*/

}
