import React, {Component} from 'react';
import itemDTO from '../model/checklistItemDTO';

//props = {url, listname}


export default class Auth extends Component {

  constructor(props){
    super(props);

    this.checkStateHandle = this.checkStateHandle.bind(this)
    this.pushChanges = this.pushChanges.bind(this)

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
    }

  }

  componentDidMount(){

    fetch(this.state.url, {headers:{authorization:'basic bnVubzoxMjM0NQ=='}})
      .then(res => res.json())
      .then(json => {
        this.setState({item: itemDTO(json)})
      })

  }


  checkStateHandle(event){

    let updatedItem = this.state.item;
    updatedItem.checkState = event.target.checked

    this.setState({item : updatedItem, hiddenButton:false});
  }

  pushChanges(){

    //do fetch with changes to api
    console.log('saved successfully')
    this.setState({hiddenButton:true});

  }

  render() {
    return (
      <div>
        <div> Item from List - {this.state.item.listId} </div>
        <div>Id = {this.state.item.id}</div>
        <div>Name = {this.state.item.name}</div>
        <div>Description = {this.state.item.description}</div>
        <div>State = <input type="checkbox" checked={this.state.item.checkState} onClick={this.checkStateHandle}/></div>
        <button hidden={this.state.hiddenButton}  onClick={this.pushChanges} > Save Changes</button>

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
