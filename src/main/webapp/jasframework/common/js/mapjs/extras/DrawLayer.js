define("dojo/_base/declare dojo/parser dijit/registry dojo/on dojo/_base/lang dijit/layout/TabContainer dijit/layout/ContentPane esri/graphicsUtils dojo/dom-construct dojo/dom esri/layers/GraphicsLayer esri/InfoTemplate extras/InfoWindow esri/Color esri/symbols/SimpleFillSymbol esri/symbols/SimpleMarkerSymbol esri/symbols/SimpleLineSymbol esri/symbols/PictureMarkerSymbol esri/symbols/TextSymbol".split(" "),function(l,w,n,a,z,A,B,C,D,E,x,y,F,m,q,r,t,u,v){return l([x],{constructor:function(){this.symbolInfoTemplates=
{"":""};this.dataName="ok"},_pictureMarkerTemplate:'<div class="myInfoWindow"><div data-dojo-type="dijit/layout/TabContainer" style="width: 290px;height: 220px;" ><div data-dojo-type="dijit/layout/ContentPane" title="\u6570\u636e" data-dojo-props="selected:true"><table><tr><td><label for="dataName">\u540d\u79f0:</label></td><td><input id="dataName" data-dojo-attach-point="dataNameNode" data-dojo-type="dijit/form/TextBox"></td></tr><tr><td><label for="dataInfo">\u4fe1\u606f:</label></td><td><textarea id="dataInfo" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr><tr><td><label for="dataRemark">\u5907\u6ce8:</label></td><td><textarea id="dataRemark" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr></table></div><div data-dojo-type="dijit/layout/ContentPane" title="\u6837\u5f0f" data-dojo-props=""><table><tr><td><label for="picture">\u56fe\u6807:</label></td><td><select id="picture" data-dojo-type="dijit/form/ComboBox" ><option value="images/plot/pic_government.png" selected>\u653f\u5e9c</option><option value="images/plot/pic_school.png">\u5b66\u6821</option><option value="images/plot/pic_vehicle.png">\u8f66\u8f86</option></select></td></tr></table></div></div></div>',
_simpleMarkerTemplate:'<div class="myInfoWindow"><div data-dojo-type="dijit/layout/TabContainer" style="width: 290px;height: 220px;" ><div data-dojo-type="dijit/layout/ContentPane" title="\u6570\u636e" data-dojo-props="selected:true"><table><tr><td><label for="dataName">\u540d\u79f0:</label></td><td><input id="dataName" data-dojo-attach-point="dataNameNode" data-dojo-type="dijit/form/TextBox"></td></tr><tr><td><label for="dataInfo">\u4fe1\u606f:</label></td><td><textarea id="dataInfo" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr><tr><td><label for="dataRemark">\u5907\u6ce8:</label></td><td><textarea id="dataRemark" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr></table></div><div data-dojo-type="dijit/layout/ContentPane" title="\u6837\u5f0f" data-dojo-props=""><table><tr><td><label for="markerColor">\u6807\u8bb0\u989c\u8272:</label></td><td><span id="markerColor" data-dojo-attach-point="markerColor" data-dojo-props="" data-dojo-type="dijit/ColorPalette"></td></tr><tr><td><label for="opacity">\u900f\u660e\u5ea6:</label></td><td><input id="opacity"  data-dojo-type="dijit/form/NumberTextBox" value="1" data-dojo-props="smallDelta:0.1, constraints:{min:0,max:1,places:0}" ></td></tr><tr><td><label for="size">\u5927\u5c0f:</label></td><td><input id="size" data-dojo-type="dijit/form/NumberSpinner" value="12" data-dojo-props="smallDelta:1, constraints:{min:1,max:100,places:0}" ></td></tr><tr><td><label for="style">\u6837\u5f0f:</label></td><td><select id="style" data-dojo-type="dijit/form/ComboBox" ><option value="circle" selected>\u5706</option><option value="cross">\u5341\u5b57</option><option value="diamond">\u94bb\u77f3</option><option value="path">\u8def\u5f84</option><option value="square">\u65b9\u5757</option><option value="x">X</option></select></td></tr></table></div></div></div>',
_simpleLineTemplate:'<div class="myInfoWindow"><div data-dojo-type="dijit/layout/TabContainer" style="width: 290px;height: 220px;" ><div data-dojo-type="dijit/layout/ContentPane" title="\u6570\u636e" data-dojo-props="selected:true"><table><tr><td><label for="dataName">\u540d\u79f0:</label></td><td><input id="dataName" data-dojo-attach-point="dataNameNode" data-dojo-type="dijit/form/TextBox"></td></tr><tr><td><label for="dataInfo">\u4fe1\u606f:</label></td><td><textarea id="dataInfo" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr><tr><td><label for="dataRemark">\u5907\u6ce8:</label></td><td><textarea id="dataRemark" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr></table></div><div data-dojo-type="dijit/layout/ContentPane" title="\u6837\u5f0f" data-dojo-props=""><table><tr><td><label for="lineColor">\u7ebf\u989c\u8272:</label></td><td><span id="lineColor" data-dojo-attach-point="lineColor" data-dojo-props="" data-dojo-type="dijit/ColorPalette"></td></tr><tr><td><label for="opacity">\u900f\u660e\u5ea6:</label></td><td><input id="opacity"  data-dojo-type="dijit/form/NumberTextBox" value="1" data-dojo-props="smallDelta:0.1, constraints:{min:0,max:1,places:0}" ></td></tr><tr><td><label for="width">\u7ebf\u5bbd:</label></td><td><input id="width" data-dojo-type="dijit/form/NumberSpinner" value="12" data-dojo-props="smallDelta:1, constraints:{min:1,max:100,places:0}" ></td></tr><tr><td><label for="style">\u7ebf\u6837\u5f0f:</label></td><td><select id="style" data-dojo-type="dijit/form/ComboBox" data-dojo-props="hiddenValue :\'value\'" ><option value="solid" selected>\u5b9e\u7ebf</option><option value="dash">\u865a\u7ebf</option><option value="dashdot">\u5355\u70b9\u865a\u7ebf</option><option value="dashdotdot">\u53cc\u70b9\u865a\u7ebf</option><option value="dot">\u70b9\u865a\u7ebf</option><option value="longdash">\u957f\u865a\u7ebf</option><option value="longdashdot">\u957f\u70b9\u865a\u7ebf</option><option value="null">\u7a7a</option><option value="shortdash">\u77ed\u865a\u7ebf</option><option value="shortdashdot">\u77ed\u70b9\u865a\u7ebf</option><option value="shortdashdotdot">\u77ed\u53cc\u70b9\u865a\u7ebf</option><option value="shortdot">\u77ed\u70b9</option></select></td></tr></table></div></div></div>',
_simpleFillTemplate:'<div class="myInfoWindow"><div data-dojo-type="dijit/layout/TabContainer" style="width: 290px;height: 220px;" ><div data-dojo-type="dijit/layout/ContentPane" title="\u6570\u636e" data-dojo-props="selected:true"><table><tr><td><label for="dataName">\u540d\u79f0:</label></td><td><input id="dataName" data-dojo-attach-point="dataNameNode" data-dojo-type="dijit/form/TextBox"></td></tr><tr><td><label for="dataInfo">\u4fe1\u606f:</label></td><td><textarea id="dataInfo" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr><tr><td><label for="dataRemark">\u5907\u6ce8:</label></td><td><textarea id="dataRemark" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr></table></div><div data-dojo-type="dijit/layout/ContentPane" title="\u6837\u5f0f" data-dojo-props=""><table><tr><td><label for="fillColor">\u586b\u5145\u989c\u8272:</label></td><td><span id="fillColor" data-dojo-attach-point="fillColor" data-dojo-props="" data-dojo-type="dijit/ColorPalette"></td></tr><tr><td><label for="fillOpacity">\u900f\u660e\u5ea6:</label></td><td><input id="fillOpacity"  data-dojo-type="dijit/form/NumberTextBox" value="1" data-dojo-props="smallDelta:0.1, constraints:{min:0,max:1,places:0}" ></td></tr><tr><td><label for="fillType">\u586b\u5145\u6837\u5f0f:</label></td><td><select id="fillType" data-dojo-type="dijit/form/ComboBox" data-dojo-props="hiddenValue :\'value\'" ><option value="solid" selected>\u586b\u5145\u989c\u8272</option><option value="null">\u65e0\u586b\u5145</option><option value="cross">\u5341\u5b57</option><option value="vertical">\uff1f\uff1f</option></select></td></tr><tr><td><label for="lineColor">\u989c\u8272:</label></td><td><span id="lineColor" data-dojo-attach-point="lineColor" data-dojo-props="" data-dojo-type="dijit/ColorPalette"></td></tr><tr><td><label for="opacity">\u900f\u660e\u5ea6:</label></td><td><input id="opacity"  data-dojo-type="dijit/form/NumberTextBox" value="1" data-dojo-props="smallDelta:0.1, constraints:{min:0,max:1,places:0}" ></td></tr><tr><td><label for="width">\u7ebf\u5bbd:</label></td><td><input id="width" data-dojo-type="dijit/form/NumberSpinner" value="12" data-dojo-props="smallDelta:1, constraints:{min:1,max:100,places:0}" ></td></tr><tr><td><label for="style">\u7ebf\u6837\u5f0f:</label></td><td><select id="style" data-dojo-type="dijit/form/ComboBox" data-dojo-props="hiddenValue :\'value\'" ><option value="solid" selected>\u5b9e\u7ebf</option><option value="dash">\u865a\u7ebf</option><option value="dashdot">\u5355\u70b9\u865a\u7ebf</option><option value="dashdotdot">\u53cc\u70b9\u865a\u7ebf</option><option value="dot">\u70b9\u865a\u7ebf</option><option value="longdash">\u957f\u865a\u7ebf</option><option value="longdashdot">\u957f\u70b9\u865a\u7ebf</option><option value="null">\u7a7a</option><option value="shortdash">\u77ed\u865a\u7ebf</option><option value="shortdashdot">\u77ed\u70b9\u865a\u7ebf</option><option value="shortdashdotdot">\u77ed\u53cc\u70b9\u865a\u7ebf</option><option value="shortdot">\u77ed\u70b9</option></select></td></tr></table></div></div></div>',
_textTemplate:'<div class="myInfoWindow"><div data-dojo-type="dijit/layout/TabContainer" style="width: 290px;height: 220px;" ><div data-dojo-type="dijit/layout/ContentPane" title="\u6570\u636e" data-dojo-props="selected:true"><table><tr><td><label for="dataName">\u540d\u79f0:</label></td><td><input id="dataName" data-dojo-attach-point="dataNameNode" data-dojo-type="dijit/form/TextBox"></td></tr><tr><td><label for="dataInfo">\u4fe1\u606f:</label></td><td><textarea id="dataInfo" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr><tr><td><label for="dataRemark">\u5907\u6ce8:</label></td><td><textarea id="dataRemark" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr></table></div><div data-dojo-type="dijit/layout/ContentPane" title="\u6837\u5f0f" data-dojo-props=""><table><tr><td><label for="text">\u6587\u672c:</label></td><td><textarea id="text" rows="4" cols="25" data-dojo-type="dijit/form/SimpleTextarea" ></textarea></td></tr><tr><td><label for="color">\u989c\u8272:</label></td><td><span id="color" data-dojo-attach-point="color" data-dojo-props="" data-dojo-type="dijit/ColorPalette"></td></tr><tr><td><label for="size">\u5927\u5c0f:</label></td><td><input id="size" data-dojo-type="dijit/form/NumberSpinner" value="12" data-dojo-props="smallDelta:1, constraints:{min:1,max:100,places:0}" ></td></tr><tr><td><label for="style">\u7ebf\u6837\u5f0f:</label></td><td><select id="style" data-dojo-type="dijit/form/ComboBox" data-dojo-props="hiddenValue :\'value\'" ><option value="solid" selected>\u5b9e\u7ebf</option><option value="dash">\u865a\u7ebf</option><option value="dashdot">\u5355\u70b9\u865a\u7ebf</option><option value="dashdotdot">\u53cc\u70b9\u865a\u7ebf</option><option value="dot">\u70b9\u865a\u7ebf</option><option value="longdash">\u957f\u865a\u7ebf</option><option value="longdashdot">\u957f\u70b9\u865a\u7ebf</option><option value="null">\u7a7a</option><option value="shortdash">\u77ed\u865a\u7ebf</option><option value="shortdashdot">\u77ed\u70b9\u865a\u7ebf</option><option value="shortdashdotdot">\u77ed\u53cc\u70b9\u865a\u7ebf</option><option value="shortdot">\u77ed\u70b9</option></select></td></tr></table></div></div></div>',
_setMap:function(a,c){return this.inherited(arguments)},_unsetMap:function(){this.inherited(arguments)},_draw:function(a){this._map&&this.inherited(arguments)},onMouseOver:function(a){this.inherited(arguments)},onMouseOut:function(a){this.inherited(arguments)},onUpdateEnd:function(a){this.inherited(arguments)},onClick:function(d){var c=this,b=d.graphic;d.graphic.attributes||(d.graphic.attributes={});var k=d.graphic.attributes;this._map.infoWindow.onShow=function(d){var e=this.domNode,l=function(){var f=
n.byId("dataName",e),d=n.byId("dataInfo",e),g=n.byId("dataRemark",e);k.name&&f.setValue(k.name);k.info&&d.setValue(k.info);k.remark&&g.setValue(k.remark);a(f,"change",function(b){k.name=b});a(d,"change",function(b){k.info=b});a(g,"change",function(b){k.remark=b});if(b.symbol instanceof r){var h=dijit.byId("markerColor",e);d=dijit.byId("opacity",e);g=dijit.byId("size",e);f=dijit.byId("style",e);b.symbol.size&&g.setValue(b.symbol.size);b.symbol.color[3]&&d.setValue(b.symbol.color[3]);a(h,"change",function(p){b.symbol.setColor(m.fromHex(p));
c.redraw()});a(d,"change",function(p){var a=b.symbol.color;a[3]=p;b.symbol.setColor(a);c.redraw()});a(g,"change",function(a){b.symbol.setSize(a);c.redraw()});a(f,"change",function(a){b.symbol.setStyle(this.item.value);c.redraw()})}else if(b.symbol instanceof u)f=dijit.byId("picture",e),a(f,"change",function(a){b.symbol.setUrl(this.item.value);c.redraw()});else if(b.symbol instanceof t)g=dijit.byId("lineColor",e),d=dijit.byId("opacity",e),h=dijit.byId("width",e),f=dijit.byId("style",e),b.symbol.width&&
h.setValue(b.symbol.width),a(g,"change",function(a){b.symbol.setColor(m.fromHex(a));c.redraw()}),a(d,"change",function(a){var d=b.symbol.color;d[3]=a;b.symbol.setColor(d);c.redraw()}),a(h,"change",function(a){b.symbol.setWidth(a);c.redraw()}),a(f,"change",function(a){b.symbol.setStyle(this.item.value);c.redraw()});else if(b.symbol instanceof q){dijit.byId("fillColor",e);var l=dijit.byId("fillOpacity",e);dijit.byId("fillStyle",e);g=dijit.byId("lineColor",e);d=dijit.byId("opacity",e);h=dijit.byId("width",
e);f=dijit.byId("style",e);b.symbol.color[3]&&l.setValue(b.symbol.color[3]);b.symbol.outline.width&&h.setValue(b.symbol.outline.width);b.symbol.outline.color[3]&&d.setValue(b.symbol.outline.color[3]);a(g,"change",function(a){b.symbol.outline.setColor(m.fromHex(a));c.redraw()});a(d,"change",function(a){var d=b.symbol.outline.color;d[3]=a;b.symbol.outline.setColor(d);c.redraw()});a(h,"change",function(a){b.symbol.outline.setWidth(a);c.redraw()});a(f,"change",function(a){b.symbol.outline.setStyle(this.item.value);
c.redraw()})}else b.symbol instanceof v&&(d=dijit.byId("color",e),h=dijit.byId("text",e),g=dijit.byId("size",e),f=dijit.byId("style",e),b.symbol.font.size&&g.setValue(b.symbol.font.size),b.symbol.text&&h.setValue(b.symbol.text),a(d,"change",function(a){b.symbol.setColor(m.fromHex(a));c.redraw()}),a(g,"change",function(a){b.symbol.font.setSize(a);c.redraw()}),a(h,"change",function(a){b.symbol.setText(a);c.redraw()}),a(f,"change",function(a){b.symbol.font.setStyle(this.item.value);c.redraw()}))};w.parse(this.domNode).then(function(a){l()})};
this._map.infoWindow.resize(320,220);this.inherited(arguments)},onGraphicAdd:function(a){var c=new y;c.setTitle("\u6807\u7ed8\u8bbe\u7f6e");a.symbol instanceof r?c.setContent(this._simpleMarkerTemplate):a.symbol instanceof u?c.setContent(this._pictureMarkerTemplate):a.symbol instanceof t?c.setContent(this._simpleLineTemplate):a.symbol instanceof q?c.setContent(this._simpleFillTemplate):a.symbol instanceof v&&c.setContent(this._textTemplate);a.setInfoTemplate(c);this.inherited(arguments)}})});
