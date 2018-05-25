import React, { Component } from 'react'
import templateDTO from '../model/templateItemListDTO'
import List from '../components/List'
import TemplateItemForm from '../components/templateItemForm'
import GoHome from '../components/GoHome'
import GoBack from '../components/GoBack'
import TemplateDetail from '../components/templateDetail'

export default class ChecklistsPage extends Component {

  constructor (props) {
    super(props)

    this.goToChecklistItem = this.goToChecklistItem.bind(this)
    this.errorCallback = this.errorCallback.bind(this)
    this.checklistItemFormCallback = this.checklistItemFormCallback.bind(this)
    this.addList = this.addList.bind(this)

    this.state = {
      history: props.history,
      listId: props.match.params.templateId,
      newListName: ''
    }
  }

  goToChecklistItem (itemId) {
    this.state.history.push(`/template/${this.state.listId}/item/${itemId}`)
  }

  errorCallback (err) {
    if (err === 'TypeError: Failed to fetch')
      this.state.history.push('/server-error')
    if (err === 'no-access')
      this.state.history.push('/no-access-checklist')
    else
      this.state.history.push('/invalid-list')
  }

  checklistItemFormCallback (checklistItem) {
    this.state.history.push(`/template/${this.state.listId}/item/${checklistItem.id}`)
  }

  addList () {

    if (!this.state.newListName ) {
      //TODO verify
      return
    }

    const session_id = localStorage.getItem('session-id')

    const listName= this.state.newListName;

    const header = {
      authorization: `basic ${session_id}`,
    }

    const data = {
      method: 'POST',
      headers: header,
    }

    if (session_id)
      return fetch(`http://localhost:8080/listing/checklist/template/${this.state.listId}?listName=${listName}`, data)
        .then(res => {
          if (!res.ok) return this.setState({hiddenMessage: false})
          return res.json()
        })
        .then((list) => this.state.history.push(`/checklist/${list.checklist.id}`))
        .catch(console.log)
  }

  render () {
    return (
      <div>
        <div>Your Template</div>
        <GoHome history={this.state.history}/>
        <GoBack history={this.state.history}/>
        <TemplateDetail id={this.state.listId} url={`http://localhost:8080/listing/template/${this.state.listId}`}
                        errorCallback={this.errorCallback} updateUrl={`http://localhost:8080/listing/template/`}/>
        <p/>
        <div>
          <strong>Add a list with this template</strong>
          <p/>
          <label>
            Name:
            <input type="text" required value={this.state.name}
                   onChange={(event) => this.setState({newListName: event.target.value})}/>
          </label>
          <button onClick={this.addList}>Add New List</button>
        </div>
        <div align="right">
          <TemplateItemForm listId={this.state.listId} url='http://localhost:8080/listing/template/item'
                            callback={this.checklistItemFormCallback}/>


        </div>
        <div align="center">
          <strong>Template Items</strong>
          <List url={`http://localhost:8080/listing/template/${this.state.listId}/items`}
                loadItem={this.goToChecklistItem} loadDTO={templateDTO} errorCallback={this.errorCallback}/>
        </div>

      </div>
    )
  }
}


