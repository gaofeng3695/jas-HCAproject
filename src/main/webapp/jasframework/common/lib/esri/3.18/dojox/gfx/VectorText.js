//>>built
define("dojox/gfx/VectorText","dojo/_base/lang dojo/_base/declare dojo/_base/array dojo/_base/loader dojo/_base/xhr ./_base dojox/xml/DomParser dojox/html/metrics ./matrix".split(" "),function(t,B,p,G,C,k,D,E,u){var F=function(a){var b;C.get({url:a,sync:!0,load:function(a){b=a}});return b};t.getObject("dojox.gfx.VectorText",!0);t.mixin(k,{vectorFontFitting:{NONE:0,FLOW:1,FIT:2},defaultVectorText:{type:"vectortext",x:0,y:0,width:null,height:null,text:"",align:"start",decoration:"none",fitting:0,leading:1.5},
defaultVectorFont:{type:"vectorfont",size:"10pt",family:null},_vectorFontCache:{},_svgFontCache:{},getVectorFont:function(a){return k._vectorFontCache[a]?k._vectorFontCache[a]:new k.VectorFont(a)}});return B("dojox.gfx.VectorFont",null,{_entityRe:/&(quot|apos|lt|gt|amp|#x[^;]+|#\d+);/g,_decodeEntitySequence:function(a){if(a.match(this._entityRe)){for(var b={amp:"\x26",apos:"'",quot:'"',lt:"\x3c",gt:"\x3e"},e,f="";null!==(e=this._entityRe.exec(a));)f="x"==e[1].charAt(1)?f+String.fromCharCode(parseInt(e[1].slice(2),
16)):isNaN(parseInt(e[1].slice(1),10))?f+(b[e[1]]||""):f+String.fromCharCode(parseInt(e[1].slice(1),10));return f}},_parse:function(a,b){var e=k._svgFontCache[b]||D.parse(a),f=e.documentElement.byName("font")[0],c=e.documentElement.byName("font-face")[0],h=parseFloat(c.getAttribute("units-per-em")||1E3,10),g={x:parseFloat(f.getAttribute("horiz-adv-x"),10),y:parseFloat(f.getAttribute("vert-adv-y")||0,10)};g.y||(g.y=h);var f={horiz:{x:parseFloat(f.getAttribute("horiz-origin-x")||0,10),y:parseFloat(f.getAttribute("horiz-origin-y")||
0,10)},vert:{x:parseFloat(f.getAttribute("vert-origin-x")||0,10),y:parseFloat(f.getAttribute("vert-origin-y")||0,10)}},d=c.getAttribute("font-family"),q=c.getAttribute("font-style")||"all",l=c.getAttribute("font-variant")||"normal",u=c.getAttribute("font-weight")||"all",n=c.getAttribute("font-stretch")||"normal",s=c.getAttribute("unicode-range")||"U+0-10FFFF";c.getAttribute("panose-1");c.getAttribute("cap-height");var x=parseFloat(c.getAttribute("ascent")||h-f.vert.y,10),y=parseFloat(c.getAttribute("descent")||
f.vert.y,10),r={},m=d;c.byName("font-face-name")[0]&&(m=c.byName("font-face-name")[0].getAttribute("name"));if(!k._vectorFontCache[m]){p.forEach(["alphabetic","ideographic","mathematical","hanging"],function(a){var b=c.getAttribute(a);null!==b&&(r[a]=parseFloat(b,10))});var v=parseFloat(e.documentElement.byName("missing-glyph")[0].getAttribute("horiz-adv-x")||g.x,10),z={},w={},A=e.documentElement.byName("glyph");p.forEach(A,function(a){var b=a.getAttribute("unicode"),e=a.getAttribute("glyph-name"),
c=parseFloat(a.getAttribute("horiz-adv-x")||g.x,10);a=a.getAttribute("d");b.match(this._entityRe)&&(b=this._decodeEntitySequence(b));c={code:b,name:e,xAdvance:c,path:a};z[b]=c;w[e]=c},this);A=e.documentElement.byName("hkern");p.forEach(A,function(a,b){var e=-parseInt(a.getAttribute("k"),10),c=a.getAttribute("u1"),f=a.getAttribute("g1"),d=a.getAttribute("u2"),h=a.getAttribute("g2"),g;c?(c=this._decodeEntitySequence(c),z[c]&&(g=z[c])):w[f]&&(g=w[f]);g&&(g.kern||(g.kern={}),d?(d=this._decodeEntitySequence(d),
g.kern[d]={x:e}):w[h]&&(g.kern[w[h].code]={x:e}))},this);t.mixin(this,{family:d,name:m,style:q,variant:l,weight:u,stretch:n,range:s,viewbox:{width:h,height:h},origin:f,advance:t.mixin(g,{missing:{x:v,y:v}}),ascent:x,descent:y,baseline:r,glyphs:z});k._vectorFontCache[m]=this;k._vectorFontCache[b]=this;m!=d&&!k._vectorFontCache[d]&&(k._vectorFontCache[d]=this);k._svgFontCache[b]||(k._svgFontCache[b]=e)}},_clean:function(){var a=this.name,b=this.family;p.forEach("family name style variant weight stretch range viewbox origin advance ascent descent baseline glyphs".split(" "),
function(a){try{delete this[a]}catch(b){}},this);k._vectorFontCache[a]&&delete k._vectorFontCache[a];k._vectorFontCache[b]&&delete k._vectorFontCache[b];return this},constructor:function(a){this._defaultLeading=1.5;void 0!==a&&this.load(a)},load:function(a){this.onLoadBegin(a.toString());this._parse(k._svgFontCache[a.toString()]||F(a.toString()),a.toString());this.onLoad(this);return this},initialized:function(){return null!==this.glyphs},_round:function(a){return Math.round(1E3*a)/1E3},_leading:function(a){return this.viewbox.height*
(a||this._defaultLeading)},_normalize:function(a){return a.replace(/\s+/g,String.fromCharCode(32))},_getWidth:function(a){var b=0,e=0,f=null;p.forEach(a,function(c,h){e=c.xAdvance;a[h]&&(c.kern&&c.kern[a[h].code])&&(e+=c.kern[a[h].code].x);b+=e;f=c});f&&" "==f.code&&(b-=f.xAdvance);return this._round(b)},_getLongestLine:function(a){var b=0,e=0;p.forEach(a,function(a,c){var h=Math.max(b,this._getWidth(a));h>b&&(b=h,e=c)},this);return{width:b,index:e,line:a[e]}},_trim:function(a){var b=function(a){a.length&&
(" "==a[a.length-1].code&&a.splice(a.length-1,1),a.length&&" "==a[0].code&&a.splice(0,1))};t.isArray(a[0])?p.forEach(a,b):b(a);return a},_split:function(a,b){for(var e=this._getWidth(a),e=Math.floor(e/b),f=[],c=0,h=[],g=!1,d=0,k=a.length;d<k;d++){" "==a[d].code&&(g=!0);c+=a[d].xAdvance;d+1<k&&(a[d].kern&&a[d].kern[a[d+1].code])&&(c+=a[d].kern[a[d+1].code].x);if(c>=e){for(c=a[d];g&&" "!=c.code&&0<=d;)c=h.pop(),d--;f.push(h);h=[];c=0;g=!1}h.push(a[d])}h.length&&f.push(h);return this._trim(f)},_getSizeFactor:function(a){a+=
"";var b=E.getCachedFontMeasurements(),e=this.viewbox.height,f=b["1em"],f=parseFloat(a,10);if(-1<a.indexOf("em"))return this._round(b["1em"]*f/e);if(-1<a.indexOf("ex"))return this._round(b["1ex"]*f/e);if(-1<a.indexOf("pt"))return this._round(b["12pt"]/12*f/e);if(-1<a.indexOf("px"))return this._round(b["16px"]/16*f/e);if(-1<a.indexOf("%"))return this._round(b["1em"]*(f/100)/e);f=b[a]||b.medium;return this._round(f/e)},_getFitFactor:function(a,b,e,f){if(e){var c=this._getLongestLine(a).width;return this._round(Math.min(b/
c,e/(a.length*this.viewbox.height*f-(this.viewbox.height*f-this.viewbox.height))))}return this._round(b/this._getWidth(a))},_getBestFit:function(a,b,e,f){for(var c=32,h=0,g=c;0<c;){var d=this._getFitFactor(this._split(a,c),b,e,f);d>h&&(h=d,g=c);c--}return{scale:h,lines:this._split(a,g)}},_getBestFlow:function(a,b,e){for(var f=[],c=0,h=[],g=!1,d=0,k=a.length;d<k;d++){" "==a[d].code&&(g=!0);var l=a[d].xAdvance;d+1<k&&(a[d].kern&&a[d].kern[a[d+1].code])&&(l+=a[d].kern[a[d+1].code].x);c+=e*l;if(c>=b){for(c=
a[d];g&&" "!=c.code&&0<=d;)c=h.pop(),d--;f.push(h);h=[];c=0;g=!1}h.push(a[d])}h.length&&f.push(h);return this._trim(f)},getWidth:function(a,b){return this._getWidth(p.map(this._normalize(a).split(""),function(a){return this.glyphs[a]||{xAdvance:this.advance.missing.x}},this))*(b||1)},getLineHeight:function(a){return this.viewbox.height*(a||1)},getCenterline:function(a){return(a||1)*(this.viewbox.height/2)},getBaseline:function(a){return(a||1)*(this.viewbox.height+this.descent)},draw:function(a,b,
e,f,c){if(!this.initialized())throw Error("dojox.gfx.VectorFont.draw(): we have not been initialized yet.");var h=a.createGroup();if(b.x||b.y)a.applyTransform({dx:b.x||0,dy:b.y||0});var g=p.map(this._normalize(b.text).split(""),function(a){return this.glyphs[a]||{path:null,xAdvance:this.advance.missing.x}},this);a=e.size;var d=b.fitting,q=b.width,l=b.height;e=b.align;b=b.leading||this._defaultLeading;if(d&&(d==k.vectorFontFitting.FLOW&&!q||d==k.vectorFontFitting.FIT&&(!q||!l)))d=k.vectorFontFitting.NONE;
switch(d){case k.vectorFontFitting.FIT:g=this._getBestFit(g,q,l,b);a=g.scale;g=g.lines;break;case k.vectorFontFitting.FLOW:a=this._getSizeFactor(a);g=this._getBestFlow(g,q,a);break;default:a=this._getSizeFactor(a),g=[g]}for(var g=p.filter(g,function(a){return 0<a.length}),q=0,d=this._getLongestLine(g).width,l=0,t=g.length;l<t;l++){for(var n=0,s=g[l],x=this._getWidth(s),y=h.createGroup(),r=0;r<s.length;r++){var m=s[r];if(null!==m.path){var v=y.createPath(m.path).setFill(f);c&&v.setStroke(c);v.setTransform([u.flipY,
u.translate(n,-this.viewbox.height-this.descent)])}n+=m.xAdvance;r+1<s.length&&(m.kern&&m.kern[s[r+1].code])&&(n+=m.kern[s[r+1].code].x)}n=0;"middle"==e?n=d/2-x/2:"end"==e&&(n=d-x);y.setTransform({dx:n,dy:q});q+=this.viewbox.height*b}h.setTransform(u.scale(a));return h},onLoadBegin:function(a){},onLoad:function(a){}})});