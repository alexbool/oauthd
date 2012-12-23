var App = App || {};
App.Views || (App.Views = {});

$(function($) {
    'use strict';

    // Clients View
    // ---------------

    // Our overall **AppView** is the top-level piece of UI.
    App.Views.Clients = Backbone.View.extend({
        el : '#main',
        template : _.template($('#tmpl-clients').html()),
        render : function() {
            this.$el.html(this.template);
            return this;
        }
    });
});
