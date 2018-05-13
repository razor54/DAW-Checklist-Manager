import React, { Component } from 'react';
import { Link } from "react-router-dom";



export default class InvalidPage extends Component {

  constructor (props) {
    super(props);

    this.state = {

      errorType : props.errorType
    }
  }

  render() {
    return (
      <div>
        <div>The {this.state.errorType} you are looking for it's not available, please proceed to home page!</div>
        <Link to='/homepage'>Home Page</Link>
      </div>
    );
  }
}

