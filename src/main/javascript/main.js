import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

import Recorder from './recorder';
import Analyzer from './analyzer';
import ToolBar  from './toolbar';

// const isMobile = true
const isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)

class App extends Component {

  constructor(props) {
    super(props);

    // TODO: check support
    this.recorder = new Recorder(
      new (window.AudioContext || window.webkitAudioContext)(),
      new (window.SpeechRecognition || window.webkitSpeechRecognition)(),
      !isMobile,
      2048
    );

    this.state = {
      recording: false
    }
  }

  toggleRecording() {
    let recording = !this.state.recording;

    this.setState({recording});
    if (recording) {
      this.recorder.start();
    } else {
      this.recorder.stop();
      let duration = this.recorder.endAt - this.recorder.startAt,
          text = [].concat.apply([], this.recorder.words).filter(w => w).map(w => w.text).join(' ')

      axios.get(`http://192.168.1.115:8080/summarize?text=${text}&duration=${duration}`)
        .then(data => {
          // TODO: display summarize screen
          console.log(data)
        })
    }
  }

  render() {
    return (
      <div className="app">
        <h1 className="header">Fluent</h1>
        <Analyzer recorder={this.recorder}/>
        <ToolBar
          recorder={this.recorder}
          recording={this.state.recording}
          onClick={this.toggleRecording.bind(this)}
          isMobile={isMobile}/>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.getElementById('app'));
