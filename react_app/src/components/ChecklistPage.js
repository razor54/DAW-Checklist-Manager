import React, { Component } from 'react';
import checklistDTO from '../model/checklistDTO'
import List from './List'


export default class ChecklistsPage extends Component {

  constructor (props) {
    super(props);

    this.goToChecklistItem = this.goToChecklistItem.bind(this)

    this.state = {
      history : props.history,
      listId : props.match.params.listId,
    }
  }


  goToChecklistItem(itemId){
    this.state.history.push(`/checklist/${this.state.listId}/item/${itemId}`)
  }

  render() {
    return (
      <div>
        <div>Your Checklist</div>
        <div align="center">
          <List url={`http://localhost:8080/listing/checklist/${this.state.listId}/items`}  loadItem={this.goToChecklistItem} loadDTO={checklistDTO}/>
        </div>
      </div>
    );
  }
}


