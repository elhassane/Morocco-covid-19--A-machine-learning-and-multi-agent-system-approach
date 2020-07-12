import { Component, OnInit } from '@angular/core';
import { ServService } from '../services/serv.service';
import {Chart} from 'node_modules/chart.js'


@Component({
  selector: 'app-main-container',
  templateUrl: './main-container.component.html',
  styleUrls: ['./main-container.component.css','./Scrapping.css']
})
export class MainContainerComponent implements OnInit {

  isPositive:string="n"; //var to store posi or neg news
  numberNews:number=0; //var to number of news

  dataMaroc:any;// Data dyal nada de maroc avec states
  dataNews:any;// Data dyal nada de maroc avec states

  selector:string = "Home";// pour afficher la page correspondate

  strToAnalyze:string;//String to be analyze
  sentimentState:string = "__";//To show sentiment analyze of phrase
  confidence:number = 0;

  nDeaths:number=0;
  nCured:number=0;
  nCases:number=0;
  nActive:number=0;

  nToPredict:number=0;//Number of cases to Predict
  nOfDeathsPredicted:number=0;//Number predicted of deaths

  newsStatistics:number=0;
  
  constructor(private serT:ServService) { }

  ngOnInit() {
    this.getData();
    
    setTimeout(()=>{ 
      this.graphe();
    }, 2000);
  }

  resize(){
    if( document.getElementById('contents').style.marginLeft == '0px'){
      let aside = document.getElementsByClassName('side-nav') as HTMLCollectionOf<HTMLElement>;
      document.getElementById('contents').style.marginLeft='290px';
      aside[0].style.width = "290px";
    }else{
      let aside = document.getElementsByClassName('side-nav') as HTMLCollectionOf<HTMLElement>;
      document.getElementById('contents').style.marginLeft='0px';
      aside[0].style.width = "0px";
    }
  }

  setPositiveValue(e){
    if(e.checked){
      this.isPositive="p";
    }else{
      this.isPositive="n";
    }
  }
  setNewsNumber(e){
    this.numberNews = e.value;
  }

  getNews(){
    console.log(this.numberNews,this.isPositive);
    this.serT.getNews(this.numberNews,this.isPositive).subscribe( (data:any) => {
      console.log(data[1].headline,44);  
      this.dataNews=data;
    });
  }

  getNewsStat(){
    this.serT.getNewsStat().subscribe( (data:any) => {  
      this.newsStatistics=data;
      console.log(this.newsStatistics);
      this.ChartsNews();
    });
  } 

  predictDeaths(){
    this.serT.prediction(this.nToPredict).subscribe( (data:string) => {
      this.ChartsPrediction(Number(data));
    });
   
    
  }

  AnalyzePhrase(){
    this.serT.AnalyzePhrase(this.strToAnalyze).subscribe( (data:any) => {
      this.sentimentState=data.result;
      this.confidence=data.confidence;
    });
  }

  ChangeSelector(msg:string){
    this.selector = msg;
    setTimeout(()=>{    //<<<---    using ()=> syntax
      this.graphe();
    }, 2000);
  }

  StartScrapping(str:string){
    this.serT.StartScrapping(str);
  }

  getData(){
    this.serT.getMaroc().subscribe(data => {
      console.log(data);  
      this.dataMaroc=data;
    });
  }

  graphe(){
    this.CalculCases();//To Calculate number of deaths cases ....
    this.ChartsCluster();
    this.ChartsVisualisation();
    this.getNewsStat();
  }

