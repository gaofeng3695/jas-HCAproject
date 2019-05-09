//>>built
define("dojox/wire/ml/Transfer",["dojo","dijit","dojox","dojo/require!dijit/_Widget,dijit/_Container,dojox/wire/_base,dojox/wire/ml/Action"],function(b,h,d){b.provide("dojox.wire.ml.Transfer");b.require("dijit._Widget");b.require("dijit._Container");b.require("dojox.wire._base");b.require("dojox.wire.ml.Action");b.declare("dojox.wire.ml.Transfer",d.wire.ml.Action,{source:"",sourceStore:"",sourceAttribute:"",sourcePath:"",type:"",converter:"",target:"",targetStore:"",targetAttribute:"",targetPath:"",
delimiter:"",_run:function(){var c=this._getWire("source"),a=this._getWire("target");d.wire.transfer(c,a,arguments)},_getWire:function(c){var a=void 0,a="source"==c?{object:this.source,dataStore:this.sourceStore,attribute:this.sourceAttribute,path:this.sourcePath,type:this.type,converter:this.converter}:{object:this.target,dataStore:this.targetStore,attribute:this.targetAttribute,path:this.targetPath};if(a.object)if(9<=a.object.length&&"arguments"==a.object.substring(0,9))a.property=a.object.substring(9),
a.object=null;else{var b=a.object.indexOf(".");0>b?a.object=d.wire.ml._getValue(a.object):(a.property=a.object.substring(b+1),a.object=d.wire.ml._getValue(a.object.substring(0,b)))}a.dataStore&&(a.dataStore=d.wire.ml._getValue(a.dataStore));var e=void 0,g=this.getChildren();for(b in g){var f=g[b];f instanceof d.wire.ml.ChildWire&&f.which==c&&(e||(e={}),f._addWire(this,e))}e&&(e.object=d.wire.create(a),e.dataStore=a.dataStore,a=e);return a}});b.declare("dojox.wire.ml.ChildWire",h._Widget,{which:"source",
object:"",property:"",type:"",converter:"",attribute:"",path:"",name:"",_addWire:function(c,a){this.name?(a.children||(a.children={}),a.children[this.name]=this._getWire(c)):(a.children||(a.children=[]),a.children.push(this._getWire(c)))},_getWire:function(c){return{object:this.object?d.wire.ml._getValue(this.object):void 0,property:this.property,type:this.type,converter:this.converter,attribute:this.attribute,path:this.path}}});b.declare("dojox.wire.ml.ColumnWire",d.wire.ml.ChildWire,{column:"",
_addWire:function(c,a){this.column?(a.columns||(a.columns={}),a.columns[this.column]=this._getWire(c)):(a.columns||(a.columns=[]),a.columns.push(this._getWire(c)))}});b.declare("dojox.wire.ml.NodeWire",[d.wire.ml.ChildWire,h._Container],{titleProperty:"",titleAttribute:"",titlePath:"",_addWire:function(c,a){a.nodes||(a.nodes=[]);a.nodes.push(this._getWires(c))},_getWires:function(c){var a={node:this._getWire(c),title:{type:"string",property:this.titleProperty,attribute:this.titleAttribute,path:this.titlePath}},
b=[],e=this.getChildren(),g;for(g in e){var f=e[g];f instanceof d.wire.ml.NodeWire&&b.push(f._getWires(c))}0<b.length&&(a.children=b);return a}});b.declare("dojox.wire.ml.SegmentWire",d.wire.ml.ChildWire,{_addWire:function(b,a){a.segments||(a.segments=[]);a.segments.push(this._getWire(b));b.delimiter&&!a.delimiter&&(a.delimiter=b.delimiter)}})});