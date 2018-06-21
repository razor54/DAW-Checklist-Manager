import React, { Component } from 'react';


const token_key = 'oidc.user:http://35.234.140.198/openid-connect-server-webapp:061b7558-463e-4adb-8a47-cf22f334f06b';


export default class LoginPage extends Component {

  constructor(props) {
    super(props)

    this.goToChecklists = this.goToChecklists.bind(this)
    this.logout = this.logout.bind(this)
    this.goToTemplates = this.goToTemplates.bind(this)

    this.state = {
      history : props.history,
    }
  }

  componentDidMount(){

    const session = sessionStorage.getItem(token_key)
    if(!session) return this.state.history.push('/loginpage')
    const token =  JSON.parse(session)

    fetch('http://35.234.136.49/api/listing/',{headers:{authorization:`${token.token_type} ${token.access_token}`}})
      .then(res=> {

        if(!res.ok){
          return this.logout()
        }
        this.state.history.push('/homepage')

      })
  }

  goToChecklists(){
    this.state.history.push('/checklists')
  }

  goToTemplates(){
    this.state.history.push('/templates')
  }

  logout(){
    sessionStorage.removeItem(token_key)
    this.state.history.push('/loginpage')
  }

  render() {
    return (
      <div >
        <div >Welcome Page</div>
        <div>
          <button onClick={this.logout}> Logout</button>
        </div>
        <div align="center">
          <strong>Go To</strong>
          <div/>
          <button onClick={this.goToTemplates}> My Templates</button>
          <div/>
          <button onClick={this.goToChecklists}> My Checklists</button>
        </div>
      </div>
    );
  }
}


