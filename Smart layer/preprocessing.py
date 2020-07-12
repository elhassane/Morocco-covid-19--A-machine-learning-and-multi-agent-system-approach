import pandas as pd

def preprocessing_data():
    news_df=pd.read_csv("myScrapedData.csv")  
    news_df=news_df.sort_values('headline', ascending=False)
    news_df=news_df.drop_duplicates(subset='headline')
    news_df=news_df.drop('url', 1)
    news_df.to_csv("myScrapedData.csv",index=False)
