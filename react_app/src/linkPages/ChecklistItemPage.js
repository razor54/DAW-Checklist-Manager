import React, { Component } from 'react';
import Item from '../components/Item'

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
    else
      this.state.history.push('/invalid-item')
  }


  render() {
    return (
      <div>
        <div>Your Item</div>
        <div align="center">
          <Item url={`http://localhost:8080/listing/checklist/item/${this.state.itemId}` } errorCallback={this.errorCallback}/>
        </div>
      </div>
    );
  }
}

