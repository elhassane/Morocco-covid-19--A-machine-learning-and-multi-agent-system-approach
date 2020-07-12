import { Component, OnInit } from '@angular/core';
import {Chart} from 'node_modules/chart.js'
import { ServService } from '../services/serv.service';

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['./data.component.css']
})
export class DataComponent implements OnInit {

  data:any;

  constructor(private serT:ServService) { }

  ngOnInit() {
  }

   getData(){
  //   this.serT.Getdata().subscribe(data => {
  //     console.log(data);  
  //     this.data=data;
  // });
  }

  graphe  (){
    var myChart = new Chart("myChart", {
      type: 'line',
      data: {
          labels:   this.data.state,
          datasets: [{
              label: '# of Total Confirmed cases ',
              data: this.data.confirmed,
              backgroundColor: '#F5F5F5',
              fill: false,
    
            borderWidth: 2,
          borderColor : '#8B0000'
          }]
      }
    });
  }

}
