import React, { Component } from 'react'

//props = {url, listname, loadItem}
export default class extends Component {

  constructor (props) {
    super(props)

    this.getList = this.getList.bind(this)
    this.nameHandler = this.nameHandler.bind(this)
    this.updateList = this.updateList.bind(this)
    this.errorCallback = props.errorCallback
    this.updateUrl = props.updateUrl

    this.state = {
      hiddenMessage: true,
      name: '',
      edit: false
    }

  }

  getList () {

    const session_id = localStorage.getItem('session-id')

    const header = {
      authorization: `basic ${session_id}`,
      'Content-Type': 'application/json',
    }

    const data = {
      method: 'GET',
      headers: header,
    }

    if (session_id)
      return fetch(this.props.url, data)
        .then(res => {
          if (!res.ok) return this.setState({hiddenMessage: false})
          return res.json()
        })
        .then(json => json.template)
        .then(list => {
          this.setState(
            {
              name: list.name,
            })

        })
        .catch(this.errorCallback)
  }

  nameHandler (event) {
    this.setState({name: event.target.value, hiddenMessage: true})
  }



  updateList () {
    const id = this.props.id
    const name = this.state.name
    const completionDate = this.state.completionDate

    const session_id = localStorage.getItem('session-id')

    const body = {
      completionDate,
      name,
      id
    }

    const header = {
      authorization: `basic ${session_id}`,
      'Content-Type': 'application/json',
    }

    const data = {
      body: JSON.stringify(body),
      method: 'PUT',
      headers: header,
    }

    if (session_id)
      return fetch(this.updateUrl, data)
        .then(res => {
          if (!res.ok) return this.setState({hiddenMessage: false})
          return res.json()
        })
        .then(json => json.template)
        .then(checklist => this.setState(
          {
            edit: false,
            name: checklist.name,
            completionDate: checklist.completionDate
          }))
        .catch(this.errorCallback)

  }

  componentDidMount () {
    this.getList()
  }

  render () {
    const nameComponent = this.state.name && !this.state.edit ? <strong>Name: {this.state.name}</strong> :
      <label>
        Name:
        <input type="text" value={this.state.name} onChange={this.nameHandler}/>
      </label>

    const updateOrEdit = this.state.edit ? <button onClick={this.updateList}>Update Template</button> :
      <button onClick={() => this.setState({edit: true})}>Edit Template</button>
    return (

      <div>
        <p>List Info: </p>
        <div/>
        {nameComponent}
        <div/>
        {updateOrEdit}
        <div/>
        <div hidden={this.state.hiddenMessage}>Invalid name or date, please insert a valid list</div>
      </div>
    )
  }
}