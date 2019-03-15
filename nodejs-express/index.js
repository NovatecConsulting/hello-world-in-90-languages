const express = require('express');
const app = express();

const APP_PORT = 8080;
const HTTP_STATUS_OK = 200;

app.disable('etag').disable('x-powered-by');

app.use(function(req, res, next) {
    res.removeHeader('Connection');
    next();
});

app.get('/say-hello', (req, res) => {
    res.statusCode = HTTP_STATUS_OK;
    res.send({message: `Hello ${req.query.name || 'World'}!`});
});

app.listen(APP_PORT, () => console.log(`Listening on port ${APP_PORT}`));