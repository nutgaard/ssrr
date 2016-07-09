import React from "react";
import { renderToString } from 'react-dom/server';
import routes from './routes';
import { match, RouterContext } from 'react-router'


global.render = ((url, resp) => {
    match({ routes, location: url }, (error, redirectLocation, renderProps) => {
        if (error) {
            resp(500, error.message);
        } else if (redirectLocation) {
            resp(302, redirectLocation.pathname + redirectLocation.search);
        } else if (renderProps) {
            resp(200, renderToString(<RouterContext {...renderProps} />));
        } else {
            resp(404, 'Not found');
        }
    });
});