var App = App || {};
App.Views || (App.Views = {});

$(function($) {
    'use strict';

    App.Views.LoginForm = Backbone.View.extend({
        template : _.template($('#tmpl-login-form').html()),
        render : function() {
            var po = $('#login-btn-link').popover({
                html : true,
                placement : 'bottom',
                title : 'Sign in',
                content : this.template
            });
            var that = this;
            $('#login-btn-link').on('click', function(e) {
                po.data('popover').tip().find('#login-form-btn-sign-in').on('click', { that : that }, that.trySignIn);
            });
            return this;
        },
        trySignIn : function(e) {
            var username = $('#login-form-username').val();
            var password = $('#login-form-password').val();
            e.data.that.testAuthentication(username, password);
            e.preventDefault();
        },
        testAuthentication : function(username, password) {
            var server = window.location.host.split(':')[0];
            $.ajax({
                url : "http://" + server + ":8081/client",
                username : username,
                password : password
            }).done(function(data) {
                alert("ololo");
            });
        }
    });
});
