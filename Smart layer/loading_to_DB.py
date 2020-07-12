
import pandas as pd
from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.covML
covTS_col = db.MAtimeSeries
news_col =  db.scrapedData
regions_col =  db.regionsData

def load_to_DB(loading_type):
    if loading_type=="sentiment":
        news_df=pd.read_csv("myScrapedData.csv")  
        myData = news_df.to_dict('records')
        news_col.delete_many({})
        news_col.insert_many(myData)
    if loading_type=="prediction":
        news_df=pd.read_csv("MA-times_series.csv")  
        myData = news_df.to_dict('records')
        covTS_col.delete_many({})
        covTS_col.insert_many(myData)
    if loading_type=="regions":
        news_df=pd.read_csv("MA-regions_data.csv")  
        myData = news_df.to_dict('records')
        regions_col.delete_many({})
        regions_col.insert_many(myData)

if __name__ == "__main__":
   load_to_DB("prediction")   



