import React, { Component } from 'react';
import { Link } from "react-router-dom";


export default class ServerErrorPage extends Component {

  constructor (props) {
    super(props);

    this.state = {}
  }

  render() {
    return (
      <div>
        <div>Our internal server it's down, please try again another time!</div>
        <Link to='/homepage'>Home Page</Link>
      </div>
    );
  }
}

