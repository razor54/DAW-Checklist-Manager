import React, { Component } from 'react';
import Auth from './Auth'


export default class HomePage extends Component {

  constructor(props) {
    super(props)

    this.goToChecklists = this.goToChecklists.bind(this)

    this.state = {
      history : props.history,
    }
  }

  componentDidMount(){
    this.state.history.push('/homepage')

  }
  goToChecklists(){
    this.state.history.push('/checklists')
  }

  render() {
    return (
      <div >
        <div >Welcome Page</div>
        <div align="center">
          <Auth callback={this.goToChecklists} buttonName='Register' url='http://localhost:8080/register'>
          </Auth>
        </div>
      </div>
    );
  }
}


