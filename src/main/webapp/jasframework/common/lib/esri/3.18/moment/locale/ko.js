//>>built
(function(b,a){"object"===typeof exports&&"undefined"!==typeof module&&"function"===typeof require?a(require("../moment")):"function"===typeof define&&define.amd?define("moment/locale/ko",["../moment"],a):a(b.moment)})(this,function(b){return b.defineLocale("ko",{months:"1\uc6d4 2\uc6d4 3\uc6d4 4\uc6d4 5\uc6d4 6\uc6d4 7\uc6d4 8\uc6d4 9\uc6d4 10\uc6d4 11\uc6d4 12\uc6d4".split(" "),monthsShort:"1\uc6d4 2\uc6d4 3\uc6d4 4\uc6d4 5\uc6d4 6\uc6d4 7\uc6d4 8\uc6d4 9\uc6d4 10\uc6d4 11\uc6d4 12\uc6d4".split(" "),
weekdays:"\uc77c\uc694\uc77c \uc6d4\uc694\uc77c \ud654\uc694\uc77c \uc218\uc694\uc77c \ubaa9\uc694\uc77c \uae08\uc694\uc77c \ud1a0\uc694\uc77c".split(" "),weekdaysShort:"\uc77c\uc6d4\ud654\uc218\ubaa9\uae08\ud1a0".split(""),weekdaysMin:"\uc77c\uc6d4\ud654\uc218\ubaa9\uae08\ud1a0".split(""),longDateFormat:{LT:"A h\uc2dc m\ubd84",LTS:"A h\uc2dc m\ubd84 s\ucd08",L:"YYYY.MM.DD",LL:"YYYY\ub144 MMMM D\uc77c",LLL:"YYYY\ub144 MMMM D\uc77c A h\uc2dc m\ubd84",LLLL:"YYYY\ub144 MMMM D\uc77c dddd A h\uc2dc m\ubd84"},
calendar:{sameDay:"\uc624\ub298 LT",nextDay:"\ub0b4\uc77c LT",nextWeek:"dddd LT",lastDay:"\uc5b4\uc81c LT",lastWeek:"\uc9c0\ub09c\uc8fc dddd LT",sameElse:"L"},relativeTime:{future:"%s \ud6c4",past:"%s \uc804",s:"\uba87 \ucd08",ss:"%d\ucd08",m:"\uc77c\ubd84",mm:"%d\ubd84",h:"\ud55c \uc2dc\uac04",hh:"%d\uc2dc\uac04",d:"\ud558\ub8e8",dd:"%d\uc77c",M:"\ud55c \ub2ec",MM:"%d\ub2ec",y:"\uc77c \ub144",yy:"%d\ub144"},ordinalParse:/\d{1,2}\uc77c/,ordinal:"%d\uc77c",meridiemParse:/\uc624\uc804|\uc624\ud6c4/,
isPM:function(a){return"\uc624\ud6c4"===a},meridiem:function(a,b,c){return 12>a?"\uc624\uc804":"\uc624\ud6c4"}})});