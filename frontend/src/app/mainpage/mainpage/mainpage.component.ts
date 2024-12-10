import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SystemConfig } from '../../models/system-config/system-config.module';
import { DataSharingService } from '../../services/data-sharing.service';
import { SimulationService } from '../../services/simulation.service';

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
      console.log('message');
      this.fetchLogs();
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
    alert('Simulation stopped');
    this.router.navigate(['/']);
    this.simulationService.stopSimulation().subscribe(() => {
      console.log('Simulation stopped');
    });
  }
}
