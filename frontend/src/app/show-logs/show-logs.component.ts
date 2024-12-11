import { Component } from '@angular/core';
import { SimulationService } from '../services/simulation.service';
import { NgFor, NgIf } from '@angular/common';
import { Router } from '@angular/router';


@Component({
  selector: 'app-show-logs',
  standalone: true,
  imports: [NgFor, NgIf],
  templateUrl: './show-logs.component.html',
  styleUrls: ['./show-logs.component.css'],
})
export class ShowLogsComponent {
  simulationIds: number[] = []; // List of all simulation IDs
  logs: any[] = []; // Logs for the selected simulation
  selectedSimulationId: number | null = null; // Currently selected simulation ID
  currentIndex: number = 0; // Index to track which simulation ID is displayed

  constructor(private simulationService: SimulationService, private router:Router) {}

  // Fetch all simulation IDs from the backend
  fetchSimulationIds(): void {
    this.simulationService.getSimulationIds().subscribe((ids: number[]) => {
      this.simulationIds = ids;
      if (ids.length > 0) {
        this.selectedSimulationId = ids[0]; // Default to the first ID
        this.fetchLogsForSimulation(this.selectedSimulationId);
      }
    });
  }

  // Fetch logs for a specific simulation ID
  fetchLogsForSimulation(id: number): void {
    this.selectedSimulationId = id;
    this.simulationService.getLogsBySimulationId(id).subscribe((logs: any[]) => {
      this.logs = logs;
    });
  }

  // Show the next simulation's logs
  showNextSimulation(): void {
    if (this.currentIndex < this.simulationIds.length - 1) {
      this.currentIndex++;
      const nextId = this.simulationIds[this.currentIndex];
      this.fetchLogsForSimulation(nextId);
    }
  }

  // Show the previous simulation's logs
  showPreviousSimulation(): void {
    if (this.currentIndex > 0) {
      this.currentIndex--;
      const prevId = this.simulationIds[this.currentIndex];
      this.fetchLogsForSimulation(prevId);
    }
  }

  backToMainPage(): void {
    
      this.simulationIds = [];
      this.logs = [];
      this.selectedSimulationId = null;
      this.currentIndex = 0;
      this.router.navigate(['/mainpage']);

    
  }
}
