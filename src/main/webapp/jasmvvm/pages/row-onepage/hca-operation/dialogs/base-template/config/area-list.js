var pageConfig = {
    privilegeCode: ['bt_select', 'bt_delete', 'bt_export', 'bt_import'],
    searchPath: "/jdbc/commonData/hcaarea/getPage.do",
    deletePath: '/jdbc/commonData/hcaarea/delete.do',
    detailPath: '/jdbc/commonData/hcaarea/getPage.do',
    savePath: '/jdbc/commonData/hcaarea/save.do',
    updatePath: '/jdbc/commonData/hcaarea/update.do',
    importConfig: {
    	'functionName': "areainfo",
        'tableName': "hca_area",
        'exportUrl': "/hcaarea/exportToExcelAction.do",
    },
    searchFields: [
       // 'pipelineOid',
        'areaCode',
        'regionLevel',
    ],
    tableFields: [
        'pipelineName',
        'areaCode',
        'regionLevelName',
        'startMileage',
        'endMileage',
        'areaLength',
        'population',
        'description',
        'remarks'
    ],
    addFields: [{
        title: '基本信息',
        fields: [
           // 'pipelineOid',
            'areaCode',
            'regionLevel',
            'startMileage',
            'endMileage',
            'areaLength',
            'population',
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
            'areaCode',
            'regionLevelName',
            'startMileage',
            'endMileage',
            'areaLength',
            'population',
            'description',
        ]
    }, {
        title: '其他信息',
        fields: [
            'remarks'
        ]
    }],
    fieldsConfig: {
        /*pipelineOid:{
            type: 'select',
            name: '管线名称',
            optionUrl: '/jdbc/commonData/hcapipeline/getPage.do',
            isRequired: true,
            disabled: true,
        },*/
        pipelineName: {
            name: '管线名称',
        },
        areaCode: {
            name: '编号',
            type: 'input',
            isRequired: true
        },
        regionLevelName: {
            name: '等级'
        },
        regionLevel: {
            name: '等级',
            type: 'select',
            domainName: 'region_level_domain',
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
        areaLength: {
            name: '长度（km）',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
            isRequired: true
        },
        population:{
            name: '人口数量',
            type: 'number',
            max:9999999,
            min: 0,
            precision:0,
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
            method: 'locateArea'
        }
    ],
    methods:{
        locateArea: function(item){
        	if(!top.app.panelShowed){
        		top.app._goMap();
        	}
            top.jasMap.flashGraphic(item.oid, "hca_area",{
                deep:2,
                fieldName: 'OID'
            });
        },
        /*previewFile : function(){
            window.jasTools.dialog.show({
                title: '分析报告',
                src: jasTools.base.rootPath + '/jasmvvm/pages/row-onepage/hca-operation/dialogs/preview_file.html',
                height: '700px',
                width: '35%',
                left:'35%',
                bottom:'5%',
            })
        },*/
    }
};