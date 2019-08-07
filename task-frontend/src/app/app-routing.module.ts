import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GreetpageComponent } from './greetpage/greetpage.component';
import { WorkersComponent } from './workers/workers.component';

const routes: Routes = [
    { path: '', pathMatch: 'full', component: GreetpageComponent },
    { path: 'worker', component: WorkersComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
