let requestAnimationFrame = (window.requestAnimationFrame ||
                             window.webkitRequestAnimationFrame ||
                             window.mozRequestAnimationFrame ||
                             window.msRequestAnimationFrame).bind(window)

import React, { Component } from 'react'

export default class Visualizer extends Component {

  componentDidMount() {
    let { recorder } = this.props,
        canvas = this.refs.canvas,
        width = canvas.width,
        height = canvas.height,
        graphic = canvas.getContext('2d'),
        x = 0, y

    function draw() {
      if (x > width) {
        x = 0
        graphic.save()
        graphic.fillStyle = '#fff'
        graphic.fillRect(0, 0, width, height)
        graphic.restore()
      }

      // recorder.buffer

      var energy = recorder.analyze("energy")
      if (energy) {
        console.log(energy)
      }
      y = height / 2 - recorder.analyze("energy") * 4
      // y = height / 2 - meyda.get("rms") * 256
      if (x === 0) {
        graphic.beginPath()
        graphic.moveTo(x, y)
      } else {
        graphic.lineTo(x, y)
      }
      x += 1

      graphic.strokeStyle = '#000'
      graphic.strokeWidth = 2
      graphic.stroke()
      requestAnimationFrame(draw)
    }

    draw()
  }

  shouldComponentUpdate() {
    return false
  }

  render() {
    return <canvas ref="canvas"></canvas>
  }
}
