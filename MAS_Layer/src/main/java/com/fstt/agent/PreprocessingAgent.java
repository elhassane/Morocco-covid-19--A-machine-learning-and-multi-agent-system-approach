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
import jade.lang.acl.MessageTemplate;

@RestController
@CrossOrigin("*")
public class PreprocessingAgent extends Agent{
	private static final long serialVersionUID = 1L;
	
	boolean isterminated=false;

	@Override
	public void setup() {
	     RestService restService=new RestService();
		System.out.println("Initialisation de l'agent :"+this.getAID().getName());
		addBehaviour(new Behaviour() {  		
			@Override
			public void action() {	
				ACLMessage aclMessage = receive();
				if (aclMessage!=null) {
					try {
						String type=(String) aclMessage.getContent();
						if(type.contains("analysis")){
							JsonNode result=restService.get("/api/v1/preprocessing");
							ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
							demande.addReceiver(new AID("LoadingAgent", AID.ISLOCALNAME));
							demande.setContent("Start loading");
							System.out.println("preprocessed succesfully *****************************");
							send(demande);
						}
						else {
							ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
							demande.addReceiver(new AID("LoadingAgent", AID.ISLOCALNAME));
							demande.setContent(type);
							System.out.println("preprocessed succesfully *****************************");
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
