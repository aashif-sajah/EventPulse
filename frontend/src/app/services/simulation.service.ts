import { HttpClient } from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemConfig } from '../models/system-config/system-config.module';

@Injectable({
  providedIn: 'root',
})
export class SimulationService {
  private baseUrl: string = 'http://localhost:8080/event-pulse';

  constructor(private httpClient: HttpClient, private zone:NgZone) {}

  startSimulation(config: SystemConfig): Observable<void> {
    return this.httpClient.post<void>(`${this.baseUrl}/start-simulation`, config);
  }

  stopSimulation(): Observable<void> {
    return this.httpClient.post<void>(`${this.baseUrl}/stop-simulation`, {});
  }

  fetchLogs(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.baseUrl}/logs`);
  }

  getLogStream(): Observable<any> {
    return new Observable((observer) => {
      const eventSource = new EventSource(`${this.baseUrl}/stream-logs`);

      eventSource.onmessage = (event) => {
        this.zone.run(() => {
          observer.next(JSON.parse(event.data)); // Parse the incoming JSON
        });
      };

      eventSource.onerror = (error) => {
        this.zone.run(() => {
          observer.error(error);
        });
      };

      return () => eventSource.close();
    });
  }
}
