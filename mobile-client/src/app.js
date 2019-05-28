require('bulma/bulma.sass');

const {Elm} = require('./Main.elm');

Elm.Main.init({
    node: document.getElementById('elm')
});