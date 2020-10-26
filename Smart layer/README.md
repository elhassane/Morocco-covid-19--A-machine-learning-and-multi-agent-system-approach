# Smart layer
This is a Flask based REST api containing all the scripts needed for scraping, pre-processing and loading data into a Mongodb database in addition to all the models we have developed for clustering, predictions, sentimental analysis and visualizations scripts.

## Built With
* Python 3.7  

## Prerequisites
1- Make sure you have installed all the necessary libraries:
```
pip install flask
```
```
pip install pymongo
```
```
pip install beautifulsoup4
```
```
pip install scikit-learn
```
```
pip install pandas
```
```
pip install flair 
```

Flair is built with pytorch, so it requires torch version >=1.5

2 -Make sure to create a mongodb database called "covML" and the used collections:
scrapedData , dataCollection, regionsData , MAtimeSeries

3 -run the file app.py 
p.s: Flair needs to download a 260Mb file "sentiment-en-mix-distillbert".

## API Description 
```
@app.route('/api/v1/scrapNews', methods=['GET']) :
```
requires paramrter  "number of pages" in order to start scrapping news titles from "maroc world news".
```
@app.route('/api/v1/preprocessing', methods=['GET'])
```
lance le prétraitement et le nettoyage des données collectées
```
@app.route('/api/v1/scrapCovData', methods=['GET'])
```
nécessite un paramètre "type de data", pour commencer à collecter les données
correctes de covid19 au maroc selon le type de données que nous voulons.
```
@app.route('/api/v1/loadToDB', methods=['GET'])
```
nécessite un paramètre "type", pour charger les données collectées dans la
collection mongodb appropriée.

```
@app.route('/api/v1/startAnalysis', methods=['GET'])
```
il commence à analyser les titres de l'actualité recueillis et enregistre les résultats.
```
@app.route('/api/v1/analyzeSentiment', methods=['POST'])
```
nécessite un paramètre "text", pour commencer à analyser le texte passé en
paramètres et renvoie le sentiment, et la confiance de model pour ce résultat.
```
@app.route('/api/v1/getNews', methods=['GET'])
```
nécessite les paramètre "number" et 'sentiment' , et renvoie le nombre d’actualités
correspondent au sentiment passé en paramètres.
```
@app.route('/api/v1/statAnalysis', methods=['GET'])
```
renvoie des statistiques sur les titres de l'actualité, le nombre de titres, le nombre
de titres positifs et négatifs.
```
@app.route('/api/v1/marocData', methods = ["GET"])
```
renvoie les données covid19 marocaines pour la visualisation.
```
@app.route('/api/v1/getClusters', methods = ["GET"])
```
renvoie les régions regroupées “clusters” du maroc en ce qui concerne les données
de la covid 19.
```
@app.route('/api/v1/prediction', methods = ["GET"])
```
nécessite un paramètre "number", c’est le nombre des cas confirmés pour prédire
et renvoie le nombre des mort.

## DataSets
### Morocco news headlines 
We have scarapped the news headlines from the daily updated website "MoroccoWorldNews.com" using beautifulsoup4
### Morocco covid 19 time series data  
We have fetched the data directly from a raw updated source </br>
https://github.com/aboullaite/Covid19-MA/blob/master/stats/MA-times_series.csv 
### Morocco regions data
We have fetched the data directly from a raw updated source </br>
https://github.com/aboullaite/Covid19-MA/blob/master/stats/regions.csv

## Machine Learning Models
### Sentiment Analysis Model
A pretrained model from Flair in order to analyze Moroccan news headlines sentiment polarity (positive or negative).
### Clustering Model
A Kmeans algorithm trained on a unlabled data, in order to get cluters in moroccan regions based on number of confirmed cases and deaths
### Prediction Model
A linear regression model trained on time series data (morocco covid 19 dataset), in order to predict number of deaths based on number of cases.


