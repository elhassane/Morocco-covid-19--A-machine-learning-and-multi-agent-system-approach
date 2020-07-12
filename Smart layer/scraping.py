from bs4 import BeautifulSoup
import requests
import pandas as pd

def scrap_moroccan_news(numOfPages):
  website='moroccoworldnews'
  news_dict=[]
  numOfPages=int(numOfPages)
  for i in range(1, numOfPages+1):
      url="https://www.moroccoworldnews.com/news-2/page/{}".format(i)
      response = requests.get(url)
      soup = BeautifulSoup(response.content,"html.parser")
      for div in soup.findAll("div", {'class':'td-ss-main-sidebar'}): 
        div.decompose() 
      for div1 in soup.findAll("div", {'class':'td-subcategory-header'}):
        div1.decompose() 
      for head in soup.find_all('h3', {'class':'entry-title td-module-title'}):
        headl=head.find('a').get('title')
        news_dict.append({'website':website,'url': url,'headline': headl})
       
  news_df=pd.DataFrame(news_dict)
  news_df.to_csv("myScrapedData.csv" ,index=False, encoding='utf8')

def covid19TSdataScrapingMA():
    json_url ="https://raw.githubusercontent.com/aboullaite/Covid19-MA/master/stats/MA-times_series.csv"
    df = pd.read_csv(json_url,index_col=0)
    df.reset_index(level=0, inplace=True)
    df = df.rename(columns={'Dates / التواريخ': 'Dates', 'Cases / الحالات': 'Cases', 'Recovered / تعافى': 'Recovered', 'Deaths / الوفيات': 'Deaths'})
    df.to_csv("MA-times_series.csv", index=False)

def regionDataScraping():
    json_url ="https://raw.githubusercontent.com/aboullaite/Covid19-MA/master/stats/regions.csv"
    df = pd.read_csv(json_url,index_col=0)
    df.reset_index(level=0, inplace=True)
    df = df.rename(columns={'Region / الجهة': 'Region', 'Total Cases / إجمالي الحالات': 'Confirmed', 'Active Cases / الحالات النشطة': 'Active','Total Deaths / إجمالي الوفيات': 'Deaths', 'Total Recovered / إجمالي المعافين': 'Recovered'})
    df.to_csv("MA-regions_data.csv", index=False)



