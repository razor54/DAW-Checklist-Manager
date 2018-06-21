import React, { Component } from 'react';
import { UserManager } from 'oidc-client'

const manager = new UserManager({})

export default class Redirect extends Component {

  constructor(props) {
    super(props)
    this.state = {}
  }

  componentDidMount(){
    manager.signinPopupCallback()
  }

  render() {
    return (
      <div >
        <div >Redirect Page</div>
      </div>
    );
  }



}


