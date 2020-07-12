package com.fstt.agent;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import com.fstt.service.RestService;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;


@RestController
@CrossOrigin("*")
public class ScrapingAgent extends Agent {
	private static final long serialVersionUID = 1L;	
	boolean isterminated=false;

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
							String numOfPages= (String) aclMessage.getContentObject();
							restService.get("/api/v1/scrapNews?numOfPages="+numOfPages);
							ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
							demande.addReceiver(new AID("PreprocessingAgent", AID.ISLOCALNAME));
							demande.setContent("analysis");
							System.out.println("scrapped succesfully *****************************");
							send(demande);
						}
						if(type.contains("prediction")) {
							restService.get("/api/v1/scrapCovData?typeData=prediction");
							ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
							demande.addReceiver(new AID("PreprocessingAgent", AID.ISLOCALNAME));
							demande.setContent("prediction");
							System.out.println("scrapped succesfully *****************************");
							send(demande);
						}
						if(type.contains("clustering")) {
							restService.get("/api/v1/scrapCovData?typeData=regions");
							ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
							demande.addReceiver(new AID("PreprocessingAgent", AID.ISLOCALNAME));
							demande.setContent("clustering");
							System.out.println("scrapped succesfully *****************************");
							send(demande);
						}
						if(type.contains("visualization")) {
							restService.get("/api/v1/scrapCovData?typeData=regions");
							ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
							demande.addReceiver(new AID("PreprocessingAgent", AID.ISLOCALNAME));
							demande.setContent("visualization");
							System.out.println("scrapped succesfully *****************************");
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
