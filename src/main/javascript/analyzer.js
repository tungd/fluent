
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import QueueAnim from 'rc-queue-anim';
import _ from 'lodash';

import Word from './word';

const mock = [
  { text: 'Hello', duration: 10, final: true },
  { text: 'nice', duration: 10, final: true },
  { text: 'to', duration: 10, final: true },
  { text: 'meet', duration: 10, final: true },
  { text: 'you', duration: 10, final: true },
  { text: 'all', duration: 10, final: false },
]

let scrolling = false;

function scrollTo(element, to, duration) {
  if (duration <= 0) return;
  let difference = to - element.scrollTop,
      perTick = difference / duration * 10;

  setTimeout(function() {
    element.scrollTop = element.scrollTop + perTick;
    if (element.scrollTop === to) return;
    scrollTo(element, to, duration - 10);
  }, 10);
}


export default class Analyzer extends Component {

  constructor(props) {
    super(props);

    this.props.recorder.onUpdate = words => this.setState({ words });

    // this.state = { words: mock };
    this.state = { words: [] };

    this.scrolling = false;
    this.scrollToBottom = _.debounce(_ => {
      if (this.scrolling) {
        return;
      }
      ReactDOM.findDOMNode(this).scrollTop = 999999
    });
  }

  componentDidUpdate() {
    this.scrollToBottom()
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
        <div className="analyzer__inner">
          {words.map(this.renderSentence.bind(this))}
        </div>
      </div>
    )
  }
}
