import React, {Component} from 'react';


export default class GoHome extends Component {

  constructor(props){
    super(props);

    this.goBack = this.goBack.bind(this)

    this.state = {
      history : props.history,
    }
  }

  goBack(){
    this.state.history.goBack()
  }

  render() {
    return (
      <div>
        <button onClick={this.goBack}>
          Back
        </button>
      </div>

    );
  }
}

