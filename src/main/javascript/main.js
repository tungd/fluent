import React, { Component } from 'react';
import ReactDOM from 'react-dom';

import Recorder from './recorder';
import Analyzer from './analyzer';
import Visualizer from './visualizer';

class App extends Component {

  constructor(props) {
    super(props);

    // TODO: check support
    this.recorder = new Recorder(
      new (window.AudioContext || window.webkitAudioContext)(),
      new (window.SpeechRecognition || window.webkitSpeechRecognition)(),
      2048);

    this.state = {
      recording: false
    }
  }

  toggleRecording() {
    let recording = !this.state.recording;

    this.setState({ recording });
    if (recording) {
      this.recorder.start();
    }
  }

  render() {
    return (
      <div className="container">
        <Analyzer recorder={this.recorder} />
        <div className="row">
          <div className="col-xs-4">
            <button className="recording"
                    onClick={this.toggleRecording.bind(this)}>
              Recording
            </button>
          </div>
          <div className="col-xs-8">
            <Visualizer recorder={this.recorder} />
          </div>
        </div>
      </div>
    )
  }
}

ReactDOM.render(<App />, document.getElementById('app'));
