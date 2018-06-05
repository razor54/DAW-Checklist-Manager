import React, { Component } from 'react';
import Item from '../components/Item'
import GoHome from '../components/GoHome'
import GoBack from '../components/GoBack'

export default class ChecklistItemPage extends Component {

  constructor (props) {
    super(props);

    this.errorCallback = this.errorCallback.bind(this)

    this.state = {
      history : props.history,
      itemId : props.match.params.itemId,
    }
  }

  errorCallback(err){
    if(err==='TypeError: Failed to fetch')
      this.state.history.push('/server-error')
    if(err==='no-access')
      this.state.history.push('/no-access-checklist-item')
    else
      this.state.history.push('/invalid-item')
  }


  render() {
    return (
      <div>
        <div>Your Item</div>
        <GoHome history={this.state.history}/>
        <GoBack history={this.state.history}/>
        <div align="center">
          <Item url={`http://localhost:9000/listing/checklist/item/${this.state.itemId}` } errorCallback={this.errorCallback}/>
        </div>
      </div>
    );
  }
}

