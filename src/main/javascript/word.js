import React, {Component} from 'react';

export default class Word extends Component {

  render() {
    const { messages, correction } = this.props;

    if (messages && correction) {
      return (
        <div
          className={`word ${this.props.final ? 'final' : ''} ${this.props.correction ? 'error' : ''}`}
          data-tip={`${messages} ${correction && correction.length > 1 ? '.Suggest correction: ' + correction.join(',') : ''}`}
          data-place='bottom'
          data-html='true'
          data-border='true'
          data-class='tooltip-bg'
          data-type='error'>{this.props.text}</div>
      );
    }

    return (
      <div className={`word ${this.props.final ? 'final' : ''} ${this.props.correction ? 'error' : ''}`}>{this.props.text}</div>
    )
  }
}
