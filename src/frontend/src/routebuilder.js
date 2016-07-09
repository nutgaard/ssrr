import React from "react";
import { Router, Route, browserHistory } from "react-router";

export function buildRouter(routes, components) {
    let { children, ...restprops } = routes;

    if (children) {
        children = children.map((child) => buildRouter(child, components));
    }

    const props = {
        ...restprops,
        key: restprops.path || 'component',
        component: components[routes.component]
    };

    return (
        <Router history={browserHistory} key="router">
            <Route {...props}>{children}</Route>
        </Router>
    );
}