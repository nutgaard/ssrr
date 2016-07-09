import React from "react";
import { renderToString } from 'react-dom/server';
import routes from './routes';
import { match, RouterContext } from 'react-router'


global.render = ((url, reply) => {
    // Note that req.url here should be the full URL path from
    // the original request, including the query string.
    console.log(url);
    const resp = (a, b) => {
        console.log(a + ' ' + b);
        reply(a, b);
    };


    match({ routes, location: url }, (error, redirectLocation, renderProps) => {
        if (error) {
            resp(500, error.message);
        } else if (redirectLocation) {
            resp(302, redirectLocation.pathname + redirectLocation.search);
        } else if (renderProps) {
            // You can also check renderProps.components or renderProps.routes for
            // your "not found" component or route respectively, and send a 404 as
            // below, if you're using a catch-all route.
            resp(200, renderToString(<RouterContext {...renderProps} />));
        } else {
            resp(404, 'Not found');
        }
    });

    return { statis: 101, body: 'ok' };
});