import React, { Component } from 'react';
import List from '../components/List'

import GoHome from '../components/GoHome'
import GoBack from '../components/GoBack'
import templateItemsDTO from '../model/templatesListDTO'
import TemplateForm from '../components/templateForm'


export default class extends Component {

  constructor (props) {
    super(props);

    this.goToTemplate = this.goToTemplate.bind(this)
    this.errorCallback = this.errorCallback.bind(this)
    this.templatesFormCallback = this.templatesFormCallback.bind(this)


    this.state = {
      history : props.history,
    }
  }

  goToTemplate(templateId){
    this.state.history.push(`/template/${templateId}`)
  }

  errorCallback(err){

    if(err==='TypeError: Failed to fetch')
      this.state.history.push('/server-error')
    if(err==='no-access')
      this.state.history.push('/no-access-checklist-list')
    else
      this.state.history.push('/invalid-list')
  }

  templatesFormCallback(template){
    this.state.history.push('/template/'+ template.id)
  }


  render() {
    return (
      <div>
        <div>Your Templates</div>
        <GoHome history={this.state.history}/>
        <GoBack history={this.state.history}/>
        <div align="right">
          <TemplateForm url='http://localhost:9000/listing/template' callback={this.templatesFormCallback}/>
        </div>
        <div align="center">
          <strong>Templates List</strong>
          <List url='http://localhost:9000/listing/templates' loadItem={this.goToTemplate} loadDTO={templateItemsDTO} errorCallback={this.errorCallback}/>
        </div>

      </div>
    );
  }
}


