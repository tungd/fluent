import React, { Component } from 'react';

import Word from './word';

const mock = [
  { text: 'Hello', duration: 10, final: true },
  { text: 'nice', duration: 10, final: true },
  { text: 'to', duration: 10, final: true },
  { text: 'meet', duration: 10, final: true },
  { text: 'you', duration: 10, final: true },
  { text: 'all', duration: 10, final: false },
]

export default class Analyzer extends Component {

  constructor(props) {
    super(props);

    this.state = { words: [] };
    // this.state = { words: mock }
  }

  renderWord(word, i) {
    return <Word key={i} {...word} />
  }

  componentDidMount() {
    this.interval = setInterval(() => {
      this.setState({ words: this.props.recorder.words })
    }, 60);
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  render() {
    let words = this.state.words;

    return (
      <div className="analyzer">
        {words.map(this.renderWord.bind(this))}
      </div>
    )
  }
}
