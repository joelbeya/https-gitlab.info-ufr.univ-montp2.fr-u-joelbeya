const mongoose = require('mongoose');
let Schema = mongoose.Schema;

var CourseSchema = mongoose.Schema(
    {
        name: {
            type: String,
            required: true,
        },
        subject: {
            type: String,
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
