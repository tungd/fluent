import React, { Component } from 'react';

let requestAnimationFrame = (window.requestAnimationFrame ||
                             window.webkitRequestAnimationFrame ||
                             window.mozRequestAnimationFrame ||
                             window.msRequestAnimationFrame).bind(window);

export default class Visualizer extends Component {

  componentDidMount() {
    let { recorder } = this.props,
        canvas = this.refs.canvas,
        width = canvas.width,
        height = canvas.height,
        graphic = canvas.getContext('2d'),
        x = 0, y;

    console.log('canvas height', canvas.height);

    // function draw() {
    //   if (x > width) {
    //     x = 0;
    //     graphic.save();
    //     graphic.fillStyle = '#fff';
    //     graphic.fillRect(0, 0, width, height);
    //     graphic.restore();
    //   }

    //   y = height / 2 - recorder.analyze("energy") * 4;
    //   if (x === 0) {
    //     graphic.beginPath();
    //     graphic.moveTo(x, y);
    //   } else {
    //     graphic.lineTo(x, y);
    //   }
    //   x += 1;

    //   graphic.strokeStyle = '#000';
    //   graphic.strokeWidth = 2;
    //   graphic.stroke();
    //   requestAnimationFrame(draw)
    // }

    // draw();
  }

  shouldComponentUpdate() {
    return false;
  }

  render() {
    return <canvas ref="canvas"></canvas>
  }
}
