import React, {Component} from 'react';
import ReactDOM from 'react-dom';

import Recorder from './recorder';
import Analyzer from './analyzer';
import ToolBar  from './toolbar';

const isMobile =
        /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)

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
    }
  }

  render() {
    return (
      <div className="app">
        <Analyzer recorder={this.recorder}/>
        <ToolBar
           recorder={this.recorder}
           onClick={this.toggleRecording.bind(this)}/>
      </div>
    )
  }
}

ReactDOM.render(<App />, document.getElementById('app'));
