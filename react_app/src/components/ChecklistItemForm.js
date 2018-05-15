import React, {Component} from 'react';

//props = {url, listname, loadItem}
export default class List extends Component {

  constructor(props){
    super(props);

    this.callback = props.callback
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

    if(!this.state.listId || !this.state.name || !this.state.description){
      return this.setState({hiddenMessage: false})
    }

    const session_id = localStorage.getItem('session-id')

    const body = {
      list_id : this.state.listId,
      name : this.state.name,
      description : this.state.description,
      state : false,
    }

    const header = {
      authorization : `basic ${session_id}`,
      'Content-Type' : 'application/json',
    }

    const data = {
      body: JSON.stringify(body),
      method:'POST',
      headers: header,
    }

    if(session_id)
      return fetch(this.state.url,data)
        .then(res =>{
          if(!res.ok) return this.setState({hiddenMessage: false})
          return res.json()
        })
        .then(json =>this.callback(json.checklistItem))
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

