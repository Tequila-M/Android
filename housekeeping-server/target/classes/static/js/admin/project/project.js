$(function () {
	var columns = [

			{ checkbox: true, align: 'center' },
			{ title: '图片', field: 'picUrl',
				formatter: function (value, row, index) {
					return '<img width="80px" src="'+value+'" />';
				}	
			}, 
			{ title: '项目名称', field: 'projectName' }, 			
			{ title: '分类', field: 'category.categoryName' }, 			
			{ title: '价格', field: 'price' }, 			
			{ title: '备注', field: 'remark' }, 			
			{ title: '创建时间', field: 'createTime' }			
]

$("#table").bootstrapTable({
	        url: baseURL + 'project/list',
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
					projectName: vm.q.projectName
	        	}
	        }
	   });
});
var vm = new Vue({
	el:'#app',
	data:{
		showList: true,
		title: null,
		project: {
			
		},
		categoryList: [],
		q: {
			projectName: ''
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.project = {
					categoryId: '',
					picUrl: ''
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
			var url = vm.project.id == null ? "project/save" : "project/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.project),
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
				    url: baseURL + "project/delete",
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
			$.get(baseURL + "project/info/"+id, function(r){
                vm.project = r.project;
            });
		},
		getCategoryList: function(){
			$.get(baseURL + "category/getAll", function(r){
				vm.categoryList = r.categoryList;
			});
		},
		reload: function (event) {
			vm.showList = true;
			$('#table').bootstrapTable('refreshOptions',  {pageNumber: 1});
		}
	},
	created: function(){
		this.getCategoryList();
	}
});