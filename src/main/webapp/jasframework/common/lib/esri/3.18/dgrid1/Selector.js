//>>built
define("dgrid1/Selector","dojo/_base/declare dojo/_base/lang dojo/_base/sniff dojo/dom-construct dojo/dom-class ./Selection".split(" "),function(l,k,m,n,p,q){return l(q,{postCreate:function(){this.inherited(arguments);this.on(".dgrid-selector:click,.dgrid-selector:keydown",k.hitch(this,"_handleSelectorClick"));this.on("dgrid-select",k.hitch(this,"_changeSelectorInput",!0));this.on("dgrid-deselect",k.hitch(this,"_changeSelectorInput",!1))},_defaultRenderSelectorInput:function(a,b,c,e){var f=a.grid;
p.add(c,"dgrid-selector");return c.input=n.create("input",{"aria-checked":b,checked:b,disabled:!f.allowSelect(f.row(e)),tabIndex:isNaN(a.tabIndex)?-1:a.tabIndex,type:a.selector},c)},_configureSelectorColumn:function(a){var b=this,c=a.selector;this._selectorColumns.push(a);this._selectorSingleRow=this._selectorSingleRow||"radio"===a.selector;var e="function"===typeof c?c:this._defaultRenderSelectorInput;a.sortable=!1;a.renderCell=function(c,d,g){d=(d=c&&b.row(c))&&b.selection[d.id];e(a,!!d,g,c)};a.renderHeaderCell=
function(c){var d="label"in a?a.label:a.field||"";"radio"===a.selector||!b.allowSelectAll?c.appendChild(document.createTextNode(d)):(a._selectorHeaderCheckbox=e(a,!1,c,{}),b._hasSelectorHeaderCheckbox=!0)}},_handleSelectorClick:function(a){if(!("INPUT"===a.target.nodeName&&"keydown"===a.type&&32===a.keyCode)){var b=this.cell(a).row;if("click"===a.type||32===a.keyCode||!m("opera")&&13===a.keyCode||0===a.keyCode){this._selectionTriggerEvent=a;if(b){if(this.allowSelect(b)){var c=this._lastSelected&&
this.row(this._lastSelected);if(this._selectorSingleRow){if(!c||c.id!==b.id)this.clearSelection(),this.select(b,null,!0),this._lastSelected=b.element}else b&&(a.shiftKey?this._changeSelectorInput(!0,{rows:[b]}):c=null,c=a.shiftKey?c:null,this.select(c||b,b,c?void 0:null),this._lastSelected=b.element)}}else this[this.allSelected?"clearSelection":"selectAll"]();this._selectionTriggerEvent=null}}},_changeSelectorInput:function(a,b){this._selectorColumns.length&&this._updateRowSelectors(a,b);this._hasSelectorHeaderCheckbox&&
this._updateHeaderCheckboxes()},_updateRowSelectors:function(a,b){for(var c=b.rows,e=c.length,f=this._selectorColumns.length,d=0;d<e;d++)for(var g=0;g<f;g++){var h=this.cell(c[d],this._selectorColumns[g].id).element;if(h&&(h=(h.contents||h).input)&&!h.disabled)h.checked=a,h.setAttribute("aria-checked",a)}},_updateHeaderCheckboxes:function(){for(var a=this._selectorColumns.length,b=0;b<a;b++){var c="false",e,f,d=this._selectorColumns[b]._selectorHeaderCheckbox;if(d){e=this.selection;f=!1;for(var g in e)if(e[g]!=
this.allSelected){f=!0;break}d.indeterminate=f;d.checked=this.allSelected;f?c="mixed":this.allSelected&&(c="true");d.setAttribute("aria-checked",c)}}},configStructure:function(){this.inherited(arguments);var a=this.columns;this._selectorColumns=[];this._hasSelectorHeaderCheckbox=this._selectorSingleRow=!1;for(var b in a)a[b].selector&&this._configureSelectorColumn(a[b])},_handleSelect:function(a){var b=this.cell(a).column;(!b||!b.selector)&&this.inherited(arguments)}})});