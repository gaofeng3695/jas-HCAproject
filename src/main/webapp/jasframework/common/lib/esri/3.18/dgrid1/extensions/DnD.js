//>>built
define("dgrid1/extensions/DnD","dojo/_base/declare dojo/_base/lang dojo/_base/array dojo/aspect dojo/dom-class dojo/on dojo/topic dojo/has dojo/when dojo/dnd/Source dojo/dnd/Manager dojo/_base/NodeList ../Selection dojo/has!touch?../util/touch".split(" "),function(h,m,n,u,p,y,v,q,k,r,s,w,x,t){var l=h(r,{grid:null,getObject:function(a){var b=this.grid;return b._trackError(function(){return b.collection.get(a.id.slice(b.id.length+5))})},_legalMouseDown:function(a){return this.inherited(arguments)&&
a.target!==this.grid.bodyNode},onDrop:function(a,b,g){var f=this,c=this._targetAnchor=this.targetAnchor,d=this.grid,e=d.collection;!this.before&&c&&(c=c.nextSibling);c=c&&d.row(c);k(c&&e.get(c.id),function(d){if(f!==a)f.onDropExternal(a,b,g,d);else f.onDropInternal(b,g,d)})},onDropInternal:function(a,b,g){var f=this.grid,c=f.collection,d=this,e=d._targetAnchor,h;e&&(h=this.before?e.previousSibling:e.nextSibling);e=f.row(a[0]);(b||!(h===a[0]||!g&&e&&f.down(e).element===a[0]))&&a.forEach(function(a){k(d.getObject(a),
function(a){var e=c.getIdentity(a);f._trackError(function(){return c[b&&c.copy?"copy":"put"](a,{beforeId:g?c.getIdentity(g):null}).then(function(){d._selectedNodes[e]&&(d._selectedNodes[e]=f.row(e).element)})})})})},onDropExternal:function(a,b,g,f){var c=this.grid,d=this.grid.collection,e=a.grid;b.forEach(function(b,h){k(a.getObject(b),function(k){c._trackError(function(){return d[d.copy?"copy":"put"](k,{beforeId:f?d.getIdentity(f):null}).then(function(){if(!g){if(e){var d=e.collection.getIdentity(k);
!h&&a.selectNone();a.delItem(b.id);return e.collection.remove(d)}a.deleteSelectedNodes()}})})})})},onDndStart:function(a){this.inherited(arguments);a===this&&(s.manager().avatar.node.style.width=this.grid.domNode.offsetWidth/2+"px")},onMouseDown:function(a){q("touch")&&this.isDragging&&1<t.countCurrentTouches(a,this.grid.touchNode)?(v.publish("/dnd/cancel"),s.manager().stopDrag()):this.inherited(arguments)},onMouseMove:function(a){(!q("touch")||1>=t.countCurrentTouches(a,this.grid.touchNode))&&this.inherited(arguments)},
checkAcceptance:function(a){return a.getObject&&r.prototype.checkAcceptance.apply(this,arguments)},getSelectedNodes:function(){if(!this.grid.selection)return this.inherited(arguments);var a=new w,b;for(b in this.grid.selection)a.push(this._selectedNodes[b]);return a}});h=h(x,{dndSourceType:"dgrid-row",dndParams:null,dndConstructor:l,postMixInProperties:function(){this.inherited(arguments);this.dndParams=m.mixin({accept:[this.dndSourceType]},this.dndParams)},postCreate:function(){function a(a){c[a.id]=
a.element}function b(a){delete c[a.id];p.remove(a.element,"dojoDndItemSelected dojoDndItemAnchor")}this.inherited(arguments);var g=this.dndConstructor||l,f=m.mixin(this.dndParams,{grid:this,dropParent:this.contentNode});"function"===typeof this.expand&&(f.allowNested=!0);this.dndSource=new g(this.bodyNode,f);var c=this.dndSource._selectedNodes={};this.on("dgrid-select",function(b){n.forEach(b.rows,a)});this.on("dgrid-deselect",function(a){n.forEach(a.rows,b)});u.after(this,"destroy",function(){delete this.dndSource._selectedNodes;
c=null;this.dndSource.destroy()},!0)},insertRow:function(a){var b=this.inherited(arguments),g="function"===typeof this.getObjectDndType?this.getObjectDndType(a):[this.dndSourceType];p.add(b,"dojoDndItem");this.dndSource.setItem(b.id,{data:a,type:g instanceof Array?g:[g]});return b},removeRow:function(a){this.dndSource.delItem(this.row(a));this.inherited(arguments)}});h.GridSource=l;return h});