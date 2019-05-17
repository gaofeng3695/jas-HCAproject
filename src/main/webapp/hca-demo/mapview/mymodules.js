/**
 * Created by jas on 2019/1/3.
 */
var AreaRankManager = function(options){
    var _self = this;
    var defaults = {
        layerId:"pd_arearank",
        title:"地区等级信息列表",
        width: 300,
        height: 500,
        top:80 ,
        right:50,
        columns:[{
            "field":"index","title":"序号","align":'center'
        },{
            "field":"name","title":"名称","width":50,"align":'center'
        },{
            "field":"rank","title":"等级","width":30,"align":'center'
        },{
            "field":"startMileage","title":"起始里程","width":50,"align":'center',editor:{type:'numberbox',options:{precision:3}}
        },{
            "field":"endMileage","title":"结束里程","width":50,"align":'center',editor:{type:'numberbox',options:{precision:3}}
        },{
            "field":"oid","title":"要素编号","hidden":true
        }]
    };
    var params = null ;
    _self.mapApi = null; //
    _self.index = null; //
    _self.state = "closed"; //open/closed /hidden/ show
    _self.container = null; //容器dom
    _self.dom = null; //模块的dom节点
    _self.moduleClass = "area-rank-manager"; //模块类

    var tableToolsClass =  _self.moduleClass + "-tools";

    _self.$table = null;
    _self.template =
        "<div class='" + _self.moduleClass + "'>" +
            "<table style='height: 100%;width:100%;'>" +
                "<tr style='height: 10%'>" +
                    "<td align='center'>" +
                        "<input class='accept module-form-btn' type='button' value='更新里程'>" +
                        // "<input class='cbt module-form-btn' type='button' value='重置'>" +
                        // "<input class='drawbt module-form-btn' type='button' value='标绘'>" +
                        // "<input class='qbt module-form-btn' type='button' value='查询' >" +
                    "</td>" +
                "</tr>" +
                "<tr style='height: 90%'>" +
                    "<td><table id='area-rank-result' style='width: 100%;height:100%;'></table></td>" +
                "</tr>" +

            "</table>" +
        "</div>";
    _self.doClear = function () {
        _self.$table.datagrid({
            data:[]
        });
    };
    _self.currentEditingIndex = undefined ;
    _self.endingEditColumn = function(){
        if ( _self.currentEditingIndex === undefined){
            return true
        }
        if (_self.$table.datagrid('validateRow',  _self.currentEditingIndex)){
            _self.$table.datagrid('endEdit',  _self.currentEditingIndex);
            _self.currentEditingIndex = undefined;
            return true;
        } else {
            return false;
        }
    };
    _self.datagrid = function($dom){
        var $dom = $("#area-rank-result", _self.dom);
        var columns = params.columns ? params.columns:[];
        _self.$table = $dom.datagrid({
            fitColumns: true ,
            pagination: false ,
            singleSelect: true ,
            onSelect:function(index, row){
                var id = row.oid ;
                var layerId = params.layerId ;
                if( id && layerId ){
                    _self.mapApi.flashGraphic(id,layerId ,{
                        deep:2
                    });
                }
            },
            onClickCell:function(index, field){
                if (_self.currentEditingIndex !== index){
                    if (_self.endingEditColumn()){
                        _self.$table.datagrid('selectRow', index) .datagrid('beginEdit', index);
                        var ed = _self.$table.datagrid('getEditor', { index:index,field:field });
                        if (ed){
                            ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                        }
                        _self.currentEditingIndex = index;
                    } else {
                        setTimeout(function(){
                            _self.$table.datagrid('selectRow', _self.currentEditingIndex);
                        },0);
                    }
                }
            },
            columns: [ columns ]
        });
    };
    _self.refresh = function(data){
        _self.endingEditColumn();
        if(Array.isArray(data)){
            _self.$table.datagrid({"data":data});
        }else{
            common.simpleAjaxLoader({
                url:"/HCAProject/zone/" + areaRankLayerId+ "/query.do",
                data:"{}",
                method:"post",
                async:false,
                contentType:"application/json",
                onSuccess:function(re){
                    var result = JSON.parse(re);
                    var collection =  JSON.parse(result.data.features)  ;
                    var features = collection.features;
                    var data = [];
                    if(Array.isArray(features)){
                        for(var i = 0 ; i < features.length ; i++){
                            var properties = features[i].properties ;
                            properties.startMileage = properties.start_mileage ;
                            properties.endMileage = properties.end_mileage ;
                            properties.index = i + 1;
                            data.push(properties);
                        }
                    }
                    _self.$table.datagrid({"data":data});
                }
            });
        }

    };
    _self.startup = function () {
        var event = _self.mapApi.Events.ModuleStartupEvent;
        _self.mapApi.publish(event, { id: _self.id });
        _self.datagrid();
        _self.doClear();
        $(".accept",_self.dom).click( _self.resetMileage);
    };
    _self.domCreate = function () {
        params = $.extend(defaults ,options);
        if(_self.template .indexOf(".html") > 0){
            $(_self.template).load( function (e) {
                var initEvent = _self.mapApi.Events.ModuleInitEvent;
                _self.mapApi.publish(initEvent , { target: _self });
            });
        }else{
            $(_self.container).append(_self.template);
        }
        _self.dom = $("." + _self.moduleClass , _self.container)[0];
        var options = {
            title: params.title,
            width: params.width,
            height: params.height,
            top: params.top,
            left: params.left,
            right: params.right,
            bottom: params.bottom,
            closed: true,
            constrain:true,
            modal: false,
            onOpen: function () {
                _self.refreshState("open");
            },
            onClose: function () {
                _self.refreshState("closed");
            }
        };
        _self.mapApi.moduleDialog(options, $(_self.dom) ,_self.container);
    };
    _self.show = function (flg) {
        //true:show , false:hidden
        _self.open(true);
    };
    _self.open = function (flg) {
        if (flg === false) {
            $(_self.dom).dialog('closed');
        } else {
            $(_self.dom).dialog('open');
        }
    };
    _self.refreshState = function (state) {
        _self.state = state;
        _self.mapApi.publish(_self.mapApi.Events.ModuleStateChanged, {
            "module": _self
        });
    };
    _self.destroy = function () {
        //销毁
        //document.removeChild(_class.dom);//
    };
    _self.resetMileage = function(){
        var start_ed = _self.$table.datagrid('getEditor', { index:_self.currentEditingIndex,field:"startMileage" });
        var end_ed = _self.$table.datagrid('getEditor', { index:_self.currentEditingIndex,field:"endMileage" });
        var rowsData = _self.$table.datagrid('getData').rows;
        var row = rowsData[_self.currentEditingIndex];
        var preRow = null;
        if(_self.currentEditingIndex > 0){
            preRow =  _self.$table.datagrid('getData').rows[_self.currentEditingIndex - 1];
        }
        var sufRow = null;
        if(_self.currentEditingIndex < rowsData.length - 1){
            sufRow =  _self.$table.datagrid('getData').rows[_self.currentEditingIndex + 1];
        }
        var param = {
            "sourceName":"pd_arearank",
            "pipeSourceName":"pd_pipesegment",
            "projectOid":"",
            "bufferDistance":200,
            "pipeKeyValue":"fa692d8c-6dfe-41d6-a8e1-6303a5ebfae5",
            "oldStartMileageValue":parseFloat(start_ed.oldHtml),
            "oldEndMileageValue":parseFloat(end_ed.oldHtml),
            "newStartMileageValue":$(start_ed.target).val(),
            "newEndMileageValue":$(end_ed.target).val(),
            "preStartMileageValue":preRow ? preRow["start_mileage"]:-1,
            "sufEndMileageValue":sufRow ? sufRow["end_mileage"]:-1,
        };
        $.ajax({
            url:"HCAProject/zone/mileage/reset.do",
            data:JSON.stringify(param),
            type:"post",
            contentType:'application/json',
            dataType:'json',
            success:function(e){
                //
                _self.mapApi.updateLayer(param["sourceName"]);
                _self.refresh();

                $.messager.show({
                    title:'提示',
                    msg:e.msg,
                    timeout: 3000,
                    showType:'slide'
                });
            },
            error:function(e){
                alert("error");
            }
        })
    }
};

