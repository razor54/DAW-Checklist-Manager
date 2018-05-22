import React, { Component } from 'react';
import { Link } from "react-router-dom";



export default class InvalidCredentials extends Component {

  constructor (props) {
    super(props);

    this.state = {

      errorType : props.errorType
    }
  }

  render() {
    return (
      <div>
        <div>The inserted username or password  are invalid, please insert valid credentials!</div>
        <Link to='/homepage'>Home Page</Link>
      </div>
    );
  }
}

