// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.18/esri/copyright.txt for details.
//>>built
require({cache:{"url:esri/dijit/metadata/types/arcgis/keywords/templates/TopicCategory.html":'\x3cdiv data-dojo-attach-point\x3d"containerNode"\x3e\r\n\r\n  \x3c!-- topic category --\x3e\r\n  \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/OpenElement"\r\n    data-dojo-props\x3d"target:\'tpCat\',minOccurs:1,maxOccurs:\'unbounded\',label:\'${i18nArcGIS.codelist.MD_TopicCategoryCode}\'"\x3e\r\n    \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/InputSelectMany"\r\n      data-dojo-props\x3d"subTarget:\'TopicCatCd@value\'"\x3e\r\n      \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Options"\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.001}\',value:\'001\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.002}\',value:\'002\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.003}\',value:\'003\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.004}\',value:\'004\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.005}\',value:\'005\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.006}\',value:\'006\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.007}\',value:\'007\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.008}\',value:\'008\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.009}\',value:\'009\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.010}\',value:\'010\'"\x3e\x3c/div\x3e\r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.011}\',value:\'011\'"\x3e\x3c/div\x3e    \r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.012}\',value:\'012\'"\x3e\x3c/div\x3e  \r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.013}\',value:\'013\'"\x3e\x3c/div\x3e  \r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.014}\',value:\'014\'"\x3e\x3c/div\x3e  \r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.015}\',value:\'015\'"\x3e\x3c/div\x3e                \r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.016}\',value:\'016\'"\x3e\x3c/div\x3e  \r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.017}\',value:\'017\'"\x3e\x3c/div\x3e  \r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.018}\',value:\'018\'"\x3e\x3c/div\x3e  \r\n        \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Option" \r\n          data-dojo-props\x3d"label:\'${i18nTopicCat.019}\',value:\'019\'"\x3e\x3c/div\x3e            \r\n      \x3c/div\x3e\r\n    \x3c/div\x3e      \r\n  \x3c/div\x3e\r\n\r\n\x3c/div\x3e'}});
define("esri/dijit/metadata/types/arcgis/keywords/TopicCategory","dojo/_base/declare dojo/_base/lang dojo/has ../../../../../kernel ../../../base/Descriptor dojo/text!./templates/TopicCategory.html dojo/i18n!../../../nls/i18nCodelists".split(" "),function(a,c,d,e,f,g,b){a=a(f,{i18nCodelists:b,i18nTopicCat:b.MD_TopicCategoryCode,templateString:g});d("extend-esri")&&c.setObject("dijit.metadata.types.arcgis.keywords.TopicCategory",a,e);return a});