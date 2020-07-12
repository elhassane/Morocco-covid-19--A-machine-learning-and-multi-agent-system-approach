import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn import metrics
from pymongo import MongoClient
import matplotlib.pyplot as plt 
import matplotlib.colors as mcolors
import datetime as dt

###### Mongodb connection
client = MongoClient('localhost', 27017)
db = client.covML
data_col = db.MAtimeSeries
############################

def predictDeaths(val):
    myData=data_col.find()
    df = pd.DataFrame(myData)
    x=df['Cases'].values.reshape(-1,1)
    y=df['Deaths'].values.reshape(-1,1)
    x_train,x_test,y_train,y_test=train_test_split(x,y,test_size=0.1,random_state=0)
    linear_model=LinearRegression(normalize=True,fit_intercept=True)
    linear_model.fit(x_train,y_train)
    arr = np.array([val])
    arr=arr.reshape(-1, 1)
    linear_pred=linear_model.predict(arr)
    res=int(linear_pred[0])
    return res




    # y_pred=linear_model.predict(x_test)
    # plt.plot(x, y, color='orange' )
    # plt.xlabel('Number of cases', color='blue')
    # plt.ylabel('Number of deaths ', color='red')
    # plt.show()

    # plt.plot(y_pred, label='test predictions')
    # plt.plot(y_test, label='test real values')
    # plt.legend()
    # plt.show()


# if __name__ == "__main__":
#    predictDeaths(14771)
# def predictCases(val):
    # myData=data_col.find()
    # df = pd.DataFrame(myData)
    
    # x=df['Cases'].values.reshape(-1,1)
    # y=df['Deaths'].values.reshape(-1,1)
    # # dates=df['Dates'].values.reshape(-1,1)
    # dates=pd.to_datetime(df['Dates']) 
    # dates=dates.dt.strftime("%d%m%Y").astype(int)
    # dates=dates.values.reshape(-1,1)
    # cases=np.array(x).reshape(-1,1)
    #  # forcasting for the next 10 days
    # days_in_futur=10
    # future_forcast=np.array([i for i in range(len(dates)+days_in_futur)]).reshape(-1,1)
    # adjusted_dates=(future_forcast[:10])
    # x_train,x_test,y_train,y_test=train_test_split(dates,cases,test_size=0.1,shuffle=False)
    # linear_model=LinearRegression(normalize=True,fit_intercept=True)
    # linear_model.fit(x_train,y_train)
    # test_pred=linear_model.predict(x_test)
    # linear_pred=linear_model.predict(future_forcast)
    # plt.plot(y_test)
    # plt.plot(test_pred)
    # plt.plot(linear_pred)
    # plt.show()
    # # plt.figure(figsize=(20,12))
    # # x=adjusted_dates.reshape(1,-1)
    # # y=cases
    # # plt.plot(x,y)
    # # plt.plot(future_forcast,linear_pred,linestyle='dashed',color='orange')
    # # plt.title('Number of coronavirus cases over time',size=30)
    # # plt.xlabel('Days since ',size=30)
    # # plt.ylabel('Number of cases ','Linear regression  Predictions',size=30)
    # # plt.show()