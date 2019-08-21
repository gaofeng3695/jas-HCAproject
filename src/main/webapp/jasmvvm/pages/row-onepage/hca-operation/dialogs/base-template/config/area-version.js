var pageConfig = {
    privilegeCode: ['bt_add', 'bt_select', 'bt_update', 'bt_delete'],
    searchPath: "/jdbc/commonData/hcaversion/getPage.do",
    deletePath: '/jdbc/commonData/hcaversion/delete.do',
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
            name: '评价管线',
            optionUrl: '/jdbc/commonData/hcapipeline/getPage.do',
            isRequired: true,
//            disabled: true,
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
            var that = this;
            var layerId = "";
            //this.jasMap.zoomAt('110.3530585' ,'34.540260695' ,15);
            if(that.forBusiness=="1"){
            	layerId = "hca_high_impact_area";
            }else{
            	layerId = "hca_area";
            }
            that.jasMap.updateLayer(layerId, {
            	show: true,
            	where: "VERSION_OID = '" + row.oid +"'"
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