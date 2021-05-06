import { Injectable } from '@angular/core';
import { SERVER_API } from 'app/shared/constants/api-resource.constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoomApiServiceService {
  private baseUri = SERVER_API;
  private token = localStorage.getItem('token');

  constructor(private http: HttpClient) {}

  searchRoom(searchForm?: any): Observable<any> {
    return this.http.post(this.baseUri + '/room/searchRoom', searchForm);
  }

  save(data): Observable<any> {
    return this.http.post<any>(SERVER_API + '/room/add', data);
  }

  deleteRoom(id): Observable<any> {
    return this.http.get<any>(SERVER_API + '/room/deleteRoom/' + id);
  }

  getInfo(Id): Observable<any> {
    return this.http.get(SERVER_API + '/room/get-room-by-id/' + Id);
  }
  getAsseList(): Observable<any> {
    return this.http.get(SERVER_API + '/asset/get-asset');
  }
  getRoomTypeList(): Observable<any> {
    return this.http.get(SERVER_API + '/roomType/get-room-tuype-All');
  }

  getAll(): Observable<any> {
    return this.http.get(SERVER_API + '/room/get-all-room');
  }
}
