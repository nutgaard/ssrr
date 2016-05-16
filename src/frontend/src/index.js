import React from "react";
import {render} from "react-dom";
import {Router, Route, browserHistory} from "react-router";

function Application({ children }) {
    return React.Children.only(children);
}

function Home() {
    return (
        <h1>home</h1>
    );
}

function About() {
    return (
        <h1>about</h1>
    );
}

function Routes() {
    return (
        <Router history={browserHistory}>
            <Route component={Application}>
                <Route path="home" component={Home}/>
                <Route path="about" component={About}/>
                <Route path="*" component={Home}/>
            </Route>
        </Router>
    );
}

render(<Routes />, document.querySelector('#application'));
