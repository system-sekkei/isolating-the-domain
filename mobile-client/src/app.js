require('./assets/css/bluma-customize.scss');

const {Elm} = require('./Main.elm');

Elm.Main.init({
    node: document.getElementById('elm')
});