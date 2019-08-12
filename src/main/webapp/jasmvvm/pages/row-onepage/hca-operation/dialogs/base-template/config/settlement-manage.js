var pageConfig = {
    privilegeCode: ['bt_add', 'bt_select', 'bt_update', 'bt_delete','bt_position'],
    searchPath: "/jdbc/commonData/hcaelementunit/getPage.do",
    deletePath: '/jdbc/commonData/hcabuildings/delete.do',
    detailPath: '/jdbc/commonData/hcaelementunit/getPage.do',
    savePath: '/jdbc/commonData/hcabuildings/save.do',
    updatePath: '/jdbc/commonData/hcabuildings/update.do',
    searchFields: [
        'buildingCode',
        'buildingType'
    ],
    tableFields: [
        'buildingCode',
        'buildingTypeName',
        'startMileage',
        'endMileage',
        'horizontalDistance',
        'verticalDistance',
        'pointx',
        'pointy',
        'buildingDistributionName',
        'households',
        'population',
        'address',
        'pressurePipeline',
        'remarks'
    ],
    addFields: [{
        title: '基本信息',
        fields: [
            'buildingCode',
            'buildingType',
            'startMileage',
            'endMileage',
            'horizontalDistance',
            'verticalDistance',
            'pointx',
            'pointy',
            'buildingDistribution',
            'households',
            'population',
            'address',
            'pressurePipeline',
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
            'buildingCode',
            'buildingTypeName',
            'startMileage',
            'endMileage',
            'horizontalDistance',
            'verticalDistance',
            'pointx',
            'pointy',
            'buildingDistributionName',
            'households',
            'population',
            'address',
            'pressurePipeline',
        ]
    }, {
        title: '其他信息',
        fields: [
            'remarks'
        ]
    }],
    fieldsConfig: {
        buildingCode: {
            name: '建(构)筑物编号',
            type: 'input',
            isRequired: true
        },
        buildingTypeName: {
            name: '建(构)筑物类型'
        },
        buildingType: {
            name: '建(构)筑物类型',
            type: 'select',
            domainName: 'building_type_domain',
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
            isRequired: true,
            max:999999.999,
            min: 0,
            precision:3
        },
        horizontalDistance: {
            name: '水平距离',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3
        },
        verticalDistance: {
            name: '垂直距离',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3
        },
        pointx: {
            name: '起始坐标x',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3
        },
        pointy: {
            name: '起始坐标y',
            type: 'number',
            max:999999.999,
            min: 0,
            precision:3
        },
        buildingDistributionName: {
            name: '建(构)筑物分布'
        },
        buildingDistribution: {
            name: '建(构)筑物分布',
            type: 'select',
            domainName: 'building_distribution_domain',
            isRequired: true
        },
        households: {
            name: '户数',
            type: 'number',
            max:999999,
            min: 0,
            precision:0
        },
        population: {
            name: '人口',
            type: 'number',
            max:999999,
            min: 0,
            precision:0
        },
        address: {
            name: '地址',
            type: 'input'
        },
        pressurePipeline: {
            name: '是否占压管道',
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
        },
        remarks: {
            name: "备注",
            type: "textarea"
        }
    },
    btncolwidth:200,
    rowBtns:[
        {
            name:'定位',
            icon: 'fa fa-info-circle pointer',
            method: 'locateSettlement'
        },
    ],
    methods:{
        locateSettlement: function(item){
            this.jasMap.flashGraphic(item.oid, 'hca_buildings',{
                deep:2
            });
            //this.jasMap.zoomAt(item.pointx, item.pointy, 15)
        },
    }
};