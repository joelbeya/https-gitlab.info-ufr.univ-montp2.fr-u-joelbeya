const mongoose = require('mongoose');
let Schema = mongoose.Schema;

var EmailVerifSchema = mongoose.Schema(
    {
        email: {
            type: String,
            trim: true,
            required: true
        },
        hash: {
            type: String,
            required: true,
        }
    }
);

var EmailVerif = mongoose.model('EmailVerif', EmailVerifSchema);
