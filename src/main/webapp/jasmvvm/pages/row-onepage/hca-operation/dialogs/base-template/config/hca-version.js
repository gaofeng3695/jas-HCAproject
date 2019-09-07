var pageConfig = {
    privilegeCode: ['bt_add', 'bt_select', 'bt_update', 'bt_delete'],
    searchPath: "/jdbc/commonData/hcaversion/getPage.do",
    deletePath: '/jdbc/commonData/hcaversion/delete.do?layerId=hca_high_impact_area',
    detailPath: '/jdbc/commonData/hcaversion/getPage.do',
    savePath: '/jdbc/commonData/hcaversion/save.do',
    updatePath: '/jdbc/commonData/hcaversion/update.do',
    searchFields: [
        'pipelineOid',
        'versionName',
        'versionCode'
    ],
    tableFields: [
        'pipelineName',
        'versionName',
        'versionCode',
        'modifyUserName',
        'modifyDatetime',
        'remarks'
    ],
    addFields: [{
        title: '基本信息',
        fields: [
            'pipelineOid',
            'versionName',
            'versionCode'
        ]
    }, {
        title: '其他信息',
        fields: [
            'remarks'
        ]
    }],
    detailFields: [{
        title: '基本信息',
        fields: [
            'pipelineName',
            'versionName',
            'versionCode',
            'modifyUserName',
            'modifyDatetime'
        ]
    }, {
        title: '其他信息',
        fields: [
            'remarks'
        ]
    }],
    fieldsConfig: {
        pipelineOid:{
            type: 'select',
            name: '评价管线',
            optionUrl: '/jdbc/commonData/hcapipeline/getPage.do',
            isRequired: true,
            //disabled: true,
        },
        pipelineName: {
            name: '评价管线',
        },
        versionName: {
            name: '评价名称',
            type: 'input',
            isRequired: true
        },
        versionCode: {
            name: '评价编号',
            type: 'input',
            isRequired: true
        },
        remarks: {
            name: "备注",
            type: "textarea"
        },
        modifyUserName: {
            name: "评价人"
        },
        modifyDatetime: {
            name: "评价时间"
        }
    },
    btncolwidth:370,
    rowBtns:[
        {
            name: '定位',
            method: 'enableUse'
        },
        {
            name:'生成报告',
            method: 'previewFile'
        },
        {
            name:'高后果区详情',
            method: 'hcaAreaList'
        },
    ],
    methods:{
        /*importFile: function(){
            window.jasTools.dialog.show({
                src: jasTools.base.rootPath + '/jasmvvm/pages/row-onepage/hca-operation/dialogs/base-template/dialogs/upload.html?forbusiness=3',
                width: '50%',
                height: '80%',
                bottom:'20%',
                left:'35%',
                title: '导入',
            })
        },*/
        enableUse: function(row) {
        	if(!top.app.panelShowed){
        		top.app._goMap();
        	}
            top.jasMap.flashGraphic(row.pipelineOid, 'hca_pipeline', {
                deep: 2,
                fieldName: 'OID'
            });        	
            top.jasMap.updateLayer("hca_high_impact_area", {
            	show: true,
            	where: "VERSION_OID = '" + row.oid +"'"
            });
            var lineGraphic = top.jasMap.getPipeline();
            var x = lineGraphic.geometry.paths[0][0][0];
            var y = lineGraphic.geometry.paths[0][0][1] - 0.003;
            top.jasMap.zoomAt(15,x ,y );
        },
        previewFile : function(row){
        	var that = this;
        	var loading = top.app.$loading({
                lock: true,
                text: '报告生成中......',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            })
        	var url = jasTools.base.rootPath + '/hcaReport/createHcaDoc.do';
        	jasTools.ajax.post(url, {
        		pipelineOid: row.pipelineOid,
        		versionOid: row.oid
            }, function (data) {
            	loading.close();
                top.jasTools.dialog.show({
                	title: '分析报告',
                	src: jasTools.base.rootPath + '/jasmvvm/common/pages/pdfjs_1.10.88/web/viewer.html?isHcaReport=true&fileId=' + data.data,
                	height: '700px',
                	width: '35%',
                	left:'35%',
                	bottom:'5%',
                })
            }, function(data){
            	loading.close();
            });
        },
        hcaAreaList: function (row) {
        	var that = this;
            jasTools.mask.show({
                src: jasTools.base.rootPath + '/jasmvvm/pages/row-onepage/hca-operation/dialogs/base-template/base-template.html?pageCode=hca-list&pipelineOid='+row.pipelineOid+'&versionOid='+row.oid,
                height: '80%',
                width: '80%',
                title: '高后果区列表',
                cbForClose: function (param) {
                    that.$refs.table.refresh();
                }
            })
        }
    }
};