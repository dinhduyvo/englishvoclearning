import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ElectronService } from 'ngx-electron';
import { Observable, of } from 'rxjs';
import { Word } from 'src/entity/Word';
import { catchError } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class AppService {
    csvData = 'assets/csv/data.csv';
    constructor(private http: HttpClient, private _electronService: ElectronService) { }
    getInfo() {
        return this.http.get(this.csvData, { responseType: 'text' });
    }

    getItems(): Observable<Word[]> {
      return of(this._electronService.ipcRenderer.sendSync('get-items')).pipe(
        catchError((error: any) => Observable.throw(error.json))
      );
    }

    addItem(item: Word): Observable<Word[]> {
      return of(
        this._electronService.ipcRenderer.sendSync('add-item', item)
      ).pipe(catchError((error: any) => Observable.throw(error.json)));
    }

    deleteItem(item: Word): Observable<Word[]> {
      return of(
        this._electronService.ipcRenderer.sendSync('delete-item', item)
      ).pipe(catchError((error: any) => Observable.throw(error.json)));
    }
}
