import React, { Component } from 'react';
import Auth from '../components/Auth'


export default class HomePage extends Component {

  constructor(props) {
    super(props)

    this.callback = this.callback.bind(this)


    this.state = {
      history : props.history,
    }
  }

  componentDidMount(){

    if(localStorage.getItem('session-id')){
      return this.state.history.push('/checklists')
    }

    this.state.history.push('/homepage')

  }

  callback(err,user){

    if(err){
      if(err==='TypeError: Failed to fetch')
        return this.state.history.push('/server-error')
      else
        return this.state.history.push('/invalid-listname-or-password')
    }

    localStorage.setItem('session-id',user.id)

    this.state.history.push('/checklists')
  }

  render() {
    return (
      <div >
        <div >Welcome Page</div>
        <div align="center">
          <Auth callback={this.callback} buttonName='Register' url='http://localhost:8080/auth/register'>
          </Auth>
        </div>
        <div align="center">
          <Auth callback={this.callback} buttonName='Login' url='http://localhost:8080/auth/login'>
          </Auth>
        </div>
      </div>
    );
  }
}


