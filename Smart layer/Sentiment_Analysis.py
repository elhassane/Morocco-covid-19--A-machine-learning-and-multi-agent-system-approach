from pymongo import MongoClient
import pandas as pd
import numpy as np
from flair.models import TextClassifier
from flair.data import Sentence 
import logging
logging.basicConfig(level=logging.ERROR)

###### Mongodb connection
client = MongoClient('localhost', 27017)
db = client.covML
data_col = db.scrapedData
############################

flair_sentiment = TextClassifier.load('en-sentiment')

## 
def analyze_sentiment(headline):
    s = Sentence(headline)
    flair_sentiment.predict(s)
    total_sentiment = s.labels[0].to_dict()
    return total_sentiment

##
def analyseSentiments():
    myNews=data_col.find()
    df =  pd.DataFrame(myNews)
    del df['_id']
    df['Result'] = np.array([analyze_sentiment(headline)['value'] for headline in df['headline']])
    df['confidence'] = np.array([analyze_sentiment(headline)['confidence'] for headline in df['headline']])
    data_col.delete_many({})
    myData = df.to_dict('records')
    data_col.insert_many(myData)