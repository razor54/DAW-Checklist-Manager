import React, {Component} from 'react';
import itemDTO from '../model/checklistItemDTO';

//props = {url, listname}


export default class Auth extends Component {

  constructor(props){
    super(props);

    this.checkStateHandler = this.checkStateHandler.bind(this)
    this.nameHandler = this.nameHandler.bind(this)
    this.descriptionHandler = this.descriptionHandler.bind(this)

    this.pushChanges = this.pushChanges.bind(this)
    this.edit_save = this.edit_save.bind(this)
    this.errorCallback = props.errorCallback


    this.state = {
      url : props.url,
      listname : props.listname,
      item : {
        id: '',
        name: '',
        description: '',
        listId: '',
        selfLink: '',
        parentLink: '',
        checkState: false
      },
      hiddenButton : true,
      disabled : true,
      buttonTitle : 'Edit',
      changed : false,
    }

  }

  componentDidMount(){

    const session_id = localStorage.getItem('session-id')

    if(!session_id) return this.errorCallback('no-access')

    return fetch(this.state.url, {headers:{authorization:`basic ${session_id}`}})
      .then(res =>{
        if(!res.ok)this.errorCallback(res.status)
        return res.json()
      })
      .then(json => this.setState({item: itemDTO(json)}))
      .catch(this.errorCallback)
  }



  nameHandler(event){
    let updatedItem = this.state.item;
    updatedItem.name = event.target.value

    this.setState({item : updatedItem, changed:true});
  }

  descriptionHandler(event){
    let updatedItem = this.state.item;
    updatedItem.description = event.target.value

    this.setState({item : updatedItem, changed:true});
  }

  checkStateHandler(event){

    let updatedItem = this.state.item;
    updatedItem.checkState = event.target.checked

    this.setState({item : updatedItem, changed:true});
  }



  pushChanges(){

    const session_id = localStorage.getItem('session-id')

    const data = {
      headers:{
        authorization : `basic ${session_id}`,
        'Content-Type' : 'application/json',
      },
      method : 'PUT',
      body : JSON.stringify({
        id : this.state.item.id,
        list_id : this.state.item.listId,
        name : this.state.item.name,
        description : this.state.item.description,
        state : this.state.item.checkState,
      })
    }

    fetch('http://localhost:8080/listing/checklist/item', data)
      .then(res =>{
        if(!res.ok)this.errorCallback(res.status)
        return res.json()
      })
      .then(json => {
        this.setState({item: itemDTO(json)})
      })
      .catch(this.errorCallback)

    console.log('saved successfully')
  }

  edit_save(){

    if(this.state.disabled){
      this.setState({disabled: false, buttonTitle:'Save'})
    }

    else{
      if(this.state.changed){

        this.pushChanges()
      }

      this.setState({disabled: true, buttonTitle:'Edit', changed:false})
    }
  }

  render() {
    return (
      <div>
        <div> Item from List - {this.state.item.listId} </div>
        <div>Id = {this.state.item.id}</div>
        <div>
          {'Name '}
          <input type="text" disabled={this.state.disabled} value={this.state.item.name} onChange={this.nameHandler} />
        </div>
        <div>
          {'Description '}
          <input type="text" disabled={this.state.disabled} value={this.state.item.description} onChange={this.descriptionHandler} />
        </div>
        <div>
          {'State '}
          <input type="checkbox" disabled={this.state.disabled} value={this.state.item.name} onChange={this.checkStateHandler} />
        </div>
        <div>
          <button onClick={this.edit_save}>
            {this.state.buttonTitle}
          </button>
        </div>
      </div>
    );
  }
}




// EXAMPLE OF ITEM
/*


class ItemModel{

  constructor (id,name,description,checkState,listId,selfLink){
    this.id = id
    this.name = name
    this.description = description
    this.checkState = checkState
    this.listId = listId
    this.selfLink = selfLink
  }
}

 */
