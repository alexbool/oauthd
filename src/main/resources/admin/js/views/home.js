var App = App || {};
App.Views || (App.Views = {});

$(function($) {
    'use strict';

    // Home View
    // ---------------

    // Our overall **AppView** is the top-level piece of UI.
    App.Views.Home = Backbone.View.extend({
        el : '#main',
        template : _.template($('#tmpl-home').html()),
        render : function() {
            this.$el.html(this.template);
            return this;
        }
    });
});
