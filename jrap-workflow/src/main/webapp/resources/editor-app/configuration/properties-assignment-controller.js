/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

/*
 * Assignment
 */
var KisBpmAssignmentCtrl = [ '$scope', '$modal', function($scope, $modal) {

	// Config for the modal window
	var opts = {
		template:  'activiti-editor/configuration/properties/assignment-popup.html?version=' + Date.now(),
		scope: $scope
	};

	// Open the dialog
	$modal(opts);
}];
var KisBpmAssignmentQueryCtrl= [ '$scope', '$modal', function($scope, $modal) {

	// Config for the modal window
	var opts = {
		template:  'activiti-editor/configuration/properties/assignment-popup-query.html?version=' + Date.now(),
		scope: $scope
	};
	// Open the dialog
	$modal(opts);
}];

var KisBpmAssignmentQueryPopupCtrl = ['$scope', function($scope) {
	//关闭查询框
	$scope.closeQuery = function() {
		$scope.property.isQuery = false;
		$scope.$hide();
	}
}];
var KisBpmAssignmentPopupCtrl = [ '$scope',  function($scope) {

	// Put json representing assignment on scope
	if ($scope.property.value !== undefined && $scope.property.value !== null
		&& $scope.property.value.assignment !== undefined
		&& $scope.property.value.assignment !== null)
	{
		$scope.assignment = clone($scope.property.value.assignment);
	} else {
		$scope.assignment = {};
	}
	function clone(obj) {
		var o,i,j;
		if(typeof(obj)!="object" || obj===null)return obj;
		if(obj instanceof(Array)) {
			o=[];
			i=0;j=obj.length;
			for(;i<j;i++) {
				if(typeof(obj[i])=="object" && obj[i]!=null){
					o[i]=arguments.callee(obj[i]);
				} else {
					o[i]=obj[i];
				}
			}
		} else {
			o={};
			for(i in obj) {
				if(typeof(obj[i])=="object" && obj[i]!=null){
					o[i]=arguments.callee(obj[i]);
				}else{
					o[i]=obj[i];
				}
			}
		}
		return o;
	}
	//定义查询变量
	if($scope.property.isQuery == undefined){
		$scope.property.isQuery = false;
	}
	if( $scope.property.queryType== undefined){
		$scope.property.queryType = "";
	}
	if( $scope.candidateUserIndex == undefined){
		$scope.candidateUserIndex = 0;
	}
	if( $scope.candidateGroupIndex == undefined){
		$scope.candidateGroupIndex = 0;
	}
	//点击查询按钮
	$scope.query = function(queryType,candidateUserIndex,candidateGroupIndex){
		$scope.property.isQuery = true;
		$scope.property.queryType = queryType;
		if(candidateUserIndex!=-1){
			$scope.candidateUserIndex = candidateUserIndex;
		}
		if(candidateGroupIndex!=-1){
			$scope.candidateGroupIndex = candidateGroupIndex;
		}
	}

	//返回查询页面
	$scope.getQueryUrl = function(){
		return "activiti-editor/configuration/properties/assignment-write-template-query.html";
	}

	if ($scope.assignment.candidateUsers == undefined || $scope.assignment.candidateUsers.length == 0)
	{
		$scope.assignment.candidateUsers = [{value: ''}];
	}

	// Click handler for + button after enum value
	var userValueIndex = 1;
	$scope.addCandidateUserValue = function(index) {
		$scope.assignment.candidateUsers.splice(index + 1, 0, {value: 'value ' + userValueIndex++});
	};

	// Click handler for - button after enum value
	$scope.removeCandidateUserValue = function(index) {
		$scope.assignment.candidateUsers.splice(index, 1);
	};

	if ($scope.assignment.candidateGroups == undefined || $scope.assignment.candidateGroups.length == 0)
	{
		$scope.assignment.candidateGroups = [{value: ''}];
	}

	var groupValueIndex = 1;
	$scope.addCandidateGroupValue = function(index) {
		$scope.assignment.candidateGroups.splice(index + 1, 0, {value: 'value ' + groupValueIndex++});
	};

	// Click handler for - button after enum value
	$scope.removeCandidateGroupValue = function(index) {
		$scope.assignment.candidateGroups.splice(index, 1);
	};

	$scope.save = function() {
		$scope.property.value = {};
		handleAssignmentInput($scope);
		$scope.property.value.assignment = $scope.assignment;
		$scope.updatePropertyInModel($scope.property);
		$scope.close();
	};

	// Close button handler
	$scope.close = function() {
		handleAssignmentInput($scope);
		$scope.property.value.assignment;
		$scope.property.mode = 'read';
		$scope.$hide();
	};

	var handleAssignmentInput = function($scope) {
		if ($scope.assignment.candidateUsers)
		{
			var emptyUsers = true;
			var toRemoveIndexes = [];
			for (var i = 0; i < $scope.assignment.candidateUsers.length; i++)
			{
				if ($scope.assignment.candidateUsers[i].value != '')
				{
					emptyUsers = false;
				}
				else
				{
					toRemoveIndexes[toRemoveIndexes.length] = i;
				}
			}

			for (var i = 0; i < toRemoveIndexes.length; i++)
			{
				$scope.assignment.candidateUsers.splice(toRemoveIndexes[i], 1);
			}

			if (emptyUsers)
			{
				$scope.assignment.candidateUsers = undefined;
			}
		}

		if ($scope.assignment.candidateGroups)
		{
			var emptyGroups = true;
			var toRemoveIndexes = [];
			for (var i = 0; i < $scope.assignment.candidateGroups.length; i++)
			{
				if ($scope.assignment.candidateGroups[i].value != '')
				{
					emptyGroups = false;
				}
				else
				{
					toRemoveIndexes[toRemoveIndexes.length] = i;
				}
			}

			for (var i = 0; i < toRemoveIndexes.length; i++)
			{
				$scope.assignment.candidateGroups.splice(toRemoveIndexes[i], 1);
			}

			if (emptyGroups)
			{
				$scope.assignment.candidateGroups = undefined;
			}
		}
	};
}];