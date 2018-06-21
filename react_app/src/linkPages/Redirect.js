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


