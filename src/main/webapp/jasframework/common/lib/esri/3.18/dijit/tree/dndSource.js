//>>built
define("dijit/tree/dndSource","dojo/_base/array dojo/_base/declare dojo/dnd/common dojo/dom-class dojo/dom-geometry dojo/_base/lang dojo/mouse dojo/on dojo/touch dojo/topic dojo/dnd/Manager ./_dndSelector".split(" "),function(p,u,v,t,w,m,x,z,A,n,k,y){return u("dijit.tree.dndSource",y,{isSource:!0,accept:["text","treeNode"],copyOnly:!1,dragThreshold:5,betweenThreshold:0,generateText:!0,constructor:function(a,f){f||(f={});m.mixin(this,f);var c=f.accept instanceof Array?f.accept:["text","treeNode"];
this.accept=null;if(c.length){this.accept={};for(var d=0;d<c.length;++d)this.accept[c[d]]=1}this.mouseDown=this.isDragging=!1;this.targetBox=this.targetAnchor=null;this.dropPosition="";this._lastY=this._lastX=0;this.sourceState="";this.isSource&&t.add(this.node,"dojoDndSource");this.targetState="";this.accept&&t.add(this.node,"dojoDndTarget");this.topics=[n.subscribe("/dnd/source/over",m.hitch(this,"onDndSourceOver")),n.subscribe("/dnd/start",m.hitch(this,"onDndStart")),n.subscribe("/dnd/drop",m.hitch(this,
"onDndDrop")),n.subscribe("/dnd/cancel",m.hitch(this,"onDndCancel"))]},checkAcceptance:function(){return!0},copyState:function(a){return this.copyOnly||a},destroy:function(){this.inherited(arguments);for(var a;a=this.topics.pop();)a.remove();this.targetAnchor=null},_onDragMouse:function(a,f){var c=k.manager(),d=this.targetAnchor,b=this.current,e=this.dropPosition,g="Over";if(b&&0<this.betweenThreshold){if(!this.targetBox||d!=b)this.targetBox=w.position(b.rowNode,!0);a.pageY-this.targetBox.y<=this.betweenThreshold?
g="Before":a.pageY-this.targetBox.y>=this.targetBox.h-this.betweenThreshold&&(g="After")}if(f||b!=d||g!=e){d&&this._removeItemClass(d.rowNode,e);b&&this._addItemClass(b.rowNode,g);if(b)if(b==this.tree.rootNode&&"Over"!=g)c.canDrop(!1);else{e=d=!1;if(c.source==this){var e="Over"===g,h;for(h in this.selection){var l=this.selection[h];if(l.item===b.item){d=!0;break}l.getParent().id!==b.id&&(e=!1)}}c.canDrop(!d&&!e&&!this._isParentChildDrop(c.source,b.rowNode)&&this.checkItemAcceptance(b.rowNode,c.source,
g.toLowerCase()))}else c.canDrop(!1);this.targetAnchor=b;this.dropPosition=g}},onMouseMove:function(a){if(!(this.isDragging&&"Disabled"==this.targetState)){this.inherited(arguments);var f=k.manager();if(this.isDragging)this._onDragMouse(a);else if(this.mouseDown&&this.isSource&&(Math.abs(a.pageX-this._lastX)>=this.dragThreshold||Math.abs(a.pageY-this._lastY)>=this.dragThreshold)){var c=this.getSelectedTreeNodes();if(c.length){if(1<c.length){var d=this.selection,b=0,e=[],g,h;a:for(;g=c[b++];){for(h=
g.getParent();h&&h!==this.tree;h=h.getParent())if(d[h.id])continue a;e.push(g)}c=e}c=p.map(c,function(a){return a.domNode});f.startDrag(this,c,this.copyState(v.getCopyKeyState(a)));this._onDragMouse(a,!0)}}}},onMouseDown:function(a){if("touchstart"==a.type||x.isLeft(a))this.mouseDown=!0,this.mouseButton=a.button,this._lastX=a.pageX,this._lastY=a.pageY;this.inherited(arguments)},onMouseUp:function(a){this.mouseDown&&(this.mouseDown=!1,this.inherited(arguments))},onMouseOut:function(){this.inherited(arguments);
this._unmarkTargetAnchor()},checkItemAcceptance:function(){return!0},onDndSourceOver:function(a){this!=a?(this.mouseDown=!1,this._unmarkTargetAnchor()):this.isDragging&&k.manager().canDrop(!1)},onDndStart:function(a,f,c){this.isSource&&this._changeState("Source",this==a?c?"Copied":"Moved":"");f=this.checkAcceptance(a,f);this._changeState("Target",f?"":"Disabled");this==a&&k.manager().overSource(this);this.isDragging=!0},itemCreator:function(a){return p.map(a,function(a){return{id:a.id,name:a.textContent||
a.innerText||""}})},onDndDrop:function(a,f,c){if("Over"==this.containerState){var d=this.tree,b=d.model,e=this.targetAnchor,g=!1;this.isDragging=!1;var h,l,k;h=e&&e.item||d.item;"Before"==this.dropPosition||"After"==this.dropPosition?(h=e.getParent()&&e.getParent().item||d.item,l=e.getIndexInParent(),"After"==this.dropPosition?(l=e.getIndexInParent()+1,k=e.getNextSibling()&&e.getNextSibling().item):k=e.item):(h=e&&e.item||d.item,g=!0);var m;p.forEach(f,function(d,g){var n=a.getItem(d.id);if(-1!=p.indexOf(n.type,
"treeNode"))var q=n.data,r=q.item,s=q.getParent().item;a==this?("number"==typeof l&&h==s&&q.getIndexInParent()<l&&(l-=1),b.pasteItem(r,s,h,c,l,k)):b.isItem(r)?b.pasteItem(r,s,h,c,l,k):(m||(m=this.itemCreator(f,e.rowNode,a)),b.newItem(m[g],h,l,k))},this);g&&this.tree._expandNode(e)}this.onDndCancel()},onDndCancel:function(){this._unmarkTargetAnchor();this.mouseDown=this.isDragging=!1;delete this.mouseButton;this._changeState("Source","");this._changeState("Target","")},onOverEvent:function(){this.inherited(arguments);
k.manager().overSource(this)},onOutEvent:function(){this._unmarkTargetAnchor();var a=k.manager();this.isDragging&&a.canDrop(!1);a.outSource(this);this.inherited(arguments)},_isParentChildDrop:function(a,f){if(!a.tree||a.tree!=this.tree)return!1;for(var c=a.tree.domNode,d=a.selection,b=f.parentNode;b!=c&&!d[b.id];)b=b.parentNode;return b.id&&d[b.id]},_unmarkTargetAnchor:function(){this.targetAnchor&&(this._removeItemClass(this.targetAnchor.rowNode,this.dropPosition),this.dropPosition=this.targetBox=
this.targetAnchor=null)},_markDndStatus:function(a){this._changeState("Source",a?"Copied":"Moved")}})});