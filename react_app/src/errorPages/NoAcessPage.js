import React, { Component } from 'react';
import { Link } from "react-router-dom";



export default class NoAcessPage extends Component {

  constructor (props) {
    super(props);

    this.state = {

      errorType : props.errorType
    }
  }

  render() {
    return (
      <div>
        <div>You need to be authenticate to access this {this.state.errorType}, please proceed to home page!</div>
        <Link to='/loginpage'>Login Page</Link>
      </div>
    );
  }
}

