import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {
  projects = [];
  projectFilter: string;
  filteredProjects = [];

  constructor() { }

  ngOnInit(): void {
  }

  filterProjects() {
    this.filteredProjects = this.projects
        .filter(project => project.name.includes(this.projectFilter));
  }
}
