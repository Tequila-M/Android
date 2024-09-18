$(function () {
	var columns = [

			{ checkbox: true, align: 'center' },
			{ title: '公司', field: 'teacher.realName' },
			{ title: '开始时间', field: 'startTime' }, 			
			{ title: '结束时间', field: 'endTime' }, 			
			{ title: '预约人数', field: 'maxPeople' }			
]

$("#table").bootstrapTable({
	        url: baseURL + 'setting/list',
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
		        	limit: params.limit
	        	}
	        }
	   });
});
var vm = new Vue({
	el:'#app',
	data:{
		showList: true,
		title: null,
		setting: {},
		q: {
			teacherName: ''
		},
		teacherList: []
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.setting = {
					teacherId: ''
			};
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
			var url = vm.setting.id == null ? "setting/save" : "setting/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.setting),
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
				    url: baseURL + "setting/delete",
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
			$.get(baseURL + "setting/info/"+id, function(r){
                vm.setting = r.setting;
            });
		},
		getTeacherList: function(){
			$.get(baseURL + "teacher/listAll", function(r){
				vm.teacherList = r.teacherList;
			});
		},
		reload: function (event) {
			vm.showList = true;
			$('#table').bootstrapTable('refreshOptions',  {pageNumber: 1});
		}
	},
	created: function(){
		this.getTeacherList();
	}
});