package com.fstt.container;

import org.springframework.stereotype.Service;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;


public class ScrapingContainer {

	public ScrapingContainer() {
		try {
			Runtime runtime = Runtime.instance();
			Profile profile = new ProfileImpl(false);
			profile.setParameter(Profile.MAIN_HOST, "localhost");
			AgentContainer agentContainer = runtime.createAgentContainer(profile); 
			
			AgentController agentController = agentContainer.createNewAgent("ScrapingAgent", "com.fstt.agent.ScrapingAgent", new Object[] {});
			AgentController agentController1 = agentContainer.createNewAgent("PreprocessingAgent", "com.fstt.agent.PreprocessingAgent", new Object[] {});
			AgentController agentController2 = agentContainer.createNewAgent("LoadingAgent", "com.fstt.agent.LoadingAgent", new Object[] {});
			

			agentController.start();	
			agentController1.start();
			agentController2.start();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

