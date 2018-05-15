import React, {Component} from 'react';


//props = {url, listname, loadItem}
export default class List extends Component {

  constructor(props){
    super(props);

    this.listToHtml = this.listToHtml.bind(this)
    this.request =     this.request.bind(this)
    this.loadItem =  props.loadItem
    this.loadDTO = props.loadDTO
    this.errorCallback = props.errorCallback


    this.state = {
      url: props.url,
      listname: props.listname,
      list: {
        items: [], // item = {id, name,selfLink}
        selfLink : ''
      }
    }



  }

  componentDidMount(){
    this.request()
  }


  request(){
    const session_id = localStorage.getItem('session-id')

    if(!session_id) return this.errorCallback('no-access')

    return fetch(this.state.url, {headers:{authorization:`basic ${session_id}`}})
      .then(res =>{
        if(!res.ok) return this.errorCallback(res.status)
        return res.json()
      })
      .then(json =>this.setState({list: this.loadDTO(json)}))
      .catch(this.errorCallback)
  }



  listToHtml(){
    return this.state.list.items.map(item =>
      <div  key={item.id}><button onClick={()=>this.loadItem(item.id)}> {item.name} </button></div>
    )
  }




  render() {
    return (
      <div>{this.listToHtml()}</div>

    );
  }
}



// EXAMPLE OF LIST ITEMS
/*
  items : []
  selfLink : string
}*/
