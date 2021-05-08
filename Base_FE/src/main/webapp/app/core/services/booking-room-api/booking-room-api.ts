import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { SERVER_API } from 'app/shared/constants/api-resource.constants';
import { KeySearch } from 'app/core/models/system-categories/keysearch.model';

@Injectable({
  providedIn: 'root'
})
export class BookingRoomApi {
  private baseUri = SERVER_API;
  private token = localStorage.getItem('token');

  constructor(private http: HttpClient) {}

  searchBookingRoom(searchForm?: any): Observable<any> {
    return this.http.post(this.baseUri + '/room/onSearch', searchForm);
  }

  searchBookingRoomFuture(searchForm?: any): Observable<any> {
    return this.http.post(this.baseUri + '/booking/onSearch', searchForm);
  }

  save(data): Observable<any> {
    return this.http.post<any>(SERVER_API + '/booking/add', data);
  }

  addService(data): Observable<any> {
    return this.http.post<any>(SERVER_API + '/booking/addService', data);
  }

  getBookingService(bookingId): Observable<any> {
    return this.http.get(SERVER_API + '/booking/getAllServiceBooking/' + bookingId);
  }
}
