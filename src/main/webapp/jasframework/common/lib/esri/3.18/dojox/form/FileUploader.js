//>>built
define("dojox/form/FileUploader","dojo/_base/kernel dojo/_base/declare dojo/_base/lang dojo/_base/array dojo/_base/connect dojo/_base/window dojo/_base/sniff dojo/query dojo/dom dojo/dom-style dojo/dom-geometry dojo/dom-attr dojo/dom-class dojo/dom-construct dojo/dom-form dojo/_base/config dijit/_base/manager dojo/io/iframe dojo/_base/Color dojo/_base/unload dijit/_Widget dijit/_TemplatedMixin dijit/_Contained dojox/embed/Flash dojox/embed/flashVars dojox/html/styles".split(" "),function(v,w,h,k,
d,x,n,p,r,c,q,l,f,g,y,z,m,A,s,B,C,D,E,t,u,F){v.deprecated("dojox.form.FileUploader","Use dojox.form.Uploader","2.0");return w("dojox.form.FileUploader",[C,D,E],{swfPath:z.uploaderPath||require.toUrl("dojox/form/resources/fileuploader.swf"),templateString:'\x3cdiv\x3e\x3cdiv dojoAttachPoint\x3d"progNode"\x3e\x3cdiv dojoAttachPoint\x3d"progTextNode"\x3e\x3c/div\x3e\x3c/div\x3e\x3cdiv dojoAttachPoint\x3d"insideNode" class\x3d"uploaderInsideNode"\x3e\x3c/div\x3e\x3c/div\x3e',uploadUrl:"",isDebug:!1,devMode:!1,
baseClass:"dojoxUploaderNorm",hoverClass:"dojoxUploaderHover",activeClass:"dojoxUploaderActive",disabledClass:"dojoxUploaderDisabled",force:"",uploaderType:"",flashObject:null,flashMovie:null,insideNode:null,deferredUploading:1,fileListId:"",uploadOnChange:!1,selectMultipleFiles:!0,htmlFieldName:"uploadedfile",flashFieldName:"flashUploadFiles",fileMask:null,minFlashVersion:9,tabIndex:-1,showProgress:!1,progressMessage:"Loading",progressBackgroundUrl:require.toUrl("dijit/themes/tundra/images/buttonActive.png"),
progressBackgroundColor:"#ededed",progressWidgetId:"",skipServerCheck:!1,serverTimeout:5E3,log:function(){this.isDebug&&console.log(Array.prototype.slice.call(arguments).join(" "))},constructor:function(){this._subs=[]},postMixInProperties:function(){this.fileList=[];this._cons=[];this.fileMask=this.fileMask||[];this.fileInputs=[];this.fileCount=0;this._disabled=this.flashReady=!1;this.force=this.force.toLowerCase();this.uploaderType=(t.available>=this.minFlashVersion||"flash"==this.force)&&"html"!=
this.force?"flash":"html";this.deferredUploading=!0===this.deferredUploading?1:this.deferredUploading;this._refNode=this.srcNodeRef;this.getButtonStyle()},startup:function(){},postCreate:function(){this.inherited(arguments);this.setButtonStyle();var a;"flash"==this.uploaderType?a="createFlashUploader":(this.uploaderType="html",a="createHtmlUploader");this[a]();this.fileListId&&this.connect(r.byId(this.fileListId),"click",function(a){a=a.target.parentNode.parentNode.parentNode;a.id&&-1<a.id.indexOf("file_")&&
this.removeFile(a.id.split("file_")[1])});B.addOnUnload(this,this.destroy)},getHiddenNode:function(a){if(!a)return null;var b=null;for(a=a.parentNode;a&&"body"!=a.tagName.toLowerCase();){if("none"==c.get(a,"display")){b=a;break}a=a.parentNode}return b},getButtonStyle:function(){var a=this.srcNodeRef;(this._hiddenNode=this.getHiddenNode(a))&&c.set(this._hiddenNode,"display","block");if(!a&&this.button&&this.button.domNode){var b=!0,a=this.button.domNode.className+" dijitButtonNode",e=this.getText(p(".dijitButtonText",
this.button.domNode)[0]);this.srcNodeRef=a=g.place('\x3cbutton id\x3d"'+this.button.id+'" class\x3d"'+a+'"\x3e'+e+"\x3c/button\x3e",this.button.domNode,"after");this.button.destroy();this.baseClass="dijitButton";this.hoverClass="dijitButtonHover";this.pressClass="dijitButtonActive";this.disabledClass="dijitButtonDisabled"}else!this.srcNodeRef&&this.button&&(a=this.button);l.get(a,"class")&&(this.baseClass+=" "+l.get(a,"class"));l.set(a,"class",this.baseClass);this.norm=this.getStyle(a);this.width=
this.norm.w;this.height=this.norm.h;"flash"==this.uploaderType?(this.over=this.getTempNodeStyle(a,this.baseClass+" "+this.hoverClass,b),this.down=this.getTempNodeStyle(a,this.baseClass+" "+this.activeClass,b),this.dsbl=this.getTempNodeStyle(a,this.baseClass+" "+this.disabledClass,b),this.fhtml={cn:this.getText(a),nr:this.norm,ov:this.over,dn:this.down,ds:this.dsbl}):(this.fhtml={cn:this.getText(a),nr:this.norm},"middle"==this.norm.va&&(this.norm.lh=this.norm.h));this.devMode&&(this.log("classes - base:",
this.baseClass," hover:",this.hoverClass,"active:",this.activeClass),this.log("fhtml:",this.fhtml),this.log("norm:",this.norm),this.log("over:",this.over),this.log("down:",this.down))},setButtonStyle:function(){c.set(this.domNode,{width:this.fhtml.nr.w+"px",height:this.fhtml.nr.h+"px",padding:"0px",lineHeight:"normal",position:"relative"});"html"==this.uploaderType&&"middle"==this.norm.va&&c.set(this.domNode,"lineHeight",this.norm.lh+"px");this.showProgress?(this.progTextNode.innerHTML=this.progressMessage,
c.set(this.progTextNode,{width:this.fhtml.nr.w+"px",height:this.fhtml.nr.h+0+"px",padding:"0px",margin:"0px",left:"0px",lineHeight:this.fhtml.nr.h+0+"px",position:"absolute"}),c.set(this.progNode,{width:this.fhtml.nr.w+"px",height:this.fhtml.nr.h+0+"px",padding:"0px",margin:"0px",left:"0px",position:"absolute",display:"none",backgroundImage:"url("+this.progressBackgroundUrl+")",backgroundPosition:"bottom",backgroundRepeat:"repeat-x",backgroundColor:this.progressBackgroundColor})):g.destroy(this.progNode);
c.set(this.insideNode,{position:"absolute",top:"0px",left:"0px",display:""});f.add(this.domNode,this.srcNodeRef.className);-1<this.fhtml.nr.d.indexOf("inline")&&f.add(this.domNode,"dijitInline");try{this.insideNode.innerHTML=this.fhtml.cn}catch(a){if("flash"==this.uploaderType){this.insideNode=this.insideNode.parentNode.removeChild(this.insideNode);x.body().appendChild(this.insideNode);this.insideNode.innerHTML=this.fhtml.cn;var b=d.connect(this,"onReady",this,function(){d.disconnect(b);this.insideNode=
this.insideNode.parentNode.removeChild(this.insideNode);this.domNode.appendChild(this.insideNode)})}else this.insideNode.appendChild(document.createTextNode(this.fhtml.cn))}this._hiddenNode&&c.set(this._hiddenNode,"display","none")},onChange:function(a){},onProgress:function(a){},onComplete:function(a){},onCancel:function(){},onError:function(a){},onReady:function(a){},onLoad:function(a){},submit:function(a){a=a?y.toObject(a):null;this.upload(a);return!1},upload:function(a){if(!this.fileList.length)return!1;
if(!this.uploadUrl)return console.warn("uploadUrl not provided. Aborting."),!1;this.showProgress||this.set("disabled",!0);if(this.progressWidgetId){var b=m.byId(this.progressWidgetId).domNode;"none"==c.get(b,"display")&&(this.restoreProgDisplay="none",c.set(b,"display","block"));"hidden"==c.get(b,"visibility")&&(this.restoreProgDisplay="hidden",c.set(b,"visibility","visible"))}a&&!a.target&&(this.postData=a);this.log("upload type:",this.uploaderType," - postData:",this.postData);for(a=0;a<this.fileList.length;a++)b=
this.fileList[a],b.bytesLoaded=0,b.bytesTotal=b.size||1E5,b.percent=0;"flash"==this.uploaderType?this.uploadFlash():this.uploadHTML();return!1},removeFile:function(a,b){var e;for(e=0;e<this.fileList.length;e++)if(this.fileList[e].name==a){b||this.fileList.splice(e,1);break}"flash"==this.uploaderType?this.flashMovie.removeFile(a):b||(g.destroy(this.fileInputs[e]),this.fileInputs.splice(e,1),this._renumberInputs());this.fileListId&&g.destroy("file_"+a)},destroy:function(){"flash"==this.uploaderType&&
!this.flashMovie?this._cons.push(d.connect(this,"onLoad",this,"destroy")):(k.forEach(this._subs,d.unsubscribe,dojo),k.forEach(this._cons,d.disconnect,dojo),this.scrollConnect&&d.disconnect(this.scrollConnect),"flash"==this.uploaderType?(this.flashObject.destroy(),delete this.flashObject):(g.destroy(this._fileInput),g.destroy(this._formNode)),this.inherited(arguments))},_displayProgress:function(a){!0===a?("flash"==this.uploaderType?c.set(this.insideNode,"top","-2500px"):c.set(this.insideNode,"display",
"none"),c.set(this.progNode,"display","")):!1===a?(c.set(this.insideNode,{display:"",top:"0"}),c.set(this.progNode,"display","none")):c.set(this.progNode,"width",a*this.fhtml.nr.w+"px")},_animateProgress:function(){this._displayProgress(!0);var a=!1,b=d.connect(this,"_complete",function(){d.disconnect(b);a=!0}),e=0,c=setInterval(h.hitch(this,function(){e+=5;e>this.fhtml.nr.w&&(e=0,a=!0);this._displayProgress(e/this.fhtml.nr.w);a&&(clearInterval(c),setTimeout(h.hitch(this,function(){this._displayProgress(!1)}),
500))}),50)},_error:function(a){"string"==typeof a&&(a=Error(a));this.onError(a)},_addToFileList:function(){if(this.fileListId){var a="";k.forEach(this.fileList,function(b){a+='\x3ctable id\x3d"file_'+b.name+'" class\x3d"fileToUpload"\x3e\x3ctr\x3e\x3ctd class\x3d"fileToUploadClose"\x3e\x3c/td\x3e\x3ctd class\x3d"fileToUploadName"\x3e'+b.name+'\x3c/td\x3e\x3ctd class\x3d"fileToUploadSize"\x3e'+(b.size?Math.ceil(0.0010*b.size)+"kb":"")+"\x3c/td\x3e\x3c/tr\x3e\x3c/table\x3e"},this);r.byId(this.fileListId).innerHTML=
a}},_change:function(a){n("ie")&&k.forEach(a,function(a){a.name=a.name.split("\\")[a.name.split("\\").length-1]});this.selectMultipleFiles?this.fileList=this.fileList.concat(a):(this.fileList[0]&&this.removeFile(this.fileList[0].name,!0),this.fileList=a);this._addToFileList();this.onChange(a);this.uploadOnChange?("html"==this.uploaderType&&this._buildFileInput(),this.upload()):"html"==this.uploaderType&&this.selectMultipleFiles&&(this._buildFileInput(),this._connectInput())},_complete:function(a){a=
h.isArray(a)?a:[a];k.forEach(a,function(a){a.ERROR&&this._error(a.ERROR)},this);k.forEach(this.fileList,function(a){a.bytesLoaded=1;a.bytesTotal=1;a.percent=100;this._progress(a)},this);k.forEach(this.fileList,function(a){this.removeFile(a.name,!0)},this);this.onComplete(a);this.fileList=[];this._resetHTML();this.set("disabled",!1);this.restoreProgDisplay&&setTimeout(h.hitch(this,function(){c.set(m.byId(this.progressWidgetId).domNode,"none"==this.restoreProgDisplay?"display":"visibility",this.restoreProgDisplay)}),
500)},_progress:function(a){for(var b=0,e=0,c=0;c<this.fileList.length;c++){var d=this.fileList[c];d.name==a.name&&(d.bytesLoaded=a.bytesLoaded,d.bytesTotal=a.bytesTotal,d.percent=Math.ceil(100*(d.bytesLoaded/d.bytesTotal)),this.log(d.name,"percent:",d.percent));e+=Math.ceil(0.0010*d.bytesLoaded);b+=Math.ceil(0.0010*d.bytesTotal)}a=Math.ceil(100*(e/b));this.progressWidgetId&&m.byId(this.progressWidgetId).update({progress:a+"%"});this.showProgress&&this._displayProgress(0.01*a);this.onProgress(this.fileList)},
_getDisabledAttr:function(){return this._disabled},_setDisabledAttr:function(a){if(this._disabled!=a){if("flash"==this.uploaderType){if(!this.flashReady){var b=d.connect(this,"onLoad",this,function(){d.disconnect(b);this._setDisabledAttr(a)});return}this._disabled=a;this.flashMovie.doDisable(a)}else this._disabled=a,c.set(this._fileInput,"display",this._disabled?"none":"");f.toggle(this.domNode,this.disabledClass,a)}},_onFlashBlur:function(){this.flashMovie.blur();if(!this.nextFocusObject&&this.tabIndex)for(var a=
p("[tabIndex]"),b=0;b<a.length;b++)if(a[b].tabIndex>=Number(this.tabIndex)+1){this.nextFocusObject=a[b];break}this.nextFocusObject.focus()},_disconnect:function(){k.forEach(this._cons,d.disconnect,dojo)},uploadHTML:function(){this.selectMultipleFiles&&g.destroy(this._fileInput);this._setHtmlPostData();this.showProgress&&this._animateProgress();A.send({url:this.uploadUrl.toString(),form:this._formNode,handleAs:"json",error:h.hitch(this,function(a){this._error("HTML Upload Error:"+a.message)}),load:h.hitch(this,
function(a,b,e){this._complete(a)})})},createHtmlUploader:function(){this._buildForm();this._setFormStyle();this._buildFileInput();this._connectInput();this._styleContent();c.set(this.insideNode,"visibility","visible");this.onReady()},_connectInput:function(){this._disconnect();this._cons.push(d.connect(this._fileInput,"mouseover",this,function(a){f.add(this.domNode,this.hoverClass);this.onMouseOver(a)}));this._cons.push(d.connect(this._fileInput,"mouseout",this,function(a){setTimeout(h.hitch(this,
function(){f.remove(this.domNode,this.activeClass);f.remove(this.domNode,this.hoverClass);this.onMouseOut(a);this._checkHtmlCancel("off")}),0)}));this._cons.push(d.connect(this._fileInput,"mousedown",this,function(a){f.add(this.domNode,this.activeClass);f.remove(this.domNode,this.hoverClass);this.onMouseDown(a)}));this._cons.push(d.connect(this._fileInput,"mouseup",this,function(a){f.remove(this.domNode,this.activeClass);this.onMouseUp(a);this.onClick(a);this._checkHtmlCancel("up")}));this._cons.push(d.connect(this._fileInput,
"change",this,function(){this._checkHtmlCancel("change");var a=this._fileInput.value;a?this._change([{name:a,type:"",size:0}]):this._change([])}));0<=this.tabIndex&&l.set(this.domNode,"tabIndex",this.tabIndex)},_checkHtmlCancel:function(a){"change"==a&&(this.dialogIsOpen=!1);"up"==a&&(this.dialogIsOpen=!0);if("off"==a){if(this.dialogIsOpen)this.onCancel();this.dialogIsOpen=!1}},_styleContent:function(){var a=this.fhtml.nr;c.set(this.insideNode,{width:a.w+"px",height:"middle"==a.va?a.h+"px":"auto",
textAlign:a.ta,paddingTop:a.p[0]+"px",paddingRight:a.p[1]+"px",paddingBottom:a.p[2]+"px",paddingLeft:a.p[3]+"px"});try{c.set(this.insideNode,"lineHeight","inherit")}catch(b){}},_resetHTML:function(){"html"==this.uploaderType&&this._formNode&&(this.fileInputs=[],p("*",this._formNode).forEach(function(a){g.destroy(a)}),this.fileCount=0,this._buildFileInput(),this._connectInput())},_buildForm:function(){this._formNode||(9>n("ie")||n("ie")&&n("quirks")?(this._formNode=document.createElement('\x3cform enctype\x3d"multipart/form-data" method\x3d"post"\x3e'),
this._formNode.encoding="multipart/form-data",this._formNode.id=m.getUniqueId("FileUploaderForm"),this.domNode.appendChild(this._formNode)):this._formNode=g.create("form",{enctype:"multipart/form-data",method:"post",id:m.getUniqueId("FileUploaderForm")},this.domNode))},_buildFileInput:function(){this._fileInput&&(this._disconnect(),this._fileInput.id+=this.fileCount,c.set(this._fileInput,"display","none"));this._fileInput=document.createElement("input");this.fileInputs.push(this._fileInput);var a=
this.htmlFieldName;this.selectMultipleFiles&&(a+=this.fileCount,this.fileCount++);l.set(this._fileInput,{id:this.id,name:a,type:"file"});f.add(this._fileInput,"dijitFileInputReal");this._formNode.appendChild(this._fileInput);a=q.getMarginBox(this._fileInput);c.set(this._fileInput,{position:"relative",left:this.fhtml.nr.w-a.w+"px",opacity:0})},_renumberInputs:function(){if(this.selectMultipleFiles){var a;this.fileCount=0;k.forEach(this.fileInputs,function(b){a=this.htmlFieldName+this.fileCount;this.fileCount++;
l.set(b,"name",a)},this)}},_setFormStyle:function(){var a=Math.max(2,Math.max(Math.ceil(this.fhtml.nr.w/60),Math.ceil(this.fhtml.nr.h/15)));F.insertCssRule("#"+this._formNode.id+" input","font-size:"+a+"em");c.set(this.domNode,{overflow:"hidden",position:"relative"});c.set(this.insideNode,"position","absolute")},_setHtmlPostData:function(){if(this.postData)for(var a in this.postData)g.create("input",{type:"hidden",name:a,value:this.postData[a]},this._formNode)},uploadFlash:function(){try{if(this.showProgress){this._displayProgress(!0);
var a=d.connect(this,"_complete",this,function(){d.disconnect(a);this._displayProgress(!1)})}var b={},e;for(e in this.postData)b[e]=this.postData[e];this.flashMovie.doUpload(b)}catch(c){this._error("FileUploader - Sorry, the SWF failed to initialize."+c)}},createFlashUploader:function(){if(this.uploadUrl=this.uploadUrl.toString())if(0>this.uploadUrl.toLowerCase().indexOf("http")&&0!=this.uploadUrl.indexOf("/")){var a=window.location.href.split("/");a.pop();a=a.join("/")+"/";this.uploadUrl=a+this.uploadUrl;
this.log("SWF Fixed - Relative loc:",a," abs loc:",this.uploadUrl)}else this.log("SWF URL unmodified:",this.uploadUrl);else console.warn("Warning: no uploadUrl provided.");a={expressInstall:!0,path:this.swfPath.uri||this.swfPath,width:this.fhtml.nr.w,height:this.fhtml.nr.h,allowScriptAccess:"always",allowNetworking:"all",vars:{uploadDataFieldName:this.flashFieldName,uploadUrl:this.uploadUrl,uploadOnSelect:this.uploadOnChange,deferredUploading:this.deferredUploading||0,selectMultipleFiles:this.selectMultipleFiles,
id:this.id,isDebug:this.isDebug,devMode:this.devMode,flashButton:u.serialize("fh",this.fhtml),fileMask:u.serialize("fm",this.fileMask),noReturnCheck:this.skipServerCheck,serverTimeout:this.serverTimeout},params:{scale:"noscale",wmode:"opaque",allowScriptAccess:"always",allowNetworking:"all"}};this.flashObject=new t(a,this.insideNode);this.flashObject.onError=h.hitch(function(a){this._error("Flash Error: "+a)});this.flashObject.onReady=h.hitch(this,function(){c.set(this.insideNode,"visibility","visible");
this.log("FileUploader flash object ready");this.onReady(this)});this.flashObject.onLoad=h.hitch(this,function(a){this.flashMovie=a;this.flashReady=!0;this.onLoad(this)});this._connectFlash()},_connectFlash:function(){this._doSub("/filesSelected","_change");this._doSub("/filesUploaded","_complete");this._doSub("/filesProgress","_progress");this._doSub("/filesError","_error");this._doSub("/filesCanceled","onCancel");this._doSub("/stageBlur","_onFlashBlur");this._doSub("/up","onMouseUp");this._doSub("/down",
"onMouseDown");this._doSub("/over","onMouseOver");this._doSub("/out","onMouseOut");this.connect(this.domNode,"focus",function(){this.flashMovie.focus();this.flashMovie.doFocus()});0<=this.tabIndex&&l.set(this.domNode,"tabIndex",this.tabIndex)},_doSub:function(a,b){this._subs.push(d.subscribe(this.id+a,this,b))},urlencode:function(a){return!a||"none"==a?!1:a.replace(/:/g,"||").replace(/\./g,"^^").replace("url(","").replace(")","").replace(/'/g,"").replace(/"/g,"")},isButton:function(a){a=a.tagName.toLowerCase();
return"button"==a||"input"==a},getTextStyle:function(a){var b={};b.ff=c.get(a,"fontFamily");if(b.ff){b.ff=b.ff.replace(", ",",");b.ff=b.ff.replace(/\"|\'/g,"");b.ff="sans-serif"==b.ff?"Arial":b.ff;b.fw=c.get(a,"fontWeight");b.fi=c.get(a,"fontStyle");b.fs=parseInt(c.get(a,"fontSize"),10);if(-1<c.get(a,"fontSize").indexOf("%"))for(var e=a;e.tagName;){if(-1==c.get(e,"fontSize").indexOf("%")){b.fs=parseInt(c.get(e,"fontSize"),10);break}"body"==e.tagName.toLowerCase()&&(b.fs=0.16*parseInt(c.get(e,"fontSize"),
10));e=e.parentNode}b.fc=(new s(c.get(a,"color"))).toHex();b.fc=parseInt(b.fc.substring(1,Infinity),16)}b.lh=c.get(a,"lineHeight");b.ta=c.get(a,"textAlign");b.ta="start"==b.ta||!b.ta?"left":b.ta;b.va=this.isButton(a)?"middle":b.lh==b.h?"middle":c.get(a,"verticalAlign");return b},getText:function(a){a=h.trim(a.innerHTML);-1<a.indexOf("\x3c")&&(a=escape(a));return a},getStyle:function(a){var b={},e=q.getContentBox(a),d=q.getPadExtents(a);b.p=[d.t,d.w-d.l,d.h-d.t,d.l];b.w=e.w+d.w;b.h=e.h+d.h;b.d=c.get(a,
"display");e=new s(c.get(a,"backgroundColor"));b.bc=0==e.a?"#ffffff":e.toHex();b.bc=parseInt(b.bc.substring(1,Infinity),16);if(e=this.urlencode(c.get(a,"backgroundImage")))b.bi={url:e,rp:c.get(a,"backgroundRepeat"),pos:escape(c.get(a,"backgroundPosition"))},b.bi.pos||(e=c.get(a,"backgroundPositionX"),d=c.get(a,"backgroundPositionY"),b.bi.pos=escape(("left"==e?"0%":"right"==e?"100%":e)+" "+("top"==d?"0%":"bottom"==d?"100%":d)));return h.mixin(b,this.getTextStyle(a))},getTempNodeStyle:function(a,b,
c){if(c){c=g.place("\x3c"+a.tagName+"\x3e\x3cspan\x3e"+a.innerHTML+"\x3c/span\x3e\x3c/"+a.tagName+"\x3e",a.parentNode);var d=c.firstChild;f.add(d,a.className);f.add(c,b);a=this.getStyle(d)}else c=g.place("\x3c"+a.tagName+"\x3e"+a.innerHTML+"\x3c/"+a.tagName+"\x3e",a.parentNode),f.add(c,a.className),f.add(c,b),c.id=a.id,a=this.getStyle(c);g.destroy(c);return a}})});