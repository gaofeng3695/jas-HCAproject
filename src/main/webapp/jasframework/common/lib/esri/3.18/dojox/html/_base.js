//>>built
define("dojox/html/_base","dojo/_base/declare dojo/Deferred dojo/dom-construct dojo/html dojo/_base/kernel dojo/_base/lang dojo/ready dojo/_base/sniff dojo/_base/url dojo/_base/xhr dojo/when dojo/_base/window".split(" "),function(s,t,l,n,u,k,v,w,m,x,y,z){var d=u.getObject("dojox.html",!0);if(w("ie"))var p=/(AlphaImageLoader\([^)]*?src=(['"]))(?![a-z]+:|\/)([^\r\n;}]+?)(\2[^)]*\)\s*[;}]?)/g;var A=/(?:(?:@import\s*(['"])(?![a-z]+:|\/)([^\r\n;{]+?)\1)|url\(\s*(['"]?)(?![a-z]+:|\/)([^\r\n;]+?)\3\s*\))([a-z, \s]*[;}]?)/g,
r=d._adjustCssPaths=function(b,a){if(a&&b)return p&&(a=a.replace(p,function(a,q,g,f,e){return q+(new m(b,"./"+f)).toString()+e})),a.replace(A,function(a,q,g,f,e,d){return g?'@import "'+(new m(b,"./"+g)).toString()+'"'+d:"url("+(new m(b,"./"+e)).toString()+")"+d})},B=/(<[a-z][a-z0-9]*\s[^>]*)(?:(href|src)=(['"]?)([^>]*?)\3|style=(['"]?)([^>]*?)\5)([^>]*>)/gi,C=d._adjustHtmlPaths=function(b,a){var c=b||"./";return a.replace(B,function(a,b,f,e,d,h,k,l){return b+(f?f+"\x3d"+e+(new m(c,d)).toString()+
e:"style\x3d"+h+r(c,k)+h)+l})},D=d._snarfStyles=function(b,a,c){c.attributes=[];a=a.replace(/<[!][-][-](.|\s)*?[-][-]>/g,function(a){return a.replace(/<(\/?)style\b/ig,"\x26lt;$1Style").replace(/<(\/?)link\b/ig,"\x26lt;$1Link").replace(/@import "/ig,'@ import "')});return a.replace(/(?:<style([^>]*)>([\s\S]*?)<\/style>|<link\s+(?=[^>]*rel=['"]?stylesheet)([^>]*?href=(['"])([^>]*?)\4[^>\/]*)\/?>)/gi,function(a,g,f,e,d,h){a=(g||e||"").replace(/^\s*([\s\S]*?)\s*$/i,"$1");f?f=c.push(b?r(b,f):f):(f=c.push('@import "'+
h+'";'),a=a.replace(/\s*(?:rel|href)=(['"])?[^\s]*\1\s*/gi,""));if(a){a=a.split(/\s+/);h={};e=0;for(d=a.length;e<d;e++)g=a[e].split("\x3d"),h[g[0]]=g[1].replace(/^\s*['"]?([\s\S]*?)['"]?\s*$/,"$1");c.attributes[f-1]=h}return""})},E=d._snarfScripts=function(b,a){function c(b){a.downloadRemote&&(b=b.replace(/&([a-z0-9#]+);/g,function(a,b){switch(b){case "amp":return"\x26";case "gt":return"\x3e";case "lt":return"\x3c";default:return"#"==b.charAt(0)?String.fromCharCode(b.substring(1)):"\x26"+b+";"}}),
x.get({url:b,sync:!0,load:function(b){""!==a.code&&(b="\n"+b);a.code+=b+";"},error:a.errBack}))}a.code="";b=b.replace(/<[!][-][-](.|\s)*?[-][-]>/g,function(a){return a.replace(/<(\/?)script\b/ig,"\x26lt;$1Script")});return b.replace(/<script\s*(?![^>]*type=['"]?(?:dojo\/|text\/html\b))[^>]*?(?:src=(['"]?)([^>]*?)\1[^>]*)?>([\s\S]*?)<\/script>/gi,function(b,d,f,e){f?c(f):(""!==a.code&&(e="\n"+e),a.code+=e+";");return""})},F=d.evalInGlobal=function(b,a){a=a||z.doc.body;var c=a.ownerDocument.createElement("script");
c.type="text/javascript";a.appendChild(c);c.text=b};d._ContentSetter=s(n._ContentSetter,{adjustPaths:!1,referencePath:".",renderStyles:!1,executeScripts:!1,scriptHasHooks:!1,scriptHookReplacement:null,_renderStyles:function(b){this._styleNodes=[];for(var a,c,d,g=this.node.ownerDocument,f=g.getElementsByTagName("head")[0],e=0,k=b.length;e<k;e++){d=b[e];c=b.attributes[e];a=g.createElement("style");a.setAttribute("type","text/css");for(var h in c)a.setAttribute(h,c[h]);this._styleNodes.push(a);f.appendChild(a);
a.styleSheet?a.styleSheet.cssText=d:a.appendChild(g.createTextNode(d))}},empty:function(){this.inherited("empty",arguments);this._styles=[]},onBegin:function(){this.inherited("onBegin",arguments);var b=this.content,a=this._styles;this._code=null;if(k.isString(b)){this.adjustPaths&&this.referencePath&&(b=C(this.referencePath,b));if(this.renderStyles||this.cleanContent)b=D(this.referencePath,b,a);if(this.executeScripts){var c=this,a={downloadRemote:!0,errBack:function(a){c._onError.call(c,"Exec",'Error downloading remote script in "'+
c.id+'"',a)}},b=E(b,a);this._code=a.code}}this.content=b},onEnd:function(){var b=this._code,a=this._styles;if(this._styleNodes&&this._styleNodes.length)for(;this._styleNodes.length;)l.destroy(this._styleNodes.pop());this.renderStyles&&(a&&a.length)&&this._renderStyles(a);var c=new t,d=this.getInherited(arguments),g=arguments,a=k.hitch(this,function(){d.apply(this,g);y(this.parseDeferred,function(){c.resolve()})});if(this.executeScripts&&b){this.cleanContent&&(b=b.replace(/(\x3c!--|(?:\/\/)?--\x3e|<!\[CDATA\[|\]\]>)/g,
""));this.scriptHasHooks&&(b=b.replace(/_container_(?!\s*=[^=])/g,this.scriptHookReplacement));try{F(b,this.node)}catch(f){this._onError("Exec","Error eval script in "+this.id+", "+f.message,f)}v(a)}else a();return c.promise},tearDown:function(){this.inherited(arguments);delete this._styles;if(this._styleNodes&&this._styleNodes.length)for(;this._styleNodes.length;)l.destroy(this._styleNodes.pop());delete this._styleNodes;k.mixin(this,d._ContentSetter.prototype)}});d.set=function(b,a,c){return c?(new d._ContentSetter(k.mixin(c,
{content:a,node:b}))).set():n._setNodeContent(b,a,!0)};return d});