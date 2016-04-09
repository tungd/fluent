let getUserMedia = (navigator.getUserMedia ||
                    navigator.webkitGetUserMedia ||
                    navigator.mozGetUserMedia ||
                    navigator.msGetUserMedia).bind(navigator);

import Meyda from 'meyda';


export default class Recorder {

  constructor(audio, recognition, enableAnalyze = false, bufferSize = 2048, features = ["rms", "energy"]) {
    this.started = false;
    this.audio = audio;

    if (enableAnalyze) {
      this.buffer = new Uint8Array(bufferSize);
      this.features = features;
      this.analyzer = this._createAnalyzer(audio, bufferSize);
    }

    this.recognition = this._configureRecognition(recognition);
    this.words = [];
  }

  start() {
    if (this.started) {
      return;
    }

    this.started = true;
    this.words = [];

    if (this.analyzer) {
      getUserMedia({ audio: true }, stream => {
        if (!this.source) {
          this.source = this.audio.createMediaStreamSource(stream);
          this.source.connect(this.analyzer);

          this.meyda = Meyda.createMeydaAnalyzer({
            audioContext: this.audio,
            source: this.source,
            bufferSize: this.bufferSize,
            featureExtractors: this.features
          });

          this.meyda.start();
          this.recognition.start();
        }
      }, console.error.bind(console));
    } else {
      this.recognition.start();
    }
  }

  stop() {
    this.meyda.stop();
    this.recognition.stop();
    this.started = false;
  }

  analyze(feature) {
    if (!this.meyda) {
      // throw new Error("Recorder is not started.")
      return 0;
    }

    return this.meyda.get(feature);
  }

  _createAnalyzer(audio, bufferSize) {
    let analyzer = audio.createAnalyser();

    analyzer.fftSize = bufferSize;
    analyzer.minDecibels = -90;
    analyzer.maxDecibels = -10;
    analyzer.smoothingTimeConstant = 0.85;

    return analyzer;
  }

  _configureRecognition(recognition) {
    recognition.continuous = true;
    recognition.interimResults = true;
    console.log(recognition.lang);
    recognition.lang = 'en';

    recognition.onstart = _ => console.log("Started!");
    recognition.onend = _ => console.log('End');
    recognition.onerror = e => console.error(e);

    recognition.onresult = this._handleResult.bind(this);

    return recognition;
  }

  _handleResult(e) {
    let i, word, text = '', partial = '';

    for (i = e.resultIndex; i < e.results.length; i += 1) {
      word = e.results[i][0].transcript;

      if (e.results[i].isFinal) {
        text += word;
        console.log("Text:", text);
        this.end = true;
      } else {
        partial += word;
        console.log("Partial:", partial);
      }
    }

    if (text) {
      this._updateWords(text.split(' '));
    } else if (partial) {
      this._updateWords(partial.split(' '));
    }
  }

  _updateWords(words) {
    words.forEach((word, i) => {
      if (i < this.words.length) {
        this.words[i].text = word;
        this.words[i].final = true;
      } else {
        this.words.push({ text: word, final: false });
      }
    });

    console.log(this.words.map(w => w.text))
  }
}
