var App = App || {};
App.Models || (App.Models = {});

(function() {
    'use strict';

    // Client Model
    // ----------

    // Our basic **Client** model has `client_secret` and `authorities` attributes.
    App.Models.Client = Backbone.Model.extend({

        idProperty : "client_id",
        // Default attributes for the client
        // and ensure that each todo created has `title` and `completed` keys.
        defaults : {
            client_secret : '',
            authorities : []
        }
    });
}());
