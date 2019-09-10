var pageConfig = {
    privilegeCode: ['bt_select', 'bt_delete'],
    searchPath: "/jdbc/commonData/hcahighimpactarea/getPage.do",
    deletePath: '/jdbc/commonData/hcahighimpactarea/delete.do?layerId=hca_high_impact_area',
    detailPath: '/jdbc/commonData/hcahighimpactarea/getPage.do',
    savePath: '/jdbc/commonData/hcahighimpactarea/save.do',
    updatePath: '/jdbc/commonData/hcahighimpactarea/update.do',
    importConfig: {
    	'functionName': "hcahighinfo",
        'tableName': "hca_high_impact_area",
        'exportUrl': "/hcahighimpactarea/exportToExcelAction.do",
        'isExportAll': true,
        'hasDownload': false
    },
    searchFields: [
        //'pipelineOid',
        'highImpactAreaCode',
        'highImpactAreaName',
        'highImpactLevel',
    ],
    tableFields: [
        'pipelineName',
        'highImpactAreaCode',
        'highImpactAreaName',
        'highImpactLevelName',
        'startMileage',
        'endMileage',
        'hcaLength',
        'description',
        'remarks'
    ],
    addFields: [{
        title: '基本信息',
        fields: [
            'highImpactAreaCode',
            'highImpactAreaName',
            'highImpactLevel',
            'startMileage',
            'endMileage',
            'hcaLength',
            'description',
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
            'highImpactAreaCode',
            'highImpactAreaName',
            'highImpactLevelName',
            'startMileage',
            'endMileage',
            'hcaLength',
            'description',
        ]
    }, {
        title: '其他信息',
        fields: [
            'remarks'
        ]
    }],
    fieldsConfig: {
        pipelineName: {
            name: '管线名称',
        },
        highImpactAreaCode: {
            name: '编号',
            type: 'input',
            isRequired: true
        },
        highImpactAreaName:{
            name: '名称',
            type: 'input',
            isRequired: true
        },
        highImpactLevelName: {
            name: '等级'
        },
        highImpactLevel: {
            name: '等级',
            type: 'select',
            domainName: 'high_impact_level_domain',
            isRequired: true
        },
        startMileage: {
            name: '起始里程（km）',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
            isRequired: true
        },
        endMileage: {
            name: '终止里程（km）',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
            isRequired: true
        },
        hcaLength: {
            name: '长度（km）',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
            isRequired: true
        },
        description: {
            name: "地区描述",
            type: "textarea"
        },
        remarks: {
            name: "备注",
            type: "textarea"
        },
    },
    btncolwidth:300,
    rowBtns:[
        {
            name:'定位',
            icon: 'fa fa-info-circle pointer',
            method: 'locate'
        }
    ],
    methods:{
        locate: function(item){
        	if(!top.app.panelShowed){
        		top.app._goMap();
        	}
        	var layerId = "hca_high_impact_area";
        	if(!top.jasMap.getLayerVisible(layerId)){
        		top.jasMap.updateLayer(layerId, {
        			show: true,
        			where:"VERSION_OID like'" + item.versionOid + "'"
        		});
        	}
            top.jasMap.flashGraphic(item.oid, layerId, {
                deep:2,
                fieldName: 'OID'
            });
        },
        previewFile : function(){
            top.jasTools.dialog.show({
                title: '分析报告',
                src: jasTools.base.rootPath + '/jasmvvm/pages/row-onepage/hca-operation/dialogs/preview_file.html',
                height: '700px',
                width: '35%',
                left:'35%',
                bottom:'5%',
            })
        },
    }
};