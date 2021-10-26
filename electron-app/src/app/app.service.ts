import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class AppService {
    csvData = 'assets/csv/data.csv';
    constructor(private http: HttpClient) { }
    getInfo() {
        return this.http.get(this.csvData, { responseType: 'text' });
    }
}