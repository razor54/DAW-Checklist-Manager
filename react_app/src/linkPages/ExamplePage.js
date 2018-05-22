import React, { Component } from 'react';
import AuthForm from '../components/AuthForm';
import Item from '../components/Item';
import List from '../components/List'



//this page is just to try components, maybe future login page or home page


export default class ExamplePage extends Component {

  constructor (props) {
    super(props);

    this.afterRegister = this.afterRegister.bind(this)
    this.afterLogin = this.afterLogin.bind(this)
    this.listLoadItemDetais = this.listLoadItemDetais.bind(this)

    this.state = {
      itemUrl : ''
    }
  }

  afterRegister (error,result) {
    if(error) console.log('Not possible to register, please try again')

    console.log(result)
  }

  afterLogin (error,result) {
    if(error) console.log('Not possible to login, invalid listname or password')

    console.log(result)
  }

  listLoadItemDetais(itemLink){
    console.log(itemLink)
    this.setState({itemUrl: itemLink})
  }

  render () {
    return (
      <div align='center'>
        <div>
          Example Page
        </div>


        <AuthForm callback={this.state.afterRegister} buttonName='Register' url='http://localhost:8080/register'/>
        <AuthForm callback={this.state.afterLogin} buttonName='Login' url='http://localhost:8080/listing/checklists'/>

        <List url='http://localhost:8080/listing/checklist/1/items' username='nuno'  loadItem={this.listLoadItemDetais} />


        <Item url='http://localhost:8080/listing/checklist/item/1'> </Item>


      </div>
    );
  }

}



