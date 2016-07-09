import React from 'react';
import { Route, IndexRoute } from 'react-router';
import * as Pages from './pages/pages';

export default (
    <Route path="/" component={Pages.Application}>
        <IndexRoute component={Pages.Home} />
        <Route path="home" component={Pages.Home} />
        <Route path="about" component={Pages.About} />
    </Route>
);