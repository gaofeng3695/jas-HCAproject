//>>built
define("dojox/lang/observable",["dojo","dijit","dojox"],function(b,m,e){b.provide("dojox.lang.observable");b.experimental("dojox.lang.observable");e.lang.observable=function(b,k,g,h){return e.lang.makeObservable(k,g,h)(b)};e.lang.makeObservable=function(b,k,g,h){function m(c,d,a){return function(){return g(c,d,a,arguments)}}h=h||{};g=g||function(c,d,a,b){return d[a].apply(c,b)};if(e.lang.lettableWin){var n=e.lang.makeObservable;n.inc=(n.inc||0)+1;var r="gettable_"+n.inc;e.lang.lettableWin[r]=b;var s=
"settable_"+n.inc;e.lang.lettableWin[s]=k;var t={};return function(c){if(c.__observable)return c.__observable;if(c.data__)throw Error("Can wrap an object that is already wrapped");var d=[],a,b;for(a in h)d.push(a);b={type:1,event:1};for(a in c)a.match(/^[a-zA-Z][\w\$_]*$/)&&(!(a in h)&&!(a in b))&&d.push(a);var l=d.join(","),f;a=t[l];if(!a){var k="dj_lettable_"+n.inc++,g=k+"_dj_getter",p=["Class "+k,"\tPublic data__"];a=0;for(b=d.length;a<b;a++){f=d[a];var q=typeof c[f];"function"==q||h[f]?p.push("  Public "+
f):"object"!=q&&p.push("\tPublic Property Let "+f+"(val)","\t\tCall "+s+'(me.data__,"'+f+'",val)',"\tEnd Property","\tPublic Property Get "+f,"\t\t"+f+" \x3d "+r+'(me.data__,"'+f+'")',"\tEnd Property")}p.push("End Class");p.push("Function "+g+"()","\tDim tmp","\tSet tmp \x3d New "+k,"\tSet "+g+" \x3d tmp","End Function");e.lang.lettableWin.vbEval(p.join("\n"));t[l]=a=function(){return e.lang.lettableWin.construct(g)}}console.log("starting5");l=a();l.data__=c;console.log("starting6");try{c.__observable=
l}catch(w){}a=0;for(b=d.length;a<b;a++){f=d[a];try{var u=c[f]}catch(v){console.log("error ",f,v)}if("function"==typeof u||h[f])l[f]=m(l,c,f)}return l}}return function(c){if(c.__observable)return c.__observable;var d=c instanceof Array?[]:{};d.data__=c;for(var a in c)"_"!=a.charAt(0)&&("function"==typeof c[a]?d[a]=m(d,c,a):"object"!=typeof c[a]&&function(a){d.__defineGetter__(a,function(){return b(c,a)});d.__defineSetter__(a,function(b){return k(c,a,b)})}(a));for(a in h)d[a]=m(d,c,a);return c.__observable=
d}};if(!{}.__defineGetter__)if(b.isIE)document.body?(b=document.createElement("iframe"),document.body.appendChild(b)):(document.write("\x3ciframe id\x3d'dj_vb_eval_frame'\x3e\x3c/iframe\x3e"),b=document.getElementById("dj_vb_eval_frame")),b.style.display="none",m=b.contentWindow.document,e.lang.lettableWin=b.contentWindow,m.write('\x3chtml\x3e\x3chead\x3e\x3cscript language\x3d"VBScript" type\x3d"text/VBScript"\x3eFunction vb_global_eval(code)ExecuteGlobal(code)End Function\x3c/script\x3e\x3cscript type\x3d"text/javascript"\x3efunction vbEval(code){ \nreturn vb_global_eval(code);}function construct(name){ \nreturn window[name]();}\x3c/script\x3e\x3c/head\x3e\x3cbody\x3evb-eval\x3c/body\x3e\x3c/html\x3e'),
m.close();else throw Error("This browser does not support getters and setters");e.lang.ReadOnlyProxy=e.lang.makeObservable(function(b,e){return b[e]},function(b,e,g){})});