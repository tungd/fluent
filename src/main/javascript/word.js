
import React, { Component } from 'react';

export default class Word extends Component {

  render() {
    let classes = ["word"]

    if (this.props.final) {
      classes.push("final")
    }

    return (
      <div className={classes.join(' ')}>{this.props.text}</div>
    )
  }
}
