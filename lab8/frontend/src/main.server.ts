import { bootstrapApplication } from '@angular/platform-browser';
import { provideServerRendering } from '@angular/platform-server';
import { AppComponent } from './app/app.component';

const bootstrap = () => bootstrapApplication(AppComponent, {
  providers: [
    provideServerRendering()
  ]
});

export default bootstrap;
