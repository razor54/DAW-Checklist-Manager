import React, {Component} from 'react';



export default class Auth extends Component {

    constructor(props){
      super(props);

      this.state = {
        callback : props.callback,
        buttonName : props.buttonName,
        url : props.url,
        username : '',
        password : '',
        usernameHandle : this.usernameHandle.bind(this),
        passwordHandle : this.passwordHandle.bind(this),
        authenticate : this.authenticate.bind(this),


      };

    }


    authenticate(){

      const userCredentials = {
        username : this.state.username,
        password : this.state.password
      }

      const data = {
        body : JSON.stringify(userCredentials),
        method : 'POST'
      }

      fetch(this.state.url, data).then(
        (res) =>{

          console.log(res);
          //this.state.callback(res);
        }
      )
    }

    usernameHandle (event) {
      this.setState({username: event.target.value})
    }


    passwordHandle (event) {
      this.setState({password: event.target.value})
    }

    render() {
      return (
          <form onSubmit={this.state.authenticate}>
              <label>
                  Username:
                  <input type="text" value={this.state.username} onChange={this.state.usernameHandle} />
              </label>
              <div></div>
              <label>
                   Password:
                  <input type="text" value={this.state.password} onChange={this.state.passwordHandle} />
              </label>
            <div></div>
            <input type="submit" value={this.state.buttonName} />
          </form>
      );
    }
}

