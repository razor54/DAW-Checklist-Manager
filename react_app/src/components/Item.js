import React, {Component} from 'react';



export default class Auth extends Component {

  constructor(props){
    super(props);

    this.state = {
      url : props.url,
      listName : props.listName,

      checkStateHandle: this.checkStateHandle.bind(this),
      pushChanges: this.pushChanges.bind(this),


      id : '12345',
      name : 'Item',
      description : 'Item just for test',
      checkState : false,
      hiddenButton : true,
    };

  }


  checkStateHandle(event){
    this.setState({checkState: event.target.checked});
    this.setState({hiddenButton:false});

    console.log('checkbutton changed')

  }

  pushChanges(){

    //do fetch with changes to api
    console.log('saved successfully')
    this.setState({hiddenButton:true});

  }

  render() {
    return (
      <div>
        <div> Item from List - {this.state.listName} </div>
        <div>Id = {this.state.id}</div>
        <div>Name = {this.state.name}</div>
        <div>Description = {this.state.description}</div>
        <div>State = <input type="checkbox" checked={this.state.checkState} name="State" onChange={this.state.checkStateHandle}/></div>

        <button hidden={this.state.hiddenButton}  onClick={this.state.pushChanges} > Save Changes</button>

      </div>

    );
  }
}

