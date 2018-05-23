import React, { Component } from 'react';


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

    if(!localStorage.getItem('session-id')){
      return this.state.history.push('/loginpage')
    }

    this.state.history.push('/homepage')

  }

  goToChecklists(){
    this.state.history.push('/checklists')
  }

  goToTemplates(){
    this.state.history.push('/templates')
  }

  logout(){
    localStorage.removeItem('session-id')
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
          <button onClick={this.goToChecklists}> My Checklists</button>
        </div>
        <div align="center">
          <button onClick={this.goToTemplates}> My Templates</button>
        </div>
      </div>
    );
  }
}


