var pageConfig = {
    privilegeCode: ['bt_add', 'bt_select', 'bt_update', 'bt_delete','bt_position'],
    searchPath: "/jdbc/commonData/hcapipeline/getPage.do",
    deletePath: '/jdbc/commonData/hcapipeline/delete.do',
    detailPath: '/jdbc/commonData/hcapipeline/getPage.do',
    savePath: '/jdbc/commonData/hcapipeline/save.do',
    updatePath: '/jdbc/commonData/hcapipeline/update.do',
    searchFields: [
        'pipelineName',
        'pipelineCode'
    ],
    tableFields: [
        'pipelineName',
        'pipelineCode',
        'startMileage',
        'endMileage',
        'pipelineLength',
        'outsideDiameter',
        'pressure',
        'remarks'
    ],
    addFields: [{
        title: '基本信息',
        fields: [
            "pipelineName",
            'pipelineCode',
            'startMileage',
            'endMileage',
            'pipelineLength',
            'outsideDiameter',
            'pressure'
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
            "pipelineName",
            'pipelineCode',
            'startMileage',
            'endMileage',
            'pipelineLength',
            'outsideDiameter',
            'pressure'
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
            type: 'input',
            isRequired: true
        },
        pipelineCode: {
            name: '管线编号',
            type: 'input',
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
        pipelineLength: {
            name: '管道长度',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
            isRequired: true
        },
        outsideDiameter: {
            name: '管道外管径(mm)',
            type: 'number',
            max:999999999999999,
            min: 0,
            precision:0
        },
        pressure: {
            name: '管线稳态运行时允许的最大压力(mpa)',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
        },
        remarks: {
            name: "备注",
            type: "textarea"
        }
    },
    btncolwidth:320,
    rowBtns:[
        {
            name: '导入',
            icon: 'fa fa-mail-forward',
            method: 'importFile'
        },
        {
            name:'定位',
            icon: 'fa fa-info-circle pointer',
            method: 'locatePipeline'
        },
        {
            name:'开始识别',
            icon: 'fa fa-eye',
            method: 'createBuffer'
        }
    ],
    methods:{
        createBuffer: function(){
            var that = this;
            var selectedRowId = that.selectedRowOids;
            window.Vue.prototype.$confirm('是否开始高后果区识别？',  "提示",  {
                type: 'warning',
                callback: function(action){
                    if (action === 'confirm') {
                        that.jasMap.flashGraphic(selectedRowId, 'hca_pipeline',{
                            deep:2
                        });
                        that.jasMap.updateLayer('hca_pipeline',{
                            "show":true,
                            "fieldName":"oid",
                            "fieldValues":["fa692d8c-6dfe-41d6-a8e1-6303a5ebfae5"]
                        });
                    }
                }
            });
        },
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
        locatePipeline: function(item){
        	if(!top.hcaMapApp){
        		top.showmap2d();
        		return; 
        	}
        	top.jasMap.flashGraphic(item.oid, 'hca_pipeline',{
                deep:2,
                fieldName: 'OID'
            });
        },
    }
};