import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { WorkersComponent } from './workers/workers.component';

const routes: Routes = [
    { path: '**', pathMatch: 'full', component: WorkersComponent },
    { path: '', pathMatch: 'full', component: WorkersComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
