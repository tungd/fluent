import React from 'react';

import Visualizer from './visualizer';

const renderVisualizer = (props) => {
  if (props.isMobile) {
    return null;
  }

  return <Visualizer recorder={props.recorder}/>
};

export default (props) => {
  return (
    <div className="toolbar">
      <a className={`recording btn btn-lg ${props.recording ? 'btn-danger' : 'btn-primary'}`}
         onClick={props.onClick}>
        <span className="glyphicon glyphicon-off"/>
      </a>
      {renderVisualizer(props)}
    </div>
  );
}
