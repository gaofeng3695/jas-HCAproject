define("dojo/Evented dojo/parser dojo/on dojo/_base/declare dojo/dom-construct dojo/_base/array dojo/dom-style dojo/_base/lang dojo/dom-class dojo/fx/Toggler dojo/fx dojo/Deferred esri/domUtils esri/InfoWindowBase".split(" "),function(l,q,h,m,c,r,f,d,b,n,k,t,g,p){return m([p,l],{isContentShowing:!1,constructor:function(a){d.mixin(this,a);b.add(this.domNode,"myInfoWindow");this._closeButton=c.create("div",{"class":"close",title:"Close"},this.domNode);this._title=c.create("div",{"class":"title"},this.domNode);
this._content=c.create("div",{"class":"content"},this.domNode);this._toggleButton=c.create("div",{"class":"toggleOpen",title:"Toggle"},this.domNode);var e=new n({node:this._content,showFunc:k.wipeIn,hideFunc:k.wipeOut});e.hide();h(this._closeButton,"click",d.hitch(this,function(){this.hide();this.isContentShowing&&(e.hide(),this.isContentShowing=!1,b.remove(this._toggleButton),b.add(this._toggleButton,"toggleOpen"))}));h(this._toggleButton,"click",d.hitch(this,function(){this.isContentShowing?(e.hide(),
this.isContentShowing=!1,b.remove(this._toggleButton),b.add(this._toggleButton,"toggleOpen")):(e.show(),this.isContentShowing=!0,b.remove(this._toggleButton),b.add(this._toggleButton,"toggleClose"))}));g.hide(this.domNode);this.isShowing=!1},setMap:function(a){this.inherited(arguments);a.on("pan-start",d.hitch(this,function(){this.hide()}));a.on("zoom-start",d.hitch(this,function(){this.hide()}))},setTitle:function(a){this.place(a,this._title)},setContent:function(a){this.place(a,this._content)},
show:function(a){a.spatialReference&&(a=this.map.toScreen(a));f.set(this.domNode,{left:a.x+10+"px",top:a.y+10+"px"});g.show(this.domNode);this.isShowing=!0;this.onShow()},hide:function(){g.hide(this.domNode);this.isShowing=!1;this.onHide()},resize:function(a,b){f.set(this._content,{width:a+"px",height:b+"px"});f.set(this._title,{width:a+"px"})},destroy:function(){c.destroy(this.domNode);this._closeButton=this._title=this._content=null}})});
