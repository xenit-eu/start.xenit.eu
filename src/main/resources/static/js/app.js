!function(e){function n(n){for(var i,o,c=n[0],s=n[1],d=n[2],u=0,p=[];u<c.length;u++)o=c[u],t[o]&&p.push(t[o][0]),t[o]=0;for(i in s)Object.prototype.hasOwnProperty.call(s,i)&&(e[i]=s[i]);for(l&&l(n);p.length;)p.shift()();return r.push.apply(r,d||[]),a()}function a(){for(var e,n=0;n<r.length;n++){for(var a=r[n],i=!0,c=1;c<a.length;c++){var s=a[c];0!==t[s]&&(i=!1)}i&&(r.splice(n--,1),e=o(o.s=a[0]))}return e}var i={},t={0:0},r=[];function o(n){if(i[n])return i[n].exports;var a=i[n]={i:n,l:!1,exports:{}};return e[n].call(a.exports,a,a.exports,o),a.l=!0,a.exports}o.m=e,o.c=i,o.d=function(e,n,a){o.o(e,n)||Object.defineProperty(e,n,{enumerable:!0,get:a})},o.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},o.t=function(e,n){if(1&n&&(e=o(e)),8&n)return e;if(4&n&&"object"==typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(o.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&n&&"string"!=typeof e)for(var i in e)o.d(a,i,function(n){return e[n]}.bind(null,i));return a},o.n=function(e){var n=e&&e.__esModule?function(){return e.default}:function(){return e};return o.d(n,"a",n),n},o.o=function(e,n){return Object.prototype.hasOwnProperty.call(e,n)},o.p="";var c=window.webpackJsonp=window.webpackJsonp||[],s=c.push.bind(c);c.push=n,c=c.slice();for(var d=0;d<c.length;d++)n(c[d]);var l=s;r.push([139,1]),a()}({139:function(e,n,a){a(140),a(297),e.exports=a(311)},297:function(e,n,a){"use strict";a.r(n);a(68),a(87),a(82),a(57),a(84),a(298);$.fn.radio=function(){$(this).each(function(){var e=$(this).find(".radio"),n=e.find("a"),a=$(this).find("input");n.click(function(){var n=$(this).parent();return e.removeClass("active"),n.addClass("active"),a.val($(this).data("value")).trigger("change"),!1})})},$.fn.dropdown=function(){var e=$(this);$("body").click(function(){e.each(function(){$(this).removeClass("open")})}),e.each(function(){var e=$(this);$(this).find(".dropdown-toggle").click(function(n){return e.addClass("open"),!1})})},$(function(){var e,n=$("input[name='name']"),a=$("input[name='groupId']"),i=$("input[name='artifactId']"),t=$("input[name='packageName']"),r=function(){var e=a.val(),n=i.val(),r=e.concat(".").concat(n).replace(/-/g,"");t.val(r)};a.on("change",function(){r()}),i.on("change",function(){n.val($(this).val()),$("#baseDir").attr("value",this.value),r()}),$(".dropdown").dropdown(),$(".radios").radio(),-1!=navigator.appVersion.indexOf("Mac")?$("#btn-generate span.shortcut").html(" &#8984; + &#9166;"):$("#btn-generate span.shortcut").html(" alt + &#9166;"),e=function(e,n,a){var i=(e=e||window.location.href).indexOf("#!");if(i<0)return[];var t=[],r=e.slice(i+2).split("&");for(n=r.length;n--;)a=r[n].split("="),t.push({name:a[0],value:a.length>1?a[1]:null});return t}(),$.each(e,function(e,n){var a,i=decodeURIComponent(n.value);switch(n.name){case"type":case"language":case"packaging":case"javaVersion":(a=$("a[data-value='"+i+"']")).click();break;case"groupId":case"artifactId":case"name":case"description":case"packageName":(a=$("input[name='"+n.name+"']")).val(i)}a.trigger("change")})})},298:function(e,n,a){},311:function(e,n,a){"use strict";a.r(n);a(101),a(133),a(53),a(100),a(97),a(112),a(123),a(109),a(84),a(68),a(119),a(57),a(114),a(115);var i=a(138),t=(a(87),a(82),a(122),function(){}),r=/\[(.*),(.*)\]/,o=/\[(.*),(.*)\)/,c=/\((.*),(.*)\]/,s=["M","RC","BUILD-SNAPSHOT","RELEASE"];function d(e){var n=(e||"").replace(/\d+/g,"");return-1!=s.indexOf(n)?n:"RELEASE"}function l(e,n){for(var a,i=e.split("."),t=n.split("."),r=0;r<3;r++)if(0!=(a=parseInt(i[r],10)-parseInt(t[r],10)))return a;var o=d(i[3]),c=d(t[3]);return 0!=(a=s.indexOf(o)-s.indexOf(c))?a:i[3].localeCompare(t[3])}t.prototype.matchRange=function(e){var n=e.match(r);if(n)return function(e){return l(n[1],e)<=0&&l(n[2],e)>=0};var a=e.match(o);if(a)return function(e){return l(a[1],e)<=0&&l(a[2],e)>0};var i=e.match(c);return i?function(e){return l(i[1],e)<0&&l(i[2],e)>=0}:function(n){return l(e,n)<=0}};var u,p=[],f=[],v=-1,g='<div class="item {{ className }}" data-id="{{ id }}" data-index="{{ index }}">\n    <div class="title">{{ name }} <span class="cat">[{{ group }}]</span></div>\n    <div class="desc">{{ desc }}</div>\n    <div class="incompatible">Requires Spring Boot {{ versionRequirement }}.</div>\n    <a data-id="{{ id }}" class="btn-ico"><i class="fas fa-times"></i></a>\n    <input type="hidden" name="style" value="{{ id }}">\n</div>',h='<div class="item {{ className }}" data-id="{{ id }}" data-index="{{ index }}">\n    <div class="title">{{ name }} <span class="cat">[{{ group }}]</span></div>\n    <div class="desc">{{ desc }}</div>\n    <div class="incompatible">Requires Spring Boot {{ versionRequirement }}.</div>\n    <a data-id="{{ id }}" class="btn-ico"><i class="fas fa-plus"></i></a>\n</div>',m='<li class="{{ className }}">\n    <label>\n        <input {{ disabled }} {{ checked }} type="checkbox" value="true" data-id="{{ id }}"> \n        <strong>{{ name }}</strong>: {{ desc }}\n        <span>Requires Spring Boot {{ versionRequirement }}.</span>\n    </label>\n</li>',w=new i.Search("name");function b(e){$("#inputSearch").val(""),f.push(e),y(),R(),x(),$("#inputSearch").focus()}function R(){var e=$("#list-added"),n=$("#col-dep");if(0===f.length)n.addClass("hide"),e.html("");else{var a=new t,i=$("#input-boot-version").val(),r=f.map(function(e,n){var t=g.replace(new RegExp("{{ id }}","g"),e.id).replace(new RegExp("{{ index }}","g"),n).replace(new RegExp("{{ name }}","g"),e.name).replace(new RegExp("{{ desc }}","g"),e.description).replace(new RegExp("{{ versionRequirement }}","g"),e.versionRequirement).replace(new RegExp("{{ group }}","g"),e.group);return t=e.versionRange&&!a.matchRange(e.versionRange)(i)?t.replace(new RegExp("{{ className }}","g"),"invalid").replace(new RegExp("<input.*>","g"),""):t.replace(new RegExp("{{ className }}","g"),"")});e.html(r),e.find(".item").click(function(){var e=$(this).find("a").data("id");!function(e){f=f.filter(function(n){return n.id!==e.id}),y(),R(),x()}(u.find(function(n){return n.id===e}))}),n.removeClass("hide")}E()}function x(){var e=$("#list-to-add"),n=$("#noresult-to-add"),a=$("#inputSearch").val().trim();if(0===p.length)""!==a?n.addClass("show"):n.removeClass("show"),e.html("");else{var i=p.length>5?p.slice(0,6):p,r=new t,o=$("#input-boot-version").val(),c=i.map(function(e,n){var a="";return e.versionRange&&!r.matchRange(e.versionRange)(o)&&(a="invalid"),h.replace(new RegExp("{{ id }}","g"),e.id).replace(new RegExp("{{ index }}","g"),n).replace(new RegExp("{{ name }}","g"),e.name).replace(new RegExp("{{ desc }}","g"),e.description).replace(new RegExp("{{ versionRequirement }}","g"),e.versionRequirement).replace(new RegExp("{{ className }}","g"),a).replace(new RegExp("{{ group }}","g"),e.group)});n.removeClass("show"),e.html(c),k(),e.find(".item").mouseenter(function(){v=$(this).data("index"),k()}),e.find(".item").click(function(){var e=$(this),n=e.find("a").data("id");if(e.hasClass("invalid"))return!1;b(u.find(function(e){return e.id===n}))})}E()}function y(){var e=$("#inputSearch").val().trim();p=(p=""===e?[]:w.search(e)).filter(function(e){return 0===f.filter(function(n){return n.id===e.id}).length}).sort(function(e,n){return n.weight-e.weight})}function k(){var e,n,a,i,t=$("#list-to-add .item");if(t.removeClass("active"),v>-1&&v<t.length){var r=$(t.get(v));e=r,n=$(window).scrollTop(),a=n+$(window).height(),(i=$(e).offset().top)+$(e).height()+80<=a&&i>=n||$("body,html").scrollTop(r.offset().top),r.addClass("active")}}function E(){var e,n=$("body");e=$(window).height(),$("body").height()>e?n.addClass("fixed"):n.removeClass("fixed")}function C(){var e=$("#modal-dependencies .modal-body"),n=$(window).height();e.height(Math.min(n-250,1e3))}function S(){$(".modal .modal-body").scrollTop(0),$("#dependencies-list").html(""),$("body").removeClass("dependencies"),$("#overlay").fadeOut(100),$("#wrapper-modal").fadeOut(150)}function O(){C(),function(e){var n=$("#input-boot-version").val();$("#dependencies-list-title strong").html("Spring Boot "+n);var a,i=new t,r={};for(a=0;a<e.length;a++){var o=e[a];r[o.group]||(r[o.group]=[]),r[o.group].push(o)}var c=Object.keys(r),s="";for(a=0;a<c.length;a++){var d=c[a],l=r[d],u="<ul>";s+='<div class="group"><h3><span>'.concat(d,"</span></h3>"),u+=l.map(function(e,a){var t="",r="",o="";return e.versionRange&&!i.matchRange(e.versionRange)(n)&&(t="invalid",o='disabled=""'),f.find(function(n){return n.id===e.id})&&(r='checked="checked"'),m.replace(new RegExp("{{ checked }}","g"),r).replace(new RegExp("{{ id }}","g"),e.id).replace(new RegExp("{{ index }}","g"),a).replace(new RegExp("{{ name }}","g"),e.name).replace(new RegExp("{{ desc }}","g"),e.description).replace(new RegExp("{{ versionRequirement }}","g"),e.versionRequirement).replace(new RegExp("{{ className }}","g"),t).replace(new RegExp("{{ disabled }}","g"),o).replace(new RegExp("{{ group }}","g"),e.group)}).join(""),s+=u+="</ul></div>"}$("#dependencies-list").html(s)}(u),$("body").addClass("dependencies"),$("#overlay").fadeIn(100),$("#wrapper-modal").fadeIn(150)}w.addIndex("name"),w.addIndex("id"),w.addIndex("description"),w.addIndex("group"),$(function(){$.getJSON("/ui/dependencies").done(function(e){u=e.dependencies,w.addDocuments(e.dependencies),y(),x(),R()}),$("#input-boot-version").change(function(){y(),x(),R()});var e=$("#more-block"),n=$("#fewer-options"),a=$("#more-options");$("#fewer-options button").click(function(){n.addClass("hide"),a.removeClass("hide"),e.slideUp(200,function(){E()})}),$("#more-options button").click(function(){a.addClass("hide"),n.removeClass("hide"),e.slideDown(200,function(){E()})}),$(window).resize(function(){E(),C()});var i=new t,r=$("#input-boot-version");$("#inputSearch").keyup(function(){y(),x()}).keydown(function(e){var n;switch(e.keyCode){case 40:n=$("#list-to-add .item"),v<n.length-1&&v++;break;case 38:v>0&&v--,e.preventDefault();break;case 13:if(n=$("#list-to-add .item"),v>-1&&v<n.length){var a=p[v];a.versionRange&&!i.matchRange(a.versionRange)(r.val())||(b(a),1===p.length&&(v=0))}e.preventDefault();break;case 39:case 37:break;case 27:$("#inputSearch").val(""),v=0;break;default:v=0}k()}).focus(function(){v=0,k()}).blur(function(){v=-1,k()}),$("#see-all").click(function(){O()}),$(".modal").click(function(e){e.stopImmediatePropagation()}),$(".close-modal").click(function(){S()}),$("#btn-validate").click(function(){var e=$("#modal-dependencies .modal-body input:checked"),n=[];e.each(function(){var e=$(this);n.push(u.find(function(n){return n.id===e.data("id")}))}),f=n,y(),R(),x(),S()}),Mousetrap.bind(["escape"],function(){S()}),Mousetrap.bind(["command+enter","alt+enter"],function(e){$(".submit .btn-primary").click()})})}});
//# sourceMappingURL=app.js.map