<%@ page 
    language="java"
    contentType="text/html; charset=UTF-8"
    trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/taglibs.jsp" %>

<div data-ng-controller="DashboardCtrl">
	<div class="row">
         <div class="col-lg-12">
         	<h2><i class="fa fa-pie-chart fa-fw"></i> Dashboard <sec:authorize access="hasAnyRole('ROLE_ADMIN')"><button class="btn btn-xs btn-success" data-ng-click="openDashboardDetailsModal()"> <i class="fa fa-plus-circle"></i> new</button></sec:authorize></h2><br/>
         </div>
    </div>
	<div class="row">
        <div class="col-lg-{{dashboard.size}}" data-ng-repeat="dashboard in dashboards | orderBy:'position'">
            <div class="panel panel-default">
                <div class="panel-heading">
                    {{dashboard.title}}
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                    <i class="float_right fa fa-gear pointer" style="line-height: 20px;" data-ng-click="openDashboardDetailsModal(dashboard.id, false)"></i>
                    <i class="float_right fa fa-copy pointer" style="line-height: 20px;  margin-right: 5px;" data-ng-click="openDashboardDetailsModal(dashboard.id, true)"></i>
                	</sec:authorize>
                </div>
                <div align="center" class="panel-body" style="height: 350px; padding-bottom: 35px;">
                     <linechart data-ng-if="dashboard.type == 'linechart'" data="dashboard.data" options="dashboard.model"></linechart>
                     <div  data-ng-if="dashboard.type == 'piechart'" class="pie-chart">
                     	<pie-chart data="dashboard.data.dataset" options="dashboard.model"></pie-chart>
                     </div>
                </div>
            </div>
        </div>
	</div>
</div>

              