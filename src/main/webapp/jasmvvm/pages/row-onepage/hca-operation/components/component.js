/**
 * table of Contents 目录
 *
 * -- 通用业务组件
 * ---- jas-file-list 附件展示列表
 * ---- jas-file-upload 附件上传表单项
 *
 *
 * @GF created on 2019/7/4
 */








/** 通用业务组件--end ****************************************************************************************************************************************************************************************************************************************************************************************************/


Vue.component('jas-remote-select-form-item', {
    props: {
        params: {
            default: function () {
                return {}
            }
        },
        keyword: {
            default: 'ruleName'
        },
        prop: {},
        disabled: {
            default: false
        },
        isShowButton: {
            default: false
        },
        rules: {},
        label: {},
        searchurl: {},
        value: {}, // v-model
        vlabel: {},
        propconfig: {
            default: function () {
                return {
                    label: 'label',
                    value: 'value',
                }
            }
        }
    },
    data: function () {
        return {
            sloading: false,
            myoptions: [],
            cacheoption: [],
            selectObj: null,
        }
    },
    mounted: function () {
        if (this.value && this.vlabel) {
            var op = {};
            op[this.propconfig.value] = this.value;
            op[this.propconfig.label] = this.vlabel;
            this.myoptions = [op];
            console.log('0000000000000000000000000')
        }
    },
    watch: {
        vlabel: function () {
            if (this.value && this.vlabel) {
                var op = {};
                op[this.propconfig.value] = this.value;
                op[this.propconfig.label] = this.vlabel;
                this.myoptions = [op];
                console.log('0000000000000000000000000-----------')
            }
        },

    },
    computed: {
        _value: {
            get: function () {
                var that = this;
                return this.value;
            },
            set: function (newVal) {
                var that = this;
                this.cacheoption.forEach(function (item) {
                    if (item[that.propconfig.value] === newVal) {
                        that.selectObj = item;
                    }
                });
                this.$emit('input', newVal);
            }
        }
    },
    template: [
        '<el-form-item :rules="rules" :label="label" :prop="prop">',
        '	<div><el-select size="small" :disabled="disabled" v-model="_value" filterable remote reserve-keyword placeholder="请输入关键词" :remote-method="remoteMethod" @focus="remoteMethod" :loading="sloading">',
        '		<el-option v-for="item in myoptions" :key="item[propconfig.value]" :label="item[propconfig.label]" :value="item[propconfig.value]">',
        '     <slot v-bind:item="item"></slot>',
        '   </el-option>',
        '	</el-select>',
        ' <el-button style="position:absolute;top:4px;right:-30px;" @click="newRules" v-if="isShowButton" type="text" size="small">创建</el-button>',
        '</div></el-form-item>'
    ].join(''),
    methods: {
        newRules: function (query) {
            var that = this;
            var url = jasTools.base.rootPath +
                '/jasmvvm/pages/group-risk-test/base-template-new/dialogs/add-rule.html?pageCode=rule';

            top.jasTools.dialog.show({
                width: '900px',
                title: '新增',
                src: url,
                cbForClose: function (form) {
                    console.log(form)
                    // that.vlabel = form.ruleName;
                    if (form) {
                        that._value = form.oid;
                        var op = {};
                        op[that.propconfig.value] = form.oid;
                        op[that.propconfig.label] = form.ruleName;
                        that.myoptions = [op];
                    }
                    // that.$refs.table.refresh();
                }
            });
        },
        remoteMethod: function (query) {
            var that = this;
            if (query !== '') {
                if(typeof query !== 'string'){
                    query = ''
                }
                this.sloading = true;
                var obj = jasTools.base.extend({}, this.params);
                obj[this.keyword] = query;
                var url = jasTools.base.rootPath + (this.searchurl || "/jdbc/commonData/rule/getPage.do");
                jasTools.ajax.post(url, obj, function (data) {
                    that.sloading = false;
                    that.myoptions = data.rows;
                    that.cacheoption = that.cacheoption.concat(data.rows);
                });
            } else {
                this.myoptions = [];
            }
        },
    }
});


