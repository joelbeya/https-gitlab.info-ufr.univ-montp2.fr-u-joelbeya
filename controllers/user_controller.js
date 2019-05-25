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
exports.deleteUser = (req, res) => {
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
exports.getUserByEmail = (req, res) => {
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
exports.list = (req, res) => {
    User.find({}, (err, users) => {
        if (err) return res.status(400).send({ message: err });
        res.json(users);
    });
};

/*
* Login a user
**/
exports.login = (req, res, next) => {
    // On cherche d'abord si l'utilisateur existe
    User.findOne({ email : req.body.email }, (err, user) => {
        if (err) return res.status(400).send({ message: err });

        if (user) { // Si l'utilisateur existe

            /* Comparaison du mot de passe saisi et du hash */
            bcrypt.compare(req.body.password, user.hash_password, (err, match) => {
                if (!match) return res.send({"msg" : "Incorrect email or password"});

                // Make sure the user has been verified
                if (!user.active) return res.send({
                    "msg" : "Your account has not been verified."
                });

                const jwtUser = { email: user.email, userId: user._id };
                user.token = jwt.sign(jwtUser, 'RESTFULAPIs');

                res.json(user);
            });
        } else {
            res.send({ "msg" : "Incorrect email or password"});
        }
    });
};

/*
* Register a user
**/
exports.register = (req, res, next) => {
    req.connection.setTimeout(180 * 1000);

    /* Vérifier qu'aucun utilisateur n'utilise l'email du nouvel utilisateur */
    User.findOne({ email : req.body.email }, (err, user) => {
        if (err) return console.log(err);

        // Un utilisateur utilise déjà cet email
        if (user) return res.json({ "message" : "User already exists" });

        let queryUrl = 'https://' + req.get('host') + '/users/sendverif';
        let options = {
          // headers: {'content-type' : 'application/x-www-form-urlencoded'},
            url : queryUrl,
            body : req.body,
            json: true
        };

        let newUser = new User(req.body);

        /* Cryptage du mot de passe */
        let salt = bcrypt.genSaltSync(BCRYPT_SALT_ROUNDS);
        let hashedPassword = bcrypt.hashSync(req.body.password, salt);

        newUser.hash_password = hashedPassword;

        newUser.save((err, user) => {
            if (err)  return console.error(err);

            /* Creation d'un token de vérification pour cet utilisateur */
            // Génération d'un hash alétoire
            let hash = crypto.randomBytes(16).toString('hex');

            let emailVerif = new EmailVerif({
                email : req.body.email,
                hash : hash
            });

            /* Et sauvegarde du token dans la base de données */

            emailVerif.save((err, emailVerif) => {
                if (err) return console.log(err);

                /* Envoi de l'email de vérification */

                let smtpTransport = nodemailer.createTransport({
                    service: 'Gmail',
                    auth: { user: 'easycourse.contact@gmail.com', pass: 'easycourse2019' }
                });

                let host = req.get('host');
                let link = 'https://' + req.get('host') + '/users/verify?hash=' + hash
                    + '&host=' + req.get('host');

                let emailMess = 'Hello,<br> Please click on the link to verify your account.'
                    + '<br><br><a href=' + link + '>Click here to verify</a>';

                let mailOptions = {
                    to : req.body.email,
                    subject : 'Please confirm your email account',
                    html : emailMess
                }

                smtpTransport.sendMail(mailOptions, (err) => {
                    if (err) return res.status(500).send({ msg: err.message });

                    res.status(200).send('A verification email has been sent to ' + user.email + '.');
                });
            });

            console.log('User ' + user.email + ' created successfully');
            res.json(user);
        });
    });
};

/*
* Verify email
**/
exports.verifyEmail = (req, res, next) => {

    let queryUrl = req.protocol + '://' + req.get('host');

    if (queryUrl == ('http://' + req.query.host)) {
        // Delete hash
        EmailVerif.findOneAndDelete({ hash : req.query.hash }, (err, token) => {
            if (err) return res.status(400).send({ message: err });

            if (!token) return res.status(400).send({
                type: 'not-verified',
                msg: 'We were unable to find a valid token. Your token my have expired.'
            });

            User.findOne({ email : token.email }, (err, user) => {
                if (!user) return res.status(400).send({
                    msg: 'We were unable to find a user for this token.'
                });

                /* Set user to active */
                user.active = true;

                user.save(function (err) {
                    if (err) { return res.status(500).send({ msg: err.message }); }
                    res.status(200).send("The account has been verified. Please log in.");
                });
            });
        });
    } else {
        res.end('<h1>Request is from unknown source</h1>');
    }
}
