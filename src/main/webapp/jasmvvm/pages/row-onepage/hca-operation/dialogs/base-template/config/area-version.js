var pageConfig = {
    privilegeCode: ['bt_add', 'bt_select', 'bt_update', 'bt_delete'],
    searchPath: "/jdbc/commonData/hcaversion/getPage.do",
    deletePath: '/jdbc/commonData/hcaversion/delete.do',
    detailPath: '/jdbc/commonData/hcaversion/getPage.do',
    savePath: '/jdbc/commonData/hcaversion/save.do',
    updatePath: '/jdbc/commonData/hcaversion/update.do',
    searchFields: [
        'versionName',
        'versionCode'
    ],
    tableFields: [
        'versionName',
        'versionCode',
        'modifyUserName',
        'modifyDatetime',
        'hasUsed',
        'remarks'
    ],
    addFields: [{
        title: '基本信息',
        fields: [
            'versionName',
            'versionCode',
            'hasUsed',
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
            'versionName',
            'versionCode',
            'modifyUserName',
            'modifyDatetime',
            'hasUsed',
        ]
    }, {
        title: '其他信息',
        fields: [
            'remarks'
        ]
    }],
    fieldsConfig: {
        versionName: {
            name: '版本名称',
            type: 'input',
            isRequired: true
        },
        versionCode: {
            name: '版本编号',
            type: 'input',
            isRequired: true
        },
        remarks: {
            name: "备注",
            type: "textarea"
        },
        modifyUserName: {
            name: "版本修改人"
        },
        modifyDatetime: {
            name: "版本修改时间"
        },
        hasUsed: {
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
        }
    },
    btncolwidth:360,
    rowBtns:[
        {
            name: '导入',
            method: 'importFile'
        },
        {
            name: '启用',
            method: 'enableUse'
        },
        {
            name:'生成报告',
            method: 'previewFile'
        },
        {
            name:'地区列表',
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
            row.hasUsed = 1;
            url = jasTools.base.rootPath + "/hcaversion/updateUsed.do";
            jasTools.ajax.post(url, row, function (data) {
                if(data==1){
                    window.Vue.prototype.$message({
                        type: 'success',
                        message: '启用成功'
                    });
                    that.searchList();
                }else{
                    window.Vue.prototype.$message({
                        type: 'waring',
                        message: '启用失败'
                    });
                }
            });
            that.jasMap.layerVisibleSwitch('pd_arearank',false);
            that.jasMap.layerVisibleSwitch('pd_zonerankcell',false);
            this.jasMap.zoomAt('110.3530585' ,'34.540260695' ,15);
            if(that.forBusiness=="1"){
                setTimeout(function(){
                    that.jasMap.layerVisibleSwitch('pd_zonerankcell',true);
                }, 1000);
            }else{
                setTimeout(function(){
                    that.jasMap.layerVisibleSwitch('pd_arearank',true);
                }, 1000);
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
/*        areaList: function (row) {
            jasTools.mask.show({
                title: '地区列表',
                src: jasTools.base.rootPath + '/jasmvvm/pages/row-onepage/hca-operation/dialogs/base-template/base-template.html?pageCode=area-list&forBusiness=0',
                height: '70%',
                width: '80%',
                left:'10%',
                bottom:'5%',
            })
        },*/
        areaList: function (row) {
            jasTools.mask.show({
                title: '地区列表',
                src: jasTools.base.rootPath + '/jasmvvm/pages/row-onepage/hca-operation/dialogs/base-template/base-template.html?pageCode=area-list&forBusiness=0',
                height: '80%',
                width: '80%',
                title: '地区列表',
                cbForClose: function (param) {
                    that.$refs.table.refresh();
                }
            })
        },
    }
};