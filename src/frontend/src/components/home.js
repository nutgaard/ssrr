import React, {Component} from "react";

const delay = (data, time) => new Promise((resolve) => {
    setTimeout(() => {
        resolve(data);
    }, time);
});

function LoadingGuard({ isLoaded, children }) {
    if (!isLoaded) {
        return <h1>Loading...</h1>
    }
    return React.Children.only(children);
}

class Home extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: null,
            isLoaded: false,
            error: false
        };
    }

    componentDidMount() {
        this.setState({ isLoading: true });
        fetch('/api/test')
            .then((resp) => resp.json())
            .then((resp) => delay(resp, 5000))
            .then((resp) => this.setState({ error: false, data: resp, isLoaded: true }))
            .catch((resp) => this.setState({ error: true, data: resp, isLoaded: true }));
    }

    render() {
        return (
            <LoadingGuard isLoaded={this.state.isLoaded}>
                <div className="home">
                    {JSON.stringify(this.state.data)}<br />
                    {JSON.stringify(this.state.isLoaded)}<br />
                    {JSON.stringify(this.state.error)}<br />
                </div>
            </LoadingGuard>
        );
    }
}

export default Home;
