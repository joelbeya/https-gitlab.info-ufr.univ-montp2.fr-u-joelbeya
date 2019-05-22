const mongoose = require('mongoose');
const CourseSchema = require('./course_model').CourseSchema;

let Schema = mongoose.Schema;

const Course = mongoose.model('Course');


var CourseSetSchema = mongoose.Schema(
    {
        courses: {
            type: [CourseSchema],
            required: true,
        },
        schoolLevel: {
            type: String,
            required: true,
            enum: ['6e', '5e', '4e', '3e', '2nde', '1ere', 'Term']
        }
    }
);

var Course = mongoose.model('Course', CourseSchema);
