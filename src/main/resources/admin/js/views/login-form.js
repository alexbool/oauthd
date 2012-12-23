var App = App || {};
App.Views || (App.Views = {});

$(function($) {
    'use strict';

    App.Views.LoginForm = Backbone.View.extend({
        template : _.template($('#tmpl-login-form').html()),
        render : function() {
            $('#login-btn-link').popover({
                html : true,
                placement : 'bottom',
                title : 'Sign in',
                content : this.template
            });
            return this;
        }
    });
});
