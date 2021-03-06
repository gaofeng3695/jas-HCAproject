var pageConfig = {
    privilegeCode: ['bt_add', 'bt_select', 'bt_update', 'bt_delete','bt_position', 'bt_export', 'bt_import'],
    searchPath: "/jdbc/commonData/hcapipeline/getPage.do",
    deletePath: '/jdbc/commonData/hcapipeline/delete.do?layerId=hca_pipeline',
    detailPath: '/jdbc/commonData/hcapipeline/getPage.do',
    savePath: '/jdbc/commonData/hcapipeline/save.do',
    updatePath: '/jdbc/commonData/hcapipeline/update.do',
    importConfig: {
    	'functionName': "pipelineinfo",
        'tableName': "hca_pipeline",
        'exportUrl': "/hcapipeline/exportToExcelAction.do",
        'hasDownload': true
    },
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
        pipelineLength: {
            name: '管线长度（km）',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
            isRequired: true
        },
        outsideDiameter: {
            name: '管线外管径(mm)',
            type: 'number',
            max:9999999,
            min: 0,
            precision:0,
            isRequired: true
        },
        pressure: {
            name: '管线稳态运行时允许的最大压力(mpa)',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3,
            isRequired: true
        },
        remarks: {
            name: "备注",
            type: "textarea"
        }
    },
    btncolwidth:320,
    rowBtns:[
        /*{
            name: '导入',
            icon: 'fa fa-mail-forward',
            method: 'importFile'
        },*/
        {
            name:'定位',
            icon: 'fa fa-info-circle pointer',
            method: 'locatePipeline'
        },
        {
            name:'高后果区识别',
            icon: 'fa fa-eye',
            method: 'createBuffer'
        }
    ],
    methods:{
        createBuffer: function(item){
            var that = this;
            top.Vue.prototype.$confirm('是否开始高后果区识别？',  "提示",  {
                type: 'warning',
                callback: function(action){
                    if (action === 'confirm') {
                    	if(!top.app.panelShowed){
                    		top.app._goMap();
                    	}
                        /*top.jasMap.flashGraphic(item.oid, 'hca_pipeline',{
                            deep:2,
                            fieldName: 'OID'
                        });*/
                        top.jasMap.updateLayer('hca_pipeline',{
                            "show":true,
                            "where":"oid like'" + item.oid + "'"
                        });
                        
                        top.$('#mapIframe')[0].contentWindow.hcaMapApp.localPipelineName = item.pipelineName;
                        top.$('#mapIframe')[0].contentWindow.hcaMapApp.pipelineOid = item.oid;
                        //top.jasMap.createBufferDialog(item.oid);
                        top.$('#mapIframe')[0].contentWindow.hcaMapApp.isManage = true;
                        top.$('#mapIframe')[0].contentWindow.hcaMapApp.bufferValue=200;
                        top.jasMap.createBuffer();
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
        	if(!top.app.panelShowed){
        		top.app._goMap();
        	}
            top.jasMap.flashGraphic(item.oid, 'hca_pipeline', {
                deep: 2,
                fieldName: 'OID'
            });
        },
    }
};