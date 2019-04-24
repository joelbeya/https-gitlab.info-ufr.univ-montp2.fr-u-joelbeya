const mongoose = require('mongoose');
let Schema = mongoose.Schema;

var UserSchema = mongoose.Schema(
    {
        kind: {
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
        },
        formula: {
            type: String,
            required: true,
            enum: ['progression', 'support']
        }
    },
    {
        collection: 'user',
        discriminatorKey: 'kind'
    },
);

var User = mongoose.model('User', UserSchema);

var StudentSchema = mongoose.Schema(
    {
        schoolLevel: {
            type: String,
            required: true,
            enum: ['6e', '5e', '4e', '3e', '2nde', '1ere', 'Term']
        }
    },
    { discriminatorKey: 'kind' }
)
var Student = User.discriminator('Student', StudentSchema);

var ParentSchema = mongoose.Schema(
    {
        children : {
            type: [{ type: Schema.Types.ObjectId, ref: 'Student' }],
            required: true
        },
    },
    { discriminatorKey: 'kind' }
);
var Parent = User.discriminator('Parent', ParentSchema);
