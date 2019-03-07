/**
 * Created by xuhailin on 2017/4/7.
 */
var ApproveStrategyInstanceCtrl = [ '$scope', function($scope) {

    if ($scope.property.value == undefined && $scope.property.value == null)
    {
         $scope.property.value = '';
    }

    jQuery.ajax({
        async      :false,
        url        : _basePath +'/wfl/approve/strategy/queryAll',
        type       : 'post',
        dataType   : 'json',
        success    : function (data) {
            $scope.approveStrategyData = data.rows;
        },
     });
    

    $scope.valueChanged = function() {     
         findCodeByValue($scope.property.value);
         $scope.setApproveStrategy($scope.property);
         $scope.updatePropertyInModel($scope.property);
    };
    
    
    function findCodeByValue(value){
        var data =$scope.approveStrategyData.slice(0);
        if(!value){
            $scope.property.code = '';
            return;
        }
        for(var i=0;i<data.length;i++){
           if(data[i].description == value){
               $scope.property.code = data[i].code;
               break;
           }
        }
    }
}];
