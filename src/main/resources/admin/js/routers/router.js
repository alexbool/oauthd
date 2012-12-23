var App = App || {};

(function() {
    'use strict';

    // App Router
    // ----------

    var AppRouter = Backbone.Router.extend({
        routes : {
            'home' : 'home',
            'clients' : 'clients'
        },

        home : function() {
            new App.Views.Home().render();
        },

        clients : function() {
            new App.Views.Clients().render();
        }
    });

    App.router = new AppRouter();
}());
