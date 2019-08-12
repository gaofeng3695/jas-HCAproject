var pageConfig = {
    privilegeCode: ['bt_add', 'bt_select', 'bt_update', 'bt_delete'],
    searchPath: "/jdbc/commonData/hcaarea/getPage.do",
    deletePath: '/jdbc/commonData/hcaarea/delete.do',
    detailPath: '/jdbc/commonData/hcaarea/getPage.do',
    savePath: '/jdbc/commonData/hcaarea/save.do',
    updatePath: '/jdbc/commonData/hcaarea/update.do',
    searchFields: [
        'areaCode',
    ],
    tableFields: [
        'areaCode',
        'regionLevelName',
        'startMileage',
        'endMileage',
        'remarks'
    ],
    addFields: [{
        title: '基本信息',
        fields: [
            'areaCode',
            'regionLevel',
            'startMileage',
            'endMileage',
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
            'areaCode',
            'regionLevelName',
            'startMileage',
            'endMileage',
        ]
    }, {
        title: '其他信息',
        fields: [
            'remarks'
        ]
    }],
    fieldsConfig: {
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
            if(this.forBusiness == "0"){
                if(item.startMileage == 0){
                    this.jasMap.flashGraphic("e1885098-38d7-470d-a4d2-42c12ac5ce1d", this.layerId ,{
                        deep:2
                    });

                }
                if(item.startMileage == 0.165){
                    this.jasMap.flashGraphic("e19d4a5d-7381-48cd-a0be-4a626fa52250", this.layerId ,{
                        deep:2
                    });

                }
                if(item.startMileage == 3.616){
                    this.jasMap.flashGraphic("c51de314-34fe-4f4e-9f50-34317de808c9", this.layerId ,{
                        deep:2
                    });

                }
                if(item.startMileage == 4.471){
                    this.jasMap.flashGraphic("c4c32e7c-4a33-4311-a090-5ab7aa51d3ac", this.layerId ,{
                        deep:2
                    });

                }
            }else{
                this.jasMap.flashGraphic(item.oid, this.layerId ,{
                    deep:2
                });
            }
            /* this.jasMap.flashGraphic(item.oid, this.layerId ,{
                deep:2
            }); */
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