
import React, { Component } from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

import Word from './word';

const mock = [
  // { text: 'Hello', duration: 10, final: true },
  // { text: 'nice', duration: 10, final: true },
  // { text: 'to', duration: 10, final: true },
  // { text: 'meet', duration: 10, final: true },
  // { text: 'you', duration: 10, final: true },
  // { text: 'all', duration: 10, final: false },
]

export default class Analyzer extends Component {

  constructor(props) {
    super(props);

    // this.state = { words: [] };
    this.state = { words: mock }

    this.props.recorder.onUpdate = words => this.setState({ words })
  }

  renderWord(word, i) {
    return <Word key={i} {...word} />
  }

  renderSentence(words, i) {
    return (
      <div key={i}>{words.map(this.renderWord)}.</div>
    )
  }

  render() {
    let words = this.state.words;

    return (
      <div className="analyzer">
        {words.map(this.renderSentence.bind(this))}
      </div>
    )
  }
}
