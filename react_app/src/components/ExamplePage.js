import React, { Component } from 'react';
import Auth from './Auth';
import Item from './Item';



//this page is just to try components, maybe future login page or home page


export default class ExamplePage extends Component {

  constructor (props) {
    super(props);

    this.state = {
      afterRegister: this.afterRegister.bind(this),
      afterLogin: this.afterLogin.bind(this),
    }

  }

  afterRegister (error,result) {
    if(error) console.log('Not possible to register, please try again')

    console.log(result)
  }

  afterLogin (error,result) {
    if(error) console.log('Not possible to login, invalid username or password')

    console.log(result)
  }

  render () {
    return (
      <div align='center'>
        <div>
          Example Page
        </div>


        <Auth callback={this.state.afterRegister} buttonName='Register' url='http://localhost:8080/register'/>
        <Auth callback={this.state.afterLogin} buttonName='Login' url='http://localhost:8080/listing/checklists'/>

        <Item listName={'exampleList'}> </Item>

      </div>
    );
  }

}



