//>>built
(function(b,c){"object"===typeof exports&&"undefined"!==typeof module&&"function"===typeof require?c(require("../moment")):"function"===typeof define&&define.amd?define("moment/locale/fy",["../moment"],c):c(b.moment)})(this,function(b){var c="jan. feb. mrt. apr. mai jun. jul. aug. sep. okt. nov. des.".split(" "),d="jan feb mrt apr mai jun jul aug sep okt nov des".split(" ");return b.defineLocale("fy",{months:"jannewaris febrewaris maart april maaie juny july augustus septimber oktober novimber desimber".split(" "),
monthsShort:function(a,b){return/-MMM-/.test(b)?d[a.month()]:c[a.month()]},monthsParseExact:!0,weekdays:"snein moandei tiisdei woansdei tongersdei freed sneon".split(" "),weekdaysShort:"si. mo. ti. wo. to. fr. so.".split(" "),weekdaysMin:"Si Mo Ti Wo To Fr So".split(" "),weekdaysParseExact:!0,longDateFormat:{LT:"HH:mm",LTS:"HH:mm:ss",L:"DD-MM-YYYY",LL:"D MMMM YYYY",LLL:"D MMMM YYYY HH:mm",LLLL:"dddd D MMMM YYYY HH:mm"},calendar:{sameDay:"[hjoed om] LT",nextDay:"[moarn om] LT",nextWeek:"dddd [om] LT",
lastDay:"[juster om] LT",lastWeek:"[\u00f4fr\u00fbne] dddd [om] LT",sameElse:"L"},relativeTime:{future:"oer %s",past:"%s lyn",s:"in pear sekonden",m:"ien min\u00fat",mm:"%d minuten",h:"ien oere",hh:"%d oeren",d:"ien dei",dd:"%d dagen",M:"ien moanne",MM:"%d moannen",y:"ien jier",yy:"%d jierren"},ordinalParse:/\d{1,2}(ste|de)/,ordinal:function(a){return a+(1===a||8===a||20<=a?"ste":"de")},week:{dow:1,doy:4}})});