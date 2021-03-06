import React, {Component} from 'react';

export default class GoHome extends Component {

  constructor(props){
    super(props);

    this.goToHome = this.goToHome.bind(this)

    this.state = {
      history : props.history,
    }
  }


  goToHome(){

    this.state.history.push('/homepage')
  }

  render() {
    return (
      <div>
        <button onClick={this.goToHome}>
          Home
        </button>
      </div>

    );
  }
}

