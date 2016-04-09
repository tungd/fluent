import React, { Component } from 'react';

class Word extends Component {

  render() {
    return (
      <div className="word" style={{width: 45}}>
        {this.props.text}
      </div>
    )
  }
}

export default class Analyzer extends Component {

  constructor(props) {
    super(props);

    this.state = { words: [] };
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
      <div className="analyzer text-center">
        {words.map(this.renderWord.bind(this))}
      </div>
    )
  }
}
