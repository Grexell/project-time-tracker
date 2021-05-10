import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-salary-modal',
  templateUrl: './bonus-modal.component.html',
  styleUrls: ['./bonus-modal.component.scss']
})
export class BonusModalComponent implements OnInit {
  projectComparator = (p1, p2) => p1.id === p2.id;

  bonus = { project: null, amount: 0 };

  constructor(public dialogRef: MatDialogRef<BonusModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

  save() {
    this.dialogRef.close(this.bonus);
  }

}
