import React, {Component} from 'react';


const token_key = 'oidc.user:http://35.234.140.198/openid-connect-server-webapp:061b7558-463e-4adb-8a47-cf22f334f06b';

//TODO improve this because its is almost the same as checklistForm
export default class extends Component {

  constructor(props){
    super(props);

    this.callback = props.callback
    this.errorCallback = props.errorCallback
    this.addList = this.addList.bind(this)
    this.nameHandler = this.nameHandler.bind(this)

    this.state = {
      url: props.url,
      listname: '',
      hiddenMessage : true,
    }

  }

  addList(){

    const session = sessionStorage.getItem(token_key)
    if(!session) return this.errorCallback('no-access')
    const token =  JSON.parse(session)

    if(!this.state.listname){
      return this.setState({hiddenMessage: false})
    }


    const body = {
      name : this.state.listname,
    }

    const header = {
      authorization:`${token.token_type} ${token.access_token}`,
      'Content-Type' : 'application/json',
    }

    const data = {
      body: JSON.stringify(body),
      method:'POST',
      headers: header,
    }

    return fetch(this.state.url,data)
      .then(res =>{
        if(!res.ok) return this.setState({hiddenMessage: false})
        return res.json()
      })
      .then(json =>this.callback(json.template))
      .catch(this.errorCallback)
  }

  nameHandler (event) {
    this.setState({listname: event.target.value, hiddenMessage:true})
  }


  render() {
    return (

      <div>
        <div/>
        <label>
          Name:
          <input type="text" value={this.state.listname} onChange={this.nameHandler} />
        </label>

        <div/>
        <button onClick={this.addList}>Add Template</button>
        <div/>
        <div hidden={this.state.hiddenMessage}>Invalid name. Please insert a valid Template</div>
      </div>
    );
  }
}

