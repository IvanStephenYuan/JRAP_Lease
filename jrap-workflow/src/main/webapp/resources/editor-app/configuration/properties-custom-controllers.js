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
var ApproveRuleInstanceCtrl = [ '$scope', function($scope) {
     if ($scope.property.value !== undefined && $scope.property.value !== null
     && $scope.property.value.assignment !== undefined
     && $scope.property.value.assignment !== null){
         $scope.assignment = $scope.property.value.assignment;
         } else {
            $scope.assignment = null;
     }
     $scope.showCustom= function () {
        if (jQuery('#win').data('kendoWindow')) {
             jQuery('#win').data('kendoWindow').open()
        } else {
            jQuery('#win').kendoWindow({
                width: 600,
                height: 500,
                modal: true,
                title: "审批规则",
            }).data('kendoWindow').center();

            var dataSource = new kendo.data.DataSource({
                transport:{
                    read:function(options){
                       var data = $scope.query();
                       if(!data){
                           data = []
                       }
                       options.success(data);
                    },
                    create: function(options) {
                        options.success(options.data.models)
                    },
                    update: function(options) {
                        options.success(options.data.models)
                    },
                    destroy: function(options) {
                        options.success(options.data.models)
                    }
                },
                batch: true,
                schema: {
                    model: {
                   	 id: "code",
                        fields: {
                            "code": {},
                            "assignee"  :{
                                validation: {
                                    required:true
                                }
                            }
                        },
                        editable: function (col) {
                            if (col == 'assignee') {
                                if( this.code != 'APPOINTED_POSITION' && this.code != 'APPOINTED_EMPLOYEE' && this.code != 'APPOINTED_ROLE'){
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                }
            });
           
           var crudServiceBaseUrl = _basePath +'/wfl/approve/candidate/rule',
            comboboxDataSource = new kendo.data.DataSource({
               transport:{
                   read:{
                       url:crudServiceBaseUrl+"/queryAll",
                       type : "POST",
                       dataType: "json"
                   }
               },
               
               schema: {
                   data:'rows',
                   total:'total',
                   model: {

                       id:'code',
                       fields: {}
                   }
               }
           }); 
           
           var grid = jQuery('#win').find("#grid").kendoGrid({
               navigatable: true,
               dataSource:dataSource,
               height: 430,
               scrollable: true,
               selectable:'rowbox,multiple',
               columns: [
                   {
                       field: "code",
                       title: '审批规则',
                       width: 150,
                       template: function (dataItem) {
                           var v = dataItem.code;
                           if(comboboxDataSource.data().length){
                        	   jQuery.each(comboboxDataSource.data(), function (i, n) {
                                   if ((n.code || '').toLowerCase() == (v || '').toLowerCase()) {
                                       v = n.description;
                                       dataItem.description = v;
                                       return v;
                                   }
                               })
                           }else{
                              v = dataItem.description;
                           }
                           return v || '';
                       },
                       editor: function (container, options) {
                             jQuery('<input required  name="' + options.field + '"/>')
                                   .appendTo(container)
                                   .kendoComboBox({
                                       dataTextField: "description",
                                       dataValueField: "code",
                                       dataSource: comboboxDataSource,
                                       valuePrimitive: true,
                                       change:function(e){
                                           options.model.set("assigneeName",undefined);
                                           options.model.set("assigneeCode",undefined);
                                           options.model.set("assignee",undefined);
                                       }
                           });
                       }
                   },
                   {
                       field: "assignee",
                       title: '审批者',
                       template: function (dataItem) {
                           return dataItem['assigneeName'] || ''
                       },
                       width: 150,
                       editor: function (container, options) {
                           var code = options.model.code;
                           var lov = "";
                           if( code == "APPOINTED_POSITION"){
                              lov = "LOV_POSITION";
                              jQuery('<input required name="' + options.field + '"/>')
                              .appendTo(container)
                              .kendoLov({
                                  contextPath:_basePath,
                                  locale:_locale,
                                  code:lov,
                                  model:options.model,
                                  textField:'assigneeName',
                                  select:function(e){
                                      var data = e.item;
                                      options.model.set("assigneeName",data.name);
                                      options.model.set("assigneeCode",data.positionCode);
                                  }
                              });
                           }else if(code=="APPOINTED_EMPLOYEE"){
                              lov = "LOV_ACT_EMPLOYEE";
                              jQuery('<input required name="' + options.field + '"/>')
                              .appendTo(container)
                              .kendoLov({
                                  contextPath:_basePath,
                                  locale:_locale,
                                  code:lov,
                                  model:options.model,
                                  textField:'assigneeName',
                                  select:function(e){
                                      var data = e.item;
                                      options.model.set("assigneeName",data.name);
                                      options.model.set("assigneeCode",data.employeeCode);
                                  },
                                  query: function (e) {
                                      e.param['enabledFlag'] = 'Y'
                                  }
                              });
                           }else if(code=="APPOINTED_ROLE"){
                               lov = "LOV_ROLE";
                               jQuery('<input required name="' + options.field + '"/>')
                                   .appendTo(container)
                                   .kendoLov({
                                       contextPath:_basePath,
                                       locale:_locale,
                                       code:lov,
                                       model:options.model,
                                       textField:'assigneeName',
                                       select:function(e){
                                           var data = e.item;
                                           options.model.set("assigneeName",data.name);
                                           options.model.set("assigneeCode",data.roleCode);
                                       },
                                       query: function (e) {
                                           e.param['enabledFlag'] = 'Y'
                                       }
                                   });
                           }else{
                              return;
                           }

                       }
                   },
                   {
                       title: '审批权限',
                       width: 80,
                       attributes: {style: "text-align:center"},
                       template: function (dataItem) {
                           return '<a href="javascript:void(0);" onclick="editCondition(\'' + dataItem.uid + '\')">编辑</a>'
                       }
                   }],
                editable: true
           }).data("kendoGrid"); 

           jQuery('#win').data('kendoWindow').open()
        }
        
     };
   
    	
    	//定义查询变量
    	//点击查询按钮
    	$scope.query = function(){
            return $scope.assignment;
    	}

    	$scope.save = function() {
            var grid = jQuery('#win').find("#grid");
            if(grid.data("kendoGrid").validate()) {
                $scope.property.value = {};
                $scope.property.value.assignment = $scope.assignment;
                $scope.property.value.assignee = handleAssignmentInput($scope);
                $scope.setAssignment($scope.property);
                $scope.updatePropertyInModel($scope.property);
                $scope.close();
            }
    	};

        $scope.queryCondition = function(uid){
            if(uid ){
                 var gridData =jQuery('#grid').data('kendoGrid');
                 var item = gridData.dataSource.getByUid(uid)
                 return item.rules;
            }
            return [];
    	}
    	
        $scope.saveCondition = function(uid,data){
            var a =jQuery('#grid').data('kendoGrid');
            var item = a.dataSource.getByUid(uid);
            item.rules = data;
        }
    	$scope.close = function() {
            $scope.property.value.assignment;
            $scope.property.mode = 'read';
            jQuery('#win').data('kendoWindow').close();
        };

        var handleAssignmentInput = function($scope) {
            var assignee = [];
            if ($scope.assignment)
            {
                for (var i = 0; i < $scope.assignment.length; i++)
                {
                   assignee[assignee.length] = $scope.assignment[i].assigneeCode;

               }
            }
            return assignee.join(',');
        };
}];