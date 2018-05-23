import React, { Component } from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import LoginPage from './linkPages/LoginPage'
import ChecklistListPage from './linkPages/ChecklistListPage'
import ChecklistsPage from './linkPages/ChecklistPage'
import ChecklistItemPage from './linkPages/ChecklistItemPage'
import InvalidPage from './errorPages/InvalidPage'
import NoAccessPage from './errorPages/NoAccessPage'
import ServerErrorPage from './errorPages/ServerErrorPage'
import HomePage from './linkPages/HomePage'
import InvalidCredentials from './errorPages/InvalidCredentials'
import TemplatesPage from './linkPages/TemplatesPage'
import templateItemPage from './linkPages/templateItemPage'
import templatePage from './linkPages/templatePage'

export default class Router extends Component {


  render() {
    return <BrowserRouter>
      <Switch>
        <Route path='/' exact component={HomePage}/>
        <Route path='/loginpage' exact component={LoginPage}/>}/>
        <Route path='/homepage' exact component={HomePage}/>}/>

        <Route path='/checklists' exact component={ChecklistListPage}/>}/>
        <Route path='/checklist/:listId' exact component={ChecklistsPage}/>
        <Route path='/checklist/:listId/item/:itemId' exact component={ChecklistItemPage}/>

        <Route path='/templates' exact component={TemplatesPage}/>}/>
        <Route path='/template/:templateId' exact component={templatePage}/>
        <Route path='/template/:templateId/item/:itemId' exact component={templateItemPage}/>

        <Route path='/invalid-checklist-item' exact render={()=><InvalidPage errorType='checklist item'/>}/>
        <Route path='/invalid-checklist' exact render={()=><InvalidPage errorType='checklist'/>}/>

        <Route path='/no-access-checklist-item' exact render={()=><NoAccessPage errorType='checklist item'/>}/>
        <Route path='/no-access-checklist' exact render={()=><NoAccessPage errorType='checklist'/>}/>
        <Route path='/no-access-checklist-list' exact render={()=><NoAccessPage errorType='checklist list'/>}/>

        <Route path='/invalid-listname-or-password' exact component={InvalidCredentials}/>

        <Route path='/server-error' exact component={ServerErrorPage}/>

        <Route path='/' render={()=><InvalidPage errorType='page'/>}/>
      </Switch>
    </BrowserRouter>;
  }
}


