const bodyParser = require('body-parser');
const cors = require('cors');
const mongoose = require('mongoose');

const app = require('express')();

require('./models/course_model');
require('./models/email_verif_model');
require('./models/user_model');

const userRoutes = require('./routes/user_routes.js');
const courseRoutes = require('./routes/course_routes.js');

app.use(cors());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use('/users', userRoutes);
app.use('/courses', courseRoutes);

/* Connect to MongoDB through mongoose */
mongoose.connect(
    'mongodb://127.0.0.1:27017/ec_db',
    { useNewUrlParser: true, useFindAndModify: false }
);

let db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));
db.once('open', () => {
    console.log('\x1b[36m%s\x1b[0m', 'Connected to MongoDB');
});

let port = process.env.PORT || 8080;

app.listen(port, () => {
    console.log('\x1b[36m%s\x1b[0m', 'Server running on port ' + port);
});
