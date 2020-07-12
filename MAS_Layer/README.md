# Multi Agent System (MAS) layer 
This is a REST api based on SpringBoot containing JADE Framework with all our agents, and their behaviours and how they communicate with each other and 
how they serve the SPA frontend layer. <br/><br/>
<img src="img/arch.png" alt="alt text" width="700" height="430">
## Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used to build the MAS layer API
* [JADE Framework](https://jade.tilab.com/) - The framework used to implement agents in the MAS layer

## Scraping Agents
### Scrapping agent 
An agent that handle scrapping requests comming from other agents using ACL messges.<br/>
1- The agent starts the appropriate request in order to handle the corresponding scrapped data.<br/>
2- The agent send a REQUEST message to Preprocessing Agent in order to start preprocessing the data.<br/>
### Preprocessing Agent
An agent that handle preprocessing requests comming from other agents using ACL messges.<br/>
1- The agent starts the appropriate request in order to preprocess the corresponding scrapped data.<br/>
2- The agent send a REQUEST message to Loading Agent in order to start loading the data.<br/>
### Loading Agents
An agent that handle loading to database requests comming from other agents using ACL messges.<br/>
1- The agent starts the appropriate request in order to load the scrapped data to the corresponding collection in a mongodb database.<br/>
2- The agent send an INFORM message to the corresponding agent.<br/>

## Sentiment analysis agent
An agent that handle all the comming request concerning sentiment analysis: scrapping updated data, start analysis, providing statistics and usefull data...ect <br/><br/>
<img src="img/sentiment.png" alt="alt text" width="700" height="430">


## Prediction agent
An agent that handle all the comming request concerning prediction: scrapping updated data, geting predictions, providing statistics and usefull data...ect <br/><br/>
<img src="img/prediction.PNG" alt="alt text" width="700" height="430">


## Clustering agent
An agent that handle all the comming request concerning clustering: scrapping updated data, geting clusters, providing statistics and usefull data...ect <br/><br/>
<img src="img/clustering.PNG" alt="alt text" width="700" height="430">

## Data visualization agennts
An agent that handle all the comming request concerning visualization: scrapping updated data, providing statistics and usefull data...ect <br/><br/>
<img src="img/visualization.PNG" alt="alt text" width="700" height="430">


## API Description 
Semilar to Smart Layer API, except this time each request is handlled by it's corresponding agent.

