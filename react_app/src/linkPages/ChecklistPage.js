import React, { Component } from 'react';
import checklistDTO from '../model/checklistDTO'
import List from '../components/List'
import ChecklistItemForm from '../components/ChecklistItemForm'
import GoHome from '../components/GoHome'
import GoBack from '../components/GoBack'


export default class ChecklistsPage extends Component {

  constructor (props) {
    super(props);

    this.goToChecklistItem = this.goToChecklistItem.bind(this)
    this.errorCallback = this.errorCallback.bind(this)
    this.checklistItemFormCallback = this.checklistItemFormCallback.bind(this)

    this.state = {
      history : props.history,
      listId : props.match.params.listId,
    }
  }


  goToChecklistItem(itemId){
    this.state.history.push(`/checklist/${this.state.listId}/item/${itemId}`)
  }

  errorCallback(err){
    if(err==='TypeError: Failed to fetch')
      this.state.history.push('/server-error')
    if(err==='no-access')
      this.state.history.push('/no-access-checklist')
    else
      this.state.history.push('/invalid-list')
  }

  checklistItemFormCallback(checklistItem){
    this.state.history.push(`/checklist/${this.state.listId}/item/${checklistItem.id}`)
  }

  render() {
    return (
      <div>
        <div>Your Checklist</div>
        <GoHome history={this.state.history}/>
        <GoBack history={this.state.history}/>
        <div align="right">
          <ChecklistItemForm listId={this.state.listId} url='http://localhost:8080/listing/checklist/item' callback={this.checklistItemFormCallback}/>
        </div>
        <div align="center">
          <List url={`http://localhost:8080/listing/checklist/${this.state.listId}/items`}  loadItem={this.goToChecklistItem} loadDTO={checklistDTO} errorCallback={this.errorCallback} />
        </div>

      </div>
    );
  }
}


