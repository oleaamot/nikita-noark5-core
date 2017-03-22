var bodyParser = require('body-parser');
var pjson = require('./package.json');
var express = require('express');

var app = express();

app.use(express.static('.'))
app.use(bodyParser.json());

app.get('/version', function(req, res){
  res.send(pjson.version);
});

// Let the angular application handle requests.
app.get('*', function(req, res) {
      res.redirect('/');
});


app.listen(3000);
