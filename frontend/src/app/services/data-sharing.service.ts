import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { SystemConfig } from '../models/system-config/system-config.module';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {

  constructor() { }

  private configData = new BehaviorSubject<SystemConfig | null>(null);
  configData$ = this.configData.asObservable();

  setConfigData(data: SystemConfig):void
  {
    this.configData.next(data);
  }

  getConfigData(): SystemConfig | null
  {
    return this.configData.getValue();
  }

}
