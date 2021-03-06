var pageConfig = {
    privilegeCode: ['bt_add', 'bt_select', 'bt_update', 'bt_delete'],
    searchPath: "/jdbc/commonData/hcaversion/getPage.do",
    deletePath: '/jdbc/commonData/hcaversion/delete.do?layerId=hca_area_version',
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
            'versionCode',
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
            'modifyDatetime',
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
            name: '识别管线',
            optionUrl: '/jdbc/commonData/hcapipeline/getPage.do',
            isRequired: true,
//            disabled: true,
        },
        pipelineName: {
            name: '识别管线',
        },
        versionName: {
            name: '地区等级划分名称',
            type: 'input',
            isRequired: true
        },
        versionCode: {
            name: '划分编号',
            type: 'input',
            isRequired: true
        },
        remarks: {
            name: "备注",
            type: "textarea"
        },
        modifyUserName: {
            name: "识别人"
        },
        modifyDatetime: {
            name: "识别时间"
        },
        /*hasUsed: {
            name: "是否启用",
            type: "select",
            options: [{
                key: 0,
                value: "否"
            }, {
                key: 1,
                value: "是"
            }],
            formatter: function (a, b, value, c) {
                if (value == 0) return '否';
                if (value == 1) return '是';
                return '-';
            }
        }*/
    },
    btncolwidth:360,
    rowBtns:[
        {
            name: '定位',
            method: 'enableUse'
        },
        {
            name:'地区划分详情',
            method: 'areaList'
        },
    ],
    methods:{
        importFile: function(row){
            window.jasTools.dialog.show({
                src: jasTools.base.rootPath + '/jasmvvm/pages/row-onepage/hca-operation/dialogs/base-template/dialogs/upload.html?forbusiness=3',
                width: '50%',
                height: '80%',
                bottom:'20%',
                left:'35%',
                title: '导入',
            })
        },
        enableUse: function(row) {
        	if(!top.app.panelShowed){
        		top.app._goMap();
        	}
            top.$('#mapIframe')[0].contentWindow.hcaMapApp.localAreaVersionOid = row.oid;
            top.jasMap.updateLayer("hca_high_impact_area", {
                show: false,
            });
            top.jasMap.updateLayer("hca_area", {
            	show: true,
            	where: "VERSION_OID = '" + row.oid +"'"
            });
            var layer = top.jasMap.getLayerById('hca_pipeline');
            var hasGraphics = false;
            $.each(layer.graphics, function(i, lineGraphic){
            	if(lineGraphic.attributes.OID == row.pipelineOid){
            		var x = lineGraphic.geometry.paths[0][0][0];
                    var y = lineGraphic.geometry.paths[0][0][1];
                    top.jasMap.zoomAt(12,x ,y );
                    hasGraphics = true;
                    return;
            	}
            });
            if(!hasGraphics){
            	top.jasMap.flashGraphic(row.pipelineOid, 'hca_pipeline', {
                    fieldName: 'OID'
                });
            }
        },
        previewFile : function(){
            window.jasTools.dialog.show({
                title: '分析报告',
                src: jasTools.base.rootPath + '/jasmvvm/pages/row-onepage/hca-operation/dialogs/preview_file.html',
                height: '700px',
                width: '35%',
                left:'35%',
                bottom:'5%',
            })
        },
        areaList: function (row) {
        	var that = this;
            jasTools.mask.show({
                title: '地区列表',
                src: jasTools.base.rootPath + '/jasmvvm/pages/row-onepage/hca-operation/dialogs/base-template/base-template.html?pageCode=area-list&pipelineOid='+row.pipelineOid+'&versionOid='+row.oid,
                height: '80%',
                width: '80%',
                cbForClose: function (param) {
                    that.$refs.table.refresh();
                }
            })
        },
    }
};