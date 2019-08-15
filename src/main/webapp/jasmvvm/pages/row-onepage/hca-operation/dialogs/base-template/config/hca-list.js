var pageConfig = {
    privilegeCode: ['bt_add', 'bt_select', 'bt_update', 'bt_delete'],
    searchPath: "/jdbc/commonData/hcahighimpactarea/getPage.do",
    deletePath: '/jdbc/commonData/hcahighimpactarea/delete.do',
    detailPath: '/jdbc/commonData/hcahighimpactarea/getPage.do',
    savePath: '/jdbc/commonData/hcahighimpactarea/save.do',
    updatePath: '/jdbc/commonData/hcahighimpactarea/update.do',
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
            name: '起始里程',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
            isRequired: true
        },
        endMileage: {
            name: '终止里程',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
            isRequired: true
        },
        hcaLength: {
            name: '长度',
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
        },
        {
            name:'生成报告',
            method: 'previewFile'
        }
    ],
    methods:{
        locate: function(item){
            top.showmap2d();
            top.jasMap.flashGraphic(item.oid, "pd_zonerankcell",{
                deep:2,
                fieldName: 'OID'
            });
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
    }
};