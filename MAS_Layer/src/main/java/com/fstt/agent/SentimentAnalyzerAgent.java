package com.fstt.agent;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Soundbank;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@RestController
@CrossOrigin("*")
public class SentimentAnalyzerAgent extends Agent {
	
	private static final long serialVersionUID = 1L;
	boolean isterminated=false;
	public static SentimentAnalyzerAgent dump;
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
 						restService.get("/api/v1/startAnalysis");
						System.out.println("Scraping-Preprocessing-Loading-SentimentAnalysis finished ---------------------");
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

	public SentimentAnalyzerAgent getDump(){
		return dump;
	}
	
	public void sendMessage(String param1){
		ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
		aclMessage.addReceiver(new AID("ScrapingAgent",AID.ISLOCALNAME));
		try {
			
			aclMessage.setContentObject((Serializable) param1);
			aclMessage.setContent("analysis");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aclMessage.setOntology("Start Scraping");
		send(aclMessage);
	}
	

	@RequestMapping(value = "/getNews/{sentiment}/{number}", method = RequestMethod.GET)
	public JsonNode getSentiment(@PathVariable("sentiment") String sentiment, @PathVariable("number") String number) throws JsonMappingException, JsonProcessingException{	
		return rs.get("api/v1/getNews?number="+number+"&sentiment="+sentiment+"");
	}
	
	@RequestMapping(value = "/startScraping/sentiment", method = RequestMethod.GET)
	public String startScraping(){
		getDump().sendMessage("2");
		return "success";
	} 
	
	@RequestMapping(value = "/getNewsStatistics", method = RequestMethod.GET)
	public JsonNode getStatistics() throws JsonMappingException, JsonProcessingException{
		return rs.get("/api/v1/statAnalysis");
	}
	
	@RequestMapping(value = "/analyzeText/{text}", method = RequestMethod.GET)
	public JsonNode analyseText(@PathVariable("text") String text) throws JsonMappingException, JsonProcessingException{
		return rs.get("/api/v1/analyzeSentiment?text="+text);
	}

}




