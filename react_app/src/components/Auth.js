import React, {Component} from 'react';
import userDTO from '../model/userDTO'


//props = {callback,buttonNam,url}

export default class Auth extends Component {

    constructor(props){
      super(props);

      this.usernameHandle = this.usernameHandle.bind(this)
      this.passwordHandle = this.passwordHandle.bind(this)
      this.authenticate = this.authenticate.bind(this)
      this.callback = props.callback

      this.state = {
        buttonName : props.buttonName,
        url : props.url,

        username : '',
        password : '',

        hiddenMessage:true
      };

    }


    authenticate(){

      if(!this.state.password || !this.state.username){
        this.setState({hiddenMessage: false})
        return
      }

      let formBody = new FormData()
      formBody.append('username',this.state.username)
      formBody.append('password',this.state.password)

      const data = {
        body : formBody,
        method : 'POST',
      }

      return fetch(this.state.url, data)
        .then(res=> {
          console.log(res.headers.get('Set-Cookie'))
          return res.json()
        })
        .then(json => {
          console.log(json)
          this.callback(null,userDTO(json))
        })
        .catch(this.errorCallback)
    }

    usernameHandle (event) {
      this.setState({username: event.target.value})
    }


    passwordHandle (event) {
      this.setState({password: event.target.value})
    }

    render() {
      return (
        <div>
          <label>
              Username:
              <input type="text" value={this.state.username} onChange={this.usernameHandle} />
          </label>
          <div></div>
          <label>
               Password:
              <input type="password" value={this.state.password} onChange={this.passwordHandle} />
          </label>
          <div></div>
          <button onClick={this.authenticate}>{this.state.buttonName} </button>
          <div></div>
          <div hidden={this.state.hiddenMessage}>Please insert valid username and password!!!</div>
        </div>
      );
    }
}

