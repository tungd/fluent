import React, {Component} from 'react';

export default class Word extends Component {

  render() {
    const { messages, correction, final } = this.props;

    let classes = [],
      isSuggestion = messages && messages.indexOf("Synonym:") == 0,
      isError = !isSuggestion && this.props.correction
    
    if (isError) {
      classes.push('error')
    }
    if (isSuggestion) {
      classes.push('suggestion')
    }
    if (final) {
      classes.push('final')
    }

    if (messages && correction) {
      return (
        <div
          className={`word ${classes.join(' ')}`}
          data-tip={`${messages} ${correction && correction.length > 1 ? '<br/>Suggest correction: ' + correction.join(',') : ''}`}
          data-place='bottom'
          data-html='true'
          data-border='true'
          data-class='tooltip-bg'
          data-type={isError ? 'error' : 'warning'}>{this.props.text}</div>
      );
    }

    return (
      <div className={`word ${this.props.final ? 'final' : ''} ${this.props.correction ? 'error' : ''}`}>{this.props.text}</div>
    )
  }
}
