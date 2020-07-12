import numpy as np 
import pandas as pd 
from sklearn.cluster import KMeans
from pymongo import MongoClient

#Database connection
client = MongoClient('localhost', 27017)
db = client.covML
regions_col =  db.regionsData


def clustering():
    myData=regions_col.find({}, {"_id": 0})
    df = pd.DataFrame(myData) 
    data_kmeans=df[['Confirmed','Deaths']]
    kmeans = KMeans(n_clusters = 3, init = 'k-means++', random_state = 42)
    y_kmeans = kmeans.fit_predict(data_kmeans)
    y_kmeans1=y_kmeans
    y_kmeans1=y_kmeans+1
    cluster = pd.DataFrame(y_kmeans1)
    data_kmeans['cluster'] = cluster
    data_risk= pd.DataFrame()
    data_risk["country"]=df["Region"]
    data_risk["Confirmed"]=y_kmeans1
    lis = list()
    for group in range(1,4):
        countries=data_risk.loc[data_risk['Confirmed']==group]
        listofcountries= list(countries['country'])
        lis.append(listofcountries)
    return lis