var App = App || {};
App.Models || (App.Models = {});

(function() {
    'use strict';

    // Authentication Model
    // ----------

    // Our basic **Authentication** model has `username` and `password` attributes.
    App.Models.Authentication = Backbone.Model.extend({
        defaults : {
            username : '',
            password : ''
        }
    });
}());
