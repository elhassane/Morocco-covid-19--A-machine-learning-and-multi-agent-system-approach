package com.fstt.agent;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fstt.service.RestService;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;


@RestController
@CrossOrigin("*")
public class DataVisualizationAgent extends Agent{
	private static final long serialVersionUID = 1L;
	boolean isterminated=false;
	public static DataVisualizationAgent dump;
	@Autowired
	RestService rs;

	@Override
	public void setup() {
		RestService restService=new RestService();
		System.out.println("Initialisation de l'agent :"+this.getAID().getName());
		dump=this;
		addBehaviour(new Behaviour() {  		
			@Override
			public void action() {	
				ACLMessage aclMessage = receive();
				if (aclMessage!=null) {
					try {
						System.out.println("Scraping-Preprocessing-Loading finished successfully ---------------------");
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
	
	public DataVisualizationAgent getDump(){
		return dump;
	}
	public void sendMessage() throws IOException{
		ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
		aclMessage.addReceiver(new AID("ScrapingAgent",AID.ISLOCALNAME));
		aclMessage.setContent("visualization");
		aclMessage.setOntology("Start visualization");
		send(aclMessage);
	}
	
	
	@RequestMapping(value = "/marocData", method = RequestMethod.GET)
	public JsonNode getSentiment() throws JsonMappingException, JsonProcessingException{	
		return rs.get("/api/v1/marocData");
	}
	
	@RequestMapping(value = "/startScraping/visualization", method = RequestMethod.GET)
	public String startScraping() throws IOException{
		getDump().sendMessage();
		return "success";
	} 

}
