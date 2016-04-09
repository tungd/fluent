import React, { Component } from 'react';
import ReactDOM from 'react-dom';


import Recorder from './recorder'
import Analyzer from './analyzer'
import Visualizer from './visualizer'


class App extends Component {

  constructor(props) {
    super(props)

    // TODO: check support

    this.recorder = new Recorder(
      new (window.AudioContext || window.webkitAudioContext)(),
      2048)

    this.state = {
      recording: false
    }
  }

  toggleRecording() {
    let recording = !this.state.recording

    this.setState({ recording })
    if (recording) {
      this.recorder.start()
    }
  }

  render() {
    return (
      <div className="app">
        <Analyzer />
        <div className="toolbar">
          <button className="recording"
                  onClick={this.toggleRecording.bind(this)}>
            Recording
          </button>
          <Visualizer recorder={this.recorder} />
        </div>
      </div>
    )
  }
}

ReactDOM.render(<App />, document.getElementById('app'));
