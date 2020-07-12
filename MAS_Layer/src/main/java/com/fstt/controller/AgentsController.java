package com.fstt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fstt.container.ClusteringContainer;
import com.fstt.container.DataVisualizationContainer;
import com.fstt.container.MainContainer;
import com.fstt.container.PredictionContainer;
import com.fstt.container.ScrapingContainer;
import com.fstt.container.SentimentAnalysisContainer;

import jade.wrapper.StaleProxyException;


@RestController
@CrossOrigin("*")
public class AgentsController {
	@Autowired
	MainContainer mainContainer;
	
	
	@RequestMapping(value = "/startAgents", method = RequestMethod.GET)
	public String startAgents() {
		SentimentAnalysisContainer sentimentAnalysisContainer=new SentimentAnalysisContainer();
		DataVisualizationContainer  dataVisualizationContainer=new DataVisualizationContainer();
		PredictionContainer predictionContainer=new PredictionContainer();
		ClusteringContainer clusteringContainer=new ClusteringContainer();
		ScrapingContainer scrapingContainer=new ScrapingContainer();
	
		return "Containers have been invocked";
	}
	
}