var HcaDataManager = function(options){
    var _self = this;
    var defaults = {
        layerId:"pd_zonerankcell",
        title:"高后果区评价列表",
        width: 300,
        height: 500,
        top:80 ,
        right:50,
        columns:[{
            "field":"index","title":"序号","align":'center'
        },{
            "field":"name","title":"名称","width":50,"align":'center'
        },{
            "field":"rank","title":"等级","width":30,"align":'center'
        },{
            "field":"startMileage","title":"起始里程","width":50,"align":'center'
        },{
            "field":"endMileage","title":"结束里程","width":50,"align":'center'
        },{
            "field":"oid","title":"要素编号","hidden":true
        }]
    };
    var params = null ;
    _self.mapApi = null; //
    _self.index = null; //
    _self.state = "closed"; //open/closed /hidden/ show
    _self.container = null; //容器dom
    _self.dom = null; //模块的dom节点
    _self.moduleClass = "hca-data-manager"; //模块类

    _self.$table = null;
    _self.template =
        "<div class='" + _self.moduleClass + "'>" +
            "<table style='height: 100%;width:100%;'>" +
                "<tr style='height: 90%'>" +
                    "<td><table id='hca-data-result' style='width: 100%;height:100%;'></table></td>" +
                "</tr>" +
                "<tr style='height: 10%'>" +

                "</tr>" +
            "</table>" +
        "</div>";
    //
    _self.doClear = function () {
        _self.$table.datagrid({
            data:[]
        });
    };
    _self.currentEditingIndex = undefined ;
    _self.endingEditColumn = function(){
        if ( _self.currentEditingIndex === undefined){return true}
        if (_self.$table.datagrid('validateRow',  _self.currentEditingIndex)){
            _self.$table.datagrid('endEdit',  _self.currentEditingIndex);
            _self.currentEditingIndex = undefined;
            return true;
        } else {
            return false;
        }
    };
    _self.datagrid = function(){
        var $dom = $("#hca-data-result", _self.dom);
        var columns = params.columns ? params.columns:[];
        _self.$table = $dom.datagrid({
            fitColumns: true ,
            pagination: false ,
            singleSelect: true ,
            onSelect:function(index, row){
                var id = row.oid ;
                var layerId = params.layerId ;
                if( id && layerId ){
                    _self.mapApi.flashGraphic(id,layerId ,{
                        deep:2
                    });
                }
            },
            onClickCell:function(index, field){

            },
            columns: [ columns ]
        });
    };
    _self.refresh = function(data){
        _self.$table.datagrid({"data":data});
    };
    _self.startup = function () {
        var event = _self.mapApi.Events.ModuleStartupEvent;
        _self.mapApi.publish(event, { id: _self.id });
        _self.datagrid();
        _self.doClear();
    };
    _self.domCreate = function () {
        params = $.extend(defaults ,options);
        if(_self.template .indexOf(".html") > 0){
            $(_self.template).load( function (e) {
                var initEvent = _self.mapApi.Events.ModuleInitEvent;
                _self.mapApi.publish(initEvent , { target: _self });
            });
        }else{
            $(_self.container).append(_self.template);
        }
        _self.dom = $("." + _self.moduleClass , _self.container)[0];
        //
        var options = {
            title: params.title,
            width: params.width,
            height: params.height,
            top: params.top,
            left: params.left,
            right: params.right,
            bottom: params.bottom,
            closed: true,
            constrain:true,
            modal: false,
            onOpen: function () {
                _self.refreshState("open");
            },
            onClose: function () {
                _self.refreshState("closed");
            }
        };
        _self.mapApi.moduleDialog(options, $(_self.dom) ,_self.container);
    };
    _self.show = function (flg) {
        //true:show , false:hidden
        _self.open(true);
    };
    _self.open = function (flg) {
        if (flg === false) {
            $(_self.dom).dialog('closed');
        } else {
            $(_self.dom).dialog('open');
        }
    };
    _self.refreshState = function (state) {
        _self.state = state;
        _self.mapApi.publish(_self.mapApi.Events.ModuleStateChanged, {
            "module": _self
        });
    };
    _self.destroy = function () {
        //销毁
        //document.removeChild(_class.dom);//
    };

};

