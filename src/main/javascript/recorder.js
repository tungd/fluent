import Meyda from 'meyda';

let getUserMedia = (navigator.getUserMedia ||
                    navigator.webkitGetUserMedia ||
                    navigator.mozGetUserMedia ||
                    navigator.msGetUserMedia).bind(navigator);

export default class Recorder {

  constructor(audio, recognition, enableAnalyze = false, bufferSize = 2048, features = ["rms", "energy"]) {
    this.started = false;
    this.end = false;
    this.audio = audio;
    this.startAt = 0;
    this.endAt = 0;

    if (enableAnalyze) {
      this.buffer = new Uint8Array(bufferSize);
      this.features = features;
      this.analyzer = this._createAnalyzer(audio, bufferSize);
    }

    this.recognition = this._configureRecognition(recognition);
    this.words = [];
    this.sentence = 0;
  }

  start() {
    if (this.started) {
      return;
    }

    this.started = true;
    this.startAt = Date.now();
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
    if (this.meyda) {
      this.meyda.stop();
    }

    this.endAt = Date.now();
    this.started = false;
    this.end = true;
    this.recognition.stop();
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
    // recognition.continuous = true;
    recognition.interimResults = true;
    recognition.lang = 'en-US';

    recognition.onstart = _ => console.log("Started!");
    recognition.onend = _ => {
      console.log('End');

      if (!this.end) {
        this.sentence += 1;
        recognition.start();
      }
    };
    recognition.onerror = e => console.error(e);

    recognition.onresult = this._handleResult.bind(this);

    return recognition;
  }

  _handleResult(e) {
    // console.log(this.words);
    let text = '';

    if (!this.words[this.sentence]) {
      this.words[this.sentence] = [];

      if (this.words[this.sentence - 1]) {
        this.words[this.sentence - 1].forEach(w => w.final = true);
      }
    }

    text = e.results[e.resultIndex][0].transcript;

    text.split(' ').forEach((word, i) => {
      if (this.words[this.sentence][i]) {
        if (this.words[this.sentence][i].text == word) {
          this.words[this.sentence][i].final = true
        } else {
          this.words[this.sentence][i].text = word
        }
      } else {
        this.words[this.sentence].push({ text: word, final: false })
      }
    });

    if (this.onUpdate) {
      this.onUpdate(this.words);
    }
  }
}
