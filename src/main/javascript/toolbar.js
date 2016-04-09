import React from 'react';

import Visualizer from './visualizer';

export default (props) => {
  return (
    <div className="row">
      <div className="col-xs-3">
        <div className="row">
        </div>
        <a
          className={`btn btn-lg ${props.recording ? 'btn-danger' : 'btn-primary'}`}
          onClick={props.onClick}>
          <span className="glyphicon glyphicon-off"/>
        </a>
        <p className="label label-default">Recording</p>
      </div>
      <div className="col-xs-9" style={{position: 'relative'}}>
        <Visualizer recorder={props.recorder}/>
      </div>
    </div>
  );
}