var HcaEditorBox = function(options) {
    var _self = this;
    var defaults = {
        sources: [{
            text: "居民区",
            value: "pd_settlement",
            fields: [{
                "title": "OID", "field": "oid"
            }, {
                "title": "名称", "field": "name"
            }, {
                "title": "编码", "field": "code"
            }, {
                "title": "户数", "field": "households"
            }, {
                "title": "人口", "field": "population"
            },{
                "title": "起始里程", "field": "start_mileage"
            },{
                "title": "结束里程", "field": "end_mileage"
            },{
                "title": "水平距离", "field": "horizontal_distance"
            },{
                "title": "垂直距离", "field": "vertical_distance"
            },{
                "title": "开始坐标x", "field": "start_x"
            },{
                "title": "开始坐标y", "field": "start_y"
            },{
                "title": "开始坐标y", "field": "start_y"
            },{
                "title": "要素类型", "field": "feature_type"
            },{
                "title": "建筑分布", "field": "building_type"
            },{
                "title": "备注", "field": "description"
            }]
        }, {
            text: "居民区2",
            value: "pd_settlement2",
            fields: [{
                "title": "OID", "field": "oid"
            }, {
                "title": "名称", "field": "name"
            }, {
                "title": "编码", "field": "code"
            }, {
                "title": "描述", "field": "description"
            }]
        }],
        title: "居民地编辑",
        width: 300,
        height: 500,
        top: 80,
        left: 50
    };
    var params = null;
    _self.mapApi = null; //
    _self.index = null; //
    _self.state = "closed"; //open/closed /hidden/ show
    _self.container = null; //容器dom
    _self.dom = null; //模块的dom节点
    _self.moduleClass = "hca-data-editor"; //模块类
    _self.template =
        "<div class='" + _self.moduleClass + "'>" +
        "<div><label>选择数据源:</label></div>" +
        "<div style='width:100%;height:30px;padding: 5px;'>" +
        "<select class='sources' style='padding:2px;' ></select>" +
        "<input class='refresh' type='button' value='刷新'>" +
        "</div>" +
        "<div><label>数据编辑:</label></div>" +
        "<div style='width:100%;height:30px;padding: 5px;'>" +
        "<input class='draw' type='button' value='绘制'>" +
        "<input class='edit' type='button' value='编辑'>" +
        "<input class='pan' type='button' value='导航'>" +
        "<input class='del' type='button' value='删除'>" +
        "</div>" +
        "<div><label>数据操作:</label></div>" +
        "<div style='width:100%;height:30px;padding: 5px;'>" +
        "<input class='cancel' type='button' value='撤销'>" +
        "<input class='save-edit' type='button' value='保存编辑'>" +
        "<input class='save-all' type='button' value='保存所有'>" +
        "</div>" +
        "<div><label>属性数据:</label></div>" +
        "<div class='attrs'>" +
        "<table class='grid' style='width: 100%;'></table>" +
        "</div>" +
        "</div>";
    //
    _self.sourceFieldsData = {};
    _self.currentSource = null;
    _self.attrDataGrid = null;
    _self.changedFeatures = {};
    //
    _self.doClear = function () {

    };
    _self.refresh = function (data) {

    };
    _self.startup = function () {
        var event = _self.mapApi.Events.ModuleStartupEvent;
        _self.mapApi.publish(event, {id: _self.id});
        _self.addListeners();
        _self.addFormData();
        $(".sources", _self.dom).change();
    };
    _self.domCreate = function () {
        params = $.extend(defaults, options);
        if (_self.template.indexOf(".html") > 0) {
            $(_self.template).load(function (e) {
                var initEvent = _self.mapApi.Events.ModuleInitEvent;
                _self.mapApi.publish(initEvent, {target: _self});
            });
        } else {
            $(_self.container).append(_self.template);
        }
        _self.dom = $("." + _self.moduleClass, _self.container)[0];
        var options = {
            title: params.title,
            width: params.width,
            height: params.height,
            top: params.top,
            left: params.left,
            right: params.right,
            bottom: params.bottom,
            closed: true,
            constrain: true,
            modal: false,
            onOpen: function () {
                _self.refreshState("open");
            },
            onClose: function () {
                _self.refreshState("closed");
            }
        };
        _self.mapApi.moduleDialog(options, $(_self.dom), _self.container);

    };
    _self.show = function (flg) {
        //true:show , false:hidden
        _self.open(true);
    };
    _self.open = function (flg) {
        if (flg === false) {
            $(_self.dom).dialog('closed');
        } else {
            $(_self.dom).dialog('open');
        }
    };
    _self.refreshState = function (state) {
        _self.state = state;
        _self.mapApi.publish(_self.mapApi.Events.ModuleStateChanged, {
            "module": _self
        });
    };
    _self.destroy = function () {
        //销毁
        //document.removeChild(_class.dom);//
    };
    _self.addListeners = function () {
        $(".draw", _self.dom).click(function () {
            _self.mapApi.drawPolygon({
                drawLayerId: _self.currentSource,
                onDrawEnd:function(evt){
                    var feature = evt.feature ;
                    var fId = feature.getId();
                    _self.changedFeatures[fId] = feature;
                }
            })
        });
        $(".edit", _self.dom).click(function () {
            _self.mapApi.editGraphic(_self.currentSource, {
                onSelected: function (feature ,attr) {
                    var fId = feature.getId();
                    _self.changedFeatures[fId] = feature;
                    _self.refreshAttrTable(feature,attr);
                }
            });
        });
        $(".pan", _self.dom).click(function () {
            _self.mapApi.startPanMode();
        });
        $(".save-all", _self.dom).click(function () {
            var source = _self.mapApi.getLayerById(_self.currentSource).getSource();
            var features = source.getFeatures();
            saveSettlementData(features);
        });
        $(".save-edit", _self.dom).click(function () {
            var module = _self.mapApi.getModuleById("hcaEditorBox");
            var features = module.getFeatures();
            saveSettlementData(features);
        });
        $(".cancel", _self.dom).click(function () {
            alert("开发中");
        });
        $(".refresh", _self.dom).click(function () {
            _self.mapApi.refreshLayerById(_self.currentSource);
        });
        $(".del", _self.dom).click( function () {
            var options = {
                onDelete: function(feature){
                    if(feature){
                        var oid = feature.get("oid") ;
                        var name = feature.get("name") ;

                        var msg = "确定要删除要素" + name + " ？";
                        $.messager.confirm("确认", msg , function (r) {
                            if (r) {
                                var oids = [];
                                oids.push(oid);
                                common.simpleAjaxLoader({
                                    url:"/HCAProject/jasgis/" + _self.currentSource + "/delete.do",
                                    data:JSON.stringify(oids),
                                    onSuccess:function(re){
                                        var result = JSON.parse(re);
                                        var count =  result.data;
                                        $.messager.show({
                                            title:'提示',
                                            msg:'成功删除' + count + "个居民区" ,
                                            timeout: 3000,
                                            showType:'slide'
                                        });
                                    }
                                })
                            }
                        });

                    }
                }
            };
            _self.mapApi.deleteGraphic(_self.currentSource ,options);
        });
        $(".sources", _self.dom).change(function (e) {
            var sourceName = $(e.target).val();
            _self.initAttrTable(sourceName);
        });
    };
    _self.getFeatures = function(){
        var result = [];
        for(var key in _self.changedFeatures){
            var feature = _self.changedFeatures[key] ;
            result.push(feature);
        }
        return result;
    };
    _self.addFormData = function () {
        var $selector = $(".sources", _self.dom);
        for (var i = 0; i < params.sources.length; i++) {
            var source = params.sources[i];
            var option = "<option value='" + source.value + "'>" + source.text + "</option>";
            $selector.append(option);
            //
            var fields = source.fields;
            _self.sourceFieldsData[source.value] = fields;
        }
    };
    _self.initAttrTable = function (sourceName) {
        _self.currentSource = sourceName;
        var fields = _self.sourceFieldsData[sourceName];
        var table = _self.attrDataGrid = $("table.grid", _self.dom);
        $("input.attr-ipt" ,table).change();
        table.empty();

        table.append("<tbody></tbody>");

        var tbody = $("tbody", table);

        for (var i = 0; i < fields.length; i++) {
            if(i % 2 === 0 ){
                tbody.append("<tr></tr>") ;
            }
            var tr = $("tr",tbody).last();
            var item = fields[i];
            var td_title = "<td class='td-title'><label>" + item.title + "</label></td>";
            var td_ipt = "<td><input class='attr-ipt' type='text' data-field='" + item.field +"'></td>";
            tr.append(td_title);
            tr.append(td_ipt);

        }
    };
    _self.refreshAttrTable = function ( feature ,attr) {
        var tbody = $("tbody", _self.attrDataGrid );
        var fields = _self.sourceFieldsData[_self.currentSource];

        $("input.attr-ipt" ,tbody).change();

        if(JSON.stringify(attr) !== "{}"){
            for(var i = 0 ; i < fields.length ; i++ ){
                var field = fields[i]['field'];
                var value = attr[field] ? attr[field]:'';
                $("input.attr-ipt[data-field='" + field+"']",tbody).val(value);
            }
        }else{
            for(var i = 0 ; i < fields.length ; i++ ){
                $("input.attr-ipt",tbody).val("");
            }
        }
        //
        $("input.attr-ipt" ,tbody).on("change",function(e){
            var ipt = $(e.target);
            var field = ipt.attr("data-field");
            var value = ipt.val();            //设置要素的属性
            feature.set(field,value);
        });

        if(attr && !attr["oid"] ){
            var uuid = _self.mapApi.commonUtil.uuid();
            $("input.attr-ipt[data-field='oid']" ,tbody).val(uuid).change();
        }
    };

    var saveSettlementData = function( features){
        // 保存全部
        //var module = _self.mapApi.getModuleById("hcaEditorBox");
        //var features = module.getFeatures();

        // 保存编辑
        // var source = _self.mapApi.getLayerById(settlementLayerId).getSource();
        // var features = source.getFeatures();

        var size = features.length ;
        if(size === 0){
            $.messager.show({
                title:'提示',
                msg:'没有需要保存的要素' ,
                timeout: 3000,
                showType:'slide'
            });
            return
        }
        //
        var geojson = _self.mapApi.commonUtil.toGeoJson(features);//先只处理GeoJSON格式数据
        var geomObj = JSON.parse(geojson) ;
        geomObj.pipelineKeyValue = "fa692d8c-6dfe-41d6-a8e1-6303a5ebfae5";
        geomObj.pipelineKeyName = "eventid";
        //
        var msg = "确认保存"+ size +"个居民区？";
        $.messager.confirm("确认", msg , function (r) {
            if (r) {
                _self.mapApi.commonUtil.simpleAjaxLoader({
                    url: "/HCAProject/zone/settlement/save.do",
                    data: JSON.stringify(geomObj) ,
                    onSuccess: function(res){
                        var result = JSON.parse(res) ;
                        _self.mapApi.updateLayer(settlementLayerId);
                        $.messager.show({
                            title:'提示',
                            msg:result.msg  ,
                            timeout: 3000,
                            showType:'slide'
                        });
                    },
                    onError: function(){
                        $.messager.alert({
                            title:'错误',
                            msg:'数据保存出错',
                            timeout: 3000,
                            showType:'slide'
                        });
                    }
                });
            }
        });
    };
};

