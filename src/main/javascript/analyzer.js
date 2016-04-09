
import React, { Component } from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import QueueAnim from 'rc-queue-anim'

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
      <span key={i}>
        <QueueAnim animConfig={{opacity: [1, 1]}} interval={350} duration={0}>
          {words.map(this.renderWord)}
        </QueueAnim>
        .
      </span>
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
