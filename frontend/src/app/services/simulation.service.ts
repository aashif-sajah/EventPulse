import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemConfig } from '../models/system-config/system-config.module';

@Injectable({
  providedIn: 'root',
})
export class SimulationService {
  private baseUrl: string = 'http://localhost:8080/event-pulse';

  constructor(private httpClient: HttpClient) {}

  startSimulation(config: SystemConfig): Observable<void> {
    return this.httpClient.post<void>(`${this.baseUrl}/start-simulation`, config);
  }

  stopSimulation(): Observable<void> {
    return this.httpClient.post<void>(`${this.baseUrl}/stop-simulation`, {});
  }

  fetchLogs(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.baseUrl}/logs`);
  }
}
