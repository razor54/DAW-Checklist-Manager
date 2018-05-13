import React, { Component } from 'react';
import Item from './Item'

export default class ChecklistItemPage extends Component {

  constructor (props) {
    super(props);

    this.state = {
      history : props.history,
      itemId : props.match.params.itemId,
    }
  }


  render() {
    return (
      <div>
        <div>Your Item</div>
        <div align="center">
          <Item url={`http://localhost:8080/listing/checklist/item/${this.state.itemId}`}/>
        </div>
      </div>
    );
  }
}

