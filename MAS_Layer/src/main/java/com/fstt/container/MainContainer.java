package com.fstt.container;


import org.springframework.stereotype.Service;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;

@Service
public class MainContainer {	
	

	public MainContainer() {
		try {
			Runtime runtime = Runtime.instance();
			Properties properties = new ExtendedProperties();
			properties.setProperty(Profile.GUI, "true");
			Profile profile = new ProfileImpl(properties);
			AgentContainer mainContainer = runtime.createMainContainer(profile);
			mainContainer.start();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
