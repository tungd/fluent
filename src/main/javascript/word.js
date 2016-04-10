import React, { Component } from 'react';

export default class Word extends Component {

  render() {
    console.log(this.props);

    return (
      <div className={`word ${this.props.final ? 'final' : ''} ${this.props.correction ? 'error' : ''}`}>{this.props.text}</div>
    )
  }
}
