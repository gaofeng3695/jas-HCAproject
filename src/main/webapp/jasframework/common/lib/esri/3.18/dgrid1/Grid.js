//>>built
define("dgrid1/Grid","dojo/_base/declare dojo/_base/lang dojo/dom-construct dojo/dom-class dojo/on dojo/has ./List ./util/misc dojo/_base/sniff".split(" "),function(l,q,m,r,w,x,y,s){function p(a,b){b&&b.nodeType&&a.appendChild(b)}l=l(y,{columns:null,hasNeutralSort:!1,cellNavigation:!0,tabableHeader:!0,showHeader:!0,column:function(a){return"object"!==typeof a?this.columns[a]:this.cell(a).column},listType:"grid",cell:function(a,b){if(a.column&&a.element)return a;a.target&&a.target.nodeType&&(a=a.target);
var c;if(a.nodeType){do{if(this._rowIdToObject[a.id])break;var d=a.columnId;if(d){b=d;c=a;break}a=a.parentNode}while(a&&a!==this.domNode)}if(!c&&"undefined"!==typeof b){var e=this.row(a);if(d=e&&e.element)for(var d=d.getElementsByTagName("td"),f=0;f<d.length;f++)if(d[f].columnId===b){c=d[f];break}}if(null!=a)return{row:e||this.row(a),column:b&&this.column(b),element:c}},createRowCells:function(a,b,c,d,e){var f=m.create("table",{className:"dgrid-row-table",role:"presentation"}),g=9>x("ie")?m.create("tbody",
null,f):f,l,u,p,v,q,t,h,n,k,r;c=c||this.subRows;u=0;for(p=c.length;u<p;u++){t=c[u];l=m.create("tr",null,g);t.className&&(l.className=t.className);v=0;for(q=t.length;v<q;v++){h=t[v];n=h.id;k=h.field?" field-"+s.escapeCssIdentifier(h.field,"-"):"";(r="function"===typeof h.className?h.className(d):h.className)&&(k+=" "+r);k=m.create(a,{className:"dgrid-cell"+(n?" dgrid-column-"+s.escapeCssIdentifier(n,"-"):"")+k,role:"th"===a?"columnheader":"gridcell"});k.columnId=n;if(n=h.colSpan)k.colSpan=n;if(n=h.rowSpan)k.rowSpan=
n;b(k,h,d,e);l.appendChild(k)}}return f},_createBodyRowCell:function(a,b,c,d){var e=c;b.get?e=b.get(c):"field"in b&&"_item"!==b.field&&(e=c[b.field]);b.renderCell?p(a,b.renderCell(c,e,a,d)):this._defaultRenderCell.call(b,c,e,a,d)},_createHeaderRowCell:function(a,b){var c=b.headerNode=a,d=b.field;d&&(a.field=d);if(b.renderHeaderCell)p(c,b.renderHeaderCell(c));else if("label"in b||b.field)c.appendChild(document.createTextNode("label"in b?b.label:b.field));!1!==b.sortable&&(d&&"_item"!==d)&&(a.sortable=
!0,a.className+=" dgrid-sortable")},left:function(a,b){a.element||(a=this.cell(a));return this.cell(this._move(a,-(b||1),"dgrid-cell"))},right:function(a,b){a.element||(a=this.cell(a));return this.cell(this._move(a,b||1,"dgrid-cell"))},_defaultRenderCell:function(a,b,c){if(this.formatter){var d=this.formatter,e=this.grid.formatterScope;c.innerHTML="string"===typeof d&&e?e[d](b,a):this.formatter(b,a)}else null!=b&&c.appendChild(document.createTextNode(b))},renderRow:function(a,b){var c=this.createRowCells("td",
q.hitch(this,"_createBodyRowCell"),b&&b.subRows,a,b),d=m.create("div",{role:"row"});d.appendChild(c);return d},renderHeader:function(){var a=this,b=this.headerNode;b.setAttribute("role","row");m.empty(b);var c=this.createRowCells("th",q.hitch(this,"_createHeaderRowCell"),this.subRows&&this.subRows.headerRows);this._rowIdToObject[c.id=this.id+"-header"]=this.columns;b.appendChild(c);this._sortListener&&this._sortListener.remove();this._sortListener=w(c,"click,keydown",function(c){if("click"===c.type||
32===c.keyCode||!x("opera")&&13===c.keyCode){var e=c.target,f,g;do if(e.sortable){f=e.field||e.columnId;g=a.sort[0];f=!a.hasNeutralSort||!g||g.property!==f||!g.descending?[{property:f,descending:g&&g.property===f&&!g.descending}]:[];g={bubbles:!0,cancelable:!0,grid:a,parentType:c.type,sort:f};w.emit(c.target,"dgrid-sort",g)&&(a._sortNode=e,a.set("sort",f));break}while((e=e.parentNode)&&e!==b)}})},resize:function(){var a=this.headerNode.firstChild,b=this.contentNode,c;this.inherited(arguments);b.style.width=
"";if(b&&a&&(c=a.offsetWidth)>b.offsetWidth)b.style.width=c+"px"},destroy:function(){this._destroyColumns();this._sortListener&&this._sortListener.remove();this.inherited(arguments)},_setSort:function(){this.inherited(arguments);this.updateSortArrow(this.sort)},_findSortArrowParent:function(a){var b=this.columns,c;for(c in b){var d=b[c];if(d.field===a)return d.headerNode}},updateSortArrow:function(a,b){this._lastSortedArrow&&(r.remove(this._lastSortedArrow.parentNode,"dgrid-sort-up dgrid-sort-down"),
m.destroy(this._lastSortedArrow),delete this._lastSortedArrow);b&&(this.sort=a);if(a[0]){var c=a[0].property,d=a[0].descending,c=this._sortNode||this._findSortArrowParent(c);delete this._sortNode;c&&(c=c.contents||c,this._lastSortedArrow=m.create("div",{className:"dgrid-sort-arrow ui-icon",innerHTML:"\x26nbsp;",role:"presentation"},c,"first"),r.add(c,"dgrid-sort-"+(d?"down":"up")),this.resize())}},styleColumn:function(a,b){return this.addCssRule("#"+s.escapeCssIdentifier(this.domNode.id)+" .dgrid-column-"+
s.escapeCssIdentifier(a,"-"),b)},_configColumns:function(a,b){var c=[],d=b instanceof Array;s.each(b,function(e,f){"string"===typeof e&&(b[f]=e={label:e});!d&&!e.field&&(e.field=f);f=e.id=e.id||(isNaN(f)?f:a+f);this._configColumn&&(this._configColumn(e,b,a),f=e.id);d&&(this.columns[f]=e);e.grid=this;c.push(e)},this);return d?b:c},_destroyColumns:function(){this.cleanup()},configStructure:function(){var a=this.subRows,b=this._columns=this.columns;this.columns=!b||b instanceof Array?{}:b;if(a)for(b=
0;b<a.length;b++)a[b]=this._configColumns(b+"-",a[b]);else this.subRows=[this._configColumns("",b)]},_getColumns:function(){return this._columns||this.columns},_setColumns:function(a){this._destroyColumns();this.subRows=null;this.columns=a;this._updateColumns()},_setSubRows:function(a){this._destroyColumns();this.subRows=a;this._updateColumns()},_updateColumns:function(){this.configStructure();this.renderHeader();this.refresh();this._lastCollection&&this.renderArray(this._lastCollection);this._started&&
(this.sort.length?(this._lastSortedArrow=null,this.updateSortArrow(this.sort)):this.resize())}});l.appendIfNode=p;return l});