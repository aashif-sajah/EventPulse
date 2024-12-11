import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SystemConfig } from '../../models/system-config/system-config.module';
import { DataSharingService } from '../../services/data-sharing.service';
import { SimulationService } from '../../services/simulation.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-mainpage',
  standalone: true,
  imports: [],
  templateUrl: './mainpage.component.html',
  styleUrl: './mainpage.component.css',
})
export class MainpageComponent {
  config!: SystemConfig;
  logs: string[] = [];
  ticketsProduced = 0;
  ticketsConsumed = 0;
  currentTickets = 0;

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
    alert('Simulation stopped');
    this.router.navigate(['/']);
    this.simulationService.stopSimulation().subscribe(() => {
      console.log('Simulation stopped');
    });
  }
}
