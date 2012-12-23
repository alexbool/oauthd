var App = App || {};

$(function($) {
    'use strict';

    // The Application
    // ---------------

    // Our overall **AppView** is the top-level piece of UI.
    App.Views.App = Backbone.View.extend({

        // Instead of generating a new element, bind to the existing skeleton of
        // the App already present in the HTML.
        el : '#app',

        // Delegated events
        events : {
        },

        // At initialization we bind to the relevant events on the `Todos`
        // collection, when items are added or changed. Kick things off by
        // loading any preexisting todos that might be saved in *localStorage*.
        initialize : function() {
            this.navbarLinks = this.$('#navbar-links > li');
            
            App.router.on('all', this.selectNavElement, this);
            /*this.input = this.$('#new-todo');
            this.allCheckbox = this.$('#toggle-all')[0];
            this.$footer = this.$('#footer');
            this.$main = this.$('#main');

            app.Todos.on('add', this.addOne, this);
            app.Todos.on('reset', this.addAll, this);
            app.Todos.on('change:completed', this.filterOne, this);
            app.Todos.on('filter', this.filterAll, this);
            app.Todos.on('all', this.render, this);

            app.Todos.fetch();*/
            this.loginView = new App.Views.LoginBtn().render();
        },

        render : function() {
            return this;
        },
        
        selectNavElement : function(e) {
            var route = e.split(':')[1];
            if (route.length == 0) return;
            _(this.navbarLinks).each(function(link) { $(link).removeClass('active'); });
            $('#navbar-links > li > a[href="#' + route + '"]').parent().addClass('active');
        }
    });
});
