import React, { Component } from 'react';
import ReactDOM from 'react-dom';


import Analyzer from './analyzer'
import Visualizer from './visualizer'


class App extends Component {

  toggleRecording() {

  }

  render() {
    return (
      <div className="app">
        <Analyzer />
        <div className="toolbar">
          <button className="recording"
                  onPress={this.toggleRecording.bind(this)}>
            Recording
          </button>
          <Visualizer />
        </div>
      </div>
    )
  }
}

ReactDOM.render(<App />, document.getElementById('app'));
