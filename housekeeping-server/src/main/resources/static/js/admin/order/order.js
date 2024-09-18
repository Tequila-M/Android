$(function () {
	var columns = [

			{ checkbox: true, align: 'center' },
			{ title: '用户', field: 'user.nickname' }, 			
			{ title: '姓名', field: 'user.realName' }, 			
			{ title: '手机号码', field: 'user.mobile' }, 			
			{ title: '预约项目', field: 'project.projectName' }, 			
			{ title: '金额', field: 'totalAmount' }, 			
			{ title: '状态', field: 'status', formatter: function(value, row){
				if(value == 0){
					return '<span class="label label-default">已取消</span>';
				}else if(value == 1){
					return '<span class="label label-primary">待付款</span>';
				}else if(value == 2){
					return '<span class="label label-info">预约中</span>';
				}else if(value == 3){
					return '<span class="label label-success">已完成</span>';
				}else if(value == 4){
					return '<span class="label label-warning">已评价</span>';
				}
			}}, 			
			{ title: '预约时间', field: 'appointTime', formatter: function(value, row){
				return row.appointTime + ' ' + row.time;
			}}, 			
			{ title: '创建时间', field: 'createTime' }			
]

$("#table").bootstrapTable({
	        url: baseURL + 'order/list',
	        cache: false,
	        striped: true,
	        pagination: true,
	        pageSize: 10,
	        pageNumber: 1,
	        sidePagination: 'server',
	        pageList: [10, 25, 50],
	        columns: columns,
	        queryParams: function (params) {
	        	return {
		        	page: params.offset / 10 + 1,
		        	limit: params.limit,
					realName: vm.q.realName
	        	}
	        }
	   });
});
var vm = new Vue({
	el:'#app',
	data:{
		showList: true,
		title: null,
		appoint: {},
		q: {
			realName: ''
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.appoint = {};
		},
		compelteAppoint: function(){
			var ids = getSelectedVals("id");
			if(ids == null){
				return ;
			}
			
			confirm('确定要完成选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "order/complete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg, function(e){});
						}
					}
				});
			});
		},
		update: function (event) {
			var id = getSelectedVal("id");
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.appoint.id == null ? "order/save" : "order/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.appoint),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg, function(e){});
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedVals("id");
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "order/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg, function(e){});
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "order/info/"+id, function(r){
                vm.appoint = r.appoint;
            });
		},
		reload: function (event) {
			vm.showList = true;
			$('#table').bootstrapTable('refreshOptions',  {pageNumber: 1});
		}
	}
});