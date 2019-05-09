//>>built
define("dojox/collections/Set",["./_base","./ArrayList"],function(h,g){h.Set=new function(){function d(a){return a.constructor==Array?new g(a):a}this.union=function(a,b){a=d(a);b=d(b);for(var c=new g(a.toArray()),e=b.getIterator();!e.atEnd();){var f=e.get();c.contains(f)||c.add(f)}return c};this.intersection=function(a,b){a=d(a);b=d(b);for(var c=new g,e=b.getIterator();!e.atEnd();){var f=e.get();a.contains(f)&&c.add(f)}return c};this.difference=function(a,b){a=d(a);b=d(b);for(var c=new g,e=a.getIterator();!e.atEnd();){var f=
e.get();b.contains(f)||c.add(f)}return c};this.isSubSet=function(a,b){a=d(a);b=d(b);for(var c=a.getIterator();!c.atEnd();)if(!b.contains(c.get()))return!1;return!0};this.isSuperSet=function(a,b){a=d(a);b=d(b);for(var c=b.getIterator();!c.atEnd();)if(!a.contains(c.get()))return!1;return!0}};return h.Set});