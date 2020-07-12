from flask import abort, Flask, jsonify, request
from flair.models import TextClassifier
from flair.data import Sentence
from pymongo import MongoClient
from bson.json_util import dumps
from scraping import (scrap_moroccan_news,covid19TSdataScrapingMA,regionDataScraping)
from loading_to_DB import load_to_DB
from preprocessing import preprocessing_data
from Sentiment_Analysis import analyseSentiments
from clustering import clustering
from prediction import predictDeaths
import pandas as pd


app = Flask(__name__ )

#Database connection
client = MongoClient('localhost', 27017)
db = client.covML
news_col = db.scrapedData
cov_col = db.dataCollection
regions_col =  db.regionsData
covTS_col = db.MAtimeSeries

classifier = TextClassifier.load('en-sentiment')
# index 
@app.route('/')
def index():
    return "Welcome to morocco covid-19 api!"

@app.route('/api/v1/scrapNews', methods=['GET']) 
def startScraping():
    """ scrap n number of pages news """
    numOfPages = request.args['numOfPages'] 
    scrap_moroccan_news(numOfPages)
    return jsonify("news scraped succefuly") , 200

@app.route('/api/v1/scrapCovData', methods=['GET']) 
def doScraping():
    """ scrap covid 19 data """
    typeData = request.args['typeData'] 
    if typeData=="prediction":
        covid19TSdataScrapingMA()
    else: 
        regionDataScraping()
    return jsonify("covid 19 data scraped succefuly") , 200 

@app.route('/api/v1/preprocessing', methods=['GET'])
def preprocessing():
    """ preprocess scraped data """
    preprocessing_data()
    return jsonify("news preprocessed succefuly") , 200


@app.route('/api/v1/loadToDB', methods=['GET'])
def loadToDB():
    """ load csv file to database collection """
    loading_type = request.args['type']
    load_to_DB(loading_type)
    return jsonify("loaded succefuly to Database") , 200

@app.route('/api/v1/startAnalysis', methods=['GET'])
def startAnalysis():
    """ load csv file to database collection """
    analyseSentiments()
    return jsonify("news loaded succefuly to Database") , 200

@app.route('/api/v1/analyzeSentiment', methods=['GET'])
def analyzeSentiment():
    message = request.args['text']
    sentence = Sentence(message)
    classifier.predict(sentence)
    print('Sentence sentiment: ', sentence.labels)
    label = sentence.labels[0]
    labscore = (label.score)*100
    response = {'result': label.value, 'confidence':"%.2f" % labscore}
    return jsonify(response), 200


@app.route('/api/v1/getNews', methods=['GET'])
def getPositiveSentiment():
    """ returns json file containing headlines 
    you have to specify 2 parameters : 
    number : the number of retrieved documents 
    sentiment : p for positive , n for negative , else all  """
    number = request.args['number']
    sentiment = request.args['sentiment']
    if sentiment=="n":
        res = news_col.find({ "Result": "NEGATIVE"} , {"headline": 1,"Result": 1, "_id": 0}).limit(int(number))
    elif sentiment=="p":
        res = news_col.find({ "Result": "POSITIVE" } , {"headline": 1,"Result": 1, "_id": 0}).limit(int(number))
    else:
        res = news_col.find({}, {"headline": 1,"Result": 1, "_id": 0}).limit(int(number))
    list_res = list(res)
    return jsonify(list_res) , 200


@app.route('/api/v1/statAnalysis', methods=['GET'])
def getSentimentStatistics():
    """ returns json file containing news statistics"""
    all_news = news_col.count_documents({})
    neg = 100*news_col.count_documents({ "Result": "NEGATIVE"})/all_news
    pos = 100*news_col.count_documents({ "Result": "POSITIVE" })/all_news
    resp = {'all': all_news, 'positives':pos, 'negatives':neg}
    return jsonify(resp) , 200

@app.route('/api/v1/marocData', methods = ["GET"])
def marocData():
    myData=regions_col.find({}, {"_id": 0})
    df = pd.DataFrame(myData)   
    df2 = df.fillna(0)
    df3 = df2.to_numpy()
    regions = [l[0] for l in df3]
    Confirmed = [l[1] for l in df3]
    Active = [l[2] for l in df3] 
    Deaths = [l[3] for l in df3]
    Recovered = [l[4] for l in df3]
    statsData=covTS_col.find({}, {"_id": 0})
    df_stats = pd.DataFrame(statsData)   
    satistics=df_stats.tail(1)
    satistic=satistics.to_dict('records')
    return jsonify(regions = regions, Confirmed= Confirmed, Active=Active,Deaths=Deaths, Recovered=Recovered, statistics=satistic)
     
@app.route('/api/v1/getClusters', methods = ["GET"])
def getClusters():
    return jsonify(clustering())

@app.route('/api/v1/prediction', methods = ["GET"])
def predict():
    number = request.args['number']
    return jsonify(predictDeaths(int(number)))

if __name__ == "__main__":
    app.run()