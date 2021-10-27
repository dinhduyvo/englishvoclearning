import { AppService } from './../app.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  outputData: any[] = [];

  constructor(private appService: AppService) { }

  ngOnInit(): void {
    this.appService.getInfo().subscribe(data => {
      const list = data.split("\n");
      list.forEach(e => {
        var itemData = e.split(",");
        this.outputData.push(itemData);
      })
    });
  }

}
