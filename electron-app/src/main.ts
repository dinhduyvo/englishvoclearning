import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import "reflect-metadata";
import {createConnection} from "typeorm";
import {User} from "./entity/User";

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

createConnection().then(async connection => {
  debugger
  console.log("Inserting a new user into the database...");
  const user = new User();
  user.id = 1;
  user.firstName = "Timber";
  user.lastName = "Saw";
  user.age = 25;
  await connection.manager.save(user);
  console.log("Saved a new user with id: " + user.id);

  console.log("Loading users from the database...");
  const users = await connection.manager.find(User);
  console.log("Loaded users: ", users);

  console.log("Here you can setup and run express/koa/any other framework.");

}).catch(error => console.log(error));

console.log('Run here!');


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
