import React, { Component } from 'react';

const requestAnimationFrame = (window.requestAnimationFrame ||
                               window.webkitRequestAnimationFrame ||
                               window.mozRequestAnimationFrame ||
                               window.msRequestAnimationFrame).bind(window);


export default class Visualizer extends Component {

  componentDidMount() {
    if (this.props.isMobile) {
      return
    }

    const canvas = this.refs.canvas;

    let { recorder } = this.props,
        width = canvas.width,
        height = canvas.height,
        graphic = canvas.getContext('2d'),
        tick = 0,
        x = 0, y;

    let current, atPeek = false, peek = 0
    const MIN_THRESHOLD = 0.5, MAX_THRESHOLD = 4

    function draw() {
      tick += 1;

      current = recorder.analyze("energy")
      if (current > MIN_THRESHOLD) {
        if (!atPeek) {
          atPeek = true;
        }

        if (atPeek && current > peek) {
          peek = current;
        }
      } else {
        // console.log(peek)
        if (peek > MAX_THRESHOLD) {
          console.log("Peeked");
          peek = 0;
        }
        atPeek = false;
      }

      if (tick % 4 == 0) {
        if (x > width) {
          x = 0;
          graphic.save();
          graphic.fillStyle = '#f5f5f5';
          graphic.fillRect(0, 0, width, height);
          graphic.restore();
        }

        y = height / 2 - current * 2.5;
        if (x === 0) {
          graphic.beginPath();
          graphic.moveTo(x, y);
        } else {
          graphic.lineTo(x, y);
        }
        x += 4;
        // var i, v, buffer = recorder.buffer, slice = width * 1.0 / buffer.length

        // for (i = 0; i < buffer.length; i += 1, x += slice) {
        //   v = buffer[i] / 128.0
        //   y = v * height / 2

        //   if (i === 0) {
        //     graphic.moveTo(x, y)
        //   } else {
        //     graphic.lineTo(x, y)
        //   }
        // }

        graphic.strokeStyle = '#000';
        graphic.strokeWidth = 2;
        graphic.stroke();
      }

      requestAnimationFrame(draw);
    }

    draw();
  }

  shouldComponentUpdate() {
    return false;
  }

  render() {
    return <canvas className="visualizer" ref="canvas"></canvas>
  }
}
