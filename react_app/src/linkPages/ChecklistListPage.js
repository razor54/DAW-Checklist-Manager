import React, { Component } from 'react';
import List from '../components/List'
import checklistListDTO from '../model/checklistListDTO'
import ChecklistForm from '../components/ChecklistForm'
import GoHome from '../components/GoHome'
import GoBack from '../components/GoBack'


export default class extends Component {

  constructor (props) {
    super(props);

    this.goToCheckList = this.goToCheckList.bind(this)
    this.errorCallback = this.errorCallback.bind(this)
    this.checkListFormCallback = this.checkListFormCallback.bind(this)


    this.state = {
      history : props.history,
    }
  }

  goToCheckList(checklistId){
    this.state.history.push(`/checklist/${checklistId}`)
  }

  errorCallback(err){
    if(err==='TypeError: Failed to fetch')
      this.state.history.push('/server-error')
    if(err==='no-access')
      this.state.history.push('/no-access-checklist-list')
    else
      this.state.history.push('/invalid-list')
  }

  checkListFormCallback(checklist){
    this.state.history.push('/checklist/'+ checklist.id)
  }


  render() {
    return (
      <div>
        <div>Your Checklists</div>
        <GoHome history={this.state.history}/>
        <GoBack history={this.state.history}/>
        <div align="right">
          <ChecklistForm url='http://35.234.136.49/api/listing/checklist' callback={this.checkListFormCallback} errorCallback={this.errorCallback}/>
        </div>
        <div align="center">
          <strong>Checklists List</strong>
          <List url='http://35.234.136.49/api/listing/checklists' loadItem={this.goToCheckList} loadDTO={checklistListDTO} errorCallback={this.errorCallback}/>
        </div>

      </div>
    );
  }
}


