import React, {Component} from 'react';


//props = {url, username, loadItem}
export default class List extends Component {

  constructor(props){
    super(props);

    this.listToHtml = this.listToHtml.bind(this)
    this.loadItem =  props.loadItem
    this.loadDTO = props.loadDTO


    this.state = {
      url: props.url,
      username: props.username,
      list: {
        items: [], // item = {id, name,selfLink}
        selfLink : ''
      }
    }

  }

  componentDidMount(){

    fetch(this.state.url, {headers:{authorization:'basic bnVubzoxMjM0NQ=='}})
      .then(res => res.json())
      .then(json => {
        this.setState({list: this.loadDTO(json)})
      })
  }



  listToHtml(){
    return this.state.list.items.map(item =>
      <div  key={item.id}><button onClick={()=>this.loadItem(item.id)}> {item.name} </button></div>
    )
  }




  render() {
    return (

      <ul>{this.listToHtml()}</ul>

    );
  }
}



// EXAMPLE OF LIST ITEMS
/*
  items : []
  selfLink : string
}*/
