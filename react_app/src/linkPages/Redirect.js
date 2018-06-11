import React, { Component } from 'react';
import { UserManager } from 'oidc-client'

var mitreIDsettings = {
  authority: 'http://localhost:8080/openid-connect-server-webapp',
  client_id: 'react-web-app',
  client_secret: 'XifoBArqYrf9mqXaYaJwRWTSmvEbhekyJ22hPTO7eQbbMNxKa6Jv4MTRThimShETdxt_yUFhVK9CFbeU4KOXKQ',
  grant_type:'authorization_code',
  redirect_uri: 'http://localhost:3000/redirect',
  popup_redirect_uri: 'http://localhost:3000/redirect',
  post_logout_redirect_uri: 'http://localhost:3000/login',
  response_type: 'token id_token',
  scope: 'openid email profile',
  silent_redirect_uri: 'http://localhost:3000/redirect',
  automaticSilentRenew: true,
  filterProtocolClaims: true,
  loadUserInfo: true
}


const manager = new UserManager(mitreIDsettings)

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


