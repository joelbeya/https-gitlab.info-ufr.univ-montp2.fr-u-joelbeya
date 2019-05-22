# Easy course

## Instructions pour le développement

### Étapes pour le lancement du serveur

* #### Lancer MongoDB :

    mongod --dbpath data --logpath /dev/null

* #### Lancer Serveo :

    ssh -R easycourse:80:localhost:8888 serveo.net

* #### Lancer le serveur

    node index.js
