const express = require('express');
const router = express.Router();
const courseController = require('../controllers/course_controller.js');

router.get('/', courseController.listCourses);
router.get('/:name', courseController.getCourseByName);
router.get('/:schoolLevel', courseController.getCoursesByLevel);

router.post('/', courseController.addCourse);

module.exports = router;
