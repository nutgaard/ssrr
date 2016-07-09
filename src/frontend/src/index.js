import React from "react";
import { render } from "react-dom";
import routes from './routes';
import { Router, Route, browserHistory } from "react-router";

const router = (
    <Router history={browserHistory}>
        {routes}
    </Router>
);

render(router, document.querySelector('#application'));
    