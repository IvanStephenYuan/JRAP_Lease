'use strict';

angular.module('activitiModeler')
    .directive('editorSidebar', function () {
        return {
            restrict: 'AC',
            template: '<i class="fa fa-arrows-h"></i>',
            link: function (scope, el, attr) {
                el.on('click', function () {
                    $("#canvasHelpWrapper").toggleClass("hide");
                    el.toggleClass("active");
                    return false;
                });
            }
        };
    }
);