import React, { Component } from 'react';
import { UserManager } from 'oidc-client'

var mitreIDsettings = {
  authority: 'http://35.234.140.198/openid-connect-server-webapp',
  client_id: '061b7558-463e-4adb-8a47-cf22f334f06b',
  client_secret: 'XifoBArqYrf9mqXaYaJwRWTSmvEbhekyJ22hPTO7eQbbMNxKa6Jv4MTRThimShETdxt_yUFhVK9CFbeU4KOXKQ',
  grant_type:'authorization_code',
  redirect_uri: 'http://35.234.136.49/redirect',
  popup_redirect_uri: 'http://35.234.136.49/redirect',
  post_logout_redirect_uri: 'http://35.234.136.49/login',
  response_type: 'token id_token',
  scope: 'openid email profile',
  silent_redirect_uri: 'http://35.234.136.49/redirect',
  automaticSilentRenew: true,
  filterProtocolClaims: true,
  loadUserInfo: true
}

const token_key = 'oidc.user:http://35.234.140.198/openid-connect-server-webapp:061b7558-463e-4adb-8a47-cf22f334f06b';

const manager = new UserManager(mitreIDsettings)



export default class LoginPage extends Component {

  constructor(props) {
    super(props)

    this.login = this.login.bind(this)

    this.state = {
      history : props.history,
    }
  }

  login () {

    manager.signinPopup()
      .then(user => {
        manager.storeUser(user)
        return this.state.history.push('/homepage')
      })
      .catch(error=> console.log(error))
  }



  componentDidMount(){
    if(sessionStorage.getItem(token_key)){
      return this.state.history.push('/homepage')
    }
  }

  render() {
    return (
      <div >
        <div >Login Page</div>
        <button title='Login' onClick={this.login}>Login</button>
      </div>
    );
  }



}


