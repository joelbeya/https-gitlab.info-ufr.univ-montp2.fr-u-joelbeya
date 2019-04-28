const bcrypt = require('bcrypt');
const crypto = require("crypto");
const jwt = require("jsonwebtoken");
const mongoose = require('mongoose');
const nodemailer = require('nodemailer');
const request = require('request');

const BCRYPT_SALT_ROUNDS = 10;

const User = mongoose.model('User');
const EmailVerif = mongoose.model('EmailVerif');

/*
* Delete a user retrieved by his email
**/
const deleteUser = (req, res) => {
    User.deleteOne({ email : req.body.email }, (err, user) => {
        if (err) {
            return res.status(400).send({ message: err });
        } else {
            res.json(user);
        }
    });
};

/*
* Retrive a user retrieved by his email
**/
const getUserByEmail = (req, res) => {
    User.findOne({ email : req.body.email }, (err, user) => {
        if (err) {
            return res.status(400).send({ message: err });
        } else {
            res.json(user);
        }
    });
};

/*
* List all users of the database
**/
const list = (req, res) => {
    User.find({}, (err, users) => {
        if (err) return res.status(400).send({ message: err });
        res.json(users);
    });
};

/*
* Login a user
**/
const login = (req, res, next) => {
    // On cherche d'abord si l'utilisateur existe
    User.findOne({ email : req.body.email }, (err, user) => {
        if (err) return res.status(400).send({ message: err });

        if (user) { // Si l'utilisateur existe

            /* Comparaison du mot de passe saisi et du hash */
            bcrypt.compare(req.body.password, user.hash_password, (err, match) => {
                if (match) {
                    const jwtUser = { email: user.email, userId: user._id };
                    const token = jwt.sign(jwtUser, 'RESTFULAPIs');
                    // res.json({ token: token, user: jwtUser });
                    res.end('User Logged in');
                } else {
                    res.end('Incorrect email or password');
                }
            });
        } else {
            res.end('Incorrect email or password');
        }
    });
};

/*
* Register a user
**/
const register = (req, res, next) => {

    /* Vérifier qu'aucun utilisateur n'utilise l'email du nouvel utilisateur */
    User.findOne({ email : req.body.email }, (err, user) => {
        if (err) return console.log(err);

        if (user == null) { // Aucun utilisateur n'utilise cet email

            let queryUrl = 'https://' + req.get('host') + '/users/sendverif';
            let options = {
              // headers: {'content-type' : 'application/x-www-form-urlencoded'},
                url : queryUrl,
                body : req.body,
                json: true
            };

            request(options, (err, resp, body) => {
                if (err) return console.log(err);

                /*----------- Email is verified now proceed to connexion -----------*/

                /* Cryptage du mot de passe avant insertion dans la base de données */
                bcrypt.hash(req.body.password, BCRYPT_SALT_ROUNDS, (err, hashedPassword) => {

                    let newUser = new User(req.body);
                    newUser.hash_password = hashedPassword;

                    /* Set user to active */
                    newUser.active = true;

                    newUser.save((err, user) => {
                        if (err)  return console.error(err);

                        console.log('User created successfully');
                        console.log('User: ', user);
                        res.json(user);
                    });
                });
            });
        } else {
            res.json('User already exists');
        }
    });
};

/*
* Send verification email
**/
const sendEmailVerif = (req, res, next) => {
    /* Here we are configuring our SMTP Server details. */

    let smtpTransport = nodemailer.createTransport({
        service: 'Gmail',
        auth: { user: 'easycourse.contact@gmail.com', pass: 'easycourse2019' }
    });

    // Génération d'un hash alétoire
    let hash = crypto.randomBytes(10).toString('hex');

    // Et stockage dans la base de données
    let emailVerif = new EmailVerif({
        email : req.body.email,
        hash : hash
    });
    emailVerif.save((err, emailVerif) => {
        if (err) return console.log(err);
    });

    let host = req.get('host');
    let link = 'https://' + req.get('host') + '/users/verify?hash=' + hash
        + '&host=' + req.get('host');

    let emailMess = 'Hello,<br> Please click on the link to verify your email.'
        + '<br><br><a href=' + link + '>Click here to verify</a>';

    let mailOptions = {
        to : req.body.email,
        subject : 'Please confirm your email account',
        html : emailMess
    }

    smtpTransport.sendMail(mailOptions, (err, res) => {
        if (err) {
            console.log(err);
            res.end('Error');
        } else {
            //  + response.message
            console.log('Verification email sent...');
            res.end('Email sent');
        }
    });
}

/*
* Verify email
**/
const verifyEmail = (req, res, next) => {

    let queryUrl = req.protocol + '://' + req.get('host');

    if (queryUrl == ('http://' + req.query.host)) {
        console.log('Domain is matched. Information is from authentic email');

        // Delete hash
        EmailVerif.findOneAndDelete({ hash : req.query.hash }, (err, match) => {
            if (err) return res.status(400).send({ message: err });

            if (match) {
                console.log('Email is verified');
                res.end('<h1>Your email has been successfully verified</h1>');
            } else {
                console.log('Email is not verified');
                res.end('<h1>Bad Request</h1>');
            }
        });
    } else {
        res.end('<h1>Request is from unknown source</h1>');
    }
}

module.exports = {
    deleteUser,
    getUserByEmail,
    list,
    login,
    register,
    sendEmailVerif,
    verifyEmail
};
