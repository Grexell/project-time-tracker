import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../api.service";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {
  projects = [];
  selectedProject: any;
  tasks: any[];
  projectFilter: string;
  filteredProjects = [];

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadProjects();
  }

  private loadProjects() {
    this.api.loadProjects().subscribe(projects => this.projects = projects);
  }

  loadTasks() {
    this.tasks = [];
    this.api.loadTasks(this.selectedProject.id).subscribe(tasks => this.tasks = tasks);
  }

  filterProjects() {
    this.filteredProjects = this.projects
        .filter(project => project.name.includes(this.projectFilter));
  }
}
