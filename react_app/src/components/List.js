import React, {Component} from 'react';
import listDTO from '../model/listDTO'


//props = {url, username, loadItem}
export default class List extends Component {

  constructor(props){
    super(props);

    this.listToHtml = this.listToHtml.bind(this)
    this.loadItem =  props.loadItem


    this.state = {
      url: props.url,
      username: props.username,
      list: {
        items: [],
        selfLink : ''
      }
    }

  }

  componentDidMount(){

    fetch(this.state.url, {headers:{authorization:'basic bnVubzoxMjM0NQ=='}})
      .then(res => res.json())
      .then(json => {
        this.setState({list: listDTO(json)})
      })
  }



  listToHtml(){
    return this.state.list.items.map(item =>
      <div  key={item.checklistItem.id}><button onClick={()=>this.loadItem(item._links.self.href)}> {item.checklistItem.name} </button></div>
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
