import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SystemConfig } from '../../models/system-config/system-config.module';
import { DataSharingService } from '../../services/data-sharing.service';
import { SimulationService } from '../../services/simulation.service';
import { Subscription } from 'rxjs';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-mainpage',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './mainpage.component.html',
  styleUrl: './mainpage.component.css',
})
export class MainpageComponent {
  config!: SystemConfig;
  logs: string[] = [];
  ticketsProduced = 0;
  ticketsConsumed = 0;
  currentTickets = 0;
  startTime!: Date | null;

  private logStreamSubscription!: Subscription;

  constructor(
    private dataSharingService: DataSharingService,
    private simulationService: SimulationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.config = this.dataSharingService.getConfigData()!;
    console.log(this.config);
    this.startSimulation();
  }

  startSimulation(): void {
    this.simulationService.startSimulation(this.config).subscribe(() => {
      console.log('message from start simulation');
      /* this.fetchLogs(); */
    });

    this.logStreamSubscription = this.simulationService.getLogStream().subscribe((data) => {

      if (data.type === 'log') {
        console.log("data coming from log stream");
        this.logs.push(data.message);
        /* console.log(data.message); */
      } else if (data.type === 'stats') {
        this.ticketsProduced = data.ticketsProduced | 0;
        this.ticketsConsumed = data.ticketsConsumed | 0;
        this.currentTickets = data.currentTicketsInPool | 0;
      }
    });
    this.startTime = new Date();
  }

  fetchLogs(): void {
    setInterval(() => {
      this.simulationService.fetchLogs().subscribe((data: string[]) => {
        this.logs = data;
      });
    }, 2000);
  }

  stopSimulation(): void {
    if (this.logStreamSubscription) {
      this.logStreamSubscription.unsubscribe();
    }
    this.simulationService.stopSimulation().subscribe(() => {
      console.log('Simulation stopped');
    });

    this.logs = [];
    this.ticketsProduced = 0;
    this.ticketsConsumed = 0;
    this.currentTickets = 0;
    this.startTime = null;

  }

  restartSimulation(): void {
    this.stopSimulation();
    this.router.navigate(['/']);
  }

  checkLogs(): void {
    this.router.navigate(['/showLogs']);
  }
}
