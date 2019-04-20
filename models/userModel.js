const mongoose = require('mongoose');
let Schema = mongoose.Schema;

var UserSchema = mongoose.Schema({
    lastname: {
        type: String,
        trim: true,
        required: true
    },
    firstname: {
        type: String,
        trim: true,
        required: true
    },
    email: {
        type: String,
        trim: true,
        required: true
    },
    hash_password: {
        type: String,
        required: true
    }
}, { collection: 'user' });

mongoose.model('User', UserSchema);
