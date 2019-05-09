// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.18/esri/copyright.txt for details.
//>>built
require({cache:{"url:esri/dijit/metadata/types/arcgis/resource/templates/Description.html":'\x3cdiv data-dojo-attach-point\x3d"containerNode"\x3e\r\n\r\n  \x3c!-- abstract --\x3e\r\n  \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/OpenElement"\r\n    data-dojo-props\x3d"target:\'idAbs\',minOccurs:1,label:\'${i18nArcGIS.resource.idAbs}\'"\x3e\r\n    \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/types/arcgis/form/InputHtmlArea"\x3e\x3c/div\x3e\r\n  \x3c/div\x3e\r\n  \r\n  \x3c!-- purpose --\x3e\r\n  \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/OpenElement"\r\n    data-dojo-props\x3d"target:\'idPurp\',minOccurs:0,label:\'${i18nArcGIS.resource.idPurp}\'"\x3e\r\n    \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/InputTextArea"\x3e\x3c/div\x3e\r\n  \x3c/div\x3e\r\n  \r\n  \x3c!-- supplemental --\x3e\r\n  \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/OpenElement"\r\n    data-dojo-props\x3d"target:\'suppInfo\',minOccurs:0,label:\'${i18nArcGIS.resource.suppInfo}\'"\x3e\r\n    \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/InputTextArea"\x3e\x3c/div\x3e\r\n  \x3c/div\x3e\r\n  \r\n  \x3c!-- credits --\x3e\r\n  \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/OpenElement"\r\n    data-dojo-props\x3d"target:\'idCredit\',minOccurs:0,maxOccurs:\'unbounded\',label:\'${i18nArcGIS.resource.idCredit}\'"\x3e\r\n    \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/InputTextArea"\x3e\x3c/div\x3e\r\n  \x3c/div\x3e\r\n  \r\n  \x3c!-- processing environment --\x3e\r\n  \x3cdiv style\x3d"margin-top:8px"\x3e\x3c/div\x3e\r\n  \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/OpenElement"\r\n    data-dojo-props\x3d"target:\'envirDesc\',minOccurs:0,label:\'${i18nArcGIS.resource.envirDesc}\'"\x3e\r\n  \x3c/div\x3e\r\n  \r\n\x3c/div\x3e'}});
define("esri/dijit/metadata/types/arcgis/resource/Description","dojo/_base/declare dojo/_base/lang dojo/has ../../../../../kernel ../../../base/Descriptor dojo/text!./templates/Description.html ../common/LanguageCode ../common/CountryCode ../common/CharSetCd".split(" "),function(a,b,c,d,e,f){a=a(e,{templateString:f});c("extend-esri")&&b.setObject("dijit.metadata.types.arcgis.resource.Description",a,d);return a});