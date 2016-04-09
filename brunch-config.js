module.exports = {
  files: {
    javascripts: {joinTo: 'app.js'},
    stylesheets: {joinTo: 'app.css'},
    templates: {joinTo: 'app.js'}
  },
  paths: {
    watched: [
      "src/main/javascript",
      "src/main/stylesheet"
    ],
    public: "src/main/resources/static/"
  },
  plugins: {
    babel: {
      presets: ['es2015', 'react'],
      ignore: [/^(bower_components|npm_modules|vendor)/]
    },
    sass: {
      modules: false
    }
  },
  server: {
    hostname: '0.0.0.0'
  },
  npm: {
    enabled: true,
    whitelist: ['whatwg-fetch', 'react', 'react-dom', 'lodash']
  }
};
