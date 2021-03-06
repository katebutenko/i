'use strict';

var express = require('express');
var controller = require('./reports.controller');

var router = express.Router();

router.get('/export', controller.index);
router.get('/statistic', controller.statistic);

module.exports = router;
