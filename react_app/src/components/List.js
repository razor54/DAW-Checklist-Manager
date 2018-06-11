import React, {Component} from 'react';

const token_key = 'oidc.user:http://localhost:8080/openid-connect-server-webapp:react-web-app';

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

    const session = sessionStorage.getItem(token_key)
    if(!session) return this.errorCallback('no-access')
    const token =  JSON.parse(session)

    return fetch(this.state.url, {headers:{authorization:`${token.token_type} ${token.access_token}`}})
      .then(res =>{
        if(!res.ok) return this.errorCallback(res.status)
        return res.json()
      })
      .then(json =>this.setState({list: this.loadDTO(json)}))
      .catch(this.errorCallback)
  }



  listToHtml(){
    return this.state.list.items.map(item => {

      if (!item.checkState)
        return <div key={item.id}>
          <button onClick={() => this.loadItem(item.id)}> {item.name} </button>
        </div>

      return <div key={item.id}>
        <button onClick={() => this.loadItem(item.id)} > {item.name} </button>
        <input type="checkbox" disabled={true} checked={item.checkState}/>
      </div>
    }
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