  CalculCases(){
    this.nDeaths=this.dataMaroc.statistics[0].Deaths;
    this.nCured=this.dataMaroc.statistics[0].Recovered;
    this.nCases=this.dataMaroc.statistics[0].Cases;
    this.nActive= this.nCases - (this.nCured+this.nDeaths);
  }

  
  ChartsVisualisation (){
    var myChart = new Chart("myChart", {
      type: 'line',
      data: {
          labels:   this.dataMaroc.regions,
          datasets: [{
              label: '# of Total Confirmed cases ',
              data: this.dataMaroc.Confirmed,
              backgroundColor: '#D7A449',
              fill: true,
    
            borderWidth: 2,
          borderColor : '#8B0000'
          }]
      }
    });
    var myChart2 = new Chart("myChart2", {
      type: 'bar',
      data: {
        labels:   this.dataMaroc.regions,
        datasets: [{
          label: '# of Total cured cases ',
          fill: false,
          lineTension: 0,
          data: this.dataMaroc.Recovered,
          pointBorderColor: "#4bc0c0",
          borderColor: '#4bc0c0',
          borderWidth: 2,
          showLine: true,
        }]
      },
    });

  }

  ChartsCluster(){
    var myChart3 = new Chart("myChart3", {
      type: 'pie',
      data: {
        labels: ["Deaths", "Recovered", "Confirmed"],
        datasets: [{
          backgroundColor: ["#DB3F29", "#1DC690","#D7A449"],
          data: [this.nDeaths, this.nCured, this.nCases]
        }]
      },
      options: {
        title: {
          display: true,
          text: "Cases situation in Morocco"
        }
      }
    });

    var myChartCluster = new Chart("myChartCluster", {
      type: 'bar',
      data: {
        labels:   this.dataMaroc.regions,
        datasets: [{
          label: 'Nombre of deaths',
          data: this.dataMaroc.Deaths,
          backgroundColor: "#DB3F29",
          fill: false,
          pointRadius:0,
          pointHitRadius: 0,
          borderWidth: 2,
          borderColor : "#ccc"
        },{
          label: 'Confirmed cases',
          data: this.dataMaroc.Confirmed,
          backgroundColor:"#D7A449",
          pointRadius:0,
          pointHitRadius: 0,
          borderWidth: 2,
          fill: false,
          borderColor: "#ccc"
        },{
          label: 'Nombre recovered',
          data: this.dataMaroc.Recovered,
          backgroundColor: '#33ff77',
          fill: false,
          pointRadius:0,
          pointHitRadius: 0,
          borderWidth: 2,
          borderColor : "#ccc"         
      }]},
    });

    var bubbleChart = new Chart("bubbleChart",{
      type: 'bubble',
      data: {
        datasets: [
          {
            label: ["CasablancaSettat"],
            backgroundColor: "rgba(0,0,0,0.2)",
            borderColor: "#000",
            data: [{
              x: 3979083,
              y: 6.994,
              r: 30
            }]
          },
          {
            label: ["FesMeknes - MarrakeshSafi - RabatSaleKenitra - TangerTetouanAlHoceima"],
            backgroundColor: "rgba(255,221,50,0.2)",
            borderColor: "rgba(255,221,50,1)",
            data: [{
              x: 258702,
              y: 7.526,
              r: 20
              
            }]
          },{
            label: ["BeniMellalKhenifra - DraaTafilalet - DakhlaOuedEdDahab - GuelmimOuedNoun - LaayouneSakiaEl Hamra - Oriental - SoussMassa"],
            backgroundColor: "rgba(60,186,159,0.2)",
            borderColor: "rgba(60,186,159,1)",
            data: [{
              x: 21269017,
              y: 5.245,
              r: 25
            }]
          }]
      }
    });
  }

  ChartsPrediction(n:number){
    var numberOfDeaths= this.nToPredict-n;
    var predictChart = new Chart("predictChart", {
      type: 'pie',
      data: {
        labels: ["Deaths" ,"Recovered"],
        datasets: [{
          backgroundColor: ["#DB3F29", "#00d639"],
          data: [n, numberOfDeaths]
        }]},
    });
  }

  ChartsNews(){
    var newsChart = new Chart("newsChart", {
      type: 'pie',
      data: {
        labels: ["Negative" ,"Positive"],
        datasets: [{
          backgroundColor: ["#DB3F29", "#00d639"],
          data: [ this.newsStatistics['negatives'],  this.newsStatistics['positives']]
        }]},
    });
  }

}
