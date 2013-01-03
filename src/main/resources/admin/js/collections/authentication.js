var App = App || {};
App.Collections || (App.Collections = {});

(function() {
    'use strict';

    // Authentication Collection
    // ---------------

    // The collection of single authentication object backed by local storage.
    var Authentication = Backbone.Collection.extend({
        localStorage: new Backbone.LocalStorage("Authentication"),
        defaults: { id: 1 },

        // Reference to this collection's model.
        model : App.Models.Authentication
    });

    // Create our global collection of **Clients**.
    App.Collections.Authentication = new Authentication();
}());
