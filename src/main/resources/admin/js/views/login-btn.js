var App = App || {};
App.Views || (App.Views = {});

$(function($) {
    'use strict';

    App.Views.LoginBtn = Backbone.View.extend({
        el : '#login-btn',
        template : _.template($('#tmpl-login-btn').html()),
        render : function() {
            this.$el.html(this.template);
            new App.Views.LoginForm().render();
            return this;
        }
    });
});
