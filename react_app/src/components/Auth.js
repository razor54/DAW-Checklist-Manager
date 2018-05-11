import React, {Component} from 'react';



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
          //this.callback(res);
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
                  <input type="text" value={this.state.username} onChange={this.usernameHandle} />
              </label>
              <div></div>
              <label>
                   Password:
                  <input type="password" value={this.state.password} onChange={this.passwordHandle} />
              </label>
            <div></div>
            <input type="submit" value={this.state.buttonName} />
          </form>
      );
    }
}

