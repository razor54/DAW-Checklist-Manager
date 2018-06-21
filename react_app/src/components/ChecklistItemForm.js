import React, {Component} from 'react';
const token_key = 'oidc.user:http://35.234.140.198/openid-connect-server-webapp:061b7558-463e-4adb-8a47-cf22f334f06b';

//props = {url, listname, loadItem}
export default class List extends Component {

  constructor(props){
    super(props);

    this.callback = props.callback
    this.errorCallback = props.errorCallback
    this.addList = this.addList.bind(this)
    this.descriptionHandler = this.descriptionHandler.bind(this)
    this.nameHandler = this.nameHandler.bind(this)

    this.state = {
      url: props.url,
      listId: props.listId,
      name : '',
      description : '',
      hiddenMessage : true,
    }

  }

  addList(){

    const session = sessionStorage.getItem(token_key)
    if(!session) return this.errorCallback('no-access')
    const token =  JSON.parse(session)

    if(!this.state.listId || !this.state.name || !this.state.description){
      return this.setState({hiddenMessage: false})
    }

    const body = {
      list_id : this.state.listId,
      name : this.state.name,
      description : this.state.description,
      state : false,
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
      .then(json =>this.callback(json.checklistItem))
      .catch(this.errorCallback)
  }

  nameHandler (event) {
    this.setState({name: event.target.value, hiddenMessage:true})
  }

  descriptionHandler (event) {
    this.setState({description: event.target.value, hiddenMessage:true})
  }



  render() {
    return (

      <div>
        <div/>
        <label>
          Name:
          <input type="text" value={this.state.name} onChange={this.nameHandler} />
        </label>
        <div/>
        <label>
          Description:
          <input type="text" value={this.state.description} onChange={this.descriptionHandler} />
        </label>
        <div/>
        <button onClick={this.addList}>Add Item</button>
        <div/>
        <div hidden={this.state.hiddenMessage}>Invalid name or description, please insert a valid item</div>
      </div>
    );
  }
}

