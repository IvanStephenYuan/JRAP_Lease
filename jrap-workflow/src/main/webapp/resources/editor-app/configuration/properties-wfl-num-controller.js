/**
 * Created by xuhailin on 2017/4/7.
 */
var WfiNumPropertyCtrl = [ '$scope', function ($scope) {

    $scope.shapeId = $scope.selectedShape.id;
    $scope.valueFlushed = false;
    /** Handler called when input field is blurred */
    $scope.inputBlurred = function() {
        $scope.setWfiNum($scope.property);
        $scope.valueFlushed = true;
        $scope.updatePropertyInModel($scope.property);
    };

    $scope.enterPressed = function(keyEvent) {
        if (keyEvent && keyEvent.which === 13) {
            keyEvent.preventDefault();
            $scope.inputBlurred(); // we want to do the same as if the user would blur the input field
        }
    };

    $scope.$on('$destroy', function controllerDestroyed() {
        if(!$scope.valueFlushed) {
            /*输入框失焦未保存到表单属性*/
            /*$scope.updatePropertyInModel($scope.property, $scope.shapeId);*/
        }
    });

}];