const bcrypt = require('bcrypt');
const BCRYPT_SALT_ROUNDS = 10;
const jwt = require("jsonwebtoken");
const mongoose = require('mongoose');
const User = mongoose.model('User');

const list = (req, res) => {
    User.find({}, (err, users) => {
        if (err) {
            return res.status(400).send({ message: err });
        } else {
            res.json(users);
        }
    });
};

const deleteUser = (req, res) => {
    User.deleteOne({ email : req.body.email }, (err, user) => {
        if (err) {
            return res.status(400).send({ message: err });
        } else {
            res.json(user);
        }
    });
};

const getUserByEmail = (req, res) => {
    User.findOne({ email : req.body.email }, (err, user) => {
        if (err) {
            return res.status(400).send({ message: err });
        } else {
            res.json(user);
        }
    });
};

const register = (req, res, next) => {
    console.log(req.body);
    User.findOne({ email : req.body.email }, (err, user) => {
        if (user == null) { // Aucun utilisateur n'utilise cet email

            /* Cryptage du mot de passe avant insertion dans la base de données */
            bcrypt.hash(req.body.password, BCRYPT_SALT_ROUNDS, (err, hashedPassword) => {
                var newUser = new User(req.body);
                newUser.hash_password = hashedPassword;
                newUser.save((err, user) => {
                    if (err) {
                        return res.status(400).send({ message: err });
                    } else {
                        user.hash_password = undefined;
                        res.json(user);
                    }
                });
            });
        } else {
            res.json('User already exists');
        }
    });
};

const login = (req, res, next) => {
    // On cherche d'abord si l'utilisateur existe
    User.findOne({ email : req.body.email }, (err, user) => {
        if (err) {
            return res.status(400).send({ message: err });
        } else {
            // Si l'utilisateur (l'email) existe
            if (user) {
                // Comparaison du mot de passe saisi et du hash
                bcrypt.compare(req.body.password, user.hash_password, (err, match) => {
                    if (match) {
                        const jwtUser = { email: user.email, userId: user._id };
                        const token = jwt.sign(jwtUser, 'RESTFULAPIs');
                        // res.json({ token: token, user: jwtUser });
                        res.json('User Logged in');
                    } else {
                        res.json('Incorrect email or password');
                    }
                });
            } else {
                res.json('Incorrect email or password');
            }
        }
    });
};

module.exports = { list, register, login, getUserByEmail, deleteUser };
