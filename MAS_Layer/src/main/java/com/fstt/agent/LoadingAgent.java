package com.fstt.agent;

import java.io.Serializable;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fstt.service.RestService;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;

@RestController 
@CrossOrigin("*")
public class LoadingAgent extends Agent {
	private static final long serialVersionUID = 1L;
	
	boolean isterminated=false;

	@Override
	public void setup() {
		System.out.println("Initialisation de l'agent :"+this.getAID().getName());
		addBehaviour(new Behaviour() {  		
			@Override
			public void action() {	
			    RestService restService=new RestService();
				ACLMessage aclMessage = receive();
				if (aclMessage!=null) {
					try {
						String typeScraping=(String)aclMessage.getContent();
						System.out.println("typeScraping : "+typeScraping);
						if(typeScraping.contains("analysis")){
							restService.get("/api/v1/loadToDB?type=sentiment");
							ACLMessage demande = new ACLMessage(ACLMessage.INFORM);
							demande.addReceiver(new AID("SentimentAnalyzerAgent", AID.ISLOCALNAME));
							demande.setContent("Start analysis");
							System.out.println("loaded succesfully *****************************");
							send(demande);
						}
						if(typeScraping.contains("prediction")) {
							restService.get("/api/v1/loadToDB?type=prediction");
							ACLMessage demande = new ACLMessage(ACLMessage.INFORM);
							demande.addReceiver(new AID("PredictionAgent", AID.ISLOCALNAME));
							demande.setContent("Start prediction");
							System.out.println("loaded succesfully *****************************");
							send(demande);
						}
						if(typeScraping.contains("clustering")) {
							restService.get("/api/v1/loadToDB?type=regions");
							ACLMessage demande = new ACLMessage(ACLMessage.INFORM);
							demande.addReceiver(new AID("ClusteringAgent", AID.ISLOCALNAME));
							demande.setContent("Start clustering");
							System.out.println("loaded succesfully *****************************");
							send(demande);
						}
						if(typeScraping.contains("visualization")) {
							restService.get("/api/v1/loadToDB?type=regions");
							ACLMessage demande = new ACLMessage(ACLMessage.INFORM);
							demande.addReceiver(new AID("DataVisualizationAgent", AID.ISLOCALNAME));
							demande.setContent("Start visualization");
							System.out.println("loaded succesfully *****************************");
							send(demande);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
			}

			@Override
			public boolean done() {
				return isterminated;
			}
	  	});
	}
	

}