Vue.component('jas-table-for-list', {
    props: {
        dialogconfig: {
            default: function () {
                return {}
            }
        },
        privilegeCode: {
            type: [String, Array],
        },
        propconfig: {
            default: function () {
                return {}
            }
        },
        rowBtns: {
            type: Array, // [locate]
            default: function () {
                return []
            }
        },
        btncolwidth: {},
        form: {
            type: Object,
            required: true
        },
        fields: {
            type: Array,
            required: true
        },
        searchPath: {
            // type: String,
            required: true
        },
        upcallPath: {
            type: String,
        },
        isSearchBtn: { //是否带有搜索的 收缩按钮
            default: true,
        },
        isHideBtnCol: { //是否带有搜索的 收缩按钮
            default: false,
        },
        deletePath: {
            type: String,
            // required: true
        },
        detailUrl: {},
        addUrl: {},
        editUrl: {},
        templateCode: {},
        exportTemplateCode: {},
        className: {},
        importConfig: {},
        searchType: {
            default: 'post' //可以是get,post和postForm
        }
    },
    data: function () {
        return {
            prop: {
                oid: 'oid'
            },
            headStyle: {
                'background-color': '#f5f7fa ',
            },
            functionCode: '', //自定义配置表单需要
            _templateCode: '', //通用模板接口需要
            _exportTemplateCode: '', //通用模板接口需要
            _className: '', //通用权限接口需要
            _classNameQuery: '', //通用模板接口需要
            isApprove: '', //是否带有审核功能
            privilege: [], //权限数组 bt_add,bt_update,bt_delete,bt_select,bt_export,bt_import,bt_position
            tableData: [],
            currentPage: 1,
            loading: true,
            total: 0,
            pageSize: 10,
            oids: [],
            rows: [],
            fieldshowed: [],
            isClosed: false,
            _privilegeCode: '',
        }
    },
    computed: {
        reportRows: function () {
            var that = this;
            return this.rows.filter(function (row) {
                return !that.frozenBtn(row);
            });
        },
        approveRows: function () {
            var that = this;
            return this.rows.filter(function (row) {
                return (row.approve_status == '待审核' || row.approveStatus == 1);
            });
        },
    },
    template: [
        '<div  class="jas-flex-box is-vertical is-grown">',
        '<div style="padding: 15px 0;">',
        '	<el-button size="small" plain type="primary" icon="fa fa-plus" v-if="isHasPrivilege(' + "'bt_add'" + ')"  @click="add">增加</el-button>',
        '	<el-button size="small" plain type="primary" icon="fa fa-level-up" v-if="isApprove&&isHasPrivilege(' + "'bt_report'" + ')"  :disabled="reportRows.length==0" @click="upcall">上报</el-button>',
        '	<el-button size="small" plain type="primary" icon="fa fa-check" v-if="isApprove&&isHasPrivilege(' + "'bt_approve'" + ')" :disabled="approveRows.length==0" @click="approve">审核</el-button>',
        '   <slot name="btns"></slot>',
        '<jas-import-export-btns  @refreshtable="refresh" :is-import="isHasPrivilege(' + "'bt_import'" + ')" :is-export="isHasPrivilege(' + "'bt_export'" + ')" ',
        '		:form="form" :oids="oids" :import-config="importConfig" :template-code="_templateCode" :export-template-code="_exportTemplateCode" :function-code="functionCode" :class-name="_classNameQuery"></jas-import-export-btns>',

        '  <span class="fr">',
        '		<el-popover ref="popover4" placement="bottom" trigger="click">',
        '			<el-checkbox-group v-model="fieldshowed">',
        '				<div v-for="item in fields" :key="item.field" style="padding:2px 0;">',
        '					<el-checkbox  :label="item.name"></el-checkbox>',
        '				</div>',
        '			</el-checkbox-group>',
        '		</el-popover>',
        '		<el-tooltip class="item" content="字段显隐" placement="top">',
        '           <el-button size="small" icon="fa fa-cog" v-popover:popover4></el-button>',
        '		</el-tooltip>',
        '		<el-tooltip class="item" content="刷新" placement="top">',
        '          <el-button size="small" icon="el-icon-refresh" @click="refresh"></el-button>',
        '		</el-tooltip>',
        '		<el-tooltip v-show="isClosed" v-if="isSearchBtn" class="item" content="展开搜索" placement="top">',
        '	       <el-button size="small" icon="el-icon-arrow-down" @click="toggleSearch"></el-button>',
        '		</el-tooltip>',
        '		<el-tooltip v-show="!isClosed" v-if="isSearchBtn" class="item" content="收起搜索" placement="top">',
        '	       <el-button size="small" icon="el-icon-arrow-up" @click="toggleSearch"></el-button>',
        '		</el-tooltip>',
        '  </span>',
        '</div>',
        '<div class="is-grown">',
        '	<el-table ref="mytable" @selection-change="handleSelectionChange" @sort-change="sortChange" @row-dblclick="preview" @row-click="checkRow" v-loading="loading" height="100%" :data="tableData" border :header-cell-style="headStyle" style="width: 100%" stripe>',
        '    <el-table-column type="selection" width="55" align="center" fixed></el-table-column>',
        '		<el-table-column label="序号" type="index" align="center" width="50" fixed>',
        '		</el-table-column>',
        '   <template v-for="item,index in fields" v-if="fieldshowed.indexOf(item.name)>-1">',
        ' 		<el-table-column  v-if="item.tagTypeFn" :sortable="item.sortable?\'custom \':false" :key="item.oid" :fixed="index=== 0?true:false" :label="item.name" :prop="item.field" :formatter="item.formatter" min-width="130px" :width="item.width" show-overflow-tooltip align="center">',
        '       <template slot-scope="scope">',
        '         <el-tag size="small" :type="item.tagTypeFn(scope.row)" close-transition>{{scope.row[item.field]}}</el-tag>',
        '       </template>',
        ' 		</el-table-column>',
        ' 		<el-table-column v-else :sortable="item.sortable?\'custom \':false" :key="item.oid" :fixed="index=== 0?true:false" :label="item.name" :prop="item.field" :formatter="item.formatter" min-width="130px" :width="item.width" show-overflow-tooltip align="center">',
        ' 		</el-table-column>',
        '   </template>',
        '		<el-table-column label="操作" align="center" v-if="!isHideBtnCol" :width="btncolwidth" fixed="right">',
        '			<template slot-scope="scope">',
/*
         '				<el-button @click.stop="locate(scope.row)"  v-if="isHasPrivilege(' + "'bt_position'" + ')" type="text" size="mini">定位</el-button>',
*/
         '				<el-button @click.stop="preview(scope.row)"  v-if="isHasPrivilege(' + "'bt_select'" + ')" type="text" size="mini">查看</el-button>',
         '				<el-button @click.stop="edit(scope.row)"  :disabled="frozenBtn(scope.row)" v-if="isHasPrivilege(' + "'bt_update'" + ')"  type="text" size="mini">编辑</el-button>',
         '				<el-button @click.stop="deleteItem(scope.row)" :disabled="frozenBtn(scope.row)" v-if="isHasPrivilege(' + "'bt_delete'" + ')"   type="text" size="mini">删除</el-button>',
        '				    <el-button  v-for="item in rowBtns" :key="item.name" v-if="ckeckIfShow(scope.row,item)"  @click.stop="clickRowBtns(scope.row,item)" :type="item.type || \'text\'" size="mini">{{item.name}}</el-button>',
        '			</template>',
        '		</el-table-column>',
        '	</el-table>',
        '</div>',
        '<div style="padding: 15px 0 0;" class="clearfix">',
        '	<el-pagination class="fr" @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"',
        '		:page-sizes="[10, 20, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">',
        '	</el-pagination>',
        '</div>',
        '</div>',
    ].join(''),
    watch: {
        privilegeCode: function () {
            this._requestPrivilege(this._privilegeCode);
            // this.search();
        }
    },
    created: function () {
        var that = this
        var param = window.jasTools.base.getParamsInUrl(location.href);
        this.isApprove = param.isApprove;
        this._className = this.className || param.className;
        this._classNameQuery = this.classNameQuery || param.classNameQuery;
        this._templateCode = this.templateCode || param.templateCode;
        this._exportTemplateCode = this.exportTemplateCode || param.exportTemplateCode;
        this.functionCode = param.menuCode || param.functionCode;
        this._privilegeCode = this.privilegeCode || param.privilegeCode;
        this.fieldshowed = this.fields.filter(function (item) {
            return !item.tablehidden;
        }).map(function (item) {
            return item.name;
        });
        this.propconfig.oid && (this.prop.oid = this.propconfig.oid);
    },
    mounted: function () {

        this._requestPrivilege(this._privilegeCode);
        this.search();
    },
    methods: {
        ckeckIfShow: function (row, item) {
            var isShow = item.isShow ? item.isShow(row) : true;
            var isHasPrivilege = item.privilegeCode ? this.isHasPrivilege(item.privilegeCode) : true;
            return (isShow && isHasPrivilege);
        },
        clickRowBtns: function (row, item) {
            if (item.isDefaultMethod) { //'bt_position'、'bt_select'、'bt_update'、'bt_delete'
                var fnMap = {
                    'bt_position': 'locate',
                    'bt_select': 'preview',
                    'bt_update': 'edit',
                    'bt_delete': 'deleteItem',
                }
                if (item.privilegeCode && fnMap[item.privilegeCode]) {
                    this[fnMap[item.privilegeCode]](row);
                }
            } else {
                this.$emit('clickrowbtn', row, item.name)
            }
        },
        toggleSearch: function () {
            this.$parent.toggleSearch();
            this.isClosed = this.$parent.isClosed;
        },
        frozenBtn: function (row) {
            if (row.approveStatus > 0) {
                return true;
            }
            return false;
        },
        upcall: function () {

            var that = this;
            var oids = this.reportRows.map(function (item) {
                return item[that.prop.oid];
            });
            if (oids.length === 0) return;
            var url = jasTools.base.rootPath + '/daq/dataApprove/save.do';
            jasTools.ajax.post(url, {
                businessOid: oids,
                approveStatus: 1, //status 2 通过 -1 驳回
                className: this._className,
                functionCode: this.functionCode,
            }, function (data) {
                top.Vue.prototype.$message({
                    type: 'success',
                    message: '上报成功'
                });
                that.refresh();
            });
        },
        approve: function () {
            var that = this;
            var oids = this.approveRows.map(function (item) {
                return item[that.prop.oid];
            });
            if (oids.length === 0) {
                return;
            } else if (oids.length === 1) {

                var src = jasTools.base.setParamsToUrl(this.detailUrl, {
                    approveType: 2,
                    className: this._className,
                    menuCode: this.functionCode || '',
                });
                var url = jasTools.base.setParamsToUrl(src, this.approveRows[0]);
                top.jasTools.dialog.show({
                    width: '60%',
                    height: '80%',
                    title: '审核',
                    src: url,
                    cbForClose: function (param) {
                        if (param == 'success') {
                            that.refresh();
                        }
                    }
                });
            } else {
                var src = jasTools.base.setParamsToUrl('./pages/template/dialogs/approveTemplate.html', {
                    approveType: 2,
                    className: this._className,
                    menuCode: this.functionCode || '',
                });
                var url = jasTools.base.setParamsToUrl(src, {
                    oids: oids.join(',')
                });
                top.jasTools.dialog.show({
                    width: '600px',
                    height: '400px',
                    title: '批量审核',
                    src: url,
                    cbForClose: function (param) {
                        if (param == 'success') {
                            that.refresh();
                        }
                    }
                });
            }
        },
        handleSelectionChange: function (val) {
            var that = this;
            this.oids = val.map(function (item) {
                return item[that.prop.oid];
            });
            this.$emit('selectchanged', val);
            this.rows = val;
        },
        sortChange: function (param) {
            if (param.prop) {
                var type = param.order == 'descending' ? 'desc' : 'asc';
                this.form.orderBy = param.prop + ' ' + type;
            } else {
                this.form.orderBy = null;
            }
            this.search();
        },
        locate: function (item) {
            this.$emit('locate', item)
        },
        isHasPrivilege: function (sName) {
            //console.log(sName);
            if (this._privilegeCode && this.privilege.indexOf(sName) === -1) {
                return false;
            }
            return true;
        },
        _requestPrivilege: function (privilegeCode) {
            var that = this;
            if (!privilegeCode) return;
            if ((typeof privilegeCode) === 'string') {
                var url = jasTools.base.rootPath + "/jasframework/privilege/privilege/getFunctionConfig.do";
                jasTools.ajax.get(url, {
                    privilegeCode: privilegeCode, //菜单权限编号
                    appId: "402894a152681ba30152681e8b320003" //应用id，值默认
                }, function (data) {
                    that.privilege = data.rows.map(function (item) {
                        return item.functionType;
                    });
                });
            } else {
                that.privilege = privilegeCode;
            }
        },
        search: function () {
            this._requestTable();
        },
        refresh: function () {
            this.search();
        },
        add: function () {
            var that = this;
            if (!this.addUrl) return;
            top.jasTools.dialog.show({
                width: this.dialogconfig.addWidth || '60%',
                height: this.dialogconfig.addHeight || '80%',
                title: '增加',
                src: this.addUrl,
                cbForClose: function () {
                    that.refresh()
                }
            });
        },
        checkRow: function (row) {
            this.$refs['mytable'].toggleRowSelection(row)
        },
        preview: function (row) {
            var that = this;

            if (!this.detailUrl || !this.isHasPrivilege('bt_select')) return;
            var url = this.detailUrl;
            if (this.isApprove) {
                url = jasTools.base.setParamsToUrl(this.detailUrl, {
                    approveType: 1
                });
            }
            var paramObj = {
                oid: row[that.prop.oid]
            };
            url = jasTools.base.setParamsToUrl(url, paramObj);
            top.jasTools.dialog.show({
                width: this.dialogconfig.detailWidth || '60%',
                height: this.dialogconfig.detailHeight || '80%',
                title: '查看',
                src: url,
            });
        },
        _requestTable: function (str, cb) {
            var that = this;
            that.loading = true;
            var obj = jasTools.base.extend({}, {
                pageNo: this.currentPage,
                pageSize: this.pageSize,
            }, this.form);
            if (typeof this.searchPath != 'string') {
                that.loading = false;
                that.isNotFirst = true;
                that.tableData = this.searchPath;
                that.total = this.searchPath.length;
                return;
            }
            var url = jasTools.base.rootPath + this.searchPath;
            jasTools.ajax[this.searchType](url, obj, function (data) {
                if (that.isNotFirst) {
                    setTimeout(function () {
                        that.loading = false;
                    }, 100);
                } else {
                    that.loading = false;
                    that.isNotFirst = true;
                }
                that.tableData = data.rows;
                that.total = data.total;
            });
        },
        edit: function (row) {
            var that = this;
            var url = jasTools.base.setParamsToUrl(this.addUrl, row)
            top.jasTools.dialog.show({
                width: this.dialogconfig.addWidth || '60%',
                height: this.dialogconfig.addHeight || '80%',
                title: '修改',
                src: url,
                cbForClose: function () {
                    that.refresh()
                }
            });
        },
        deleteItem: function (row) {
            var that = this;
            top.Vue.prototype.$confirm('您确定要删除本条数据吗？', '提示', {
                type: 'warning',
                callback: function (action) {
                    if (action === 'confirm') {
                        that._deleteItem(row);
                    }
                }
            });
        },
        _deleteItem: function (row) {
            var that = this;
            if (this.deletePath) {
                var url = jasTools.base.rootPath + this.deletePath;
                jasTools.ajax.post(url, {
                    oid: row[that.prop.oid]
                }, function (data) {
                    top.Vue.prototype.$message({
                        type: 'success',
                        message: '删除成功'
                    });
                    that.refresh();
                });
            } else {
                this.$emit('deleterow', row);
            }
        },
        handleSizeChange: function (val) {
            this.pageSize = val;
            this.search();
        },
        handleCurrentChange: function (val) {
            this.currentPage = val;
            this.search();
        }
    },

});