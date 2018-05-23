import React, {Component} from 'react';

//TODO improve this because its is almost the same as checklistForm
export default class extends Component {

  constructor(props){
    super(props);

    this.callback = props.callback
    this.addList = this.addList.bind(this)
    this.nameHandler = this.nameHandler.bind(this)

    this.state = {
      url: props.url,
      listname: '',
      hiddenMessage : true,
    }

  }

  addList(){

    if(!this.state.listname){
      return this.setState({hiddenMessage: false})
    }

    const session_id = localStorage.getItem('session-id')

    const body = {
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
        .then(json =>this.callback(json.template))
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

