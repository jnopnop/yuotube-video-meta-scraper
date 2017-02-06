package org.nop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.AsyncRestTemplate;

@SpringBootApplication
public class YoutubeMetaParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoutubeMetaParserApplication.class, args);
	}

	@Bean
	public AsyncRestTemplate getAsyncRestTemplate() {
		return new AsyncRestTemplate();
	}
}
