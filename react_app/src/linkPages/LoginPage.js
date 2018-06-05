import React, { Component } from 'react';
import { UserManager } from 'oidc-client'



export default class LoginPage extends Component {

  constructor(props) {
    super(props)

    this.callback = this.callback.bind(this)


    this.state = {
      history : props.history,
    }
  }

  componentDidMount(){

    if(localStorage.getItem('bearer-token')){
      return this.state.history.push('/homepage')
    }

    const mgr = new UserManager({})
    mgr.signinPopupCallback()
  }
}


