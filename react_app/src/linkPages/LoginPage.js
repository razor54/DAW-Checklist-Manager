import React, { Component } from 'react';
import AuthForm from '../components/AuthForm'


export default class LoginPage extends Component {

  constructor(props) {
    super(props)

    this.callback = this.callback.bind(this)


    this.state = {
      history : props.history,
    }
  }

  componentDidMount(){

    if(localStorage.getItem('session-id')){
      return this.state.history.push('/homepage')
    }
  }

  callback(error,user){

    console.log(error)
    if(error){
      if(error==='TypeError: Failed to fetch')
        return this.state.history.push('/server-error')
      else
        return this.state.history.push('/invalid-listname-or-password')
    }

    localStorage.setItem('session-id',user.id)

    this.state.history.push('/homepage')
  }

  render() {
    return (
      <div >
        <div >Login Page</div>
        <div align="center">
          <AuthForm callback={this.callback} buttonName='Register' url='http://localhost:8080/auth/register'>
          </AuthForm>
        </div>
        <div align="center">
          <AuthForm callback={this.callback} buttonName='Login' url='http://localhost:8080/auth/login'>
          </AuthForm>
        </div>
      </div>
    );
  }
}


