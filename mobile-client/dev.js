const Bundler = require('parcel-bundler');
const express = require('express');
const proxy = require('http-proxy-middleware');

const app = express();

app.use('/api', proxy({
    target: 'http://localhost:8080/'
}));

const bundler = new Bundler('src/index.html');
app.use(bundler.middleware());

app.listen(Number(process.env.PORT || 1234));