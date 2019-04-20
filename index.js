const bodyParser = require('body-parser');
const cors = require('cors');
const fs = require('fs');
const mongoose = require('mongoose');
const ngrok = require('ngrok');

const app = require('express')();

require('./models/userModel');

const userRoutes = require('./routes/user_routes.js');

app.use(cors());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use('/users', userRoutes);

mongoose.connect('mongodb://127.0.0.1:27017/ec_db', { useNewUrlParser: true });
let db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));
db.once('open', () => {
    console.log("Connected to MongoDB");
});

let port = process.env.PORT || 8080;

/* Obtention d'une URL publique en utilisant le service ngrok */
(async function() {
    const publicUrl = await ngrok.connect(port);
    console.log('Url du serveur : ', publicUrl);
})();

app.listen(port, () => {
    console.log('Server running on port ' + port);
});
