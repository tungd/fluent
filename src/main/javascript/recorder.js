
let getUserMedia = (navigator.getUserMedia ||
                    navigator.webkitGetUserMedia ||
                    navigator.mozGetUserMedia ||
                    navigator.msGetUserMedia).bind(navigator)

import Meyda from 'meyda'


export default class Recorder {

  constructor(audio, bufferSize = 2048, features = ["rms", "energy"]) {
    this.started = false
    this.audio = audio

    this.buffer = new Uint8Array(bufferSize)
    this.analyzer = this._createAnalyzer(audio, bufferSize)
  }

  start() {
    console.log("Started!")
    if (this.started) {
      return
    }

    this.started = true
    getUserMedia({ audio: true }, stream => {
      if (!this.source) {
        this.source = this.audio.createMediaStreamSource(stream)
        this.source.connect(this.analyzer)

        this.meyda = Meyda.createMeydaAnalyzer({
          audioContext: this.audio,
          source: this.source,
          bufferSize: this.bufferSize,
          featureExtractors: this.features
        })
      }
    }, console.error.bind(console))
  }

  analyze(feature) {
    if (!this.meyda) {
      // throw new Error("Recorder is not started.")
      return 0
    }

    return this.meyda.get(feature)
  }

  _createAnalyzer(audio, bufferSize) {
    let analyzer = audio.createAnalyser()

    analyzer.fftSize = bufferSize
    analyzer.minDecibels = -90
    analyzer.maxDecibels = -10
    analyzer.smoothingTimeConstant = 0.85

    return analyzer
  }
}
