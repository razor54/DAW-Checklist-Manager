import React, {Component} from 'react';


const token_key = 'oidc.user:http://35.234.140.198/openid-connect-server-webapp:061b7558-463e-4adb-8a47-cf22f334f06b';

//props = {url, listname, loadItem}
export default class List extends Component {

  constructor(props){
    super(props);

    this.callback = props.callback
    this.errorCallback = props.errorCallback
    this.addList = this.addList.bind(this)
    this.dateHandler = this.dateHandler.bind(this)
    this.nameHandler = this.nameHandler.bind(this)

    this.state = {
      url: props.url,
      listname: '',
      date: null,
      hiddenMessage : true,
    }

  }

  addList(){

    const session = sessionStorage.getItem(token_key)
    if(!session) return this.errorCallback('no-access')
    const token =  JSON.parse(session)


    const body = {
      completionDate: this.state.date,
      name : this.state.listname,
    }

    if(!this.state.listname){
     return this.setState({hiddenMessage: false})
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
      .then(json =>this.callback(json.checklist))
      .catch(this.errorCallback)
  }

  nameHandler (event) {
    this.setState({listname: event.target.value, hiddenMessage:true})
  }


  dateHandler (event) {
    this.setState({date: event.target.value, hiddenMessage:true})
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
        <label>
          Completion Date:
          <input type="date" onChange={this.dateHandler} />
        </label>
        <div/>
        <button onClick={this.addList}>Add List</button>
        <div/>
        <div hidden={this.state.hiddenMessage}>Invalid name or date, please insert a valid list</div>
      </div>
    );
  }
}

