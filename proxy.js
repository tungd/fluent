
var fs = require('fs'),
    httpProxy = require('http-proxy')

var base = 'src/main/resources',
    hostname = '0.0.0.0',
    proxy = httpProxy.createServer({
      target: {
        host: 'localhost',
        port: 3333
      },
      ssl: {
        key: fs.readFileSync(base + '/keys/server.pem'),
        cert: fs.readFileSync(base + '/keys/server.crt')
      }
    });

proxy.on('upgrade', function(req, socket, head) {
  proxy.ws(req, socket, head);
});

proxy.listen(8443)
