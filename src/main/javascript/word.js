import React from 'react';

export default (props) => {
  let classes = ["word"]

  if (props.final) {
    classes.push("final")
  }

  return (
    <div className={classes.join(' ')}>{props.text}</div>
  )
}
