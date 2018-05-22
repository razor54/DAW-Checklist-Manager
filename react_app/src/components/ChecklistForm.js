import React, {Component} from 'react';


//props = {url, listname, loadItem}
export default class List extends Component {

  constructor(props){
    super(props);

    this.callback = props.callback
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

    if(!this.state.listname){
     return this.setState({hiddenMessage: false})
    }

    const session_id = localStorage.getItem('session-id')

    const body = {
      completionDate: this.state.date,
      name : this.state.listname,
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
        .then(json =>this.callback(json.checklist))
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

