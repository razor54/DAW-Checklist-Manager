import React, { Component } from 'react';
import List from './List'
import checklistListDTO from '../model/checklistListDTO'


export default class ChecklistListPage extends Component {

  constructor (props) {
    super(props);

    this.goToCheckList = this.goToCheckList.bind(this)

    this.state = {
      history : props.history,
    }
  }

  goToCheckList(checklistId){
    this.state.history.push(`/checklist/${checklistId}`)
  }

  render() {
    return (
      <div>
        <div>Your Checklists</div>
        <div align="center">
          <List url='http://localhost:8080/listing/checklists' loadItem={this.goToCheckList} loadDTO={checklistListDTO}/>
        </div>
      </div>
    );
  }
}


