
import React, { Component } from 'react'
import ReactDOM from 'react-dom'


class App extends Component {
  render() {
    return <h1>Hello, World</h1>
  }
}

ReactDOM.renderElement(<App />, document.getElementById('app'))

console.log("Hello, World")
