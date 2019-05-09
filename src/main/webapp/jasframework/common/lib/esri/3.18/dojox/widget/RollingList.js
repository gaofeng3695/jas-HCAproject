//>>built
define("dojox/widget/RollingList",["dojo","dijit","dojox","dojo/i18n!dijit/nls/common","dojo/require!dojo/window,dijit/layout/ContentPane,dijit/_Templated,dijit/_Contained,dijit/layout/_LayoutWidget,dijit/Menu,dijit/form/Button,dijit/focus,dijit/_base/focus,dojox/html/metrics,dojo/i18n"],function(c,g,l){c.provide("dojox.widget.RollingList");c.experimental("dojox.widget.RollingList");c.require("dojo.window");c.require("dijit.layout.ContentPane");c.require("dijit._Templated");c.require("dijit._Contained");
c.require("dijit.layout._LayoutWidget");c.require("dijit.Menu");c.require("dijit.form.Button");c.require("dijit.focus");c.require("dijit._base.focus");c.require("dojox.html.metrics");c.require("dojo.i18n");c.requireLocalization("dijit","common");c.declare("dojox.widget._RollingListPane",[g.layout.ContentPane,g._Templated,g._Contained],{templateString:'\x3cdiv class\x3d"dojoxRollingListPane"\x3e\x3ctable\x3e\x3ctbody\x3e\x3ctr\x3e\x3ctd dojoAttachPoint\x3d"containerNode"\x3e\x3c/td\x3e\x3c/tr\x3e\x3c/tbody\x3e\x3c/div\x3e',
parentWidget:null,parentPane:null,store:null,items:null,query:null,queryOptions:null,_focusByNode:!0,minWidth:0,_setContentAndScroll:function(a,b){this._setContent(a,b);this.parentWidget.scrollIntoView(this)},_updateNodeWidth:function(a,b){a.style.width="";c.marginBox(a).w<b&&c.marginBox(a,{w:b})},_onMinWidthChange:function(a){this._updateNodeWidth(this.domNode,a)},_setMinWidthAttr:function(a){a!==this.minWidth&&(this.minWidth=a,this._onMinWidthChange(a))},startup:function(){this._started||(this.store&&
this.store.getFeatures()["dojo.data.api.Notification"]&&window.setTimeout(c.hitch(this,function(){this.connect(this.store,"onSet","_onSetItem");this.connect(this.store,"onNew","_onNewItem");this.connect(this.store,"onDelete","_onDeleteItem")}),1),this.connect(this.focusNode||this.domNode,"onkeypress","_focusKey"),this.parentWidget._updateClass(this.domNode,"Pane"),this.inherited(arguments),this._onMinWidthChange(this.minWidth))},_focusKey:function(a){a.charOrCode==c.keys.BACKSPACE?c.stopEvent(a):
a.charOrCode==c.keys.LEFT_ARROW&&this.parentPane?(this.parentPane.focus(),this.parentWidget.scrollIntoView(this.parentPane)):a.charOrCode==c.keys.ENTER&&this.parentWidget._onExecute()},focus:function(a){if(this.parentWidget._focusedPane!=this&&(this.parentWidget._focusedPane=this,this.parentWidget.scrollIntoView(this),this._focusByNode&&(!this.parentWidget._savedFocus||a)))try{(this.focusNode||this.domNode).focus()}catch(b){}},_onShow:function(){this.inherited(arguments);(this.store||this.items)&&
(this.refreshOnShow&&this.domNode||!this.isLoaded&&this.domNode)&&this.refresh()},_load:function(){this.isLoaded=!1;this.items?(this._setContentAndScroll(this.onLoadStart(),!0),window.setTimeout(c.hitch(this,"_doQuery"),1)):this._doQuery()},_doLoadItems:function(a,b){var f=0,e=this.store;c.forEach(a,function(a){e.isItemLoaded(a)||f++});if(0===f)b();else{var d=function(a){f--;0===f&&b()};c.forEach(a,function(a){e.isItemLoaded(a)||e.loadItem({item:a,onItem:d})})}},_doQuery:function(){if(this.domNode){var a=
this.parentWidget.preloadItems,a=!0===a||this.items&&this.items.length<=Number(a);if(this.items&&a)this._doLoadItems(this.items,c.hitch(this,"onItems"));else if(this.items)this.onItems();else this._setContentAndScroll(this.onFetchStart(),!0),this.store.fetch({query:this.query,onComplete:function(a){this.items=a;this.onItems()},onError:function(a){this._onError("Fetch",a)},scope:this})}},_hasItem:function(a){for(var b=this.items||[],c=0,e;e=b[c];c++)if(this.parentWidget._itemsMatch(e,a))return!0;return!1},
_onSetItem:function(a,b,c,e){this._hasItem(a)&&this.refresh()},_onNewItem:function(a,b){var c;!b&&!this.parentPane||b&&this.parentPane&&this.parentPane._hasItem(b.item)&&(c=this.parentPane._getSelected())&&this.parentWidget._itemsMatch(c.item,b.item)?(this.items.push(a),this.refresh()):b&&(this.parentPane&&this._hasItem(b.item))&&this.refresh()},_onDeleteItem:function(a){this._hasItem(a)&&(this.items=c.filter(this.items,function(b){return b!=a}),this.refresh())},onFetchStart:function(){return this.loadingMessage},
onFetchError:function(a){return this.errorMessage},onLoadStart:function(){return this.loadingMessage},onLoadError:function(a){return this.errorMessage},onItems:function(){this.onLoadDeferred||(this.cancel(),this.onLoadDeferred=new c.Deferred(c.hitch(this,"cancel")));this._onLoadHandler()}});c.declare("dojox.widget._RollingListGroupPane",[l.widget._RollingListPane],{templateString:'\x3cdiv\x3e\x3cdiv dojoAttachPoint\x3d"containerNode"\x3e\x3c/div\x3e\x3cdiv dojoAttachPoint\x3d"menuContainer"\x3e\x3cdiv dojoAttachPoint\x3d"menuNode"\x3e\x3c/div\x3e\x3c/div\x3e\x3c/div\x3e',
_menu:null,_setContent:function(a){this._menu||this.inherited(arguments)},_onMinWidthChange:function(a){if(this._menu){var b=c.marginBox(this.domNode).w,f=c.marginBox(this._menu.domNode).w;this._updateNodeWidth(this._menu.domNode,a-(b-f))}},onItems:function(){var a;this._menu&&(a=this._getSelected(),this._menu.destroyRecursive());this._menu=this._getMenu();var b,f;this.items.length?c.forEach(this.items,function(c){if(b=this.parentWidget._getMenuItemForItem(c,this))a&&this.parentWidget._itemsMatch(b.item,
a.item)&&(f=b),this._menu.addChild(b)},this):(b=this.parentWidget._getMenuItemForItem(null,this))&&this._menu.addChild(b);if(f){if(this._setSelected(f),a&&!a.children&&f.children||a&&a.children&&!f.children){var e=this.parentWidget._getPaneForItem(f.item,this,f.children);e?this.parentWidget.addChild(e,this.getIndexInParent()+1):(this.parentWidget._removeAfter(this),this.parentWidget._onItemClick(null,this,f.item,f.children))}}else a&&this.parentWidget._removeAfter(this);this.containerNode.innerHTML=
"";this.containerNode.appendChild(this._menu.domNode);this.parentWidget.scrollIntoView(this);this._checkScrollConnection(!0);this.inherited(arguments);this._onMinWidthChange(this.minWidth)},_checkScrollConnection:function(a){var b=this.store;this._scrollConn&&this.disconnect(this._scrollConn);delete this._scrollConn;c.every(this.items,function(a){return b.isItemLoaded(a)})||(a&&this._loadVisibleItems(),this._scrollConn=this.connect(this.domNode,"onscroll","_onScrollPane"))},startup:function(){this.inherited(arguments);
this.parentWidget._updateClass(this.domNode,"GroupPane")},focus:function(a){if(this._menu){this._pendingFocus&&this.disconnect(this._pendingFocus);delete this._pendingFocus;var b=this._menu.focusedChild;if(!b){var f=c.query(".dojoxRollingListItemSelected",this.domNode)[0];f&&(b=g.byNode(f))}b||(b=this._menu.getChildren()[0]||this._menu);this._focusByNode=!1;if(b.focusNode){if(!this.parentWidget._savedFocus||a)try{b.focusNode.focus()}catch(e){}window.setTimeout(function(){try{c.window.scrollIntoView(b.focusNode)}catch(a){}},
1)}else b.focus?(!this.parentWidget._savedFocus||a)&&b.focus():this._focusByNode=!0;this.inherited(arguments)}else this._pendingFocus||(this._pendingFocus=this.connect(this,"onItems","focus"))},_getMenu:function(){var a=this,b=new g.Menu({parentMenu:this.parentPane?this.parentPane._menu:null,onCancel:function(b){a.parentPane&&a.parentPane.focus(!0)},_moveToPopup:function(a){if(this.focusedChild&&!this.focusedChild.disabled)this.onItemClick(this.focusedChild,a)}},this.menuNode);this.connect(b,"onItemClick",
function(a,e){if(!a.disabled)if(e.alreadySelected=c.hasClass(a.domNode,"dojoxRollingListItemSelected"),e.alreadySelected&&("keypress"==e.type&&e.charOrCode!=c.keys.ENTER||"internal"==e.type)){var d=this.parentWidget.getChildren()[this.getIndexInParent()+1];d&&(d.focus(!0),this.parentWidget.scrollIntoView(d))}else this._setSelected(a,b),this.parentWidget._onItemClick(e,this,a.item,a.children),"keypress"==e.type&&e.charOrCode==c.keys.ENTER&&this.parentWidget._onExecute()});b._started||b.startup();return b},
_onScrollPane:function(){this._visibleLoadPending&&window.clearTimeout(this._visibleLoadPending);this._visibleLoadPending=window.setTimeout(c.hitch(this,"_loadVisibleItems"),500)},_loadVisibleItems:function(){delete this._visibleLoadPending;var a=this._menu;if(a){var b=a.getChildren();if(b&&b.length){var f=function(a,b,f){var e=c.getComputedStyle(a),d=0;b&&(d+=c._getMarginExtents(a,e).t);f&&(d+=c._getPadBorderExtents(a,e).t);return d},f=f(this.domNode,!1,!0)+f(this.containerNode,!0,!0)+f(a.domNode,
!0,!0)+f(b[0].domNode,!0,!1),e=c.contentBox(this.domNode).h,d=this.domNode.scrollTop-f-e/2,g=d+3*e/2,h=c.filter(b,function(a){var b=a.domNode.offsetTop,c=a.store;a=a.item;return b>=d&&b<=g&&!c.isItemLoaded(a)}),p=c.map(h,function(a){return a.item}),b=c.hitch(this,function(){var b=this._getSelected();c.forEach(p,function(c,f){var e=this.parentWidget._getMenuItemForItem(c,this),d=h[f],g=d.getIndexInParent();a.removeChild(d);e&&(b&&this.parentWidget._itemsMatch(e.item,b.item),a.addChild(e,g),a.focusedChild==
d&&a.focusChild(e));d.destroy()},this);this._checkScrollConnection(!1)});this._doLoadItems(p,b)}}},_getSelected:function(a){a||(a=this._menu);if(a){a=this._menu.getChildren();for(var b=0,f;f=a[b];b++)if(c.hasClass(f.domNode,"dojoxRollingListItemSelected"))return f}return null},_setSelected:function(a,b){b||(b=this._menu);b&&c.forEach(b.getChildren(),function(b){this.parentWidget._updateClass(b.domNode,"Item",{Selected:a&&b==a&&!b.disabled})},this)}});c.declare("dojox.widget.RollingList",[g._Widget,
g._Templated,g._Container],{templateString:c.cache("dojox.widget","RollingList/RollingList.html",'\x3cdiv class\x3d"dojoxRollingList ${className}"\r\n\t\x3e\x3cdiv class\x3d"dojoxRollingListContainer" dojoAttachPoint\x3d"containerNode" dojoAttachEvent\x3d"onkeypress:_onKey"\r\n\t\x3e\x3c/div\r\n\t\x3e\x3cdiv class\x3d"dojoxRollingListButtons" dojoAttachPoint\x3d"buttonsNode"\r\n        \x3e\x3cbutton dojoType\x3d"dijit.form.Button" dojoAttachPoint\x3d"okButton"\r\n\t\t\t\tdojoAttachEvent\x3d"onClick:_onExecute"\x3e${okButtonLabel}\x3c/button\r\n        \x3e\x3cbutton dojoType\x3d"dijit.form.Button" dojoAttachPoint\x3d"cancelButton"\r\n\t\t\t\tdojoAttachEvent\x3d"onClick:_onCancel"\x3e${cancelButtonLabel}\x3c/button\r\n\t\x3e\x3c/div\r\n\x3e\x3c/div\x3e\r\n'),
widgetsInTemplate:!0,className:"",store:null,query:null,queryOptions:null,childrenAttrs:["children"],parentAttr:"",value:null,executeOnDblClick:!0,preloadItems:!1,showButtons:!1,okButtonLabel:"",cancelButtonLabel:"",minPaneWidth:0,postMixInProperties:function(){this.inherited(arguments);var a=c.i18n.getLocalization("dijit","common");this.okButtonLabel=this.okButtonLabel||a.buttonOk;this.cancelButtonLabel=this.cancelButtonLabel||a.buttonCancel},_setShowButtonsAttr:function(a){var b=!1;if(this.showButtons!=
a&&this._started||this.showButtons==a&&!this.started)b=!0;c.toggleClass(this.domNode,"dojoxRollingListButtonsHidden",!a);this.showButtons=a;b&&(this._started?this.layout():window.setTimeout(c.hitch(this,"layout"),0))},_itemsMatch:function(a,b){return!a&&!b?!0:!a||!b?!1:a==b||this._isIdentity&&this.store.getIdentity(a)==this.store.getIdentity(b)},_removeAfter:function(a){"number"!=typeof a&&(a=this.getIndexOfChild(a));0<=a&&c.forEach(this.getChildren(),function(b,c){c>a&&(this.removeChild(b),b.destroyRecursive())},
this);for(var b=this.getChildren(),b=b[b.length-1],f=null;b&&!f;){var e=b._getSelected?b._getSelected():null;e&&(f=e.item);b=b.parentPane}this._setInProgress||this._setValue(f)},addChild:function(a,b){0<b&&this._removeAfter(b-1);this.inherited(arguments);a._started||a.startup();a.attr("minWidth",this.minPaneWidth);this.layout();this._savedFocus||a.focus()},_setMinPaneWidthAttr:function(a){a!==this.minPaneWidth&&(this.minPaneWidth=a,c.forEach(this.getChildren(),function(b){b.attr("minWidth",a)}))},
_updateClass:function(a,b,f){this._declaredClasses||(this._declaredClasses=("dojoxRollingList "+this.className).split(" "));c.forEach(this._declaredClasses,function(e){if(e){c.addClass(a,e+b);for(var d in f||{})c.toggleClass(a,e+b+d,f[d]);c.toggleClass(a,e+b+"FocusSelected",c.hasClass(a,e+b+"Focus")&&c.hasClass(a,e+b+"Selected"));c.toggleClass(a,e+b+"HoverSelected",c.hasClass(a,e+b+"Hover")&&c.hasClass(a,e+b+"Selected"))}})},scrollIntoView:function(a){this._scrollingTimeout&&window.clearTimeout(this._scrollingTimeout);
delete this._scrollingTimeout;this._scrollingTimeout=window.setTimeout(c.hitch(this,function(){a.domNode&&c.window.scrollIntoView(a.domNode);delete this._scrollingTimeout}),1)},resize:function(a){g.layout._LayoutWidget.prototype.resize.call(this,a)},layout:function(){var a=this.getChildren();if(this._contentBox){var b=this._contentBox.h-c.marginBox(this.buttonsNode).h-l.html.metrics.getScrollbar().h;c.forEach(a,function(a){c.marginBox(a.domNode,{h:b})})}this._focusedPane?(a=this._focusedPane,delete this._focusedPane,
this._savedFocus||a.focus()):a&&a.length&&(this._savedFocus||a[0].focus())},_onChange:function(a){this.onChange(a)},_setValue:function(a){delete this._setInProgress;this._itemsMatch(this.value,a)||(this.value=a,this._onChange(a))},_setValueAttr:function(a){if((!this._itemsMatch(this.value,a)||a)&&!(this._setInProgress&&this._setInProgress===a))if(this._setInProgress=a,!a||!this.store.isItem(a)){var b=this.getChildren()[0];b._setSelected(null);this._onItemClick(null,b,null,null)}else{var f=c.hitch(this,
function(b,f){var e=this.store,d;if(this.parentAttr&&e.getFeatures()["dojo.data.api.Identity"]&&((d=this.store.getValue(b,this.parentAttr))||""===d)){var g=function(a){e.getIdentity(a)==e.getIdentity(b)?f(null):f([a])};""===d?f(null):"string"==typeof d?e.fetchItemByIdentity({identity:d,onItem:g}):e.isItem(d)&&g(d)}else{var m=this.childrenAttrs.length,k=[];c.forEach(this.childrenAttrs,function(c){var d={};d[c]=b;e.fetch({query:d,scope:this,onComplete:function(b){this._setInProgress===a&&(k=k.concat(b),
m--,0===m&&f(k))}})},this)}}),e=c.hitch(this,function(b,d){var f=b[d],g=this.getChildren()[d],n;if(f&&g){var m=c.hitch(this,function(){n&&this.disconnect(n);delete n;if(this._setInProgress===a){var k=c.filter(g._menu.getChildren(),function(a){return this._itemsMatch(a.item,f)},this)[0];k&&(d++,g._menu.onItemClick(k,{type:"internal",stopPropagation:function(){},preventDefault:function(){}}),b[d]?e(b,d):(this._setValue(f),this.onItemClick(f,g,this.getChildItems(f))))}});g.isLoaded?m():n=this.connect(g,
"onLoad",m)}else 0===d&&this.set("value",null)}),d=[],g=c.hitch(this,function(a){a&&a.length?(d.push(a[0]),f(a[0],g)):(a||d.pop(),d.reverse(),e(d,0))}),b=this.domNode.style;"none"==b.display||"hidden"==b.visibility?this._setValue(a):this._itemsMatch(a,this._visibleItem)||g([a])}},_onItemClick:function(a,b,c,e){if(a){var d=this._getPaneForItem(c,b,e);"click"==a.type&&a.alreadySelected&&d?(this._removeAfter(b.getIndexInParent()+1),(d=b.getNextSibling())&&d._setSelected&&d._setSelected(null),this.scrollIntoView(d)):
d?(this.addChild(d,b.getIndexInParent()+1),this._savedFocus&&d.focus(!0)):(this._removeAfter(b),this.scrollIntoView(b))}else b&&(this._removeAfter(b),this.scrollIntoView(b));if(!a||"internal"!=a.type)this._setValue(c),this.onItemClick(c,b,e);this._visibleItem=c},_getPaneForItem:function(a,b,c){var e=this.getPaneForItem(a,b,c);e.store=this.store;e.parentWidget=this;e.parentPane=b||null;a?e.items=c?c:[a]:(e.query=this.query,e.queryOptions=this.queryOptions);return e},_getMenuItemForItem:function(a,
b){var f=this.store;if(!a||!f||!f.isItem(a))return f=new g.MenuItem({label:"---",disabled:!0,iconClass:"dojoxEmpty",focus:function(){}}),this._updateClass(f.domNode,"Item"),f;var e=(f=f.isItemLoaded(a))?this.getChildItems(a):void 0,d;if(e)if(d=this.getMenuItemForItem(a,b,e),d.children=e,this._updateClass(d.domNode,"Item",{Expanding:!0}),d._started)c.style(d.arrowWrapper,"visibility","");else var l=d.connect(d,"startup",function(){this.disconnect(l);c.style(this.arrowWrapper,"visibility","")});else d=
this.getMenuItemForItem(a,b,null),f?this._updateClass(d.domNode,"Item",{Single:!0}):(this._updateClass(d.domNode,"Item",{Unloaded:!0}),d.attr("disabled",!0));d.store=this.store;d.item=a;d.label||d.attr("label",this.store.getLabel(a).replace(/</,"\x26lt;"));if(d.focusNode){var h=this;d.focus=function(){if(!this.disabled)try{this.focusNode.focus()}catch(a){}};d.connect(d.focusNode,"onmouseenter",function(){this.disabled||h._updateClass(this.domNode,"Item",{Hover:!0})});d.connect(d.focusNode,"onmouseleave",
function(){this.disabled||h._updateClass(this.domNode,"Item",{Hover:!1})});d.connect(d.focusNode,"blur",function(){h._updateClass(this.domNode,"Item",{Focus:!1,Hover:!1})});d.connect(d.focusNode,"focus",function(){h._updateClass(this.domNode,"Item",{Focus:!0});h._focusedPane=b});this.executeOnDblClick&&d.connect(d.focusNode,"ondblclick",function(){h._onExecute()})}return d},_setStore:function(a){a===this.store&&this._started||(this.store=a,this._isIdentity=a.getFeatures()["dojo.data.api.Identity"],
a=this._getPaneForItem(),this.addChild(a,0))},_onKey:function(a){if(a.charOrCode==c.keys.BACKSPACE)c.stopEvent(a);else if(a.charOrCode==c.keys.ESCAPE&&this._savedFocus){try{g.focus(this._savedFocus)}catch(b){}c.stopEvent(a)}else(a.charOrCode==c.keys.LEFT_ARROW||a.charOrCode==c.keys.RIGHT_ARROW)&&c.stopEvent(a)},_resetValue:function(){this.set("value",this._lastExecutedValue)},_onCancel:function(){this._resetValue();this.onCancel()},_onExecute:function(){this._lastExecutedValue=this.get("value");this.onExecute()},
focus:function(){var a=this._savedFocus;this._savedFocus=g.getFocus(this);this._savedFocus.node||delete this._savedFocus;if(this._focusedPane){this._savedFocus=g.getFocus(this);var b=this._focusedPane;delete this._focusedPane;a||b.focus(!0)}else(b=this.getChildren()[0])&&!a&&b.focus(!0)},handleKey:function(a){return a.keyCode==c.keys.DOWN_ARROW?(delete this._savedFocus,this.focus(),!1):a.keyCode==c.keys.ESCAPE?(this._onCancel(),!1):!0},_updateChildClasses:function(){var a=this.getChildren(),b=a.length;
c.forEach(a,function(a,e){c.toggleClass(a.domNode,"dojoxRollingListPaneCurrentChild",e==b-1);c.toggleClass(a.domNode,"dojoxRollingListPaneCurrentSelected",e==b-2)})},startup:function(){if(!this._started){if(!this.getParent||!this.getParent())this.resize(),this.connect(c.global,"onresize","resize");this.connect(this,"addChild","_updateChildClasses");this.connect(this,"removeChild","_updateChildClasses");this._setStore(this.store);this.set("showButtons",this.showButtons);this.inherited(arguments);this._lastExecutedValue=
this.get("value")}},getChildItems:function(a){var b,f=this.store;c.forEach(this.childrenAttrs,function(c){(c=f.getValues(a,c))&&c.length&&(b=(b||[]).concat(c))});return b},getMenuItemForItem:function(a,b,c){return new g.MenuItem({})},getPaneForItem:function(a,b,c){return!a||c?new l.widget._RollingListGroupPane({}):null},onItemClick:function(a,b,c){},onExecute:function(){},onCancel:function(){},onChange:function(a){}})});