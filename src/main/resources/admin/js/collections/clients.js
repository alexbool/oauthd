var App = App || {};
App.Collections || (App.Collections = {});

(function() {
    'use strict';

    // Client Collection
    // ---------------

    // The collection of clients is backed by a remote server.
    var ClientList = Backbone.Collection.extend({

        // Reference to this collection's model.
        model : App.Models.Client
    });

    // Create our global collection of **Clients**.
    App.Collections.Clients = new ClientList();
}());
