/** 
 * Kendo UI v2016.3.1118 (http://www.telerik.com/kendo-ui)                                                                                                                                              
 * Copyright 2016 Telerik AD. All rights reserved.                                                                                                                                                      
 *                                                                                                                                                                                                      
 * Kendo UI commercial licenses may be obtained at                                                                                                                                                      
 * http://www.telerik.com/purchase/license-agreement/kendo-ui-complete                                                                                                                                  
 * If you do not own a commercial license, this file shall be governed by the trial license terms.                                                                                                      
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       

*/
(function (f, define) {
    define('util/main', ['kendo.core'], f);
}(function () {
    (function () {
        var math = Math, kendo = window.kendo, deepExtend = kendo.deepExtend;
        var DEG_TO_RAD = math.PI / 180, MAX_NUM = Number.MAX_VALUE, MIN_NUM = -Number.MAX_VALUE, UNDEFINED = 'undefined';
        function defined(value) {
            return typeof value !== UNDEFINED;
        }
        function round(value, precision) {
            var power = pow(precision);
            return math.round(value * power) / power;
        }
        function pow(p) {
            if (p) {
                return math.pow(10, p);
            } else {
                return 1;
            }
        }
        function limitValue(value, min, max) {
            return math.max(math.min(value, max), min);
        }
        function rad(degrees) {
            return degrees * DEG_TO_RAD;
        }
        function deg(radians) {
            return radians / DEG_TO_RAD;
        }
        function isNumber(val) {
            return typeof val === 'number' && !isNaN(val);
        }
        function valueOrDefault(value, defaultValue) {
            return defined(value) ? value : defaultValue;
        }
        function sqr(value) {
            return value * value;
        }
        function objectKey(object) {
            var parts = [];
            for (var key in object) {
                parts.push(key + object[key]);
            }
            return parts.sort().join('');
        }
        function hashKey(str) {
            var hash = 2166136261;
            for (var i = 0; i < str.length; ++i) {
                hash += (hash << 1) + (hash << 4) + (hash << 7) + (hash << 8) + (hash << 24);
                hash ^= str.charCodeAt(i);
            }
            return hash >>> 0;
        }
        function hashObject(object) {
            return hashKey(objectKey(object));
        }
        var now = Date.now;
        if (!now) {
            now = function () {
                return new Date().getTime();
            };
        }
        function arrayLimits(arr) {
            var length = arr.length, i, min = MAX_NUM, max = MIN_NUM;
            for (i = 0; i < length; i++) {
                max = math.max(max, arr[i]);
                min = math.min(min, arr[i]);
            }
            return {
                min: min,
                max: max
            };
        }
        function arrayMin(arr) {
            return arrayLimits(arr).min;
        }
        function arrayMax(arr) {
            return arrayLimits(arr).max;
        }
        function sparseArrayMin(arr) {
            return sparseArrayLimits(arr).min;
        }
        function sparseArrayMax(arr) {
            return sparseArrayLimits(arr).max;
        }
        function sparseArrayLimits(arr) {
            var min = MAX_NUM, max = MIN_NUM;
            for (var i = 0, length = arr.length; i < length; i++) {
                var n = arr[i];
                if (n !== null && isFinite(n)) {
                    min = math.min(min, n);
                    max = math.max(max, n);
                }
            }
            return {
                min: min === MAX_NUM ? undefined : min,
                max: max === MIN_NUM ? undefined : max
            };
        }
        function last(array) {
            if (array) {
                return array[array.length - 1];
            }
        }
        function append(first, second) {
            first.push.apply(first, second);
            return first;
        }
        function renderTemplate(text) {
            return kendo.template(text, {
                useWithBlock: false,
                paramName: 'd'
            });
        }
        function renderAttr(name, value) {
            return defined(value) && value !== null ? ' ' + name + '=\'' + value + '\' ' : '';
        }
        function renderAllAttr(attrs) {
            var output = '';
            for (var i = 0; i < attrs.length; i++) {
                output += renderAttr(attrs[i][0], attrs[i][1]);
            }
            return output;
        }
        function renderStyle(attrs) {
            var output = '';
            for (var i = 0; i < attrs.length; i++) {
                var value = attrs[i][1];
                if (defined(value)) {
                    output += attrs[i][0] + ':' + value + ';';
                }
            }
            if (output !== '') {
                return output;
            }
        }
        function renderSize(size) {
            if (typeof size !== 'string') {
                size += 'px';
            }
            return size;
        }
        function renderPos(pos) {
            var result = [];
            if (pos) {
                var parts = kendo.toHyphens(pos).split('-');
                for (var i = 0; i < parts.length; i++) {
                    result.push('k-pos-' + parts[i]);
                }
            }
            return result.join(' ');
        }
        function isTransparent(color) {
            return color === '' || color === null || color === 'none' || color === 'transparent' || !defined(color);
        }
        function arabicToRoman(n) {
            var literals = {
                1: 'i',
                10: 'x',
                100: 'c',
                2: 'ii',
                20: 'xx',
                200: 'cc',
                3: 'iii',
                30: 'xxx',
                300: 'ccc',
                4: 'iv',
                40: 'xl',
                400: 'cd',
                5: 'v',
                50: 'l',
                500: 'd',
                6: 'vi',
                60: 'lx',
                600: 'dc',
                7: 'vii',
                70: 'lxx',
                700: 'dcc',
                8: 'viii',
                80: 'lxxx',
                800: 'dccc',
                9: 'ix',
                90: 'xc',
                900: 'cm',
                1000: 'm'
            };
            var values = [
                1000,
                900,
                800,
                700,
                600,
                500,
                400,
                300,
                200,
                100,
                90,
                80,
                70,
                60,
                50,
                40,
                30,
                20,
                10,
                9,
                8,
                7,
                6,
                5,
                4,
                3,
                2,
                1
            ];
            var roman = '';
            while (n > 0) {
                if (n < values[0]) {
                    values.shift();
                } else {
                    roman += literals[values[0]];
                    n -= values[0];
                }
            }
            return roman;
        }
        function romanToArabic(r) {
            r = r.toLowerCase();
            var digits = {
                i: 1,
                v: 5,
                x: 10,
                l: 50,
                c: 100,
                d: 500,
                m: 1000
            };
            var value = 0, prev = 0;
            for (var i = 0; i < r.length; ++i) {
                var v = digits[r.charAt(i)];
                if (!v) {
                    return null;
                }
                value += v;
                if (v > prev) {
                    value -= 2 * prev;
                }
                prev = v;
            }
            return value;
        }
        function memoize(f) {
            var cache = Object.create(null);
            return function () {
                var id = '';
                for (var i = arguments.length; --i >= 0;) {
                    id += ':' + arguments[i];
                }
                return id in cache ? cache[id] : cache[id] = f.apply(this, arguments);
            };
        }
        function ucs2decode(string) {
            var output = [], counter = 0, length = string.length, value, extra;
            while (counter < length) {
                value = string.charCodeAt(counter++);
                if (value >= 55296 && value <= 56319 && counter < length) {
                    extra = string.charCodeAt(counter++);
                    if ((extra & 64512) == 56320) {
                        output.push(((value & 1023) << 10) + (extra & 1023) + 65536);
                    } else {
                        output.push(value);
                        counter--;
                    }
                } else {
                    output.push(value);
                }
            }
            return output;
        }
        function ucs2encode(array) {
            return array.map(function (value) {
                var output = '';
                if (value > 65535) {
                    value -= 65536;
                    output += String.fromCharCode(value >>> 10 & 1023 | 55296);
                    value = 56320 | value & 1023;
                }
                output += String.fromCharCode(value);
                return output;
            }).join('');
        }
        function mergeSort(a, cmp) {
            if (a.length < 2) {
                return a.slice();
            }
            function merge(a, b) {
                var r = [], ai = 0, bi = 0, i = 0;
                while (ai < a.length && bi < b.length) {
                    if (cmp(a[ai], b[bi]) <= 0) {
                        r[i++] = a[ai++];
                    } else {
                        r[i++] = b[bi++];
                    }
                }
                if (ai < a.length) {
                    r.push.apply(r, a.slice(ai));
                }
                if (bi < b.length) {
                    r.push.apply(r, b.slice(bi));
                }
                return r;
            }
            return function sort(a) {
                if (a.length <= 1) {
                    return a;
                }
                var m = Math.floor(a.length / 2);
                var left = a.slice(0, m);
                var right = a.slice(m);
                left = sort(left);
                right = sort(right);
                return merge(left, right);
            }(a);
        }
        deepExtend(kendo, {
            util: {
                MAX_NUM: MAX_NUM,
                MIN_NUM: MIN_NUM,
                append: append,
                arrayLimits: arrayLimits,
                arrayMin: arrayMin,
                arrayMax: arrayMax,
                defined: defined,
                deg: deg,
                hashKey: hashKey,
                hashObject: hashObject,
                isNumber: isNumber,
                isTransparent: isTransparent,
                last: last,
                limitValue: limitValue,
                now: now,
                objectKey: objectKey,
                round: round,
                rad: rad,
                renderAttr: renderAttr,
                renderAllAttr: renderAllAttr,
                renderPos: renderPos,
                renderSize: renderSize,
                renderStyle: renderStyle,
                renderTemplate: renderTemplate,
                sparseArrayLimits: sparseArrayLimits,
                sparseArrayMin: sparseArrayMin,
                sparseArrayMax: sparseArrayMax,
                sqr: sqr,
                valueOrDefault: valueOrDefault,
                romanToArabic: romanToArabic,
                arabicToRoman: arabicToRoman,
                memoize: memoize,
                ucs2encode: ucs2encode,
                ucs2decode: ucs2decode,
                mergeSort: mergeSort
            }
        });
        kendo.drawing.util = kendo.util;
        kendo.dataviz.util = kendo.util;
    }());
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('pdf/core', [
        'kendo.core',
        'util/main'
    ], f);
}(function () {
    (function (window, parseFloat, undefined) {
        'use strict';
        var kendo = window.kendo;
        var HAS_TYPED_ARRAYS = !!window.Uint8Array;
        var NL = '\n';
        var RESOURCE_COUNTER = 0;
        var BASE64 = function () {
            var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
            return {
                decode: function (str) {
                    var input = str.replace(/[^A-Za-z0-9\+\/\=]/g, ''), i = 0, n = input.length, output = [];
                    while (i < n) {
                        var enc1 = keyStr.indexOf(input.charAt(i++));
                        var enc2 = keyStr.indexOf(input.charAt(i++));
                        var enc3 = keyStr.indexOf(input.charAt(i++));
                        var enc4 = keyStr.indexOf(input.charAt(i++));
                        var chr1 = enc1 << 2 | enc2 >>> 4;
                        var chr2 = (enc2 & 15) << 4 | enc3 >>> 2;
                        var chr3 = (enc3 & 3) << 6 | enc4;
                        output.push(chr1);
                        if (enc3 != 64) {
                            output.push(chr2);
                        }
                        if (enc4 != 64) {
                            output.push(chr3);
                        }
                    }
                    return output;
                },
                encode: function (bytes) {
                    var i = 0, n = bytes.length;
                    var output = '';
                    while (i < n) {
                        var chr1 = bytes[i++];
                        var chr2 = bytes[i++];
                        var chr3 = bytes[i++];
                        var enc1 = chr1 >>> 2;
                        var enc2 = (chr1 & 3) << 4 | chr2 >>> 4;
                        var enc3 = (chr2 & 15) << 2 | chr3 >>> 6;
                        var enc4 = chr3 & 63;
                        if (i - n == 2) {
                            enc3 = enc4 = 64;
                        } else if (i - n == 1) {
                            enc4 = 64;
                        }
                        output += keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
                    }
                    return output;
                }
            };
        }();
        var PAPER_SIZE = {
            a0: [
                2383.94,
                3370.39
            ],
            a1: [
                1683.78,
                2383.94
            ],
            a2: [
                1190.55,
                1683.78
            ],
            a3: [
                841.89,
                1190.55
            ],
            a4: [
                595.28,
                841.89
            ],
            a5: [
                419.53,
                595.28
            ],
            a6: [
                297.64,
                419.53
            ],
            a7: [
                209.76,
                297.64
            ],
            a8: [
                147.4,
                209.76
            ],
            a9: [
                104.88,
                147.4
            ],
            a10: [
                73.7,
                104.88
            ],
            b0: [
                2834.65,
                4008.19
            ],
            b1: [
                2004.09,
                2834.65
            ],
            b2: [
                1417.32,
                2004.09
            ],
            b3: [
                1000.63,
                1417.32
            ],
            b4: [
                708.66,
                1000.63
            ],
            b5: [
                498.9,
                708.66
            ],
            b6: [
                354.33,
                498.9
            ],
            b7: [
                249.45,
                354.33
            ],
            b8: [
                175.75,
                249.45
            ],
            b9: [
                124.72,
                175.75
            ],
            b10: [
                87.87,
                124.72
            ],
            c0: [
                2599.37,
                3676.54
            ],
            c1: [
                1836.85,
                2599.37
            ],
            c2: [
                1298.27,
                1836.85
            ],
            c3: [
                918.43,
                1298.27
            ],
            c4: [
                649.13,
                918.43
            ],
            c5: [
                459.21,
                649.13
            ],
            c6: [
                323.15,
                459.21
            ],
            c7: [
                229.61,
                323.15
            ],
            c8: [
                161.57,
                229.61
            ],
            c9: [
                113.39,
                161.57
            ],
            c10: [
                79.37,
                113.39
            ],
            executive: [
                521.86,
                756
            ],
            folio: [
                612,
                936
            ],
            legal: [
                612,
                1008
            ],
            letter: [
                612,
                792
            ],
            tabloid: [
                792,
                1224
            ]
        };
        function makeOutput() {
            var indentLevel = 0, output = BinaryStream();
            function out() {
                for (var i = 0; i < arguments.length; ++i) {
                    var x = arguments[i];
                    if (x === undefined) {
                        throw new Error('Cannot output undefined to PDF');
                    } else if (x instanceof PDFValue) {
                        x.beforeRender(out);
                        x.render(out);
                    } else if (isArray(x)) {
                        renderArray(x, out);
                    } else if (isDate(x)) {
                        renderDate(x, out);
                    } else if (typeof x == 'number') {
                        if (isNaN(x)) {
                            throw new Error('Cannot output NaN to PDF');
                        }
                        var num = x.toFixed(7);
                        if (num.indexOf('.') >= 0) {
                            num = num.replace(/\.?0+$/, '');
                        }
                        if (num == '-0') {
                            num = '0';
                        }
                        output.writeString(num);
                    } else if (/string|boolean/.test(typeof x)) {
                        output.writeString(x + '');
                    } else if (typeof x.get == 'function') {
                        output.write(x.get());
                    } else if (typeof x == 'object') {
                        if (!x) {
                            output.writeString('null');
                        } else {
                            out(new PDFDictionary(x));
                        }
                    }
                }
            }
            out.writeData = function (data) {
                output.write(data);
            };
            out.withIndent = function (f) {
                ++indentLevel;
                f(out);
                --indentLevel;
            };
            out.indent = function () {
                out(NL, pad('', indentLevel * 2, '  '));
                out.apply(null, arguments);
            };
            out.offset = function () {
                return output.offset();
            };
            out.toString = function () {
                throw new Error('FIX CALLER');
            };
            out.get = function () {
                return output.get();
            };
            out.stream = function () {
                return output;
            };
            return out;
        }
        function wrapObject(value, id) {
            var beforeRender = value.beforeRender;
            var renderValue = value.render;
            value.beforeRender = function () {
            };
            value.render = function (out) {
                out(id, ' 0 R');
            };
            value.renderFull = function (out) {
                value._offset = out.offset();
                out(id, ' 0 obj ');
                beforeRender.call(value, out);
                renderValue.call(value, out);
                out(' endobj');
            };
        }
        function getPaperOptions(getOption) {
            if (typeof getOption != 'function') {
                var options = getOption;
                getOption = function (key, def) {
                    return key in options ? options[key] : def;
                };
            }
            var paperSize = getOption('paperSize', PAPER_SIZE.a4);
            if (!paperSize) {
                return {};
            }
            if (typeof paperSize == 'string') {
                paperSize = PAPER_SIZE[paperSize.toLowerCase()];
                if (paperSize == null) {
                    throw new Error('Unknown paper size');
                }
            }
            paperSize[0] = unitsToPoints(paperSize[0]);
            paperSize[1] = unitsToPoints(paperSize[1]);
            if (getOption('landscape', false)) {
                paperSize = [
                    Math.max(paperSize[0], paperSize[1]),
                    Math.min(paperSize[0], paperSize[1])
                ];
            }
            var margin = getOption('margin');
            if (margin) {
                if (typeof margin == 'string' || typeof margin == 'number') {
                    margin = unitsToPoints(margin, 0);
                    margin = {
                        left: margin,
                        top: margin,
                        right: margin,
                        bottom: margin
                    };
                } else {
                    margin = {
                        left: unitsToPoints(margin.left, 0),
                        top: unitsToPoints(margin.top, 0),
                        right: unitsToPoints(margin.right, 0),
                        bottom: unitsToPoints(margin.bottom, 0)
                    };
                }
                if (getOption('addMargin')) {
                    paperSize[0] += margin.left + margin.right;
                    paperSize[1] += margin.top + margin.bottom;
                }
            }
            return {
                paperSize: paperSize,
                margin: margin
            };
        }
        function PDFDocument(options) {
            var self = this;
            var out = makeOutput();
            var objcount = 0;
            var objects = [];
            function getOption(name, defval) {
                return options && options[name] != null ? options[name] : defval;
            }
            self.getOption = getOption;
            self.attach = function (value) {
                if (objects.indexOf(value) < 0) {
                    wrapObject(value, ++objcount);
                    objects.push(value);
                }
                return value;
            };
            self.pages = [];
            self.FONTS = {};
            self.IMAGES = {};
            self.GRAD_COL_FUNCTIONS = {};
            self.GRAD_OPC_FUNCTIONS = {};
            self.GRAD_COL = {};
            self.GRAD_OPC = {};
            var catalog = self.attach(new PDFCatalog());
            var pageTree = self.attach(new PDFPageTree());
            catalog.setPages(pageTree);
            self.addPage = function (options) {
                var paperOptions = getPaperOptions(function (name, defval) {
                    return options && options[name] != null ? options[name] : defval;
                });
                var paperSize = paperOptions.paperSize;
                var margin = paperOptions.margin;
                var contentWidth = paperSize[0];
                var contentHeight = paperSize[1];
                if (margin) {
                    contentWidth -= margin.left + margin.right;
                    contentHeight -= margin.top + margin.bottom;
                }
                var content = new PDFStream(makeOutput(), null, true);
                var props = {
                    Contents: self.attach(content),
                    Parent: pageTree,
                    MediaBox: [
                        0,
                        0,
                        paperSize[0],
                        paperSize[1]
                    ]
                };
                var page = new PDFPage(self, props);
                page._content = content;
                pageTree.addPage(self.attach(page));
                page.transform(1, 0, 0, -1, 0, paperSize[1]);
                if (margin) {
                    page.translate(margin.left, margin.top);
                    page.rect(0, 0, contentWidth, contentHeight);
                    page.clip();
                }
                self.pages.push(page);
                return page;
            };
            self.render = function () {
                var i;
                out('%PDF-1.4', NL, '%ÂÁÚÏÎ', NL, NL);
                for (i = 0; i < objects.length; ++i) {
                    objects[i].renderFull(out);
                    out(NL, NL);
                }
                var xrefOffset = out.offset();
                out('xref', NL, 0, ' ', objects.length + 1, NL);
                out('0000000000 65535 f ', NL);
                for (i = 0; i < objects.length; ++i) {
                    out(zeropad(objects[i]._offset, 10), ' 00000 n ', NL);
                }
                out(NL);
                out('trailer', NL);
                out(new PDFDictionary({
                    Size: objects.length + 1,
                    Root: catalog,
                    Info: new PDFDictionary({
                        Producer: new PDFString(getOption('producer', 'Kendo UI PDF Generator v.' + kendo.version)),
                        Title: new PDFString(getOption('title', '')),
                        Author: new PDFString(getOption('author', '')),
                        Subject: new PDFString(getOption('subject', '')),
                        Keywords: new PDFString(getOption('keywords', '')),
                        Creator: new PDFString(getOption('creator', 'Kendo UI PDF Generator v.' + kendo.version)),
                        CreationDate: getOption('date', new Date())
                    })
                }), NL, NL);
                out('startxref', NL, xrefOffset, NL);
                out('%%EOF', NL);
                return out.stream().offset(0);
            };
        }
        var FONT_CACHE = {
            'Times-Roman': true,
            'Times-Bold': true,
            'Times-Italic': true,
            'Times-BoldItalic': true,
            'Helvetica': true,
            'Helvetica-Bold': true,
            'Helvetica-Oblique': true,
            'Helvetica-BoldOblique': true,
            'Courier': true,
            'Courier-Bold': true,
            'Courier-Oblique': true,
            'Courier-BoldOblique': true,
            'Symbol': true,
            'ZapfDingbats': true
        };
        function loadBinary(url, cont) {
            function error() {
                if (window.console) {
                    if (window.console.error) {
                        window.console.error('Cannot load URL: %s', url);
                    } else {
                        window.console.log('Cannot load URL: %s', url);
                    }
                }
                cont(null);
            }
            var req = new XMLHttpRequest();
            req.open('GET', url, true);
            if (HAS_TYPED_ARRAYS) {
                req.responseType = 'arraybuffer';
            }
            req.onload = function () {
                if (req.status == 200 || req.status == 304) {
                    if (HAS_TYPED_ARRAYS) {
                        cont(new Uint8Array(req.response));
                    } else {
                        cont(new VBArray(req.responseBody).toArray());
                    }
                } else {
                    error();
                }
            };
            req.onerror = error;
            req.send(null);
        }
        function loadFont(url, cont) {
            var font = FONT_CACHE[url];
            if (font) {
                cont(font);
            } else {
                loadBinary(url, function (data) {
                    if (data == null) {
                        throw new Error('Cannot load font from ' + url);
                    } else {
                        var font = new kendo.pdf.TTFFont(data);
                        FONT_CACHE[url] = font;
                        cont(font);
                    }
                });
            }
        }
        var IMAGE_CACHE = {};
        function loadImage(url, cont) {
            var img = IMAGE_CACHE[url], bloburl, blob;
            if (img) {
                cont(img);
            } else {
                img = new Image();
                if (!/^data:/i.test(url)) {
                    img.crossOrigin = 'Anonymous';
                }
                if (HAS_TYPED_ARRAYS && !/^data:/i.test(url)) {
                    var xhr = new XMLHttpRequest();
                    xhr.onload = function () {
                        blob = xhr.response;
                        bloburl = URL.createObjectURL(blob);
                        _load(bloburl);
                    };
                    xhr.onerror = _onerror;
                    xhr.open('GET', url, true);
                    xhr.responseType = 'blob';
                    xhr.send();
                } else {
                    _load(url);
                }
            }
            function _load(url) {
                img.src = url;
                if (img.complete && !kendo.support.browser.msie) {
                    _onload();
                } else {
                    img.onload = _onload;
                    img.onerror = _onerror;
                }
            }
            function _onerror() {
                cont(IMAGE_CACHE[url] = 'TAINTED');
            }
            function _onload() {
                if (blob && /^image\/jpe?g$/i.test(blob.type)) {
                    var reader = new FileReader();
                    reader.onload = function () {
                        img = new PDFJpegImage(img.width, img.height, BinaryStream(new Uint8Array(this.result)));
                        URL.revokeObjectURL(bloburl);
                        cont(IMAGE_CACHE[url] = img);
                    };
                    reader.readAsArrayBuffer(blob);
                    return;
                }
                var canvas = document.createElement('canvas');
                canvas.width = img.width;
                canvas.height = img.height;
                var ctx = canvas.getContext('2d');
                ctx.drawImage(img, 0, 0);
                var imgdata;
                try {
                    imgdata = ctx.getImageData(0, 0, img.width, img.height);
                } catch (ex) {
                    return _onerror();
                } finally {
                    if (bloburl) {
                        URL.revokeObjectURL(bloburl);
                    }
                }
                var hasAlpha = false, rgb = BinaryStream(), alpha = BinaryStream();
                var rawbytes = imgdata.data;
                var i = 0;
                while (i < rawbytes.length) {
                    rgb.writeByte(rawbytes[i++]);
                    rgb.writeByte(rawbytes[i++]);
                    rgb.writeByte(rawbytes[i++]);
                    var a = rawbytes[i++];
                    if (a < 255) {
                        hasAlpha = true;
                    }
                    alpha.writeByte(a);
                }
                if (hasAlpha) {
                    img = new PDFRawImage(img.width, img.height, rgb, alpha);
                } else {
                    var data = canvas.toDataURL('image/jpeg');
                    data = data.substr(data.indexOf(';base64,') + 8);
                    var stream = BinaryStream();
                    stream.writeBase64(data);
                    stream.offset(0);
                    img = new PDFJpegImage(img.width, img.height, stream);
                }
                cont(IMAGE_CACHE[url] = img);
            }
        }
        function manyLoader(loadOne) {
            return function (urls, callback) {
                var n = urls.length, i = n;
                if (n === 0) {
                    return callback();
                }
                while (i-- > 0) {
                    loadOne(urls[i], function () {
                        if (--n === 0) {
                            callback();
                        }
                    });
                }
            };
        }
        var loadFonts = manyLoader(loadFont);
        var loadImages = manyLoader(loadImage);
        PDFDocument.prototype = {
            loadFonts: loadFonts,
            loadImages: loadImages,
            getFont: function (url) {
                var font = this.FONTS[url];
                if (!font) {
                    font = FONT_CACHE[url];
                    if (!font) {
                        throw new Error('Font ' + url + ' has not been loaded');
                    }
                    if (font === true) {
                        font = this.attach(new PDFStandardFont(url));
                    } else {
                        font = this.attach(new PDFFont(this, font));
                    }
                    this.FONTS[url] = font;
                }
                return font;
            },
            getImage: function (url) {
                var img = this.IMAGES[url];
                if (!img) {
                    img = IMAGE_CACHE[url];
                    if (!img) {
                        throw new Error('Image ' + url + ' has not been loaded');
                    }
                    if (img === 'TAINTED') {
                        return null;
                    }
                    img = this.IMAGES[url] = this.attach(img.asStream(this));
                }
                return img;
            },
            getOpacityGS: function (opacity, forStroke) {
                var id = parseFloat(opacity).toFixed(3);
                opacity = parseFloat(id);
                id += forStroke ? 'S' : 'F';
                var cache = this._opacityGSCache || (this._opacityGSCache = {});
                var gs = cache[id];
                if (!gs) {
                    var props = { Type: _('ExtGState') };
                    if (forStroke) {
                        props.CA = opacity;
                    } else {
                        props.ca = opacity;
                    }
                    gs = this.attach(new PDFDictionary(props));
                    gs._resourceName = _('GS' + ++RESOURCE_COUNTER);
                    cache[id] = gs;
                }
                return gs;
            },
            dict: function (props) {
                return new PDFDictionary(props);
            },
            name: function (str) {
                return _(str);
            },
            stream: function (props, content) {
                return new PDFStream(content, props);
            }
        };
        function pad(str, len, ch) {
            while (str.length < len) {
                str = ch + str;
            }
            return str;
        }
        function zeropad(n, len) {
            return pad(n + '', len, '0');
        }
        function hasOwnProperty(obj, key) {
            return Object.prototype.hasOwnProperty.call(obj, key);
        }
        var isArray = Array.isArray || function (obj) {
            return obj instanceof Array;
        };
        function isDate(obj) {
            return obj instanceof Date;
        }
        function renderArray(a, out) {
            out('[');
            if (a.length > 0) {
                out.withIndent(function () {
                    for (var i = 0; i < a.length; ++i) {
                        if (i > 0 && i % 8 === 0) {
                            out.indent(a[i]);
                        } else {
                            out(' ', a[i]);
                        }
                    }
                });
            }
            out(' ]');
        }
        function renderDate(date, out) {
            out('(D:', zeropad(date.getUTCFullYear(), 4), zeropad(date.getUTCMonth() + 1, 2), zeropad(date.getUTCDate(), 2), zeropad(date.getUTCHours(), 2), zeropad(date.getUTCMinutes(), 2), zeropad(date.getUTCSeconds(), 2), 'Z)');
        }
        function mm2pt(mm) {
            return mm * (72 / 25.4);
        }
        function cm2pt(cm) {
            return mm2pt(cm * 10);
        }
        function in2pt(inch) {
            return inch * 72;
        }
        function unitsToPoints(x, def) {
            if (typeof x == 'number') {
                return x;
            }
            if (typeof x == 'string') {
                var m;
                m = /^\s*([0-9.]+)\s*(mm|cm|in|pt)\s*$/.exec(x);
                if (m) {
                    var num = parseFloat(m[1]);
                    if (!isNaN(num)) {
                        if (m[2] == 'pt') {
                            return num;
                        }
                        return {
                            'mm': mm2pt,
                            'cm': cm2pt,
                            'in': in2pt
                        }[m[2]](num);
                    }
                }
            }
            if (def != null) {
                return def;
            }
            throw new Error('Can\'t parse unit: ' + x);
        }
        function PDFValue() {
        }
        PDFValue.prototype.beforeRender = function () {
        };
        function defclass(Ctor, proto, Base) {
            if (!Base) {
                Base = PDFValue;
            }
            Ctor.prototype = new Base();
            for (var i in proto) {
                if (hasOwnProperty(proto, i)) {
                    Ctor.prototype[i] = proto[i];
                }
            }
            return Ctor;
        }
        var PDFString = defclass(function PDFString(value) {
            this.value = value;
        }, {
            render: function (out) {
                var txt = '', esc = this.escape();
                for (var i = 0; i < esc.length; ++i) {
                    txt += String.fromCharCode(esc.charCodeAt(i) & 255);
                }
                out('(', txt, ')');
            },
            escape: function () {
                return this.value.replace(/([\(\)\\])/g, '\\$1');
            },
            toString: function () {
                return this.value;
            }
        });
        var PDFHexString = defclass(function PDFHexString(value) {
            this.value = value;
        }, {
            render: function (out) {
                out('<');
                for (var i = 0; i < this.value.length; ++i) {
                    out(zeropad(this.value.charCodeAt(i).toString(16), 4));
                }
                out('>');
            }
        }, PDFString);
        var PDFName = defclass(function PDFName(name) {
            this.name = name;
        }, {
            render: function (out) {
                out('/' + this.escape());
            },
            escape: function () {
                return this.name.replace(/[^\x21-\x7E]/g, function (c) {
                    return '#' + zeropad(c.charCodeAt(0).toString(16), 2);
                });
            },
            toString: function () {
                return this.name;
            }
        });
        var PDFName_cache = {};
        PDFName.get = _;
        function _(name) {
            if (hasOwnProperty(PDFName_cache, name)) {
                return PDFName_cache[name];
            }
            return PDFName_cache[name] = new PDFName(name);
        }
        var PDFDictionary = defclass(function PDFDictionary(props) {
            this.props = props;
        }, {
            render: function (out) {
                var props = this.props, empty = true;
                out('<<');
                out.withIndent(function () {
                    for (var i in props) {
                        if (hasOwnProperty(props, i) && !/^_/.test(i)) {
                            empty = false;
                            out.indent(_(i), ' ', props[i]);
                        }
                    }
                });
                if (!empty) {
                    out.indent();
                }
                out('>>');
            }
        });
        var PDFStream = defclass(function PDFStream(data, props, compress) {
            if (typeof data == 'string') {
                var tmp = BinaryStream();
                tmp.write(data);
                data = tmp;
            }
            this.data = data;
            this.props = props || {};
            this.compress = compress;
        }, {
            render: function (out) {
                var data = this.data.get(), props = this.props;
                if (this.compress && window.pako && typeof window.pako.deflate == 'function') {
                    if (!props.Filter) {
                        props.Filter = [];
                    } else if (!(props.Filter instanceof Array)) {
                        props.Filter = [props.Filter];
                    }
                    props.Filter.unshift(_('FlateDecode'));
                    data = window.pako.deflate(data);
                }
                props.Length = data.length;
                out(new PDFDictionary(props), ' stream', NL);
                out.writeData(data);
                out(NL, 'endstream');
            }
        });
        var PDFCatalog = defclass(function PDFCatalog(props) {
            props = this.props = props || {};
            props.Type = _('Catalog');
        }, {
            setPages: function (pagesObj) {
                this.props.Pages = pagesObj;
            }
        }, PDFDictionary);
        var PDFPageTree = defclass(function PDFPageTree() {
            this.props = {
                Type: _('Pages'),
                Kids: [],
                Count: 0
            };
        }, {
            addPage: function (pageObj) {
                this.props.Kids.push(pageObj);
                this.props.Count++;
            }
        }, PDFDictionary);
        function PDFJpegImage(width, height, data) {
            this.asStream = function () {
                var stream = new PDFStream(data, {
                    Type: _('XObject'),
                    Subtype: _('Image'),
                    Width: width,
                    Height: height,
                    BitsPerComponent: 8,
                    ColorSpace: _('DeviceRGB'),
                    Filter: _('DCTDecode')
                });
                stream._resourceName = _('I' + ++RESOURCE_COUNTER);
                return stream;
            };
        }
        function PDFRawImage(width, height, rgb, alpha) {
            this.asStream = function (pdf) {
                var mask = new PDFStream(alpha, {
                    Type: _('XObject'),
                    Subtype: _('Image'),
                    Width: width,
                    Height: height,
                    BitsPerComponent: 8,
                    ColorSpace: _('DeviceGray')
                }, true);
                var stream = new PDFStream(rgb, {
                    Type: _('XObject'),
                    Subtype: _('Image'),
                    Width: width,
                    Height: height,
                    BitsPerComponent: 8,
                    ColorSpace: _('DeviceRGB'),
                    SMask: pdf.attach(mask)
                }, true);
                stream._resourceName = _('I' + ++RESOURCE_COUNTER);
                return stream;
            };
        }
        var PDFStandardFont = defclass(function PDFStandardFont(name) {
            this.props = {
                Type: _('Font'),
                Subtype: _('Type1'),
                BaseFont: _(name)
            };
            this._resourceName = _('F' + ++RESOURCE_COUNTER);
        }, {
            encodeText: function (str) {
                return new PDFString(str + '');
            }
        }, PDFDictionary);
        var PDFFont = defclass(function PDFFont(pdf, font, props) {
            props = this.props = props || {};
            props.Type = _('Font');
            props.Subtype = _('Type0');
            props.Encoding = _('Identity-H');
            this._pdf = pdf;
            this._font = font;
            this._sub = font.makeSubset();
            this._resourceName = _('F' + ++RESOURCE_COUNTER);
            var head = font.head;
            this.name = font.psName;
            var scale = this.scale = font.scale;
            this.bbox = [
                head.xMin * scale,
                head.yMin * scale,
                head.xMax * scale,
                head.yMax * scale
            ];
            this.italicAngle = font.post.italicAngle;
            this.ascent = font.ascent * scale;
            this.descent = font.descent * scale;
            this.lineGap = font.lineGap * scale;
            this.capHeight = font.os2.capHeight || this.ascent;
            this.xHeight = font.os2.xHeight || 0;
            this.stemV = 0;
            this.familyClass = (font.os2.familyClass || 0) >> 8;
            this.isSerif = this.familyClass >= 1 && this.familyClass <= 7;
            this.isScript = this.familyClass == 10;
            this.flags = (font.post.isFixedPitch ? 1 : 0) | (this.isSerif ? 1 << 1 : 0) | (this.isScript ? 1 << 3 : 0) | (this.italicAngle !== 0 ? 1 << 6 : 0) | 1 << 5;
        }, {
            encodeText: function (text) {
                return new PDFHexString(this._sub.encodeText(text + ''));
            },
            getTextWidth: function (fontSize, text) {
                var width = 0, codeMap = this._font.cmap.codeMap;
                for (var i = 0; i < text.length; ++i) {
                    var glyphId = codeMap[text.charCodeAt(i)];
                    width += this._font.widthOfGlyph(glyphId || 0);
                }
                return width * fontSize / 1000;
            },
            beforeRender: function () {
                var self = this;
                var sub = self._sub;
                var data = sub.render();
                var fontStream = new PDFStream(BinaryStream(data), { Length1: data.length }, true);
                var descriptor = self._pdf.attach(new PDFDictionary({
                    Type: _('FontDescriptor'),
                    FontName: _(self._sub.psName),
                    FontBBox: self.bbox,
                    Flags: self.flags,
                    StemV: self.stemV,
                    ItalicAngle: self.italicAngle,
                    Ascent: self.ascent,
                    Descent: self.descent,
                    CapHeight: self.capHeight,
                    XHeight: self.xHeight,
                    FontFile2: self._pdf.attach(fontStream)
                }));
                var cmap = sub.ncid2ogid;
                var firstChar = sub.firstChar;
                var lastChar = sub.lastChar;
                var charWidths = [];
                (function loop(i, chunk) {
                    if (i <= lastChar) {
                        var gid = cmap[i];
                        if (gid == null) {
                            loop(i + 1);
                        } else {
                            if (!chunk) {
                                charWidths.push(i, chunk = []);
                            }
                            chunk.push(self._font.widthOfGlyph(gid));
                            loop(i + 1, chunk);
                        }
                    }
                }(firstChar));
                var descendant = new PDFDictionary({
                    Type: _('Font'),
                    Subtype: _('CIDFontType2'),
                    BaseFont: _(self._sub.psName),
                    CIDSystemInfo: new PDFDictionary({
                        Registry: new PDFString('Adobe'),
                        Ordering: new PDFString('Identity'),
                        Supplement: 0
                    }),
                    FontDescriptor: descriptor,
                    FirstChar: firstChar,
                    LastChar: lastChar,
                    DW: Math.round(self._font.widthOfGlyph(0)),
                    W: charWidths,
                    CIDToGIDMap: self._pdf.attach(self._makeCidToGidMap())
                });
                var dict = self.props;
                dict.BaseFont = _(self._sub.psName);
                dict.DescendantFonts = [self._pdf.attach(descendant)];
                var unimap = new PDFToUnicodeCmap(firstChar, lastChar, sub.subset);
                var unimapStream = new PDFStream(makeOutput(), null, true);
                unimapStream.data(unimap);
                dict.ToUnicode = self._pdf.attach(unimapStream);
            },
            _makeCidToGidMap: function () {
                return new PDFStream(BinaryStream(this._sub.cidToGidMap()), null, true);
            }
        }, PDFDictionary);
        var PDFToUnicodeCmap = defclass(function PDFUnicodeCMap(firstChar, lastChar, map) {
            this.firstChar = firstChar;
            this.lastChar = lastChar;
            this.map = map;
        }, {
            render: function (out) {
                out.indent('/CIDInit /ProcSet findresource begin');
                out.indent('12 dict begin');
                out.indent('begincmap');
                out.indent('/CIDSystemInfo <<');
                out.indent('  /Registry (Adobe)');
                out.indent('  /Ordering (UCS)');
                out.indent('  /Supplement 0');
                out.indent('>> def');
                out.indent('/CMapName /Adobe-Identity-UCS def');
                out.indent('/CMapType 2 def');
                out.indent('1 begincodespacerange');
                out.indent('  <0000><ffff>');
                out.indent('endcodespacerange');
                var self = this;
                out.indent(self.lastChar - self.firstChar + 1, ' beginbfchar');
                out.withIndent(function () {
                    for (var code = self.firstChar; code <= self.lastChar; ++code) {
                        var unicode = self.map[code];
                        var str = kendo.util.ucs2encode([unicode]);
                        out.indent('<', zeropad(code.toString(16), 4), '>', '<');
                        for (var i = 0; i < str.length; ++i) {
                            out(zeropad(str.charCodeAt(i).toString(16), 4));
                        }
                        out('>');
                    }
                });
                out.indent('endbfchar');
                out.indent('endcmap');
                out.indent('CMapName currentdict /CMap defineresource pop');
                out.indent('end');
                out.indent('end');
            }
        });
        function makeHash(a) {
            return a.map(function (x) {
                return isArray(x) ? makeHash(x) : typeof x == 'number' ? (Math.round(x * 1000) / 1000).toFixed(3) : x;
            }).join(' ');
        }
        function cacheColorGradientFunction(pdf, r1, g1, b1, r2, g2, b2) {
            var hash = makeHash([
                r1,
                g1,
                b1,
                r2,
                g2,
                b2
            ]);
            var func = pdf.GRAD_COL_FUNCTIONS[hash];
            if (!func) {
                func = pdf.GRAD_COL_FUNCTIONS[hash] = pdf.attach(new PDFDictionary({
                    FunctionType: 2,
                    Domain: [
                        0,
                        1
                    ],
                    Range: [
                        0,
                        1,
                        0,
                        1,
                        0,
                        1
                    ],
                    N: 1,
                    C0: [
                        r1,
                        g1,
                        b1
                    ],
                    C1: [
                        r2,
                        g2,
                        b2
                    ]
                }));
            }
            return func;
        }
        function cacheOpacityGradientFunction(pdf, a1, a2) {
            var hash = makeHash([
                a1,
                a2
            ]);
            var func = pdf.GRAD_OPC_FUNCTIONS[hash];
            if (!func) {
                func = pdf.GRAD_OPC_FUNCTIONS[hash] = pdf.attach(new PDFDictionary({
                    FunctionType: 2,
                    Domain: [
                        0,
                        1
                    ],
                    Range: [
                        0,
                        1
                    ],
                    N: 1,
                    C0: [a1],
                    C1: [a2]
                }));
            }
            return func;
        }
        function makeGradientFunctions(pdf, stops) {
            var hasAlpha = false;
            var opacities = [];
            var colors = [];
            var offsets = [];
            var encode = [];
            var i, prev, cur, prevColor, curColor;
            for (i = 1; i < stops.length; ++i) {
                prev = stops[i - 1];
                cur = stops[i];
                prevColor = prev.color;
                curColor = cur.color;
                colors.push(cacheColorGradientFunction(pdf, prevColor.r, prevColor.g, prevColor.b, curColor.r, curColor.g, curColor.b));
                if (prevColor.a < 1 || curColor.a < 1) {
                    hasAlpha = true;
                }
                offsets.push(cur.offset);
                encode.push(0, 1);
            }
            if (hasAlpha) {
                for (i = 1; i < stops.length; ++i) {
                    prev = stops[i - 1];
                    cur = stops[i];
                    prevColor = prev.color;
                    curColor = cur.color;
                    opacities.push(cacheOpacityGradientFunction(pdf, prevColor.a, curColor.a));
                }
            }
            offsets.pop();
            return {
                hasAlpha: hasAlpha,
                colors: assemble(colors),
                opacities: hasAlpha ? assemble(opacities) : null
            };
            function assemble(funcs) {
                if (funcs.length == 1) {
                    return funcs[0];
                }
                return {
                    FunctionType: 3,
                    Functions: funcs,
                    Domain: [
                        0,
                        1
                    ],
                    Bounds: offsets,
                    Encode: encode
                };
            }
        }
        function cacheColorGradient(pdf, isRadial, stops, coords, funcs, box) {
            var shading, hash;
            if (!box) {
                var a = [isRadial].concat(coords);
                stops.forEach(function (x) {
                    a.push(x.offset, x.color.r, x.color.g, x.color.b);
                });
                hash = makeHash(a);
                shading = pdf.GRAD_COL[hash];
            }
            if (!shading) {
                shading = new PDFDictionary({
                    Type: _('Shading'),
                    ShadingType: isRadial ? 3 : 2,
                    ColorSpace: _('DeviceRGB'),
                    Coords: coords,
                    Domain: [
                        0,
                        1
                    ],
                    Function: funcs,
                    Extend: [
                        true,
                        true
                    ]
                });
                pdf.attach(shading);
                shading._resourceName = 'S' + ++RESOURCE_COUNTER;
                if (hash) {
                    pdf.GRAD_COL[hash] = shading;
                }
            }
            return shading;
        }
        function cacheOpacityGradient(pdf, isRadial, stops, coords, funcs, box) {
            var opacity, hash;
            if (!box) {
                var a = [isRadial].concat(coords);
                stops.forEach(function (x) {
                    a.push(x.offset, x.color.a);
                });
                hash = makeHash(a);
                opacity = pdf.GRAD_OPC[hash];
            }
            if (!opacity) {
                opacity = new PDFDictionary({
                    Type: _('ExtGState'),
                    AIS: false,
                    CA: 1,
                    ca: 1,
                    SMask: {
                        Type: _('Mask'),
                        S: _('Luminosity'),
                        G: pdf.attach(new PDFStream('/a0 gs /s0 sh', {
                            Type: _('XObject'),
                            Subtype: _('Form'),
                            FormType: 1,
                            BBox: box ? [
                                box.left,
                                box.top + box.height,
                                box.left + box.width,
                                box.top
                            ] : [
                                0,
                                1,
                                1,
                                0
                            ],
                            Group: {
                                Type: _('Group'),
                                S: _('Transparency'),
                                CS: _('DeviceGray'),
                                I: true
                            },
                            Resources: {
                                ExtGState: {
                                    a0: {
                                        CA: 1,
                                        ca: 1
                                    }
                                },
                                Shading: {
                                    s0: {
                                        ColorSpace: _('DeviceGray'),
                                        Coords: coords,
                                        Domain: [
                                            0,
                                            1
                                        ],
                                        ShadingType: isRadial ? 3 : 2,
                                        Function: funcs,
                                        Extend: [
                                            true,
                                            true
                                        ]
                                    }
                                }
                            }
                        }))
                    }
                });
                pdf.attach(opacity);
                opacity._resourceName = 'O' + ++RESOURCE_COUNTER;
                if (hash) {
                    pdf.GRAD_OPC[hash] = opacity;
                }
            }
            return opacity;
        }
        function cacheGradient(pdf, gradient, box) {
            var isRadial = gradient.type == 'radial';
            var funcs = makeGradientFunctions(pdf, gradient.stops);
            var coords = isRadial ? [
                gradient.start.x,
                gradient.start.y,
                gradient.start.r,
                gradient.end.x,
                gradient.end.y,
                gradient.end.r
            ] : [
                gradient.start.x,
                gradient.start.y,
                gradient.end.x,
                gradient.end.y
            ];
            var shading = cacheColorGradient(pdf, isRadial, gradient.stops, coords, funcs.colors, gradient.userSpace && box);
            var opacity = funcs.hasAlpha ? cacheOpacityGradient(pdf, isRadial, gradient.stops, coords, funcs.opacities, gradient.userSpace && box) : null;
            return {
                hasAlpha: funcs.hasAlpha,
                shading: shading,
                opacity: opacity
            };
        }
        var PDFPage = defclass(function PDFPage(pdf, props) {
            this._pdf = pdf;
            this._rcount = 0;
            this._textMode = false;
            this._fontResources = {};
            this._gsResources = {};
            this._xResources = {};
            this._patResources = {};
            this._shResources = {};
            this._opacity = 1;
            this._matrix = [
                1,
                0,
                0,
                1,
                0,
                0
            ];
            this._annotations = [];
            this._font = null;
            this._fontSize = null;
            this._contextStack = [];
            props = this.props = props || {};
            props.Type = _('Page');
            props.ProcSet = [
                _('PDF'),
                _('Text'),
                _('ImageB'),
                _('ImageC'),
                _('ImageI')
            ];
            props.Resources = new PDFDictionary({
                Font: new PDFDictionary(this._fontResources),
                ExtGState: new PDFDictionary(this._gsResources),
                XObject: new PDFDictionary(this._xResources),
                Pattern: new PDFDictionary(this._patResources),
                Shading: new PDFDictionary(this._shResources)
            });
            props.Annots = this._annotations;
        }, {
            _out: function () {
                this._content.data.apply(null, arguments);
            },
            transform: function (a, b, c, d, e, f) {
                if (!isIdentityMatrix(arguments)) {
                    this._matrix = mmul(arguments, this._matrix);
                    this._out(a, ' ', b, ' ', c, ' ', d, ' ', e, ' ', f, ' cm');
                    this._out(NL);
                }
            },
            translate: function (dx, dy) {
                this.transform(1, 0, 0, 1, dx, dy);
            },
            scale: function (sx, sy) {
                this.transform(sx, 0, 0, sy, 0, 0);
            },
            rotate: function (angle) {
                var cos = Math.cos(angle), sin = Math.sin(angle);
                this.transform(cos, sin, -sin, cos, 0, 0);
            },
            beginText: function () {
                this._textMode = true;
                this._out('BT', NL);
            },
            endText: function () {
                this._textMode = false;
                this._out('ET', NL);
            },
            _requireTextMode: function () {
                if (!this._textMode) {
                    throw new Error('Text mode required; call page.beginText() first');
                }
            },
            _requireFont: function () {
                if (!this._font) {
                    throw new Error('No font selected; call page.setFont() first');
                }
            },
            setFont: function (font, size) {
                this._requireTextMode();
                if (font == null) {
                    font = this._font;
                } else if (!(font instanceof PDFFont)) {
                    font = this._pdf.getFont(font);
                }
                if (size == null) {
                    size = this._fontSize;
                }
                this._fontResources[font._resourceName] = font;
                this._font = font;
                this._fontSize = size;
                this._out(font._resourceName, ' ', size, ' Tf', NL);
            },
            setTextLeading: function (size) {
                this._requireTextMode();
                this._out(size, ' TL', NL);
            },
            setTextRenderingMode: function (mode) {
                this._requireTextMode();
                this._out(mode, ' Tr', NL);
            },
            showText: function (text, requestedWidth) {
                this._requireFont();
                if (text.length > 1 && requestedWidth && this._font instanceof PDFFont) {
                    var outputWidth = this._font.getTextWidth(this._fontSize, text);
                    var scale = requestedWidth / outputWidth * 100;
                    this._out(scale, ' Tz ');
                }
                this._out(this._font.encodeText(text), ' Tj', NL);
            },
            showTextNL: function (text) {
                this._requireFont();
                this._out(this._font.encodeText(text), ' \'', NL);
            },
            addLink: function (uri, box) {
                var ll = this._toPage({
                    x: box.left,
                    y: box.bottom
                });
                var ur = this._toPage({
                    x: box.right,
                    y: box.top
                });
                this._annotations.push(new PDFDictionary({
                    Type: _('Annot'),
                    Subtype: _('Link'),
                    Rect: [
                        ll.x,
                        ll.y,
                        ur.x,
                        ur.y
                    ],
                    Border: [
                        0,
                        0,
                        0
                    ],
                    A: new PDFDictionary({
                        Type: _('Action'),
                        S: _('URI'),
                        URI: new PDFString(uri)
                    })
                }));
            },
            setStrokeColor: function (r, g, b) {
                this._out(r, ' ', g, ' ', b, ' RG', NL);
            },
            setOpacity: function (opacity) {
                this.setFillOpacity(opacity);
                this.setStrokeOpacity(opacity);
                this._opacity *= opacity;
            },
            setStrokeOpacity: function (opacity) {
                if (opacity < 1) {
                    var gs = this._pdf.getOpacityGS(this._opacity * opacity, true);
                    this._gsResources[gs._resourceName] = gs;
                    this._out(gs._resourceName, ' gs', NL);
                }
            },
            setFillColor: function (r, g, b) {
                this._out(r, ' ', g, ' ', b, ' rg', NL);
            },
            setFillOpacity: function (opacity) {
                if (opacity < 1) {
                    var gs = this._pdf.getOpacityGS(this._opacity * opacity, false);
                    this._gsResources[gs._resourceName] = gs;
                    this._out(gs._resourceName, ' gs', NL);
                }
            },
            gradient: function (gradient, box) {
                this.save();
                this.rect(box.left, box.top, box.width, box.height);
                this.clip();
                if (!gradient.userSpace) {
                    this.transform(box.width, 0, 0, box.height, box.left, box.top);
                }
                var g = cacheGradient(this._pdf, gradient, box);
                var sname = g.shading._resourceName, oname;
                this._shResources[sname] = g.shading;
                if (g.hasAlpha) {
                    oname = g.opacity._resourceName;
                    this._gsResources[oname] = g.opacity;
                    this._out('/' + oname + ' gs ');
                }
                this._out('/' + sname + ' sh', NL);
                this.restore();
            },
            setDashPattern: function (dashArray, dashPhase) {
                this._out(dashArray, ' ', dashPhase, ' d', NL);
            },
            setLineWidth: function (width) {
                this._out(width, ' w', NL);
            },
            setLineCap: function (lineCap) {
                this._out(lineCap, ' J', NL);
            },
            setLineJoin: function (lineJoin) {
                this._out(lineJoin, ' j', NL);
            },
            setMitterLimit: function (mitterLimit) {
                this._out(mitterLimit, ' M', NL);
            },
            save: function () {
                this._contextStack.push(this._context());
                this._out('q', NL);
            },
            restore: function () {
                this._out('Q', NL);
                this._context(this._contextStack.pop());
            },
            moveTo: function (x, y) {
                this._out(x, ' ', y, ' m', NL);
            },
            lineTo: function (x, y) {
                this._out(x, ' ', y, ' l', NL);
            },
            bezier: function (x1, y1, x2, y2, x3, y3) {
                this._out(x1, ' ', y1, ' ', x2, ' ', y2, ' ', x3, ' ', y3, ' c', NL);
            },
            bezier1: function (x1, y1, x3, y3) {
                this._out(x1, ' ', y1, ' ', x3, ' ', y3, ' y', NL);
            },
            bezier2: function (x2, y2, x3, y3) {
                this._out(x2, ' ', y2, ' ', x3, ' ', y3, ' v', NL);
            },
            close: function () {
                this._out('h', NL);
            },
            rect: function (x, y, w, h) {
                this._out(x, ' ', y, ' ', w, ' ', h, ' re', NL);
            },
            ellipse: function (x, y, rx, ry) {
                function _X(v) {
                    return x + v;
                }
                function _Y(v) {
                    return y + v;
                }
                var k = 0.5522847498307936;
                this.moveTo(_X(0), _Y(ry));
                this.bezier(_X(rx * k), _Y(ry), _X(rx), _Y(ry * k), _X(rx), _Y(0));
                this.bezier(_X(rx), _Y(-ry * k), _X(rx * k), _Y(-ry), _X(0), _Y(-ry));
                this.bezier(_X(-rx * k), _Y(-ry), _X(-rx), _Y(-ry * k), _X(-rx), _Y(0));
                this.bezier(_X(-rx), _Y(ry * k), _X(-rx * k), _Y(ry), _X(0), _Y(ry));
            },
            circle: function (x, y, r) {
                this.ellipse(x, y, r, r);
            },
            stroke: function () {
                this._out('S', NL);
            },
            nop: function () {
                this._out('n', NL);
            },
            clip: function () {
                this._out('W n', NL);
            },
            clipStroke: function () {
                this._out('W S', NL);
            },
            closeStroke: function () {
                this._out('s', NL);
            },
            fill: function () {
                this._out('f', NL);
            },
            fillStroke: function () {
                this._out('B', NL);
            },
            drawImage: function (url) {
                var img = this._pdf.getImage(url);
                if (img) {
                    this._xResources[img._resourceName] = img;
                    this._out(img._resourceName, ' Do', NL);
                }
            },
            comment: function (txt) {
                var self = this;
                txt.split(/\r?\n/g).forEach(function (line) {
                    self._out('% ', line, NL);
                });
            },
            _context: function (val) {
                if (val != null) {
                    this._opacity = val.opacity;
                    this._matrix = val.matrix;
                } else {
                    return {
                        opacity: this._opacity,
                        matrix: this._matrix
                    };
                }
            },
            _toPage: function (p) {
                var m = this._matrix;
                var a = m[0], b = m[1], c = m[2], d = m[3], e = m[4], f = m[5];
                return {
                    x: a * p.x + c * p.y + e,
                    y: b * p.x + d * p.y + f
                };
            }
        }, PDFDictionary);
        function BinaryStream(data) {
            var offset = 0, length = 0;
            if (data == null) {
                data = HAS_TYPED_ARRAYS ? new Uint8Array(256) : [];
            } else {
                length = data.length;
            }
            var ensure = HAS_TYPED_ARRAYS ? function (len) {
                if (len >= data.length) {
                    var tmp = new Uint8Array(Math.max(len + 256, data.length * 2));
                    tmp.set(data, 0);
                    data = tmp;
                }
            } : function () {
            };
            var get = HAS_TYPED_ARRAYS ? function () {
                return new Uint8Array(data.buffer, 0, length);
            } : function () {
                return data;
            };
            var write = HAS_TYPED_ARRAYS ? function (bytes) {
                if (typeof bytes == 'string') {
                    return writeString(bytes);
                }
                var len = bytes.length;
                ensure(offset + len);
                data.set(bytes, offset);
                offset += len;
                if (offset > length) {
                    length = offset;
                }
            } : function (bytes) {
                if (typeof bytes == 'string') {
                    return writeString(bytes);
                }
                for (var i = 0; i < bytes.length; ++i) {
                    writeByte(bytes[i]);
                }
            };
            var slice = HAS_TYPED_ARRAYS ? function (start, length) {
                if (data.buffer.slice) {
                    return new Uint8Array(data.buffer.slice(start, start + length));
                } else {
                    var x = new Uint8Array(length);
                    x.set(new Uint8Array(data.buffer, start, length));
                    return x;
                }
            } : function (start, length) {
                return data.slice(start, start + length);
            };
            function eof() {
                return offset >= length;
            }
            function readByte() {
                return offset < length ? data[offset++] : 0;
            }
            function writeByte(b) {
                ensure(offset);
                data[offset++] = b & 255;
                if (offset > length) {
                    length = offset;
                }
            }
            function readShort() {
                return readByte() << 8 | readByte();
            }
            function writeShort(w) {
                writeByte(w >> 8);
                writeByte(w);
            }
            function readShort_() {
                var w = readShort();
                return w >= 32768 ? w - 65536 : w;
            }
            function writeShort_(w) {
                writeShort(w < 0 ? w + 65536 : w);
            }
            function readLong() {
                return readShort() * 65536 + readShort();
            }
            function writeLong(w) {
                writeShort(w >>> 16 & 65535);
                writeShort(w & 65535);
            }
            function readLong_() {
                var w = readLong();
                return w >= 2147483648 ? w - 4294967296 : w;
            }
            function writeLong_(w) {
                writeLong(w < 0 ? w + 4294967296 : w);
            }
            function readFixed() {
                return readLong() / 65536;
            }
            function writeFixed(f) {
                writeLong(Math.round(f * 65536));
            }
            function readFixed_() {
                return readLong_() / 65536;
            }
            function writeFixed_(f) {
                writeLong_(Math.round(f * 65536));
            }
            function read(len) {
                return times(len, readByte);
            }
            function readString(len) {
                return String.fromCharCode.apply(String, read(len));
            }
            function writeString(str) {
                for (var i = 0; i < str.length; ++i) {
                    writeByte(str.charCodeAt(i));
                }
            }
            function times(n, reader) {
                for (var ret = new Array(n), i = 0; i < n; ++i) {
                    ret[i] = reader();
                }
                return ret;
            }
            var stream = {
                eof: eof,
                readByte: readByte,
                writeByte: writeByte,
                readShort: readShort,
                writeShort: writeShort,
                readLong: readLong,
                writeLong: writeLong,
                readFixed: readFixed,
                writeFixed: writeFixed,
                readShort_: readShort_,
                writeShort_: writeShort_,
                readLong_: readLong_,
                writeLong_: writeLong_,
                readFixed_: readFixed_,
                writeFixed_: writeFixed_,
                read: read,
                write: write,
                readString: readString,
                writeString: writeString,
                times: times,
                get: get,
                slice: slice,
                offset: function (pos) {
                    if (pos != null) {
                        offset = pos;
                        return stream;
                    }
                    return offset;
                },
                skip: function (nbytes) {
                    offset += nbytes;
                },
                toString: function () {
                    throw new Error('FIX CALLER.  BinaryStream is no longer convertible to string!');
                },
                length: function () {
                    return length;
                },
                saveExcursion: function (f) {
                    var pos = offset;
                    try {
                        return f();
                    } finally {
                        offset = pos;
                    }
                },
                writeBase64: function (base64) {
                    if (window.atob) {
                        writeString(window.atob(base64));
                    } else {
                        write(BASE64.decode(base64));
                    }
                },
                base64: function () {
                    return BASE64.encode(get());
                }
            };
            return stream;
        }
        function unquote(str) {
            return str.replace(/^\s*(['"])(.*)\1\s*$/, '$2');
        }
        function parseFontDef(fontdef) {
            var rx = /^\s*((normal|italic)\s+)?((normal|small-caps)\s+)?((normal|bold|\d+)\s+)?(([0-9.]+)(px|pt))(\/(([0-9.]+)(px|pt)|normal))?\s+(.*?)\s*$/i;
            var m = rx.exec(fontdef);
            if (!m) {
                return {
                    fontSize: 12,
                    fontFamily: 'sans-serif'
                };
            }
            var fontSize = m[8] ? parseInt(m[8], 10) : 12;
            return {
                italic: m[2] && m[2].toLowerCase() == 'italic',
                variant: m[4],
                bold: m[6] && /bold|700/i.test(m[6]),
                fontSize: fontSize,
                lineHeight: m[12] ? m[12] == 'normal' ? fontSize : parseInt(m[12], 10) : null,
                fontFamily: m[14].split(/\s*,\s*/g).map(unquote)
            };
        }
        function getFontURL(style) {
            function mkFamily(name) {
                if (style.bold) {
                    name += '|bold';
                }
                if (style.italic) {
                    name += '|italic';
                }
                return name.toLowerCase();
            }
            var fontFamily = style.fontFamily;
            var name, url;
            if (fontFamily instanceof Array) {
                for (var i = 0; i < fontFamily.length; ++i) {
                    name = mkFamily(fontFamily[i]);
                    url = FONT_MAPPINGS[name];
                    if (url) {
                        break;
                    }
                }
            } else {
                url = FONT_MAPPINGS[fontFamily.toLowerCase()];
            }
            while (typeof url == 'function') {
                url = url();
            }
            if (!url) {
                url = 'Times-Roman';
            }
            return url;
        }
        var FONT_MAPPINGS = {
            'serif': 'Times-Roman',
            'serif|bold': 'Times-Bold',
            'serif|italic': 'Times-Italic',
            'serif|bold|italic': 'Times-BoldItalic',
            'sans-serif': 'Helvetica',
            'sans-serif|bold': 'Helvetica-Bold',
            'sans-serif|italic': 'Helvetica-Oblique',
            'sans-serif|bold|italic': 'Helvetica-BoldOblique',
            'monospace': 'Courier',
            'monospace|bold': 'Courier-Bold',
            'monospace|italic': 'Courier-Oblique',
            'monospace|bold|italic': 'Courier-BoldOblique',
            'zapfdingbats': 'ZapfDingbats',
            'zapfdingbats|bold': 'ZapfDingbats',
            'zapfdingbats|italic': 'ZapfDingbats',
            'zapfdingbats|bold|italic': 'ZapfDingbats'
        };
        function fontAlias(alias, name) {
            alias = alias.toLowerCase();
            FONT_MAPPINGS[alias] = function () {
                return FONT_MAPPINGS[name];
            };
            FONT_MAPPINGS[alias + '|bold'] = function () {
                return FONT_MAPPINGS[name + '|bold'];
            };
            FONT_MAPPINGS[alias + '|italic'] = function () {
                return FONT_MAPPINGS[name + '|italic'];
            };
            FONT_MAPPINGS[alias + '|bold|italic'] = function () {
                return FONT_MAPPINGS[name + '|bold|italic'];
            };
        }
        fontAlias('Times New Roman', 'serif');
        fontAlias('Courier New', 'monospace');
        fontAlias('Arial', 'sans-serif');
        fontAlias('Helvetica', 'sans-serif');
        fontAlias('Verdana', 'sans-serif');
        fontAlias('Tahoma', 'sans-serif');
        fontAlias('Georgia', 'sans-serif');
        fontAlias('Monaco', 'monospace');
        fontAlias('Andale Mono', 'monospace');
        function defineFont(name, url) {
            if (arguments.length == 1) {
                for (var i in name) {
                    if (hasOwnProperty(name, i)) {
                        defineFont(i, name[i]);
                    }
                }
            } else {
                name = name.toLowerCase();
                FONT_MAPPINGS[name] = url;
                switch (name) {
                case 'dejavu sans':
                    FONT_MAPPINGS['sans-serif'] = url;
                    break;
                case 'dejavu sans|bold':
                    FONT_MAPPINGS['sans-serif|bold'] = url;
                    break;
                case 'dejavu sans|italic':
                    FONT_MAPPINGS['sans-serif|italic'] = url;
                    break;
                case 'dejavu sans|bold|italic':
                    FONT_MAPPINGS['sans-serif|bold|italic'] = url;
                    break;
                case 'dejavu serif':
                    FONT_MAPPINGS['serif'] = url;
                    break;
                case 'dejavu serif|bold':
                    FONT_MAPPINGS['serif|bold'] = url;
                    break;
                case 'dejavu serif|italic':
                    FONT_MAPPINGS['serif|italic'] = url;
                    break;
                case 'dejavu serif|bold|italic':
                    FONT_MAPPINGS['serif|bold|italic'] = url;
                    break;
                case 'dejavu mono':
                    FONT_MAPPINGS['monospace'] = url;
                    break;
                case 'dejavu mono|bold':
                    FONT_MAPPINGS['monospace|bold'] = url;
                    break;
                case 'dejavu mono|italic':
                    FONT_MAPPINGS['monospace|italic'] = url;
                    break;
                case 'dejavu mono|bold|italic':
                    FONT_MAPPINGS['monospace|bold|italic'] = url;
                    break;
                }
            }
        }
        kendo.pdf = {
            Document: PDFDocument,
            BinaryStream: BinaryStream,
            defineFont: defineFont,
            parseFontDef: parseFontDef,
            getFontURL: getFontURL,
            loadFonts: loadFonts,
            loadImages: loadImages,
            getPaperOptions: getPaperOptions,
            TEXT_RENDERING_MODE: {
                fill: 0,
                stroke: 1,
                fillAndStroke: 2,
                invisible: 3,
                fillAndClip: 4,
                strokeAndClip: 5,
                fillStrokeClip: 6,
                clip: 7
            }
        };
        function mmul(a, b) {
            var a1 = a[0], b1 = a[1], c1 = a[2], d1 = a[3], e1 = a[4], f1 = a[5];
            var a2 = b[0], b2 = b[1], c2 = b[2], d2 = b[3], e2 = b[4], f2 = b[5];
            return [
                a1 * a2 + b1 * c2,
                a1 * b2 + b1 * d2,
                c1 * a2 + d1 * c2,
                c1 * b2 + d1 * d2,
                e1 * a2 + f1 * c2 + e2,
                e1 * b2 + f1 * d2 + f2
            ];
        }
        function isIdentityMatrix(m) {
            return m[0] === 1 && m[1] === 0 && m[2] === 0 && m[3] === 1 && m[4] === 0 && m[5] === 0;
        }
    }(window, parseFloat));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('pdf/ttf', [
        'pdf/core',
        'util/main'
    ], f);
}(function () {
    (function (window) {
        'use strict';
        function hasOwnProperty(obj, key) {
            return Object.prototype.hasOwnProperty.call(obj, key);
        }
        function sortedKeys(obj) {
            return Object.keys(obj).sort(function (a, b) {
                return a - b;
            }).map(parseFloat);
        }
        var PDF = window.kendo.pdf;
        var BinaryStream = PDF.BinaryStream;
        function Directory(data) {
            this.raw = data;
            this.scalerType = data.readLong();
            this.tableCount = data.readShort();
            this.searchRange = data.readShort();
            this.entrySelector = data.readShort();
            this.rangeShift = data.readShort();
            var tables = this.tables = {};
            for (var i = 0; i < this.tableCount; ++i) {
                var entry = {
                    tag: data.readString(4),
                    checksum: data.readLong(),
                    offset: data.readLong(),
                    length: data.readLong()
                };
                tables[entry.tag] = entry;
            }
        }
        Directory.prototype = {
            readTable: function (name, Ctor) {
                var def = this.tables[name];
                if (!def) {
                    throw new Error('Table ' + name + ' not found in directory');
                }
                return this[name] = def.table = new Ctor(this, def);
            },
            render: function (tables) {
                var tableCount = Object.keys(tables).length;
                var maxpow2 = Math.pow(2, Math.floor(Math.log(tableCount) / Math.LN2));
                var searchRange = maxpow2 * 16;
                var entrySelector = Math.floor(Math.log(maxpow2) / Math.LN2);
                var rangeShift = tableCount * 16 - searchRange;
                var out = BinaryStream();
                out.writeLong(this.scalerType);
                out.writeShort(tableCount);
                out.writeShort(searchRange);
                out.writeShort(entrySelector);
                out.writeShort(rangeShift);
                var directoryLength = tableCount * 16;
                var offset = out.offset() + directoryLength;
                var headOffset = null;
                var tableData = BinaryStream();
                for (var tag in tables) {
                    if (hasOwnProperty(tables, tag)) {
                        var table = tables[tag];
                        out.writeString(tag);
                        out.writeLong(this.checksum(table));
                        out.writeLong(offset);
                        out.writeLong(table.length);
                        tableData.write(table);
                        if (tag == 'head') {
                            headOffset = offset;
                        }
                        offset += table.length;
                        while (offset % 4) {
                            tableData.writeByte(0);
                            offset++;
                        }
                    }
                }
                out.write(tableData.get());
                var sum = this.checksum(out.get());
                var adjustment = 2981146554 - sum;
                out.offset(headOffset + 8);
                out.writeLong(adjustment);
                return out.get();
            },
            checksum: function (data) {
                data = BinaryStream(data);
                var sum = 0;
                while (!data.eof()) {
                    sum += data.readLong();
                }
                return sum & 4294967295;
            }
        };
        function deftable(methods) {
            function Ctor(file, def) {
                this.definition = def;
                this.length = def.length;
                this.offset = def.offset;
                this.file = file;
                this.rawData = file.raw;
                this.parse(file.raw);
            }
            Ctor.prototype.raw = function () {
                return this.rawData.slice(this.offset, this.length);
            };
            for (var i in methods) {
                if (hasOwnProperty(methods, i)) {
                    Ctor[i] = Ctor.prototype[i] = methods[i];
                }
            }
            return Ctor;
        }
        var HeadTable = deftable({
            parse: function (data) {
                data.offset(this.offset);
                this.version = data.readLong();
                this.revision = data.readLong();
                this.checkSumAdjustment = data.readLong();
                this.magicNumber = data.readLong();
                this.flags = data.readShort();
                this.unitsPerEm = data.readShort();
                this.created = data.read(8);
                this.modified = data.read(8);
                this.xMin = data.readShort_();
                this.yMin = data.readShort_();
                this.xMax = data.readShort_();
                this.yMax = data.readShort_();
                this.macStyle = data.readShort();
                this.lowestRecPPEM = data.readShort();
                this.fontDirectionHint = data.readShort_();
                this.indexToLocFormat = data.readShort_();
                this.glyphDataFormat = data.readShort_();
            },
            render: function (indexToLocFormat) {
                var out = BinaryStream();
                out.writeLong(this.version);
                out.writeLong(this.revision);
                out.writeLong(0);
                out.writeLong(this.magicNumber);
                out.writeShort(this.flags);
                out.writeShort(this.unitsPerEm);
                out.write(this.created);
                out.write(this.modified);
                out.writeShort_(this.xMin);
                out.writeShort_(this.yMin);
                out.writeShort_(this.xMax);
                out.writeShort_(this.yMax);
                out.writeShort(this.macStyle);
                out.writeShort(this.lowestRecPPEM);
                out.writeShort_(this.fontDirectionHint);
                out.writeShort_(indexToLocFormat);
                out.writeShort_(this.glyphDataFormat);
                return out.get();
            }
        });
        var LocaTable = deftable({
            parse: function (data) {
                data.offset(this.offset);
                var format = this.file.head.indexToLocFormat;
                if (format === 0) {
                    this.offsets = data.times(this.length / 2, function () {
                        return 2 * data.readShort();
                    });
                } else {
                    this.offsets = data.times(this.length / 4, data.readLong);
                }
            },
            offsetOf: function (id) {
                return this.offsets[id];
            },
            lengthOf: function (id) {
                return this.offsets[id + 1] - this.offsets[id];
            },
            render: function (offsets) {
                var out = BinaryStream();
                var needsLongFormat = offsets[offsets.length - 1] > 65535;
                for (var i = 0; i < offsets.length; ++i) {
                    if (needsLongFormat) {
                        out.writeLong(offsets[i]);
                    } else {
                        out.writeShort(offsets[i] / 2);
                    }
                }
                return {
                    format: needsLongFormat ? 1 : 0,
                    table: out.get()
                };
            }
        });
        var HheaTable = deftable({
            parse: function (data) {
                data.offset(this.offset);
                this.version = data.readLong();
                this.ascent = data.readShort_();
                this.descent = data.readShort_();
                this.lineGap = data.readShort_();
                this.advanceWidthMax = data.readShort();
                this.minLeftSideBearing = data.readShort_();
                this.minRightSideBearing = data.readShort_();
                this.xMaxExtent = data.readShort_();
                this.caretSlopeRise = data.readShort_();
                this.caretSlopeRun = data.readShort_();
                this.caretOffset = data.readShort_();
                data.skip(4 * 2);
                this.metricDataFormat = data.readShort_();
                this.numOfLongHorMetrics = data.readShort();
            },
            render: function (ids) {
                var out = BinaryStream();
                out.writeLong(this.version);
                out.writeShort_(this.ascent);
                out.writeShort_(this.descent);
                out.writeShort_(this.lineGap);
                out.writeShort(this.advanceWidthMax);
                out.writeShort_(this.minLeftSideBearing);
                out.writeShort_(this.minRightSideBearing);
                out.writeShort_(this.xMaxExtent);
                out.writeShort_(this.caretSlopeRise);
                out.writeShort_(this.caretSlopeRun);
                out.writeShort_(this.caretOffset);
                out.write([
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
                ]);
                out.writeShort_(this.metricDataFormat);
                out.writeShort(ids.length);
                return out.get();
            }
        });
        var MaxpTable = deftable({
            parse: function (data) {
                data.offset(this.offset);
                this.version = data.readLong();
                this.numGlyphs = data.readShort();
                this.maxPoints = data.readShort();
                this.maxContours = data.readShort();
                this.maxComponentPoints = data.readShort();
                this.maxComponentContours = data.readShort();
                this.maxZones = data.readShort();
                this.maxTwilightPoints = data.readShort();
                this.maxStorage = data.readShort();
                this.maxFunctionDefs = data.readShort();
                this.maxInstructionDefs = data.readShort();
                this.maxStackElements = data.readShort();
                this.maxSizeOfInstructions = data.readShort();
                this.maxComponentElements = data.readShort();
                this.maxComponentDepth = data.readShort();
            },
            render: function (glyphIds) {
                var out = BinaryStream();
                out.writeLong(this.version);
                out.writeShort(glyphIds.length);
                out.writeShort(this.maxPoints);
                out.writeShort(this.maxContours);
                out.writeShort(this.maxComponentPoints);
                out.writeShort(this.maxComponentContours);
                out.writeShort(this.maxZones);
                out.writeShort(this.maxTwilightPoints);
                out.writeShort(this.maxStorage);
                out.writeShort(this.maxFunctionDefs);
                out.writeShort(this.maxInstructionDefs);
                out.writeShort(this.maxStackElements);
                out.writeShort(this.maxSizeOfInstructions);
                out.writeShort(this.maxComponentElements);
                out.writeShort(this.maxComponentDepth);
                return out.get();
            }
        });
        var HmtxTable = deftable({
            parse: function (data) {
                data.offset(this.offset);
                var dir = this.file, hhea = dir.hhea;
                this.metrics = data.times(hhea.numOfLongHorMetrics, function () {
                    return {
                        advance: data.readShort(),
                        lsb: data.readShort_()
                    };
                });
                var lsbCount = dir.maxp.numGlyphs - dir.hhea.numOfLongHorMetrics;
                this.leftSideBearings = data.times(lsbCount, data.readShort_);
            },
            forGlyph: function (id) {
                var metrics = this.metrics;
                var n = metrics.length;
                if (id < n) {
                    return metrics[id];
                }
                return {
                    advance: metrics[n - 1].advance,
                    lsb: this.leftSideBearings[id - n]
                };
            },
            render: function (glyphIds) {
                var out = BinaryStream();
                for (var i = 0; i < glyphIds.length; ++i) {
                    var m = this.forGlyph(glyphIds[i]);
                    out.writeShort(m.advance);
                    out.writeShort_(m.lsb);
                }
                return out.get();
            }
        });
        var GlyfTable = function () {
            function SimpleGlyph(raw) {
                this.raw = raw;
            }
            SimpleGlyph.prototype = {
                compound: false,
                render: function () {
                    return this.raw.get();
                }
            };
            var ARG_1_AND_2_ARE_WORDS = 1;
            var WE_HAVE_A_SCALE = 8;
            var MORE_COMPONENTS = 32;
            var WE_HAVE_AN_X_AND_Y_SCALE = 64;
            var WE_HAVE_A_TWO_BY_TWO = 128;
            function CompoundGlyph(data) {
                this.raw = data;
                var ids = this.glyphIds = [];
                var offsets = this.idOffsets = [];
                while (true) {
                    var flags = data.readShort();
                    offsets.push(data.offset());
                    ids.push(data.readShort());
                    if (!(flags & MORE_COMPONENTS)) {
                        break;
                    }
                    data.skip(flags & ARG_1_AND_2_ARE_WORDS ? 4 : 2);
                    if (flags & WE_HAVE_A_TWO_BY_TWO) {
                        data.skip(8);
                    } else if (flags & WE_HAVE_AN_X_AND_Y_SCALE) {
                        data.skip(4);
                    } else if (flags & WE_HAVE_A_SCALE) {
                        data.skip(2);
                    }
                }
            }
            CompoundGlyph.prototype = {
                compound: true,
                render: function (old2new) {
                    var out = BinaryStream(this.raw.get());
                    for (var i = 0; i < this.glyphIds.length; ++i) {
                        var id = this.glyphIds[i];
                        out.offset(this.idOffsets[i]);
                        out.writeShort(old2new[id]);
                    }
                    return out.get();
                }
            };
            return deftable({
                parse: function () {
                    this.cache = {};
                },
                glyphFor: function (id) {
                    var cache = this.cache;
                    if (hasOwnProperty(cache, id)) {
                        return cache[id];
                    }
                    var loca = this.file.loca;
                    var length = loca.lengthOf(id);
                    if (length === 0) {
                        return cache[id] = null;
                    }
                    var data = this.rawData;
                    var offset = this.offset + loca.offsetOf(id);
                    var raw = BinaryStream(data.slice(offset, length));
                    var numberOfContours = raw.readShort_();
                    var xMin = raw.readShort_();
                    var yMin = raw.readShort_();
                    var xMax = raw.readShort_();
                    var yMax = raw.readShort_();
                    var glyph = cache[id] = numberOfContours == -1 ? new CompoundGlyph(raw) : new SimpleGlyph(raw);
                    glyph.numberOfContours = numberOfContours;
                    glyph.xMin = xMin;
                    glyph.yMin = yMin;
                    glyph.xMax = xMax;
                    glyph.yMax = yMax;
                    return glyph;
                },
                render: function (glyphs, oldIds, old2new) {
                    var out = BinaryStream(), offsets = [];
                    for (var i = 0; i < oldIds.length; ++i) {
                        var id = oldIds[i];
                        var glyph = glyphs[id];
                        offsets.push(out.offset());
                        if (glyph) {
                            out.write(glyph.render(old2new));
                        }
                    }
                    offsets.push(out.offset());
                    return {
                        table: out.get(),
                        offsets: offsets
                    };
                }
            });
        }();
        var NameTable = function () {
            function NameEntry(text, entry) {
                this.text = text;
                this.length = text.length;
                this.platformID = entry.platformID;
                this.platformSpecificID = entry.platformSpecificID;
                this.languageID = entry.languageID;
                this.nameID = entry.nameID;
            }
            return deftable({
                parse: function (data) {
                    data.offset(this.offset);
                    data.readShort();
                    var count = data.readShort();
                    var stringOffset = this.offset + data.readShort();
                    var nameRecords = data.times(count, function () {
                        return {
                            platformID: data.readShort(),
                            platformSpecificID: data.readShort(),
                            languageID: data.readShort(),
                            nameID: data.readShort(),
                            length: data.readShort(),
                            offset: data.readShort() + stringOffset
                        };
                    });
                    var strings = this.strings = {};
                    for (var i = 0; i < nameRecords.length; ++i) {
                        var rec = nameRecords[i];
                        data.offset(rec.offset);
                        var text = data.readString(rec.length);
                        if (!strings[rec.nameID]) {
                            strings[rec.nameID] = [];
                        }
                        strings[rec.nameID].push(new NameEntry(text, rec));
                    }
                    this.postscriptEntry = strings[6][0];
                    this.postscriptName = this.postscriptEntry.text.replace(/[^\x20-\x7F]/g, '');
                },
                render: function (psName) {
                    var strings = this.strings;
                    var strCount = 0;
                    for (var i in strings) {
                        if (hasOwnProperty(strings, i)) {
                            strCount += strings[i].length;
                        }
                    }
                    var out = BinaryStream();
                    var strTable = BinaryStream();
                    out.writeShort(0);
                    out.writeShort(strCount);
                    out.writeShort(6 + 12 * strCount);
                    for (i in strings) {
                        if (hasOwnProperty(strings, i)) {
                            var list = i == 6 ? [new NameEntry(psName, this.postscriptEntry)] : strings[i];
                            for (var j = 0; j < list.length; ++j) {
                                var str = list[j];
                                out.writeShort(str.platformID);
                                out.writeShort(str.platformSpecificID);
                                out.writeShort(str.languageID);
                                out.writeShort(str.nameID);
                                out.writeShort(str.length);
                                out.writeShort(strTable.offset());
                                strTable.writeString(str.text);
                            }
                        }
                    }
                    out.write(strTable.get());
                    return out.get();
                }
            });
        }();
        var PostTable = function () {
            var POSTSCRIPT_GLYPHS = '.notdef .null nonmarkingreturn space exclam quotedbl numbersign dollar percent ampersand quotesingle parenleft parenright asterisk plus comma hyphen period slash zero one two three four five six seven eight nine colon semicolon less equal greater question at A B C D E F G H I J K L M N O P Q R S T U V W X Y Z bracketleft backslash bracketright asciicircum underscore grave a b c d e f g h i j k l m n o p q r s t u v w x y z braceleft bar braceright asciitilde Adieresis Aring Ccedilla Eacute Ntilde Odieresis Udieresis aacute agrave acircumflex adieresis atilde aring ccedilla eacute egrave ecircumflex edieresis iacute igrave icircumflex idieresis ntilde oacute ograve ocircumflex odieresis otilde uacute ugrave ucircumflex udieresis dagger degree cent sterling section bullet paragraph germandbls registered copyright trademark acute dieresis notequal AE Oslash infinity plusminus lessequal greaterequal yen mu partialdiff summation product pi integral ordfeminine ordmasculine Omega ae oslash questiondown exclamdown logicalnot radical florin approxequal Delta guillemotleft guillemotright ellipsis nonbreakingspace Agrave Atilde Otilde OE oe endash emdash quotedblleft quotedblright quoteleft quoteright divide lozenge ydieresis Ydieresis fraction currency guilsinglleft guilsinglright fi fl daggerdbl periodcentered quotesinglbase quotedblbase perthousand Acircumflex Ecircumflex Aacute Edieresis Egrave Iacute Icircumflex Idieresis Igrave Oacute Ocircumflex apple Ograve Uacute Ucircumflex Ugrave dotlessi circumflex tilde macron breve dotaccent ring cedilla hungarumlaut ogonek caron Lslash lslash Scaron scaron Zcaron zcaron brokenbar Eth eth Yacute yacute Thorn thorn minus multiply onesuperior twosuperior threesuperior onehalf onequarter threequarters franc Gbreve gbreve Idotaccent Scedilla scedilla Cacute cacute Ccaron ccaron dcroat'.split(/\s+/g);
            return deftable({
                parse: function (data) {
                    data.offset(this.offset);
                    this.format = data.readLong();
                    this.italicAngle = data.readFixed_();
                    this.underlinePosition = data.readShort_();
                    this.underlineThickness = data.readShort_();
                    this.isFixedPitch = data.readLong();
                    this.minMemType42 = data.readLong();
                    this.maxMemType42 = data.readLong();
                    this.minMemType1 = data.readLong();
                    this.maxMemType1 = data.readLong();
                    var numberOfGlyphs;
                    switch (this.format) {
                    case 65536:
                    case 196608:
                        break;
                    case 131072:
                        numberOfGlyphs = data.readShort();
                        this.glyphNameIndex = data.times(numberOfGlyphs, data.readShort);
                        this.names = [];
                        var limit = this.offset + this.length;
                        while (data.offset() < limit) {
                            this.names.push(data.readString(data.readByte()));
                        }
                        break;
                    case 151552:
                        numberOfGlyphs = data.readShort();
                        this.offsets = data.read(numberOfGlyphs);
                        break;
                    case 262144:
                        this.map = data.times(this.file.maxp.numGlyphs, data.readShort);
                        break;
                    }
                },
                glyphFor: function (code) {
                    switch (this.format) {
                    case 65536:
                        return POSTSCRIPT_GLYPHS[code] || '.notdef';
                    case 131072:
                        var index = this.glyphNameIndex[code];
                        if (index < POSTSCRIPT_GLYPHS.length) {
                            return POSTSCRIPT_GLYPHS[index];
                        }
                        return this.names[index - POSTSCRIPT_GLYPHS.length] || '.notdef';
                    case 151552:
                    case 196608:
                        return '.notdef';
                    case 262144:
                        return this.map[code] || 65535;
                    }
                },
                render: function (mapping) {
                    if (this.format == 196608) {
                        return this.raw();
                    }
                    var out = BinaryStream(this.rawData.slice(this.offset, 32));
                    out.writeLong(131072);
                    out.offset(32);
                    var indexes = [];
                    var strings = [];
                    for (var i = 0; i < mapping.length; ++i) {
                        var id = mapping[i];
                        var post = this.glyphFor(id);
                        var index = POSTSCRIPT_GLYPHS.indexOf(post);
                        if (index >= 0) {
                            indexes.push(index);
                        } else {
                            indexes.push(POSTSCRIPT_GLYPHS.length + strings.length);
                            strings.push(post);
                        }
                    }
                    out.writeShort(mapping.length);
                    for (i = 0; i < indexes.length; ++i) {
                        out.writeShort(indexes[i]);
                    }
                    for (i = 0; i < strings.length; ++i) {
                        out.writeByte(strings[i].length);
                        out.writeString(strings[i]);
                    }
                    return out.get();
                }
            });
        }();
        var CmapTable = function () {
            function CmapEntry(data, offset, codeMap) {
                var self = this;
                self.platformID = data.readShort();
                self.platformSpecificID = data.readShort();
                self.offset = offset + data.readLong();
                data.saveExcursion(function () {
                    var code;
                    data.offset(self.offset);
                    self.format = data.readShort();
                    switch (self.format) {
                    case 0:
                        self.length = data.readShort();
                        self.language = data.readShort();
                        for (var i = 0; i < 256; ++i) {
                            codeMap[i] = data.readByte();
                        }
                        break;
                    case 4:
                        self.length = data.readShort();
                        self.language = data.readShort();
                        var segCount = data.readShort() / 2;
                        data.skip(6);
                        var endCode = data.times(segCount, data.readShort);
                        data.skip(2);
                        var startCode = data.times(segCount, data.readShort);
                        var idDelta = data.times(segCount, data.readShort_);
                        var idRangeOffset = data.times(segCount, data.readShort);
                        var count = (self.length + self.offset - data.offset()) / 2;
                        var glyphIds = data.times(count, data.readShort);
                        for (i = 0; i < segCount; ++i) {
                            var start = startCode[i], end = endCode[i];
                            for (code = start; code <= end; ++code) {
                                var glyphId;
                                if (idRangeOffset[i] === 0) {
                                    glyphId = code + idDelta[i];
                                } else {
                                    var index = idRangeOffset[i] / 2 - (segCount - i) + (code - start);
                                    glyphId = glyphIds[index] || 0;
                                    if (glyphId !== 0) {
                                        glyphId += idDelta[i];
                                    }
                                }
                                codeMap[code] = glyphId & 65535;
                            }
                        }
                        break;
                    case 6:
                        self.length = data.readShort();
                        self.language = data.readShort();
                        code = data.readShort();
                        var length = data.readShort();
                        while (length-- > 0) {
                            codeMap[code++] = data.readShort();
                        }
                        break;
                    case 12:
                        data.readShort();
                        self.length = data.readLong();
                        self.language = data.readLong();
                        var ngroups = data.readLong();
                        while (ngroups-- > 0) {
                            code = data.readLong();
                            var endCharCode = data.readLong();
                            var glyphCode = data.readLong();
                            while (code <= endCharCode) {
                                codeMap[code++] = glyphCode++;
                            }
                        }
                        break;
                    default:
                        if (window.console) {
                            window.console.error('Unhandled CMAP format: ' + self.format);
                        }
                    }
                });
            }
            function renderCharmap(ncid2ogid, ogid2ngid) {
                var codes = sortedKeys(ncid2ogid);
                var startCodes = [];
                var endCodes = [];
                var last = null;
                var diff = null;
                function new_gid(charcode) {
                    return ogid2ngid[ncid2ogid[charcode]];
                }
                for (var i = 0; i < codes.length; ++i) {
                    var code = codes[i];
                    var gid = new_gid(code);
                    var delta = gid - code;
                    if (last == null || delta !== diff) {
                        if (last) {
                            endCodes.push(last);
                        }
                        startCodes.push(code);
                        diff = delta;
                    }
                    last = code;
                }
                if (last) {
                    endCodes.push(last);
                }
                endCodes.push(65535);
                startCodes.push(65535);
                var segCount = startCodes.length;
                var segCountX2 = segCount * 2;
                var searchRange = 2 * Math.pow(2, Math.floor(Math.log(segCount) / Math.LN2));
                var entrySelector = Math.log(searchRange / 2) / Math.LN2;
                var rangeShift = segCountX2 - searchRange;
                var deltas = [];
                var rangeOffsets = [];
                var glyphIds = [];
                for (i = 0; i < segCount; ++i) {
                    var startCode = startCodes[i];
                    var endCode = endCodes[i];
                    if (startCode == 65535) {
                        deltas.push(0);
                        rangeOffsets.push(0);
                        break;
                    }
                    var startGlyph = new_gid(startCode);
                    if (startCode - startGlyph >= 32768) {
                        deltas.push(0);
                        rangeOffsets.push(2 * (glyphIds.length + segCount - i));
                        for (var j = startCode; j <= endCode; ++j) {
                            glyphIds.push(new_gid(j));
                        }
                    } else {
                        deltas.push(startGlyph - startCode);
                        rangeOffsets.push(0);
                    }
                }
                var out = BinaryStream();
                out.writeShort(3);
                out.writeShort(1);
                out.writeLong(12);
                out.writeShort(4);
                out.writeShort(16 + segCount * 8 + glyphIds.length * 2);
                out.writeShort(0);
                out.writeShort(segCountX2);
                out.writeShort(searchRange);
                out.writeShort(entrySelector);
                out.writeShort(rangeShift);
                endCodes.forEach(out.writeShort);
                out.writeShort(0);
                startCodes.forEach(out.writeShort);
                deltas.forEach(out.writeShort_);
                rangeOffsets.forEach(out.writeShort);
                glyphIds.forEach(out.writeShort);
                return out.get();
            }
            return deftable({
                parse: function (data) {
                    var self = this;
                    var offset = self.offset;
                    data.offset(offset);
                    self.codeMap = {};
                    self.version = data.readShort();
                    var tableCount = data.readShort();
                    self.tables = data.times(tableCount, function () {
                        return new CmapEntry(data, offset, self.codeMap);
                    });
                },
                render: function (ncid2ogid, ogid2ngid) {
                    var out = BinaryStream();
                    out.writeShort(0);
                    out.writeShort(1);
                    out.write(renderCharmap(ncid2ogid, ogid2ngid));
                    return out.get();
                }
            });
        }();
        var OS2Table = deftable({
            parse: function (data) {
                data.offset(this.offset);
                this.version = data.readShort();
                this.averageCharWidth = data.readShort_();
                this.weightClass = data.readShort();
                this.widthClass = data.readShort();
                this.type = data.readShort();
                this.ySubscriptXSize = data.readShort_();
                this.ySubscriptYSize = data.readShort_();
                this.ySubscriptXOffset = data.readShort_();
                this.ySubscriptYOffset = data.readShort_();
                this.ySuperscriptXSize = data.readShort_();
                this.ySuperscriptYSize = data.readShort_();
                this.ySuperscriptXOffset = data.readShort_();
                this.ySuperscriptYOffset = data.readShort_();
                this.yStrikeoutSize = data.readShort_();
                this.yStrikeoutPosition = data.readShort_();
                this.familyClass = data.readShort_();
                this.panose = data.times(10, data.readByte);
                this.charRange = data.times(4, data.readLong);
                this.vendorID = data.readString(4);
                this.selection = data.readShort();
                this.firstCharIndex = data.readShort();
                this.lastCharIndex = data.readShort();
                if (this.version > 0) {
                    this.ascent = data.readShort_();
                    this.descent = data.readShort_();
                    this.lineGap = data.readShort_();
                    this.winAscent = data.readShort();
                    this.winDescent = data.readShort();
                    this.codePageRange = data.times(2, data.readLong);
                    if (this.version > 1) {
                        this.xHeight = data.readShort();
                        this.capHeight = data.readShort();
                        this.defaultChar = data.readShort();
                        this.breakChar = data.readShort();
                        this.maxContext = data.readShort();
                    }
                }
            },
            render: function () {
                return this.raw();
            }
        });
        var subsetTag = 100000;
        function nextSubsetTag() {
            var ret = '', n = subsetTag + '';
            for (var i = 0; i < n.length; ++i) {
                ret += String.fromCharCode(n.charCodeAt(i) - 48 + 65);
            }
            ++subsetTag;
            return ret;
        }
        function Subfont(font) {
            this.font = font;
            this.subset = {};
            this.unicodes = {};
            this.ogid2ngid = { 0: 0 };
            this.ngid2ogid = { 0: 0 };
            this.ncid2ogid = {};
            this.next = this.firstChar = 1;
            this.nextGid = 1;
            this.psName = nextSubsetTag() + '+' + this.font.psName;
        }
        Subfont.prototype = {
            use: function (ch) {
                var self = this;
                if (typeof ch == 'string') {
                    return kendo.util.ucs2decode(ch).reduce(function (ret, code) {
                        return ret + String.fromCharCode(self.use(code));
                    }, '');
                }
                var code = self.unicodes[ch];
                if (!code) {
                    code = self.next++;
                    self.subset[code] = ch;
                    self.unicodes[ch] = code;
                    var old_gid = self.font.cmap.codeMap[ch];
                    if (old_gid) {
                        self.ncid2ogid[code] = old_gid;
                        if (self.ogid2ngid[old_gid] == null) {
                            var new_gid = self.nextGid++;
                            self.ogid2ngid[old_gid] = new_gid;
                            self.ngid2ogid[new_gid] = old_gid;
                        }
                    }
                }
                return code;
            },
            encodeText: function (text) {
                return this.use(text);
            },
            glyphIds: function () {
                return sortedKeys(this.ogid2ngid);
            },
            glyphsFor: function (glyphIds, result) {
                if (!result) {
                    result = {};
                }
                for (var i = 0; i < glyphIds.length; ++i) {
                    var id = glyphIds[i];
                    if (!result[id]) {
                        var glyph = result[id] = this.font.glyf.glyphFor(id);
                        if (glyph && glyph.compound) {
                            this.glyphsFor(glyph.glyphIds, result);
                        }
                    }
                }
                return result;
            },
            render: function () {
                var glyphs = this.glyphsFor(this.glyphIds());
                for (var old_gid in glyphs) {
                    if (hasOwnProperty(glyphs, old_gid)) {
                        old_gid = parseInt(old_gid, 10);
                        if (this.ogid2ngid[old_gid] == null) {
                            var new_gid = this.nextGid++;
                            this.ogid2ngid[old_gid] = new_gid;
                            this.ngid2ogid[new_gid] = old_gid;
                        }
                    }
                }
                var new_gid_ids = sortedKeys(this.ngid2ogid);
                var old_gid_ids = new_gid_ids.map(function (id) {
                    return this.ngid2ogid[id];
                }, this);
                var font = this.font;
                var glyf = font.glyf.render(glyphs, old_gid_ids, this.ogid2ngid);
                var loca = font.loca.render(glyf.offsets);
                this.lastChar = this.next - 1;
                var tables = {
                    'cmap': CmapTable.render(this.ncid2ogid, this.ogid2ngid),
                    'glyf': glyf.table,
                    'loca': loca.table,
                    'hmtx': font.hmtx.render(old_gid_ids),
                    'hhea': font.hhea.render(old_gid_ids),
                    'maxp': font.maxp.render(old_gid_ids),
                    'post': font.post.render(old_gid_ids),
                    'name': font.name.render(this.psName),
                    'head': font.head.render(loca.format),
                    'OS/2': font.os2.render()
                };
                return this.font.directory.render(tables);
            },
            cidToGidMap: function () {
                var out = BinaryStream(), len = 0;
                for (var cid = this.firstChar; cid < this.next; ++cid) {
                    while (len < cid) {
                        out.writeShort(0);
                        len++;
                    }
                    var old_gid = this.ncid2ogid[cid];
                    if (old_gid) {
                        var new_gid = this.ogid2ngid[old_gid];
                        out.writeShort(new_gid);
                    } else {
                        out.writeShort(0);
                    }
                    len++;
                }
                return out.get();
            }
        };
        function TTFFont(rawData, name) {
            var self = this;
            var data = self.contents = BinaryStream(rawData);
            if (data.readString(4) == 'ttcf') {
                if (!name) {
                    throw new Error('Must specify a name for TTC files');
                }
                data.readLong();
                var numFonts = data.readLong();
                for (var i = 0; i < numFonts; ++i) {
                    var offset = data.readLong();
                    data.saveExcursion(function () {
                        data.offset(offset);
                        self.parse();
                    });
                    if (self.psName == name) {
                        return;
                    }
                }
                throw new Error('Font ' + name + ' not found in collection');
            } else {
                data.offset(0);
                self.parse();
            }
        }
        TTFFont.prototype = {
            parse: function () {
                var dir = this.directory = new Directory(this.contents);
                this.head = dir.readTable('head', HeadTable);
                this.loca = dir.readTable('loca', LocaTable);
                this.hhea = dir.readTable('hhea', HheaTable);
                this.maxp = dir.readTable('maxp', MaxpTable);
                this.hmtx = dir.readTable('hmtx', HmtxTable);
                this.glyf = dir.readTable('glyf', GlyfTable);
                this.name = dir.readTable('name', NameTable);
                this.post = dir.readTable('post', PostTable);
                this.cmap = dir.readTable('cmap', CmapTable);
                this.os2 = dir.readTable('OS/2', OS2Table);
                this.psName = this.name.postscriptName;
                this.ascent = this.os2.ascent || this.hhea.ascent;
                this.descent = this.os2.descent || this.hhea.descent;
                this.lineGap = this.os2.lineGap || this.hhea.lineGap;
                this.scale = 1000 / this.head.unitsPerEm;
            },
            widthOfGlyph: function (glyph) {
                return this.hmtx.forGlyph(glyph).advance * this.scale;
            },
            makeSubset: function () {
                return new Subfont(this);
            }
        };
        PDF.TTFFont = TTFFont;
    }(window));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('util/text-metrics', [
        'kendo.core',
        'util/main'
    ], f);
}(function () {
    (function ($) {
        var doc = document, kendo = window.kendo, Class = kendo.Class, util = kendo.util, defined = util.defined;
        var LRUCache = Class.extend({
            init: function (size) {
                this._size = size;
                this._length = 0;
                this._map = {};
            },
            put: function (key, value) {
                var lru = this, map = lru._map, entry = {
                        key: key,
                        value: value
                    };
                map[key] = entry;
                if (!lru._head) {
                    lru._head = lru._tail = entry;
                } else {
                    lru._tail.newer = entry;
                    entry.older = lru._tail;
                    lru._tail = entry;
                }
                if (lru._length >= lru._size) {
                    map[lru._head.key] = null;
                    lru._head = lru._head.newer;
                    lru._head.older = null;
                } else {
                    lru._length++;
                }
            },
            get: function (key) {
                var lru = this, entry = lru._map[key];
                if (entry) {
                    if (entry === lru._head && entry !== lru._tail) {
                        lru._head = entry.newer;
                        lru._head.older = null;
                    }
                    if (entry !== lru._tail) {
                        if (entry.older) {
                            entry.older.newer = entry.newer;
                            entry.newer.older = entry.older;
                        }
                        entry.older = lru._tail;
                        entry.newer = null;
                        lru._tail.newer = entry;
                        lru._tail = entry;
                    }
                    return entry.value;
                }
            }
        });
        var defaultMeasureBox = $('<div style=\'position: absolute !important; top: -4000px !important; width: auto !important; height: auto !important;' + 'padding: 0 !important; margin: 0 !important; border: 0 !important;' + 'line-height: normal !important; visibility: hidden !important; white-space: nowrap!important;\' />')[0];
        function zeroSize() {
            return {
                width: 0,
                height: 0,
                baseline: 0
            };
        }
        var TextMetrics = Class.extend({
            init: function (options) {
                this._cache = new LRUCache(1000);
                this._initOptions(options);
            },
            options: { baselineMarkerSize: 1 },
            measure: function (text, style, box) {
                if (!text) {
                    return zeroSize();
                }
                var styleKey = util.objectKey(style), cacheKey = util.hashKey(text + styleKey), cachedResult = this._cache.get(cacheKey);
                if (cachedResult) {
                    return cachedResult;
                }
                var size = zeroSize();
                var measureBox = box ? box : defaultMeasureBox;
                var baselineMarker = this._baselineMarker().cloneNode(false);
                for (var key in style) {
                    var value = style[key];
                    if (defined(value)) {
                        measureBox.style[key] = value;
                    }
                }
                $(measureBox).text(text);
                measureBox.appendChild(baselineMarker);
                doc.body.appendChild(measureBox);
                if ((text + '').length) {
                    size.width = measureBox.offsetWidth - this.options.baselineMarkerSize;
                    size.height = measureBox.offsetHeight;
                    size.baseline = baselineMarker.offsetTop + this.options.baselineMarkerSize;
                }
                if (size.width > 0 && size.height > 0) {
                    this._cache.put(cacheKey, size);
                }
                measureBox.parentNode.removeChild(measureBox);
                return size;
            },
            _baselineMarker: function () {
                return $('<div class=\'k-baseline-marker\' ' + 'style=\'display: inline-block; vertical-align: baseline;' + 'width: ' + this.options.baselineMarkerSize + 'px; height: ' + this.options.baselineMarkerSize + 'px;' + 'overflow: hidden;\' />')[0];
            }
        });
        TextMetrics.current = new TextMetrics();
        function measureText(text, style, measureBox) {
            return TextMetrics.current.measure(text, style, measureBox);
        }
        function loadFonts(fonts, callback) {
            var promises = [];
            if (fonts.length > 0 && document.fonts) {
                try {
                    promises = fonts.map(function (font) {
                        return document.fonts.load(font);
                    });
                } catch (e) {
                    kendo.logToConsole(e);
                }
                Promise.all(promises).then(callback, callback);
            } else {
                callback();
            }
        }
        kendo.util.TextMetrics = TextMetrics;
        kendo.util.LRUCache = LRUCache;
        kendo.util.loadFonts = loadFonts;
        kendo.util.measureText = measureText;
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('util/base64', ['util/main'], f);
}(function () {
    (function () {
        var kendo = window.kendo, deepExtend = kendo.deepExtend, fromCharCode = String.fromCharCode;
        var KEY_STR = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
        function encodeBase64(input) {
            var output = '';
            var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
            var i = 0;
            input = encodeUTF8(input);
            while (i < input.length) {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
                enc1 = chr1 >> 2;
                enc2 = (chr1 & 3) << 4 | chr2 >> 4;
                enc3 = (chr2 & 15) << 2 | chr3 >> 6;
                enc4 = chr3 & 63;
                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }
                output = output + KEY_STR.charAt(enc1) + KEY_STR.charAt(enc2) + KEY_STR.charAt(enc3) + KEY_STR.charAt(enc4);
            }
            return output;
        }
        function encodeUTF8(input) {
            var output = '';
            for (var i = 0; i < input.length; i++) {
                var c = input.charCodeAt(i);
                if (c < 128) {
                    output += fromCharCode(c);
                } else if (c < 2048) {
                    output += fromCharCode(192 | c >>> 6);
                    output += fromCharCode(128 | c & 63);
                } else if (c < 65536) {
                    output += fromCharCode(224 | c >>> 12);
                    output += fromCharCode(128 | c >>> 6 & 63);
                    output += fromCharCode(128 | c & 63);
                }
            }
            return output;
        }
        deepExtend(kendo.util, {
            encodeBase64: encodeBase64,
            encodeUTF8: encodeUTF8
        });
    }());
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('mixins/observers', ['kendo.core'], f);
}(function () {
    (function ($) {
        var math = Math, kendo = window.kendo, deepExtend = kendo.deepExtend, inArray = $.inArray;
        var ObserversMixin = {
            observers: function () {
                this._observers = this._observers || [];
                return this._observers;
            },
            addObserver: function (element) {
                if (!this._observers) {
                    this._observers = [element];
                } else {
                    this._observers.push(element);
                }
                return this;
            },
            removeObserver: function (element) {
                var observers = this.observers();
                var index = inArray(element, observers);
                if (index != -1) {
                    observers.splice(index, 1);
                }
                return this;
            },
            trigger: function (methodName, event) {
                var observers = this._observers;
                var observer;
                var idx;
                if (observers && !this._suspended) {
                    for (idx = 0; idx < observers.length; idx++) {
                        observer = observers[idx];
                        if (observer[methodName]) {
                            observer[methodName](event);
                        }
                    }
                }
                return this;
            },
            optionsChange: function (e) {
                e = e || {};
                e.element = this;
                this.trigger('optionsChange', e);
            },
            geometryChange: function () {
                this.trigger('geometryChange', { element: this });
            },
            suspend: function () {
                this._suspended = (this._suspended || 0) + 1;
                return this;
            },
            resume: function () {
                this._suspended = math.max((this._suspended || 0) - 1, 0);
                return this;
            },
            _observerField: function (field, value) {
                if (this[field]) {
                    this[field].removeObserver(this);
                }
                this[field] = value;
                value.addObserver(this);
            }
        };
        deepExtend(kendo, { mixins: { ObserversMixin: ObserversMixin } });
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('pdf/drawing', [
        'kendo.core',
        'kendo.color',
        'kendo.drawing',
        'pdf/core'
    ], f);
}(function () {
    (function (kendo, $) {
        'use strict';
        var drawing = kendo.drawing;
        var geo = kendo.geometry;
        var TEXT_RENDERING_MODE = kendo.pdf.TEXT_RENDERING_MODE;
        var DASH_PATTERNS = {
            dash: [4],
            dashDot: [
                4,
                2,
                1,
                2
            ],
            dot: [
                1,
                2
            ],
            longDash: [
                8,
                2
            ],
            longDashDot: [
                8,
                2,
                1,
                2
            ],
            longDashDotDot: [
                8,
                2,
                1,
                2,
                1,
                2
            ],
            solid: []
        };
        var LINE_CAP = {
            butt: 0,
            round: 1,
            square: 2
        };
        var LINE_JOIN = {
            miter: 0,
            round: 1,
            bevel: 2
        };
        function render(group, callback) {
            var fonts = [], images = [], options = group.options;
            function getOption(name, defval, hash) {
                if (!hash) {
                    hash = options;
                }
                if (hash.pdf && hash.pdf[name] != null) {
                    return hash.pdf[name];
                }
                return defval;
            }
            var multiPage = getOption('multiPage');
            group.traverse(function (element) {
                dispatch({
                    Image: function (element) {
                        if (images.indexOf(element.src()) < 0) {
                            images.push(element.src());
                        }
                    },
                    Text: function (element) {
                        var style = kendo.pdf.parseFontDef(element.options.font);
                        var url = kendo.pdf.getFontURL(style);
                        if (fonts.indexOf(url) < 0) {
                            fonts.push(url);
                        }
                    }
                }, element);
            });
            function doIt() {
                if (--count > 0) {
                    return;
                }
                var pdf = new kendo.pdf.Document({
                    producer: getOption('producer'),
                    title: getOption('title'),
                    author: getOption('author'),
                    subject: getOption('subject'),
                    keywords: getOption('keywords'),
                    creator: getOption('creator'),
                    date: getOption('date')
                });
                function drawPage(group) {
                    var options = group.options;
                    var tmp = optimize(group);
                    var bbox = tmp.bbox;
                    group = tmp.root;
                    var paperSize = getOption('paperSize', getOption('paperSize', 'auto'), options), addMargin = false;
                    if (paperSize == 'auto') {
                        if (bbox) {
                            var size = bbox.getSize();
                            paperSize = [
                                size.width,
                                size.height
                            ];
                            addMargin = true;
                            var origin = bbox.getOrigin();
                            tmp = new drawing.Group();
                            tmp.transform(new geo.Matrix(1, 0, 0, 1, -origin.x, -origin.y));
                            tmp.append(group);
                            group = tmp;
                        } else {
                            paperSize = 'A4';
                        }
                    }
                    var page;
                    page = pdf.addPage({
                        paperSize: paperSize,
                        margin: getOption('margin', getOption('margin'), options),
                        addMargin: addMargin,
                        landscape: getOption('landscape', getOption('landscape', false), options)
                    });
                    drawElement(group, page, pdf);
                }
                if (multiPage) {
                    group.children.forEach(drawPage);
                } else {
                    drawPage(group);
                }
                callback(pdf.render(), pdf);
            }
            var count = 2;
            kendo.pdf.loadFonts(fonts, doIt);
            kendo.pdf.loadImages(images, doIt);
        }
        function toDataURL(group, callback) {
            render(group, function (data) {
                callback('data:application/pdf;base64,' + data.base64());
            });
        }
        function toBlob(group, callback) {
            render(group, function (data) {
                callback(new Blob([data.get()], { type: 'application/pdf' }));
            });
        }
        function saveAs(group, filename, proxy, callback) {
            if (window.Blob && !kendo.support.browser.safari) {
                toBlob(group, function (blob) {
                    kendo.saveAs({
                        dataURI: blob,
                        fileName: filename
                    });
                    if (callback) {
                        callback(blob);
                    }
                });
            } else {
                toDataURL(group, function (dataURL) {
                    kendo.saveAs({
                        dataURI: dataURL,
                        fileName: filename,
                        proxyURL: proxy
                    });
                    if (callback) {
                        callback(dataURL);
                    }
                });
            }
        }
        function dispatch(handlers, element) {
            var handler = handlers[element.nodeType];
            if (handler) {
                return handler.call.apply(handler, arguments);
            }
            return element;
        }
        function drawElement(element, page, pdf) {
            if (element.options._pdfDebug) {
                page.comment('BEGIN: ' + element.options._pdfDebug);
            }
            var transform = element.transform();
            var opacity = element.opacity();
            page.save();
            if (opacity != null && opacity < 1) {
                page.setOpacity(opacity);
            }
            setStrokeOptions(element, page, pdf);
            setFillOptions(element, page, pdf);
            if (transform) {
                var m = transform.matrix();
                page.transform(m.a, m.b, m.c, m.d, m.e, m.f);
            }
            setClipping(element, page, pdf);
            dispatch({
                Path: drawPath,
                MultiPath: drawMultiPath,
                Circle: drawCircle,
                Arc: drawArc,
                Text: drawText,
                Image: drawImage,
                Group: drawGroup,
                Rect: drawRect
            }, element, page, pdf);
            page.restore();
            if (element.options._pdfDebug) {
                page.comment('END: ' + element.options._pdfDebug);
            }
        }
        function setStrokeOptions(element, page) {
            var stroke = element.stroke && element.stroke();
            if (!stroke) {
                return;
            }
            var color = stroke.color;
            if (color) {
                color = parseColor(color);
                if (color == null) {
                    return;
                }
                page.setStrokeColor(color.r, color.g, color.b);
                if (color.a != 1) {
                    page.setStrokeOpacity(color.a);
                }
            }
            var width = stroke.width;
            if (width != null) {
                if (width === 0) {
                    return;
                }
                page.setLineWidth(width);
            }
            var dashType = stroke.dashType;
            if (dashType) {
                page.setDashPattern(DASH_PATTERNS[dashType], 0);
            }
            var lineCap = stroke.lineCap;
            if (lineCap) {
                page.setLineCap(LINE_CAP[lineCap]);
            }
            var lineJoin = stroke.lineJoin;
            if (lineJoin) {
                page.setLineJoin(LINE_JOIN[lineJoin]);
            }
            var opacity = stroke.opacity;
            if (opacity != null) {
                page.setStrokeOpacity(opacity);
            }
        }
        function setFillOptions(element, page) {
            var fill = element.fill && element.fill();
            if (!fill) {
                return;
            }
            if (fill instanceof drawing.Gradient) {
                return;
            }
            var color = fill.color;
            if (color) {
                color = parseColor(color);
                if (color == null) {
                    return;
                }
                page.setFillColor(color.r, color.g, color.b);
                if (color.a != 1) {
                    page.setFillOpacity(color.a);
                }
            }
            var opacity = fill.opacity;
            if (opacity != null) {
                page.setFillOpacity(opacity);
            }
        }
        function setClipping(element, page, pdf) {
            var clip = element.clip();
            if (clip) {
                _drawPath(clip, page, pdf);
                page.clip();
            }
        }
        function shouldDraw(thing) {
            return thing && (thing instanceof drawing.Gradient || thing.color && !/^(none|transparent)$/i.test(thing.color) && (thing.width == null || thing.width > 0) && (thing.opacity == null || thing.opacity > 0));
        }
        function maybeGradient(element, page, pdf, stroke) {
            var fill = element.fill();
            if (fill instanceof drawing.Gradient) {
                if (stroke) {
                    page.clipStroke();
                } else {
                    page.clip();
                }
                var isRadial = fill instanceof drawing.RadialGradient;
                var start, end;
                if (isRadial) {
                    start = {
                        x: fill.center().x,
                        y: fill.center().y,
                        r: 0
                    };
                    end = {
                        x: fill.center().x,
                        y: fill.center().y,
                        r: fill.radius()
                    };
                } else {
                    start = {
                        x: fill.start().x,
                        y: fill.start().y
                    };
                    end = {
                        x: fill.end().x,
                        y: fill.end().y
                    };
                }
                var stops = fill.stops.elements().map(function (stop) {
                    var offset = stop.offset();
                    if (/%$/.test(offset)) {
                        offset = parseFloat(offset) / 100;
                    } else {
                        offset = parseFloat(offset);
                    }
                    var color = parseColor(stop.color());
                    color.a *= stop.opacity();
                    return {
                        offset: offset,
                        color: color
                    };
                });
                stops.unshift(stops[0]);
                stops.push(stops[stops.length - 1]);
                var gradient = {
                    userSpace: fill.userSpace(),
                    type: isRadial ? 'radial' : 'linear',
                    start: start,
                    end: end,
                    stops: stops
                };
                var box = element.rawBBox();
                var tl = box.topLeft(), size = box.getSize();
                box = {
                    left: tl.x,
                    top: tl.y,
                    width: size.width,
                    height: size.height
                };
                page.gradient(gradient, box);
                return true;
            }
        }
        function maybeFillStroke(element, page, pdf) {
            if (shouldDraw(element.fill()) && shouldDraw(element.stroke())) {
                if (!maybeGradient(element, page, pdf, true)) {
                    page.fillStroke();
                }
            } else if (shouldDraw(element.fill())) {
                if (!maybeGradient(element, page, pdf, false)) {
                    page.fill();
                }
            } else if (shouldDraw(element.stroke())) {
                page.stroke();
            } else {
                page.nop();
            }
        }
        function maybeDrawRect(path, page) {
            var segments = path.segments;
            if (segments.length == 4 && path.options.closed) {
                var a = [];
                for (var i = 0; i < segments.length; ++i) {
                    if (segments[i].controlIn()) {
                        return false;
                    }
                    a[i] = segments[i].anchor();
                }
                var isRect = a[0].y == a[1].y && a[1].x == a[2].x && a[2].y == a[3].y && a[3].x == a[0].x || a[0].x == a[1].x && a[1].y == a[2].y && a[2].x == a[3].x && a[3].y == a[0].y;
                if (isRect) {
                    page.rect(a[0].x, a[0].y, a[2].x - a[0].x, a[2].y - a[0].y);
                    return true;
                }
            }
        }
        function _drawPath(element, page, pdf) {
            var segments = element.segments;
            if (segments.length === 0) {
                return;
            }
            if (!maybeDrawRect(element, page, pdf)) {
                for (var prev, i = 0; i < segments.length; ++i) {
                    var seg = segments[i];
                    var anchor = seg.anchor();
                    if (!prev) {
                        page.moveTo(anchor.x, anchor.y);
                    } else {
                        var prevOut = prev.controlOut();
                        var controlIn = seg.controlIn();
                        if (prevOut && controlIn) {
                            page.bezier(prevOut.x, prevOut.y, controlIn.x, controlIn.y, anchor.x, anchor.y);
                        } else {
                            page.lineTo(anchor.x, anchor.y);
                        }
                    }
                    prev = seg;
                }
                if (element.options.closed) {
                    page.close();
                }
            }
        }
        function drawPath(element, page, pdf) {
            _drawPath(element, page, pdf);
            maybeFillStroke(element, page, pdf);
        }
        function drawMultiPath(element, page, pdf) {
            var paths = element.paths;
            for (var i = 0; i < paths.length; ++i) {
                _drawPath(paths[i], page, pdf);
            }
            maybeFillStroke(element, page, pdf);
        }
        function drawCircle(element, page, pdf) {
            var g = element.geometry();
            page.circle(g.center.x, g.center.y, g.radius);
            maybeFillStroke(element, page, pdf);
        }
        function drawArc(element, page, pdf) {
            var points = element.geometry().curvePoints();
            page.moveTo(points[0].x, points[0].y);
            for (var i = 1; i < points.length;) {
                page.bezier(points[i].x, points[i++].y, points[i].x, points[i++].y, points[i].x, points[i++].y);
            }
            maybeFillStroke(element, page, pdf);
        }
        function drawText(element, page) {
            var style = kendo.pdf.parseFontDef(element.options.font);
            var pos = element._position;
            var mode;
            if (element.fill() && element.stroke()) {
                mode = TEXT_RENDERING_MODE.fillAndStroke;
            } else if (element.fill()) {
                mode = TEXT_RENDERING_MODE.fill;
            } else if (element.stroke()) {
                mode = TEXT_RENDERING_MODE.stroke;
            }
            page.transform(1, 0, 0, -1, pos.x, pos.y + style.fontSize);
            page.beginText();
            page.setFont(kendo.pdf.getFontURL(style), style.fontSize);
            page.setTextRenderingMode(mode);
            page.showText(element.content(), element._pdfRect ? element._pdfRect.width() : null);
            page.endText();
        }
        function drawGroup(element, page, pdf) {
            if (element._pdfLink) {
                page.addLink(element._pdfLink.url, element._pdfLink);
            }
            var children = element.children;
            for (var i = 0; i < children.length; ++i) {
                drawElement(children[i], page, pdf);
            }
        }
        function drawImage(element, page) {
            var url = element.src();
            if (!url) {
                return;
            }
            var rect = element.rect();
            var tl = rect.getOrigin();
            var sz = rect.getSize();
            page.transform(sz.width, 0, 0, -sz.height, tl.x, tl.y + sz.height);
            page.drawImage(url);
        }
        function drawRect(element, page, pdf) {
            var geometry = element.geometry();
            page.rect(geometry.origin.x, geometry.origin.y, geometry.size.width, geometry.size.height);
            maybeFillStroke(element, page, pdf);
        }
        function exportPDF(group, options) {
            var defer = $.Deferred();
            for (var i in options) {
                if (i == 'margin' && group.options.pdf && group.options.pdf._ignoreMargin) {
                    continue;
                }
                group.options.set('pdf.' + i, options[i]);
            }
            drawing.pdf.toDataURL(group, defer.resolve);
            return defer.promise();
        }
        function parseColor(x) {
            var color = kendo.parseColor(x, true);
            return color ? color.toRGB() : null;
        }
        function optimize(root) {
            var clipbox = false;
            var matrix = geo.Matrix.unit();
            var currentBox = null;
            var changed;
            do {
                changed = false;
                root = opt(root);
            } while (root && changed);
            return {
                root: root,
                bbox: currentBox
            };
            function change(newShape) {
                changed = true;
                return newShape;
            }
            function visible(shape) {
                return shape.visible() && shape.opacity() > 0 && (shouldDraw(shape.fill()) || shouldDraw(shape.stroke()));
            }
            function optArray(a) {
                var b = [];
                for (var i = 0; i < a.length; ++i) {
                    var el = opt(a[i]);
                    if (el != null) {
                        b.push(el);
                    }
                }
                return b;
            }
            function withClipping(shape, f) {
                var saveclipbox = clipbox;
                var savematrix = matrix;
                if (shape.transform()) {
                    matrix = matrix.multiplyCopy(shape.transform().matrix());
                }
                var clip = shape.clip();
                if (clip) {
                    clip = clip.bbox();
                    if (clip) {
                        clip = clip.bbox(matrix);
                        clipbox = clipbox ? geo.Rect.intersect(clipbox, clip) : clip;
                    }
                }
                try {
                    return f();
                } finally {
                    clipbox = saveclipbox;
                    matrix = savematrix;
                }
            }
            function inClipbox(shape) {
                if (clipbox == null) {
                    return false;
                }
                var box = shape.rawBBox().bbox(matrix);
                if (clipbox && box) {
                    box = geo.Rect.intersect(box, clipbox);
                }
                return box;
            }
            function opt(shape) {
                return withClipping(shape, function () {
                    if (!(shape instanceof drawing.Group || shape instanceof drawing.MultiPath)) {
                        var box = inClipbox(shape);
                        if (!box) {
                            return change(null);
                        }
                        currentBox = currentBox ? geo.Rect.union(currentBox, box) : box;
                    }
                    return dispatch({
                        Path: function (shape) {
                            if (shape.segments.length === 0 || !visible(shape)) {
                                return change(null);
                            }
                            return shape;
                        },
                        MultiPath: function (shape) {
                            if (!visible(shape)) {
                                return change(null);
                            }
                            var el = new drawing.MultiPath(shape.options);
                            el.paths = optArray(shape.paths);
                            if (el.paths.length === 0) {
                                return change(null);
                            }
                            return el;
                        },
                        Circle: function (shape) {
                            if (!visible(shape)) {
                                return change(null);
                            }
                            return shape;
                        },
                        Arc: function (shape) {
                            if (!visible(shape)) {
                                return change(null);
                            }
                            return shape;
                        },
                        Text: function (shape) {
                            if (!/\S/.test(shape.content()) || !visible(shape)) {
                                return change(null);
                            }
                            return shape;
                        },
                        Image: function (shape) {
                            if (!(shape.visible() && shape.opacity() > 0)) {
                                return change(null);
                            }
                            return shape;
                        },
                        Group: function (shape) {
                            var el = new drawing.Group(shape.options);
                            el.children = optArray(shape.children);
                            el._pdfLink = shape._pdfLink;
                            if (shape !== root && el.children.length === 0 && !shape._pdfLink) {
                                return change(null);
                            }
                            return el;
                        },
                        Rect: function (shape) {
                            if (!visible(shape)) {
                                return change(null);
                            }
                            return shape;
                        }
                    }, shape);
                });
            }
        }
        kendo.deepExtend(drawing, {
            exportPDF: exportPDF,
            pdf: {
                toDataURL: toDataURL,
                toBlob: toBlob,
                saveAs: saveAs,
                toStream: render
            }
        });
    }(window.kendo, window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.pdf', [
        'kendo.core',
        'pdf/core',
        'pdf/ttf',
        'pdf/drawing',
        'kendo.drawing'
    ], f);
}(function () {
    var __meta__ = {
        id: 'pdf',
        name: 'PDF export',
        description: 'PDF Generation framework',
        mixin: true,
        category: 'framework',
        depends: [
            'core',
            'drawing'
        ]
    };
    (function (kendo, $) {
        kendo.PDFMixin = {
            extend: function (proto) {
                proto.events.push('pdfExport');
                proto.options.pdf = this.options;
                proto.saveAsPDF = this.saveAsPDF;
                proto._drawPDF = this._drawPDF;
                proto._drawPDFShadow = this._drawPDFShadow;
            },
            options: {
                fileName: 'Export.pdf',
                proxyURL: '',
                paperSize: 'auto',
                allPages: false,
                landscape: false,
                margin: null,
                title: null,
                author: null,
                subject: null,
                keywords: null,
                creator: 'Kendo UI PDF Generator v.' + kendo.version,
                date: null
            },
            saveAsPDF: function () {
                var progress = new $.Deferred();
                var promise = progress.promise();
                var args = { promise: promise };
                if (this.trigger('pdfExport', args)) {
                    return;
                }
                var options = this.options.pdf;
                options.multiPage = options.multiPage || options.allPages;
                this._drawPDF(progress).then(function (root) {
                    return kendo.drawing.exportPDF(root, options);
                }).done(function (dataURI) {
                    kendo.saveAs({
                        dataURI: dataURI,
                        fileName: options.fileName,
                        proxyURL: options.proxyURL,
                        forceProxy: options.forceProxy,
                        proxyTarget: options.proxyTarget
                    });
                    progress.resolve();
                }).fail(function (err) {
                    progress.reject(err);
                });
                return promise;
            },
            _drawPDF: function (progress) {
                var promise = new $.Deferred();
                kendo.drawing.drawDOM(this.wrapper).done(function (group) {
                    var args = {
                        page: group,
                        pageNumber: 1,
                        progress: 1,
                        totalPages: 1
                    };
                    progress.notify(args);
                    promise.resolve(args.page);
                }).fail(function (err) {
                    promise.reject(err);
                });
                return promise;
            },
            _drawPDFShadow: function (settings, drawOptions) {
                settings = settings || {};
                var wrapper = this.wrapper;
                var shadow = $('<div class=\'k-pdf-export-shadow\'>');
                if (settings.width) {
                    shadow.css({
                        width: settings.width,
                        overflow: 'visible'
                    });
                }
                wrapper.before(shadow);
                shadow.append(settings.content || wrapper.clone(true, true));
                var defer = $.Deferred();
                setTimeout(function () {
                    var promise = kendo.drawing.drawDOM(shadow, drawOptions);
                    promise.always(function () {
                        shadow.remove();
                    }).then(function () {
                        defer.resolve.apply(defer, arguments);
                    }).fail(function () {
                        defer.reject.apply(defer, arguments);
                    }).progress(function () {
                        defer.progress.apply(defer, arguments);
                    });
                }, 15);
                return defer.promise();
            }
        };
    }(kendo, window.kendo.jQuery));
    return kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
/** 
 * Kendo UI v2016.2.714 (http://www.telerik.com/kendo-ui)                                                                                                                                               
 * Copyright 2016 Telerik AD. All rights reserved.                                                                                                                                                      
 *                                                                                                                                                                                                      
 * Kendo UI commercial licenses may be obtained at                                                                                                                                                      
 * http://www.telerik.com/purchase/license-agreement/kendo-ui-complete                                                                                                                                  
 * If you do not own a commercial license, this file shall be governed by the trial license terms.                                                                                                      
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       

*/
(function (f, define) {
    define('kendo.gantt.list', [
        'kendo.dom',
        'kendo.touch',
        'kendo.draganddrop',
        'kendo.columnsorter',
        'kendo.datetimepicker',
        'kendo.editable'
    ], f);
}(function () {
    var __meta__ = {
        id: 'gantt.list',
        name: 'Gantt List',
        category: 'web',
        description: 'The Gantt List',
        depends: [
            'dom',
            'touch',
            'draganddrop',
            'columnsorter',
            'datetimepicker',
            'editable'
        ],
        hidden: true
    };
    (function ($) {
        var kendo = window.kendo;
        var kendoDom = kendo.dom;
        var kendoDomElement = kendoDom.element;
        var kendoTextElement = kendoDom.text;
        var browser = kendo.support.browser;
        var mobileOS = kendo.support.mobileOS;
        var ui = kendo.ui;
        var Widget = ui.Widget;
        var extend = $.extend;
        var map = $.map;
        var isFunction = $.isFunction;
        var oldIE = browser.msie && browser.version < 9;
        var keys = kendo.keys;
        var titleFromField = {
            'title': 'Title',
            'start': 'Start Time',
            'end': 'End Time',
            'percentComplete': '% Done',
            'parentId': 'Predecessor ID',
            'id': 'ID',
            'orderId': 'Order ID'
        };
        var STRING = 'string';
        var NS = '.kendoGanttList';
        var CLICK = 'click';
        var DOT = '.';
        var SIZE_CALCULATION_TEMPLATE = '<table style=\'visibility: hidden;\'>' + '<tbody>' + '<tr style=\'height:{0}\'>' + '<td>&nbsp;</td>' + '</tr>' + '</tbody>' + '</table>';
        var listStyles = {
            wrapper: 'k-treelist k-grid k-widget',
            header: 'k-header',
            alt: 'k-alt',
            rtl: 'k-rtl',
            editCell: 'k-edit-cell',
            group: 'k-treelist-group',
            gridHeader: 'k-grid-header',
            gridHeaderWrap: 'k-grid-header-wrap',
            gridContent: 'k-grid-content',
            gridContentWrap: 'k-grid-content',
            selected: 'k-state-selected',
            icon: 'k-icon',
            iconCollapse: 'k-i-collapse',
            iconExpand: 'k-i-expand',
            iconHidden: 'k-i-none',
            iconPlaceHolder: 'k-icon k-i-none',
            input: 'k-input',
            link: 'k-link',
            resizeHandle: 'k-resize-handle',
            resizeHandleInner: 'k-resize-handle-inner',
            dropPositions: 'k-insert-top k-insert-bottom k-add k-insert-middle',
            dropTop: 'k-insert-top',
            dropBottom: 'k-insert-bottom',
            dropAdd: 'k-add',
            dropMiddle: 'k-insert-middle',
            dropDenied: 'k-denied',
            dragStatus: 'k-drag-status',
            dragClue: 'k-drag-clue',
            dragClueText: 'k-clue-text'
        };
        function createPlaceholders(options) {
            var spans = [];
            var className = options.className;
            for (var i = 0, level = options.level; i < level; i++) {
                spans.push(kendoDomElement('span', { className: className }));
            }
            return spans;
        }
        function blurActiveElement() {
            var activeElement = kendo._activeElement();
            if (activeElement && activeElement.nodeName.toLowerCase() !== 'body') {
                $(activeElement).blur();
            }
        }
        var GanttList = ui.GanttList = Widget.extend({
            init: function (element, options) {
                Widget.fn.init.call(this, element, options);
                if (this.options.columns.length === 0) {
                    this.options.columns.push('title');
                }
                this.dataSource = this.options.dataSource;
                this._columns();
                this._layout();
                this._domTrees();
                this._header();
                this._sortable();
                this._editable();
                this._selectable();
                this._draggable();
                this._resizable();
                this._attachEvents();
                this._adjustHeight();
                this.bind('render', function () {
                    var headerCols;
                    var tableCols;
                    if (this.options.resizable) {
                        headerCols = this.header.find('col');
                        tableCols = this.content.find('col');
                        this.header.find('th').not(':last').each(function (index) {
                            var width = $(this).outerWidth();
                            headerCols.eq(index).width(width);
                            tableCols.eq(index).width(width);
                        });
                        headerCols.last().css('width', 'auto');
                        tableCols.last().css('width', 'auto');
                    }
                }, true);
            },
            _adjustHeight: function () {
                this.content.height(this.element.height() - this.header.parent().outerHeight());
            },
            destroy: function () {
                Widget.fn.destroy.call(this);
                if (this._reorderDraggable) {
                    this._reorderDraggable.destroy();
                }
                if (this._tableDropArea) {
                    this._tableDropArea.destroy();
                }
                if (this._contentDropArea) {
                    this._contentDropArea.destroy();
                }
                if (this._columnResizable) {
                    this._columnResizable.destroy();
                }
                if (this.touch) {
                    this.touch.destroy();
                }
                if (this.timer) {
                    clearTimeout(this.timer);
                }
                this.content.off(NS);
                this.header.find('thead').off(NS);
                this.header.find(DOT + GanttList.link).off(NS);
                this.header = null;
                this.content = null;
                this.levels = null;
                kendo.destroy(this.element);
            },
            options: {
                name: 'GanttList',
                selectable: true,
                editable: true,
                resizable: false
            },
            _attachEvents: function () {
                var that = this;
                var listStyles = GanttList.styles;
                that.content.on(CLICK + NS, 'td > span.' + listStyles.icon + ':not(.' + listStyles.iconHidden + ')', function (e) {
                    var element = $(this);
                    var model = that._modelFromElement(element);
                    model.set('expanded', !model.get('expanded'));
                    e.stopPropagation();
                });
            },
            _domTrees: function () {
                this.headerTree = new kendoDom.Tree(this.header[0]);
                this.contentTree = new kendoDom.Tree(this.content[0]);
            },
            _columns: function () {
                var columns = this.options.columns;
                var model = function () {
                    this.field = '';
                    this.title = '';
                    this.editable = false;
                    this.sortable = false;
                };
                this.columns = map(columns, function (column) {
                    column = typeof column === STRING ? {
                        field: column,
                        title: titleFromField[column]
                    } : column;
                    return extend(new model(), column);
                });
            },
            _layout: function () {
                var that = this;
                var options = this.options;
                var element = this.element;
                var listStyles = GanttList.styles;
                var calculateRowHeight = function () {
                    var rowHeight = typeof options.rowHeight === STRING ? options.rowHeight : options.rowHeight + 'px';
                    var table = $(kendo.format(SIZE_CALCULATION_TEMPLATE, rowHeight));
                    var height;
                    that.content.append(table);
                    height = table.find('tr').outerHeight();
                    table.remove();
                    return height;
                };
                element.addClass(listStyles.wrapper).append('<div class=\'' + listStyles.gridHeader + '\'><div class=\'' + listStyles.gridHeaderWrap + '\'></div></div>').append('<div class=\'' + listStyles.gridContentWrap + '\'></div>');
                this.header = element.find(DOT + listStyles.gridHeaderWrap);
                this.content = element.find(DOT + listStyles.gridContent);
                if (options.rowHeight) {
                    this._rowHeight = calculateRowHeight();
                }
            },
            _header: function () {
                var domTree = this.headerTree;
                var colgroup;
                var thead;
                var table;
                colgroup = kendoDomElement('colgroup', null, this._cols());
                thead = kendoDomElement('thead', { 'role': 'rowgroup' }, [kendoDomElement('tr', { 'role': 'row' }, this._ths())]);
                table = kendoDomElement('table', {
                    'style': { 'minWidth': this.options.listWidth + 'px' },
                    'role': 'grid'
                }, [
                    colgroup,
                    thead
                ]);
                domTree.render([table]);
            },
            _render: function (tasks) {
                var colgroup;
                var tbody;
                var table;
                var tableAttr = {
                    'style': { 'minWidth': this.options.listWidth + 'px' },
                    'tabIndex': 0,
                    'role': 'treegrid'
                };
                if (this._rowHeight) {
                    tableAttr.style.height = tasks.length * this._rowHeight + 'px';
                }
                this.levels = [{
                        field: null,
                        value: 0
                    }];
                colgroup = kendoDomElement('colgroup', null, this._cols());
                tbody = kendoDomElement('tbody', { 'role': 'rowgroup' }, this._trs(tasks));
                table = kendoDomElement('table', tableAttr, [
                    colgroup,
                    tbody
                ]);
                this.contentTree.render([table]);
                this.trigger('render');
            },
            _ths: function () {
                var columns = this.columns;
                var column;
                var attr;
                var ths = [];
                for (var i = 0, length = columns.length; i < length; i++) {
                    column = columns[i];
                    attr = {
                        'data-field': column.field,
                        'data-title': column.title,
                        className: GanttList.styles.header,
                        'role': 'columnheader'
                    };
                    ths.push(kendoDomElement('th', attr, [kendoTextElement(column.title)]));
                }
                if (this.options.resizable) {
                    ths.push(kendoDomElement('th', {
                        className: GanttList.styles.header,
                        'role': 'columnheader'
                    }));
                }
                return ths;
            },
            _cols: function () {
                var columns = this.columns;
                var column;
                var style;
                var width;
                var cols = [];
                for (var i = 0, length = columns.length; i < length; i++) {
                    column = columns[i];
                    width = column.width;
                    if (width && parseInt(width, 10) !== 0) {
                        style = { style: { width: typeof width === STRING ? width : width + 'px' } };
                    } else {
                        style = null;
                    }
                    cols.push(kendoDomElement('col', style, []));
                }
                if (this.options.resizable) {
                    cols.push(kendoDomElement('col', { style: { width: '1px' } }));
                }
                return cols;
            },
            _trs: function (tasks) {
                var task;
                var rows = [];
                var attr;
                var className = [];
                var level;
                var listStyles = GanttList.styles;
                for (var i = 0, length = tasks.length; i < length; i++) {
                    task = tasks[i];
                    level = this._levels({
                        idx: task.parentId,
                        id: task.id,
                        summary: task.summary
                    });
                    attr = {
                        'data-uid': task.uid,
                        'data-level': level,
                        'role': 'row'
                    };
                    if (task.summary) {
                        attr['aria-expanded'] = task.expanded;
                    }
                    if (i % 2 !== 0) {
                        className.push(listStyles.alt);
                    }
                    if (task.summary) {
                        className.push(listStyles.group);
                    }
                    if (className.length) {
                        attr.className = className.join(' ');
                    }
                    rows.push(this._tds({
                        task: task,
                        attr: attr,
                        level: level
                    }));
                    className = [];
                }
                return rows;
            },
            _tds: function (options) {
                var children = [];
                var columns = this.columns;
                var column;
                for (var i = 0, l = columns.length; i < l; i++) {
                    column = columns[i];
                    children.push(this._td({
                        task: options.task,
                        column: column,
                        level: options.level
                    }));
                }
                if (this.options.resizable) {
                    children.push(kendoDomElement('td', { 'role': 'gridcell' }));
                }
                return kendoDomElement('tr', options.attr, children);
            },
            _td: function (options) {
                var children = [];
                var resourcesField = this.options.resourcesField;
                var listStyles = GanttList.styles;
                var task = options.task;
                var column = options.column;
                var value = task.get(column.field);
                var formatedValue;
                var label;
                if (column.field == resourcesField) {
                    value = value || [];
                    formatedValue = [];
                    for (var i = 0; i < value.length; i++) {
                        formatedValue.push(kendo.format('{0} [{1}]', value[i].get('name'), value[i].get('formatedValue')));
                    }
                    formatedValue = formatedValue.join(', ');
                } else {
                    formatedValue = column.format ? kendo.format(column.format, value) : value;
                }
                if (column.field === 'title') {
                    children = createPlaceholders({
                        level: options.level,
                        className: listStyles.iconPlaceHolder
                    });
                    children.push(kendoDomElement('span', { className: listStyles.icon + ' ' + (task.summary ? task.expanded ? listStyles.iconCollapse : listStyles.iconExpand : listStyles.iconHidden) }));
                    label = kendo.format('{0}, {1:P0}', formatedValue, task.percentComplete);
                }
                children.push(kendoDomElement('span', { 'aria-label': label }, [kendoTextElement(formatedValue)]));
                return kendoDomElement('td', { 'role': 'gridcell' }, children);
            },
            _levels: function (options) {
                var levels = this.levels;
                var level;
                var summary = options.summary;
                var idx = options.idx;
                var id = options.id;
                for (var i = 0, length = levels.length; i < length; i++) {
                    level = levels[i];
                    if (level.field == idx) {
                        if (summary) {
                            levels.push({
                                field: id,
                                value: level.value + 1
                            });
                        }
                        return level.value;
                    }
                }
            },
            _sortable: function () {
                var that = this;
                var resourcesField = this.options.resourcesField;
                var columns = this.columns;
                var column;
                var sortableInstance;
                var cells = this.header.find('th[' + kendo.attr('field') + ']');
                var handler = function (e) {
                    if (that.dataSource.total() === 0 || that.editable && that.editable.trigger('validate')) {
                        e.preventDefault();
                        e.stopImmediatePropagation();
                    }
                };
                var cell;
                for (var idx = 0, length = cells.length; idx < length; idx++) {
                    column = columns[idx];
                    if (column.sortable && column.field !== resourcesField) {
                        cell = cells.eq(idx);
                        sortableInstance = cell.data('kendoColumnSorter');
                        if (sortableInstance) {
                            sortableInstance.destroy();
                        }
                        cell.attr('data-' + kendo.ns + 'field', column.field).kendoColumnSorter({ dataSource: this.dataSource }).find(DOT + GanttList.styles.link).on('click' + NS, handler);
                    }
                }
                cells = null;
            },
            _selectable: function () {
                var that = this;
                var selectable = this.options.selectable;
                if (selectable) {
                    this.content.on(CLICK + NS, 'tr', function (e) {
                        var element = $(this);
                        if (that.editable) {
                            that.editable.trigger('validate');
                        }
                        if (!e.ctrlKey) {
                            that.select(element);
                        } else {
                            that.clearSelection();
                        }
                    });
                }
            },
            select: function (value) {
                var element = this.content.find(value);
                var selectedClassName = GanttList.styles.selected;
                if (element.length) {
                    element.siblings(DOT + selectedClassName).removeClass(selectedClassName).attr('aria-selected', false).end().addClass(selectedClassName).attr('aria-selected', true);
                    this.trigger('change');
                    return;
                }
                return this.content.find(DOT + selectedClassName);
            },
            clearSelection: function () {
                var selected = this.select();
                if (selected.length) {
                    selected.removeClass(GanttList.styles.selected);
                    this.trigger('change');
                }
            },
            _setDataSource: function (dataSource) {
                this.dataSource = dataSource;
            },
            _editable: function () {
                var that = this;
                var listStyles = GanttList.styles;
                var iconSelector = 'span.' + listStyles.icon + ':not(' + listStyles.iconHidden + ')';
                var finishEdit = function () {
                    var editable = that.editable;
                    if (editable) {
                        if (editable.end()) {
                            that._closeCell();
                        } else {
                            editable.trigger('validate');
                        }
                    }
                };
                var mousedown = function (e) {
                    var currentTarget = $(e.currentTarget);
                    if (!currentTarget.hasClass(listStyles.editCell)) {
                        blurActiveElement();
                    }
                };
                if (!this.options.editable) {
                    return;
                }
                this._startEditHandler = function (e) {
                    var td = e.currentTarget ? $(e.currentTarget) : e;
                    var column = that._columnFromElement(td);
                    if (that.editable) {
                        return;
                    }
                    if (column && column.editable) {
                        that._editCell({
                            cell: td,
                            column: column
                        });
                    }
                };
                that.content.on('focusin' + NS, function () {
                    clearTimeout(that.timer);
                    that.timer = null;
                }).on('focusout' + NS, function () {
                    that.timer = setTimeout(finishEdit, 1);
                }).on('keydown' + NS, function (e) {
                    if (e.keyCode === keys.ENTER) {
                        e.preventDefault();
                    }
                }).on('keyup' + NS, function (e) {
                    var key = e.keyCode;
                    var cell;
                    var model;
                    switch (key) {
                    case keys.ENTER:
                        blurActiveElement();
                        finishEdit();
                        break;
                    case keys.ESC:
                        if (that.editable) {
                            cell = that._editableContainer;
                            model = that._modelFromElement(cell);
                            if (!that.trigger('cancel', {
                                    model: model,
                                    cell: cell
                                })) {
                                that._closeCell(true);
                            }
                        }
                        break;
                    }
                });
                if (!mobileOS) {
                    that.content.on('mousedown' + NS, 'td', function (e) {
                        mousedown(e);
                    }).on('dblclick' + NS, 'td', function (e) {
                        if (!$(e.target).is(iconSelector)) {
                            that._startEditHandler(e);
                        }
                    });
                } else {
                    that.touch = that.content.kendoTouch({
                        filter: 'td',
                        touchstart: function (e) {
                            mousedown(e.touch);
                        },
                        doubletap: function (e) {
                            if (!$(e.touch.initialTouch).is(iconSelector)) {
                                that._startEditHandler(e.touch);
                            }
                        }
                    }).data('kendoTouch');
                }
            },
            _editCell: function (options) {
                var resourcesField = this.options.resourcesField;
                var listStyles = GanttList.styles;
                var cell = options.cell;
                var column = options.column;
                var model = this._modelFromElement(cell);
                var modelCopy = this.dataSource._createNewModel(model.toJSON());
                var field = modelCopy.fields[column.field] || modelCopy[column.field];
                var validation = field.validation;
                var DATATYPE = kendo.attr('type');
                var BINDING = kendo.attr('bind');
                var FORMAT = kendo.attr('format');
                var attr = {
                    'name': column.field,
                    'required': field.validation ? field.validation.required === true : false
                };
                var editor;
                if (column.field === resourcesField) {
                    column.editor(cell, modelCopy);
                    return;
                }
                this._editableContent = cell.children().detach();
                this._editableContainer = cell;
                cell.data('modelCopy', modelCopy);
                if ((field.type === 'date' || $.type(field) === 'date') && (!column.format || /H|m|s|F|g|u/.test(column.format))) {
                    attr[BINDING] = 'value:' + column.field;
                    attr[DATATYPE] = 'date';
                    if (column.format) {
                        attr[FORMAT] = kendo._extractFormat(column.format);
                    }
                    editor = function (container, options) {
                        $('<input type="text"/>').attr(attr).appendTo(container).kendoDateTimePicker({ format: options.format });
                    };
                }
                this.editable = cell.addClass(listStyles.editCell).kendoEditable({
                    fields: {
                        field: column.field,
                        format: column.format,
                        editor: column.editor || editor
                    },
                    model: modelCopy,
                    clearContainer: false
                }).data('kendoEditable');
                if (validation && validation.dateCompare && isFunction(validation.dateCompare) && validation.message) {
                    $('<span ' + kendo.attr('for') + '="' + column.field + '" class="k-invalid-msg"/>').hide().appendTo(cell);
                    cell.find('[name=' + column.field + ']').attr(kendo.attr('dateCompare-msg'), validation.message);
                }
                this.editable.bind('validate', function (e) {
                    var focusable = this.element.find(':kendoFocusable:first').focus();
                    if (oldIE) {
                        focusable.focus();
                    }
                    e.preventDefault();
                });
                if (this.trigger('edit', {
                        model: model,
                        cell: cell
                    })) {
                    this._closeCell(true);
                }
            },
            _closeCell: function (cancelUpdate) {
                var listStyles = GanttList.styles;
                var cell = this._editableContainer;
                var model = this._modelFromElement(cell);
                var column = this._columnFromElement(cell);
                var field = column.field;
                var copy = cell.data('modelCopy');
                var taskInfo = {};
                taskInfo[field] = copy.get(field);
                cell.empty().removeData('modelCopy').removeClass(listStyles.editCell).append(this._editableContent);
                this.editable.unbind();
                this.editable.destroy();
                this.editable = null;
                this._editableContainer = null;
                this._editableContent = null;
                if (!cancelUpdate) {
                    if (field === 'start') {
                        taskInfo.end = new Date(taskInfo.start.getTime() + model.duration());
                    }
                    this.trigger('update', {
                        task: model,
                        updateInfo: taskInfo
                    });
                }
            },
            _draggable: function () {
                var that = this;
                var draggedTask = null;
                var dropAllowed = true;
                var dropTarget;
                var listStyles = GanttList.styles;
                var isRtl = kendo.support.isRtl(this.element);
                var selector = 'tr[' + kendo.attr('level') + ' = 0]:last';
                var action = {};
                var clear = function () {
                    draggedTask = null;
                    dropTarget = null;
                    dropAllowed = true;
                    action = {};
                };
                var allowDrop = function (task) {
                    var parent = task;
                    while (parent) {
                        if (draggedTask.get('id') === parent.get('id')) {
                            dropAllowed = false;
                            break;
                        }
                        parent = that.dataSource.taskParent(parent);
                    }
                };
                var defineLimits = function () {
                    var height = $(dropTarget).height();
                    var offsetTop = kendo.getOffset(dropTarget).top;
                    extend(dropTarget, {
                        beforeLimit: offsetTop + height * 0.25,
                        afterLimit: offsetTop + height * 0.75
                    });
                };
                var defineAction = function (coordinate) {
                    if (!dropTarget) {
                        return;
                    }
                    var location = coordinate.location;
                    var className = listStyles.dropAdd;
                    var command = 'add';
                    var level = parseInt(dropTarget.attr(kendo.attr('level')), 10);
                    var sibling;
                    if (location <= dropTarget.beforeLimit) {
                        sibling = dropTarget.prev();
                        className = listStyles.dropTop;
                        command = 'insert-before';
                    } else if (location >= dropTarget.afterLimit) {
                        sibling = dropTarget.next();
                        className = listStyles.dropBottom;
                        command = 'insert-after';
                    }
                    if (sibling && parseInt(sibling.attr(kendo.attr('level')), 10) === level) {
                        className = listStyles.dropMiddle;
                    }
                    action.className = className;
                    action.command = command;
                };
                var status = function () {
                    return that._reorderDraggable.hint.children(DOT + listStyles.dragStatus).removeClass(listStyles.dropPositions);
                };
                if (!this.options.editable) {
                    return;
                }
                this._reorderDraggable = this.content.kendoDraggable({
                    distance: 10,
                    holdToDrag: mobileOS,
                    group: 'listGroup',
                    filter: 'tr[data-uid]',
                    ignore: DOT + listStyles.input,
                    hint: function (target) {
                        return $('<div class="' + listStyles.header + ' ' + listStyles.dragClue + '"/>').css({
                            width: 300,
                            paddingLeft: target.css('paddingLeft'),
                            paddingRight: target.css('paddingRight'),
                            lineHeight: target.height() + 'px',
                            paddingTop: target.css('paddingTop'),
                            paddingBottom: target.css('paddingBottom')
                        }).append('<span class="' + listStyles.icon + ' ' + listStyles.dragStatus + '" /><span class="' + listStyles.dragClueText + '"/>');
                    },
                    cursorOffset: {
                        top: -20,
                        left: 0
                    },
                    container: this.content,
                    'dragstart': function (e) {
                        if (that.editable && that.editable.trigger('validate')) {
                            e.preventDefault();
                            return;
                        }
                        draggedTask = that._modelFromElement(e.currentTarget);
                        this.hint.children(DOT + listStyles.dragClueText).text(draggedTask.get('title'));
                        if (isRtl) {
                            this.hint.addClass(listStyles.rtl);
                        }
                    },
                    'drag': function (e) {
                        if (dropAllowed) {
                            defineAction(e.y);
                            status().addClass(action.className);
                        }
                    },
                    'dragend': function () {
                        clear();
                    },
                    'dragcancel': function () {
                        clear();
                    }
                }).data('kendoDraggable');
                this._tableDropArea = this.content.kendoDropTargetArea({
                    distance: 0,
                    group: 'listGroup',
                    filter: 'tr[data-uid]',
                    'dragenter': function (e) {
                        dropTarget = e.dropTarget;
                        allowDrop(that._modelFromElement(dropTarget));
                        defineLimits();
                        status().toggleClass(listStyles.dropDenied, !dropAllowed);
                    },
                    'dragleave': function () {
                        dropAllowed = true;
                        status();
                    },
                    'drop': function () {
                        var target = that._modelFromElement(dropTarget);
                        var orderId = target.orderId;
                        var taskInfo = { parentId: target.parentId };
                        if (dropAllowed) {
                            switch (action.command) {
                            case 'add':
                                taskInfo.parentId = target.id;
                                break;
                            case 'insert-before':
                                if (target.parentId === draggedTask.parentId && target.orderId > draggedTask.orderId) {
                                    taskInfo.orderId = orderId - 1;
                                } else {
                                    taskInfo.orderId = orderId;
                                }
                                break;
                            case 'insert-after':
                                if (target.parentId === draggedTask.parentId && target.orderId > draggedTask.orderId) {
                                    taskInfo.orderId = orderId;
                                } else {
                                    taskInfo.orderId = orderId + 1;
                                }
                                break;
                            }
                            that.trigger('update', {
                                task: draggedTask,
                                updateInfo: taskInfo
                            });
                        }
                    }
                }).data('kendoDropTargetArea');
                this._contentDropArea = this.element.kendoDropTargetArea({
                    distance: 0,
                    group: 'listGroup',
                    filter: DOT + listStyles.gridContent,
                    'drop': function () {
                        var target = that._modelFromElement(that.content.find(selector));
                        var orderId = target.orderId;
                        var taskInfo = {
                            parentId: null,
                            orderId: draggedTask.parentId !== null ? orderId + 1 : orderId
                        };
                        that.trigger('update', {
                            task: draggedTask,
                            updateInfo: taskInfo
                        });
                    }
                }).data('kendoDropTargetArea');
            },
            _resizable: function () {
                var that = this;
                var listStyles = GanttList.styles;
                var positionResizeHandle = function (e) {
                    var th = $(e.currentTarget);
                    var resizeHandle = that.resizeHandle;
                    var position = th.position();
                    var left = position.left;
                    var cellWidth = th.outerWidth();
                    var container = th.closest('div');
                    var clientX = e.clientX + $(window).scrollLeft();
                    var indicatorWidth = that.options.columnResizeHandleWidth;
                    left += container.scrollLeft();
                    if (!resizeHandle) {
                        resizeHandle = that.resizeHandle = $('<div class="' + listStyles.resizeHandle + '"><div class="' + listStyles.resizeHandleInner + '" /></div>');
                    }
                    var cellOffset = th.offset().left + cellWidth;
                    var show = clientX > cellOffset - indicatorWidth && clientX < cellOffset + indicatorWidth;
                    if (!show) {
                        resizeHandle.hide();
                        return;
                    }
                    container.append(resizeHandle);
                    resizeHandle.show().css({
                        top: position.top,
                        left: left + cellWidth - indicatorWidth - 1,
                        height: th.outerHeight(),
                        width: indicatorWidth * 3
                    }).data('th', th);
                };
                if (!this.options.resizable) {
                    return;
                }
                if (this._columnResizable) {
                    this._columnResizable.destroy();
                }
                this.header.find('thead').on('mousemove' + NS, 'th', positionResizeHandle);
                this._columnResizable = this.header.kendoResizable({
                    handle: DOT + listStyles.resizeHandle,
                    start: function (e) {
                        var th = $(e.currentTarget).data('th');
                        var colSelector = 'col:eq(' + th.index() + ')';
                        var header = that.header.find('table');
                        var contentTable = that.content.find('table');
                        that.element.addClass('k-grid-column-resizing');
                        this.col = contentTable.children('colgroup').find(colSelector).add(header.find(colSelector));
                        this.th = th;
                        this.startLocation = e.x.location;
                        this.columnWidth = th.outerWidth();
                        this.table = header.add(contentTable);
                        this.totalWidth = this.table.width() - header.find('th:last').outerWidth();
                    },
                    resize: function (e) {
                        var minColumnWidth = 11;
                        var delta = e.x.location - this.startLocation;
                        if (this.columnWidth + delta < minColumnWidth) {
                            delta = minColumnWidth - this.columnWidth;
                        }
                        this.table.css({ 'minWidth': this.totalWidth + delta });
                        this.col.width(this.columnWidth + delta);
                    },
                    resizeend: function () {
                        that.element.removeClass('k-grid-column-resizing');
                        var oldWidth = Math.floor(this.columnWidth);
                        var newWidth = Math.floor(this.th.outerWidth());
                        var column = that.columns[this.th.index()];
                        that.trigger('columnResize', {
                            column: column,
                            oldWidth: oldWidth,
                            newWidth: newWidth
                        });
                        this.table = this.col = this.th = null;
                    }
                }).data('kendoResizable');
            },
            _modelFromElement: function (element) {
                var row = element.closest('tr');
                var model = this.dataSource.getByUid(row.attr(kendo.attr('uid')));
                return model;
            },
            _columnFromElement: function (element) {
                var td = element.closest('td');
                var tr = td.parent();
                var idx = tr.children().index(td);
                return this.columns[idx];
            }
        });
        extend(true, ui.GanttList, { styles: listStyles });
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
/** 
 * Kendo UI v2016.2.714 (http://www.telerik.com/kendo-ui)                                                                                                                                               
 * Copyright 2016 Telerik AD. All rights reserved.                                                                                                                                                      
 *                                                                                                                                                                                                      
 * Kendo UI commercial licenses may be obtained at                                                                                                                                                      
 * http://www.telerik.com/purchase/license-agreement/kendo-ui-complete                                                                                                                                  
 * If you do not own a commercial license, this file shall be governed by the trial license terms.                                                                                                      
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       

*/
(function (f, define) {
    define('kendo.gantt.timeline', [
        'kendo.dom',
        'kendo.touch',
        'kendo.draganddrop'
    ], f);
}(function () {
    var __meta__ = {
        id: 'gantt.timeline',
        name: 'Gantt Timeline',
        category: 'web',
        description: 'The Gantt Timeline',
        depends: [
            'dom',
            'touch',
            'draganddrop'
        ],
        hidden: true
    };
    (function ($) {
        var Widget = kendo.ui.Widget;
        var kendoDomElement = kendo.dom.element;
        var kendoTextElement = kendo.dom.text;
        var kendoHtmlElement = kendo.dom.html;
        var isPlainObject = $.isPlainObject;
        var extend = $.extend;
        var proxy = $.proxy;
        var browser = kendo.support.browser;
        var isRtl = false;
        var keys = kendo.keys;
        var Query = kendo.data.Query;
        var STRING = 'string';
        var NS = '.kendoGanttTimeline';
        var CLICK = 'click';
        var DBLCLICK = 'dblclick';
        var MOUSEMOVE = 'mousemove';
        var MOUSEENTER = 'mouseenter';
        var MOUSELEAVE = 'mouseleave';
        var KEYDOWN = 'keydown';
        var DOT = '.';
        var TIME_HEADER_TEMPLATE = kendo.template('#=kendo.toString(start, \'t\')#');
        var DAY_HEADER_TEMPLATE = kendo.template('#=kendo.toString(start, \'ddd M/dd\')#');
        var WEEK_HEADER_TEMPLATE = kendo.template('#=kendo.toString(start, \'ddd M/dd\')# - #=kendo.toString(kendo.date.addDays(end, -1), \'ddd M/dd\')#');
        var MONTH_HEADER_TEMPLATE = kendo.template('#=kendo.toString(start, \'MMM\')#');
        var YEAR_HEADER_TEMPLATE = kendo.template('#=kendo.toString(start, \'yyyy\')#');
        var RESIZE_HINT = kendo.template('<div class="#=styles.marquee#">' + '<div class="#=styles.marqueeColor#"></div>' + '</div>');
        var RESIZE_TOOLTIP_TEMPLATE = kendo.template('<div style="z-index: 100002;" class="#=styles.tooltipWrapper#">' + '<div class="#=styles.tooltipContent#">' + '<div>#=messages.start#: #=kendo.toString(start, format)#</div>' + '<div>#=messages.end#: #=kendo.toString(end, format)#</div>' + '</div>' + '</div>');
        var PERCENT_RESIZE_TOOLTIP_TEMPLATE = kendo.template('<div style="z-index: 100002;" class="#=styles.tooltipWrapper#" >' + '<div class="#=styles.tooltipContent#">#=text#%</div>' + '<div class="#=styles.tooltipCallout#" style="left:13px;"></div>' + '</div>');
        var TASK_TOOLTIP_TEMPLATE = kendo.template('<div class="#=styles.taskDetails#">' + '<strong>#=task.title#</strong>' + '<div class="#=styles.taskDetailsPercent#">#=kendo.toString(task.percentComplete, "p0")#</div>' + '<ul class="#=styles.reset#">' + '<li>#=messages.start#: #=kendo.toString(task.start, "h:mm tt ddd, MMM d")#</li>' + '<li>#=messages.end#: #=kendo.toString(task.end, "h:mm tt ddd, MMM d")#</li>' + '</ul>' + '</div>');
        var SIZE_CALCULATION_TEMPLATE = '<table style=\'visibility: hidden;\'>' + '<tbody>' + '<tr style=\'height:{0}\'>' + '<td>&nbsp;</td>' + '</tr>' + '</tbody>' + '</table>';
        var defaultViews = {
            day: { type: 'kendo.ui.GanttDayView' },
            week: { type: 'kendo.ui.GanttWeekView' },
            month: { type: 'kendo.ui.GanttMonthView' },
            year: { type: 'kendo.ui.GanttYearView' }
        };
        function trimOptions(options) {
            delete options.name;
            delete options.prefix;
            delete options.views;
            return options;
        }
        function getWorkDays(options) {
            var workDays = [];
            var dayIndex = options.workWeekStart;
            workDays.push(dayIndex);
            while (options.workWeekEnd != dayIndex) {
                if (dayIndex > 6) {
                    dayIndex -= 7;
                } else {
                    dayIndex++;
                }
                workDays.push(dayIndex);
            }
            return workDays;
        }
        function blurActiveElement() {
            var activeElement = kendo._activeElement();
            if (activeElement && activeElement.nodeName.toLowerCase() !== 'body') {
                $(activeElement).blur();
            }
        }
        var viewStyles = {
            alt: 'k-alt',
            reset: 'k-reset',
            nonWorking: 'k-nonwork-hour',
            header: 'k-header',
            gridHeader: 'k-grid-header',
            gridHeaderWrap: 'k-grid-header-wrap',
            gridContent: 'k-grid-content',
            tasksWrapper: 'k-gantt-tables',
            rowsTable: 'k-gantt-rows',
            columnsTable: 'k-gantt-columns',
            tasksTable: 'k-gantt-tasks',
            dependenciesWrapper: 'k-gantt-dependencies',
            resource: 'k-resource',
            resourceAlt: 'k-resource k-alt',
            task: 'k-task',
            taskSingle: 'k-task-single',
            taskMilestone: 'k-task-milestone',
            taskSummary: 'k-task-summary',
            taskWrap: 'k-task-wrap',
            taskMilestoneWrap: 'k-milestone-wrap',
            resourcesWrap: 'k-resources-wrap',
            taskDot: 'k-task-dot',
            taskDotStart: 'k-task-start',
            taskDotEnd: 'k-task-end',
            taskDragHandle: 'k-task-draghandle',
            taskContent: 'k-task-content',
            taskTemplate: 'k-task-template',
            taskActions: 'k-task-actions',
            taskDelete: 'k-task-delete',
            taskComplete: 'k-task-complete',
            taskDetails: 'k-task-details',
            taskDetailsPercent: 'k-task-pct',
            link: 'k-link',
            icon: 'k-icon',
            iconDelete: 'k-si-close',
            taskResizeHandle: 'k-resize-handle',
            taskResizeHandleWest: 'k-resize-w',
            taskResizeHandleEast: 'k-resize-e',
            taskSummaryProgress: 'k-task-summary-progress',
            taskSummaryComplete: 'k-task-summary-complete',
            line: 'k-line',
            lineHorizontal: 'k-line-h',
            lineVertical: 'k-line-v',
            arrowWest: 'k-arrow-w',
            arrowEast: 'k-arrow-e',
            dragHint: 'k-drag-hint',
            dependencyHint: 'k-dependency-hint',
            tooltipWrapper: 'k-widget k-tooltip k-popup k-group k-reset',
            tooltipContent: 'k-tooltip-content',
            tooltipCallout: 'k-callout k-callout-s',
            callout: 'k-callout',
            marquee: 'k-marquee k-gantt-marquee',
            marqueeColor: 'k-marquee-color'
        };
        var GanttView = kendo.ui.GanttView = Widget.extend({
            init: function (element, options) {
                Widget.fn.init.call(this, element, options);
                this.title = this.options.title || this.options.name;
                this.header = this.element.find(DOT + GanttView.styles.gridHeader);
                this.content = this.element.find(DOT + GanttView.styles.gridContent);
                this.contentWidth = this.content.width();
                this._workDays = getWorkDays(this.options);
                this._headerTree = options.headerTree;
                this._taskTree = options.taskTree;
                this._taskTemplate = options.taskTemplate ? kendo.template(options.taskTemplate, extend({}, kendo.Template, options.templateSettings)) : null;
                this._dependencyTree = options.dependencyTree;
                this._taskCoordinates = {};
                this._currentTime();
            },
            destroy: function () {
                Widget.fn.destroy.call(this);
                clearTimeout(this._tooltipTimeout);
                this.headerRow = null;
                this.header = null;
                this.content = null;
                this._dragHint = null;
                this._resizeHint = null;
                this._resizeTooltip = null;
                this._taskTooltip = null;
                this._percentCompleteResizeTooltip = null;
                this._headerTree = null;
                this._taskTree = null;
                this._dependencyTree = null;
            },
            options: {
                showWorkHours: false,
                showWorkDays: false,
                workDayStart: new Date(1980, 1, 1, 8, 0, 0),
                workDayEnd: new Date(1980, 1, 1, 17, 0, 0),
                workWeekStart: 1,
                workWeekEnd: 5,
                hourSpan: 1,
                slotSize: 100,
                currentTimeMarker: { updateInterval: 10000 }
            },
            renderLayout: function () {
                this._slots = this._createSlots();
                this._tableWidth = this._calculateTableWidth();
                this.createLayout(this._layout());
                this._slotDimensions();
                this._adjustHeight();
                this.content.find(DOT + GanttView.styles.dependenciesWrapper).width(this._tableWidth);
            },
            _adjustHeight: function () {
                this.content.height(this.element.height() - this.header.outerHeight());
            },
            createLayout: function (rows) {
                var headers = this._headers(rows);
                var colgroup = this._colgroup();
                var tree = this._headerTree;
                var header = kendoDomElement('thead', null, headers);
                var table = kendoDomElement('table', { style: { width: this._tableWidth + 'px' } }, [
                    colgroup,
                    header
                ]);
                tree.render([table]);
                this.headerRow = this.header.find('table:first tr').last();
            },
            _slotDimensions: function () {
                var headers = this.headerRow[0].children;
                var slots = this._timeSlots();
                var slot;
                var header;
                for (var i = 0, length = headers.length; i < length; i++) {
                    header = headers[i];
                    slot = slots[i];
                    slot.offsetLeft = header.offsetLeft;
                    slot.offsetWidth = header.offsetWidth;
                }
            },
            render: function (tasks) {
                var taskCount = tasks.length;
                var styles = GanttView.styles;
                var contentTable;
                var rowsTable = this._rowsTable(taskCount);
                var columnsTable = this._columnsTable(taskCount);
                var tasksTable = this._tasksTable(tasks);
                var currentTimeMarker = this.options.currentTimeMarker;
                var calculatedSize = this.options.calculatedSize;
                var totalHeight;
                this._taskTree.render([
                    rowsTable,
                    columnsTable,
                    tasksTable
                ]);
                contentTable = this.content.find(DOT + styles.rowsTable);
                if (calculatedSize) {
                    totalHeight = calculatedSize.row * tasks.length;
                    this.content.find(DOT + styles.tasksTable).height(totalHeight);
                    contentTable.height(totalHeight);
                }
                this._contentHeight = contentTable.height();
                this._rowHeight = calculatedSize ? calculatedSize.row : this._contentHeight / contentTable.find('tr').length;
                this.content.find(DOT + styles.columnsTable).height(this._contentHeight);
                if (currentTimeMarker !== false && currentTimeMarker.updateInterval !== undefined) {
                    this._renderCurrentTime();
                }
            },
            _rowsTable: function (rowCount) {
                var rows = [];
                var row;
                var styles = GanttView.styles;
                var attributes = [
                    null,
                    { className: styles.alt }
                ];
                for (var i = 0; i < rowCount; i++) {
                    row = kendoDomElement('tr', attributes[i % 2], [kendoDomElement('td', null, [kendoTextElement('\xA0')])]);
                    rows.push(row);
                }
                return this._createTable(1, rows, { className: styles.rowsTable });
            },
            _columnsTable: function () {
                var cells = [];
                var row;
                var styles = GanttView.styles;
                var slots = this._timeSlots();
                var slotsCount = slots.length;
                var slot;
                var slotSpan;
                var totalSpan = 0;
                var attributes;
                for (var i = 0; i < slotsCount; i++) {
                    slot = slots[i];
                    attributes = {};
                    slotSpan = slot.span;
                    totalSpan += slotSpan;
                    if (slotSpan !== 1) {
                        attributes.colspan = slotSpan;
                    }
                    if (slot.isNonWorking) {
                        attributes.className = styles.nonWorking;
                    }
                    cells.push(kendoDomElement('td', attributes, [kendoTextElement('\xA0')]));
                }
                row = kendoDomElement('tr', null, cells);
                return this._createTable(totalSpan, [row], { className: styles.columnsTable });
            },
            _tasksTable: function (tasks) {
                var rows = [];
                var row;
                var cell;
                var position;
                var task;
                var styles = GanttView.styles;
                var coordinates = this._taskCoordinates = {};
                var size = this._calculateMilestoneWidth();
                var milestoneWidth = Math.round(size.width);
                var resourcesField = this.options.resourcesField;
                var className = [
                    styles.resource,
                    styles.resourceAlt
                ];
                var calculatedSize = this.options.calculatedSize;
                var resourcesPosition;
                var resourcesMargin = this._calculateResourcesMargin();
                var taskBorderWidth = this._calculateTaskBorderWidth();
                var resourceStyle;
                var addCoordinates = function (rowIndex) {
                    var taskLeft;
                    var taskRight;
                    taskLeft = position.left;
                    taskRight = taskLeft + position.width;
                    if (task.isMilestone()) {
                        taskLeft -= milestoneWidth / 2;
                        taskRight = taskLeft + milestoneWidth;
                    }
                    coordinates[task.id] = {
                        start: taskLeft,
                        end: taskRight,
                        rowIndex: rowIndex
                    };
                };
                for (var i = 0, l = tasks.length; i < l; i++) {
                    task = tasks[i];
                    position = this._taskPosition(task);
                    position.borderWidth = taskBorderWidth;
                    row = kendoDomElement('tr', null);
                    cell = kendoDomElement('td', null, [this._renderTask(tasks[i], position)]);
                    if (task[resourcesField] && task[resourcesField].length) {
                        if (isRtl) {
                            resourcesPosition = this._tableWidth - position.left;
                        } else {
                            resourcesPosition = Math.max(position.width || size.clientWidth, 0) + position.left;
                        }
                        resourceStyle = { width: this._tableWidth - (resourcesPosition + resourcesMargin) + 'px' };
                        resourceStyle[isRtl ? 'right' : 'left'] = resourcesPosition + 'px';
                        if (calculatedSize) {
                            resourceStyle.height = calculatedSize.cell + 'px';
                        }
                        cell.children.push(kendoDomElement('div', {
                            className: styles.resourcesWrap,
                            style: resourceStyle
                        }, this._renderResources(task[resourcesField], className[i % 2])));
                    }
                    row.children.push(cell);
                    rows.push(row);
                    addCoordinates(i);
                }
                return this._createTable(1, rows, { className: GanttView.styles.tasksTable });
            },
            _createTable: function (colspan, rows, styles) {
                var cols = [];
                var colgroup;
                var tbody;
                for (var i = 0; i < colspan; i++) {
                    cols.push(kendoDomElement('col'));
                }
                colgroup = kendoDomElement('colgroup', null, cols);
                tbody = kendoDomElement('tbody', null, rows);
                if (!styles.style) {
                    styles.style = {};
                }
                styles.style.width = this._tableWidth + 'px';
                return kendoDomElement('table', styles, [
                    colgroup,
                    tbody
                ]);
            },
            _calculateTableWidth: function () {
                var slots = this._timeSlots();
                var maxSpan = 0;
                var totalSpan = 0;
                var currentSpan;
                var tableWidth;
                for (var i = 0, length = slots.length; i < length; i++) {
                    currentSpan = slots[i].span;
                    totalSpan += currentSpan;
                    if (currentSpan > maxSpan) {
                        maxSpan = currentSpan;
                    }
                }
                tableWidth = Math.round(totalSpan * this.options.slotSize / maxSpan);
                return tableWidth;
            },
            _calculateMilestoneWidth: function () {
                var size;
                var className = GanttView.styles.task + ' ' + GanttView.styles.taskMilestone;
                var milestone = $('<div class=\'' + className + '\' style=\'visibility: hidden; position: absolute\'>');
                var boundingClientRect;
                this.content.append(milestone);
                boundingClientRect = milestone[0].getBoundingClientRect();
                size = {
                    'width': boundingClientRect.right - boundingClientRect.left,
                    'clientWidth': milestone[0].clientWidth
                };
                milestone.remove();
                return size;
            },
            _calculateResourcesMargin: function () {
                var margin;
                var wrapper = $('<div class=\'' + GanttView.styles.resourcesWrap + '\' style=\'visibility: hidden; position: absolute\'>');
                this.content.append(wrapper);
                margin = parseInt(wrapper.css(isRtl ? 'margin-right' : 'margin-left'), 10);
                wrapper.remove();
                return margin;
            },
            _calculateTaskBorderWidth: function () {
                var width;
                var className = GanttView.styles.task + ' ' + GanttView.styles.taskSingle;
                var task = $('<div class=\'' + className + '\' style=\'visibility: hidden; position: absolute\'>');
                var computedStyle;
                this.content.append(task);
                computedStyle = kendo.getComputedStyles(task[0], ['border-left-width']);
                width = parseFloat(computedStyle['border-left-width'], 10);
                task.remove();
                return width;
            },
            _renderTask: function (task, position) {
                var taskWrapper;
                var taskElement;
                var editable = this.options.editable;
                var progressHandleOffset;
                var taskLeft = position.left;
                var styles = GanttView.styles;
                var wrapClassName = styles.taskWrap;
                var calculatedSize = this.options.calculatedSize;
                var dragHandleStyle = {};
                var taskWrapAttr = {
                    className: wrapClassName,
                    style: { left: taskLeft + 'px' }
                };
                if (calculatedSize) {
                    taskWrapAttr.style.height = calculatedSize.cell + 'px';
                }
                if (task.summary) {
                    taskElement = this._renderSummary(task, position);
                } else if (task.isMilestone()) {
                    taskElement = this._renderMilestone(task, position);
                    taskWrapAttr.className += ' ' + styles.taskMilestoneWrap;
                } else {
                    taskElement = this._renderSingleTask(task, position);
                }
                taskWrapper = kendoDomElement('div', taskWrapAttr, [taskElement]);
                if (editable) {
                    taskWrapper.children.push(kendoDomElement('div', { className: styles.taskDot + ' ' + styles.taskDotStart }));
                    taskWrapper.children.push(kendoDomElement('div', { className: styles.taskDot + ' ' + styles.taskDotEnd }));
                }
                if (!task.summary && !task.isMilestone() && editable && this._taskTemplate === null) {
                    progressHandleOffset = Math.round(position.width * task.percentComplete);
                    dragHandleStyle[isRtl ? 'right' : 'left'] = progressHandleOffset + 'px';
                    taskWrapper.children.push(kendoDomElement('div', {
                        className: styles.taskDragHandle,
                        style: dragHandleStyle
                    }));
                }
                return taskWrapper;
            },
            _renderSingleTask: function (task, position) {
                var styles = GanttView.styles;
                var progressWidth = Math.round(position.width * task.percentComplete);
                var taskChildren = [];
                var taskContent;
                if (this._taskTemplate !== null) {
                    taskContent = kendoHtmlElement(this._taskTemplate(task));
                } else {
                    taskContent = kendoTextElement(task.title);
                    taskChildren.push(kendoDomElement('div', {
                        className: styles.taskComplete,
                        style: { width: progressWidth + 'px' }
                    }));
                }
                var content = kendoDomElement('div', { className: styles.taskContent }, [kendoDomElement('div', { className: styles.taskTemplate }, [taskContent])]);
                taskChildren.push(content);
                if (this.options.editable) {
                    content.children.push(kendoDomElement('span', { className: styles.taskActions }, [kendoDomElement('a', {
                            className: styles.link + ' ' + styles.taskDelete,
                            href: '#'
                        }, [kendoDomElement('span', { className: styles.icon + ' ' + styles.iconDelete })])]));
                    content.children.push(kendoDomElement('span', { className: styles.taskResizeHandle + ' ' + styles.taskResizeHandleWest }));
                    content.children.push(kendoDomElement('span', { className: styles.taskResizeHandle + ' ' + styles.taskResizeHandleEast }));
                }
                var element = kendoDomElement('div', {
                    className: styles.task + ' ' + styles.taskSingle,
                    'data-uid': task.uid,
                    style: { width: Math.max(position.width - position.borderWidth * 2, 0) + 'px' }
                }, taskChildren);
                return element;
            },
            _renderMilestone: function (task) {
                var styles = GanttView.styles;
                var element = kendoDomElement('div', {
                    className: styles.task + ' ' + styles.taskMilestone,
                    'data-uid': task.uid
                });
                return element;
            },
            _renderSummary: function (task, position) {
                var styles = GanttView.styles;
                var progressWidth = Math.round(position.width * task.percentComplete);
                var element = kendoDomElement('div', {
                    className: styles.task + ' ' + styles.taskSummary,
                    'data-uid': task.uid,
                    style: { width: position.width + 'px' }
                }, [kendoDomElement('div', {
                        className: styles.taskSummaryProgress,
                        style: { width: progressWidth + 'px' }
                    }, [kendoDomElement('div', {
                            className: styles.taskSummaryComplete,
                            style: { width: position.width + 'px' }
                        })])]);
                return element;
            },
            _renderResources: function (resources, className) {
                var children = [];
                var resource;
                for (var i = 0, length = resources.length; i < length; i++) {
                    resource = resources[i];
                    children.push(kendoDomElement('span', {
                        className: className,
                        style: { 'color': resource.get('color') }
                    }, [kendoTextElement(resource.get('name'))]));
                }
                if (isRtl) {
                    children.reverse();
                }
                return children;
            },
            _taskPosition: function (task) {
                var round = Math.round;
                var startLeft = round(this._offset(isRtl ? task.end : task.start));
                var endLeft = round(this._offset(isRtl ? task.start : task.end));
                return {
                    left: startLeft,
                    width: endLeft - startLeft
                };
            },
            _offset: function (date) {
                var slots = this._timeSlots();
                var slot;
                var startOffset;
                var slotDuration;
                var slotOffset = 0;
                var startIndex;
                if (!slots.length) {
                    return 0;
                }
                startIndex = this._slotIndex('start', date);
                slot = slots[startIndex];
                if (slot.end < date) {
                    slotOffset = slot.offsetWidth;
                } else if (slot.start <= date) {
                    startOffset = date - slot.start;
                    slotDuration = slot.end - slot.start;
                    slotOffset = startOffset / slotDuration * slot.offsetWidth;
                }
                if (isRtl) {
                    slotOffset = slot.offsetWidth + 1 - slotOffset;
                }
                return slot.offsetLeft + slotOffset;
            },
            _slotIndex: function (field, value, reverse) {
                var slots = this._timeSlots();
                var startIdx = 0;
                var endIdx = slots.length - 1;
                var middle;
                if (reverse) {
                    slots = [].slice.call(slots).reverse();
                }
                do {
                    middle = Math.ceil((endIdx + startIdx) / 2);
                    if (slots[middle][field] < value) {
                        startIdx = middle;
                    } else {
                        if (middle === endIdx) {
                            middle--;
                        }
                        endIdx = middle;
                    }
                } while (startIdx !== endIdx);
                if (reverse) {
                    startIdx = slots.length - 1 - startIdx;
                }
                return startIdx;
            },
            _timeByPosition: function (x, snap, snapToEnd) {
                var slot = this._slotByPosition(x);
                if (snap) {
                    return snapToEnd ? slot.end : slot.start;
                }
                var offsetLeft = x - $(DOT + GanttView.styles.tasksTable).offset().left;
                var duration = slot.end - slot.start;
                var slotOffset = offsetLeft - slot.offsetLeft;
                if (isRtl) {
                    slotOffset = slot.offsetWidth - slotOffset;
                }
                return new Date(slot.start.getTime() + duration * (slotOffset / slot.offsetWidth));
            },
            _slotByPosition: function (x) {
                var offsetLeft = x - $(DOT + GanttView.styles.tasksTable).offset().left;
                var slotIndex = this._slotIndex('offsetLeft', offsetLeft, isRtl);
                return this._timeSlots()[slotIndex];
            },
            _renderDependencies: function (dependencies) {
                var elements = [];
                var tree = this._dependencyTree;
                for (var i = 0, l = dependencies.length; i < l; i++) {
                    elements.push.apply(elements, this._renderDependency(dependencies[i]));
                }
                tree.render(elements);
            },
            _renderDependency: function (dependency) {
                var predecessor = this._taskCoordinates[dependency.predecessorId];
                var successor = this._taskCoordinates[dependency.successorId];
                var elements;
                var method;
                if (!predecessor || !successor) {
                    return [];
                }
                method = '_render' + [
                    'FF',
                    'FS',
                    'SF',
                    'SS'
                ][isRtl ? 3 - dependency.type : dependency.type];
                elements = this[method](predecessor, successor);
                for (var i = 0, length = elements.length; i < length; i++) {
                    elements[i].attr['data-uid'] = dependency.uid;
                }
                return elements;
            },
            _renderFF: function (from, to) {
                var lines = this._dependencyFF(from, to, false);
                lines[lines.length - 1].children[0] = this._arrow(true);
                return lines;
            },
            _renderSS: function (from, to) {
                var lines = this._dependencyFF(to, from, true);
                lines[0].children[0] = this._arrow(false);
                return lines.reverse();
            },
            _renderFS: function (from, to) {
                var lines = this._dependencyFS(from, to, false);
                lines[lines.length - 1].children[0] = this._arrow(false);
                return lines;
            },
            _renderSF: function (from, to) {
                var lines = this._dependencyFS(to, from, true);
                lines[0].children[0] = this._arrow(true);
                return lines.reverse();
            },
            _dependencyFF: function (from, to, reverse) {
                var that = this;
                var lines = [];
                var left = 0;
                var top = 0;
                var width = 0;
                var height = 0;
                var dir = reverse ? 'start' : 'end';
                var delta;
                var overlap = 2;
                var arrowOverlap = 1;
                var rowHeight = this._rowHeight;
                var minLineWidth = 10;
                var fromTop = from.rowIndex * rowHeight + Math.floor(rowHeight / 2) - 1;
                var toTop = to.rowIndex * rowHeight + Math.floor(rowHeight / 2) - 1;
                var styles = GanttView.styles;
                var addHorizontal = function () {
                    lines.push(that._line(styles.line + ' ' + styles.lineHorizontal, {
                        left: left + 'px',
                        top: top + 'px',
                        width: width + 'px'
                    }));
                };
                var addVertical = function () {
                    lines.push(that._line(styles.line + ' ' + styles.lineVertical, {
                        left: left + 'px',
                        top: top + 'px',
                        height: height + 'px'
                    }));
                };
                left = from[dir];
                top = fromTop;
                width = minLineWidth;
                delta = to[dir] - from[dir];
                if (delta > 0 !== reverse) {
                    width = Math.abs(delta) + minLineWidth;
                }
                if (reverse) {
                    left -= width;
                    width -= arrowOverlap;
                    addHorizontal();
                } else {
                    addHorizontal();
                    left += width - overlap;
                }
                if (toTop < top) {
                    height = top - toTop;
                    height += overlap;
                    top = toTop;
                    addVertical();
                } else {
                    height = toTop - top;
                    height += overlap;
                    addVertical();
                    top += height - overlap;
                }
                width = Math.abs(left - to[dir]);
                if (!reverse) {
                    width -= arrowOverlap;
                    left -= width;
                }
                addHorizontal();
                return lines;
            },
            _dependencyFS: function (from, to, reverse) {
                var that = this;
                var lines = [];
                var left = 0;
                var top = 0;
                var width = 0;
                var height = 0;
                var rowHeight = this._rowHeight;
                var minLineHeight = Math.floor(rowHeight / 2);
                var minLineWidth = 10;
                var minDistance = 2 * minLineWidth;
                var delta = to.start - from.end;
                var overlap = 2;
                var arrowOverlap = 1;
                var fromTop = from.rowIndex * rowHeight + Math.floor(rowHeight / 2) - 1;
                var toTop = to.rowIndex * rowHeight + Math.floor(rowHeight / 2) - 1;
                var styles = GanttView.styles;
                var addHorizontal = function () {
                    lines.push(that._line(styles.line + ' ' + styles.lineHorizontal, {
                        left: left + 'px',
                        top: top + 'px',
                        width: width + 'px'
                    }));
                };
                var addVertical = function () {
                    lines.push(that._line(styles.line + ' ' + styles.lineVertical, {
                        left: left + 'px',
                        top: top + 'px',
                        height: height + 'px'
                    }));
                };
                left = from.end;
                top = fromTop;
                width = minLineWidth;
                if (reverse) {
                    left += arrowOverlap;
                    if (delta > minDistance) {
                        width = delta - (minLineWidth - overlap);
                    }
                    width -= arrowOverlap;
                }
                addHorizontal();
                left += width - overlap;
                if (delta <= minDistance) {
                    height = reverse ? Math.abs(toTop - fromTop) - minLineHeight : minLineHeight;
                    if (toTop < fromTop) {
                        top -= height;
                        height += overlap;
                        addVertical();
                    } else {
                        addVertical();
                        top += height;
                    }
                    width = from.end - to.start + minDistance;
                    if (width < minLineWidth) {
                        width = minLineWidth;
                    }
                    left -= width - overlap;
                    addHorizontal();
                }
                if (toTop < fromTop) {
                    height = top - toTop;
                    top = toTop;
                    height += overlap;
                    addVertical();
                } else {
                    height = toTop - top;
                    addVertical();
                    top += height;
                }
                width = to.start - left;
                if (!reverse) {
                    width -= arrowOverlap;
                }
                addHorizontal();
                return lines;
            },
            _line: function (className, styles) {
                return kendoDomElement('div', {
                    className: className,
                    style: styles
                });
            },
            _arrow: function (direction) {
                return kendoDomElement('span', { className: direction ? GanttView.styles.arrowWest : GanttView.styles.arrowEast });
            },
            _colgroup: function () {
                var slots = this._timeSlots();
                var count = slots.length;
                var cols = [];
                for (var i = 0; i < count; i++) {
                    for (var j = 0, length = slots[i].span; j < length; j++) {
                        cols.push(kendoDomElement('col'));
                    }
                }
                return kendoDomElement('colgroup', null, cols);
            },
            _createDragHint: function (element) {
                this._dragHint = element.clone().addClass(GanttView.styles.dragHint).css('cursor', 'move');
                element.parent().append(this._dragHint);
            },
            _updateDragHint: function (start) {
                var left = this._offset(start);
                this._dragHint.css({ 'left': left });
            },
            _removeDragHint: function () {
                this._dragHint.remove();
                this._dragHint = null;
            },
            _createResizeHint: function (task) {
                var styles = GanttView.styles;
                var taskTop = this._taskCoordinates[task.id].rowIndex * this._rowHeight;
                var tooltipHeight;
                var tooltipTop;
                var options = this.options;
                var messages = options.messages;
                this._resizeHint = $(RESIZE_HINT({ styles: styles })).css({
                    'top': 0,
                    'height': this._contentHeight
                });
                this.content.append(this._resizeHint);
                this._resizeTooltip = $(RESIZE_TOOLTIP_TEMPLATE({
                    styles: styles,
                    start: task.start,
                    end: task.end,
                    messages: messages.views,
                    format: options.resizeTooltipFormat
                })).css({
                    'top': 0,
                    'left': 0
                });
                this.content.append(this._resizeTooltip);
                this._resizeTooltipWidth = this._resizeTooltip.outerWidth();
                tooltipHeight = this._resizeTooltip.outerHeight();
                tooltipTop = taskTop - tooltipHeight;
                if (tooltipTop < 0) {
                    tooltipTop = taskTop + this._rowHeight;
                }
                this._resizeTooltipTop = tooltipTop;
            },
            _updateResizeHint: function (start, end, resizeStart) {
                var left = this._offset(isRtl ? end : start);
                var right = this._offset(isRtl ? start : end);
                var width = right - left;
                var tooltipLeft = resizeStart !== isRtl ? left : right;
                var tablesWidth = this._tableWidth - kendo.support.scrollbar();
                var tooltipWidth = this._resizeTooltipWidth;
                var options = this.options;
                var messages = options.messages;
                var tableOffset = $(DOT + GanttView.styles.tasksTable).offset().left - $(DOT + GanttView.styles.tasksWrapper).offset().left;
                if (isRtl) {
                    left += tableOffset;
                }
                this._resizeHint.css({
                    'left': left,
                    'width': width
                });
                if (this._resizeTooltip) {
                    this._resizeTooltip.remove();
                }
                tooltipLeft -= Math.round(tooltipWidth / 2);
                if (tooltipLeft < 0) {
                    tooltipLeft = 0;
                } else if (tooltipLeft + tooltipWidth > tablesWidth) {
                    tooltipLeft = tablesWidth - tooltipWidth;
                }
                if (isRtl) {
                    tooltipLeft += tableOffset;
                }
                this._resizeTooltip = $(RESIZE_TOOLTIP_TEMPLATE({
                    styles: GanttView.styles,
                    start: start,
                    end: end,
                    messages: messages.views,
                    format: options.resizeTooltipFormat
                })).css({
                    'top': this._resizeTooltipTop,
                    'left': tooltipLeft,
                    'min-width': tooltipWidth
                }).appendTo(this.content);
            },
            _removeResizeHint: function () {
                this._resizeHint.remove();
                this._resizeHint = null;
                this._resizeTooltip.remove();
                this._resizeTooltip = null;
            },
            _updatePercentCompleteTooltip: function (top, left, text) {
                this._removePercentCompleteTooltip();
                var tooltip = this._percentCompleteResizeTooltip = $(PERCENT_RESIZE_TOOLTIP_TEMPLATE({
                    styles: GanttView.styles,
                    text: text
                })).appendTo(this.element);
                var tooltipMiddle = Math.round(tooltip.outerWidth() / 2);
                var arrow = tooltip.find(DOT + GanttView.styles.callout);
                var arrowHeight = Math.round(arrow.outerWidth() / 2);
                tooltip.css({
                    'top': top - (tooltip.outerHeight() + arrowHeight),
                    'left': left - tooltipMiddle
                });
                arrow.css('left', tooltipMiddle - arrowHeight);
            },
            _removePercentCompleteTooltip: function () {
                if (this._percentCompleteResizeTooltip) {
                    this._percentCompleteResizeTooltip.remove();
                }
                this._percentCompleteResizeTooltip = null;
            },
            _updateDependencyDragHint: function (from, to, useVML) {
                this._removeDependencyDragHint();
                if (useVML) {
                    this._creteVmlDependencyDragHint(from, to);
                } else {
                    this._creteDependencyDragHint(from, to);
                }
            },
            _creteDependencyDragHint: function (from, to) {
                var styles = GanttView.styles;
                var deltaX = to.x - from.x;
                var deltaY = to.y - from.y;
                var width = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                var angle = Math.atan(deltaY / deltaX);
                if (deltaX < 0) {
                    angle += Math.PI;
                }
                $('<div class=\'' + styles.line + ' ' + styles.lineHorizontal + ' ' + styles.dependencyHint + '\'></div>').css({
                    'top': from.y,
                    'left': from.x,
                    'width': width,
                    'transform-origin': '0% 0',
                    '-ms-transform-origin': '0% 0',
                    '-webkit-transform-origin': '0% 0',
                    'transform': 'rotate(' + angle + 'rad)',
                    '-ms-transform': 'rotate(' + angle + 'rad)',
                    '-webkit-transform': 'rotate(' + angle + 'rad)'
                }).appendTo(this.content);
            },
            _creteVmlDependencyDragHint: function (from, to) {
                var hint = $('<kvml:line class=\'' + GanttView.styles.dependencyHint + '\' style=\'position:absolute; top: 0px; left: 0px;\' strokecolor=\'black\' strokeweight=\'2px\' from=\'' + from.x + 'px,' + from.y + 'px\' to=\'' + to.x + 'px,' + to.y + 'px\'' + '></kvml:line>').appendTo(this.content);
                hint[0].outerHTML = hint[0].outerHTML;
            },
            _removeDependencyDragHint: function () {
                this.content.find(DOT + GanttView.styles.dependencyHint).remove();
            },
            _createTaskTooltip: function (task, element, mouseLeft) {
                var styles = GanttView.styles;
                var options = this.options;
                var content = this.content;
                var contentOffset = content.offset();
                var contentWidth = content.width();
                var contentScrollLeft = kendo.scrollLeft(content);
                var row = $(element).parents('tr').first();
                var rowOffset = row.offset();
                var template = options.tooltip && options.tooltip.template ? kendo.template(options.tooltip.template) : TASK_TOOLTIP_TEMPLATE;
                var left = isRtl ? mouseLeft - (contentOffset.left + contentScrollLeft + kendo.support.scrollbar()) : mouseLeft - (contentOffset.left - contentScrollLeft);
                var top = rowOffset.top + row.outerHeight() - contentOffset.top + content.scrollTop();
                var tooltip = this._taskTooltip = $('<div style="z-index: 100002;" class="' + styles.tooltipWrapper + '" >' + '<div class="' + styles.taskContent + '"></div></div>');
                var tooltipWidth;
                tooltip.css({
                    'left': left,
                    'top': top
                }).appendTo(content).find(DOT + styles.taskContent).append(template({
                    styles: styles,
                    task: task,
                    messages: options.messages.views
                }));
                if (tooltip.outerHeight() < rowOffset.top - contentOffset.top) {
                    tooltip.css('top', rowOffset.top - contentOffset.top - tooltip.outerHeight() + content.scrollTop());
                }
                tooltipWidth = tooltip.outerWidth();
                if (tooltipWidth + left - contentScrollLeft > contentWidth) {
                    left -= tooltipWidth;
                    if (left < contentScrollLeft) {
                        left = contentScrollLeft + contentWidth - (tooltipWidth + 17);
                    }
                    tooltip.css('left', left);
                }
            },
            _removeTaskTooltip: function () {
                if (this._taskTooltip) {
                    this._taskTooltip.remove();
                }
                this._taskTooltip = null;
            },
            _scrollTo: function (element) {
                var elementLeft = element.offset().left;
                var elementWidth = element.width();
                var elementRight = elementLeft + elementWidth;
                var row = element.closest('tr');
                var rowTop = row.offset().top;
                var rowHeight = row.height();
                var rowBottom = rowTop + rowHeight;
                var content = this.content;
                var contentOffset = content.offset();
                var contentTop = contentOffset.top;
                var contentHeight = content.height();
                var contentBottom = contentTop + contentHeight;
                var contentLeft = contentOffset.left;
                var contentWidth = content.width();
                var contentRight = contentLeft + contentWidth;
                var scrollbarWidth = kendo.support.scrollbar();
                if (rowTop < contentTop) {
                    content.scrollTop(content.scrollTop() + (rowTop - contentTop));
                } else if (rowBottom > contentBottom) {
                    content.scrollTop(content.scrollTop() + (rowBottom + scrollbarWidth - contentBottom));
                }
                if (elementLeft < contentLeft && elementWidth > contentWidth && elementRight < contentRight || elementRight > contentRight && elementWidth < contentWidth) {
                    content.scrollLeft(content.scrollLeft() + (elementRight + scrollbarWidth - contentRight));
                } else if (elementRight > contentRight && elementWidth > contentWidth && elementLeft > contentLeft || elementLeft < contentLeft && elementWidth < contentWidth) {
                    content.scrollLeft(content.scrollLeft() + (elementLeft - contentLeft));
                }
            },
            _timeSlots: function () {
                if (!this._slots || !this._slots.length) {
                    return [];
                }
                return this._slots[this._slots.length - 1];
            },
            _headers: function (columnLevels) {
                var rows = [];
                var level;
                var headers;
                var column;
                var headerText;
                var styles = GanttView.styles;
                for (var levelIndex = 0, levelCount = columnLevels.length; levelIndex < levelCount; levelIndex++) {
                    level = columnLevels[levelIndex];
                    headers = [];
                    for (var columnIndex = 0, columnCount = level.length; columnIndex < columnCount; columnIndex++) {
                        column = level[columnIndex];
                        headerText = kendoTextElement(column.text);
                        headers.push(kendoDomElement('th', {
                            colspan: column.span,
                            className: styles.header + (column.isNonWorking ? ' ' + styles.nonWorking : '')
                        }, [headerText]));
                    }
                    rows.push(kendoDomElement('tr', null, headers));
                }
                return rows;
            },
            _hours: function (start, end) {
                var slotEnd;
                var slots = [];
                var options = this.options;
                var workDayStart = options.workDayStart.getHours();
                var workDayEnd = options.workDayEnd.getHours();
                var isWorkHour;
                var hours;
                var hourSpan = options.hourSpan;
                start = new Date(start);
                end = new Date(end);
                while (start < end) {
                    slotEnd = new Date(start);
                    hours = slotEnd.getHours();
                    isWorkHour = hours >= workDayStart && hours < workDayEnd;
                    slotEnd.setHours(slotEnd.getHours() + hourSpan);
                    if (hours == slotEnd.getHours()) {
                        slotEnd.setHours(slotEnd.getHours() + 2 * hourSpan);
                    }
                    if (!options.showWorkHours || isWorkHour) {
                        slots.push({
                            start: start,
                            end: slotEnd,
                            isNonWorking: !isWorkHour,
                            span: 1
                        });
                    }
                    start = slotEnd;
                }
                return slots;
            },
            _days: function (start, end) {
                var slotEnd;
                var slots = [];
                var isWorkDay;
                start = new Date(start);
                end = new Date(end);
                while (start < end) {
                    slotEnd = kendo.date.nextDay(start);
                    isWorkDay = this._isWorkDay(start);
                    if (!this.options.showWorkDays || isWorkDay) {
                        slots.push({
                            start: start,
                            end: slotEnd,
                            isNonWorking: !isWorkDay,
                            span: 1
                        });
                    }
                    start = slotEnd;
                }
                return slots;
            },
            _weeks: function (start, end) {
                var slotEnd;
                var slots = [];
                var firstDay = this.calendarInfo().firstDay;
                var daySlots;
                var span;
                start = new Date(start);
                end = new Date(end);
                while (start < end) {
                    slotEnd = kendo.date.dayOfWeek(kendo.date.addDays(start, 1), firstDay, 1);
                    if (slotEnd > end) {
                        slotEnd = end;
                    }
                    daySlots = this._days(start, slotEnd);
                    span = daySlots.length;
                    if (span > 0) {
                        slots.push({
                            start: daySlots[0].start,
                            end: daySlots[span - 1].end,
                            span: span
                        });
                    }
                    start = slotEnd;
                }
                return slots;
            },
            _months: function (start, end) {
                var slotEnd;
                var slots = [];
                var daySlots;
                var span;
                start = new Date(start);
                end = new Date(end);
                while (start < end) {
                    slotEnd = new Date(start);
                    slotEnd.setMonth(slotEnd.getMonth() + 1);
                    daySlots = this._days(start, slotEnd);
                    span = daySlots.length;
                    if (span > 0) {
                        slots.push({
                            start: daySlots[0].start,
                            end: daySlots[span - 1].end,
                            span: span
                        });
                    }
                    start = slotEnd;
                }
                return slots;
            },
            _years: function (start, end) {
                var slotEnd;
                var slots = [];
                start = new Date(start);
                end = new Date(end);
                while (start < end) {
                    slotEnd = new Date(start);
                    slotEnd.setFullYear(slotEnd.getFullYear() + 1);
                    slots.push({
                        start: start,
                        end: slotEnd,
                        span: 12
                    });
                    start = slotEnd;
                }
                return slots;
            },
            _slotHeaders: function (slots, template) {
                var columns = [];
                var slot;
                for (var i = 0, l = slots.length; i < l; i++) {
                    slot = slots[i];
                    columns.push({
                        text: template(slot),
                        isNonWorking: !!slot.isNonWorking,
                        span: slot.span
                    });
                }
                return columns;
            },
            _isWorkDay: function (date) {
                var day = date.getDay();
                var workDays = this._workDays;
                for (var i = 0, l = workDays.length; i < l; i++) {
                    if (workDays[i] === day) {
                        return true;
                    }
                }
                return false;
            },
            calendarInfo: function () {
                return kendo.getCulture().calendars.standard;
            },
            _renderCurrentTime: function () {
                var currentTime = this._getCurrentTime();
                var timeOffset = this._offset(currentTime);
                var element = $('<div class=\'k-current-time\'></div>');
                var viewStyles = GanttView.styles;
                var tablesWrap = $(DOT + viewStyles.tasksWrapper);
                var tasksTable = $(DOT + viewStyles.tasksTable);
                var slot;
                if (!this.content || !this._timeSlots().length) {
                    return;
                }
                this.content.find('.k-current-time').remove();
                slot = this._timeSlots()[this._slotIndex('start', currentTime)];
                if (currentTime < slot.start || currentTime > slot.end) {
                    return;
                }
                if (tablesWrap.length && tasksTable.length) {
                    timeOffset += tasksTable.offset().left - tablesWrap.offset().left;
                }
                element.css({
                    left: timeOffset + 'px',
                    top: '0px',
                    width: '1px',
                    height: this._contentHeight + 'px'
                }).appendTo(this.content);
            },
            _getCurrentTime: function () {
                return new Date();
            },
            _currentTime: function () {
                var markerOptions = this.options.currentTimeMarker;
                if (markerOptions !== false && markerOptions.updateInterval !== undefined) {
                    this._renderCurrentTime();
                    this._currentTimeUpdateTimer = setInterval(proxy(this._renderCurrentTime, this), markerOptions.updateInterval);
                }
            }
        });
        extend(true, GanttView, { styles: viewStyles });
        kendo.ui.GanttDayView = GanttView.extend({
            name: 'day',
            options: {
                timeHeaderTemplate: TIME_HEADER_TEMPLATE,
                dayHeaderTemplate: DAY_HEADER_TEMPLATE,
                resizeTooltipFormat: 'h:mm tt ddd, MMM d'
            },
            range: function (range) {
                this.start = kendo.date.getDate(range.start);
                this.end = kendo.date.getDate(range.end);
                if (kendo.date.getMilliseconds(range.end) > 0 || this.end.getTime() === this.start.getTime()) {
                    this.end = kendo.date.addDays(this.end, 1);
                }
            },
            _createSlots: function () {
                var daySlots;
                var daySlot;
                var hourSlots;
                var hours;
                var slots = [];
                daySlots = this._days(this.start, this.end);
                hourSlots = [];
                for (var i = 0, l = daySlots.length; i < l; i++) {
                    daySlot = daySlots[i];
                    hours = this._hours(daySlot.start, daySlot.end);
                    daySlot.span = hours.length;
                    hourSlots.push.apply(hourSlots, hours);
                }
                slots.push(daySlots);
                slots.push(hourSlots);
                return slots;
            },
            _layout: function () {
                var rows = [];
                var options = this.options;
                rows.push(this._slotHeaders(this._slots[0], kendo.template(options.dayHeaderTemplate)));
                rows.push(this._slotHeaders(this._slots[1], kendo.template(options.timeHeaderTemplate)));
                return rows;
            }
        });
        kendo.ui.GanttWeekView = GanttView.extend({
            name: 'week',
            options: {
                dayHeaderTemplate: DAY_HEADER_TEMPLATE,
                weekHeaderTemplate: WEEK_HEADER_TEMPLATE,
                resizeTooltipFormat: 'h:mm tt ddd, MMM d'
            },
            range: function (range) {
                var calendarInfo = this.calendarInfo();
                var firstDay = calendarInfo.firstDay;
                var rangeEnd = range.end;
                if (firstDay === rangeEnd.getDay()) {
                    rangeEnd.setDate(rangeEnd.getDate() + 7);
                }
                this.start = kendo.date.getDate(kendo.date.dayOfWeek(range.start, firstDay, -1));
                this.end = kendo.date.getDate(kendo.date.dayOfWeek(rangeEnd, firstDay, 1));
            },
            _createSlots: function () {
                var slots = [];
                slots.push(this._weeks(this.start, this.end));
                slots.push(this._days(this.start, this.end));
                return slots;
            },
            _layout: function () {
                var rows = [];
                var options = this.options;
                rows.push(this._slotHeaders(this._slots[0], kendo.template(options.weekHeaderTemplate)));
                rows.push(this._slotHeaders(this._slots[1], kendo.template(options.dayHeaderTemplate)));
                return rows;
            }
        });
        kendo.ui.GanttMonthView = GanttView.extend({
            name: 'month',
            options: {
                weekHeaderTemplate: WEEK_HEADER_TEMPLATE,
                monthHeaderTemplate: MONTH_HEADER_TEMPLATE,
                resizeTooltipFormat: 'dddd, MMM d, yyyy'
            },
            range: function (range) {
                this.start = kendo.date.firstDayOfMonth(range.start);
                this.end = kendo.date.addDays(kendo.date.getDate(kendo.date.lastDayOfMonth(range.end)), 1);
            },
            _createSlots: function () {
                var slots = [];
                slots.push(this._months(this.start, this.end));
                slots.push(this._weeks(this.start, this.end));
                return slots;
            },
            _layout: function () {
                var rows = [];
                var options = this.options;
                rows.push(this._slotHeaders(this._slots[0], kendo.template(options.monthHeaderTemplate)));
                rows.push(this._slotHeaders(this._slots[1], kendo.template(options.weekHeaderTemplate)));
                return rows;
            }
        });
        kendo.ui.GanttYearView = GanttView.extend({
            name: 'year',
            options: {
                yearHeaderTemplate: YEAR_HEADER_TEMPLATE,
                monthHeaderTemplate: MONTH_HEADER_TEMPLATE,
                resizeTooltipFormat: 'dddd, MMM d, yyyy'
            },
            range: function (range) {
                this.start = kendo.date.firstDayOfMonth(new Date(range.start.setMonth(0)));
                this.end = kendo.date.firstDayOfMonth(new Date(range.end.setMonth(12)));
            },
            _createSlots: function () {
                var slots = [];
                var monthSlots = this._months(this.start, this.end);
                $(monthSlots).each(function (index, slot) {
                    slot.span = 1;
                });
                slots.push(this._years(this.start, this.end));
                slots.push(monthSlots);
                return slots;
            },
            _layout: function () {
                var rows = [];
                var options = this.options;
                rows.push(this._slotHeaders(this._slots[0], kendo.template(options.yearHeaderTemplate)));
                rows.push(this._slotHeaders(this._slots[1], kendo.template(options.monthHeaderTemplate)));
                return rows;
            }
        });
        var timelineStyles = {
            wrapper: 'k-timeline k-grid k-widget',
            gridHeader: 'k-grid-header',
            gridHeaderWrap: 'k-grid-header-wrap',
            gridContent: 'k-grid-content',
            gridContentWrap: 'k-grid-content',
            tasksWrapper: 'k-gantt-tables',
            dependenciesWrapper: 'k-gantt-dependencies',
            task: 'k-task',
            line: 'k-line',
            taskResizeHandle: 'k-resize-handle',
            taskResizeHandleWest: 'k-resize-w',
            taskDragHandle: 'k-task-draghandle',
            taskComplete: 'k-task-complete',
            taskDelete: 'k-task-delete',
            taskWrapActive: 'k-task-wrap-active',
            taskWrap: 'k-task-wrap',
            taskDot: 'k-task-dot',
            taskDotStart: 'k-task-start',
            taskDotEnd: 'k-task-end',
            hovered: 'k-state-hover',
            selected: 'k-state-selected',
            origin: 'k-origin'
        };
        var GanttTimeline = kendo.ui.GanttTimeline = Widget.extend({
            init: function (element, options) {
                Widget.fn.init.call(this, element, options);
                if (!this.options.views || !this.options.views.length) {
                    this.options.views = [
                        'day',
                        'week',
                        'month'
                    ];
                }
                isRtl = kendo.support.isRtl(element);
                this._wrapper();
                this._domTrees();
                this._views();
                this._selectable();
                this._draggable();
                this._resizable();
                this._percentResizeDraggable();
                this._createDependencyDraggable();
                this._attachEvents();
                this._tooltip();
            },
            options: {
                name: 'GanttTimeline',
                messages: {
                    views: {
                        day: 'Day',
                        week: 'Week',
                        month: 'Month',
                        year: 'Year',
                        start: 'Start',
                        end: 'End'
                    }
                },
                snap: true,
                selectable: true,
                editable: true
            },
            destroy: function () {
                Widget.fn.destroy.call(this);
                clearTimeout(this._tooltipTimeout);
                if (this._currentTimeUpdateTimer) {
                    clearInterval(this._currentTimeUpdateTimer);
                }
                this._unbindView(this._selectedView);
                if (this._moveDraggable) {
                    this._moveDraggable.destroy();
                }
                if (this._resizeDraggable) {
                    this._resizeDraggable.destroy();
                }
                if (this._percentDraggable) {
                    this._percentDraggable.destroy();
                }
                if (this._dependencyDraggable) {
                    this._dependencyDraggable.destroy();
                }
                if (this.touch) {
                    this.touch.destroy();
                }
                this._headerTree = null;
                this._taskTree = null;
                this._dependencyTree = null;
                this.wrapper.off(NS);
                kendo.destroy(this.wrapper);
            },
            _wrapper: function () {
                var styles = GanttTimeline.styles;
                var that = this;
                var options = this.options;
                var calculateSize = function () {
                    var rowHeight = typeof options.rowHeight === STRING ? options.rowHeight : options.rowHeight + 'px';
                    var table = $(kendo.format(SIZE_CALCULATION_TEMPLATE, rowHeight));
                    var calculatedRowHeight;
                    var calculatedCellHeight;
                    var content = that.wrapper.find(DOT + styles.tasksWrapper);
                    content.append(table);
                    calculatedRowHeight = table.find('tr').outerHeight();
                    calculatedCellHeight = table.find('td').height();
                    table.remove();
                    return {
                        'row': calculatedRowHeight,
                        'cell': calculatedCellHeight
                    };
                };
                this.wrapper = this.element.addClass(styles.wrapper).append('<div class=\'' + styles.gridHeader + '\'><div class=\'' + styles.gridHeaderWrap + '\'></div></div>').append('<div class=\'' + styles.gridContentWrap + '\'><div class=\'' + styles.tasksWrapper + '\'></div><div class=\'' + styles.dependenciesWrapper + '\'></div></div>');
                if (options.rowHeight) {
                    this._calculatedSize = calculateSize();
                }
            },
            _domTrees: function () {
                var styles = GanttTimeline.styles;
                var tree = kendo.dom.Tree;
                var wrapper = this.wrapper;
                this._headerTree = new tree(wrapper.find(DOT + styles.gridHeaderWrap)[0]);
                this._taskTree = new tree(wrapper.find(DOT + styles.tasksWrapper)[0]);
                this._dependencyTree = new tree(wrapper.find(DOT + styles.dependenciesWrapper)[0]);
            },
            _views: function () {
                var views = this.options.views;
                var view;
                var isSettings;
                var name;
                var defaultView;
                var selected;
                this.views = {};
                for (var i = 0, l = views.length; i < l; i++) {
                    view = views[i];
                    isSettings = isPlainObject(view);
                    if (isSettings && view.selectable === false) {
                        continue;
                    }
                    name = isSettings ? typeof view.type !== 'string' ? view.title : view.type : view;
                    defaultView = defaultViews[name];
                    if (defaultView) {
                        if (isSettings) {
                            view.type = defaultView.type;
                        }
                        defaultView.title = this.options.messages.views[name];
                    }
                    view = extend({ title: name }, defaultView, isSettings ? view : {});
                    if (name) {
                        this.views[name] = view;
                        if (!selected || view.selected) {
                            selected = name;
                        }
                    }
                }
                if (selected) {
                    this._selectedViewName = selected;
                }
            },
            view: function (name) {
                if (name) {
                    this._selectView(name);
                    this.trigger('navigate', {
                        view: name,
                        action: 'changeView'
                    });
                }
                return this._selectedView;
            },
            _selectView: function (name) {
                if (name && this.views[name]) {
                    if (this._selectedView) {
                        this._unbindView(this._selectedView);
                    }
                    this._selectedView = this._initializeView(name);
                    this._selectedViewName = name;
                }
            },
            _viewByIndex: function (index) {
                var view;
                var views = this.views;
                for (view in views) {
                    if (!index) {
                        return view;
                    }
                    index--;
                }
            },
            _initializeView: function (name) {
                var view = this.views[name];
                if (view) {
                    var type = view.type;
                    if (typeof type === 'string') {
                        type = kendo.getter(view.type)(window);
                    }
                    if (type) {
                        view = new type(this.wrapper, trimOptions(extend(true, {
                            headerTree: this._headerTree,
                            taskTree: this._taskTree,
                            dependencyTree: this._dependencyTree,
                            calculatedSize: this._calculatedSize
                        }, view, this.options)));
                    } else {
                        throw new Error('There is no such view');
                    }
                }
                return view;
            },
            _unbindView: function (view) {
                if (view) {
                    view.destroy();
                }
            },
            _range: function (tasks) {
                var startOrder = {
                    field: 'start',
                    dir: 'asc'
                };
                var endOrder = {
                    field: 'end',
                    dir: 'desc'
                };
                if (!tasks || !tasks.length) {
                    return {
                        start: new Date(),
                        end: new Date()
                    };
                }
                var start = new Query(tasks).sort(startOrder).toArray()[0].start || new Date();
                var end = new Query(tasks).sort(endOrder).toArray()[0].end || new Date();
                return {
                    start: new Date(start),
                    end: new Date(end)
                };
            },
            _render: function (tasks) {
                var view = this.view();
                var range = this._range(tasks);
                this._tasks = tasks;
                view.range(range);
                view.renderLayout();
                view.render(tasks);
            },
            _renderDependencies: function (dependencies) {
                this.view()._renderDependencies(dependencies);
            },
            _taskByUid: function (uid) {
                var tasks = this._tasks;
                var length = tasks.length;
                var task;
                for (var i = 0; i < length; i++) {
                    task = tasks[i];
                    if (task.uid === uid) {
                        return task;
                    }
                }
            },
            _draggable: function () {
                var that = this;
                var element;
                var task;
                var currentStart;
                var startOffset;
                var snap = this.options.snap;
                var styles = GanttTimeline.styles;
                var cleanUp = function () {
                    that.view()._removeDragHint();
                    if (element) {
                        element.css('opacity', 1);
                    }
                    element = null;
                    task = null;
                    that.dragInProgress = false;
                };
                if (!this.options.editable) {
                    return;
                }
                this._moveDraggable = new kendo.ui.Draggable(this.wrapper, {
                    distance: 0,
                    filter: DOT + styles.task,
                    holdToDrag: kendo.support.mobileOS,
                    ignore: DOT + styles.taskResizeHandle
                });
                this._moveDraggable.bind('dragstart', function (e) {
                    var view = that.view();
                    element = e.currentTarget.parent();
                    task = that._taskByUid(e.currentTarget.attr('data-uid'));
                    if (that.trigger('moveStart', { task: task })) {
                        e.preventDefault();
                        return;
                    }
                    currentStart = task.start;
                    startOffset = view._timeByPosition(e.x.location, snap) - currentStart;
                    view._createDragHint(element);
                    element.css('opacity', 0.5);
                    clearTimeout(that._tooltipTimeout);
                    that.dragInProgress = true;
                }).bind('drag', kendo.throttle(function (e) {
                    if (!that.dragInProgress) {
                        return;
                    }
                    var view = that.view();
                    var date = new Date(view._timeByPosition(e.x.location, snap) - startOffset);
                    var updateHintDate = date;
                    if (!that.trigger('move', {
                            task: task,
                            start: date
                        })) {
                        currentStart = date;
                        if (isRtl) {
                            updateHintDate = new Date(currentStart.getTime() + task.duration());
                        }
                        view._updateDragHint(updateHintDate);
                    }
                }, 15)).bind('dragend', function () {
                    that.trigger('moveEnd', {
                        task: task,
                        start: currentStart
                    });
                    cleanUp();
                }).bind('dragcancel', function () {
                    cleanUp();
                }).userEvents.bind('select', function () {
                    blurActiveElement();
                });
            },
            _resizable: function () {
                var that = this;
                var element;
                var task;
                var currentStart;
                var currentEnd;
                var resizeStart;
                var snap = this.options.snap;
                var styles = GanttTimeline.styles;
                var cleanUp = function () {
                    that.view()._removeResizeHint();
                    element = null;
                    task = null;
                    that.dragInProgress = false;
                };
                if (!this.options.editable) {
                    return;
                }
                this._resizeDraggable = new kendo.ui.Draggable(this.wrapper, {
                    distance: 0,
                    filter: DOT + styles.taskResizeHandle,
                    holdToDrag: false
                });
                this._resizeDraggable.bind('dragstart', function (e) {
                    resizeStart = e.currentTarget.hasClass(styles.taskResizeHandleWest);
                    if (isRtl) {
                        resizeStart = !resizeStart;
                    }
                    element = e.currentTarget.closest(DOT + styles.task);
                    task = that._taskByUid(element.attr('data-uid'));
                    if (that.trigger('resizeStart', { task: task })) {
                        e.preventDefault();
                        return;
                    }
                    currentStart = task.start;
                    currentEnd = task.end;
                    that.view()._createResizeHint(task);
                    clearTimeout(that._tooltipTimeout);
                    that.dragInProgress = true;
                }).bind('drag', kendo.throttle(function (e) {
                    if (!that.dragInProgress) {
                        return;
                    }
                    var view = that.view();
                    var date = view._timeByPosition(e.x.location, snap, !resizeStart);
                    if (resizeStart) {
                        if (date < currentEnd) {
                            currentStart = date;
                        } else {
                            currentStart = currentEnd;
                        }
                    } else {
                        if (date > currentStart) {
                            currentEnd = date;
                        } else {
                            currentEnd = currentStart;
                        }
                    }
                    if (!that.trigger('resize', {
                            task: task,
                            start: currentStart,
                            end: currentEnd
                        })) {
                        view._updateResizeHint(currentStart, currentEnd, resizeStart);
                    }
                }, 15)).bind('dragend', function () {
                    that.trigger('resizeEnd', {
                        task: task,
                        resizeStart: resizeStart,
                        start: currentStart,
                        end: currentEnd
                    });
                    cleanUp();
                }).bind('dragcancel', function () {
                    cleanUp();
                }).userEvents.bind('select', function () {
                    blurActiveElement();
                });
            },
            _percentResizeDraggable: function () {
                var that = this;
                var task;
                var taskElement;
                var taskElementOffset;
                var timelineOffset;
                var originalPercentWidth;
                var maxPercentWidth;
                var currentPercentComplete;
                var tooltipTop;
                var tooltipLeft;
                var styles = GanttTimeline.styles;
                var delta;
                var cleanUp = function () {
                    that.view()._removePercentCompleteTooltip();
                    taskElement = null;
                    task = null;
                    that.dragInProgress = false;
                };
                var updateElement = function (width) {
                    taskElement.find(DOT + styles.taskComplete).width(width).end().siblings(DOT + styles.taskDragHandle).css(isRtl ? 'right' : 'left', width);
                };
                if (!this.options.editable) {
                    return;
                }
                this._percentDraggable = new kendo.ui.Draggable(this.wrapper, {
                    distance: 0,
                    filter: DOT + styles.taskDragHandle,
                    holdToDrag: false
                });
                this._percentDraggable.bind('dragstart', function (e) {
                    if (that.trigger('percentResizeStart')) {
                        e.preventDefault();
                        return;
                    }
                    taskElement = e.currentTarget.siblings(DOT + styles.task);
                    task = that._taskByUid(taskElement.attr('data-uid'));
                    currentPercentComplete = task.percentComplete;
                    taskElementOffset = taskElement.offset();
                    timelineOffset = this.element.offset();
                    originalPercentWidth = taskElement.find(DOT + styles.taskComplete).width();
                    maxPercentWidth = taskElement.outerWidth();
                    clearTimeout(that._tooltipTimeout);
                    that.dragInProgress = true;
                }).bind('drag', kendo.throttle(function (e) {
                    if (!that.dragInProgress) {
                        return;
                    }
                    delta = isRtl ? -e.x.initialDelta : e.x.initialDelta;
                    var currentWidth = Math.max(0, Math.min(maxPercentWidth, originalPercentWidth + delta));
                    currentPercentComplete = Math.round(currentWidth / maxPercentWidth * 100);
                    updateElement(currentWidth);
                    tooltipTop = taskElementOffset.top - timelineOffset.top;
                    tooltipLeft = taskElementOffset.left + currentWidth - timelineOffset.left;
                    if (isRtl) {
                        tooltipLeft += maxPercentWidth - 2 * currentWidth;
                    }
                    that.view()._updatePercentCompleteTooltip(tooltipTop, tooltipLeft, currentPercentComplete);
                }, 15)).bind('dragend', function () {
                    that.trigger('percentResizeEnd', {
                        task: task,
                        percentComplete: currentPercentComplete / 100
                    });
                    cleanUp();
                }).bind('dragcancel', function () {
                    updateElement(originalPercentWidth);
                    cleanUp();
                }).userEvents.bind('select', function () {
                    blurActiveElement();
                });
            },
            _createDependencyDraggable: function () {
                var that = this;
                var originalHandle;
                var hoveredHandle = $();
                var hoveredTask = $();
                var startX;
                var startY;
                var useVML = browser.msie && browser.version < 9;
                var styles = GanttTimeline.styles;
                var cleanUp = function () {
                    originalHandle.css('display', '').removeClass(styles.hovered);
                    originalHandle.parent().removeClass(styles.origin);
                    originalHandle = null;
                    toggleHandles(false);
                    hoveredTask = $();
                    hoveredHandle = $();
                    that.view()._removeDependencyDragHint();
                    that.dragInProgress = false;
                };
                var toggleHandles = function (value) {
                    if (!hoveredTask.hasClass(styles.origin)) {
                        hoveredTask.find(DOT + styles.taskDot).css('display', value ? 'block' : '');
                        hoveredHandle.toggleClass(styles.hovered, value);
                    }
                };
                if (!this.options.editable) {
                    return;
                }
                if (useVML && document.namespaces) {
                    document.namespaces.add('kvml', 'urn:schemas-microsoft-com:vml', '#default#VML');
                }
                this._dependencyDraggable = new kendo.ui.Draggable(this.wrapper, {
                    distance: 0,
                    filter: DOT + styles.taskDot,
                    holdToDrag: false
                });
                this._dependencyDraggable.bind('dragstart', function (e) {
                    if (that.trigger('dependencyDragStart')) {
                        e.preventDefault();
                        return;
                    }
                    originalHandle = e.currentTarget.css('display', 'block').addClass(styles.hovered);
                    originalHandle.parent().addClass(styles.origin);
                    var elementOffset = originalHandle.offset();
                    var tablesOffset = that.wrapper.find(DOT + styles.tasksWrapper).offset();
                    startX = Math.round(elementOffset.left - tablesOffset.left + originalHandle.outerHeight() / 2);
                    startY = Math.round(elementOffset.top - tablesOffset.top + originalHandle.outerWidth() / 2);
                    clearTimeout(that._tooltipTimeout);
                    that.dragInProgress = true;
                }).bind('drag', kendo.throttle(function (e) {
                    if (!that.dragInProgress) {
                        return;
                    }
                    that.view()._removeDependencyDragHint();
                    var target = $(kendo.elementUnderCursor(e));
                    var tablesOffset = that.wrapper.find(DOT + styles.tasksWrapper).offset();
                    var currentX = e.x.location - tablesOffset.left;
                    var currentY = e.y.location - tablesOffset.top;
                    that.view()._updateDependencyDragHint({
                        x: startX,
                        y: startY
                    }, {
                        x: currentX,
                        y: currentY
                    }, useVML);
                    toggleHandles(false);
                    hoveredHandle = target.hasClass(styles.taskDot) ? target : $();
                    hoveredTask = target.closest(DOT + styles.taskWrap);
                    toggleHandles(true);
                }, 15)).bind('dragend', function () {
                    if (hoveredHandle.length) {
                        var fromStart = originalHandle.hasClass(styles.taskDotStart);
                        var toStart = hoveredHandle.hasClass(styles.taskDotStart);
                        var type = fromStart ? toStart ? 3 : 2 : toStart ? 1 : 0;
                        var predecessor = that._taskByUid(originalHandle.siblings(DOT + styles.task).attr('data-uid'));
                        var successor = that._taskByUid(hoveredHandle.siblings(DOT + styles.task).attr('data-uid'));
                        if (predecessor !== successor) {
                            that.trigger('dependencyDragEnd', {
                                type: type,
                                predecessor: predecessor,
                                successor: successor
                            });
                        }
                    }
                    cleanUp();
                }).bind('dragcancel', function () {
                    cleanUp();
                }).userEvents.bind('select', function () {
                    blurActiveElement();
                });
            },
            _selectable: function () {
                var that = this;
                var styles = GanttTimeline.styles;
                if (this.options.selectable) {
                    this.wrapper.on(CLICK + NS, DOT + styles.task, function (e) {
                        e.stopPropagation();
                        if (!e.ctrlKey) {
                            that.trigger('select', { uid: $(this).attr('data-uid') });
                        } else {
                            that.trigger('clear');
                        }
                    }).on(CLICK + NS, DOT + styles.taskWrap, function (e) {
                        e.stopPropagation();
                        $(this).css('z-index', '0');
                        var target = $(document.elementFromPoint(e.clientX, e.clientY));
                        if (target.hasClass(styles.line)) {
                            target.click();
                        }
                        $(this).css('z-index', '');
                    }).on(CLICK + NS, DOT + styles.tasksWrapper, function () {
                        if (that.selectDependency().length > 0) {
                            that.clearSelection();
                        } else {
                            that.trigger('clear');
                        }
                    }).on(CLICK + NS, DOT + styles.line, function (e) {
                        e.stopPropagation();
                        that.selectDependency(this);
                    });
                }
            },
            select: function (value) {
                var element = this.wrapper.find(value);
                var styles = GanttTimeline.styles;
                if (element.length) {
                    this.clearSelection();
                    element.addClass(styles.selected);
                    if (kendo.support.mobileOS) {
                        element.parent().addClass(styles.taskWrapActive);
                    }
                    return;
                }
                return this.wrapper.find(DOT + styles.task + DOT + styles.selected);
            },
            selectDependency: function (value) {
                var element = this.wrapper.find(value);
                var uid;
                var styles = GanttTimeline.styles;
                if (element.length) {
                    this.clearSelection();
                    this.trigger('clear');
                    uid = $(element).attr('data-uid');
                    this.wrapper.find(DOT + styles.line + '[data-uid=\'' + uid + '\']').addClass(styles.selected);
                    return;
                }
                return this.wrapper.find(DOT + styles.line + DOT + styles.selected);
            },
            clearSelection: function () {
                var styles = GanttTimeline.styles;
                this.wrapper.find(DOT + styles.selected).removeClass(styles.selected);
                if (kendo.support.mobileOS) {
                    this.wrapper.find(DOT + styles.taskWrapActive).removeClass(styles.taskWrapActive);
                }
            },
            _attachEvents: function () {
                var that = this;
                var styles = GanttTimeline.styles;
                if (this.options.editable) {
                    this._tabindex();
                    this.wrapper.on(CLICK + NS, DOT + styles.taskDelete, function (e) {
                        that.trigger('removeTask', { uid: $(this).closest(DOT + styles.task).attr('data-uid') });
                        e.stopPropagation();
                        e.preventDefault();
                    }).on(KEYDOWN + NS, function (e) {
                        var selectedDependency;
                        if (e.keyCode === keys.DELETE) {
                            selectedDependency = that.selectDependency();
                            if (selectedDependency.length) {
                                that.trigger('removeDependency', { uid: selectedDependency.attr('data-uid') });
                                that.clearSelection();
                            }
                        }
                    });
                    if (!kendo.support.mobileOS) {
                        this.wrapper.on(DBLCLICK + NS, DOT + styles.task, function (e) {
                            that.trigger('editTask', { uid: $(this).attr('data-uid') });
                            e.stopPropagation();
                            e.preventDefault();
                        });
                    } else {
                        this.touch = this.wrapper.kendoTouch({
                            filter: DOT + styles.task,
                            doubletap: function (e) {
                                that.trigger('editTask', { uid: $(e.touch.currentTarget).attr('data-uid') });
                            }
                        }).data('kendoTouch');
                    }
                }
            },
            _tooltip: function () {
                var that = this;
                var tooltipOptions = this.options.tooltip;
                var styles = GanttTimeline.styles;
                var currentMousePosition;
                var mouseMoveHandler = function (e) {
                    currentMousePosition = e.clientX;
                };
                if (tooltipOptions && tooltipOptions.visible === false) {
                    return;
                }
                if (!kendo.support.mobileOS) {
                    this.wrapper.on(MOUSEENTER + NS, DOT + styles.task, function () {
                        var element = this;
                        var task = that._taskByUid($(this).attr('data-uid'));
                        if (that.dragInProgress) {
                            return;
                        }
                        that._tooltipTimeout = setTimeout(function () {
                            that.view()._createTaskTooltip(task, element, currentMousePosition);
                        }, 800);
                        $(this).on(MOUSEMOVE, mouseMoveHandler);
                    }).on(MOUSELEAVE + NS, DOT + styles.task, function () {
                        clearTimeout(that._tooltipTimeout);
                        that.view()._removeTaskTooltip();
                        $(this).off(MOUSEMOVE, mouseMoveHandler);
                    });
                } else {
                    this.wrapper.on(CLICK + NS, DOT + styles.taskDelete, function (e) {
                        e.stopPropagation();
                        that.view()._removeTaskTooltip();
                    }).on(MOUSELEAVE + NS, DOT + styles.task, function (e) {
                        var parents = $(e.relatedTarget).parents(DOT + styles.taskWrap, DOT + styles.task);
                        if (parents.length === 0) {
                            that.view()._removeTaskTooltip();
                        }
                    });
                    if (this.touch) {
                        this.touch.bind('tap', function (e) {
                            var element = e.touch.target;
                            var task = that._taskByUid($(element).attr('data-uid'));
                            var currentPosition = e.touch.x.client;
                            if (that.view()._taskTooltip) {
                                that.view()._removeTaskTooltip();
                            }
                            that.view()._createTaskTooltip(task, element, currentPosition);
                        }).bind('doubletap', function () {
                            that.view()._removeTaskTooltip();
                        });
                    }
                }
            }
        });
        extend(true, GanttTimeline, { styles: timelineStyles });
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
/** 
 * Kendo UI v2016.2.714 (http://www.telerik.com/kendo-ui)                                                                                                                                               
 * Copyright 2016 Telerik AD. All rights reserved.                                                                                                                                                      
 *                                                                                                                                                                                                      
 * Kendo UI commercial licenses may be obtained at                                                                                                                                                      
 * http://www.telerik.com/purchase/license-agreement/kendo-ui-complete                                                                                                                                  
 * If you do not own a commercial license, this file shall be governed by the trial license terms.                                                                                                      
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       

*/
(function (f, define) {
    define('util/main', ['kendo.core'], f);
}(function () {
    (function () {
        var math = Math, kendo = window.kendo, deepExtend = kendo.deepExtend;
        var DEG_TO_RAD = math.PI / 180, MAX_NUM = Number.MAX_VALUE, MIN_NUM = -Number.MAX_VALUE, UNDEFINED = 'undefined';
        function defined(value) {
            return typeof value !== UNDEFINED;
        }
        function round(value, precision) {
            var power = pow(precision);
            return math.round(value * power) / power;
        }
        function pow(p) {
            if (p) {
                return math.pow(10, p);
            } else {
                return 1;
            }
        }
        function limitValue(value, min, max) {
            return math.max(math.min(value, max), min);
        }
        function rad(degrees) {
            return degrees * DEG_TO_RAD;
        }
        function deg(radians) {
            return radians / DEG_TO_RAD;
        }
        function isNumber(val) {
            return typeof val === 'number' && !isNaN(val);
        }
        function valueOrDefault(value, defaultValue) {
            return defined(value) ? value : defaultValue;
        }
        function sqr(value) {
            return value * value;
        }
        function objectKey(object) {
            var parts = [];
            for (var key in object) {
                parts.push(key + object[key]);
            }
            return parts.sort().join('');
        }
        function hashKey(str) {
            var hash = 2166136261;
            for (var i = 0; i < str.length; ++i) {
                hash += (hash << 1) + (hash << 4) + (hash << 7) + (hash << 8) + (hash << 24);
                hash ^= str.charCodeAt(i);
            }
            return hash >>> 0;
        }
        function hashObject(object) {
            return hashKey(objectKey(object));
        }
        var now = Date.now;
        if (!now) {
            now = function () {
                return new Date().getTime();
            };
        }
        function arrayLimits(arr) {
            var length = arr.length, i, min = MAX_NUM, max = MIN_NUM;
            for (i = 0; i < length; i++) {
                max = math.max(max, arr[i]);
                min = math.min(min, arr[i]);
            }
            return {
                min: min,
                max: max
            };
        }
        function arrayMin(arr) {
            return arrayLimits(arr).min;
        }
        function arrayMax(arr) {
            return arrayLimits(arr).max;
        }
        function sparseArrayMin(arr) {
            return sparseArrayLimits(arr).min;
        }
        function sparseArrayMax(arr) {
            return sparseArrayLimits(arr).max;
        }
        function sparseArrayLimits(arr) {
            var min = MAX_NUM, max = MIN_NUM;
            for (var i = 0, length = arr.length; i < length; i++) {
                var n = arr[i];
                if (n !== null && isFinite(n)) {
                    min = math.min(min, n);
                    max = math.max(max, n);
                }
            }
            return {
                min: min === MAX_NUM ? undefined : min,
                max: max === MIN_NUM ? undefined : max
            };
        }
        function last(array) {
            if (array) {
                return array[array.length - 1];
            }
        }
        function append(first, second) {
            first.push.apply(first, second);
            return first;
        }
        function renderTemplate(text) {
            return kendo.template(text, {
                useWithBlock: false,
                paramName: 'd'
            });
        }
        function renderAttr(name, value) {
            return defined(value) && value !== null ? ' ' + name + '=\'' + value + '\' ' : '';
        }
        function renderAllAttr(attrs) {
            var output = '';
            for (var i = 0; i < attrs.length; i++) {
                output += renderAttr(attrs[i][0], attrs[i][1]);
            }
            return output;
        }
        function renderStyle(attrs) {
            var output = '';
            for (var i = 0; i < attrs.length; i++) {
                var value = attrs[i][1];
                if (defined(value)) {
                    output += attrs[i][0] + ':' + value + ';';
                }
            }
            if (output !== '') {
                return output;
            }
        }
        function renderSize(size) {
            if (typeof size !== 'string') {
                size += 'px';
            }
            return size;
        }
        function renderPos(pos) {
            var result = [];
            if (pos) {
                var parts = kendo.toHyphens(pos).split('-');
                for (var i = 0; i < parts.length; i++) {
                    result.push('k-pos-' + parts[i]);
                }
            }
            return result.join(' ');
        }
        function isTransparent(color) {
            return color === '' || color === null || color === 'none' || color === 'transparent' || !defined(color);
        }
        function arabicToRoman(n) {
            var literals = {
                1: 'i',
                10: 'x',
                100: 'c',
                2: 'ii',
                20: 'xx',
                200: 'cc',
                3: 'iii',
                30: 'xxx',
                300: 'ccc',
                4: 'iv',
                40: 'xl',
                400: 'cd',
                5: 'v',
                50: 'l',
                500: 'd',
                6: 'vi',
                60: 'lx',
                600: 'dc',
                7: 'vii',
                70: 'lxx',
                700: 'dcc',
                8: 'viii',
                80: 'lxxx',
                800: 'dccc',
                9: 'ix',
                90: 'xc',
                900: 'cm',
                1000: 'm'
            };
            var values = [
                1000,
                900,
                800,
                700,
                600,
                500,
                400,
                300,
                200,
                100,
                90,
                80,
                70,
                60,
                50,
                40,
                30,
                20,
                10,
                9,
                8,
                7,
                6,
                5,
                4,
                3,
                2,
                1
            ];
            var roman = '';
            while (n > 0) {
                if (n < values[0]) {
                    values.shift();
                } else {
                    roman += literals[values[0]];
                    n -= values[0];
                }
            }
            return roman;
        }
        function romanToArabic(r) {
            r = r.toLowerCase();
            var digits = {
                i: 1,
                v: 5,
                x: 10,
                l: 50,
                c: 100,
                d: 500,
                m: 1000
            };
            var value = 0, prev = 0;
            for (var i = 0; i < r.length; ++i) {
                var v = digits[r.charAt(i)];
                if (!v) {
                    return null;
                }
                value += v;
                if (v > prev) {
                    value -= 2 * prev;
                }
                prev = v;
            }
            return value;
        }
        function memoize(f) {
            var cache = Object.create(null);
            return function () {
                var id = '';
                for (var i = arguments.length; --i >= 0;) {
                    id += ':' + arguments[i];
                }
                if (id in cache) {
                    return cache[id];
                }
                return f.apply(this, arguments);
            };
        }
        function ucs2decode(string) {
            var output = [], counter = 0, length = string.length, value, extra;
            while (counter < length) {
                value = string.charCodeAt(counter++);
                if (value >= 55296 && value <= 56319 && counter < length) {
                    extra = string.charCodeAt(counter++);
                    if ((extra & 64512) == 56320) {
                        output.push(((value & 1023) << 10) + (extra & 1023) + 65536);
                    } else {
                        output.push(value);
                        counter--;
                    }
                } else {
                    output.push(value);
                }
            }
            return output;
        }
        function ucs2encode(array) {
            return array.map(function (value) {
                var output = '';
                if (value > 65535) {
                    value -= 65536;
                    output += String.fromCharCode(value >>> 10 & 1023 | 55296);
                    value = 56320 | value & 1023;
                }
                output += String.fromCharCode(value);
                return output;
            }).join('');
        }
        deepExtend(kendo, {
            util: {
                MAX_NUM: MAX_NUM,
                MIN_NUM: MIN_NUM,
                append: append,
                arrayLimits: arrayLimits,
                arrayMin: arrayMin,
                arrayMax: arrayMax,
                defined: defined,
                deg: deg,
                hashKey: hashKey,
                hashObject: hashObject,
                isNumber: isNumber,
                isTransparent: isTransparent,
                last: last,
                limitValue: limitValue,
                now: now,
                objectKey: objectKey,
                round: round,
                rad: rad,
                renderAttr: renderAttr,
                renderAllAttr: renderAllAttr,
                renderPos: renderPos,
                renderSize: renderSize,
                renderStyle: renderStyle,
                renderTemplate: renderTemplate,
                sparseArrayLimits: sparseArrayLimits,
                sparseArrayMin: sparseArrayMin,
                sparseArrayMax: sparseArrayMax,
                sqr: sqr,
                valueOrDefault: valueOrDefault,
                romanToArabic: romanToArabic,
                arabicToRoman: arabicToRoman,
                memoize: memoize,
                ucs2encode: ucs2encode,
                ucs2decode: ucs2decode
            }
        });
        kendo.drawing.util = kendo.util;
        kendo.dataviz.util = kendo.util;
    }());
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('util/text-metrics', [
        'kendo.core',
        'util/main'
    ], f);
}(function () {
    (function ($) {
        var doc = document, kendo = window.kendo, Class = kendo.Class, util = kendo.util, defined = util.defined;
        var LRUCache = Class.extend({
            init: function (size) {
                this._size = size;
                this._length = 0;
                this._map = {};
            },
            put: function (key, value) {
                var lru = this, map = lru._map, entry = {
                        key: key,
                        value: value
                    };
                map[key] = entry;
                if (!lru._head) {
                    lru._head = lru._tail = entry;
                } else {
                    lru._tail.newer = entry;
                    entry.older = lru._tail;
                    lru._tail = entry;
                }
                if (lru._length >= lru._size) {
                    map[lru._head.key] = null;
                    lru._head = lru._head.newer;
                    lru._head.older = null;
                } else {
                    lru._length++;
                }
            },
            get: function (key) {
                var lru = this, entry = lru._map[key];
                if (entry) {
                    if (entry === lru._head && entry !== lru._tail) {
                        lru._head = entry.newer;
                        lru._head.older = null;
                    }
                    if (entry !== lru._tail) {
                        if (entry.older) {
                            entry.older.newer = entry.newer;
                            entry.newer.older = entry.older;
                        }
                        entry.older = lru._tail;
                        entry.newer = null;
                        lru._tail.newer = entry;
                        lru._tail = entry;
                    }
                    return entry.value;
                }
            }
        });
        var defaultMeasureBox = $('<div style=\'position: absolute !important; top: -4000px !important; width: auto !important; height: auto !important;' + 'padding: 0 !important; margin: 0 !important; border: 0 !important;' + 'line-height: normal !important; visibility: hidden !important; white-space: nowrap!important;\' />')[0];
        function zeroSize() {
            return {
                width: 0,
                height: 0,
                baseline: 0
            };
        }
        var TextMetrics = Class.extend({
            init: function (options) {
                this._cache = new LRUCache(1000);
                this._initOptions(options);
            },
            options: { baselineMarkerSize: 1 },
            measure: function (text, style, box) {
                if (!text) {
                    return zeroSize();
                }
                var styleKey = util.objectKey(style), cacheKey = util.hashKey(text + styleKey), cachedResult = this._cache.get(cacheKey);
                if (cachedResult) {
                    return cachedResult;
                }
                var size = zeroSize();
                var measureBox = box ? box : defaultMeasureBox;
                var baselineMarker = this._baselineMarker().cloneNode(false);
                for (var key in style) {
                    var value = style[key];
                    if (defined(value)) {
                        measureBox.style[key] = value;
                    }
                }
                $(measureBox).text(text);
                measureBox.appendChild(baselineMarker);
                doc.body.appendChild(measureBox);
                if ((text + '').length) {
                    size.width = measureBox.offsetWidth - this.options.baselineMarkerSize;
                    size.height = measureBox.offsetHeight;
                    size.baseline = baselineMarker.offsetTop + this.options.baselineMarkerSize;
                }
                if (size.width > 0 && size.height > 0) {
                    this._cache.put(cacheKey, size);
                }
                measureBox.parentNode.removeChild(measureBox);
                return size;
            },
            _baselineMarker: function () {
                return $('<div class=\'k-baseline-marker\' ' + 'style=\'display: inline-block; vertical-align: baseline;' + 'width: ' + this.options.baselineMarkerSize + 'px; height: ' + this.options.baselineMarkerSize + 'px;' + 'overflow: hidden;\' />')[0];
            }
        });
        TextMetrics.current = new TextMetrics();
        function measureText(text, style, measureBox) {
            return TextMetrics.current.measure(text, style, measureBox);
        }
        function loadFonts(fonts, callback) {
            var promises = [];
            if (fonts.length > 0 && document.fonts) {
                try {
                    promises = fonts.map(function (font) {
                        return document.fonts.load(font);
                    });
                } catch (e) {
                    kendo.logToConsole(e);
                }
                Promise.all(promises).then(callback, callback);
            } else {
                callback();
            }
        }
        kendo.util.TextMetrics = TextMetrics;
        kendo.util.LRUCache = LRUCache;
        kendo.util.loadFonts = loadFonts;
        kendo.util.measureText = measureText;
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('util/base64', ['util/main'], f);
}(function () {
    (function () {
        var kendo = window.kendo, deepExtend = kendo.deepExtend, fromCharCode = String.fromCharCode;
        var KEY_STR = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
        function encodeBase64(input) {
            var output = '';
            var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
            var i = 0;
            input = encodeUTF8(input);
            while (i < input.length) {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
                enc1 = chr1 >> 2;
                enc2 = (chr1 & 3) << 4 | chr2 >> 4;
                enc3 = (chr2 & 15) << 2 | chr3 >> 6;
                enc4 = chr3 & 63;
                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }
                output = output + KEY_STR.charAt(enc1) + KEY_STR.charAt(enc2) + KEY_STR.charAt(enc3) + KEY_STR.charAt(enc4);
            }
            return output;
        }
        function encodeUTF8(input) {
            var output = '';
            for (var i = 0; i < input.length; i++) {
                var c = input.charCodeAt(i);
                if (c < 128) {
                    output += fromCharCode(c);
                } else if (c < 2048) {
                    output += fromCharCode(192 | c >>> 6);
                    output += fromCharCode(128 | c & 63);
                } else if (c < 65536) {
                    output += fromCharCode(224 | c >>> 12);
                    output += fromCharCode(128 | c >>> 6 & 63);
                    output += fromCharCode(128 | c & 63);
                }
            }
            return output;
        }
        deepExtend(kendo.util, {
            encodeBase64: encodeBase64,
            encodeUTF8: encodeUTF8
        });
    }());
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('mixins/observers', ['kendo.core'], f);
}(function () {
    (function ($) {
        var math = Math, kendo = window.kendo, deepExtend = kendo.deepExtend, inArray = $.inArray;
        var ObserversMixin = {
            observers: function () {
                this._observers = this._observers || [];
                return this._observers;
            },
            addObserver: function (element) {
                if (!this._observers) {
                    this._observers = [element];
                } else {
                    this._observers.push(element);
                }
                return this;
            },
            removeObserver: function (element) {
                var observers = this.observers();
                var index = inArray(element, observers);
                if (index != -1) {
                    observers.splice(index, 1);
                }
                return this;
            },
            trigger: function (methodName, event) {
                var observers = this._observers;
                var observer;
                var idx;
                if (observers && !this._suspended) {
                    for (idx = 0; idx < observers.length; idx++) {
                        observer = observers[idx];
                        if (observer[methodName]) {
                            observer[methodName](event);
                        }
                    }
                }
                return this;
            },
            optionsChange: function (e) {
                e = e || {};
                e.element = this;
                this.trigger('optionsChange', e);
            },
            geometryChange: function () {
                this.trigger('geometryChange', { element: this });
            },
            suspend: function () {
                this._suspended = (this._suspended || 0) + 1;
                return this;
            },
            resume: function () {
                this._suspended = math.max((this._suspended || 0) - 1, 0);
                return this;
            },
            _observerField: function (field, value) {
                if (this[field]) {
                    this[field].removeObserver(this);
                }
                this[field] = value;
                value.addObserver(this);
            }
        };
        deepExtend(kendo, { mixins: { ObserversMixin: ObserversMixin } });
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.gantt', [
        'kendo.data',
        'kendo.popup',
        'kendo.window',
        'kendo.resizable',
        'kendo.gantt.list',
        'kendo.gantt.timeline',
        'kendo.grid',
        'kendo.pdf'
    ], f);
}(function () {
    var __meta__ = {
        id: 'gantt',
        name: 'Gantt',
        category: 'web',
        description: 'The Gantt component.',
        depends: [
            'data',
            'popup',
            'resizable',
            'window',
            'gantt.list',
            'gantt.timeline',
            'grid'
        ]
    };
    (function ($, undefined) {
        var kendo = window.kendo;
        var supportsMedia = 'matchMedia' in window;
        var browser = kendo.support.browser;
        var mobileOS = kendo.support.mobileOS;
        var Observable = kendo.Observable;
        var Widget = kendo.ui.Widget;
        var DataSource = kendo.data.DataSource;
        var ObservableObject = kendo.data.ObservableObject;
        var ObservableArray = kendo.data.ObservableArray;
        var Query = kendo.data.Query;
        var isArray = $.isArray;
        var inArray = $.inArray;
        var isFunction = kendo.isFunction;
        var proxy = $.proxy;
        var extend = $.extend;
        var isPlainObject = $.isPlainObject;
        var map = $.map;
        var keys = kendo.keys;
        var defaultIndicatorWidth = 3;
        var NS = '.kendoGantt';
        var PERCENTAGE_FORMAT = 'p0';
        var TABINDEX = 'tabIndex';
        var CLICK = 'click';
        var WIDTH = 'width';
        var STRING = 'string';
        var DIRECTIONS = {
            'down': {
                origin: 'bottom left',
                position: 'top left'
            },
            'up': {
                origin: 'top left',
                position: 'bottom left'
            }
        };
        var ARIA_DESCENDANT = 'aria-activedescendant';
        var ACTIVE_CELL = 'gantt_active_cell';
        var ACTIVE_OPTION = 'action-option-focused';
        var DOT = '.';
        var TASK_DELETE_CONFIRM = 'Are you sure you want to delete this task?';
        var DEPENDENCY_DELETE_CONFIRM = 'Are you sure you want to delete this dependency?';
        var TOGGLE_BUTTON_TEMPLATE = kendo.template('<button class="#=styles.buttonToggle#"><span class="#=styles.iconToggle#">&nbps;</span></button>');
        var BUTTON_TEMPLATE = '<button class="#=styles.button# #=className#" ' + '#if (action) {#' + 'data-action="#=action#"' + '#}#' + '><span class="#=iconClass#"></span><span>#=text#</span></button>';
        var COMMAND_BUTTON_TEMPLATE = '<a class="#=className#" #=attr# href="\\#">#=text#</a>';
        var VIEWBUTTONTEMPLATE = kendo.template('<li class="#=styles.currentView# #=styles.viewButtonDefault#"><a href="\\#" class="#=styles.link#">&nbps;</a></li>');
        var HEADER_VIEWS_TEMPLATE = kendo.template('<ul class="#=styles.viewsWrapper#">' + '#for(var view in views){#' + '<li class="#=styles.viewButtonDefault# #=styles.viewButton#-#= view.toLowerCase() #" data-#=ns#name="#=view#"><a href="\\#" class="#=styles.link#">#=views[view].title#</a></li>' + '#}#' + '</ul>');
        var TASK_DROPDOWN_TEMPLATE = kendo.template('<div class="#=styles.popupWrapper#">' + '<ul class="#=styles.popupList#" role="listbox">' + '#for(var i = 0, l = actions.length; i < l; i++){#' + '<li class="#=styles.item#" data-action="#=actions[i].data#" role="option">#=actions[i].text#</span>' + '#}#' + '</ul>' + '</div>');
        var DATERANGEEDITOR = function (container, options) {
            var attr = { name: options.field };
            var validationRules = options.model.fields[options.field].validation;
            if (validationRules && isPlainObject(validationRules) && validationRules.message) {
                attr[kendo.attr('dateCompare-msg')] = validationRules.message;
            }
            $('<input type="text" required ' + kendo.attr('type') + '="date" ' + kendo.attr('role') + '="datetimepicker" ' + kendo.attr('bind') + '="value:' + options.field + '" ' + kendo.attr('validate') + '=\'true\' />').attr(attr).appendTo(container);
            $('<span ' + kendo.attr('for') + '="' + options.field + '" class="k-invalid-msg"/>').hide().appendTo(container);
        };
        var RESOURCESEDITOR = function (container, options) {
            $('<a href="#" class="' + options.styles.button + '">' + options.messages.assignButton + '</a>').click(options.click).appendTo(container);
        };
        var ganttStyles = {
            wrapper: 'k-widget k-gantt',
            rowHeight: 'k-gantt-rowheight',
            listWrapper: 'k-gantt-layout k-gantt-treelist',
            list: 'k-gantt-treelist',
            timelineWrapper: 'k-gantt-layout k-gantt-timeline',
            timeline: 'k-gantt-timeline',
            splitBarWrapper: 'k-splitbar k-state-default k-splitbar-horizontal k-splitbar-draggable-horizontal k-gantt-layout',
            splitBar: 'k-splitbar',
            splitBarHover: 'k-splitbar-horizontal-hover',
            popupWrapper: 'k-list-container',
            popupList: 'k-list k-reset',
            resizeHandle: 'k-resize-handle',
            icon: 'k-icon',
            item: 'k-item',
            line: 'k-line',
            buttonDelete: 'k-gantt-delete',
            buttonCancel: 'k-gantt-cancel',
            buttonSave: 'k-gantt-update',
            buttonToggle: 'k-gantt-toggle',
            primary: 'k-primary',
            hovered: 'k-state-hover',
            selected: 'k-state-selected',
            focused: 'k-state-focused',
            gridHeader: 'k-grid-header',
            gridHeaderWrap: 'k-grid-header-wrap',
            gridContent: 'k-grid-content',
            popup: {
                form: 'k-popup-edit-form',
                editForm: 'k-gantt-edit-form',
                formContainer: 'k-edit-form-container',
                resourcesFormContainer: 'k-resources-form-container',
                message: 'k-popup-message',
                buttonsContainer: 'k-edit-buttons k-state-default',
                button: 'k-button',
                editField: 'k-edit-field',
                editLabel: 'k-edit-label',
                resourcesField: 'k-gantt-resources'
            },
            toolbar: {
                headerWrapper: 'k-floatwrap k-header k-gantt-toolbar',
                footerWrapper: 'k-floatwrap k-header k-gantt-toolbar',
                toolbar: 'k-gantt-toolbar',
                expanded: 'k-state-expanded',
                views: 'k-gantt-views',
                viewsWrapper: 'k-reset k-header k-gantt-views',
                actions: 'k-gantt-actions',
                button: 'k-button k-button-icontext',
                buttonToggle: 'k-button k-button-icon k-gantt-toggle',
                iconPlus: 'k-icon k-i-plus',
                iconPdf: 'k-icon k-i-pdf',
                iconToggle: 'k-icon k-i-gantt-toggle',
                viewButtonDefault: 'k-state-default',
                viewButton: 'k-view',
                currentView: 'k-current-view',
                link: 'k-link',
                pdfButton: 'k-gantt-pdf',
                appendButton: 'k-gantt-create'
            }
        };
        function selector(uid) {
            return '[' + kendo.attr('uid') + (uid ? '=\'' + uid + '\']' : ']');
        }
        function trimOptions(options) {
            delete options.name;
            delete options.prefix;
            delete options.remove;
            delete options.edit;
            delete options.add;
            delete options.navigate;
            return options;
        }
        function dateCompareValidator(input) {
            if (input.filter('[name=end], [name=start]').length) {
                var field = input.attr('name');
                var picker = kendo.widgetInstance(input, kendo.ui);
                var dates = {};
                var container = input;
                var editable;
                var model;
                while (container !== window && !editable) {
                    container = container.parent();
                    editable = container.data('kendoEditable');
                }
                model = editable ? editable.options.model : null;
                if (!model) {
                    return true;
                }
                dates.start = model.start;
                dates.end = model.end;
                dates[field] = picker ? picker.value() : kendo.parseDate(input.value());
                return dates.start <= dates.end;
            }
            return true;
        }
        function focusTable(table, direct) {
            var wrapper = table.parents('[' + kendo.attr('role') + '="gantt"]');
            var scrollPositions = [];
            var parents = scrollableParents(wrapper);
            table.attr(TABINDEX, 0);
            if (direct) {
                parents.each(function (index, parent) {
                    scrollPositions[index] = $(parent).scrollTop();
                });
            }
            try {
                table[0].setActive();
            } catch (e) {
                table[0].focus();
            }
            if (direct) {
                parents.each(function (index, parent) {
                    $(parent).scrollTop(scrollPositions[index]);
                });
            }
        }
        function scrollableParents(element) {
            return $(element).parentsUntil('body').filter(function (index, element) {
                var computedStyle = kendo.getComputedStyles(element, ['overflow']);
                return computedStyle.overflow != 'visible';
            }).add(window);
        }
        var defaultCommands;
        var TaskDropDown = Observable.extend({
            init: function (element, options) {
                Observable.fn.init.call(this);
                this.element = element;
                this.options = extend(true, {}, this.options, options);
                this._popup();
            },
            options: {
                direction: 'down',
                navigatable: false
            },
            _current: function (method) {
                var ganttStyles = Gantt.styles;
                var current = this.list.find(DOT + ganttStyles.focused);
                var sibling = current[method]();
                if (sibling.length) {
                    current.removeClass(ganttStyles.focused).removeAttr('id');
                    sibling.addClass(ganttStyles.focused).attr('id', ACTIVE_OPTION);
                    this.list.find('ul').removeAttr(ARIA_DESCENDANT).attr(ARIA_DESCENDANT, ACTIVE_OPTION);
                }
            },
            _popup: function () {
                var that = this;
                var ganttStyles = Gantt.styles;
                var itemSelector = 'li' + DOT + ganttStyles.item;
                var appendButtonSelector = DOT + ganttStyles.toolbar.appendButton;
                var actions = this.options.messages.actions;
                var navigatable = this.options.navigatable;
                this.list = $(TASK_DROPDOWN_TEMPLATE({
                    styles: ganttStyles,
                    actions: [
                        {
                            data: 'add',
                            text: actions.addChild
                        },
                        {
                            data: 'insert-before',
                            text: actions.insertBefore
                        },
                        {
                            data: 'insert-after',
                            text: actions.insertAfter
                        }
                    ]
                }));
                this.element.append(this.list);
                this.popup = new kendo.ui.Popup(this.list, extend({
                    anchor: this.element.find(appendButtonSelector),
                    open: function () {
                        that._adjustListWidth();
                    },
                    animation: this.options.animation
                }, DIRECTIONS[this.options.direction]));
                this.element.on(CLICK + NS, appendButtonSelector, function (e) {
                    var target = $(this);
                    var action = target.attr(kendo.attr('action'));
                    e.preventDefault();
                    if (action) {
                        that.trigger('command', { type: action });
                    } else {
                        that.popup.open();
                        if (navigatable) {
                            that.list.find('li:first').addClass(ganttStyles.focused).attr('id', ACTIVE_OPTION).end().find('ul').attr({
                                TABINDEX: 0,
                                'aria-activedescendant': ACTIVE_OPTION
                            }).focus();
                        }
                    }
                });
                this.list.find(itemSelector).hover(function () {
                    $(this).addClass(ganttStyles.hovered);
                }, function () {
                    $(this).removeClass(ganttStyles.hovered);
                }).end().on(CLICK + NS, itemSelector, function () {
                    that.trigger('command', { type: $(this).attr(kendo.attr('action')) });
                    that.popup.close();
                });
                if (navigatable) {
                    this.popup.bind('close', function () {
                        that.list.find(itemSelector).removeClass(ganttStyles.focused).end().find('ul').attr(TABINDEX, 0);
                        that.element.parents('[' + kendo.attr('role') + '="gantt"]').find(DOT + ganttStyles.gridContent + ' > table:first').focus();
                    });
                    this.list.find('ul').on('keydown' + NS, function (e) {
                        var key = e.keyCode;
                        switch (key) {
                        case keys.UP:
                            e.preventDefault();
                            that._current('prev');
                            break;
                        case keys.DOWN:
                            e.preventDefault();
                            that._current('next');
                            break;
                        case keys.ENTER:
                            that.list.find(DOT + ganttStyles.focused).click();
                            break;
                        case keys.ESC:
                            e.preventDefault();
                            that.popup.close();
                            break;
                        }
                    });
                }
            },
            _adjustListWidth: function () {
                var list = this.list;
                var ganttStyles = Gantt.styles;
                var width = list[0].style.width;
                var wrapper = this.element.find(DOT + ganttStyles.toolbar.appendButton);
                var outerWidth = list.outerWidth();
                var computedStyle;
                var computedWidth;
                if (!list.data(WIDTH) && width) {
                    return;
                }
                computedStyle = window.getComputedStyle ? window.getComputedStyle(wrapper[0], null) : 0;
                computedWidth = computedStyle ? parseFloat(computedStyle.width) : wrapper.outerWidth();
                if (computedStyle && (browser.mozilla || browser.msie)) {
                    computedWidth += parseFloat(computedStyle.paddingLeft) + parseFloat(computedStyle.paddingRight) + parseFloat(computedStyle.borderLeftWidth) + parseFloat(computedStyle.borderRightWidth);
                }
                if (list.css('box-sizing') !== 'border-box') {
                    width = computedWidth - (list.outerWidth() - list.width());
                } else {
                    width = computedWidth;
                }
                if (outerWidth > width) {
                    width = outerWidth;
                }
                list.css({
                    fontFamily: wrapper.css('font-family'),
                    width: width
                }).data(WIDTH, width);
            },
            destroy: function () {
                clearTimeout(this._focusTimeout);
                this.popup.destroy();
                this.element.off(NS);
                this.list.off(NS);
                this.unbind();
            }
        });
        var createDataSource = function (type, name) {
            return function (options) {
                options = isArray(options) ? { data: options } : options;
                var dataSource = options || {};
                var data = dataSource.data;
                dataSource.data = data;
                if (!(dataSource instanceof type) && dataSource instanceof DataSource) {
                    throw new Error('Incorrect DataSource type. Only ' + name + ' instances are supported');
                }
                return dataSource instanceof type ? dataSource : new type(dataSource);
            };
        };
        var GanttDependency = kendo.data.Model.define({
            id: 'id',
            fields: {
                id: { type: 'number' },
                predecessorId: { type: 'number' },
                successorId: { type: 'number' },
                type: { type: 'number' }
            }
        });
        var GanttDependencyDataSource = DataSource.extend({
            init: function (options) {
                DataSource.fn.init.call(this, extend(true, {}, {
                    schema: {
                        modelBase: GanttDependency,
                        model: GanttDependency
                    }
                }, options));
            },
            successors: function (id) {
                return this._dependencies('predecessorId', id);
            },
            predecessors: function (id) {
                return this._dependencies('successorId', id);
            },
            dependencies: function (id) {
                var predecessors = this.predecessors(id);
                var successors = this.successors(id);
                predecessors.push.apply(predecessors, successors);
                return predecessors;
            },
            _dependencies: function (field, id) {
                var data = this.view();
                var filter = {
                    field: field,
                    operator: 'eq',
                    value: id
                };
                data = new Query(data).filter(filter).toArray();
                return data;
            }
        });
        GanttDependencyDataSource.create = createDataSource(GanttDependencyDataSource, 'GanttDependencyDataSource');
        var GanttTask = kendo.data.Model.define({
            duration: function () {
                var end = this.end;
                var start = this.start;
                return end - start;
            },
            isMilestone: function () {
                return this.duration() === 0;
            },
            _offset: function (value) {
                var field = [
                    'start',
                    'end'
                ];
                var newValue;
                for (var i = 0; i < field.length; i++) {
                    newValue = new Date(this.get(field[i]).getTime() + value);
                    this.set(field[i], newValue);
                }
            },
            id: 'id',
            fields: {
                id: { type: 'number' },
                parentId: {
                    type: 'number',
                    defaultValue: null,
                    validation: { required: true }
                },
                orderId: {
                    type: 'number',
                    validation: { required: true }
                },
                title: {
                    type: 'string',
                    defaultValue: 'New task'
                },
                start: {
                    type: 'date',
                    validation: { required: true }
                },
                end: {
                    type: 'date',
                    validation: {
                        required: true,
                        dateCompare: dateCompareValidator,
                        message: 'End date should be after or equal to the start date'
                    }
                },
                percentComplete: {
                    type: 'number',
                    validation: {
                        required: true,
                        min: 0,
                        max: 1,
                        step: 0.01
                    }
                },
                summary: { type: 'boolean' },
                expanded: {
                    type: 'boolean',
                    defaultValue: true
                }
            }
        });
        var GanttDataSource = DataSource.extend({
            init: function (options) {
                DataSource.fn.init.call(this, extend(true, {}, {
                    schema: {
                        modelBase: GanttTask,
                        model: GanttTask
                    }
                }, options));
            },
            remove: function (task) {
                var parentId = task.get('parentId');
                var children = this.taskAllChildren(task);
                this._removeItems(children);
                task = DataSource.fn.remove.call(this, task);
                this._childRemoved(parentId, task.get('orderId'));
                return task;
            },
            add: function (task) {
                if (!task) {
                    return;
                }
                task = this._toGanttTask(task);
                return this.insert(this.taskSiblings(task).length, task);
            },
            insert: function (index, task) {
                if (!task) {
                    return;
                }
                task = this._toGanttTask(task);
                task.set('orderId', index);
                task = DataSource.fn.insert.call(this, index, task);
                this._reorderSiblings(task, this.taskSiblings(task).length - 1);
                this._resolveSummaryFields(this.taskParent(task));
                return task;
            },
            taskChildren: function (task) {
                var data = this.view();
                var filter = {
                    field: 'parentId',
                    operator: 'eq',
                    value: null
                };
                var order = this._sort && this._sort.length ? this._sort : {
                    field: 'orderId',
                    dir: 'asc'
                };
                var taskId;
                if (!!task) {
                    taskId = task.get('id');
                    if (taskId === undefined || taskId === null || taskId === '') {
                        return [];
                    }
                    filter.value = taskId;
                }
                data = new Query(data).filter(filter).sort(order).toArray();
                return data;
            },
            taskAllChildren: function (task) {
                var data = [];
                var that = this;
                var callback = function (task) {
                    var tasks = that.taskChildren(task);
                    data.push.apply(data, tasks);
                    map(tasks, callback);
                };
                if (!!task) {
                    callback(task);
                } else {
                    data = this.view();
                }
                return data;
            },
            taskSiblings: function (task) {
                if (!task) {
                    return null;
                }
                var parent = this.taskParent(task);
                return this.taskChildren(parent);
            },
            taskParent: function (task) {
                if (!task || task.get('parentId') === null) {
                    return null;
                }
                return this.get(task.parentId);
            },
            taskLevel: function (task) {
                var level = 0;
                var parent = this.taskParent(task);
                while (parent !== null) {
                    level += 1;
                    parent = this.taskParent(parent);
                }
                return level;
            },
            taskTree: function (task) {
                var data = [];
                var current;
                var tasks = this.taskChildren(task);
                for (var i = 0, l = tasks.length; i < l; i++) {
                    current = tasks[i];
                    data.push(current);
                    if (current.get('expanded')) {
                        var children = this.taskTree(current);
                        data.push.apply(data, children);
                    }
                }
                return data;
            },
            update: function (task, taskInfo) {
                var that = this;
                var oldValue;
                var offsetChildren = function (parentTask, offset) {
                    var children = that.taskAllChildren(parentTask);
                    for (var i = 0, l = children.length; i < l; i++) {
                        children[i]._offset(offset);
                    }
                };
                var modelChangeHandler = function (e) {
                    var field = e.field;
                    var model = e.sender;
                    switch (field) {
                    case 'start':
                        that._resolveSummaryStart(that.taskParent(model));
                        offsetChildren(model, model.get(field).getTime() - oldValue.getTime());
                        break;
                    case 'end':
                        that._resolveSummaryEnd(that.taskParent(model));
                        break;
                    case 'percentComplete':
                        that._resolveSummaryPercentComplete(that.taskParent(model));
                        break;
                    case 'orderId':
                        that._reorderSiblings(model, oldValue);
                        break;
                    }
                };
                if (taskInfo.parentId !== undefined) {
                    oldValue = task.get('parentId');
                    if (oldValue !== taskInfo.parentId) {
                        task.set('parentId', taskInfo.parentId);
                        that._childRemoved(oldValue, task.get('orderId'));
                        task.set('orderId', that.taskSiblings(task).length - 1);
                        that._resolveSummaryFields(that.taskParent(task));
                    }
                    delete taskInfo.parentId;
                }
                task.bind('change', modelChangeHandler);
                for (var field in taskInfo) {
                    oldValue = task.get(field);
                    task.set(field, taskInfo[field]);
                }
                task.unbind('change', modelChangeHandler);
            },
            _resolveSummaryFields: function (summary) {
                if (!summary) {
                    return;
                }
                this._updateSummary(summary);
                if (!this.taskChildren(summary).length) {
                    return;
                }
                this._resolveSummaryStart(summary);
                this._resolveSummaryEnd(summary);
                this._resolveSummaryPercentComplete(summary);
            },
            _resolveSummaryStart: function (summary) {
                var that = this;
                var getSummaryStart = function (parentTask) {
                    var children = that.taskChildren(parentTask);
                    var min = children[0].start.getTime();
                    var currentMin;
                    for (var i = 1, l = children.length; i < l; i++) {
                        currentMin = children[i].start.getTime();
                        if (currentMin < min) {
                            min = currentMin;
                        }
                    }
                    return new Date(min);
                };
                this._updateSummaryRecursive(summary, 'start', getSummaryStart);
            },
            _resolveSummaryEnd: function (summary) {
                var that = this;
                var getSummaryEnd = function (parentTask) {
                    var children = that.taskChildren(parentTask);
                    var max = children[0].end.getTime();
                    var currentMax;
                    for (var i = 1, l = children.length; i < l; i++) {
                        currentMax = children[i].end.getTime();
                        if (currentMax > max) {
                            max = currentMax;
                        }
                    }
                    return new Date(max);
                };
                this._updateSummaryRecursive(summary, 'end', getSummaryEnd);
            },
            _resolveSummaryPercentComplete: function (summary) {
                var that = this;
                var getSummaryPercentComplete = function (parentTask) {
                    var children = that.taskChildren(parentTask);
                    var percentComplete = new Query(children).aggregate([{
                            field: 'percentComplete',
                            aggregate: 'average'
                        }]);
                    return percentComplete.percentComplete.average;
                };
                this._updateSummaryRecursive(summary, 'percentComplete', getSummaryPercentComplete);
            },
            _updateSummaryRecursive: function (summary, field, callback) {
                if (!summary) {
                    return;
                }
                var value = callback(summary);
                summary.set(field, value);
                var parent = this.taskParent(summary);
                if (parent) {
                    this._updateSummaryRecursive(parent, field, callback);
                }
            },
            _childRemoved: function (parentId, index) {
                var parent = parentId === null ? null : this.get(parentId);
                var children = this.taskChildren(parent);
                for (var i = index, l = children.length; i < l; i++) {
                    children[i].set('orderId', i);
                }
                this._resolveSummaryFields(parent);
            },
            _reorderSiblings: function (task, oldOrderId) {
                var orderId = task.get('orderId');
                var direction = orderId > oldOrderId;
                var startIndex = direction ? oldOrderId : orderId;
                var endIndex = direction ? orderId : oldOrderId;
                var newIndex = direction ? startIndex : startIndex + 1;
                var siblings = this.taskSiblings(task);
                endIndex = Math.min(endIndex, siblings.length - 1);
                for (var i = startIndex; i <= endIndex; i++) {
                    if (siblings[i] === task) {
                        continue;
                    }
                    siblings[i].set('orderId', newIndex);
                    newIndex += 1;
                }
            },
            _updateSummary: function (task) {
                if (task !== null) {
                    var childCount = this.taskChildren(task).length;
                    task.set('summary', childCount > 0);
                }
            },
            _toGanttTask: function (task) {
                if (!(task instanceof GanttTask)) {
                    var taskInfo = task;
                    task = this._createNewModel();
                    task.accept(taskInfo);
                }
                return task;
            }
        });
        GanttDataSource.create = createDataSource(GanttDataSource, 'GanttDataSource');
        extend(true, kendo.data, {
            GanttDataSource: GanttDataSource,
            GanttTask: GanttTask,
            GanttDependencyDataSource: GanttDependencyDataSource,
            GanttDependency: GanttDependency
        });
        var editors = {
            desktop: {
                dateRange: DATERANGEEDITOR,
                resources: RESOURCESEDITOR
            }
        };
        var Editor = kendo.Observable.extend({
            init: function (element, options) {
                kendo.Observable.fn.init.call(this);
                this.element = element;
                this.options = extend(true, {}, this.options, options);
                this.createButton = this.options.createButton;
            },
            fields: function (editors, model) {
                var that = this;
                var options = this.options;
                var messages = options.messages.editor;
                var resources = options.resources;
                var fields;
                var click = function (e) {
                    e.preventDefault();
                    resources.editor(that.container.find(DOT + Gantt.styles.popup.resourcesField), model);
                };
                if (options.editable.template) {
                    fields = $.map(model.fields, function (value, key) {
                        return { field: key };
                    });
                } else {
                    fields = [
                        {
                            field: 'title',
                            title: messages.title
                        },
                        {
                            field: 'start',
                            title: messages.start,
                            editor: editors.dateRange
                        },
                        {
                            field: 'end',
                            title: messages.end,
                            editor: editors.dateRange
                        },
                        {
                            field: 'percentComplete',
                            title: messages.percentComplete,
                            format: PERCENTAGE_FORMAT
                        }
                    ];
                    if (model.get(resources.field)) {
                        fields.push({
                            field: resources.field,
                            title: messages.resources,
                            messages: messages,
                            editor: editors.resources,
                            click: click,
                            styles: Gantt.styles.popup
                        });
                    }
                }
                return fields;
            },
            _buildEditTemplate: function (model, fields, editableFields) {
                var resources = this.options.resources;
                var template = this.options.editable.template;
                var settings = extend({}, kendo.Template, this.options.templateSettings);
                var paramName = settings.paramName;
                var popupStyles = Gantt.styles.popup;
                var html = '';
                if (template) {
                    if (typeof template === STRING) {
                        template = window.unescape(template);
                    }
                    html += kendo.template(template, settings)(model);
                } else {
                    for (var i = 0, length = fields.length; i < length; i++) {
                        var field = fields[i];
                        html += '<div class="' + popupStyles.editLabel + '"><label for="' + field.field + '">' + (field.title || field.field || '') + '</label></div>';
                        if (field.field === resources.field) {
                            html += '<div class="' + popupStyles.resourcesField + '" style="display:none"></div>';
                        }
                        if (!model.editable || model.editable(field.field)) {
                            editableFields.push(field);
                            html += '<div ' + kendo.attr('container-for') + '="' + field.field + '" class="' + popupStyles.editField + '"></div>';
                        } else {
                            var tmpl = '#:';
                            if (field.field) {
                                field = kendo.expr(field.field, paramName);
                                tmpl += field + '==null?\'\':' + field;
                            } else {
                                tmpl += '\'\'';
                            }
                            tmpl += '#';
                            tmpl = kendo.template(tmpl, settings);
                            html += '<div class="' + popupStyles.editField + '">' + tmpl(model) + '</div>';
                        }
                    }
                }
                return html;
            }
        });
        var PopupEditor = Editor.extend({
            destroy: function () {
                this.close();
                this.unbind();
            },
            editTask: function (task) {
                this.editable = this._createPopupEditor(task);
            },
            close: function () {
                var that = this;
                var destroy = function () {
                    if (that.editable) {
                        that.editable.destroy();
                        that.editable = null;
                        that.container = null;
                    }
                    if (that.popup) {
                        that.popup.destroy();
                        that.popup = null;
                    }
                };
                if (this.editable && this.container.is(':visible')) {
                    that.trigger('close', { window: that.container });
                    this.container.data('kendoWindow').bind('deactivate', destroy).close();
                } else {
                    destroy();
                }
            },
            showDialog: function (options) {
                var buttons = options.buttons;
                var popupStyles = Gantt.styles.popup;
                var html = kendo.format('<div class="{0}"><div class="{1}"><p class="{2}">{3}</p><div class="{4}">', popupStyles.form, popupStyles.formContainer, popupStyles.message, options.text, popupStyles.buttonsContainer);
                for (var i = 0, length = buttons.length; i < length; i++) {
                    html += this.createButton(buttons[i]);
                }
                html += '</div></div></div>';
                var wrapper = this.element;
                if (this.popup) {
                    this.popup.destroy();
                }
                var popup = this.popup = $(html).appendTo(wrapper).eq(0).on('click', DOT + popupStyles.button, function (e) {
                    e.preventDefault();
                    popup.close();
                    var buttonIndex = $(e.currentTarget).index();
                    buttons[buttonIndex].click();
                }).kendoWindow({
                    modal: true,
                    resizable: false,
                    draggable: false,
                    title: options.title,
                    visible: false,
                    deactivate: function () {
                        this.destroy();
                        wrapper.focus();
                    }
                }).getKendoWindow();
                popup.center().open();
            },
            _createPopupEditor: function (task) {
                var that = this;
                var options = {};
                var messages = this.options.messages;
                var ganttStyles = Gantt.styles;
                var popupStyles = ganttStyles.popup;
                var html = kendo.format('<div {0}="{1}" class="{2} {3}"><div class="{4}">', kendo.attr('uid'), task.uid, popupStyles.form, popupStyles.editForm, popupStyles.formContainer);
                var fields = this.fields(editors.desktop, task);
                var editableFields = [];
                html += this._buildEditTemplate(task, fields, editableFields);
                html += '<div class="' + popupStyles.buttonsContainer + '">';
                html += this.createButton({
                    name: 'update',
                    text: messages.save,
                    className: Gantt.styles.primary
                });
                html += this.createButton({
                    name: 'cancel',
                    text: messages.cancel
                });
                html += this.createButton({
                    name: 'delete',
                    text: messages.destroy
                });
                html += '</div></div></div>';
                var container = this.container = $(html).appendTo(this.element).eq(0).kendoWindow(extend({
                    modal: true,
                    resizable: false,
                    draggable: true,
                    title: messages.editor.editorTitle,
                    visible: false,
                    close: function (e) {
                        if (e.userTriggered) {
                            if (that.trigger('cancel', {
                                    container: container,
                                    model: task
                                })) {
                                e.preventDefault();
                            }
                        }
                    }
                }, options));
                var editableWidget = container.kendoEditable({
                    fields: editableFields,
                    model: task,
                    clearContainer: false,
                    validateOnBlur: true,
                    target: that.options.target
                }).data('kendoEditable');
                kendo.cycleForm(container);
                if (!this.trigger('edit', {
                        container: container,
                        model: task
                    })) {
                    container.data('kendoWindow').center().open();
                    container.on(CLICK + NS, DOT + ganttStyles.buttonCancel, function (e) {
                        e.preventDefault();
                        e.stopPropagation();
                        that.trigger('cancel', {
                            container: container,
                            model: task
                        });
                    });
                    container.on(CLICK + NS, DOT + ganttStyles.buttonSave, function (e) {
                        e.preventDefault();
                        e.stopPropagation();
                        var fields = that.fields(editors.desktop, task);
                        var updateInfo = {};
                        var field;
                        for (var i = 0, length = fields.length; i < length; i++) {
                            field = fields[i].field;
                            updateInfo[field] = task.get(field);
                        }
                        that.trigger('save', {
                            container: container,
                            model: task,
                            updateInfo: updateInfo
                        });
                    });
                    container.on(CLICK + NS, DOT + ganttStyles.buttonDelete, function (e) {
                        e.preventDefault();
                        e.stopPropagation();
                        that.trigger('remove', {
                            container: container,
                            model: task
                        });
                    });
                } else {
                    that.trigger('cancel', {
                        container: container,
                        model: task
                    });
                }
                return editableWidget;
            }
        });
        var ResourceEditor = Widget.extend({
            init: function (element, options) {
                Widget.fn.init.call(this, element, options);
                this.wrapper = this.element;
                this.model = this.options.model;
                this.resourcesField = this.options.resourcesField;
                this.createButton = this.options.createButton;
                this._initContainer();
                this._attachHandlers();
            },
            events: ['save'],
            open: function () {
                this.window.center().open();
                this.grid.resize(true);
            },
            close: function () {
                this.window.bind('deactivate', proxy(this.destroy, this)).close();
            },
            destroy: function () {
                this._dettachHandlers();
                this.grid.destroy();
                this.grid = null;
                this.window.destroy();
                this.window = null;
                Widget.fn.destroy.call(this);
                kendo.destroy(this.wrapper);
                this.element = this.wrapper = null;
            },
            _attachHandlers: function () {
                var ganttStyles = Gantt.styles;
                var grid = this.grid;
                var closeHandler = this._cancelProxy = proxy(this._cancel, this);
                this.container.on(CLICK + NS, DOT + ganttStyles.buttonCancel, this._cancelProxy);
                this._saveProxy = proxy(this._save, this);
                this.container.on(CLICK + NS, DOT + ganttStyles.buttonSave, this._saveProxy);
                this.window.bind('close', function (e) {
                    if (e.userTriggered) {
                        closeHandler(e);
                    }
                });
                grid.wrapper.on(CLICK + NS, 'input[type=\'checkbox\']', function () {
                    var element = $(this);
                    var row = $(element).closest('tr');
                    var model = grid.dataSource.getByUid(row.attr(kendo.attr('uid')));
                    var value = $(element).is(':checked') ? 1 : '';
                    model.set('value', value);
                });
            },
            _dettachHandlers: function () {
                this._cancelProxy = null;
                this._saveProxy = null;
                this.container.off(NS);
                this.grid.wrapper.off();
            },
            _cancel: function (e) {
                e.preventDefault();
                this.close();
            },
            _save: function (e) {
                e.preventDefault();
                this._updateModel();
                if (!this.wrapper.is(DOT + Gantt.styles.popup.resourcesField)) {
                    this.trigger('save', {
                        container: this.wrapper,
                        model: this.model
                    });
                }
                this.close();
            },
            _initContainer: function () {
                var popupStyles = Gantt.styles.popup;
                var dom = kendo.format('<div class="{0} {1}"><div class="{2} {3}"/></div>"', popupStyles.form, popupStyles.editForm, popupStyles.formContainer, popupStyles.resourcesFormContainer);
                dom = $(dom);
                this.container = dom.find(DOT + popupStyles.resourcesFormContainer);
                this.window = dom.kendoWindow({
                    modal: true,
                    resizable: false,
                    draggable: true,
                    visible: false,
                    title: this.options.messages.resourcesEditorTitle
                }).data('kendoWindow');
                this._resourceGrid();
                this._createButtons();
            },
            _resourceGrid: function () {
                var that = this;
                var messages = this.options.messages;
                var element = $('<div id="resources-grid"/>').appendTo(this.container);
                this.grid = new kendo.ui.Grid(element, {
                    columns: [
                        {
                            field: 'name',
                            title: messages.resourcesHeader,
                            template: '<label><input type=\'checkbox\' value=\'#=name#\'' + '# if (value > 0 && value !== null) {#' + 'checked=\'checked\'' + '# } #' + '/>#=name#</labe>'
                        },
                        {
                            field: 'value',
                            title: messages.unitsHeader,
                            template: function (dataItem) {
                                var valueFormat = dataItem.format;
                                var value = dataItem.value !== null ? dataItem.value : '';
                                return valueFormat ? kendo.toString(value, valueFormat) : value;
                            }
                        }
                    ],
                    height: 280,
                    sortable: true,
                    editable: true,
                    filterable: true,
                    dataSource: {
                        data: that.options.data,
                        schema: {
                            model: {
                                id: 'id',
                                fields: {
                                    id: { from: 'id' },
                                    name: {
                                        from: 'name',
                                        type: 'string',
                                        editable: false
                                    },
                                    value: {
                                        from: 'value',
                                        type: 'number',
                                        defaultValue: ''
                                    },
                                    format: {
                                        from: 'format',
                                        type: 'string'
                                    }
                                }
                            }
                        }
                    },
                    save: function (e) {
                        var value = !!e.values.value;
                        e.container.parent().find('input[type=\'checkbox\']').prop('checked', value);
                    }
                });
            },
            _createButtons: function () {
                var buttons = this.options.buttons;
                var html = '<div class="' + Gantt.styles.popup.buttonsContainer + '">';
                for (var i = 0, length = buttons.length; i < length; i++) {
                    html += this.createButton(buttons[i]);
                }
                html += '</div>';
                this.container.append(html);
            },
            _updateModel: function () {
                var resources = [];
                var value;
                var data = this.grid.dataSource.data();
                for (var i = 0, length = data.length; i < length; i++) {
                    value = data[i].get('value');
                    if (value !== null && value > 0) {
                        resources.push(data[i]);
                    }
                }
                this.model[this.resourcesField] = resources;
            }
        });
        var Gantt = Widget.extend({
            init: function (element, options, events) {
                if (isArray(options)) {
                    options = { dataSource: options };
                }
                defaultCommands = {
                    append: {
                        text: 'Add Task',
                        action: 'add',
                        className: Gantt.styles.toolbar.appendButton,
                        iconClass: Gantt.styles.toolbar.iconPlus
                    },
                    pdf: {
                        text: 'Export to PDF',
                        className: Gantt.styles.toolbar.pdfButton,
                        iconClass: Gantt.styles.toolbar.iconPdf
                    }
                };
                Widget.fn.init.call(this, element, options);
                if (events) {
                    this._events = events;
                }
                this._wrapper();
                this._resources();
                if (!this.options.views || !this.options.views.length) {
                    this.options.views = [
                        'day',
                        'week',
                        'month'
                    ];
                }
                this._timeline();
                this._toolbar();
                this._footer();
                this._adjustDimensions();
                this._preventRefresh = true;
                this.view(this.timeline._selectedViewName);
                this._preventRefresh = false;
                this._dataSource();
                this._assignments();
                this._dropDowns();
                this._list();
                this._dependencies();
                this._resizable();
                this._scrollable();
                this._dataBind();
                this._attachEvents();
                this._createEditor();
                kendo.notify(this);
            },
            events: [
                'dataBinding',
                'dataBound',
                'add',
                'edit',
                'remove',
                'cancel',
                'save',
                'change',
                'navigate',
                'moveStart',
                'move',
                'moveEnd',
                'resizeStart',
                'resize',
                'resizeEnd',
                'columnResize'
            ],
            options: {
                name: 'Gantt',
                autoBind: true,
                navigatable: false,
                selectable: true,
                editable: true,
                resizable: false,
                columnResizeHandleWidth: defaultIndicatorWidth,
                columns: [],
                views: [],
                dataSource: {},
                dependencies: {},
                resources: {},
                assignments: {},
                taskTemplate: null,
                messages: {
                    save: 'Save',
                    cancel: 'Cancel',
                    destroy: 'Delete',
                    deleteTaskConfirmation: TASK_DELETE_CONFIRM,
                    deleteDependencyConfirmation: DEPENDENCY_DELETE_CONFIRM,
                    deleteTaskWindowTitle: 'Delete task',
                    deleteDependencyWindowTitle: 'Delete dependency',
                    views: {
                        day: 'Day',
                        week: 'Week',
                        month: 'Month',
                        year: 'Year',
                        start: 'Start',
                        end: 'End'
                    },
                    actions: {
                        append: 'Add Task',
                        addChild: 'Add Child',
                        insertBefore: 'Add Above',
                        insertAfter: 'Add Below',
                        pdf: 'Export to PDF'
                    },
                    editor: {
                        editorTitle: 'Task',
                        resourcesEditorTitle: 'Resources',
                        title: 'Title',
                        start: 'Start',
                        end: 'End',
                        percentComplete: 'Complete',
                        resources: 'Resources',
                        assignButton: 'Assign',
                        resourcesHeader: 'Resources',
                        unitsHeader: 'Units'
                    }
                },
                showWorkHours: true,
                showWorkDays: true,
                toolbar: null,
                workDayStart: new Date(1980, 1, 1, 8, 0, 0),
                workDayEnd: new Date(1980, 1, 1, 17, 0, 0),
                workWeekStart: 1,
                workWeekEnd: 5,
                hourSpan: 1,
                snap: true,
                height: 600,
                listWidth: '30%',
                rowHeight: null
            },
            select: function (value) {
                var list = this.list;
                if (!value) {
                    return list.select();
                }
                list.select(value);
                return;
            },
            clearSelection: function () {
                this.list.clearSelection();
            },
            destroy: function () {
                Widget.fn.destroy.call(this);
                if (this.dataSource) {
                    this.dataSource.unbind('change', this._refreshHandler);
                    this.dataSource.unbind('progress', this._progressHandler);
                    this.dataSource.unbind('error', this._errorHandler);
                }
                if (this.dependencies) {
                    this.dependencies.unbind('change', this._dependencyRefreshHandler);
                    this.dependencies.unbind('error', this._dependencyErrorHandler);
                }
                if (this.timeline) {
                    this.timeline.unbind();
                    this.timeline.destroy();
                }
                if (this.list) {
                    this.list.unbind();
                    this.list.destroy();
                }
                if (this.footerDropDown) {
                    this.footerDropDown.destroy();
                }
                if (this.headerDropDown) {
                    this.headerDropDown.destroy();
                }
                if (this._editor) {
                    this._editor.destroy();
                }
                if (this._resizeDraggable) {
                    this._resizeDraggable.destroy();
                }
                this.toolbar.off(NS);
                if (supportsMedia) {
                    this._mediaQuery.removeListener(this._mediaQueryHandler);
                    this._mediaQuery = null;
                }
                $(window).off('resize' + NS, this._resizeHandler);
                $(this.wrapper).off(NS);
                this.toolbar = null;
                this.footer = null;
            },
            setOptions: function (options) {
                var newOptions = kendo.deepExtend({}, this.options, options);
                var events = this._events;
                if (!options.views) {
                    var selectedView = this.view().name;
                    newOptions.views = $.map(this.options.views, function (view) {
                        var isSettings = isPlainObject(view);
                        var name = isSettings ? typeof view.type !== 'string' ? view.title : view.type : view;
                        if (selectedView === name) {
                            if (isSettings) {
                                view.selected = true;
                            } else {
                                view = {
                                    type: name,
                                    selected: true
                                };
                            }
                        } else if (isSettings) {
                            view.selected = false;
                        }
                        return view;
                    });
                }
                if (!options.dataSource) {
                    newOptions.dataSource = this.dataSource;
                }
                if (!options.dependencies) {
                    newOptions.dependencies = this.dependencies;
                }
                if (!options.resources) {
                    newOptions.resources = this.resources;
                }
                if (!options.assignments) {
                    newOptions.assignments = this.assignments;
                }
                this.destroy();
                this.element.empty();
                this.options = null;
                this.init(this.element, newOptions, events);
                Widget.fn._setEvents.call(this, newOptions);
            },
            _attachEvents: function () {
                this._resizeHandler = proxy(this.resize, this, false);
                $(window).on('resize' + NS, this._resizeHandler);
            },
            _wrapper: function () {
                var ganttStyles = Gantt.styles;
                var splitBarHandleClassName = [
                    ganttStyles.icon,
                    ganttStyles.resizeHandle
                ].join(' ');
                var options = this.options;
                var height = options.height;
                var width = options.width;
                this.wrapper = this.element.addClass(ganttStyles.wrapper).append('<div class=\'' + ganttStyles.listWrapper + '\'><div></div></div>').append('<div class=\'' + ganttStyles.splitBarWrapper + '\'><div class=\'' + splitBarHandleClassName + '\'></div></div>').append('<div class=\'' + ganttStyles.timelineWrapper + '\'><div></div></div>');
                this.wrapper.find(DOT + ganttStyles.list).width(options.listWidth);
                if (height) {
                    this.wrapper.height(height);
                }
                if (width) {
                    this.wrapper.width(width);
                }
                if (options.rowHeight) {
                    this.wrapper.addClass(ganttStyles.rowHeight);
                }
            },
            _toolbar: function () {
                var that = this;
                var ganttStyles = Gantt.styles;
                var viewsSelector = DOT + ganttStyles.toolbar.views + ' > li';
                var pdfSelector = DOT + ganttStyles.toolbar.pdfButton;
                var toggleSelector = DOT + ganttStyles.buttonToggle;
                var contentSelector = DOT + ganttStyles.gridContent;
                var treelist = $(DOT + ganttStyles.list);
                var timeline = $(DOT + ganttStyles.timeline);
                var hoveredClassName = ganttStyles.hovered;
                var actions = this.options.toolbar;
                var actionsWrap = $('<div class=\'' + ganttStyles.toolbar.actions + '\'>');
                var toolbar;
                var views;
                var toggleButton;
                var handler = function (e) {
                    if (e.matches) {
                        treelist.css({
                            'display': 'none',
                            'max-width': 0
                        });
                    } else {
                        treelist.css({
                            'display': 'inline-block',
                            'width': '30%',
                            'max-width': 'none'
                        });
                        timeline.css('display', 'inline-block');
                        that.refresh();
                        timeline.find(contentSelector).scrollTop(that.scrollTop);
                    }
                    that._resize();
                };
                if (!isFunction(actions)) {
                    actions = typeof actions === STRING ? actions : this._actions(actions);
                    actions = proxy(kendo.template(actions), this);
                }
                toggleButton = $(TOGGLE_BUTTON_TEMPLATE({ styles: ganttStyles.toolbar }));
                views = $(HEADER_VIEWS_TEMPLATE({
                    ns: kendo.ns,
                    views: this.timeline.views,
                    styles: ganttStyles.toolbar
                }));
                actionsWrap.append(actions({}));
                toolbar = $('<div class=\'' + ganttStyles.toolbar.headerWrapper + '\'>').append(toggleButton).append(views).append(actionsWrap);
                if (views.find('li').length > 1) {
                    views.prepend(VIEWBUTTONTEMPLATE({ styles: ganttStyles.toolbar }));
                }
                this.wrapper.prepend(toolbar);
                this.toolbar = toolbar;
                if (supportsMedia) {
                    this._mediaQueryHandler = proxy(handler, this);
                    this._mediaQuery = window.matchMedia('(max-width: 480px)');
                    this._mediaQuery.addListener(this._mediaQueryHandler);
                }
                toolbar.on(CLICK + NS, viewsSelector, function (e) {
                    e.preventDefault();
                    var list = that.list;
                    var name = $(this).attr(kendo.attr('name'));
                    var currentView = views.find(DOT + ganttStyles.toolbar.currentView);
                    if (currentView.is(':visible')) {
                        currentView.parent().toggleClass(ganttStyles.toolbar.expanded);
                    }
                    if (list.editable && list.editable.trigger('validate')) {
                        return;
                    }
                    if (!that.trigger('navigate', { view: name })) {
                        that.view(name);
                    }
                }).on(CLICK + NS, pdfSelector, function (e) {
                    e.preventDefault();
                    that.saveAsPDF();
                }).on(CLICK + NS, toggleSelector, function (e) {
                    e.preventDefault();
                    if (treelist.is(':visible')) {
                        treelist.css({
                            'display': 'none',
                            'width': '0'
                        });
                        timeline.css({
                            'display': 'inline-block',
                            'width': '100%'
                        });
                        that.refresh();
                        timeline.find(contentSelector).scrollTop(that.scrollTop);
                    } else {
                        timeline.css({
                            'display': 'none',
                            'width': 0
                        });
                        treelist.css({
                            'display': 'inline-block',
                            'width': '100%',
                            'max-width': 'none'
                        }).find(contentSelector).scrollTop(that.scrollTop);
                    }
                    that._resize();
                });
                this.wrapper.find(DOT + ganttStyles.toolbar.toolbar + ' li').hover(function () {
                    $(this).addClass(hoveredClassName);
                }, function () {
                    $(this).removeClass(hoveredClassName);
                });
            },
            _actions: function () {
                var options = this.options;
                var actions = options.toolbar;
                var html = '';
                if (!isArray(actions)) {
                    if (options.editable) {
                        actions = ['append'];
                    } else {
                        return html;
                    }
                }
                for (var i = 0, length = actions.length; i < length; i++) {
                    html += this._createButton(actions[i]);
                }
                return html;
            },
            _footer: function () {
                if (!this.options.editable) {
                    return;
                }
                var ganttStyles = Gantt.styles.toolbar;
                var messages = this.options.messages.actions;
                var button = $(kendo.template(BUTTON_TEMPLATE)(extend(true, { styles: ganttStyles }, defaultCommands.append, { text: messages.append })));
                var actionsWrap = $('<div class=\'' + ganttStyles.actions + '\'>').append(button);
                var footer = $('<div class=\'' + ganttStyles.footerWrapper + '\'>').append(actionsWrap);
                this.wrapper.append(footer);
                this.footer = footer;
            },
            _createButton: function (command) {
                var template = command.template || BUTTON_TEMPLATE;
                var messages = this.options.messages.actions;
                var commandName = typeof command === STRING ? command : command.name || command.text;
                var className = defaultCommands[commandName] ? defaultCommands[commandName].className : 'k-gantt-' + (commandName || '').replace(/\s/g, '');
                var options = {
                    iconClass: '',
                    action: '',
                    text: commandName,
                    className: className,
                    styles: Gantt.styles.toolbar
                };
                if (!commandName && !(isPlainObject(command) && command.template)) {
                    throw new Error('Custom commands should have name specified');
                }
                options = extend(true, options, defaultCommands[commandName], { text: messages[commandName] });
                if (isPlainObject(command)) {
                    if (command.className && inArray(options.className, command.className.split(' ')) < 0) {
                        command.className += ' ' + options.className;
                    }
                    options = extend(true, options, command);
                }
                return kendo.template(template)(options);
            },
            _adjustDimensions: function () {
                var element = this.element;
                var ganttStyles = Gantt.styles;
                var listSelector = DOT + ganttStyles.list;
                var timelineSelector = DOT + ganttStyles.timeline;
                var splitBarSelector = DOT + ganttStyles.splitBar;
                var toolbarHeight = this.toolbar.outerHeight();
                var footerHeight = this.footer ? this.footer.outerHeight() : 0;
                var totalHeight = element.height();
                var totalWidth = element.width();
                var splitBarWidth = element.find(splitBarSelector).outerWidth();
                var treeListWidth = element.find(listSelector).outerWidth();
                element.children([
                    listSelector,
                    timelineSelector,
                    splitBarSelector
                ].join(',')).height(totalHeight - (toolbarHeight + footerHeight)).end().children(timelineSelector).width(totalWidth - (splitBarWidth + treeListWidth));
                if (totalWidth < treeListWidth + splitBarWidth) {
                    element.find(listSelector).width(totalWidth - splitBarWidth);
                }
            },
            _scrollTo: function (value) {
                var view = this.timeline.view();
                var list = this.list;
                var attr = kendo.attr('uid');
                var id = typeof value === 'string' ? value : value.closest('tr' + selector()).attr(attr);
                var action;
                var scrollTarget;
                var scrollIntoView = function () {
                    if (scrollTarget.length !== 0) {
                        action();
                    }
                };
                if (view.content.is(':visible')) {
                    scrollTarget = view.content.find(selector(id));
                    action = function () {
                        view._scrollTo(scrollTarget);
                    };
                } else {
                    scrollTarget = list.content.find(selector(id));
                    action = function () {
                        scrollTarget.get(0).scrollIntoView();
                    };
                }
                scrollIntoView();
            },
            _dropDowns: function () {
                var that = this;
                var actionsSelector = DOT + Gantt.styles.toolbar.actions;
                var actionMessages = this.options.messages.actions;
                var timeline = this.timeline;
                var handler = function (e) {
                    var type = e.type;
                    var orderId;
                    var dataSource = that.dataSource;
                    var task = dataSource._createNewModel();
                    var selected = that.dataItem(that.select());
                    var parent = dataSource.taskParent(selected);
                    var firstSlot = timeline.view()._timeSlots()[0];
                    var target = type === 'add' ? selected : parent;
                    var editable = that.list.editable;
                    if (editable && editable.trigger('validate')) {
                        return;
                    }
                    task.set('title', 'New task');
                    if (target) {
                        task.set('parentId', target.get('id'));
                        task.set('start', target.get('start'));
                        task.set('end', target.get('end'));
                    } else {
                        task.set('start', firstSlot.start);
                        task.set('end', firstSlot.end);
                    }
                    if (type !== 'add') {
                        orderId = selected.get('orderId');
                        orderId = type === 'insert-before' ? orderId : orderId + 1;
                    }
                    that._createTask(task, orderId);
                };
                if (!this.options.editable) {
                    return;
                }
                this.footerDropDown = new TaskDropDown(this.footer.children(actionsSelector).eq(0), {
                    messages: { actions: actionMessages },
                    direction: 'up',
                    animation: { open: { effects: 'slideIn:up' } },
                    navigatable: that.options.navigatable
                });
                this.headerDropDown = new TaskDropDown(this.toolbar.children(actionsSelector).eq(0), {
                    messages: { actions: actionMessages },
                    navigatable: that.options.navigatable
                });
                this.footerDropDown.bind('command', handler);
                this.headerDropDown.bind('command', handler);
            },
            _list: function () {
                var that = this;
                var navigatable = that.options.navigatable;
                var ganttStyles = Gantt.styles;
                var listWrapper = this.wrapper.find(DOT + ganttStyles.list);
                var element = listWrapper.find('> div');
                var toggleButtons = this.wrapper.find(DOT + ganttStyles.toolbar.actions + ' > button');
                var options = {
                    columns: this.options.columns || [],
                    dataSource: this.dataSource,
                    selectable: this.options.selectable,
                    editable: this.options.editable,
                    resizable: this.options.resizable,
                    columnResizeHandleWidth: this.options.columnResizeHandleWidth,
                    listWidth: listWrapper.outerWidth(),
                    resourcesField: this.resources.field,
                    rowHeight: this.options.rowHeight
                };
                var columns = options.columns;
                var column;
                var restoreFocus = function () {
                    if (navigatable) {
                        that._current(that._cachedCurrent);
                        focusTable(that.list.content.find('table'), true);
                    }
                    delete that._cachedCurrent;
                };
                for (var i = 0; i < columns.length; i++) {
                    column = columns[i];
                    if (column.field === this.resources.field && typeof column.editor !== 'function') {
                        column.editor = proxy(this._createResourceEditor, this);
                    }
                }
                this.list = new kendo.ui.GanttList(element, options);
                this.list.bind('render', function () {
                    that._navigatable();
                }, true).bind('edit', function (e) {
                    that._cachedCurrent = e.cell;
                    if (that.trigger('edit', {
                            task: e.model,
                            container: e.cell
                        })) {
                        e.preventDefault();
                    }
                }).bind('cancel', function (e) {
                    if (that.trigger('cancel', {
                            task: e.model,
                            container: e.cell
                        })) {
                        e.preventDefault();
                    }
                    restoreFocus();
                }).bind('update', function (e) {
                    that._updateTask(e.task, e.updateInfo);
                    restoreFocus();
                }).bind('change', function () {
                    that.trigger('change');
                    var selection = that.list.select();
                    if (selection.length) {
                        toggleButtons.removeAttr('data-action', 'add');
                        that.timeline.select('[data-uid=\'' + selection.attr('data-uid') + '\']');
                    } else {
                        toggleButtons.attr('data-action', 'add');
                        that.timeline.clearSelection();
                    }
                }).bind('columnResize', function (e) {
                    that.trigger('columnResize', {
                        column: e.column,
                        oldWidth: e.oldWidth,
                        newWidth: e.newWidth
                    });
                });
            },
            _timeline: function () {
                var that = this;
                var ganttStyles = Gantt.styles;
                var options = trimOptions(extend(true, { resourcesField: this.resources.field }, this.options));
                var element = this.wrapper.find(DOT + ganttStyles.timeline + ' > div');
                var currentViewSelector = DOT + ganttStyles.toolbar.currentView + ' > ' + DOT + ganttStyles.toolbar.link;
                this.timeline = new kendo.ui.GanttTimeline(element, options);
                this.timeline.bind('navigate', function (e) {
                    var viewName = e.view.replace(/\./g, '\\.').toLowerCase();
                    var text = that.toolbar.find(DOT + ganttStyles.toolbar.views + ' > li').removeClass(ganttStyles.selected).end().find(DOT + ganttStyles.toolbar.viewButton + '-' + viewName).addClass(ganttStyles.selected).find(DOT + ganttStyles.toolbar.link).text();
                    that.toolbar.find(currentViewSelector).text(text);
                    that.refresh();
                }).bind('moveStart', function (e) {
                    var editable = that.list.editable;
                    if (editable && editable.trigger('validate')) {
                        e.preventDefault();
                        return;
                    }
                    if (that.trigger('moveStart', { task: e.task })) {
                        e.preventDefault();
                    }
                }).bind('move', function (e) {
                    var task = e.task;
                    var start = e.start;
                    var end = new Date(start.getTime() + task.duration());
                    if (that.trigger('move', {
                            task: task,
                            start: start,
                            end: end
                        })) {
                        e.preventDefault();
                    }
                }).bind('moveEnd', function (e) {
                    var task = e.task;
                    var start = e.start;
                    var end = new Date(start.getTime() + task.duration());
                    if (!that.trigger('moveEnd', {
                            task: task,
                            start: start,
                            end: end
                        })) {
                        that._updateTask(that.dataSource.getByUid(task.uid), {
                            start: start,
                            end: end
                        });
                    }
                }).bind('resizeStart', function (e) {
                    var editable = that.list.editable;
                    if (editable && editable.trigger('validate')) {
                        e.preventDefault();
                        return;
                    }
                    if (that.trigger('resizeStart', { task: e.task })) {
                        e.preventDefault();
                    }
                }).bind('resize', function (e) {
                    if (that.trigger('resize', {
                            task: e.task,
                            start: e.start,
                            end: e.end
                        })) {
                        e.preventDefault();
                    }
                }).bind('resizeEnd', function (e) {
                    var task = e.task;
                    var updateInfo = {};
                    if (e.resizeStart) {
                        updateInfo.start = e.start;
                    } else {
                        updateInfo.end = e.end;
                    }
                    if (!that.trigger('resizeEnd', {
                            task: task,
                            start: e.start,
                            end: e.end
                        })) {
                        that._updateTask(that.dataSource.getByUid(task.uid), updateInfo);
                    }
                }).bind('percentResizeStart', function (e) {
                    var editable = that.list.editable;
                    if (editable && editable.trigger('validate')) {
                        e.preventDefault();
                    }
                }).bind('percentResizeEnd', function (e) {
                    that._updateTask(that.dataSource.getByUid(e.task.uid), { percentComplete: e.percentComplete });
                }).bind('dependencyDragStart', function (e) {
                    var editable = that.list.editable;
                    if (editable && editable.trigger('validate')) {
                        e.preventDefault();
                    }
                }).bind('dependencyDragEnd', function (e) {
                    var dependency = that.dependencies._createNewModel({
                        type: e.type,
                        predecessorId: e.predecessor.id,
                        successorId: e.successor.id
                    });
                    that._createDependency(dependency);
                }).bind('select', function (e) {
                    var editable = that.list.editable;
                    if (editable) {
                        editable.trigger('validate');
                    }
                    that.select('[data-uid=\'' + e.uid + '\']');
                }).bind('editTask', function (e) {
                    var editable = that.list.editable;
                    if (editable && editable.trigger('validate')) {
                        return;
                    }
                    that.editTask(e.uid);
                }).bind('clear', function () {
                    that.clearSelection();
                }).bind('removeTask', function (e) {
                    var editable = that.list.editable;
                    if (editable && editable.trigger('validate')) {
                        return;
                    }
                    that.removeTask(that.dataSource.getByUid(e.uid));
                }).bind('removeDependency', function (e) {
                    var editable = that.list.editable;
                    if (editable && editable.trigger('validate')) {
                        return;
                    }
                    that.removeDependency(that.dependencies.getByUid(e.uid));
                });
            },
            _dataSource: function () {
                var options = this.options;
                var dataSource = options.dataSource;
                dataSource = isArray(dataSource) ? { data: dataSource } : dataSource;
                if (this.dataSource && this._refreshHandler) {
                    this.dataSource.unbind('change', this._refreshHandler).unbind('progress', this._progressHandler).unbind('error', this._errorHandler);
                } else {
                    this._refreshHandler = proxy(this.refresh, this);
                    this._progressHandler = proxy(this._requestStart, this);
                    this._errorHandler = proxy(this._error, this);
                }
                this.dataSource = kendo.data.GanttDataSource.create(dataSource).bind('change', this._refreshHandler).bind('progress', this._progressHandler).bind('error', this._errorHandler);
            },
            _dependencies: function () {
                var dependencies = this.options.dependencies || {};
                var dataSource = isArray(dependencies) ? { data: dependencies } : dependencies;
                if (this.dependencies && this._dependencyRefreshHandler) {
                    this.dependencies.unbind('change', this._dependencyRefreshHandler).unbind('error', this._dependencyErrorHandler);
                } else {
                    this._dependencyRefreshHandler = proxy(this.refreshDependencies, this);
                    this._dependencyErrorHandler = proxy(this._error, this);
                }
                this.dependencies = kendo.data.GanttDependencyDataSource.create(dataSource).bind('change', this._dependencyRefreshHandler).bind('error', this._dependencyErrorHandler);
            },
            _resources: function () {
                var resources = this.options.resources;
                var dataSource = resources.dataSource || {};
                this.resources = {
                    field: 'resources',
                    dataTextField: 'name',
                    dataColorField: 'color',
                    dataFormatField: 'format'
                };
                extend(this.resources, resources);
                this.resources.dataSource = kendo.data.DataSource.create(dataSource);
            },
            _assignments: function () {
                var assignments = this.options.assignments;
                var dataSource = assignments.dataSource || {};
                if (this.assignments) {
                    this.assignments.dataSource.unbind('change', this._assignmentsRefreshHandler);
                } else {
                    this._assignmentsRefreshHandler = proxy(this.refresh, this);
                }
                this.assignments = {
                    dataTaskIdField: 'taskId',
                    dataResourceIdField: 'resourceId',
                    dataValueField: 'value'
                };
                extend(this.assignments, assignments);
                this.assignments.dataSource = kendo.data.DataSource.create(dataSource);
                this.assignments.dataSource.bind('change', this._assignmentsRefreshHandler);
            },
            _createEditor: function () {
                var that = this;
                var editor = this._editor = new PopupEditor(this.wrapper, extend({}, this.options, {
                    target: this,
                    resources: {
                        field: this.resources.field,
                        editor: proxy(this._createResourceEditor, this)
                    },
                    createButton: proxy(this._createPopupButton, this)
                }));
                editor.bind('cancel', function (e) {
                    var task = that.dataSource.getByUid(e.model.uid);
                    if (that.trigger('cancel', {
                            container: e.container,
                            task: task
                        })) {
                        e.preventDefault();
                        return;
                    }
                    that.cancelTask();
                }).bind('edit', function (e) {
                    var task = that.dataSource.getByUid(e.model.uid);
                    if (that.trigger('edit', {
                            container: e.container,
                            task: task
                        })) {
                        e.preventDefault();
                    }
                }).bind('save', function (e) {
                    var task = that.dataSource.getByUid(e.model.uid);
                    that.saveTask(task, e.updateInfo);
                }).bind('remove', function (e) {
                    that.removeTask(e.model.uid);
                }).bind('close', that._onDialogClose);
            },
            _onDialogClose: function () {
            },
            _createResourceEditor: function (container, options) {
                var that = this;
                var model = options instanceof ObservableObject ? options : options.model;
                var id = model.get('id');
                var messages = this.options.messages;
                var resourcesField = that.resources.field;
                var editor = this._resourceEditor = new ResourceEditor(container, {
                    resourcesField: resourcesField,
                    data: this._wrapResourceData(id),
                    model: model,
                    messages: extend({}, messages.editor),
                    buttons: [
                        {
                            name: 'update',
                            text: messages.save,
                            className: Gantt.styles.primary
                        },
                        {
                            name: 'cancel',
                            text: messages.cancel
                        }
                    ],
                    createButton: proxy(this._createPopupButton, this),
                    save: function (e) {
                        that._updateAssignments(e.model.get('id'), e.model.get(resourcesField));
                    }
                });
                editor.open();
            },
            _createPopupButton: function (command) {
                var commandName = command.name || command.text;
                var options = {
                    className: Gantt.styles.popup.button + ' k-gantt-' + (commandName || '').replace(/\s/g, ''),
                    text: commandName,
                    attr: ''
                };
                if (!commandName && !(isPlainObject(command) && command.template)) {
                    throw new Error('Custom commands should have name specified');
                }
                if (isPlainObject(command)) {
                    if (command.className) {
                        command.className += ' ' + options.className;
                    }
                    options = extend(true, options, command);
                }
                return kendo.template(COMMAND_BUTTON_TEMPLATE)(options);
            },
            view: function (type) {
                return this.timeline.view(type);
            },
            dataItem: function (value) {
                if (!value) {
                    return null;
                }
                var list = this.list;
                var element = list.content.find(value);
                return list._modelFromElement(element);
            },
            setDataSource: function (dataSource) {
                this.options.dataSource = dataSource;
                this._dataSource();
                this.list._setDataSource(this.dataSource);
                if (this.options.autoBind) {
                    dataSource.fetch();
                }
            },
            setDependenciesDataSource: function (dependencies) {
                this.options.dependencies = dependencies;
                this._dependencies();
                if (this.options.autoBind) {
                    dependencies.fetch();
                }
            },
            items: function () {
                return this.wrapper.children('.k-task');
            },
            _updateAssignments: function (id, resources) {
                var dataSource = this.assignments.dataSource;
                var taskId = this.assignments.dataTaskIdField;
                var resourceId = this.assignments.dataResourceIdField;
                var hasMatch = false;
                var assignments = new Query(dataSource.view()).filter({
                    field: taskId,
                    operator: 'eq',
                    value: id
                }).toArray();
                var assignment;
                var resource;
                var value;
                while (assignments.length) {
                    assignment = assignments[0];
                    for (var i = 0, length = resources.length; i < length; i++) {
                        resource = resources[i];
                        if (assignment.get(resourceId) === resource.get('id')) {
                            value = resources[i].get('value');
                            this._updateAssignment(assignment, value);
                            resources.splice(i, 1);
                            hasMatch = true;
                            break;
                        }
                    }
                    if (!hasMatch) {
                        this._removeAssignment(assignment);
                    }
                    hasMatch = false;
                    assignments.shift();
                }
                for (var j = 0, newLength = resources.length; j < newLength; j++) {
                    resource = resources[j];
                    this._createAssignment(resource, id);
                }
                dataSource.sync();
            },
            cancelTask: function () {
                var editor = this._editor;
                var container = editor.container;
                if (container) {
                    editor.close();
                }
            },
            editTask: function (uid) {
                var task = typeof uid === 'string' ? this.dataSource.getByUid(uid) : uid;
                if (!task) {
                    return;
                }
                var taskCopy = this.dataSource._createNewModel(task.toJSON());
                taskCopy.uid = task.uid;
                this.cancelTask();
                this._editTask(taskCopy);
            },
            _editTask: function (task) {
                this._editor.editTask(task);
            },
            saveTask: function (task, updateInfo) {
                var editor = this._editor;
                var container = editor.container;
                var editable = editor.editable;
                if (container && editable && editable.end()) {
                    this._updateTask(task, updateInfo);
                }
            },
            _updateTask: function (task, updateInfo) {
                var resourcesField = this.resources.field;
                if (!this.trigger('save', {
                        task: task,
                        values: updateInfo
                    })) {
                    this._preventRefresh = true;
                    this.dataSource.update(task, updateInfo);
                    if (updateInfo[resourcesField]) {
                        this._updateAssignments(task.get('id'), updateInfo[resourcesField]);
                    }
                    this._syncDataSource();
                }
            },
            _updateAssignment: function (assignment, value) {
                var resourceValueField = this.assignments.dataValueField;
                assignment.set(resourceValueField, value);
            },
            removeTask: function (uid) {
                var that = this;
                var task = typeof uid === 'string' ? this.dataSource.getByUid(uid) : uid;
                if (!task) {
                    return;
                }
                this._taskConfirm(function (cancel) {
                    if (!cancel) {
                        that._removeTask(task);
                    }
                }, task);
            },
            _createTask: function (task, index) {
                if (!this.trigger('add', {
                        task: task,
                        dependency: null
                    })) {
                    var dataSource = this.dataSource;
                    this._preventRefresh = true;
                    if (index === undefined) {
                        dataSource.add(task);
                    } else {
                        dataSource.insert(index, task);
                    }
                    this._scrollToUid = task.uid;
                    this._syncDataSource();
                }
            },
            _createDependency: function (dependency) {
                if (!this.trigger('add', {
                        task: null,
                        dependency: dependency
                    })) {
                    this._preventDependencyRefresh = true;
                    this.dependencies.add(dependency);
                    this._preventDependencyRefresh = false;
                    this.dependencies.sync();
                }
            },
            _createAssignment: function (resource, id) {
                var assignments = this.assignments;
                var dataSource = assignments.dataSource;
                var taskId = assignments.dataTaskIdField;
                var resourceId = assignments.dataResourceIdField;
                var resourceValue = assignments.dataValueField;
                var assignment = dataSource._createNewModel();
                assignment[taskId] = id;
                assignment[resourceId] = resource.get('id');
                assignment[resourceValue] = resource.get('value');
                dataSource.add(assignment);
            },
            removeDependency: function (uid) {
                var that = this;
                var dependency = typeof uid === 'string' ? this.dependencies.getByUid(uid) : uid;
                if (!dependency) {
                    return;
                }
                this._dependencyConfirm(function (cancel) {
                    if (!cancel) {
                        that._removeDependency(dependency);
                    }
                }, dependency);
            },
            _removeTaskDependencies: function (task, dependencies) {
                this._preventDependencyRefresh = true;
                for (var i = 0, length = dependencies.length; i < length; i++) {
                    this.dependencies.remove(dependencies[i]);
                }
                this._preventDependencyRefresh = false;
                this.dependencies.sync();
            },
            _removeTaskAssignments: function (task) {
                var dataSource = this.assignments.dataSource;
                var assignments = dataSource.view();
                var filter = {
                    field: this.assignments.dataTaskIdField,
                    operator: 'eq',
                    value: task.get('id')
                };
                assignments = new Query(assignments).filter(filter).toArray();
                this._preventRefresh = true;
                for (var i = 0, length = assignments.length; i < length; i++) {
                    dataSource.remove(assignments[i]);
                }
                this._preventRefresh = false;
                dataSource.sync();
            },
            _removeTask: function (task) {
                var dependencies = this.dependencies.dependencies(task.id);
                if (!this.trigger('remove', {
                        task: task,
                        dependencies: dependencies
                    })) {
                    this._removeTaskDependencies(task, dependencies);
                    this._removeTaskAssignments(task);
                    this._preventRefresh = true;
                    if (this.dataSource.remove(task)) {
                        this._syncDataSource();
                    }
                    this._preventRefresh = false;
                }
            },
            _removeDependency: function (dependency) {
                if (!this.trigger('remove', {
                        task: null,
                        dependencies: [dependency]
                    })) {
                    if (this.dependencies.remove(dependency)) {
                        this.dependencies.sync();
                    }
                }
            },
            _removeAssignment: function (assignment) {
                this.assignments.dataSource.remove(assignment);
            },
            _taskConfirm: function (callback, task) {
                var messages = this.options.messages;
                this._confirm(callback, {
                    model: task,
                    text: messages.deleteTaskConfirmation,
                    title: messages.deleteTaskWindowTitle
                });
            },
            _dependencyConfirm: function (callback, dependency) {
                var messages = this.options.messages;
                this._confirm(callback, {
                    model: dependency,
                    text: messages.deleteDependencyConfirmation,
                    title: messages.deleteDependencyWindowTitle
                });
            },
            _confirm: function (callback, options) {
                var editable = this.options.editable;
                var messages;
                var buttons;
                if (editable === true || editable.confirmation !== false) {
                    messages = this.options.messages;
                    buttons = [
                        {
                            name: 'delete',
                            text: messages.destroy,
                            className: Gantt.styles.primary,
                            click: function () {
                                callback();
                            }
                        },
                        {
                            name: 'cancel',
                            text: messages.cancel,
                            click: function () {
                                callback(true);
                            }
                        }
                    ];
                    this.showDialog(extend(true, {}, options, { buttons: buttons }));
                } else {
                    callback();
                }
            },
            showDialog: function (options) {
                this._editor.showDialog(options);
            },
            refresh: function () {
                if (this._preventRefresh || this.list.editable) {
                    return;
                }
                this._progress(false);
                var dataSource = this.dataSource;
                var taskTree = dataSource.taskTree();
                var scrollToUid = this._scrollToUid;
                var current;
                var cachedUid;
                var cachedIndex = -1;
                if (this.current) {
                    cachedUid = this.current.closest('tr').attr(kendo.attr('uid'));
                    cachedIndex = this.current.index();
                }
                if (this.trigger('dataBinding')) {
                    return;
                }
                if (this.resources.dataSource.data().length !== 0) {
                    this._assignResources(taskTree);
                }
                if (this._editor) {
                    this._editor.close();
                }
                this.clearSelection();
                this.list._render(taskTree);
                this.timeline._render(taskTree);
                this.timeline._renderDependencies(this.dependencies.view());
                if (scrollToUid) {
                    this._scrollTo(scrollToUid);
                    this.select(selector(scrollToUid));
                }
                if ((scrollToUid || cachedUid) && cachedIndex >= 0) {
                    current = this.list.content.find('tr' + selector(scrollToUid || cachedUid) + ' > td:eq(' + cachedIndex + ')');
                    this._current(current);
                }
                this._scrollToUid = null;
                this.trigger('dataBound');
            },
            refreshDependencies: function () {
                if (this._preventDependencyRefresh) {
                    return;
                }
                if (this.trigger('dataBinding')) {
                    return;
                }
                this.timeline._renderDependencies(this.dependencies.view());
                this.trigger('dataBound');
            },
            _assignResources: function (taskTree) {
                var resources = this.resources;
                var assignments = this.assignments;
                var groupAssigments = function () {
                    var data = assignments.dataSource.view();
                    var group = { field: assignments.dataTaskIdField };
                    data = new Query(data).group(group).toArray();
                    return data;
                };
                var assigments = groupAssigments();
                var applyTaskResource = function (task, action) {
                    var taskId = task.get('id');
                    kendo.setter(resources.field)(task, new ObservableArray([]));
                    for (var i = 0, length = assigments.length; i < length; i++) {
                        if (assigments[i].value === taskId) {
                            action(task, assigments[i].items);
                        }
                    }
                };
                var wrapTask = function (task, items) {
                    for (var j = 0, length = items.length; j < length; j++) {
                        var item = items[j];
                        var resource = resources.dataSource.get(item.get(assignments.dataResourceIdField));
                        var resourceValue = item.get(assignments.dataValueField);
                        var resourcedId = item.get(assignments.dataResourceIdField);
                        var valueFormat = resource.get(resources.dataFormatField) || PERCENTAGE_FORMAT;
                        var formatedValue = kendo.toString(resourceValue, valueFormat);
                        task[resources.field].push(new ObservableObject({
                            id: resourcedId,
                            name: resource.get(resources.dataTextField),
                            color: resource.get(resources.dataColorField),
                            value: resourceValue,
                            formatedValue: formatedValue
                        }));
                    }
                };
                for (var i = 0, length = taskTree.length; i < length; i++) {
                    applyTaskResource(taskTree[i], wrapTask);
                }
            },
            _wrapResourceData: function (id) {
                var that = this;
                var result = [];
                var resource;
                var resources = this.resources.dataSource.view();
                var assignments = this.assignments.dataSource.view();
                var taskAssignments = new Query(assignments).filter({
                    field: that.assignments.dataTaskIdField,
                    operator: 'eq',
                    value: id
                }).toArray();
                var valuePerResource = function (id) {
                    var resourceValue = null;
                    new Query(taskAssignments).filter({
                        field: that.assignments.dataResourceIdField,
                        operator: 'eq',
                        value: id
                    }).select(function (assignment) {
                        resourceValue += assignment.get(that.assignments.dataValueField);
                    });
                    return resourceValue;
                };
                for (var i = 0, length = resources.length; i < length; i++) {
                    resource = resources[i];
                    result.push({
                        id: resource.get('id'),
                        name: resource.get(that.resources.dataTextField),
                        format: resource.get(that.resources.dataFormatField) || PERCENTAGE_FORMAT,
                        value: valuePerResource(resource.id)
                    });
                }
                return result;
            },
            _syncDataSource: function () {
                this._preventRefresh = false;
                this._requestStart();
                this.dataSource.sync();
            },
            _requestStart: function () {
                this._progress(true);
            },
            _error: function () {
                this._progress(false);
            },
            _progress: function (toggle) {
                kendo.ui.progress(this.element, toggle);
            },
            _resizable: function () {
                var that = this;
                var wrapper = this.wrapper;
                var ganttStyles = Gantt.styles;
                var contentSelector = DOT + ganttStyles.gridContent;
                var treeListWrapper = wrapper.find(DOT + ganttStyles.list);
                var timelineWrapper = wrapper.find(DOT + ganttStyles.timeline);
                var treeListWidth;
                var timelineWidth;
                var timelineScroll;
                this._resizeDraggable = wrapper.find(DOT + ganttStyles.splitBar).height(treeListWrapper.height()).hover(function () {
                    $(this).addClass(ganttStyles.splitBarHover);
                }, function () {
                    $(this).removeClass(ganttStyles.splitBarHover);
                }).end().kendoResizable({
                    orientation: 'horizontal',
                    handle: DOT + ganttStyles.splitBar,
                    'start': function () {
                        treeListWidth = treeListWrapper.width();
                        timelineWidth = timelineWrapper.width();
                        timelineScroll = timelineWrapper.find(contentSelector).scrollLeft();
                    },
                    'resize': function (e) {
                        var delta = e.x.initialDelta;
                        if (kendo.support.isRtl(wrapper)) {
                            delta *= -1;
                        }
                        if (treeListWidth + delta < 0 || timelineWidth - delta < 0) {
                            return;
                        }
                        treeListWrapper.width(treeListWidth + delta);
                        timelineWrapper.width(timelineWidth - delta);
                        timelineWrapper.find(contentSelector).scrollLeft(timelineScroll + delta);
                        that.timeline.view()._renderCurrentTime();
                    }
                }).data('kendoResizable');
            },
            _scrollable: function () {
                var that = this;
                var ganttStyles = Gantt.styles;
                var contentSelector = DOT + ganttStyles.gridContent;
                var headerSelector = DOT + ganttStyles.gridHeaderWrap;
                var timelineHeader = this.timeline.element.find(headerSelector);
                var timelineContent = this.timeline.element.find(contentSelector);
                var treeListHeader = this.list.element.find(headerSelector);
                var treeListContent = this.list.element.find(contentSelector);
                if (mobileOS) {
                    treeListContent.css('overflow-y', 'auto');
                }
                timelineContent.on('scroll', function () {
                    that.scrollTop = this.scrollTop;
                    timelineHeader.scrollLeft(this.scrollLeft);
                    treeListContent.scrollTop(this.scrollTop);
                });
                treeListContent.on('scroll', function () {
                    treeListHeader.scrollLeft(this.scrollLeft);
                }).on('DOMMouseScroll' + NS + ' mousewheel' + NS, function (e) {
                    var scrollTop = timelineContent.scrollTop();
                    var delta = kendo.wheelDeltaY(e);
                    if (delta) {
                        e.preventDefault();
                        $(e.currentTarget).one('wheel' + NS, false);
                        timelineContent.scrollTop(scrollTop + -delta);
                    }
                });
            },
            _navigatable: function () {
                var that = this;
                var navigatable = this.options.navigatable;
                var editable = this.options.editable;
                var headerTable = this.list.header.find('table');
                var contentTable = this.list.content.find('table');
                var ganttStyles = Gantt.styles;
                var isRtl = kendo.support.isRtl(this.wrapper);
                var timelineContent = this.timeline.element.find(DOT + ganttStyles.gridContent);
                var tables = headerTable.add(contentTable);
                var attr = selector();
                var cellIndex;
                var expandState = {
                    collapse: false,
                    expand: true
                };
                var scroll = function (reverse) {
                    var width = that.timeline.view()._timeSlots()[0].offsetWidth;
                    timelineContent.scrollLeft(timelineContent.scrollLeft() + (reverse ? -width : width));
                };
                var moveVertical = function (method) {
                    var parent = that.current.parent('tr' + selector());
                    var index = that.current.index();
                    var subling = parent[method]();
                    if (that.select().length !== 0) {
                        that.clearSelection();
                    }
                    if (subling.length !== 0) {
                        that._current(subling.children('td:eq(' + index + ')'));
                        that._scrollTo(that.current);
                    } else {
                        if (that.current.is('td') && method == 'prev') {
                            focusTable(headerTable);
                        } else if (that.current.is('th') && method == 'next') {
                            focusTable(contentTable);
                        }
                    }
                };
                var moveHorizontal = function (method) {
                    var subling = that.current[method]();
                    if (subling.length !== 0) {
                        that._current(subling);
                        cellIndex = that.current.index();
                    }
                };
                var toggleExpandedState = function (value) {
                    var model = that.dataItem(that.current);
                    if (model.summary && model.expanded !== value) {
                        model.set('expanded', value);
                    }
                };
                var deleteAction = function () {
                    if (!that.options.editable || that.list.editable) {
                        return;
                    }
                    var selectedTask = that.select();
                    var uid = kendo.attr('uid');
                    if (selectedTask.length) {
                        that.removeTask(selectedTask.attr(uid));
                    }
                };
                $(this.wrapper).on('mousedown' + NS, 'tr' + attr + ', div' + attr + ':not(' + DOT + ganttStyles.line + ')', function (e) {
                    var currentTarget = $(e.currentTarget);
                    var isInput = $(e.target).is(':button,a,:input,a>.k-icon,textarea,span.k-icon,span.k-link,.k-input,.k-multiselect-wrap');
                    var current;
                    if (e.ctrlKey) {
                        return;
                    }
                    if (navigatable) {
                        if (currentTarget.is('tr')) {
                            current = $(e.target).closest('td');
                        } else {
                            current = that.list.content.find('tr' + selector(currentTarget.attr(kendo.attr('uid'))) + ' > td:first');
                        }
                        that._current(current);
                    }
                    if ((navigatable || editable) && !isInput) {
                        that._focusTimeout = setTimeout(function () {
                            focusTable(that.list.content.find('table'), true);
                        }, 2);
                    }
                });
                if (navigatable !== true) {
                    contentTable.on('keydown' + NS, function (e) {
                        if (e.keyCode == keys.DELETE) {
                            deleteAction();
                        }
                    });
                    return;
                }
                tables.on('focus' + NS, function () {
                    var selector = this === contentTable.get(0) ? 'td' : 'th';
                    var selection = that.select();
                    var current = that.current || $(selection.length ? selection : this).find(selector + ':eq(' + (cellIndex || 0) + ')');
                    that._current(current);
                }).on('blur' + NS, function () {
                    that._current();
                    if (this == headerTable) {
                        $(this).attr(TABINDEX, -1);
                    }
                }).on('keydown' + NS, function (e) {
                    var key = e.keyCode;
                    var isCell;
                    if (!that.current) {
                        return;
                    }
                    isCell = that.current.is('td');
                    switch (key) {
                    case keys.RIGHT:
                        e.preventDefault();
                        if (e.altKey) {
                            scroll();
                        } else if (e.ctrlKey) {
                            toggleExpandedState(isRtl ? expandState.collapse : expandState.expand);
                        } else {
                            moveHorizontal(isRtl ? 'prev' : 'next');
                        }
                        break;
                    case keys.LEFT:
                        e.preventDefault();
                        if (e.altKey) {
                            scroll(true);
                        } else if (e.ctrlKey) {
                            toggleExpandedState(isRtl ? expandState.expand : expandState.collapse);
                        } else {
                            moveHorizontal(isRtl ? 'next' : 'prev');
                        }
                        break;
                    case keys.UP:
                        e.preventDefault();
                        moveVertical('prev');
                        break;
                    case keys.DOWN:
                        e.preventDefault();
                        moveVertical('next');
                        break;
                    case keys.SPACEBAR:
                        e.preventDefault();
                        if (isCell) {
                            that.select(that.current.closest('tr'));
                        }
                        break;
                    case keys.ENTER:
                        e.preventDefault();
                        if (isCell) {
                            if (that.options.editable) {
                                that._cachedCurrent = that.current;
                                that.list._startEditHandler(that.current);
                                $(this).one('keyup', function (e) {
                                    e.stopPropagation();
                                });
                            }
                        } else {
                            that.current.children('a.k-link').click();
                        }
                        break;
                    case keys.ESC:
                        e.stopPropagation();
                        break;
                    case keys.DELETE:
                        if (isCell) {
                            deleteAction();
                        }
                        break;
                    default:
                        if (key >= 49 && key <= 57) {
                            that.view(that.timeline._viewByIndex(key - 49));
                        }
                        break;
                    }
                });
            },
            _current: function (element) {
                var ganttStyles = Gantt.styles;
                var activeElement;
                if (this.current && this.current.length) {
                    this.current.removeClass(ganttStyles.focused).removeAttr('id');
                }
                if (element && element.length) {
                    this.current = element.addClass(ganttStyles.focused).attr('id', ACTIVE_CELL);
                    activeElement = $(kendo._activeElement());
                    if (activeElement.is('table') && this.wrapper.find(activeElement).length > 0) {
                        activeElement.removeAttr(ARIA_DESCENDANT).attr(ARIA_DESCENDANT, ACTIVE_CELL);
                    }
                } else {
                    this.current = null;
                }
            },
            _dataBind: function () {
                var that = this;
                if (that.options.autoBind) {
                    this._preventRefresh = true;
                    this._preventDependencyRefresh = true;
                    var promises = $.map([
                        this.dataSource,
                        this.dependencies,
                        this.resources.dataSource,
                        this.assignments.dataSource
                    ], function (dataSource) {
                        return dataSource.fetch();
                    });
                    $.when.apply(null, promises).done(function () {
                        that._preventRefresh = false;
                        that._preventDependencyRefresh = false;
                        that.refresh();
                    });
                }
            },
            _resize: function () {
                this._adjustDimensions();
                this.timeline.view()._adjustHeight();
                this.timeline.view()._renderCurrentTime();
                this.list._adjustHeight();
            }
        });
        if (kendo.PDFMixin) {
            kendo.PDFMixin.extend(Gantt.fn);
            Gantt.fn._drawPDF = function () {
                var ganttStyles = Gantt.styles;
                var listClass = '.' + ganttStyles.list;
                var listWidth = this.wrapper.find(listClass).width();
                var content = this.wrapper.clone();
                content.find(listClass).css('width', listWidth);
                return this._drawPDFShadow({ content: content }, { avoidLinks: this.options.pdf.avoidLinks });
            };
        }
        kendo.ui.plugin(Gantt);
        extend(true, Gantt, { styles: ganttStyles });
    }(window.kendo.jQuery));
    return window.kendo;
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));