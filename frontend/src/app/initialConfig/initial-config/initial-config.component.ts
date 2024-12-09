import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SystemConfig } from '../../models/system-config/system-config.module';
import { DataSharingService } from '../../services/data-sharing.service';

@Component({
  selector: 'app-initial-config',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './initial-config.component.html',
  styleUrl: './initial-config.component.css'
})
export class InitialConfigComponent {

  config: SystemConfig = {
    ticketPoolCapacity: 100,
    ticketReleaseRate: 1,
    consumerRetrievalRate: 1,
    totalTicket: 500,
  };
  constructor(private dataSharingService: DataSharingService, private router: Router) { }

  startSimulation(): void {
    console.log(this.config)
    this.dataSharingService.setConfigData(this.config);
    this.router.navigate(['/mainpage']);
  }


}

