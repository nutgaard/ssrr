import React from "react";
import {render} from "react-dom";
import routesConfig from './routes.json';
import { buildRouter } from './routebuilder';
import { Router, Route, browserHistory } from "react-router";

// pages
import Application from './pages/application';
import Home from './pages/home';
import About from './pages/about';

const router = buildRouter(routesConfig.routes, { Application, Home, About });

render(router, document.querySelector('#application'));
    