var RankCellResult = function(options){
    var _self = this;
    var defaults = {
        layerId:"pd_arearank",
        title:"地区等级信息列表",
        width: 300,
        height: 500,
        top:80 ,
        right:50,
        columns:[{
            "field":"index","title":"序号","align":'center'
        },{
            "field":"name","title":"名称","width":50,"align":'center'
        },{
            "field":"rank","title":"等级","width":30,"align":'center'
        },{
            "field":"startMileage","title":"起始里程","width":50,"align":'center',editor:{type:'numberbox',options:{precision:3}}
        },{
            "field":"endMileage","title":"结束里程","width":50,"align":'center',editor:{type:'numberbox',options:{precision:3}}
        },{
            "field":"oid","title":"要素编号","hidden":true
        }]
    };
    var params = null ;
    _self.mapApi = null; //
    _self.index = null; //
    _self.state = "closed"; //open/closed /hidden/ show
    _self.container = null; //容器dom
    _self.dom = null; //模块的dom节点
    _self.moduleClass = "rank-cell-result"; //模块类

    _self.$table = null;
    _self.template =
        "<div class='" + _self.moduleClass + "'>" +
            "<table style='height: 100%;width:100%;'>" +
                "<tbody><tr><td></td></tr><tr><td></td></tr></tbody>"+
            "</table>" +
        "</div>";
    //
    _self.doClear = function () {
        $("td",_self.$table).empty();
    };
    _self.refresh = function(data){
        _self.doClear();
        var mergedSettlementIds = data["mergedSettlementOids"];
        var cellCount = data["cellCount"];
        var settlement_el =$("td" , _self.$table).eq(0);
        var count_el =$("td" , _self.$table).eq(1);
        if(mergedSettlementIds && mergedSettlementIds.length > 0){
            var ul = settlement_el.append("<ul></ul>");
            for(var i = 0 ; i < mergedSettlementIds.length ;i++){
                ul.append("<a href='#'>" +mergedSettlementIds[i] + "</a>" )
            }
        }
        count_el.append("<span>生成识别单元" + cellCount + "个" );
    };
    _self.startup = function () {
        var event = _self.mapApi.Events.ModuleStartupEvent;
        _self.mapApi.publish(event, { id: _self.id });
        _self.doClear();
    };
    _self.domCreate = function () {
        params = $.extend(defaults ,options);
        if(_self.template .indexOf(".html") > 0){
            $(_self.template).load( function (e) {
                var initEvent = _self.mapApi.Events.ModuleInitEvent;
                _self.mapApi.publish(initEvent , { target: _self });
            });
        }else{
            $(_self.container).append(_self.template);
        }
        _self.dom = $("." + _self.moduleClass , _self.container)[0];
        _self.$table = $("table", _self.dom)[0];
        //
        var options = {
            title: params.title,
            width: params.width,
            height: params.height,
            top: params.top,
            left: params.left,
            right: params.right,
            bottom: params.bottom,
            closed: true,
            constrain:true,
            modal: false,
            onOpen: function () {
                _self.refreshState("open");
            },
            onClose: function () {
                _self.refreshState("closed");
            }
        };
        _self.mapApi.moduleDialog(options, $(_self.dom) ,_self.container);
    };
    _self.show = function (flg) {
        _self.open(true);
    };
    _self.open = function (flg) {
        if (flg === false) {
            $(_self.dom).dialog('closed');
        } else {
            $(_self.dom).dialog('open');
        }
    };
    _self.refreshState = function (state) {
        _self.state = state;
        _self.mapApi.publish(_self.mapApi.Events.ModuleStateChanged,{ "module": _self});
    };
    _self.destroy = function () {
        //销毁

    }
};

