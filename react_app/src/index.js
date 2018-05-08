import React from 'react';
import ReactDOM from 'react-dom';
import App from './exampleApp/App';
import ExamplePage from './components/ExamplePage';

import registerServiceWorker from './registerServiceWorker';
import './index.css';

ReactDOM.render(<ExamplePage />, document.getElementById('root'));
registerServiceWorker();
