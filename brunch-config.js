module.exports = {
  files: {
    javascripts: {joinTo: 'app.js'},
    stylesheets: {joinTo: 'app.css'},
    templates: {joinTo: 'app.js'}
  },
  modules: {
    autoRequire: {
      "js/app.js": ["whatwg-fetch"]
    }
  },
  paths: {
    watched: [
      "src/main/javascript"
    ],
    public: "src/main/resources/static"
  },
  plugins: {
    babel: {
      presets: ['es2015', 'react'],
      ignore: [/^(bower_components|npm_modules|vendor)/]
    }
  },
  npm: {
    enabled: true,
    whitelist: ['whatwg-fetch', 'react', 'react-dom', 'lodash']
  }
}
