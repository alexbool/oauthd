var App = App || {};

$(function() {
    // Kick things off by creating the **App**.
    new App.Views.App();
    App.router.home();
    Backbone.history.start();
});
