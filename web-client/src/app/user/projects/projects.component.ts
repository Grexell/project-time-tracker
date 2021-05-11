import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../api.service";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {
  projects = [];
  projectFilter: string;
  filteredProjects = [];

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadProjects();
  }

  private loadProjects() {
    this.api.loadProjects().subscribe(projects => {
      this.projects = projects;
      this.projects.forEach(project => project.task = {})
      this.filterProjects();
    });
  }

  loadTasks(project: any) {
    this.api.loadTasks(project.id).subscribe(tasks => project.tasks = tasks);
  }

  filterProjects() {
    this.filteredProjects = this.projects
        .filter(project => !this.projectFilter || project.name.includes(this.projectFilter));
  }

  saveTask(project, task) {
    const task$ = task.id ? this.api.updateTask(project.id, task) : this.api.createTask(project.id, task);
    task$.subscribe(() => this.loadTasks(project));
    project.task = {};
  }
}
