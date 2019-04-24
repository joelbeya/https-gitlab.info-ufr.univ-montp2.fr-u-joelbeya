const mongoose = require('mongoose');
const Course = mongoose.model('Course');

/*
* Lister tous les cours
*/
const listCourses = (req, res) => {
    Course.find({}, (err, courses) => {
        if (err) {
            return res.status(400).send({ message: err });
        } else {
            res.json(courses);
        }
    });
};

/*
* Ajouter un cours
*/
const addCourse = (req, res) => {
    Course.findOne({ name : req.body.name }, (err, course) => {
        if (course == null) { // Aucun utilisateur n'utilise cet email
            let newCourse = new Course(req.body);
            newCourse.save((err, course) => {
                if (err) {
                    return res.status(400).send({ message: err });
                } else {
                    res.json(course);
                }
            });
        } else {
            res.json('Course already exists');
        }
    });
};

/*
* Obtenir un cours à partir de son nom
*/
const getCourseByName = (req, res) => {
    Course.findOne({ name : req.params.name }, (err, course) => {
        if (err) {
            return res.status(400).send({ message: err });
        } else {
            res.json(course);
        }
    })
}

/*
* Obtenir tous les cours disponibles pour un niveau
*/
const getCoursesByLevel = (req, res) => {
    Course.find({ schoolLevel : req.params.schoolLevel }, (err, courses) => {
        if (err) {
            return res.status(400).send({ message: err });
        } else {
            res.json(courses);
        }
    });
}

module.exports = { listCourses, addCourse, getCourseByName, getCoursesByLevel };
