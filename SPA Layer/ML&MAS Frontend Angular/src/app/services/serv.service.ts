import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ServService {

  constructor(private httpClient:HttpClient) { }

  getMaroc(){
    return this.httpClient.get('http://127.0.0.1:8080/marocData');
  } 
  getNews(n:number,type:string){
    return this.httpClient.get('http://127.0.0.1:8080/getNews/'+type+'/'+n);
  } 
  getNewsStat(){
    return this.httpClient.get('http://127.0.0.1:8080/getNewsStatistics');
  } 
  AnalyzePhrase(str:string){
    return this.httpClient.get('http://127.0.0.1:8080/analyzeText/'+str);
  }
  StartScrapping(str:string){ 
    console.log(str);
    this.httpClient.get('http://127.0.0.1:8080/startScraping/'+str).subscribe((data)=>{
      console.log(data);
    });
  }

  prediction(number:number){ 
    return this.httpClient.get('http://127.0.0.1:8080/predict/'+number);
  }

}
