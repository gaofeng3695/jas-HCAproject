// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.18/esri/copyright.txt for details.
//>>built
define("esri/dijit/geoenrichment/DataBrowser","../../declare dojo/dom-construct ./DataBrowser/DataBrowserBase ./DataBrowser/VariableStore ./DataBrowser/_GeoenrichmentVariables ./DataBrowser/DataBrowserContentFactory ./ProgressHandler".split(" "),function(a,b,c,d,e,f,g){return a("esri.dijit.geoenrichment.DataBrowser",c,{_progressHandler:null,postCreate:function(){this.variables||(this.variables=new (a([d,e])));this._contentFactory||(this._contentFactory=new f);this._progressHandler||(this._progressHandler=
new g(b.create("div",{},this.domNode)));this.own(this._progressHandler);this.inherited(arguments)},showProgress:function(a){return this._progressHandler.showProgress(a)}})});