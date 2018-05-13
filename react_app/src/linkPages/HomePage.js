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
    this.state.history.push('/homepage')

  }

  callback(err,user){

    if(err){
      if(err==='TypeError: Failed to fetch')
        this.state.history.push('/server-error')
      else
        this.state.history.push('/invalid-username-or-password')
    }

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
      </div>
    );
  }
}


