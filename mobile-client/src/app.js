require('./assets/css/bluma-customize.scss');
require('@mdi/font/css/materialdesignicons.css');

const {Elm} = require('./Main.elm');

Elm.Main.init({
    node: document.getElementById('elm')
});