//>>built
(function(b,c){"object"===typeof exports&&"undefined"!==typeof module&&"function"===typeof require?c(require("../moment")):"function"===typeof define&&define.amd?define("moment/locale/nl",["../moment"],c):c(b.moment)})(this,function(b){var c="jan. feb. mrt. apr. mei jun. jul. aug. sep. okt. nov. dec.".split(" "),d="jan feb mrt apr mei jun jul aug sep okt nov dec".split(" ");return b.defineLocale("nl",{months:"januari februari maart april mei juni juli augustus september oktober november december".split(" "),
monthsShort:function(a,b){return/-MMM-/.test(b)?d[a.month()]:c[a.month()]},monthsParseExact:!0,weekdays:"zondag maandag dinsdag woensdag donderdag vrijdag zaterdag".split(" "),weekdaysShort:"zo. ma. di. wo. do. vr. za.".split(" "),weekdaysMin:"Zo Ma Di Wo Do Vr Za".split(" "),weekdaysParseExact:!0,longDateFormat:{LT:"HH:mm",LTS:"HH:mm:ss",L:"DD-MM-YYYY",LL:"D MMMM YYYY",LLL:"D MMMM YYYY HH:mm",LLLL:"dddd D MMMM YYYY HH:mm"},calendar:{sameDay:"[vandaag om] LT",nextDay:"[morgen om] LT",nextWeek:"dddd [om] LT",
lastDay:"[gisteren om] LT",lastWeek:"[afgelopen] dddd [om] LT",sameElse:"L"},relativeTime:{future:"over %s",past:"%s geleden",s:"een paar seconden",m:"\u00e9\u00e9n minuut",mm:"%d minuten",h:"\u00e9\u00e9n uur",hh:"%d uur",d:"\u00e9\u00e9n dag",dd:"%d dagen",M:"\u00e9\u00e9n maand",MM:"%d maanden",y:"\u00e9\u00e9n jaar",yy:"%d jaar"},ordinalParse:/\d{1,2}(ste|de)/,ordinal:function(a){return a+(1===a||8===a||20<=a?"ste":"de")},week:{dow:1,doy:4}})});