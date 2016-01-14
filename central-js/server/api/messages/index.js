var express = require('express');
var router = express.Router();

var messages = require('./index.controller');

router.get('/', messages.get);
router.post('/', messages.post);

router.get('/feedback', messages.findFeedback);
router.post('/feedback', messages.postFeedback);

module.exports = router;
