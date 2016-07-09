import React from 'react';

function Application({ children }) {
    return React.Children.only(children);
}

export default Application;
