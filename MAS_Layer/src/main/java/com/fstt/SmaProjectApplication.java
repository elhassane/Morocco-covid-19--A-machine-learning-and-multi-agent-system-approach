package com.fstt;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fstt.container.ClusteringContainer;
import com.fstt.container.DataVisualizationContainer;
import com.fstt.container.PredictionContainer;
import com.fstt.container.ScrapingContainer;
import com.fstt.container.SentimentAnalysisContainer;

@SpringBootApplication
public class SmaProjectApplication {
	
	@Bean
	public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	}
	
	public static void main(String[] args) {
		 new SpringApplicationBuilder(SmaProjectApplication.class).headless(false).run(args);
	    SentimentAnalysisContainer sentimentAnalysisContainer=new SentimentAnalysisContainer();
		DataVisualizationContainer  dataVisualizationContainer=new DataVisualizationContainer();
		PredictionContainer predictionContainer=new PredictionContainer();
		ClusteringContainer clusteringContainer=new ClusteringContainer();
		ScrapingContainer scrapingContainer=new ScrapingContainer();
	}

}
