//>>built
define("dijit/_KeyNavMixin","dojo/_base/array dojo/_base/declare dojo/dom-attr dojo/keys dojo/_base/lang dojo/on dijit/registry dijit/_FocusMixin".split(" "),function(m,k,g,d,b,f,h,l){return k("dijit._KeyNavMixin",l,{tabIndex:"0",childSelector:null,postCreate:function(){this.inherited(arguments);g.set(this.domNode,"tabIndex",this.tabIndex);if(!this._keyNavCodes){var a=this._keyNavCodes={};a[d.HOME]=b.hitch(this,"focusFirstChild");a[d.END]=b.hitch(this,"focusLastChild");a[this.isLeftToRight()?d.LEFT_ARROW:
d.RIGHT_ARROW]=b.hitch(this,"_onLeftArrow");a[this.isLeftToRight()?d.RIGHT_ARROW:d.LEFT_ARROW]=b.hitch(this,"_onRightArrow");a[d.UP_ARROW]=b.hitch(this,"_onUpArrow");a[d.DOWN_ARROW]=b.hitch(this,"_onDownArrow")}var e=this,a="string"==typeof this.childSelector?this.childSelector:b.hitch(this,"childSelector");this.own(f(this.domNode,"keypress",b.hitch(this,"_onContainerKeypress")),f(this.domNode,"keydown",b.hitch(this,"_onContainerKeydown")),f(this.domNode,"focus",b.hitch(this,"_onContainerFocus")),
f(this.containerNode,f.selector(a,"focusin"),function(a){e._onChildFocus(h.getEnclosingWidget(this),a)}))},_onLeftArrow:function(){},_onRightArrow:function(){},_onUpArrow:function(){},_onDownArrow:function(){},focus:function(){this.focusFirstChild()},_getFirstFocusableChild:function(){return this._getNextFocusableChild(null,1)},_getLastFocusableChild:function(){return this._getNextFocusableChild(null,-1)},focusFirstChild:function(){this.focusChild(this._getFirstFocusableChild())},focusLastChild:function(){this.focusChild(this._getLastFocusableChild())},
focusChild:function(a,e){a&&(this.focusedChild&&a!==this.focusedChild&&this._onChildBlur(this.focusedChild),a.set("tabIndex",this.tabIndex),a.focus(e?"end":"start"))},_onContainerFocus:function(a){a.target!==this.domNode||this.focusedChild||this.focus()},_onFocus:function(){g.set(this.domNode,"tabIndex","-1");this.inherited(arguments)},_onBlur:function(a){g.set(this.domNode,"tabIndex",this.tabIndex);this.focusedChild&&(this.focusedChild.set("tabIndex","-1"),this.lastFocusedChild=this.focusedChild,
this._set("focusedChild",null));this.inherited(arguments)},_onChildFocus:function(a){a&&a!=this.focusedChild&&(this.focusedChild&&!this.focusedChild._destroyed&&this.focusedChild.set("tabIndex","-1"),a.set("tabIndex",this.tabIndex),this.lastFocused=a,this._set("focusedChild",a))},_searchString:"",multiCharSearchDuration:1E3,onKeyboardSearch:function(a,e,c,b){a&&this.focusChild(a)},_keyboardSearchCompare:function(a,e){var c=a.domNode,c=(a.label||(c.focusNode?c.focusNode.label:"")||c.innerText||c.textContent||
"").replace(/^\s+/,"").substr(0,e.length).toLowerCase();return e.length&&c==e?-1:0},_isFormElement:function(a){return"INPUT"===a.tagName||"TEXTAREA"===a.tagName||"SELECT"===a.tagName||"BUTTON"===a.tagName},_onContainerKeydown:function(a){if(!this._isFormElement(document.activeElement)){var e=this._keyNavCodes[a.keyCode];e?(e(a,this.focusedChild),a.stopPropagation(),a.preventDefault(),this._searchString=""):a.keyCode==d.SPACE&&(this._searchTimer&&!a.ctrlKey&&!a.altKey&&!a.metaKey)&&(a.stopImmediatePropagation(),
a.preventDefault(),this._keyboardSearch(a," "))}},_onContainerKeypress:function(a){if(!this._isFormElement(document.activeElement)&&!(a.charCode<=d.SPACE||a.ctrlKey||a.altKey||a.metaKey))a.preventDefault(),a.stopPropagation(),this._keyboardSearch(a,String.fromCharCode(a.charCode).toLowerCase())},_keyboardSearch:function(a,e){var c=null,d,f=0;b.hitch(this,function(){this._searchTimer&&this._searchTimer.remove();this._searchString+=e;var a=/^(.)\1*$/.test(this._searchString)?1:this._searchString.length;
d=this._searchString.substr(0,a);this._searchTimer=this.defer(function(){this._searchTimer=null;this._searchString=""},this.multiCharSearchDuration);var b=this.focusedChild||null;if(1==a||!b)if(b=this._getNextFocusableChild(b,1),!b)return;a=b;do{var g=this._keyboardSearchCompare(b,d);g&&0==f++&&(c=b);if(-1==g){f=-1;break}b=this._getNextFocusableChild(b,1)}while(b&&b!=a)})();this.onKeyboardSearch(c,a,d,f)},_onChildBlur:function(){},_getNextFocusableChild:function(a,b){var c=a;do{if(a)a=this._getNext(a,
b);else if(a=this[0<b?"_getFirst":"_getLast"](),!a)break;if(null!=a&&a!=c&&a.isFocusable())return a}while(a!=c);return null},_getFirst:function(){return null},_getLast:function(){return null},_getNext:function(a,b){if(a)for(a=a.domNode;a;)if((a=a[0>b?"previousSibling":"nextSibling"])&&"getAttribute"in a){var c=h.byNode(a);if(c)return c}return null}})});