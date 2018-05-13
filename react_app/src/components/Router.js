import React, { Component } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import HomePage from './HomePage'
import ChecklistListPage from './ChecklistListPage'
import ChecklistsPage from './ChecklistPage'
import ChecklistItemPage from './ChecklistItemPage'


export default class Router extends Component {


  render() {
    return <BrowserRouter>
      <Switch>
        <Route path='/' exact component={HomePage}/>
        <Route path='/homepage' exact component={HomePage}/>}/>
        <Route path='/checklists' exact component={ChecklistListPage}/>}/>
        <Route path='/checklist/:listId' exact component={ChecklistsPage}/>
        <Route path='/checklist/:listId/item/:itemId' exact component={ChecklistItemPage}/>
        <Route path='/' render={HomePage}/>}/>
      </Switch>
    </BrowserRouter>;
  }
}


