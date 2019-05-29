const mongoose = require('mongoose');
let Schema = mongoose.Schema;

var UserSchema = mongoose.Schema(
    {
        active: {
            type: Boolean,
            required: true,
            default: false
        },
        role: {
            type: String,
            required: true,
            enum: ['Parent', 'Student']
        },
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
    },
    {
        collection: 'user',
        discriminatorKey: 'role'
    },
);

var User = mongoose.model('User', UserSchema);

var StudentSchema = mongoose.Schema(
    {
        schoolLevel: {
            type: String,
            enum: [
                'Middle school',
                'High school',
                'Undergraduate',
                'Postgraduate'
            ],
            required: true
        },
        formula: {
            type: String,
            enum: ['progression', 'support'],
            required : true
        }
    },
    { discriminatorKey: 'role' }
)
var Student = User.discriminator('Student', StudentSchema);

var ParentSchema = mongoose.Schema(
    {
        children : {
            type: [ StudentSchema ],
            required: true
        },
    },
    { discriminatorKey: 'role' }
);
var Parent = User.discriminator('Parent', ParentSchema);