/**
 * 识别区域条件模块
 */
var bufferArea = function(options){
	var _self = this;
	var defaults = {
//		layerId:"pd_bufferArea",
        title:"识别区域条件",
        width: 300,
        height: 200,
        top:80 ,
        left:0,
	};
	var params = $.extend(defaults, options);
    _self.moduleClass = "bufferArea";
    _self.label = "识别区域条件";
    _self.icon = null;
    _self.template =[
    	"<div class='" + _self.moduleClass + "'>" ,
        "<table>" +
        "<tr><td colspan='2'><label>--请输入必要的参数--</label></td></tr>" ,
        "<tr><td><span>半径：</span></td><td><input id='zone_buffer_value' type='text' value='200'><i>*</i></td></tr>" ,
        "<tr><td><span>透明度：</span></td><td><input id='zone_buffer_opacity' type='text' value='0.4'><i>*</i></td></tr>" ,
        "<tr><td><span>里程：</span></td><td><input id='zone_buffer_mileage' type='text' value='0'><i>*</i></td></tr>" ,
        "<tr>" ,
        "<td align='center' colspan='2'><input id='zone_buffer_create' type='button' value='生成' >" +
        "<input class='cbt' type='button' value='重置'></td>" ,
        "</tr>" ,
        "</table>",
        "</div>"
    ].join("");
    _self.doClear = function () {
        $("#zone_buffer_value", _self.dom).val('200');
        $("#zone_buffer_opacity", _self.dom).val('0.4');
        $("#zone_buffer_mileage", _self.dom).val('0');
    };
    _self.domCreate = function(){
        
        params = $.extend(defaults ,options);

        if(_self.template .indexOf(".html") > 0){
            $(_self.template).load( function (e) {
                var initEvent = _self.mapApi.Events.ModuleInitEvent;
                _self.mapApi.publish(initEvent , { target: _self });
            });
        }else{
            $(_self.container).append(_self.template);
        }
        _self.dom = $("." + _self.moduleClass , _self.container)[0];
        var options = {
            title: params.title,
            width: params.width,
            height: params.height,
            top: params.top,
            left: params.left,
            right: params.right,
            bottom: params.bottom,
            closed: true,
            constrain:true,
            modal: false,
            onOpen: function () {
                _self.refreshState("open");
            },
            onClose: function () {
                _self.refreshState("closed");
            }
        };
        _self.mapApi.moduleDialog(options, $(_self.dom) ,_self.container);
    };
    _self.show = function (flg) {
        //true:show , false:hidden
        _self.open(true);
    };
    _self.open = function (flg) {
        if (flg === false) {
            $(_self.dom).dialog('closed');
        } else {
            $(_self.dom).dialog('open');
        }
    };
    _self.startup = function(){
    	var bufferLayerId = "bufferLayerId";
        var pipeLayerId = "pd_pipesegment";
        var zoneCellLayerId = "zoneCellLayerId";
        var common = jasMap.commonUtil;
    	var createZoneBuffer = function(e){
            var radius = $("#zone_buffer_value") .val();
            common.simpleAjaxLoader({
                url:"/HCAProject/zone/area.do",
                data:JSON.stringify({
                    "pipesegmentTableName":"pd_pipesegment",
                    "pipesegmentKeyValue":"fa692d8c-6dfe-41d6-a8e1-6303a5ebfae5",
                    "pipesegmentKeyName":"eventid",
                    "bufferDistance":radius
                }),
                contentType:"application/json",
                method:"post",
                async:false,
                onSuccess:function(result){
                    var json = JSON.parse(result);
                    var format = new ol.format.WKT();
                    var geom = format.readGeometryFromText(json.data);
                    var options = {
                        layerId:bufferLayerId,
                        style:jasMap.Keys.defaultBufferAreaStyleName
                    };
                    jasMap.clearGraphicsLayer(bufferLayerId);
                    jasMap.addPolygonGraphic(geom,options);
                    var pipeline = getPipeline();
                    zoomToLine(pipeline,"start");
                    var mileage = pipeline.getCoordinates()[0].length;//! size
//                    setMileageRange(mileage);
                },
                onError:function(){

                }
            });
        };
        var zoomToLine = function(line ,zoomType){
            if(zoomType === "start") {
                var str = line.getFirstCoordinate();
                jasMap.zoomAt(str[0] ,str[1] ,15);

            }else if(zoomType === "end"){
                var end = line.getLastCoordinate();
                jasMap.zoomAt(end[0] ,end[1] ,15);

            }else if(!isNaN(zoomType)){ // index | mileage ?
                var coor = line.getCoordinates()[0][parseInt(zoomType)];
                jasMap.zoomAt(coor[0] ,coor[1] ,15);

            }else {
                var ext = line.getExtent();
                jasMap.zoomExtent.apply(jasMap ,ext);
            }
        };
        var getPipeline = function(){
            var layer = jasMap.getLayerById(pipeLayerId);
            var source = layer.getSource();
            var feature = source.getFeatures()[0];
            return feature.getGeometry();
        };
    	$("#zone_buffer_create") .on("click",createZoneBuffer);
    };
    _self.refreshState = function (state) {
        _self.state = state;
        _self.mapApi.publish(_self.mapApi.Events.ModuleStateChanged, {
            "module": _self
        });
    };
    
    
}