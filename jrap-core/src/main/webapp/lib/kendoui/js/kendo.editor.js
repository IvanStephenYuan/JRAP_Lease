/** 
 * Kendo UI v2016.3.1118 (http://www.telerik.com/kendo-ui)                                                                                                                                              
 * Copyright 2016 Telerik AD. All rights reserved.                                                                                                                                                      
 *                                                                                                                                                                                                      
 * Kendo UI commercial licenses may be obtained at                                                                                                                                                      
 * http://www.telerik.com/purchase/license-agreement/kendo-ui-complete                                                                                                                                  
 * If you do not own a commercial license, this file shall be governed by the trial license terms.
*/
(function (f, define) {
    define('kendo.color', ['kendo.core'], f);
}(function () {
    var __meta__ = {
        id: 'color',
        name: 'Color utils',
        category: 'framework',
        advanced: true,
        description: 'Color utilities used across components',
        depends: ['core']
    };
    (function ($, parseFloat, parseInt) {
        var Color = function (value) {
            var color = this, formats = Color.formats, re, processor, parts, i, channels;
            if (arguments.length === 1) {
                value = color.resolveColor(value);
                for (i = 0; i < formats.length; i++) {
                    re = formats[i].re;
                    processor = formats[i].process;
                    parts = re.exec(value);
                    if (parts) {
                        channels = processor(parts);
                        color.r = channels[0];
                        color.g = channels[1];
                        color.b = channels[2];
                    }
                }
            } else {
                color.r = arguments[0];
                color.g = arguments[1];
                color.b = arguments[2];
            }
            color.r = color.normalizeByte(color.r);
            color.g = color.normalizeByte(color.g);
            color.b = color.normalizeByte(color.b);
        };
        Color.prototype = {
            toHex: function () {
                var color = this, pad = color.padDigit, r = color.r.toString(16), g = color.g.toString(16), b = color.b.toString(16);
                return '#' + pad(r) + pad(g) + pad(b);
            },
            resolveColor: function (value) {
                value = value || 'black';
                if (value.charAt(0) == '#') {
                    value = value.substr(1, 6);
                }
                value = value.replace(/ /g, '');
                value = value.toLowerCase();
                value = Color.namedColors[value] || value;
                return value;
            },
            normalizeByte: function (value) {
                return value < 0 || isNaN(value) ? 0 : value > 255 ? 255 : value;
            },
            padDigit: function (value) {
                return value.length === 1 ? '0' + value : value;
            },
            brightness: function (value) {
                var color = this, round = Math.round;
                color.r = round(color.normalizeByte(color.r * value));
                color.g = round(color.normalizeByte(color.g * value));
                color.b = round(color.normalizeByte(color.b * value));
                return color;
            },
            percBrightness: function () {
                var color = this;
                return Math.sqrt(0.241 * color.r * color.r + 0.691 * color.g * color.g + 0.068 * color.b * color.b);
            }
        };
        Color.formats = [
            {
                re: /^rgb\((\d{1,3}),\s*(\d{1,3}),\s*(\d{1,3})\)$/,
                process: function (parts) {
                    return [
                        parseInt(parts[1], 10),
                        parseInt(parts[2], 10),
                        parseInt(parts[3], 10)
                    ];
                }
            },
            {
                re: /^(\w{2})(\w{2})(\w{2})$/,
                process: function (parts) {
                    return [
                        parseInt(parts[1], 16),
                        parseInt(parts[2], 16),
                        parseInt(parts[3], 16)
                    ];
                }
            },
            {
                re: /^(\w{1})(\w{1})(\w{1})$/,
                process: function (parts) {
                    return [
                        parseInt(parts[1] + parts[1], 16),
                        parseInt(parts[2] + parts[2], 16),
                        parseInt(parts[3] + parts[3], 16)
                    ];
                }
            }
        ];
        Color.namedColors = {
            aliceblue: 'f0f8ff',
            antiquewhite: 'faebd7',
            aqua: '00ffff',
            aquamarine: '7fffd4',
            azure: 'f0ffff',
            beige: 'f5f5dc',
            bisque: 'ffe4c4',
            black: '000000',
            blanchedalmond: 'ffebcd',
            blue: '0000ff',
            blueviolet: '8a2be2',
            brown: 'a52a2a',
            burlywood: 'deb887',
            cadetblue: '5f9ea0',
            chartreuse: '7fff00',
            chocolate: 'd2691e',
            coral: 'ff7f50',
            cornflowerblue: '6495ed',
            cornsilk: 'fff8dc',
            crimson: 'dc143c',
            cyan: '00ffff',
            darkblue: '00008b',
            darkcyan: '008b8b',
            darkgoldenrod: 'b8860b',
            darkgray: 'a9a9a9',
            darkgrey: 'a9a9a9',
            darkgreen: '006400',
            darkkhaki: 'bdb76b',
            darkmagenta: '8b008b',
            darkolivegreen: '556b2f',
            darkorange: 'ff8c00',
            darkorchid: '9932cc',
            darkred: '8b0000',
            darksalmon: 'e9967a',
            darkseagreen: '8fbc8f',
            darkslateblue: '483d8b',
            darkslategray: '2f4f4f',
            darkslategrey: '2f4f4f',
            darkturquoise: '00ced1',
            darkviolet: '9400d3',
            deeppink: 'ff1493',
            deepskyblue: '00bfff',
            dimgray: '696969',
            dimgrey: '696969',
            dodgerblue: '1e90ff',
            firebrick: 'b22222',
            floralwhite: 'fffaf0',
            forestgreen: '228b22',
            fuchsia: 'ff00ff',
            gainsboro: 'dcdcdc',
            ghostwhite: 'f8f8ff',
            gold: 'ffd700',
            goldenrod: 'daa520',
            gray: '808080',
            grey: '808080',
            green: '008000',
            greenyellow: 'adff2f',
            honeydew: 'f0fff0',
            hotpink: 'ff69b4',
            indianred: 'cd5c5c',
            indigo: '4b0082',
            ivory: 'fffff0',
            khaki: 'f0e68c',
            lavender: 'e6e6fa',
            lavenderblush: 'fff0f5',
            lawngreen: '7cfc00',
            lemonchiffon: 'fffacd',
            lightblue: 'add8e6',
            lightcoral: 'f08080',
            lightcyan: 'e0ffff',
            lightgoldenrodyellow: 'fafad2',
            lightgray: 'd3d3d3',
            lightgrey: 'd3d3d3',
            lightgreen: '90ee90',
            lightpink: 'ffb6c1',
            lightsalmon: 'ffa07a',
            lightseagreen: '20b2aa',
            lightskyblue: '87cefa',
            lightslategray: '778899',
            lightslategrey: '778899',
            lightsteelblue: 'b0c4de',
            lightyellow: 'ffffe0',
            lime: '00ff00',
            limegreen: '32cd32',
            linen: 'faf0e6',
            magenta: 'ff00ff',
            maroon: '800000',
            mediumaquamarine: '66cdaa',
            mediumblue: '0000cd',
            mediumorchid: 'ba55d3',
            mediumpurple: '9370d8',
            mediumseagreen: '3cb371',
            mediumslateblue: '7b68ee',
            mediumspringgreen: '00fa9a',
            mediumturquoise: '48d1cc',
            mediumvioletred: 'c71585',
            midnightblue: '191970',
            mintcream: 'f5fffa',
            mistyrose: 'ffe4e1',
            moccasin: 'ffe4b5',
            navajowhite: 'ffdead',
            navy: '000080',
            oldlace: 'fdf5e6',
            olive: '808000',
            olivedrab: '6b8e23',
            orange: 'ffa500',
            orangered: 'ff4500',
            orchid: 'da70d6',
            palegoldenrod: 'eee8aa',
            palegreen: '98fb98',
            paleturquoise: 'afeeee',
            palevioletred: 'd87093',
            papayawhip: 'ffefd5',
            peachpuff: 'ffdab9',
            peru: 'cd853f',
            pink: 'ffc0cb',
            plum: 'dda0dd',
            powderblue: 'b0e0e6',
            purple: '800080',
            red: 'ff0000',
            rosybrown: 'bc8f8f',
            royalblue: '4169e1',
            saddlebrown: '8b4513',
            salmon: 'fa8072',
            sandybrown: 'f4a460',
            seagreen: '2e8b57',
            seashell: 'fff5ee',
            sienna: 'a0522d',
            silver: 'c0c0c0',
            skyblue: '87ceeb',
            slateblue: '6a5acd',
            slategray: '708090',
            slategrey: '708090',
            snow: 'fffafa',
            springgreen: '00ff7f',
            steelblue: '4682b4',
            tan: 'd2b48c',
            teal: '008080',
            thistle: 'd8bfd8',
            tomato: 'ff6347',
            turquoise: '40e0d0',
            violet: 'ee82ee',
            wheat: 'f5deb3',
            white: 'ffffff',
            whitesmoke: 'f5f5f5',
            yellow: 'ffff00',
            yellowgreen: '9acd32'
        };
        var namedColorRegexp = ['transparent'];
        for (var i in Color.namedColors) {
            if (Color.namedColors.hasOwnProperty(i)) {
                namedColorRegexp.push(i);
            }
        }
        namedColorRegexp = new RegExp('^(' + namedColorRegexp.join('|') + ')(\\W|$)', 'i');
        function parseColor(color, nothrow) {
            var m, ret;
            if (color == null || color == 'none') {
                return null;
            }
            if (color instanceof _Color) {
                return color;
            }
            color = color.toLowerCase();
            if (m = namedColorRegexp.exec(color)) {
                if (m[1] == 'transparent') {
                    color = new _RGB(1, 1, 1, 0);
                } else {
                    color = parseColor(Color.namedColors[m[1]], nothrow);
                }
                color.match = [m[1]];
                return color;
            }
            if (m = /^#?([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})\b/i.exec(color)) {
                ret = new _Bytes(parseInt(m[1], 16), parseInt(m[2], 16), parseInt(m[3], 16), 1);
            } else if (m = /^#?([0-9a-f])([0-9a-f])([0-9a-f])\b/i.exec(color)) {
                ret = new _Bytes(parseInt(m[1] + m[1], 16), parseInt(m[2] + m[2], 16), parseInt(m[3] + m[3], 16), 1);
            } else if (m = /^rgb\(\s*([0-9]+)\s*,\s*([0-9]+)\s*,\s*([0-9]+)\s*\)/.exec(color)) {
                ret = new _Bytes(parseInt(m[1], 10), parseInt(m[2], 10), parseInt(m[3], 10), 1);
            } else if (m = /^rgba\(\s*([0-9]+)\s*,\s*([0-9]+)\s*,\s*([0-9]+)\s*,\s*([0-9.]+)\s*\)/.exec(color)) {
                ret = new _Bytes(parseInt(m[1], 10), parseInt(m[2], 10), parseInt(m[3], 10), parseFloat(m[4]));
            } else if (m = /^rgb\(\s*([0-9]*\.?[0-9]+)%\s*,\s*([0-9]*\.?[0-9]+)%\s*,\s*([0-9]*\.?[0-9]+)%\s*\)/.exec(color)) {
                ret = new _RGB(parseFloat(m[1]) / 100, parseFloat(m[2]) / 100, parseFloat(m[3]) / 100, 1);
            } else if (m = /^rgba\(\s*([0-9]*\.?[0-9]+)%\s*,\s*([0-9]*\.?[0-9]+)%\s*,\s*([0-9]*\.?[0-9]+)%\s*,\s*([0-9.]+)\s*\)/.exec(color)) {
                ret = new _RGB(parseFloat(m[1]) / 100, parseFloat(m[2]) / 100, parseFloat(m[3]) / 100, parseFloat(m[4]));
            }
            if (ret) {
                ret.match = m;
            } else if (!nothrow) {
                throw new Error('Cannot parse color: ' + color);
            }
            return ret;
        }
        function hex(n, width, pad) {
            if (!pad) {
                pad = '0';
            }
            n = n.toString(16);
            while (width > n.length) {
                n = '0' + n;
            }
            return n;
        }
        function hue2rgb(p, q, t) {
            if (t < 0) {
                t += 1;
            }
            if (t > 1) {
                t -= 1;
            }
            if (t < 1 / 6) {
                return p + (q - p) * 6 * t;
            }
            if (t < 1 / 2) {
                return q;
            }
            if (t < 2 / 3) {
                return p + (q - p) * (2 / 3 - t) * 6;
            }
            return p;
        }
        var _Color = kendo.Class.extend({
            toHSV: function () {
                return this;
            },
            toRGB: function () {
                return this;
            },
            toHex: function () {
                return this.toBytes().toHex();
            },
            toBytes: function () {
                return this;
            },
            toCss: function () {
                return '#' + this.toHex();
            },
            toCssRgba: function () {
                var rgb = this.toBytes();
                return 'rgba(' + rgb.r + ', ' + rgb.g + ', ' + rgb.b + ', ' + parseFloat((+this.a).toFixed(3)) + ')';
            },
            toDisplay: function () {
                if (kendo.support.browser.msie && kendo.support.browser.version < 9) {
                    return this.toCss();
                }
                return this.toCssRgba();
            },
            equals: function (c) {
                return c === this || c !== null && this.toCssRgba() == parseColor(c).toCssRgba();
            },
            diff: function (c2) {
                if (c2 == null) {
                    return NaN;
                }
                var c1 = this.toBytes();
                c2 = c2.toBytes();
                return Math.sqrt(Math.pow((c1.r - c2.r) * 0.3, 2) + Math.pow((c1.g - c2.g) * 0.59, 2) + Math.pow((c1.b - c2.b) * 0.11, 2));
            },
            clone: function () {
                var c = this.toBytes();
                if (c === this) {
                    c = new _Bytes(c.r, c.g, c.b, c.a);
                }
                return c;
            }
        });
        var _RGB = _Color.extend({
            init: function (r, g, b, a) {
                this.r = r;
                this.g = g;
                this.b = b;
                this.a = a;
            },
            toHSV: function () {
                var min, max, delta, h, s, v;
                var r = this.r, g = this.g, b = this.b;
                min = Math.min(r, g, b);
                max = Math.max(r, g, b);
                v = max;
                delta = max - min;
                if (delta === 0) {
                    return new _HSV(0, 0, v, this.a);
                }
                if (max !== 0) {
                    s = delta / max;
                    if (r == max) {
                        h = (g - b) / delta;
                    } else if (g == max) {
                        h = 2 + (b - r) / delta;
                    } else {
                        h = 4 + (r - g) / delta;
                    }
                    h *= 60;
                    if (h < 0) {
                        h += 360;
                    }
                } else {
                    s = 0;
                    h = -1;
                }
                return new _HSV(h, s, v, this.a);
            },
            toHSL: function () {
                var r = this.r, g = this.g, b = this.b;
                var max = Math.max(r, g, b), min = Math.min(r, g, b);
                var h, s, l = (max + min) / 2;
                if (max == min) {
                    h = s = 0;
                } else {
                    var d = max - min;
                    s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
                    switch (max) {
                    case r:
                        h = (g - b) / d + (g < b ? 6 : 0);
                        break;
                    case g:
                        h = (b - r) / d + 2;
                        break;
                    case b:
                        h = (r - g) / d + 4;
                        break;
                    }
                    h *= 60;
                    s *= 100;
                    l *= 100;
                }
                return new _HSL(h, s, l, this.a);
            },
            toBytes: function () {
                return new _Bytes(this.r * 255, this.g * 255, this.b * 255, this.a);
            }
        });
        var _Bytes = _RGB.extend({
            init: function (r, g, b, a) {
                this.r = Math.round(r);
                this.g = Math.round(g);
                this.b = Math.round(b);
                this.a = a;
            },
            toRGB: function () {
                return new _RGB(this.r / 255, this.g / 255, this.b / 255, this.a);
            },
            toHSV: function () {
                return this.toRGB().toHSV();
            },
            toHSL: function () {
                return this.toRGB().toHSL();
            },
            toHex: function () {
                return hex(this.r, 2) + hex(this.g, 2) + hex(this.b, 2);
            },
            toBytes: function () {
                return this;
            }
        });
        var _HSV = _Color.extend({
            init: function (h, s, v, a) {
                this.h = h;
                this.s = s;
                this.v = v;
                this.a = a;
            },
            toRGB: function () {
                var h = this.h, s = this.s, v = this.v;
                var i, r, g, b, f, p, q, t;
                if (s === 0) {
                    r = g = b = v;
                } else {
                    h /= 60;
                    i = Math.floor(h);
                    f = h - i;
                    p = v * (1 - s);
                    q = v * (1 - s * f);
                    t = v * (1 - s * (1 - f));
                    switch (i) {
                    case 0:
                        r = v;
                        g = t;
                        b = p;
                        break;
                    case 1:
                        r = q;
                        g = v;
                        b = p;
                        break;
                    case 2:
                        r = p;
                        g = v;
                        b = t;
                        break;
                    case 3:
                        r = p;
                        g = q;
                        b = v;
                        break;
                    case 4:
                        r = t;
                        g = p;
                        b = v;
                        break;
                    default:
                        r = v;
                        g = p;
                        b = q;
                        break;
                    }
                }
                return new _RGB(r, g, b, this.a);
            },
            toHSL: function () {
                return this.toRGB().toHSL();
            },
            toBytes: function () {
                return this.toRGB().toBytes();
            }
        });
        var _HSL = _Color.extend({
            init: function (h, s, l, a) {
                this.h = h;
                this.s = s;
                this.l = l;
                this.a = a;
            },
            toRGB: function () {
                var h = this.h, s = this.s, l = this.l;
                var r, g, b;
                if (s === 0) {
                    r = g = b = l;
                } else {
                    h /= 360;
                    s /= 100;
                    l /= 100;
                    var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
                    var p = 2 * l - q;
                    r = hue2rgb(p, q, h + 1 / 3);
                    g = hue2rgb(p, q, h);
                    b = hue2rgb(p, q, h - 1 / 3);
                }
                return new _RGB(r, g, b, this.a);
            },
            toHSV: function () {
                return this.toRGB().toHSV();
            },
            toBytes: function () {
                return this.toRGB().toBytes();
            }
        });
        Color.fromBytes = function (r, g, b, a) {
            return new _Bytes(r, g, b, a != null ? a : 1);
        };
        Color.fromRGB = function (r, g, b, a) {
            return new _RGB(r, g, b, a != null ? a : 1);
        };
        Color.fromHSV = function (h, s, v, a) {
            return new _HSV(h, s, v, a != null ? a : 1);
        };
        Color.fromHSL = function (h, s, l, a) {
            return new _HSL(h, s, l, a != null ? a : 1);
        };
        kendo.Color = Color;
        kendo.parseColor = parseColor;
    }(window.kendo.jQuery, parseFloat, parseInt));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
/** 
 * Kendo UI v2016.3.1118 (http://www.telerik.com/kendo-ui)                                                                                                                                              
 * Copyright 2016 Telerik AD. All rights reserved.                                                                                                                                                      
 *                                                                                                                                                                                                      
 * Kendo UI commercial licenses may be obtained at                                                                                                                                                      
 * http://www.telerik.com/purchase/license-agreement/kendo-ui-complete                                                                                                                                  
 * If you do not own a commercial license, this file shall be governed by the trial license terms.                                                                                                      
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       
                                                                                                                                                                                                       

*/
(function (f, define) {
    define('kendo.colorpicker', [
        'kendo.core',
        'kendo.color',
        'kendo.popup',
        'kendo.slider',
        'kendo.userevents'
    ], f);
}(function () {
    var __meta__ = {
        id: 'colorpicker',
        name: 'Color tools',
        category: 'web',
        description: 'Color selection widgets',
        depends: [
            'core',
            'color',
            'popup',
            'slider',
            'userevents'
        ]
    };
    (function ($, parseInt, undefined) {
        var kendo = window.kendo, ui = kendo.ui, Widget = ui.Widget, parseColor = kendo.parseColor, Color = kendo.Color, KEYS = kendo.keys, BACKGROUNDCOLOR = 'background-color', ITEMSELECTEDCLASS = 'k-state-selected', SIMPLEPALETTE = '000000,7f7f7f,880015,ed1c24,ff7f27,fff200,22b14c,00a2e8,3f48cc,a349a4,ffffff,c3c3c3,b97a57,ffaec9,ffc90e,efe4b0,b5e61d,99d9ea,7092be,c8bfe7', WEBPALETTE = 'FFFFFF,FFCCFF,FF99FF,FF66FF,FF33FF,FF00FF,CCFFFF,CCCCFF,CC99FF,CC66FF,CC33FF,CC00FF,99FFFF,99CCFF,9999FF,9966FF,9933FF,9900FF,FFFFCC,FFCCCC,FF99CC,FF66CC,FF33CC,FF00CC,CCFFCC,CCCCCC,CC99CC,CC66CC,CC33CC,CC00CC,99FFCC,99CCCC,9999CC,9966CC,9933CC,9900CC,FFFF99,FFCC99,FF9999,FF6699,FF3399,FF0099,CCFF99,CCCC99,CC9999,CC6699,CC3399,CC0099,99FF99,99CC99,999999,996699,993399,990099,FFFF66,FFCC66,FF9966,FF6666,FF3366,FF0066,CCFF66,CCCC66,CC9966,CC6666,CC3366,CC0066,99FF66,99CC66,999966,996666,993366,990066,FFFF33,FFCC33,FF9933,FF6633,FF3333,FF0033,CCFF33,CCCC33,CC9933,CC6633,CC3333,CC0033,99FF33,99CC33,999933,996633,993333,990033,FFFF00,FFCC00,FF9900,FF6600,FF3300,FF0000,CCFF00,CCCC00,CC9900,CC6600,CC3300,CC0000,99FF00,99CC00,999900,996600,993300,990000,66FFFF,66CCFF,6699FF,6666FF,6633FF,6600FF,33FFFF,33CCFF,3399FF,3366FF,3333FF,3300FF,00FFFF,00CCFF,0099FF,0066FF,0033FF,0000FF,66FFCC,66CCCC,6699CC,6666CC,6633CC,6600CC,33FFCC,33CCCC,3399CC,3366CC,3333CC,3300CC,00FFCC,00CCCC,0099CC,0066CC,0033CC,0000CC,66FF99,66CC99,669999,666699,663399,660099,33FF99,33CC99,339999,336699,333399,330099,00FF99,00CC99,009999,006699,003399,000099,66FF66,66CC66,669966,666666,663366,660066,33FF66,33CC66,339966,336666,333366,330066,00FF66,00CC66,009966,006666,003366,000066,66FF33,66CC33,669933,666633,663333,660033,33FF33,33CC33,339933,336633,333333,330033,00FF33,00CC33,009933,006633,003333,000033,66FF00,66CC00,669900,666600,663300,660000,33FF00,33CC00,339900,336600,333300,330000,00FF00,00CC00,009900,006600,003300,000000', APPLY_CANCEL = {
                apply: 'Apply',
                cancel: 'Cancel'
            }, NS = '.kendoColorTools', CLICK_NS = 'click' + NS, KEYDOWN_NS = 'keydown' + NS, browser = kendo.support.browser, isIE8 = browser.msie && browser.version < 9;
        var ColorSelector = Widget.extend({
            init: function (element, options) {
                var that = this, ariaId;
                Widget.fn.init.call(that, element, options);
                element = that.element;
                options = that.options;
                that._value = options.value = parseColor(options.value);
                that._tabIndex = element.attr('tabIndex') || 0;
                ariaId = that._ariaId = options.ariaId;
                if (ariaId) {
                    element.attr('aria-labelledby', ariaId);
                }
                if (options._standalone) {
                    that._triggerSelect = that._triggerChange;
                }
            },
            options: {
                name: 'ColorSelector',
                value: null,
                _standalone: true
            },
            events: [
                'change',
                'select',
                'cancel'
            ],
            color: function (value) {
                if (value !== undefined) {
                    this._value = parseColor(value);
                    this._updateUI(this._value);
                }
                return this._value;
            },
            value: function (color) {
                color = this.color(color);
                if (color) {
                    if (this.options.opacity) {
                        color = color.toCssRgba();
                    } else {
                        color = color.toCss();
                    }
                }
                return color || null;
            },
            enable: function (enable) {
                if (arguments.length === 0) {
                    enable = true;
                }
                $('.k-disabled-overlay', this.wrapper).remove();
                if (!enable) {
                    this.wrapper.append('<div class=\'k-disabled-overlay\'></div>');
                }
                this._onEnable(enable);
            },
            _select: function (color, nohooks) {
                var prev = this._value;
                color = this.color(color);
                if (!nohooks) {
                    this.element.trigger('change');
                    if (!color.equals(prev)) {
                        this.trigger('change', { value: this.value() });
                    } else if (!this._standalone) {
                        this.trigger('cancel');
                    }
                }
            },
            _triggerSelect: function (color) {
                triggerEvent(this, 'select', color);
            },
            _triggerChange: function (color) {
                triggerEvent(this, 'change', color);
            },
            destroy: function () {
                if (this.element) {
                    this.element.off(NS);
                }
                if (this.wrapper) {
                    this.wrapper.off(NS).find('*').off(NS);
                }
                this.wrapper = null;
                Widget.fn.destroy.call(this);
            },
            _updateUI: $.noop,
            _selectOnHide: function () {
                return null;
            },
            _cancel: function () {
                this.trigger('cancel');
            }
        });
        function triggerEvent(self, type, color) {
            color = parseColor(color);
            if (color && !color.equals(self.color())) {
                if (type == 'change') {
                    self._value = color;
                }
                if (color.a != 1) {
                    color = color.toCssRgba();
                } else {
                    color = color.toCss();
                }
                self.trigger(type, { value: color });
            }
        }
        var ColorPalette = ColorSelector.extend({
            init: function (element, options) {
                var that = this;
                ColorSelector.fn.init.call(that, element, options);
                element = that.wrapper = that.element;
                options = that.options;
                var colors = options.palette;
                if (colors == 'websafe') {
                    colors = WEBPALETTE;
                    options.columns = 18;
                } else if (colors == 'basic') {
                    colors = SIMPLEPALETTE;
                }
                if (typeof colors == 'string') {
                    colors = colors.split(',');
                }
                if ($.isArray(colors)) {
                    colors = $.map(colors, function (x) {
                        return parseColor(x);
                    });
                }
                that._selectedID = (options.ariaId || kendo.guid()) + '_selected';
                element.addClass('k-widget k-colorpalette').attr('role', 'grid').attr('aria-readonly', 'true').append($(that._template({
                    colors: colors,
                    columns: options.columns,
                    tileSize: options.tileSize,
                    value: that._value,
                    id: options.ariaId
                }))).on(CLICK_NS, '.k-item', function (ev) {
                    that._select($(ev.currentTarget).css(BACKGROUNDCOLOR));
                }).attr('tabIndex', that._tabIndex).on(KEYDOWN_NS, bind(that._keydown, that));
                var tileSize = options.tileSize, width, height;
                if (tileSize) {
                    if (/number|string/.test(typeof tileSize)) {
                        width = height = parseFloat(tileSize);
                    } else if (typeof tileSize == 'object') {
                        width = parseFloat(tileSize.width);
                        height = parseFloat(tileSize.height);
                    } else {
                        throw new Error('Unsupported value for the \'tileSize\' argument');
                    }
                    element.find('.k-item').css({
                        width: width,
                        height: height
                    });
                }
            },
            focus: function () {
                this.wrapper.focus();
            },
            options: {
                name: 'ColorPalette',
                columns: 10,
                tileSize: null,
                palette: 'basic'
            },
            _onEnable: function (enable) {
                if (enable) {
                    this.wrapper.attr('tabIndex', this._tabIndex);
                } else {
                    this.wrapper.removeAttr('tabIndex');
                }
            },
            _keydown: function (e) {
                var selected, wrapper = this.wrapper, items = wrapper.find('.k-item'), current = items.filter('.' + ITEMSELECTEDCLASS).get(0), keyCode = e.keyCode;
                if (keyCode == KEYS.LEFT) {
                    selected = relative(items, current, -1);
                } else if (keyCode == KEYS.RIGHT) {
                    selected = relative(items, current, 1);
                } else if (keyCode == KEYS.DOWN) {
                    selected = relative(items, current, this.options.columns);
                } else if (keyCode == KEYS.UP) {
                    selected = relative(items, current, -this.options.columns);
                } else if (keyCode == KEYS.ENTER) {
                    preventDefault(e);
                    if (current) {
                        this._select($(current).css(BACKGROUNDCOLOR));
                    }
                } else if (keyCode == KEYS.ESC) {
                    this._cancel();
                }
                if (selected) {
                    preventDefault(e);
                    this._current(selected);
                    try {
                        var color = parseColor(selected.css(BACKGROUNDCOLOR));
                        this._triggerSelect(color);
                    } catch (ex) {
                    }
                }
            },
            _current: function (item) {
                this.wrapper.find('.' + ITEMSELECTEDCLASS).removeClass(ITEMSELECTEDCLASS).attr('aria-selected', false).removeAttr('id');
                $(item).addClass(ITEMSELECTEDCLASS).attr('aria-selected', true).attr('id', this._selectedID);
                this.element.removeAttr('aria-activedescendant').attr('aria-activedescendant', this._selectedID);
            },
            _updateUI: function (color) {
                var item = null;
                this.wrapper.find('.k-item').each(function () {
                    var c = parseColor($(this).css(BACKGROUNDCOLOR));
                    if (c && c.equals(color)) {
                        item = this;
                        return false;
                    }
                });
                this._current(item);
            },
            _template: kendo.template('<table class="k-palette k-reset" role="presentation"><tr role="row">' + '# for (var i = 0; i < colors.length; ++i) { #' + '# var selected = colors[i].equals(value); #' + '# if (i && i % columns == 0) { # </tr><tr role="row"> # } #' + '<td role="gridcell" unselectable="on" style="background-color:#= colors[i].toCss() #"' + '#= selected ? " aria-selected=true" : "" # ' + '#=(id && i === 0) ? "id=\\""+id+"\\" " : "" # ' + 'class="k-item#= selected ? " ' + ITEMSELECTEDCLASS + '" : "" #" ' + 'aria-label="#= colors[i].toCss() #"></td>' + '# } #' + '</tr></table>')
        });
        var FlatColorPicker = ColorSelector.extend({
            init: function (element, options) {
                var that = this;
                ColorSelector.fn.init.call(that, element, options);
                options = that.options;
                element = that.element;
                that.wrapper = element.addClass('k-widget k-flatcolorpicker').append(that._template(options));
                that._hueElements = $('.k-hsv-rectangle, .k-transparency-slider .k-slider-track', element);
                that._selectedColor = $('.k-selected-color-display', element);
                that._colorAsText = $('input.k-color-value', element);
                that._sliders();
                that._hsvArea();
                that._updateUI(that._value || parseColor('#f00'));
                element.find('input.k-color-value').on(KEYDOWN_NS, function (ev) {
                    var input = this;
                    if (ev.keyCode == KEYS.ENTER) {
                        try {
                            var color = parseColor(input.value);
                            var val = that.color();
                            that._select(color, color.equals(val));
                        } catch (ex) {
                            $(input).addClass('k-state-error');
                        }
                    } else if (that.options.autoupdate) {
                        setTimeout(function () {
                            var color = parseColor(input.value, true);
                            if (color) {
                                that._updateUI(color, true);
                            }
                        }, 10);
                    }
                }).end().on(CLICK_NS, '.k-controls button.apply', function () {
                    that._select(that._getHSV());
                }).on(CLICK_NS, '.k-controls button.cancel', function () {
                    that._updateUI(that.color());
                    that._cancel();
                });
                if (isIE8) {
                    that._applyIEFilter();
                }
            },
            destroy: function () {
                this._hueSlider.destroy();
                if (this._opacitySlider) {
                    this._opacitySlider.destroy();
                }
                this._hueSlider = this._opacitySlider = this._hsvRect = this._hsvHandle = this._hueElements = this._selectedColor = this._colorAsText = null;
                ColorSelector.fn.destroy.call(this);
            },
            options: {
                name: 'FlatColorPicker',
                opacity: false,
                buttons: false,
                input: true,
                preview: true,
                autoupdate: true,
                messages: APPLY_CANCEL
            },
            _applyIEFilter: function () {
                var track = this.element.find('.k-hue-slider .k-slider-track')[0], url = track.currentStyle.backgroundImage;
                url = url.replace(/^url\([\'\"]?|[\'\"]?\)$/g, '');
                track.style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\'' + url + '\', sizingMethod=\'scale\')';
            },
            _sliders: function () {
                var that = this, element = that.element;
                function hueChange(e) {
                    that._updateUI(that._getHSV(e.value, null, null, null));
                }
                that._hueSlider = element.find('.k-hue-slider').kendoSlider({
                    min: 0,
                    max: 360,
                    tickPlacement: 'none',
                    showButtons: false,
                    slide: hueChange,
                    change: hueChange
                }).data('kendoSlider');
                function opacityChange(e) {
                    that._updateUI(that._getHSV(null, null, null, e.value / 100));
                }
                that._opacitySlider = element.find('.k-transparency-slider').kendoSlider({
                    min: 0,
                    max: 100,
                    tickPlacement: 'none',
                    showButtons: false,
                    slide: opacityChange,
                    change: opacityChange
                }).data('kendoSlider');
            },
            _hsvArea: function () {
                var that = this, element = that.element, hsvRect = element.find('.k-hsv-rectangle'), hsvHandle = hsvRect.find('.k-draghandle').attr('tabIndex', 0).on(KEYDOWN_NS, bind(that._keydown, that));
                function update(x, y) {
                    var offset = this.offset, dx = x - offset.left, dy = y - offset.top, rw = this.width, rh = this.height;
                    dx = dx < 0 ? 0 : dx > rw ? rw : dx;
                    dy = dy < 0 ? 0 : dy > rh ? rh : dy;
                    that._svChange(dx / rw, 1 - dy / rh);
                }
                that._hsvEvents = new kendo.UserEvents(hsvRect, {
                    global: true,
                    press: function (e) {
                        this.offset = kendo.getOffset(hsvRect);
                        this.width = hsvRect.width();
                        this.height = hsvRect.height();
                        hsvHandle.focus();
                        update.call(this, e.x.location, e.y.location);
                    },
                    start: function () {
                        hsvRect.addClass('k-dragging');
                        hsvHandle.focus();
                    },
                    move: function (e) {
                        e.preventDefault();
                        update.call(this, e.x.location, e.y.location);
                    },
                    end: function () {
                        hsvRect.removeClass('k-dragging');
                    }
                });
                that._hsvRect = hsvRect;
                that._hsvHandle = hsvHandle;
            },
            _onEnable: function (enable) {
                this._hueSlider.enable(enable);
                if (this._opacitySlider) {
                    this._opacitySlider.enable(enable);
                }
                this.wrapper.find('input').attr('disabled', !enable);
                var handle = this._hsvRect.find('.k-draghandle');
                if (enable) {
                    handle.attr('tabIndex', this._tabIndex);
                } else {
                    handle.removeAttr('tabIndex');
                }
            },
            _keydown: function (ev) {
                var that = this;
                function move(prop, d) {
                    var c = that._getHSV();
                    c[prop] += d * (ev.shiftKey ? 0.01 : 0.05);
                    if (c[prop] < 0) {
                        c[prop] = 0;
                    }
                    if (c[prop] > 1) {
                        c[prop] = 1;
                    }
                    that._updateUI(c);
                    preventDefault(ev);
                }
                function hue(d) {
                    var c = that._getHSV();
                    c.h += d * (ev.shiftKey ? 1 : 5);
                    if (c.h < 0) {
                        c.h = 0;
                    }
                    if (c.h > 359) {
                        c.h = 359;
                    }
                    that._updateUI(c);
                    preventDefault(ev);
                }
                switch (ev.keyCode) {
                case KEYS.LEFT:
                    if (ev.ctrlKey) {
                        hue(-1);
                    } else {
                        move('s', -1);
                    }
                    break;
                case KEYS.RIGHT:
                    if (ev.ctrlKey) {
                        hue(1);
                    } else {
                        move('s', 1);
                    }
                    break;
                case KEYS.UP:
                    move(ev.ctrlKey && that._opacitySlider ? 'a' : 'v', 1);
                    break;
                case KEYS.DOWN:
                    move(ev.ctrlKey && that._opacitySlider ? 'a' : 'v', -1);
                    break;
                case KEYS.ENTER:
                    that._select(that._getHSV());
                    break;
                case KEYS.F2:
                    that.wrapper.find('input.k-color-value').focus().select();
                    break;
                case KEYS.ESC:
                    that._cancel();
                    break;
                }
            },
            focus: function () {
                this._hsvHandle.focus();
            },
            _getHSV: function (h, s, v, a) {
                var rect = this._hsvRect, width = rect.width(), height = rect.height(), handlePosition = this._hsvHandle.position();
                if (h == null) {
                    h = this._hueSlider.value();
                }
                if (s == null) {
                    s = handlePosition.left / width;
                }
                if (v == null) {
                    v = 1 - handlePosition.top / height;
                }
                if (a == null) {
                    a = this._opacitySlider ? this._opacitySlider.value() / 100 : 1;
                }
                return Color.fromHSV(h, s, v, a);
            },
            _svChange: function (s, v) {
                var color = this._getHSV(null, s, v, null);
                this._updateUI(color);
            },
            _updateUI: function (color, dontChangeInput) {
                var that = this, rect = that._hsvRect;
                if (!color) {
                    return;
                }
                this._colorAsText.removeClass('k-state-error');
                that._selectedColor.css(BACKGROUNDCOLOR, color.toDisplay());
                if (!dontChangeInput) {
                    that._colorAsText.val(that._opacitySlider ? color.toCssRgba() : color.toCss());
                }
                that._triggerSelect(color);
                color = color.toHSV();
                that._hsvHandle.css({
                    left: color.s * rect.width() + 'px',
                    top: (1 - color.v) * rect.height() + 'px'
                });
                that._hueElements.css(BACKGROUNDCOLOR, Color.fromHSV(color.h, 1, 1, 1).toCss());
                that._hueSlider.value(color.h);
                if (that._opacitySlider) {
                    that._opacitySlider.value(100 * color.a);
                }
            },
            _selectOnHide: function () {
                return this.options.buttons ? null : this._getHSV();
            },
            _template: kendo.template('# if (preview) { #' + '<div class="k-selected-color"><div class="k-selected-color-display"><input class="k-color-value" #= !data.input ? \'style="visibility: hidden;"\' : "" #></div></div>' + '# } #' + '<div class="k-hsv-rectangle"><div class="k-hsv-gradient"></div><div class="k-draghandle"></div></div>' + '<input class="k-hue-slider" />' + '# if (opacity) { #' + '<input class="k-transparency-slider" />' + '# } #' + '# if (buttons) { #' + '<div unselectable="on" class="k-controls"><button class="k-button k-primary apply">#: messages.apply #</button> <button class="k-button cancel">#: messages.cancel #</button></div>' + '# } #')
        });
        function relative(array, element, delta) {
            array = Array.prototype.slice.call(array);
            var n = array.length;
            var pos = array.indexOf(element);
            if (pos < 0) {
                return delta < 0 ? array[n - 1] : array[0];
            }
            pos += delta;
            if (pos < 0) {
                pos += n;
            } else {
                pos %= n;
            }
            return array[pos];
        }
        var ColorPicker = Widget.extend({
            init: function (element, options) {
                var that = this;
                Widget.fn.init.call(that, element, options);
                options = that.options;
                element = that.element;
                var value = element.attr('value') || element.val();
                if (value) {
                    value = parseColor(value, true);
                } else {
                    value = parseColor(options.value, true);
                }
                that._value = options.value = value;
                var content = that.wrapper = $(that._template(options));
                element.hide().after(content);
                if (element.is('input')) {
                    element.appendTo(content);
                    var label = element.closest('label');
                    var id = element.attr('id');
                    if (id) {
                        label = label.add('label[for="' + id + '"]');
                    }
                    label.click(function (ev) {
                        that.open();
                        ev.preventDefault();
                    });
                }
                that._tabIndex = element.attr('tabIndex') || 0;
                that.enable(!element.attr('disabled'));
                var accesskey = element.attr('accesskey');
                if (accesskey) {
                    element.attr('accesskey', null);
                    content.attr('accesskey', accesskey);
                }
                that.bind('activate', function (ev) {
                    if (!ev.isDefaultPrevented()) {
                        that.toggle();
                    }
                });
                that._updateUI(value);
            },
            destroy: function () {
                this.wrapper.off(NS).find('*').off(NS);
                if (this._popup) {
                    this._selector.destroy();
                    this._popup.destroy();
                }
                this._selector = this._popup = this.wrapper = null;
                Widget.fn.destroy.call(this);
            },
            enable: function (enable) {
                var that = this, wrapper = that.wrapper, innerWrapper = wrapper.children('.k-picker-wrap'), arrow = innerWrapper.find('.k-select');
                if (arguments.length === 0) {
                    enable = true;
                }
                that.element.attr('disabled', !enable);
                wrapper.attr('aria-disabled', !enable);
                arrow.off(NS).on('mousedown' + NS, preventDefault);
                wrapper.addClass('k-state-disabled').removeAttr('tabIndex').add('*', wrapper).off(NS);
                if (enable) {
                    wrapper.removeClass('k-state-disabled').attr('tabIndex', that._tabIndex).on('mouseenter' + NS, function () {
                        innerWrapper.addClass('k-state-hover');
                    }).on('mouseleave' + NS, function () {
                        innerWrapper.removeClass('k-state-hover');
                    }).on('focus' + NS, function () {
                        innerWrapper.addClass('k-state-focused');
                    }).on('blur' + NS, function () {
                        innerWrapper.removeClass('k-state-focused');
                    }).on(KEYDOWN_NS, bind(that._keydown, that)).on(CLICK_NS, '.k-select', bind(that.toggle, that)).on(CLICK_NS, that.options.toolIcon ? '.k-tool-icon' : '.k-selected-color', function () {
                        that.trigger('activate');
                    });
                } else {
                    that.close();
                }
            },
            _template: kendo.template('<span role="textbox" aria-haspopup="true" class="k-widget k-colorpicker k-header">' + '<span class="k-picker-wrap k-state-default">' + '# if (toolIcon) { #' + '<span class="k-tool-icon #= toolIcon #">' + '<span class="k-selected-color"></span>' + '</span>' + '# } else { #' + '<span class="k-selected-color"></span>' + '# } #' + '<span class="k-select" unselectable="on" aria-label="select">' + '<span class="k-icon k-i-arrow-s"></span>' + '</span>' + '</span>' + '</span>'),
            options: {
                name: 'ColorPicker',
                palette: null,
                columns: 10,
                toolIcon: null,
                value: null,
                messages: APPLY_CANCEL,
                opacity: false,
                buttons: true,
                preview: true,
                ARIATemplate: 'Current selected color is #=data || ""#'
            },
            events: [
                'activate',
                'change',
                'select',
                'open',
                'close'
            ],
            open: function () {
                if (!this.element.prop('disabled')) {
                    this._getPopup().open();
                }
            },
            close: function () {
                this._getPopup().close();
            },
            toggle: function () {
                if (!this.element.prop('disabled')) {
                    this._getPopup().toggle();
                }
            },
            color: ColorSelector.fn.color,
            value: ColorSelector.fn.value,
            _select: ColorSelector.fn._select,
            _triggerSelect: ColorSelector.fn._triggerSelect,
            _isInputTypeColor: function () {
                var el = this.element[0];
                return /^input$/i.test(el.tagName) && /^color$/i.test(el.type);
            },
            _updateUI: function (value) {
                var formattedValue = '';
                if (value) {
                    if (this._isInputTypeColor() || value.a == 1) {
                        formattedValue = value.toCss();
                    } else {
                        formattedValue = value.toCssRgba();
                    }
                    this.element.val(formattedValue);
                }
                if (!this._ariaTemplate) {
                    this._ariaTemplate = kendo.template(this.options.ARIATemplate);
                }
                this.wrapper.attr('aria-label', this._ariaTemplate(formattedValue));
                this._triggerSelect(value);
                this.wrapper.find('.k-selected-color').css(BACKGROUNDCOLOR, value ? value.toDisplay() : 'transparent');
            },
            _keydown: function (ev) {
                var key = ev.keyCode;
                if (this._getPopup().visible()) {
                    if (key == KEYS.ESC) {
                        this._selector._cancel();
                    } else {
                        this._selector._keydown(ev);
                    }
                    preventDefault(ev);
                } else if (key == KEYS.ENTER || key == KEYS.DOWN) {
                    this.open();
                    preventDefault(ev);
                }
            },
            _getPopup: function () {
                var that = this, popup = that._popup;
                if (!popup) {
                    var options = that.options;
                    var selectorType;
                    if (options.palette) {
                        selectorType = ColorPalette;
                    } else {
                        selectorType = FlatColorPicker;
                    }
                    options._standalone = false;
                    delete options.select;
                    delete options.change;
                    delete options.cancel;
                    var id = kendo.guid();
                    var selector = that._selector = new selectorType($('<div id="' + id + '"/>').appendTo(document.body), options);
                    that.wrapper.attr('aria-owns', id);
                    that._popup = popup = selector.wrapper.kendoPopup({
                        anchor: that.wrapper,
                        adjustSize: {
                            width: 5,
                            height: 0
                        }
                    }).data('kendoPopup');
                    selector.bind({
                        select: function (ev) {
                            that._updateUI(parseColor(ev.value));
                        },
                        change: function () {
                            that._select(selector.color());
                            that.close();
                        },
                        cancel: function () {
                            that.close();
                        }
                    });
                    popup.bind({
                        close: function (ev) {
                            if (that.trigger('close')) {
                                ev.preventDefault();
                                return;
                            }
                            that.wrapper.children('.k-picker-wrap').removeClass('k-state-focused');
                            var color = selector._selectOnHide();
                            if (!color) {
                                setTimeout(function () {
                                    if (that.wrapper) {
                                        that.wrapper.focus();
                                    }
                                });
                                that._updateUI(that.color());
                            } else {
                                that._select(color);
                            }
                        },
                        open: function (ev) {
                            if (that.trigger('open')) {
                                ev.preventDefault();
                            } else {
                                that.wrapper.children('.k-picker-wrap').addClass('k-state-focused');
                            }
                        },
                        activate: function () {
                            selector._select(that.color(), true);
                            selector.focus();
                            that.wrapper.children('.k-picker-wrap').addClass('k-state-focused');
                        }
                    });
                }
                return popup;
            }
        });
        function preventDefault(ev) {
            ev.preventDefault();
        }
        function bind(callback, obj) {
            return function () {
                return callback.apply(obj, arguments);
            };
        }
        ui.plugin(ColorPalette);
        ui.plugin(FlatColorPicker);
        ui.plugin(ColorPicker);
    }(jQuery, parseInt));
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
    define('util/undoredostack', ['kendo.core'], f);
}(function () {
    (function (kendo) {
        var UndoRedoStack = kendo.Observable.extend({
            init: function (options) {
                kendo.Observable.fn.init.call(this, options);
                this.clear();
            },
            events: [
                'undo',
                'redo'
            ],
            push: function (command) {
                this.stack = this.stack.slice(0, this.currentCommandIndex + 1);
                this.currentCommandIndex = this.stack.push(command) - 1;
            },
            undo: function () {
                if (this.canUndo()) {
                    var command = this.stack[this.currentCommandIndex--];
                    command.undo();
                    this.trigger('undo', { command: command });
                }
            },
            redo: function () {
                if (this.canRedo()) {
                    var command = this.stack[++this.currentCommandIndex];
                    command.redo();
                    this.trigger('redo', { command: command });
                }
            },
            clear: function () {
                this.stack = [];
                this.currentCommandIndex = -1;
            },
            canUndo: function () {
                return this.currentCommandIndex >= 0;
            },
            canRedo: function () {
                return this.currentCommandIndex != this.stack.length - 1;
            }
        });
        kendo.deepExtend(kendo, { util: { UndoRedoStack: UndoRedoStack } });
    }(kendo));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/main', [
        'util/undoredostack',
        'kendo.combobox',
        'kendo.dropdownlist',
        'kendo.window',
        'kendo.colorpicker'
    ], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo, Class = kendo.Class, Widget = kendo.ui.Widget, os = kendo.support.mobileOS, browser = kendo.support.browser, extend = $.extend, proxy = $.proxy, deepExtend = kendo.deepExtend, keys = kendo.keys;
        var ToolTemplate = Class.extend({
            init: function (options) {
                this.options = options;
            },
            getHtml: function () {
                var options = this.options;
                return kendo.template(options.template, { useWithBlock: false })(options);
            }
        });
        var EditorUtils = {
            editorWrapperTemplate: '<table cellspacing="4" cellpadding="0" class="k-widget k-editor k-header" role="presentation"><tbody>' + '<tr role="presentation"><td class="k-editor-toolbar-wrap" role="presentation"><ul class="k-editor-toolbar" role="toolbar" /></td></tr>' + '<tr><td class="k-editable-area" /></tr>' + '</tbody></table>',
            buttonTemplate: '<a href="" role="button" class="k-tool"' + '#= data.popup ? " data-popup" : "" #' + ' unselectable="on" title="#= data.title #"><span unselectable="on" class="k-tool-icon #= data.cssClass #"></span><span class="k-tool-text">#= data.title #</span></a>',
            colorPickerTemplate: '<div class="k-colorpicker #= data.cssClass #" />',
            comboBoxTemplate: '<select title="#= data.title #" class="#= data.cssClass #" />',
            dropDownListTemplate: '<span class="k-editor-dropdown"><select title="#= data.title #" class="#= data.cssClass #" /></span>',
            separatorTemplate: '<span class="k-separator" />',
            overflowAnchorTemplate: '<a href="" role="button" class="k-tool k-overflow-anchor" data-popup' + ' unselectable="on"><span unselectable="on" class="k-icon k-i-more"></span></a>',
            formatByName: function (name, format) {
                for (var i = 0; i < format.length; i++) {
                    if ($.inArray(name, format[i].tags) >= 0) {
                        return format[i];
                    }
                }
            },
            registerTool: function (toolName, tool) {
                var toolOptions = tool.options;
                if (toolOptions && toolOptions.template) {
                    toolOptions.template.options.cssClass = 'k-' + toolName;
                }
                if (!tool.name) {
                    tool.options.name = toolName;
                    tool.name = toolName.toLowerCase();
                }
                Editor.defaultTools[toolName] = tool;
            },
            registerFormat: function (formatName, format) {
                Editor.fn.options.formats[formatName] = format;
            }
        };
        var messages = {
            bold: 'Bold',
            italic: 'Italic',
            underline: 'Underline',
            strikethrough: 'Strikethrough',
            superscript: 'Superscript',
            subscript: 'Subscript',
            justifyCenter: 'Center text',
            justifyLeft: 'Align text left',
            justifyRight: 'Align text right',
            justifyFull: 'Justify',
            insertUnorderedList: 'Insert unordered list',
            insertOrderedList: 'Insert ordered list',
            indent: 'Indent',
            outdent: 'Outdent',
            createLink: 'Insert hyperlink',
            unlink: 'Remove hyperlink',
            insertImage: 'Insert image',
            insertFile: 'Insert file',
            insertHtml: 'Insert HTML',
            viewHtml: 'View HTML',
            fontName: 'Select font family',
            fontNameInherit: '(inherited font)',
            fontSize: 'Select font size',
            fontSizeInherit: '(inherited size)',
            formatBlock: 'Format',
            formatting: 'Format',
            foreColor: 'Color',
            backColor: 'Background color',
            style: 'Styles',
            emptyFolder: 'Empty Folder',
            editAreaTitle: 'Editable area. Press F10 for toolbar.',
            uploadFile: 'Upload',
            orderBy: 'Arrange by:',
            orderBySize: 'Size',
            orderByName: 'Name',
            invalidFileType: 'The selected file "{0}" is not valid. Supported file types are {1}.',
            deleteFile: 'Are you sure you want to delete "{0}"?',
            overwriteFile: 'A file with name "{0}" already exists in the current directory. Do you want to overwrite it?',
            directoryNotFound: 'A directory with this name was not found.',
            imageWebAddress: 'Web address',
            imageAltText: 'Alternate text',
            imageWidth: 'Width (px)',
            imageHeight: 'Height (px)',
            fileWebAddress: 'Web address',
            fileTitle: 'Title',
            linkWebAddress: 'Web address',
            linkText: 'Text',
            linkToolTip: 'ToolTip',
            linkOpenInNewWindow: 'Open link in new window',
            dialogUpdate: 'Update',
            dialogInsert: 'Insert',
            dialogCancel: 'Cancel',
            createTable: 'Create table',
            createTableHint: 'Create a {0} x {1} table',
            addColumnLeft: 'Add column on the left',
            addColumnRight: 'Add column on the right',
            addRowAbove: 'Add row above',
            addRowBelow: 'Add row below',
            deleteRow: 'Delete row',
            deleteColumn: 'Delete column'
        };
        var supportedBrowser = !os || os.ios && os.flatVersion >= 500 || !os.ios && typeof document.documentElement.contentEditable != 'undefined';
        var toolGroups = {
            basic: [
                'bold',
                'italic',
                'underline'
            ],
            alignment: [
                'justifyLeft',
                'justifyCenter',
                'justifyRight'
            ],
            lists: [
                'insertUnorderedList',
                'insertOrderedList'
            ],
            indenting: [
                'indent',
                'outdent'
            ],
            links: [
                'createLink',
                'unlink'
            ],
            tables: [
                'createTable',
                'addColumnLeft',
                'addColumnRight',
                'addRowAbove',
                'addRowBelow',
                'deleteRow',
                'deleteColumn'
            ]
        };
        var Editor = Widget.extend({
            init: function (element, options) {
                var that = this, value, editorNS = kendo.ui.editor, toolbarContainer, toolbarOptions, type;
                var domElement;
                var dom = editorNS.Dom;
                if (!supportedBrowser) {
                    return;
                }
                Widget.fn.init.call(that, element, options);
                that.options = deepExtend({}, that.options, options);
                that.options.tools = that.options.tools.slice();
                element = that.element;
                domElement = element[0];
                type = dom.name(domElement);
                this._registerHandler(element.closest('form'), 'submit', proxy(that.update, that, undefined));
                toolbarOptions = extend({}, that.options);
                toolbarOptions.editor = that;
                if (type == 'textarea') {
                    that._wrapTextarea();
                    toolbarContainer = that.wrapper.find('.k-editor-toolbar');
                    if (domElement.id) {
                        toolbarContainer.attr('aria-controls', domElement.id);
                    }
                } else {
                    that.element.attr('contenteditable', true).addClass('k-widget k-editor k-editor-inline');
                    toolbarOptions.popup = true;
                    toolbarContainer = $('<ul class="k-editor-toolbar" role="toolbar" />').insertBefore(element);
                }
                that.toolbar = new editorNS.Toolbar(toolbarContainer[0], toolbarOptions);
                that.toolbar.bindTo(that);
                if (type == 'textarea') {
                    setTimeout(function () {
                        var heightStyle = that.wrapper[0].style.height;
                        var expectedHeight = parseInt(heightStyle, 10);
                        var actualHeight = that.wrapper.height();
                        if (heightStyle.indexOf('px') > 0 && !isNaN(expectedHeight) && actualHeight > expectedHeight) {
                            that.wrapper.height(expectedHeight - (actualHeight - expectedHeight));
                        }
                    });
                }
                that._resizable();
                that._initializeContentElement(that);
                that.keyboard = new editorNS.Keyboard([
                    new editorNS.BackspaceHandler(that),
                    new editorNS.TypingHandler(that),
                    new editorNS.SystemHandler(that)
                ]);
                that.clipboard = new editorNS.Clipboard(this);
                that.undoRedoStack = new kendo.util.UndoRedoStack();
                if (options && options.value) {
                    value = options.value;
                } else if (that.textarea) {
                    value = domElement.value;
                    if (that.options.encoded && $.trim(domElement.defaultValue).length) {
                        value = domElement.defaultValue;
                    }
                    value = value.replace(/[\r\n\v\f\t ]+/gi, ' ');
                } else {
                    value = domElement.innerHTML;
                }
                that.value(value || kendo.ui.editor.emptyElementContent);
                this._registerHandler(document, {
                    'mousedown': function () {
                        that._endTyping();
                    },
                    'mouseup': function () {
                        that._mouseup();
                    }
                });
                that.toolbar.resize();
                kendo.notify(that);
            },
            setOptions: function (options) {
                var editor = this;
                Widget.fn.setOptions.call(editor, options);
                if (options.tools) {
                    editor.toolbar.bindTo(editor);
                }
            },
            _endTyping: function () {
                var keyboard = this.keyboard;
                try {
                    if (keyboard.isTypingInProgress()) {
                        keyboard.endTyping(true);
                        this.saveSelection();
                    }
                } catch (e) {
                }
            },
            _selectionChange: function () {
                this._selectionStarted = false;
                this.saveSelection();
                this.trigger('select', {});
            },
            _resizable: function () {
                var resizable = this.options.resizable;
                var isResizable = $.isPlainObject(resizable) ? resizable.content === undefined || resizable.content === true : resizable;
                if (isResizable && this.textarea) {
                    $('<div class=\'k-resize-handle\'><span class=\'k-icon k-resize-se\' /></div>').insertAfter(this.textarea);
                    this.wrapper.kendoResizable(extend({}, this.options.resizable, {
                        start: function (e) {
                            var editor = this.editor = $(e.currentTarget).closest('.k-editor');
                            this.initialSize = editor.height();
                            editor.find('td:last').append('<div class=\'k-overlay\' />');
                        },
                        resize: function (e) {
                            var delta = e.y.initialDelta;
                            var newSize = this.initialSize + delta;
                            var min = this.options.min || 0;
                            var max = this.options.max || Infinity;
                            newSize = Math.min(max, Math.max(min, newSize));
                            this.editor.height(newSize);
                        },
                        resizeend: function () {
                            this.editor.find('.k-overlay').remove();
                            this.editor = null;
                        }
                    }));
                }
            },
            _wrapTextarea: function () {
                var that = this, textarea = that.element, w = textarea[0].style.width, h = textarea[0].style.height, template = EditorUtils.editorWrapperTemplate, editorWrap = $(template).insertBefore(textarea).width(w).height(h), editArea = editorWrap.find('.k-editable-area');
                textarea.attr('autocomplete', 'off').appendTo(editArea).addClass('k-content k-raw-content').css('display', 'none');
                that.textarea = textarea;
                that.wrapper = editorWrap;
            },
            _createContentElement: function (stylesheets) {
                var editor = this;
                var iframe, wnd, doc;
                var textarea = editor.textarea;
                var specifiedDomain = editor.options.domain;
                var domain = specifiedDomain || document.domain;
                var domainScript = '';
                var src = 'javascript:""';
                if (specifiedDomain || domain != location.hostname) {
                    domainScript = '<script>document.domain="' + domain + '"</script>';
                    src = 'javascript:document.write(\'' + domainScript + '\')';
                }
                textarea.hide();
                iframe = $('<iframe />', {
                    title: editor.options.messages.editAreaTitle,
                    frameBorder: '0'
                })[0];
                $(iframe).css('display', '').addClass('k-content').attr('tabindex', textarea[0].tabIndex).insertBefore(textarea);
                iframe.src = src;
                wnd = iframe.contentWindow || iframe;
                doc = wnd.document || iframe.contentDocument;
                $(iframe).one('load', function () {
                    editor.toolbar.decorateFrom(doc.body);
                });
                doc.open();
                doc.write('<!DOCTYPE html><html><head>' + '<meta charset=\'utf-8\' />' + '<style>' + 'html,body{padding:0;margin:0;height:100%;min-height:100%;}' + 'body{font-size:12px;font-family:Verdana,Geneva,sans-serif;margin-top:-1px;padding:1px .2em 0;' + 'word-wrap: break-word;-webkit-nbsp-mode: space;-webkit-line-break: after-white-space;' + (kendo.support.isRtl(textarea) ? 'direction:rtl;' : '') + '}' + 'h1{font-size:2em;margin:.67em 0}h2{font-size:1.5em}h3{font-size:1.16em}h4{font-size:1em}h5{font-size:.83em}h6{font-size:.7em}' + 'p{margin:0 0 1em;}.k-marker{display:none;}.k-paste-container,.Apple-style-span{position:absolute;left:-10000px;width:1px;height:1px;overflow:hidden}' + 'ul,ol{padding-left:2.5em}' + 'span{-ms-high-contrast-adjust:none;}' + 'a{color:#00a}' + 'code{font-size:1.23em}' + 'telerik\\3Ascript{display: none;}' + '.k-table{table-layout:fixed;width:100%;border-spacing:0;margin: 0 0 1em;}' + '.k-table td{min-width:1px;padding:.2em .3em;}' + '.k-table,.k-table td{outline:0;border: 1px dotted #ccc;}' + '.k-table p{margin:0;padding:0;}' + 'k\\:script{display:none;}' + '</style>' + domainScript + '<script>(function(d,c){d[c](\'header\'),d[c](\'article\'),d[c](\'nav\'),d[c](\'section\'),d[c](\'footer\');})(document, \'createElement\');</script>' + $.map(stylesheets, function (href) {
                    return '<link rel=\'stylesheet\' href=\'' + href + '\'>';
                }).join('') + '</head><body autocorrect=\'off\' contenteditable=\'true\'></body></html>');
                doc.close();
                return wnd;
            },
            _blur: function () {
                var textarea = this.textarea;
                var old = textarea ? textarea.val() : this._oldValue;
                var value = this.options.encoded ? this.encodedValue() : this.value();
                this.update();
                if (textarea) {
                    textarea.trigger('blur');
                }
                if (value != old) {
                    this.trigger('change');
                }
            },
            _spellCorrect: function (editor) {
                var beforeCorrection;
                var falseTrigger = false;
                this._registerHandler(editor.body, {
                    'contextmenu': function () {
                        editor.one('select', function () {
                            beforeCorrection = null;
                        });
                        editor._spellCorrectTimeout = setTimeout(function () {
                            beforeCorrection = new kendo.ui.editor.RestorePoint(editor.getRange());
                            falseTrigger = false;
                        }, 10);
                    },
                    'input': function () {
                        if (!beforeCorrection) {
                            return;
                        }
                        if (kendo.support.browser.mozilla && !falseTrigger) {
                            falseTrigger = true;
                            return;
                        }
                        kendo.ui.editor._finishUpdate(editor, beforeCorrection);
                    }
                });
            },
            _registerHandler: function (element, type, handler) {
                var NS = '.kendoEditor';
                element = $(element);
                if (!this._handlers) {
                    this._handlers = [];
                }
                if (element.length) {
                    if ($.isPlainObject(type)) {
                        for (var t in type) {
                            if (type.hasOwnProperty(t)) {
                                this._registerHandler(element, t, type[t]);
                            }
                        }
                    } else {
                        type = type.split(' ').join(NS + ' ') + NS;
                        this._handlers.push({
                            element: element,
                            type: type,
                            handler: handler
                        });
                        element.on(type, handler);
                    }
                }
            },
            _deregisterHandlers: function () {
                var handlers = this._handlers;
                for (var i = 0; i < handlers.length; i++) {
                    var h = handlers[i];
                    h.element.off(h.type, h.handler);
                }
                this._handlers = [];
            },
            _initializeContentElement: function () {
                var editor = this;
                var doc;
                var blurTrigger;
                if (editor.textarea) {
                    editor.window = editor._createContentElement(editor.options.stylesheets);
                    doc = editor.document = editor.window.contentDocument || editor.window.document;
                    editor.body = doc.body;
                    blurTrigger = editor.window;
                    this._registerHandler(doc, 'mouseup', proxy(this._mouseup, this));
                } else {
                    editor.window = window;
                    doc = editor.document = document;
                    editor.body = editor.element[0];
                    blurTrigger = editor.body;
                    editor.toolbar.decorateFrom(editor.body);
                }
                this._registerHandler(blurTrigger, 'blur', proxy(this._blur, this));
                try {
                    doc.execCommand('enableInlineTableEditing', null, false);
                } catch (e) {
                }
                if (kendo.support.touch) {
                    this._registerHandler(doc, {
                        'selectionchange': proxy(this._selectionChange, this),
                        'keydown': function () {
                            if (kendo._activeElement() != doc.body) {
                                editor.window.focus();
                            }
                        }
                    });
                }
                this._spellCorrect(editor);
                this._registerHandler(editor.body, {
                    'dragstart': function (e) {
                        e.preventDefault();
                    },
                    'keydown': function (e) {
                        var range;
                        if ((e.keyCode === keys.BACKSPACE || e.keyCode === keys.DELETE) && editor.body.getAttribute('contenteditable') !== 'true') {
                            return false;
                        }
                        if (e.keyCode === keys.F10) {
                            setTimeout(proxy(editor.toolbar.focus, editor.toolbar), 100);
                            e.preventDefault();
                            return;
                        } else if (e.keyCode == keys.LEFT || e.keyCode == keys.RIGHT) {
                            range = editor.getRange();
                            var left = e.keyCode == keys.LEFT;
                            var container = range[left ? 'startContainer' : 'endContainer'];
                            var offset = range[left ? 'startOffset' : 'endOffset'];
                            var direction = left ? -1 : 1;
                            if (left) {
                                offset -= 1;
                            }
                            if (offset + direction > 0 && container.nodeType == 3 && container.nodeValue[offset] == '\uFEFF') {
                                range.setStart(container, offset + direction);
                                range.collapse(true);
                                editor.selectRange(range);
                            }
                        }
                        var tools = editor.toolbar.tools;
                        var toolName = editor.keyboard.toolFromShortcut(tools, e);
                        var toolOptions = toolName ? tools[toolName].options : {};
                        if (toolName && !toolOptions.keyPressCommand) {
                            e.preventDefault();
                            if (!/^(undo|redo)$/.test(toolName)) {
                                editor.keyboard.endTyping(true);
                            }
                            editor.trigger('keydown', e);
                            editor.exec(toolName);
                            editor._runPostContentKeyCommands(e);
                            return false;
                        }
                        editor.keyboard.clearTimeout();
                        editor.keyboard.keydown(e);
                    },
                    'keypress': function (e) {
                        setTimeout(function () {
                            editor._runPostContentKeyCommands(e);
                        }, 0);
                    },
                    'keyup': function (e) {
                        var selectionCodes = [
                            8,
                            9,
                            33,
                            34,
                            35,
                            36,
                            37,
                            38,
                            39,
                            40,
                            40,
                            45,
                            46
                        ];
                        if ($.inArray(e.keyCode, selectionCodes) > -1 || e.keyCode == 65 && e.ctrlKey && !e.altKey && !e.shiftKey) {
                            editor._selectionChange();
                        }
                        editor.keyboard.keyup(e);
                    },
                    'mousedown': function (e) {
                        editor._selectionStarted = true;
                        if (browser.gecko) {
                            return;
                        }
                        var target = $(e.target);
                        if ((e.which == 2 || e.which == 1 && e.ctrlKey) && target.is('a[href]')) {
                            window.open(target.attr('href'), '_new');
                        }
                    },
                    'click': function (e) {
                        var dom = kendo.ui.editor.Dom, range;
                        if (dom.name(e.target) === 'img') {
                            range = editor.createRange();
                            range.selectNode(e.target);
                            editor.selectRange(range);
                        }
                    },
                    'cut copy paste': function (e) {
                        editor.clipboard['on' + e.type](e);
                    },
                    'focusin': function () {
                        if (editor.body.hasAttribute('contenteditable')) {
                            $(this).addClass('k-state-active');
                            editor.toolbar.show();
                        }
                    },
                    'focusout': function () {
                        setTimeout(function () {
                            var active = kendo._activeElement();
                            var body = editor.body;
                            var toolbar = editor.toolbar;
                            if (active != body && !$.contains(body, active) && !$(active).is('.k-editortoolbar-dragHandle') && !toolbar.focused()) {
                                $(body).removeClass('k-state-active');
                                toolbar.hide();
                            }
                        }, 10);
                    }
                });
            },
            _mouseup: function () {
                var that = this;
                if (that._selectionStarted) {
                    setTimeout(function () {
                        that._selectionChange();
                    }, 1);
                }
            },
            _runPostContentKeyCommands: function (e) {
                var range = this.getRange();
                var tools = this.keyboard.toolsFromShortcut(this.toolbar.tools, e);
                for (var i = 0; i < tools.length; i++) {
                    var tool = tools[i];
                    var o = tool.options;
                    if (!o.keyPressCommand) {
                        continue;
                    }
                    var cmd = new o.command({ range: range });
                    if (cmd.changesContent()) {
                        this.keyboard.endTyping(true);
                        this.exec(tool.name);
                    }
                }
            },
            refresh: function () {
                var that = this;
                if (that.textarea) {
                    that.textarea.val(that.value());
                    that.wrapper.find('iframe').remove();
                    that._initializeContentElement(that);
                    that.value(that.textarea.val());
                }
            },
            events: [
                'select',
                'change',
                'execute',
                'error',
                'paste',
                'keydown',
                'keyup'
            ],
            options: {
                name: 'Editor',
                messages: messages,
                formats: {},
                encoded: true,
                domain: null,
                resizable: false,
                deserialization: { custom: null },
                serialization: {
                    entities: true,
                    semantic: true,
                    scripts: false
                },
                pasteCleanup: {
                    all: false,
                    css: false,
                    custom: null,
                    keepNewLines: false,
                    msAllFormatting: false,
                    msConvertLists: true,
                    msTags: true,
                    none: false,
                    span: false
                },
                stylesheets: [],
                dialogOptions: {
                    modal: true,
                    resizable: false,
                    draggable: true,
                    animation: false
                },
                imageBrowser: null,
                fileBrowser: null,
                fontName: [
                    {
                        text: 'Arial',
                        value: 'Arial,Helvetica,sans-serif'
                    },
                    {
                        text: 'Courier New',
                        value: '\'Courier New\',Courier,monospace'
                    },
                    {
                        text: 'Georgia',
                        value: 'Georgia,serif'
                    },
                    {
                        text: 'Impact',
                        value: 'Impact,Charcoal,sans-serif'
                    },
                    {
                        text: 'Lucida Console',
                        value: '\'Lucida Console\',Monaco,monospace'
                    },
                    {
                        text: 'Tahoma',
                        value: 'Tahoma,Geneva,sans-serif'
                    },
                    {
                        text: 'Times New Roman',
                        value: '\'Times New Roman\',Times,serif'
                    },
                    {
                        text: 'Trebuchet MS',
                        value: '\'Trebuchet MS\',Helvetica,sans-serif'
                    },
                    {
                        text: 'Verdana',
                        value: 'Verdana,Geneva,sans-serif'
                    }
                ],
                fontSize: [
                    {
                        text: '1 (8pt)',
                        value: 'xx-small'
                    },
                    {
                        text: '2 (10pt)',
                        value: 'x-small'
                    },
                    {
                        text: '3 (12pt)',
                        value: 'small'
                    },
                    {
                        text: '4 (14pt)',
                        value: 'medium'
                    },
                    {
                        text: '5 (18pt)',
                        value: 'large'
                    },
                    {
                        text: '6 (24pt)',
                        value: 'x-large'
                    },
                    {
                        text: '7 (36pt)',
                        value: 'xx-large'
                    }
                ],
                formatBlock: [
                    {
                        text: 'Paragraph',
                        value: 'p'
                    },
                    {
                        text: 'Quotation',
                        value: 'blockquote'
                    },
                    {
                        text: 'Heading 1',
                        value: 'h1'
                    },
                    {
                        text: 'Heading 2',
                        value: 'h2'
                    },
                    {
                        text: 'Heading 3',
                        value: 'h3'
                    },
                    {
                        text: 'Heading 4',
                        value: 'h4'
                    },
                    {
                        text: 'Heading 5',
                        value: 'h5'
                    },
                    {
                        text: 'Heading 6',
                        value: 'h6'
                    }
                ],
                tools: [].concat.call(['formatting'], toolGroups.basic, toolGroups.alignment, toolGroups.lists, toolGroups.indenting, toolGroups.links, ['insertImage'], toolGroups.tables)
            },
            destroy: function () {
                Widget.fn.destroy.call(this);
                this._endTyping(true);
                this._deregisterHandlers();
                clearTimeout(this._spellCorrectTimeout);
                this._focusOutside();
                this.toolbar.destroy();
                kendo.destroy(this.wrapper);
            },
            _focusOutside: function () {
                if (kendo.support.browser.msie && this.textarea) {
                    var tempInput = $('<input style=\'position:fixed;left:1px;top:1px;width:1px;height:1px;font-size:0;border:0;opacity:0\' />').appendTo(document.body).focus();
                    tempInput.blur().remove();
                }
            },
            state: function (toolName) {
                var tool = Editor.defaultTools[toolName];
                var finder = tool && (tool.options.finder || tool.finder);
                var RangeUtils = kendo.ui.editor.RangeUtils;
                var range, textNodes;
                if (finder) {
                    range = this.getRange();
                    textNodes = RangeUtils.textNodes(range);
                    if (!textNodes.length && range.collapsed) {
                        textNodes = [range.startContainer];
                    }
                    return finder.getFormat ? finder.getFormat(textNodes) : finder.isFormatted(textNodes);
                }
                return false;
            },
            value: function (html) {
                var body = this.body, editorNS = kendo.ui.editor, options = this.options, currentHtml = editorNS.Serializer.domToXhtml(body, options.serialization);
                if (html === undefined) {
                    return currentHtml;
                }
                if (html == currentHtml) {
                    return;
                }
                editorNS.Serializer.htmlToDom(html, body, options.deserialization);
                this.selectionRestorePoint = null;
                this.update();
                this.toolbar.refreshTools();
            },
            saveSelection: function (range) {
                range = range || this.getRange();
                var container = range.commonAncestorContainer, body = this.body;
                if (container == body || $.contains(body, container)) {
                    this.selectionRestorePoint = new kendo.ui.editor.RestorePoint(range);
                }
            },
            _focusBody: function () {
                var body = this.body;
                var iframe = this.wrapper && this.wrapper.find('iframe')[0];
                var documentElement = this.document.documentElement;
                var activeElement = kendo._activeElement();
                if (activeElement != body && activeElement != iframe) {
                    var scrollTop = documentElement.scrollTop;
                    body.focus();
                    documentElement.scrollTop = scrollTop;
                }
            },
            restoreSelection: function () {
                this._focusBody();
                if (this.selectionRestorePoint) {
                    this.selectRange(this.selectionRestorePoint.toRange());
                }
            },
            focus: function () {
                this.restoreSelection();
            },
            update: function (value) {
                value = value || this.options.encoded ? this.encodedValue() : this.value();
                if (this.textarea) {
                    this.textarea.val(value);
                } else {
                    this._oldValue = value;
                }
            },
            encodedValue: function () {
                return kendo.ui.editor.Dom.encode(this.value());
            },
            createRange: function (document) {
                return kendo.ui.editor.RangeUtils.createRange(document || this.document);
            },
            getSelection: function () {
                return kendo.ui.editor.SelectionUtils.selectionFromDocument(this.document);
            },
            selectRange: function (range) {
                this._focusBody();
                var selection = this.getSelection();
                selection.removeAllRanges();
                selection.addRange(range);
                this.saveSelection(range);
            },
            getRange: function () {
                var selection = this.getSelection(), range = selection && selection.rangeCount > 0 ? selection.getRangeAt(0) : this.createRange(), doc = this.document;
                if (range.startContainer == doc && range.endContainer == doc && !range.startOffset && !range.endOffset) {
                    range.setStart(this.body, 0);
                    range.collapse(true);
                }
                return range;
            },
            selectedHtml: function () {
                return kendo.ui.editor.Serializer.domToXhtml(this.getRange().cloneContents());
            },
            paste: function (html, options) {
                this.focus();
                var command = new kendo.ui.editor.InsertHtmlCommand($.extend({
                    range: this.getRange(),
                    html: html
                }, options));
                command.editor = this;
                command.exec();
            },
            exec: function (name, params) {
                var that = this;
                var command = null;
                var range, tool, prevented;
                if (!name) {
                    throw new Error('kendoEditor.exec(): `name` parameter cannot be empty');
                }
                if (that.body.getAttribute('contenteditable') !== 'true' && name !== 'print' && name !== 'pdf') {
                    return false;
                }
                name = name.toLowerCase();
                if (!that.keyboard.isTypingInProgress()) {
                    that.restoreSelection();
                }
                tool = that.toolbar.toolById(name);
                if (!tool) {
                    for (var id in Editor.defaultTools) {
                        if (id.toLowerCase() == name) {
                            tool = Editor.defaultTools[id];
                            break;
                        }
                    }
                }
                if (tool) {
                    range = that.getRange();
                    if (tool.command) {
                        command = tool.command(extend({ range: range }, params));
                    }
                    prevented = that.trigger('execute', {
                        name: name,
                        command: command
                    });
                    if (prevented) {
                        return;
                    }
                    if (/^(undo|redo)$/i.test(name)) {
                        that.undoRedoStack[name]();
                    } else if (command) {
                        if (!command.managesUndoRedo) {
                            that.undoRedoStack.push(command);
                        }
                        command.editor = that;
                        command.exec();
                        if (command.async) {
                            command.change = proxy(that._selectionChange, that);
                            return;
                        }
                    }
                    that._selectionChange();
                }
            }
        });
        Editor.defaultTools = {
            undo: {
                options: {
                    key: 'Z',
                    ctrl: true
                }
            },
            redo: {
                options: {
                    key: 'Y',
                    ctrl: true
                }
            }
        };
        kendo.ui.plugin(Editor);
        var Tool = Class.extend({
            init: function (options) {
                this.options = options;
            },
            initialize: function (ui, options) {
                ui.attr({
                    unselectable: 'on',
                    title: options.title
                });
                ui.children('.k-tool-text').html(options.title);
            },
            command: function (commandArguments) {
                return new this.options.command(commandArguments);
            },
            update: $.noop
        });
        Tool.exec = function (editor, name, value) {
            editor.exec(name, { value: value });
        };
        var FormatTool = Tool.extend({
            init: function (options) {
                Tool.fn.init.call(this, options);
            },
            command: function (commandArguments) {
                var that = this;
                return new kendo.ui.editor.FormatCommand(extend(commandArguments, { formatter: that.options.formatter }));
            },
            update: function (ui, nodes) {
                var isFormatted = this.options.finder.isFormatted(nodes);
                ui.toggleClass('k-state-selected', isFormatted);
                ui.attr('aria-pressed', isFormatted);
            }
        });
        EditorUtils.registerTool('separator', new Tool({ template: new ToolTemplate({ template: EditorUtils.separatorTemplate }) }));
        var bomFill = browser.msie && browser.version < 9 ? '\uFEFF' : '';
        var emptyElementContent = '\uFEFF';
        if (browser.msie && browser.version == 10) {
            emptyElementContent = ' ';
        }
        extend(kendo.ui, {
            editor: {
                ToolTemplate: ToolTemplate,
                EditorUtils: EditorUtils,
                Tool: Tool,
                FormatTool: FormatTool,
                _bomFill: bomFill,
                emptyElementContent: emptyElementContent
            }
        });
        if (kendo.PDFMixin) {
            kendo.PDFMixin.extend(Editor.prototype);
            Editor.prototype._drawPDF = function () {
                return kendo.drawing.drawDOM(this.body, this.options.pdf);
            };
            Editor.prototype.saveAsPDF = function () {
                var progress = new $.Deferred();
                var promise = progress.promise();
                var args = { promise: promise };
                if (this.trigger('pdfExport', args)) {
                    return;
                }
                var options = this.options.pdf;
                this._drawPDF(progress).then(function (root) {
                    return kendo.drawing.exportPDF(root, options);
                }).done(function (dataURI) {
                    kendo.saveAs({
                        dataURI: dataURI,
                        fileName: options.fileName,
                        proxyURL: options.proxyURL,
                        forceProxy: options.forceProxy
                    });
                    progress.resolve();
                }).fail(function (err) {
                    progress.reject(err);
                });
                return promise;
            };
        }
    }(window.jQuery || window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/dom', ['editor/main'], f);
}(function () {
    (function ($) {
        var kendo = window.kendo, map = $.map, extend = $.extend, browser = kendo.support.browser, STYLE = 'style', FLOAT = 'float', CSSFLOAT = 'cssFloat', STYLEFLOAT = 'styleFloat', CLASS = 'class', KMARKER = 'k-marker';
        function makeMap(items) {
            var obj = {}, i, len;
            for (i = 0, len = items.length; i < len; i++) {
                obj[items[i]] = true;
            }
            return obj;
        }
        var empty = makeMap('area,base,basefont,br,col,frame,hr,img,input,isindex,link,meta,param,embed'.split(',')), nonListBlockElements = 'div,p,h1,h2,h3,h4,h5,h6,address,applet,blockquote,button,center,dd,dir,dl,dt,fieldset,form,frameset,hr,iframe,isindex,map,menu,noframes,noscript,object,pre,script,table,tbody,td,tfoot,th,thead,tr,header,article,nav,footer,section,aside,main,figure,figcaption'.split(','), blockElements = nonListBlockElements.concat([
                'ul',
                'ol',
                'li'
            ]), block = makeMap(blockElements), inlineElements = 'span,em,a,abbr,acronym,applet,b,basefont,bdo,big,br,button,cite,code,del,dfn,font,i,iframe,img,input,ins,kbd,label,map,object,q,s,samp,script,select,small,strike,strong,sub,sup,textarea,tt,u,var,data,time,mark,ruby'.split(','), inline = makeMap(inlineElements), fillAttrs = makeMap('checked,compact,declare,defer,disabled,ismap,multiple,nohref,noresize,noshade,nowrap,readonly,selected'.split(','));
        var normalize = function (node) {
            if (node.nodeType == 1) {
                node.normalize();
            }
        };
        if (browser.msie && browser.version >= 8) {
            normalize = function (parent) {
                if (parent.nodeType == 1 && parent.firstChild) {
                    var prev = parent.firstChild, node = prev;
                    while (true) {
                        node = node.nextSibling;
                        if (!node) {
                            break;
                        }
                        if (node.nodeType == 3 && prev.nodeType == 3) {
                            node.nodeValue = prev.nodeValue + node.nodeValue;
                            Dom.remove(prev);
                        }
                        prev = node;
                    }
                }
            };
        }
        var whitespace = /^\s+$/, emptyspace = /^[\n\r\t]+$/, rgb = /rgb\s*\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)/i, bom = /\ufeff/g, whitespaceOrBom = /^(\s+|\ufeff)$/, persistedScrollTop, cssAttributes = ('color,padding-left,padding-right,padding-top,padding-bottom,' + 'background-color,background-attachment,background-image,background-position,background-repeat,' + 'border-top-style,border-top-width,border-top-color,' + 'border-bottom-style,border-bottom-width,border-bottom-color,' + 'border-left-style,border-left-width,border-left-color,' + 'border-right-style,border-right-width,border-right-color,' + 'font-family,font-size,font-style,font-variant,font-weight,line-height').split(','), htmlRe = /[<>\&]/g, entityRe = /[\u00A0-\u2666<>\&]/g, entityTable = {
                34: 'quot',
                38: 'amp',
                39: 'apos',
                60: 'lt',
                62: 'gt',
                160: 'nbsp',
                161: 'iexcl',
                162: 'cent',
                163: 'pound',
                164: 'curren',
                165: 'yen',
                166: 'brvbar',
                167: 'sect',
                168: 'uml',
                169: 'copy',
                170: 'ordf',
                171: 'laquo',
                172: 'not',
                173: 'shy',
                174: 'reg',
                175: 'macr',
                176: 'deg',
                177: 'plusmn',
                178: 'sup2',
                179: 'sup3',
                180: 'acute',
                181: 'micro',
                182: 'para',
                183: 'middot',
                184: 'cedil',
                185: 'sup1',
                186: 'ordm',
                187: 'raquo',
                188: 'frac14',
                189: 'frac12',
                190: 'frac34',
                191: 'iquest',
                192: 'Agrave',
                193: 'Aacute',
                194: 'Acirc',
                195: 'Atilde',
                196: 'Auml',
                197: 'Aring',
                198: 'AElig',
                199: 'Ccedil',
                200: 'Egrave',
                201: 'Eacute',
                202: 'Ecirc',
                203: 'Euml',
                204: 'Igrave',
                205: 'Iacute',
                206: 'Icirc',
                207: 'Iuml',
                208: 'ETH',
                209: 'Ntilde',
                210: 'Ograve',
                211: 'Oacute',
                212: 'Ocirc',
                213: 'Otilde',
                214: 'Ouml',
                215: 'times',
                216: 'Oslash',
                217: 'Ugrave',
                218: 'Uacute',
                219: 'Ucirc',
                220: 'Uuml',
                221: 'Yacute',
                222: 'THORN',
                223: 'szlig',
                224: 'agrave',
                225: 'aacute',
                226: 'acirc',
                227: 'atilde',
                228: 'auml',
                229: 'aring',
                230: 'aelig',
                231: 'ccedil',
                232: 'egrave',
                233: 'eacute',
                234: 'ecirc',
                235: 'euml',
                236: 'igrave',
                237: 'iacute',
                238: 'icirc',
                239: 'iuml',
                240: 'eth',
                241: 'ntilde',
                242: 'ograve',
                243: 'oacute',
                244: 'ocirc',
                245: 'otilde',
                246: 'ouml',
                247: 'divide',
                248: 'oslash',
                249: 'ugrave',
                250: 'uacute',
                251: 'ucirc',
                252: 'uuml',
                253: 'yacute',
                254: 'thorn',
                255: 'yuml',
                402: 'fnof',
                913: 'Alpha',
                914: 'Beta',
                915: 'Gamma',
                916: 'Delta',
                917: 'Epsilon',
                918: 'Zeta',
                919: 'Eta',
                920: 'Theta',
                921: 'Iota',
                922: 'Kappa',
                923: 'Lambda',
                924: 'Mu',
                925: 'Nu',
                926: 'Xi',
                927: 'Omicron',
                928: 'Pi',
                929: 'Rho',
                931: 'Sigma',
                932: 'Tau',
                933: 'Upsilon',
                934: 'Phi',
                935: 'Chi',
                936: 'Psi',
                937: 'Omega',
                945: 'alpha',
                946: 'beta',
                947: 'gamma',
                948: 'delta',
                949: 'epsilon',
                950: 'zeta',
                951: 'eta',
                952: 'theta',
                953: 'iota',
                954: 'kappa',
                955: 'lambda',
                956: 'mu',
                957: 'nu',
                958: 'xi',
                959: 'omicron',
                960: 'pi',
                961: 'rho',
                962: 'sigmaf',
                963: 'sigma',
                964: 'tau',
                965: 'upsilon',
                966: 'phi',
                967: 'chi',
                968: 'psi',
                969: 'omega',
                977: 'thetasym',
                978: 'upsih',
                982: 'piv',
                8226: 'bull',
                8230: 'hellip',
                8242: 'prime',
                8243: 'Prime',
                8254: 'oline',
                8260: 'frasl',
                8472: 'weierp',
                8465: 'image',
                8476: 'real',
                8482: 'trade',
                8501: 'alefsym',
                8592: 'larr',
                8593: 'uarr',
                8594: 'rarr',
                8595: 'darr',
                8596: 'harr',
                8629: 'crarr',
                8656: 'lArr',
                8657: 'uArr',
                8658: 'rArr',
                8659: 'dArr',
                8660: 'hArr',
                8704: 'forall',
                8706: 'part',
                8707: 'exist',
                8709: 'empty',
                8711: 'nabla',
                8712: 'isin',
                8713: 'notin',
                8715: 'ni',
                8719: 'prod',
                8721: 'sum',
                8722: 'minus',
                8727: 'lowast',
                8730: 'radic',
                8733: 'prop',
                8734: 'infin',
                8736: 'ang',
                8743: 'and',
                8744: 'or',
                8745: 'cap',
                8746: 'cup',
                8747: 'int',
                8756: 'there4',
                8764: 'sim',
                8773: 'cong',
                8776: 'asymp',
                8800: 'ne',
                8801: 'equiv',
                8804: 'le',
                8805: 'ge',
                8834: 'sub',
                8835: 'sup',
                8836: 'nsub',
                8838: 'sube',
                8839: 'supe',
                8853: 'oplus',
                8855: 'otimes',
                8869: 'perp',
                8901: 'sdot',
                8968: 'lceil',
                8969: 'rceil',
                8970: 'lfloor',
                8971: 'rfloor',
                9001: 'lang',
                9002: 'rang',
                9674: 'loz',
                9824: 'spades',
                9827: 'clubs',
                9829: 'hearts',
                9830: 'diams',
                338: 'OElig',
                339: 'oelig',
                352: 'Scaron',
                353: 'scaron',
                376: 'Yuml',
                710: 'circ',
                732: 'tilde',
                8194: 'ensp',
                8195: 'emsp',
                8201: 'thinsp',
                8204: 'zwnj',
                8205: 'zwj',
                8206: 'lrm',
                8207: 'rlm',
                8211: 'ndash',
                8212: 'mdash',
                8216: 'lsquo',
                8217: 'rsquo',
                8218: 'sbquo',
                8220: 'ldquo',
                8221: 'rdquo',
                8222: 'bdquo',
                8224: 'dagger',
                8225: 'Dagger',
                8240: 'permil',
                8249: 'lsaquo',
                8250: 'rsaquo',
                8364: 'euro'
            };
        var Dom = {
            block: block,
            inline: inline,
            findNodeIndex: function (node, skipText) {
                var i = 0;
                if (!node) {
                    return -1;
                }
                while (true) {
                    node = node.previousSibling;
                    if (!node) {
                        break;
                    }
                    if (!(skipText && node.nodeType == 3)) {
                        i++;
                    }
                }
                return i;
            },
            isDataNode: function (node) {
                return node && node.nodeValue !== null && node.data !== null;
            },
            isAncestorOf: function (parent, node) {
                try {
                    return !Dom.isDataNode(parent) && ($.contains(parent, Dom.isDataNode(node) ? node.parentNode : node) || node.parentNode == parent);
                } catch (e) {
                    return false;
                }
            },
            isAncestorOrSelf: function (root, node) {
                return Dom.isAncestorOf(root, node) || root == node;
            },
            findClosestAncestor: function (root, node) {
                if (Dom.isAncestorOf(root, node)) {
                    while (node && node.parentNode != root) {
                        node = node.parentNode;
                    }
                }
                return node;
            },
            getNodeLength: function (node) {
                return Dom.isDataNode(node) ? node.length : node.childNodes.length;
            },
            splitDataNode: function (node, offset) {
                var newNode = node.cloneNode(false);
                var denormalizedText = '';
                var iterator = node.nextSibling;
                var temp;
                while (iterator && iterator.nodeType == 3 && iterator.nodeValue) {
                    denormalizedText += iterator.nodeValue;
                    temp = iterator;
                    iterator = iterator.nextSibling;
                    Dom.remove(temp);
                }
                node.deleteData(offset, node.length);
                newNode.deleteData(0, offset);
                newNode.nodeValue += denormalizedText;
                Dom.insertAfter(newNode, node);
            },
            attrEquals: function (node, attributes) {
                for (var key in attributes) {
                    var value = node[key];
                    if (key == FLOAT) {
                        value = node[kendo.support.cssFloat ? CSSFLOAT : STYLEFLOAT];
                    }
                    if (typeof value == 'object') {
                        if (!Dom.attrEquals(value, attributes[key])) {
                            return false;
                        }
                    } else if (value != attributes[key]) {
                        return false;
                    }
                }
                return true;
            },
            blockParentOrBody: function (node) {
                return Dom.parentOfType(node, blockElements) || node.ownerDocument.body;
            },
            blockParents: function (nodes) {
                var blocks = [], i, len;
                for (i = 0, len = nodes.length; i < len; i++) {
                    var block = Dom.parentOfType(nodes[i], Dom.blockElements);
                    if (block && $.inArray(block, blocks) < 0) {
                        blocks.push(block);
                    }
                }
                return blocks;
            },
            windowFromDocument: function (document) {
                return document.defaultView || document.parentWindow;
            },
            normalize: normalize,
            blockElements: blockElements,
            nonListBlockElements: nonListBlockElements,
            inlineElements: inlineElements,
            empty: empty,
            fillAttrs: fillAttrs,
            toHex: function (color) {
                var matches = rgb.exec(color);
                if (!matches) {
                    return color;
                }
                return '#' + map(matches.slice(1), function (x) {
                    x = parseInt(x, 10).toString(16);
                    return x.length > 1 ? x : '0' + x;
                }).join('');
            },
            encode: function (value, options) {
                var encodableChars = !options || options.entities ? entityRe : htmlRe;
                return value.replace(encodableChars, function (c) {
                    var charCode = c.charCodeAt(0);
                    var entity = entityTable[charCode];
                    return entity ? '&' + entity + ';' : c;
                });
            },
            stripBom: function (text) {
                return (text || '').replace(bom, '');
            },
            stripBomNode: function (node) {
                if (node && node.nodeType === 3 && node.nodeValue === '\uFEFF') {
                    node.parentNode.removeChild(node);
                }
            },
            insignificant: function (node) {
                var attr = node.attributes;
                return node.className == 'k-marker' || Dom.is(node, 'br') && (node.className == 'k-br' || attr._moz_dirty || attr._moz_editor_bogus_node);
            },
            significantNodes: function (nodes) {
                return $.grep(nodes, function (child) {
                    var name = Dom.name(child);
                    if (name == 'br') {
                        return false;
                    } else if (Dom.insignificant(child)) {
                        return false;
                    } else if (child.nodeType == 3 && whitespaceOrBom.test(child.nodeValue)) {
                        return false;
                    } else if (child.nodeType == 1 && !empty[name] && Dom.emptyNode(child)) {
                        return false;
                    }
                    return true;
                });
            },
            emptyNode: function (node) {
                return node.nodeType == 1 && !Dom.significantNodes(node.childNodes).length;
            },
            name: function (node) {
                return node.nodeName.toLowerCase();
            },
            significantChildNodes: function (node) {
                return $.grep(node.childNodes, function (child) {
                    return child.nodeType != 3 || !Dom.isWhitespace(child);
                });
            },
            lastTextNode: function (node) {
                var result = null;
                if (node.nodeType == 3) {
                    return node;
                }
                for (var child = node.lastChild; child; child = child.previousSibling) {
                    result = Dom.lastTextNode(child);
                    if (result) {
                        return result;
                    }
                }
                return result;
            },
            is: function (node, nodeName) {
                return Dom.name(node) == nodeName;
            },
            isMarker: function (node) {
                return node.className == KMARKER;
            },
            isWhitespace: function (node) {
                return whitespace.test(node.nodeValue);
            },
            isEmptyspace: function (node) {
                return emptyspace.test(node.nodeValue);
            },
            isBlock: function (node) {
                return block[Dom.name(node)];
            },
            isEmpty: function (node) {
                return empty[Dom.name(node)];
            },
            isInline: function (node) {
                return inline[Dom.name(node)];
            },
            scrollContainer: function (doc) {
                var wnd = Dom.windowFromDocument(doc), scrollContainer = (wnd.contentWindow || wnd).document || wnd.ownerDocument || wnd;
                if (kendo.support.browser.webkit || scrollContainer.compatMode == 'BackCompat') {
                    scrollContainer = scrollContainer.body;
                } else {
                    scrollContainer = scrollContainer.documentElement;
                }
                return scrollContainer;
            },
            scrollTo: function (node) {
                var element = $(Dom.isDataNode(node) ? node.parentNode : node), wnd = Dom.windowFromDocument(node.ownerDocument), windowHeight = wnd.innerHeight, elementTop, elementHeight, scrollContainer = Dom.scrollContainer(node.ownerDocument);
                elementTop = element.offset().top;
                elementHeight = element[0].offsetHeight;
                if (!elementHeight) {
                    elementHeight = parseInt(element.css('line-height'), 10) || Math.ceil(1.2 * parseInt(element.css('font-size'), 10)) || 15;
                }
                if (elementHeight + elementTop > scrollContainer.scrollTop + windowHeight) {
                    scrollContainer.scrollTop = elementHeight + elementTop - windowHeight;
                }
            },
            persistScrollTop: function (doc) {
                persistedScrollTop = Dom.scrollContainer(doc).scrollTop;
            },
            restoreScrollTop: function (doc) {
                Dom.scrollContainer(doc).scrollTop = persistedScrollTop;
            },
            insertAt: function (parent, newElement, position) {
                parent.insertBefore(newElement, parent.childNodes[position] || null);
            },
            insertBefore: function (newElement, referenceElement) {
                if (referenceElement.parentNode) {
                    return referenceElement.parentNode.insertBefore(newElement, referenceElement);
                } else {
                    return referenceElement;
                }
            },
            insertAfter: function (newElement, referenceElement) {
                return referenceElement.parentNode.insertBefore(newElement, referenceElement.nextSibling);
            },
            remove: function (node) {
                node.parentNode.removeChild(node);
            },
            removeTextSiblings: function (node) {
                var parentNode = node.parentNode;
                while (node.nextSibling && node.nextSibling.nodeType == 3) {
                    parentNode.removeChild(node.nextSibling);
                }
                while (node.previousSibling && node.previousSibling.nodeType == 3) {
                    parentNode.removeChild(node.previousSibling);
                }
            },
            trim: function (parent) {
                for (var i = parent.childNodes.length - 1; i >= 0; i--) {
                    var node = parent.childNodes[i];
                    if (Dom.isDataNode(node)) {
                        if (!Dom.stripBom(node.nodeValue).length) {
                            Dom.remove(node);
                        }
                        if (Dom.isWhitespace(node)) {
                            Dom.insertBefore(node, parent);
                        }
                    } else if (node.className != KMARKER) {
                        Dom.trim(node);
                        if (!node.childNodes.length && !Dom.isEmpty(node)) {
                            Dom.remove(node);
                        }
                    }
                }
                return parent;
            },
            closest: function (node, tag) {
                while (node && Dom.name(node) != tag) {
                    node = node.parentNode;
                }
                return node;
            },
            sibling: function (node, direction) {
                do {
                    node = node[direction];
                } while (node && node.nodeType != 1);
                return node;
            },
            next: function (node) {
                return Dom.sibling(node, 'nextSibling');
            },
            prev: function (node) {
                return Dom.sibling(node, 'previousSibling');
            },
            parentOfType: function (node, tags) {
                do {
                    node = node.parentNode;
                } while (node && !Dom.ofType(node, tags));
                return node;
            },
            ofType: function (node, tags) {
                return $.inArray(Dom.name(node), tags) >= 0;
            },
            changeTag: function (referenceElement, tagName, skipAttributes) {
                var newElement = Dom.create(referenceElement.ownerDocument, tagName), attributes = referenceElement.attributes, i, len, name, value, attribute;
                if (!skipAttributes) {
                    for (i = 0, len = attributes.length; i < len; i++) {
                        attribute = attributes[i];
                        if (attribute.specified) {
                            name = attribute.nodeName;
                            value = attribute.nodeValue;
                            if (name == CLASS) {
                                newElement.className = value;
                            } else if (name == STYLE) {
                                newElement.style.cssText = referenceElement.style.cssText;
                            } else {
                                newElement.setAttribute(name, value);
                            }
                        }
                    }
                }
                while (referenceElement.firstChild) {
                    newElement.appendChild(referenceElement.firstChild);
                }
                Dom.insertBefore(newElement, referenceElement);
                Dom.remove(referenceElement);
                return newElement;
            },
            editableParent: function (node) {
                while (node && (node.nodeType == 3 || node.contentEditable !== 'true')) {
                    node = node.parentNode;
                }
                return node;
            },
            wrap: function (node, wrapper) {
                Dom.insertBefore(wrapper, node);
                wrapper.appendChild(node);
                return wrapper;
            },
            unwrap: function (node) {
                var parent = node.parentNode;
                while (node.firstChild) {
                    parent.insertBefore(node.firstChild, node);
                }
                parent.removeChild(node);
            },
            create: function (document, tagName, attributes) {
                return Dom.attr(document.createElement(tagName), attributes);
            },
            attr: function (element, attributes) {
                attributes = extend({}, attributes);
                if (attributes && STYLE in attributes) {
                    Dom.style(element, attributes.style);
                    delete attributes.style;
                }
                for (var attr in attributes) {
                    if (attributes[attr] === null) {
                        element.removeAttribute(attr);
                        delete attributes[attr];
                    } else if (attr == 'className') {
                        element[attr] = attributes[attr];
                    }
                }
                return extend(element, attributes);
            },
            style: function (node, value) {
                $(node).css(value || {});
            },
            unstyle: function (node, value) {
                for (var key in value) {
                    if (key == FLOAT) {
                        key = kendo.support.cssFloat ? CSSFLOAT : STYLEFLOAT;
                    }
                    node.style[key] = '';
                }
                if (node.style.cssText === '') {
                    node.removeAttribute(STYLE);
                }
            },
            inlineStyle: function (body, name, attributes) {
                var span = $(Dom.create(body.ownerDocument, name, attributes)), style;
                body.appendChild(span[0]);
                style = map(cssAttributes, function (value) {
                    if (browser.msie && value == 'line-height' && span.css(value) == '1px') {
                        return 'line-height:1.5';
                    } else {
                        return value + ':' + span.css(value);
                    }
                }).join(';');
                span.remove();
                return style;
            },
            getEffectiveBackground: function (element) {
                var backgroundStyle = element.css('background-color');
                if (backgroundStyle.indexOf('rgba(0, 0, 0, 0') < 0 && backgroundStyle !== 'transparent') {
                    return backgroundStyle;
                } else if (element[0].tagName.toLowerCase() === 'html') {
                    return 'Window';
                } else {
                    return Dom.getEffectiveBackground(element.parent());
                }
            },
            innerText: function (node) {
                var text = node.innerHTML;
                text = text.replace(/<!--(.|\s)*?-->/gi, '');
                text = text.replace(/<\/?[^>]+?\/?>/gm, '');
                return text;
            },
            removeClass: function (node, classNames) {
                var className = ' ' + node.className + ' ', classes = classNames.split(' '), i, len;
                for (i = 0, len = classes.length; i < len; i++) {
                    className = className.replace(' ' + classes[i] + ' ', ' ');
                }
                className = $.trim(className);
                if (className.length) {
                    node.className = className;
                } else {
                    node.removeAttribute(CLASS);
                }
            },
            commonAncestor: function () {
                var count = arguments.length, paths = [], minPathLength = Infinity, output = null, i, ancestors, node, first, j;
                if (!count) {
                    return null;
                }
                if (count == 1) {
                    return arguments[0];
                }
                for (i = 0; i < count; i++) {
                    ancestors = [];
                    node = arguments[i];
                    while (node) {
                        ancestors.push(node);
                        node = node.parentNode;
                    }
                    paths.push(ancestors.reverse());
                    minPathLength = Math.min(minPathLength, ancestors.length);
                }
                if (count == 1) {
                    return paths[0][0];
                }
                for (i = 0; i < minPathLength; i++) {
                    first = paths[0][i];
                    for (j = 1; j < count; j++) {
                        if (first != paths[j][i]) {
                            return output;
                        }
                    }
                    output = first;
                }
                return output;
            },
            closestSplittableParent: function (nodes) {
                var result;
                if (nodes.length == 1) {
                    result = Dom.parentOfType(nodes[0], [
                        'ul',
                        'ol'
                    ]);
                } else {
                    result = Dom.commonAncestor.apply(null, nodes);
                }
                if (!result) {
                    result = Dom.parentOfType(nodes[0], [
                        'p',
                        'td'
                    ]) || nodes[0].ownerDocument.body;
                }
                if (Dom.isInline(result)) {
                    result = Dom.blockParentOrBody(result);
                }
                var editableParents = map(nodes, Dom.editableParent);
                var editableAncestor = Dom.commonAncestor(editableParents)[0];
                if ($.contains(result, editableAncestor)) {
                    result = editableAncestor;
                }
                return result;
            },
            closestEditable: function (node, types) {
                var closest;
                var editable = Dom.editableParent(node);
                if (Dom.ofType(node, types)) {
                    closest = node;
                } else {
                    closest = Dom.parentOfType(node, types);
                }
                if (closest && editable && $.contains(closest, editable)) {
                    closest = editable;
                } else if (!closest && editable) {
                    closest = editable;
                }
                return closest;
            },
            closestEditableOfType: function (node, types) {
                var editable = Dom.closestEditable(node, types);
                if (editable && Dom.ofType(editable, types)) {
                    return editable;
                }
            },
            filter: function (tagName, nodes, invert) {
                var i = 0;
                var len = nodes.length;
                var result = [];
                var name;
                for (; i < len; i++) {
                    name = Dom.name(nodes[i]);
                    if (!invert && name == tagName || invert && name != tagName) {
                        result.push(nodes[i]);
                    }
                }
                return result;
            },
            ensureTrailingBreaks: function (node) {
                var elements = $(node).find('p,td,th');
                var length = elements.length;
                var i = 0;
                if (length) {
                    for (; i < length; i++) {
                        Dom.ensureTrailingBreak(elements[i]);
                    }
                } else {
                    Dom.ensureTrailingBreak(node);
                }
            },
            removeTrailingBreak: function (node) {
                $(node).find('br[type=_moz],.k-br').remove();
            },
            ensureTrailingBreak: function (node) {
                Dom.removeTrailingBreak(node);
                var lastChild = node.lastChild;
                var name = lastChild && Dom.name(lastChild);
                var br;
                if (!name || name != 'br' && name != 'img' || name == 'br' && lastChild.className != 'k-br') {
                    br = node.ownerDocument.createElement('br');
                    br.className = 'k-br';
                    node.appendChild(br);
                }
            }
        };
        kendo.ui.editor.Dom = Dom;
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/serializer', ['editor/dom'], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo;
        var Editor = kendo.ui.editor;
        var dom = Editor.Dom;
        var extend = $.extend;
        var fontSizeMappings = 'xx-small,x-small,small,medium,large,x-large,xx-large'.split(',');
        var quoteRe = /"/g;
        var brRe = /<br[^>]*>/i;
        var pixelRe = /^\d+(\.\d*)?(px)?$/i;
        var emptyPRe = /<p>(?:&nbsp;)?<\/p>/i;
        var cssDeclaration = /(\*?[-#\/\*\\\w]+(?:\[[0-9a-z_-]+\])?)\s*:\s*((?:'(?:\\'|.)*?'|"(?:\\"|.)*?"|\([^\)]*?\)|[^};])+)/g;
        var sizzleAttr = /^sizzle-\d+/i;
        var scriptAttr = /^k-script-/i;
        var onerrorRe = /\s*onerror\s*=\s*(?:'|")?([^'">\s]*)(?:'|")?/i;
        var div = document.createElement('div');
        div.innerHTML = ' <hr>';
        var supportsLeadingWhitespace = div.firstChild.nodeType === 3;
        div = null;
        var isFunction = $.isFunction;
        var Serializer = {
            toEditableHtml: function (html) {
                var br = '<br class="k-br">';
                html = html || '';
                return html.replace(/<!\[CDATA\[(.*)?\]\]>/g, '<!--[CDATA[$1]]-->').replace(/<(\/?)script([^>]*)>/gi, '<$1k:script$2>').replace(/<img([^>]*)>/gi, function (match) {
                    return match.replace(onerrorRe, '');
                }).replace(/(<\/?img[^>]*>)[\r\n\v\f\t ]+/gi, '$1').replace(/^<(table|blockquote)/i, br + '<$1').replace(/^[\s]*(&nbsp;|\u00a0)/i, '$1').replace(/<\/(table|blockquote)>$/i, '</$1>' + br);
            },
            _fillEmptyElements: function (body) {
                $(body).find('p,td').each(function () {
                    var p = $(this);
                    if (/^\s*$/g.test(p.text()) && !p.find('img,input').length) {
                        var node = this;
                        while (node.firstChild && node.firstChild.nodeType != 3) {
                            node = node.firstChild;
                        }
                        if (node.nodeType == 1 && !dom.empty[dom.name(node)]) {
                            node.innerHTML = kendo.ui.editor.emptyElementContent;
                        }
                    }
                });
            },
            _removeSystemElements: function (body) {
                $('.k-paste-container', body).remove();
            },
            _resetOrderedLists: function (root) {
                var ols = root.getElementsByTagName('ol'), i, ol, originalStart;
                for (i = 0; i < ols.length; i++) {
                    ol = ols[i];
                    originalStart = ol.getAttribute('start');
                    ol.setAttribute('start', 1);
                    if (originalStart) {
                        ol.setAttribute('start', originalStart);
                    } else {
                        ol.removeAttribute(originalStart);
                    }
                }
            },
            _preventScriptExecution: function (root) {
                $(root).find('*').each(function () {
                    var attributes = this.attributes;
                    var attribute, i, l, name;
                    for (i = 0, l = attributes.length; i < l; i++) {
                        attribute = attributes[i];
                        name = attribute.nodeName;
                        if (attribute.specified && /^on/i.test(name)) {
                            this.setAttribute('k-script-' + name, attribute.value);
                            this.removeAttribute(name);
                        }
                    }
                });
            },
            htmlToDom: function (html, root, options) {
                var browser = kendo.support.browser;
                var msie = browser.msie;
                var legacyIE = msie && browser.version < 9;
                var originalSrc = 'originalsrc';
                var originalHref = 'originalhref';
                var o = options || {};
                html = Serializer.toEditableHtml(html);
                if (legacyIE) {
                    html = '<br/>' + html;
                    html = html.replace(/href\s*=\s*(?:'|")?([^'">\s]*)(?:'|")?/, originalHref + '="$1"');
                    html = html.replace(/src\s*=\s*(?:'|")?([^'">\s]*)(?:'|")?/, originalSrc + '="$1"');
                }
                if (isFunction(o.custom)) {
                    html = o.custom(html) || html;
                }
                root.innerHTML = html;
                if (legacyIE) {
                    dom.remove(root.firstChild);
                    $(root).find('k\\:script,script,link,img,a').each(function () {
                        var node = this;
                        if (node[originalHref]) {
                            node.setAttribute('href', node[originalHref]);
                            node.removeAttribute(originalHref);
                        }
                        if (node[originalSrc]) {
                            node.setAttribute('src', node[originalSrc]);
                            node.removeAttribute(originalSrc);
                        }
                    });
                } else if (msie) {
                    dom.normalize(root);
                    Serializer._resetOrderedLists(root);
                }
                Serializer._preventScriptExecution(root);
                Serializer._fillEmptyElements(root);
                Serializer._removeSystemElements(root);
                $('table', root).addClass('k-table');
                return root;
            },
            domToXhtml: function (root, options) {
                var result = [];
                function semanticFilter(attributes) {
                    return $.grep(attributes, function (attr) {
                        return attr.name != 'style';
                    });
                }
                var tagMap = {
                    iframe: {
                        start: function (node) {
                            result.push('<iframe');
                            attr(node);
                            result.push('>');
                        },
                        end: function () {
                            result.push('</iframe>');
                        }
                    },
                    'k:script': {
                        start: function (node) {
                            result.push('<script');
                            attr(node);
                            result.push('>');
                        },
                        end: function () {
                            result.push('</script>');
                        },
                        skipEncoding: true
                    },
                    span: {
                        semantic: true,
                        start: function (node) {
                            var style = node.style;
                            var attributes = specifiedAttributes(node);
                            var semanticAttributes = semanticFilter(attributes);
                            if (semanticAttributes.length) {
                                result.push('<span');
                                attr(node, semanticAttributes);
                                result.push('>');
                            }
                            if (style.textDecoration == 'underline') {
                                result.push('<u>');
                            }
                            var font = [];
                            if (style.color) {
                                font.push('color="' + dom.toHex(style.color) + '"');
                            }
                            if (style.fontFamily) {
                                font.push('face="' + style.fontFamily + '"');
                            }
                            if (style.fontSize) {
                                var size = $.inArray(style.fontSize, fontSizeMappings);
                                font.push('size="' + size + '"');
                            }
                            if (font.length) {
                                result.push('<font ' + font.join(' ') + '>');
                            }
                        },
                        end: function (node) {
                            var style = node.style;
                            if (style.color || style.fontFamily || style.fontSize) {
                                result.push('</font>');
                            }
                            if (style.textDecoration == 'underline') {
                                result.push('</u>');
                            }
                            if (semanticFilter(specifiedAttributes(node)).length) {
                                result.push('</span>');
                            }
                        }
                    },
                    strong: {
                        semantic: true,
                        start: function () {
                            result.push('<b>');
                        },
                        end: function () {
                            result.push('</b>');
                        }
                    },
                    em: {
                        semantic: true,
                        start: function () {
                            result.push('<i>');
                        },
                        end: function () {
                            result.push('</i>');
                        }
                    },
                    b: {
                        semantic: false,
                        start: function () {
                            result.push('<strong>');
                        },
                        end: function () {
                            result.push('</strong>');
                        }
                    },
                    i: {
                        semantic: false,
                        start: function () {
                            result.push('<em>');
                        },
                        end: function () {
                            result.push('</em>');
                        }
                    },
                    u: {
                        semantic: false,
                        start: function () {
                            result.push('<span style="text-decoration:underline;">');
                        },
                        end: function () {
                            result.push('</span>');
                        }
                    },
                    font: {
                        semantic: false,
                        start: function (node) {
                            result.push('<span style="');
                            var color = node.getAttribute('color');
                            var size = fontSizeMappings[node.getAttribute('size')];
                            var face = node.getAttribute('face');
                            if (color) {
                                result.push('color:');
                                result.push(dom.toHex(color));
                                result.push(';');
                            }
                            if (face) {
                                result.push('font-family:');
                                result.push(face);
                                result.push(';');
                            }
                            if (size) {
                                result.push('font-size:');
                                result.push(size);
                                result.push(';');
                            }
                            result.push('">');
                        },
                        end: function () {
                            result.push('</span>');
                        }
                    }
                };
                tagMap.script = tagMap['k:script'];
                options = options || {};
                if (typeof options.semantic == 'undefined') {
                    options.semantic = true;
                }
                function cssProperties(cssText) {
                    var trim = $.trim;
                    var css = trim(cssText);
                    var match;
                    var property, value;
                    var properties = [];
                    cssDeclaration.lastIndex = 0;
                    while (true) {
                        match = cssDeclaration.exec(css);
                        if (!match) {
                            break;
                        }
                        property = trim(match[1].toLowerCase());
                        value = trim(match[2]);
                        if (property == 'font-size-adjust' || property == 'font-stretch') {
                            continue;
                        }
                        if (property.indexOf('color') >= 0) {
                            value = dom.toHex(value);
                        } else if (property.indexOf('font') >= 0) {
                            value = value.replace(quoteRe, '\'');
                        } else if (/\burl\(/g.test(value)) {
                            value = value.replace(quoteRe, '');
                        }
                        properties.push({
                            property: property,
                            value: value
                        });
                    }
                    return properties;
                }
                function styleAttr(cssText) {
                    var properties = cssProperties(cssText);
                    var i;
                    for (i = 0; i < properties.length; i++) {
                        result.push(properties[i].property);
                        result.push(':');
                        result.push(properties[i].value);
                        result.push(';');
                    }
                }
                function specifiedAttributes(node) {
                    var result = [];
                    var attributes = node.attributes;
                    var attribute, i, l;
                    var name, value, specified;
                    for (i = 0, l = attributes.length; i < l; i++) {
                        attribute = attributes[i];
                        name = attribute.nodeName;
                        value = attribute.value;
                        specified = attribute.specified;
                        if (name == 'value' && 'value' in node && node.value) {
                            specified = true;
                        } else if (name == 'type' && value == 'text') {
                            specified = true;
                        } else if (name == 'class' && !value) {
                            specified = false;
                        } else if (sizzleAttr.test(name)) {
                            specified = false;
                        } else if (name == 'complete') {
                            specified = false;
                        } else if (name == 'altHtml') {
                            specified = false;
                        } else if (name == 'start' && dom.is(node, 'ul')) {
                            specified = false;
                        } else if (name == 'start' && dom.is(node, 'ol') && value == '1') {
                            specified = false;
                        } else if (name.indexOf('_moz') >= 0) {
                            specified = false;
                        } else if (scriptAttr.test(name)) {
                            specified = !!options.scripts;
                        }
                        if (specified) {
                            result.push(attribute);
                        }
                    }
                    return result;
                }
                function attr(node, attributes) {
                    var i, l, attribute, name, value;
                    attributes = attributes || specifiedAttributes(node);
                    if (dom.is(node, 'img')) {
                        var width = node.style.width, height = node.style.height, $node = $(node);
                        if (width && pixelRe.test(width)) {
                            $node.attr('width', parseInt(width, 10));
                            dom.unstyle(node, { width: undefined });
                        }
                        if (height && pixelRe.test(height)) {
                            $node.attr('height', parseInt(height, 10));
                            dom.unstyle(node, { height: undefined });
                        }
                    }
                    if (!attributes.length) {
                        return;
                    }
                    attributes.sort(function (a, b) {
                        return a.nodeName > b.nodeName ? 1 : a.nodeName < b.nodeName ? -1 : 0;
                    });
                    for (i = 0, l = attributes.length; i < l; i++) {
                        attribute = attributes[i];
                        name = attribute.nodeName;
                        value = attribute.value;
                        if (name == 'class' && value == 'k-table') {
                            continue;
                        }
                        name = name.replace(scriptAttr, '');
                        result.push(' ');
                        result.push(name);
                        result.push('="');
                        if (name == 'style') {
                            styleAttr(value || node.style.cssText);
                        } else if (name == 'src' || name == 'href') {
                            result.push(kendo.htmlEncode(node.getAttribute(name, 2)));
                        } else {
                            result.push(dom.fillAttrs[name] ? name : value);
                        }
                        result.push('"');
                    }
                }
                function children(node, skip, skipEncoding) {
                    for (var childNode = node.firstChild; childNode; childNode = childNode.nextSibling) {
                        child(childNode, skip, skipEncoding);
                    }
                }
                function text(node) {
                    return node.nodeValue.replace(/\ufeff/g, '');
                }
                function isEmptyBomNode(node) {
                    if (node.nodeValue === '\uFEFF') {
                        do {
                            node = node.parentNode;
                            if (dom.is(node, 'td') || node.childNodes.length !== 1) {
                                return false;
                            }
                        } while (!dom.isBlock(node));
                        return true;
                    }
                    return false;
                }
                function child(node, skip, skipEncoding) {
                    var nodeType = node.nodeType, tagName, mapper, parent, value, previous;
                    if (nodeType == 1) {
                        tagName = dom.name(node);
                        if (!tagName || dom.insignificant(node)) {
                            return;
                        }
                        if (!options.scripts && (tagName == 'script' || tagName == 'k:script')) {
                            return;
                        }
                        mapper = tagMap[tagName];
                        if (mapper) {
                            if (typeof mapper.semantic == 'undefined' || options.semantic ^ mapper.semantic) {
                                mapper.start(node);
                                children(node, false, mapper.skipEncoding);
                                mapper.end(node);
                                return;
                            }
                        }
                        result.push('<');
                        result.push(tagName);
                        attr(node);
                        if (dom.empty[tagName]) {
                            result.push(' />');
                        } else {
                            result.push('>');
                            children(node, skip || dom.is(node, 'pre'));
                            result.push('</');
                            result.push(tagName);
                            result.push('>');
                        }
                    } else if (nodeType == 3) {
                        if (isEmptyBomNode(node)) {
                            result.push('&nbsp;');
                            return;
                        }
                        value = text(node);
                        if (!skip && supportsLeadingWhitespace) {
                            parent = node.parentNode;
                            previous = node.previousSibling;
                            if (!previous) {
                                previous = (dom.isInline(parent) ? parent : node).previousSibling;
                            }
                            if (!previous || previous.innerHTML === '' || dom.isBlock(previous)) {
                                value = value.replace(/^[\r\n\v\f\t ]+/, '');
                            }
                            value = value.replace(/ +/, ' ');
                        }
                        result.push(skipEncoding ? value : dom.encode(value, options));
                    } else if (nodeType == 4) {
                        result.push('<![CDATA[');
                        result.push(node.data);
                        result.push(']]>');
                    } else if (nodeType == 8) {
                        if (node.data.indexOf('[CDATA[') < 0) {
                            result.push('<!--');
                            result.push(node.data);
                            result.push('-->');
                        } else {
                            result.push('<!');
                            result.push(node.data);
                            result.push('>');
                        }
                    }
                }
                function textOnly(root) {
                    var childrenCount = root.childNodes.length;
                    var textChild = childrenCount && root.firstChild.nodeType == 3;
                    return textChild && (childrenCount == 1 || childrenCount == 2 && dom.insignificant(root.lastChild));
                }
                function runCustom() {
                    if ($.isFunction(options.custom)) {
                        result = options.custom(result) || result;
                    }
                }
                if (textOnly(root)) {
                    result = dom.encode(text(root.firstChild).replace(/[\r\n\v\f\t ]+/, ' '), options);
                    runCustom();
                    return result;
                }
                children(root);
                result = result.join('');
                runCustom();
                if (result.replace(brRe, '').replace(emptyPRe, '') === '') {
                    return '';
                }
                return result;
            }
        };
        extend(Editor, { Serializer: Serializer });
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/range', ['editor/serializer'], f);
}(function () {
    (function ($) {
        var kendo = window.kendo, Class = kendo.Class, extend = $.extend, Editor = kendo.ui.editor, browser = kendo.support.browser, dom = Editor.Dom, findNodeIndex = dom.findNodeIndex, isDataNode = dom.isDataNode, findClosestAncestor = dom.findClosestAncestor, getNodeLength = dom.getNodeLength, normalize = dom.normalize;
        var SelectionUtils = {
            selectionFromWindow: function (window) {
                if (!('getSelection' in window)) {
                    return new W3CSelection(window.document);
                }
                return window.getSelection();
            },
            selectionFromRange: function (range) {
                var rangeDocument = RangeUtils.documentFromRange(range);
                return SelectionUtils.selectionFromDocument(rangeDocument);
            },
            selectionFromDocument: function (document) {
                return SelectionUtils.selectionFromWindow(dom.windowFromDocument(document));
            }
        };
        var W3CRange = Class.extend({
            init: function (doc) {
                $.extend(this, {
                    ownerDocument: doc,
                    startContainer: doc,
                    endContainer: doc,
                    commonAncestorContainer: doc,
                    startOffset: 0,
                    endOffset: 0,
                    collapsed: true
                });
            },
            setStart: function (node, offset) {
                this.startContainer = node;
                this.startOffset = offset;
                updateRangeProperties(this);
                fixIvalidRange(this, true);
            },
            setEnd: function (node, offset) {
                this.endContainer = node;
                this.endOffset = offset;
                updateRangeProperties(this);
                fixIvalidRange(this, false);
            },
            setStartBefore: function (node) {
                this.setStart(node.parentNode, findNodeIndex(node));
            },
            setStartAfter: function (node) {
                this.setStart(node.parentNode, findNodeIndex(node) + 1);
            },
            setEndBefore: function (node) {
                this.setEnd(node.parentNode, findNodeIndex(node));
            },
            setEndAfter: function (node) {
                this.setEnd(node.parentNode, findNodeIndex(node) + 1);
            },
            selectNode: function (node) {
                this.setStartBefore(node);
                this.setEndAfter(node);
            },
            selectNodeContents: function (node) {
                this.setStart(node, 0);
                this.setEnd(node, node[node.nodeType === 1 ? 'childNodes' : 'nodeValue'].length);
            },
            collapse: function (toStart) {
                var that = this;
                if (toStart) {
                    that.setEnd(that.startContainer, that.startOffset);
                } else {
                    that.setStart(that.endContainer, that.endOffset);
                }
            },
            deleteContents: function () {
                var that = this, range = that.cloneRange();
                if (that.startContainer != that.commonAncestorContainer) {
                    that.setStartAfter(findClosestAncestor(that.commonAncestorContainer, that.startContainer));
                }
                that.collapse(true);
                (function deleteSubtree(iterator) {
                    while (iterator.next()) {
                        if (iterator.hasPartialSubtree()) {
                            deleteSubtree(iterator.getSubtreeIterator());
                        } else {
                            iterator.remove();
                        }
                    }
                }(new RangeIterator(range)));
            },
            cloneContents: function () {
                var document = RangeUtils.documentFromRange(this);
                return function cloneSubtree(iterator) {
                    var node, frag = document.createDocumentFragment();
                    while (node = iterator.next()) {
                        node = node.cloneNode(!iterator.hasPartialSubtree());
                        if (iterator.hasPartialSubtree()) {
                            node.appendChild(cloneSubtree(iterator.getSubtreeIterator()));
                        }
                        frag.appendChild(node);
                    }
                    return frag;
                }(new RangeIterator(this));
            },
            extractContents: function () {
                var that = this, range = that.cloneRange();
                if (that.startContainer != that.commonAncestorContainer) {
                    that.setStartAfter(findClosestAncestor(that.commonAncestorContainer, that.startContainer));
                }
                that.collapse(true);
                var document = RangeUtils.documentFromRange(that);
                return function extractSubtree(iterator) {
                    var node, frag = document.createDocumentFragment();
                    while (node = iterator.next()) {
                        if (iterator.hasPartialSubtree()) {
                            node = node.cloneNode(false);
                            node.appendChild(extractSubtree(iterator.getSubtreeIterator()));
                        } else {
                            iterator.remove(that.originalRange);
                        }
                        frag.appendChild(node);
                    }
                    return frag;
                }(new RangeIterator(range));
            },
            insertNode: function (node) {
                var that = this;
                if (isDataNode(that.startContainer)) {
                    if (that.startOffset != that.startContainer.nodeValue.length) {
                        dom.splitDataNode(that.startContainer, that.startOffset);
                    }
                    dom.insertAfter(node, that.startContainer);
                } else {
                    dom.insertAt(that.startContainer, node, that.startOffset);
                }
                that.setStart(that.startContainer, that.startOffset);
            },
            cloneRange: function () {
                return $.extend(new W3CRange(this.ownerDocument), {
                    startContainer: this.startContainer,
                    endContainer: this.endContainer,
                    commonAncestorContainer: this.commonAncestorContainer,
                    startOffset: this.startOffset,
                    endOffset: this.endOffset,
                    collapsed: this.collapsed,
                    originalRange: this
                });
            },
            toString: function () {
                var startNodeName = this.startContainer.nodeName, endNodeName = this.endContainer.nodeName;
                return [
                    startNodeName == '#text' ? this.startContainer.nodeValue : startNodeName,
                    '(',
                    this.startOffset,
                    ') : ',
                    endNodeName == '#text' ? this.endContainer.nodeValue : endNodeName,
                    '(',
                    this.endOffset,
                    ')'
                ].join('');
            }
        });
        W3CRange.fromNode = function (node) {
            return new W3CRange(node.ownerDocument);
        };
        function compareBoundaries(start, end, startOffset, endOffset) {
            if (start == end) {
                return endOffset - startOffset;
            }
            var container = end;
            while (container && container.parentNode != start) {
                container = container.parentNode;
            }
            if (container) {
                return findNodeIndex(container) - startOffset;
            }
            container = start;
            while (container && container.parentNode != end) {
                container = container.parentNode;
            }
            if (container) {
                return endOffset - findNodeIndex(container) - 1;
            }
            var root = dom.commonAncestor(start, end);
            var startAncestor = start;
            while (startAncestor && startAncestor.parentNode != root) {
                startAncestor = startAncestor.parentNode;
            }
            if (!startAncestor) {
                startAncestor = root;
            }
            var endAncestor = end;
            while (endAncestor && endAncestor.parentNode != root) {
                endAncestor = endAncestor.parentNode;
            }
            if (!endAncestor) {
                endAncestor = root;
            }
            if (startAncestor == endAncestor) {
                return 0;
            }
            return findNodeIndex(endAncestor) - findNodeIndex(startAncestor);
        }
        function fixIvalidRange(range, toStart) {
            function isInvalidRange(range) {
                try {
                    return compareBoundaries(range.startContainer, range.endContainer, range.startOffset, range.endOffset) < 0;
                } catch (ex) {
                    return true;
                }
            }
            if (isInvalidRange(range)) {
                if (toStart) {
                    range.commonAncestorContainer = range.endContainer = range.startContainer;
                    range.endOffset = range.startOffset;
                } else {
                    range.commonAncestorContainer = range.startContainer = range.endContainer;
                    range.startOffset = range.endOffset;
                }
                range.collapsed = true;
            }
        }
        function updateRangeProperties(range) {
            range.collapsed = range.startContainer == range.endContainer && range.startOffset == range.endOffset;
            var node = range.startContainer;
            while (node && node != range.endContainer && !dom.isAncestorOf(node, range.endContainer)) {
                node = node.parentNode;
            }
            range.commonAncestorContainer = node;
        }
        var RangeIterator = Class.extend({
            init: function (range) {
                $.extend(this, {
                    range: range,
                    _current: null,
                    _next: null,
                    _end: null
                });
                if (range.collapsed) {
                    return;
                }
                var root = range.commonAncestorContainer;
                this._next = range.startContainer == root && !isDataNode(range.startContainer) ? range.startContainer.childNodes[range.startOffset] : findClosestAncestor(root, range.startContainer);
                this._end = range.endContainer == root && !isDataNode(range.endContainer) ? range.endContainer.childNodes[range.endOffset] : findClosestAncestor(root, range.endContainer).nextSibling;
            },
            hasNext: function () {
                return !!this._next;
            },
            next: function () {
                var that = this, current = that._current = that._next;
                that._next = that._current && that._current.nextSibling != that._end ? that._current.nextSibling : null;
                if (isDataNode(that._current)) {
                    if (that.range.endContainer == that._current) {
                        current = current.cloneNode(true);
                        current.deleteData(that.range.endOffset, current.length - that.range.endOffset);
                    }
                    if (that.range.startContainer == that._current) {
                        current = current.cloneNode(true);
                        current.deleteData(0, that.range.startOffset);
                    }
                }
                return current;
            },
            traverse: function (callback) {
                var that = this, current;
                function next() {
                    that._current = that._next;
                    that._next = that._current && that._current.nextSibling != that._end ? that._current.nextSibling : null;
                    return that._current;
                }
                while (current = next()) {
                    if (that.hasPartialSubtree()) {
                        that.getSubtreeIterator().traverse(callback);
                    } else {
                        callback(current);
                    }
                }
                return current;
            },
            remove: function (originalRange) {
                var that = this, inStartContainer = that.range.startContainer == that._current, inEndContainer = that.range.endContainer == that._current, start, end, delta;
                if (isDataNode(that._current) && (inStartContainer || inEndContainer)) {
                    start = inStartContainer ? that.range.startOffset : 0;
                    end = inEndContainer ? that.range.endOffset : that._current.length;
                    delta = end - start;
                    if (originalRange && (inStartContainer || inEndContainer)) {
                        if (that._current == originalRange.startContainer && start <= originalRange.startOffset) {
                            originalRange.startOffset -= delta;
                        }
                        if (that._current == originalRange.endContainer && end <= originalRange.endOffset) {
                            originalRange.endOffset -= delta;
                        }
                    }
                    that._current.deleteData(start, delta);
                } else {
                    var parent = that._current.parentNode;
                    if (originalRange && (that.range.startContainer == parent || that.range.endContainer == parent)) {
                        var nodeIndex = findNodeIndex(that._current);
                        if (parent == originalRange.startContainer && nodeIndex <= originalRange.startOffset) {
                            originalRange.startOffset -= 1;
                        }
                        if (parent == originalRange.endContainer && nodeIndex < originalRange.endOffset) {
                            originalRange.endOffset -= 1;
                        }
                    }
                    dom.remove(that._current);
                }
            },
            hasPartialSubtree: function () {
                return !isDataNode(this._current) && (dom.isAncestorOrSelf(this._current, this.range.startContainer) || dom.isAncestorOrSelf(this._current, this.range.endContainer));
            },
            getSubtreeIterator: function () {
                var that = this, subRange = that.range.cloneRange();
                subRange.selectNodeContents(that._current);
                if (dom.isAncestorOrSelf(that._current, that.range.startContainer)) {
                    subRange.setStart(that.range.startContainer, that.range.startOffset);
                }
                if (dom.isAncestorOrSelf(that._current, that.range.endContainer)) {
                    subRange.setEnd(that.range.endContainer, that.range.endOffset);
                }
                return new RangeIterator(subRange);
            }
        });
        var W3CSelection = Class.extend({
            init: function (doc) {
                this.ownerDocument = doc;
                this.rangeCount = 1;
            },
            addRange: function (range) {
                var textRange = this.ownerDocument.body.createTextRange();
                adoptContainer(textRange, range, false);
                adoptContainer(textRange, range, true);
                textRange.select();
            },
            removeAllRanges: function () {
                var selection = this.ownerDocument.selection;
                if (selection.type != 'None') {
                    selection.empty();
                }
            },
            getRangeAt: function () {
                var textRange, range = new W3CRange(this.ownerDocument), selection = this.ownerDocument.selection, element, commonAncestor;
                try {
                    textRange = selection.createRange();
                    element = textRange.item ? textRange.item(0) : textRange.parentElement();
                    if (element.ownerDocument != this.ownerDocument) {
                        return range;
                    }
                } catch (ex) {
                    return range;
                }
                if (selection.type == 'Control') {
                    range.selectNode(textRange.item(0));
                } else {
                    commonAncestor = textRangeContainer(textRange);
                    adoptEndPoint(textRange, range, commonAncestor, true);
                    adoptEndPoint(textRange, range, commonAncestor, false);
                    if (range.startContainer.nodeType == 9) {
                        range.setStart(range.endContainer, range.startOffset);
                    }
                    if (range.endContainer.nodeType == 9) {
                        range.setEnd(range.startContainer, range.endOffset);
                    }
                    if (textRange.compareEndPoints('StartToEnd', textRange) === 0) {
                        range.collapse(false);
                    }
                    var startContainer = range.startContainer, endContainer = range.endContainer, body = this.ownerDocument.body;
                    if (!range.collapsed && range.startOffset === 0 && range.endOffset == getNodeLength(range.endContainer) && !(startContainer == endContainer && isDataNode(startContainer) && startContainer.parentNode == body)) {
                        var movedStart = false, movedEnd = false;
                        while (findNodeIndex(startContainer) === 0 && startContainer == startContainer.parentNode.firstChild && startContainer != body) {
                            startContainer = startContainer.parentNode;
                            movedStart = true;
                        }
                        while (findNodeIndex(endContainer) == getNodeLength(endContainer.parentNode) - 1 && endContainer == endContainer.parentNode.lastChild && endContainer != body) {
                            endContainer = endContainer.parentNode;
                            movedEnd = true;
                        }
                        if (startContainer == body && endContainer == body && movedStart && movedEnd) {
                            range.setStart(startContainer, 0);
                            range.setEnd(endContainer, getNodeLength(body));
                        }
                    }
                }
                return range;
            }
        });
        function textRangeContainer(textRange) {
            var left = textRange.duplicate(), right = textRange.duplicate();
            left.collapse(true);
            right.collapse(false);
            return dom.commonAncestor(textRange.parentElement(), left.parentElement(), right.parentElement());
        }
        function adoptContainer(textRange, range, start) {
            var container = range[start ? 'startContainer' : 'endContainer'], offset = range[start ? 'startOffset' : 'endOffset'], textOffset = 0, isData = isDataNode(container), anchorNode = isData ? container : container.childNodes[offset] || null, anchorParent = isData ? container.parentNode : container, doc = range.ownerDocument, cursor = doc.body.createTextRange(), cursorNode;
            if (container.nodeType == 3 || container.nodeType == 4) {
                textOffset = offset;
            }
            if (!anchorParent) {
                anchorParent = doc.body;
            }
            if (anchorParent.nodeName.toLowerCase() == 'img') {
                cursor.moveToElementText(anchorParent);
                cursor.collapse(false);
                textRange.setEndPoint(start ? 'StartToStart' : 'EndToStart', cursor);
            } else {
                cursorNode = anchorParent.insertBefore(dom.create(doc, 'a'), anchorNode);
                cursor.moveToElementText(cursorNode);
                dom.remove(cursorNode);
                cursor[start ? 'moveStart' : 'moveEnd']('character', textOffset);
                cursor.collapse(false);
                textRange.setEndPoint(start ? 'StartToStart' : 'EndToStart', cursor);
            }
        }
        function adoptEndPoint(textRange, range, commonAncestor, start) {
            var cursorNode = dom.create(range.ownerDocument, 'a'), cursor = textRange.duplicate(), comparison = start ? 'StartToStart' : 'StartToEnd', result, parent, target, previous, next, args, index, appended = false;
            cursorNode.innerHTML = '\uFEFF';
            cursor.collapse(start);
            parent = cursor.parentElement();
            if (!dom.isAncestorOrSelf(commonAncestor, parent)) {
                parent = commonAncestor;
            }
            do {
                if (appended) {
                    parent.insertBefore(cursorNode, cursorNode.previousSibling);
                } else {
                    parent.appendChild(cursorNode);
                    appended = true;
                }
                cursor.moveToElementText(cursorNode);
            } while ((result = cursor.compareEndPoints(comparison, textRange)) > 0 && cursorNode.previousSibling);
            target = cursorNode.nextSibling;
            if (result == -1 && isDataNode(target)) {
                cursor.setEndPoint(start ? 'EndToStart' : 'EndToEnd', textRange);
                dom.remove(cursorNode);
                args = [
                    target,
                    cursor.text.length
                ];
            } else {
                previous = !start && cursorNode.previousSibling;
                next = start && cursorNode.nextSibling;
                if (isDataNode(next)) {
                    args = [
                        next,
                        0
                    ];
                } else if (isDataNode(previous)) {
                    args = [
                        previous,
                        previous.length
                    ];
                } else {
                    index = findNodeIndex(cursorNode);
                    if (parent.nextSibling && index == parent.childNodes.length - 1) {
                        args = [
                            parent.nextSibling,
                            0
                        ];
                    } else {
                        args = [
                            parent,
                            index
                        ];
                    }
                }
                dom.remove(cursorNode);
            }
            range[start ? 'setStart' : 'setEnd'].apply(range, args);
        }
        var RangeEnumerator = Class.extend({
            init: function (range) {
                this.enumerate = function () {
                    var nodes = [];
                    function visit(node) {
                        if (dom.is(node, 'img') || node.nodeType == 3 && (!dom.isEmptyspace(node) || node.nodeValue == '\uFEFF')) {
                            nodes.push(node);
                        } else {
                            node = node.firstChild;
                            while (node) {
                                visit(node);
                                node = node.nextSibling;
                            }
                        }
                    }
                    new RangeIterator(range).traverse(visit);
                    return nodes;
                };
            }
        });
        var RestorePoint = Class.extend({
            init: function (range, body) {
                var that = this;
                that.range = range;
                that.rootNode = RangeUtils.documentFromRange(range);
                that.body = body || that.getEditable(range);
                if (dom.name(that.body) != 'body') {
                    that.rootNode = that.body;
                }
                that.html = that.body.innerHTML;
                that.startContainer = that.nodeToPath(range.startContainer);
                that.endContainer = that.nodeToPath(range.endContainer);
                that.startOffset = that.offset(range.startContainer, range.startOffset);
                that.endOffset = that.offset(range.endContainer, range.endOffset);
            },
            index: function (node) {
                var result = 0, lastType = node.nodeType;
                while (node = node.previousSibling) {
                    var nodeType = node.nodeType;
                    if (nodeType != 3 || lastType != nodeType) {
                        result++;
                    }
                    lastType = nodeType;
                }
                return result;
            },
            getEditable: function (range) {
                var root = range.commonAncestorContainer;
                while (root && (root.nodeType == 3 || root.attributes && !root.attributes.contentEditable)) {
                    root = root.parentNode;
                }
                return root;
            },
            restoreHtml: function () {
                this.body.innerHTML = this.html;
            },
            offset: function (node, value) {
                if (node.nodeType == 3) {
                    while ((node = node.previousSibling) && node.nodeType == 3) {
                        value += node.nodeValue.length;
                    }
                }
                return value;
            },
            nodeToPath: function (node) {
                var path = [];
                while (node != this.rootNode) {
                    path.push(this.index(node));
                    node = node.parentNode;
                }
                return path;
            },
            toRangePoint: function (range, start, path, denormalizedOffset) {
                var node = this.rootNode, length = path.length, offset = denormalizedOffset;
                while (length--) {
                    node = node.childNodes[path[length]];
                }
                while (node && node.nodeType == 3 && node.nodeValue.length < offset) {
                    offset -= node.nodeValue.length;
                    node = node.nextSibling;
                }
                if (node && offset >= 0) {
                    range[start ? 'setStart' : 'setEnd'](node, offset);
                }
            },
            toRange: function () {
                var that = this, result = that.range.cloneRange();
                that.toRangePoint(result, true, that.startContainer, that.startOffset);
                that.toRangePoint(result, false, that.endContainer, that.endOffset);
                return result;
            }
        });
        var Marker = Class.extend({
            init: function () {
                this.caret = null;
            },
            addCaret: function (range) {
                var that = this;
                that.caret = dom.create(RangeUtils.documentFromRange(range), 'span', { className: 'k-marker' });
                range.insertNode(that.caret);
                range.selectNode(that.caret);
                return that.caret;
            },
            removeCaret: function (range) {
                var that = this, previous = that.caret.previousSibling, startOffset = 0;
                if (previous) {
                    startOffset = isDataNode(previous) ? previous.nodeValue.length : findNodeIndex(previous);
                }
                var container = that.caret.parentNode;
                var containerIndex = previous ? findNodeIndex(previous) : 0;
                dom.remove(that.caret);
                normalize(container);
                var node = container.childNodes[containerIndex];
                if (isDataNode(node)) {
                    range.setStart(node, startOffset);
                } else if (node) {
                    var textNode = dom.lastTextNode(node);
                    if (textNode) {
                        range.setStart(textNode, textNode.nodeValue.length);
                    } else {
                        range[previous ? 'setStartAfter' : 'setStartBefore'](node);
                    }
                } else {
                    if (!browser.msie && !container.innerHTML) {
                        container.innerHTML = '<br _moz_dirty="" />';
                    }
                    range.selectNodeContents(container);
                }
                range.collapse(true);
            },
            add: function (range, expand) {
                var that = this;
                var collapsed = range.collapsed && !RangeUtils.isExpandable(range);
                var doc = RangeUtils.documentFromRange(range);
                if (expand && range.collapsed) {
                    that.addCaret(range);
                    range = RangeUtils.expand(range);
                }
                var rangeBoundary = range.cloneRange();
                rangeBoundary.collapse(false);
                that.end = dom.create(doc, 'span', { className: 'k-marker' });
                rangeBoundary.insertNode(that.end);
                rangeBoundary = range.cloneRange();
                rangeBoundary.collapse(true);
                that.start = that.end.cloneNode(true);
                rangeBoundary.insertNode(that.start);
                that._removeDeadMarkers(that.start, that.end);
                if (collapsed) {
                    var bom = doc.createTextNode('\uFEFF');
                    dom.insertAfter(bom.cloneNode(), that.start);
                    dom.insertBefore(bom, that.end);
                }
                normalize(range.commonAncestorContainer);
                range.setStartBefore(that.start);
                range.setEndAfter(that.end);
                return range;
            },
            _removeDeadMarkers: function (start, end) {
                if (start.previousSibling && start.previousSibling.nodeValue == '\uFEFF') {
                    dom.remove(start.previousSibling);
                }
                if (end.nextSibling && end.nextSibling.nodeValue == '\uFEFF') {
                    dom.remove(end.nextSibling);
                }
            },
            _normalizedIndex: function (node) {
                var index = findNodeIndex(node);
                var pointer = node;
                while (pointer.previousSibling) {
                    if (pointer.nodeType == 3 && pointer.previousSibling.nodeType == 3) {
                        index--;
                    }
                    pointer = pointer.previousSibling;
                }
                return index;
            },
            remove: function (range) {
                var that = this, start = that.start, end = that.end, shouldNormalizeStart, shouldNormalizeEnd, shouldNormalize;
                normalize(range.commonAncestorContainer);
                while (!start.nextSibling && start.parentNode) {
                    start = start.parentNode;
                }
                while (!end.previousSibling && end.parentNode) {
                    end = end.parentNode;
                }
                shouldNormalizeStart = start.previousSibling && start.previousSibling.nodeType == 3 && (start.nextSibling && start.nextSibling.nodeType == 3);
                shouldNormalizeEnd = end.previousSibling && end.previousSibling.nodeType == 3 && (end.nextSibling && end.nextSibling.nodeType == 3);
                shouldNormalize = shouldNormalizeStart && shouldNormalizeEnd;
                start = start.nextSibling;
                end = end.previousSibling;
                var collapsed = false;
                var collapsedToStart = false;
                if (start == that.end) {
                    collapsedToStart = !!that.start.previousSibling;
                    start = end = that.start.previousSibling || that.end.nextSibling;
                    collapsed = true;
                }
                dom.remove(that.start);
                dom.remove(that.end);
                if (!start || !end) {
                    range.selectNodeContents(range.commonAncestorContainer);
                    range.collapse(true);
                    return;
                }
                var startOffset = collapsed ? isDataNode(start) ? start.nodeValue.length : start.childNodes.length : 0;
                var endOffset = isDataNode(end) ? end.nodeValue.length : end.childNodes.length;
                if (start.nodeType == 3) {
                    while (start.previousSibling && start.previousSibling.nodeType == 3) {
                        start = start.previousSibling;
                        startOffset += start.nodeValue.length;
                    }
                }
                if (end.nodeType == 3) {
                    while (end.previousSibling && end.previousSibling.nodeType == 3) {
                        end = end.previousSibling;
                        endOffset += end.nodeValue.length;
                    }
                }
                var startParent = start.parentNode;
                var endParent = end.parentNode;
                var startIndex = this._normalizedIndex(start);
                var endIndex = this._normalizedIndex(end);
                normalize(startParent);
                if (start.nodeType == 3) {
                    start = startParent.childNodes[startIndex];
                }
                normalize(endParent);
                if (end.nodeType == 3) {
                    end = endParent.childNodes[endIndex];
                }
                if (collapsed) {
                    if (start.nodeType == 3) {
                        range.setStart(start, startOffset);
                    } else {
                        range[collapsedToStart ? 'setStartAfter' : 'setStartBefore'](start);
                    }
                    range.collapse(true);
                } else {
                    if (start.nodeType == 3) {
                        range.setStart(start, startOffset);
                    } else {
                        range.setStartBefore(start);
                    }
                    if (end.nodeType == 3) {
                        range.setEnd(end, endOffset);
                    } else {
                        range.setEndAfter(end);
                    }
                }
                if (that.caret) {
                    that.removeCaret(range);
                }
            }
        });
        var boundary = /[\u0009-\u000d]|\u0020|\u00a0|\ufeff|\.|,|;|:|!|\(|\)|\?/;
        var RangeUtils = {
            nodes: function (range) {
                var nodes = RangeUtils.textNodes(range);
                if (!nodes.length) {
                    range.selectNodeContents(range.commonAncestorContainer);
                    nodes = RangeUtils.textNodes(range);
                    if (!nodes.length) {
                        nodes = dom.significantChildNodes(range.commonAncestorContainer);
                    }
                }
                return nodes;
            },
            textNodes: function (range) {
                return new RangeEnumerator(range).enumerate();
            },
            documentFromRange: function (range) {
                var startContainer = range.startContainer;
                return startContainer.nodeType == 9 ? startContainer : startContainer.ownerDocument;
            },
            createRange: function (document) {
                if (browser.msie && browser.version < 9) {
                    return new W3CRange(document);
                }
                return document.createRange();
            },
            selectRange: function (range) {
                var image = RangeUtils.image(range);
                if (image) {
                    range.setStartAfter(image);
                    range.setEndAfter(image);
                }
                var selection = SelectionUtils.selectionFromRange(range);
                selection.removeAllRanges();
                selection.addRange(range);
            },
            stringify: function (range) {
                return kendo.format('{0}:{1} - {2}:{3}', dom.name(range.startContainer), range.startOffset, dom.name(range.endContainer), range.endOffset);
            },
            split: function (range, node, trim) {
                function partition(start) {
                    var partitionRange = range.cloneRange();
                    partitionRange.collapse(start);
                    partitionRange[start ? 'setStartBefore' : 'setEndAfter'](node);
                    var contents = partitionRange.extractContents();
                    if (trim) {
                        contents = dom.trim(contents);
                    }
                    dom[start ? 'insertBefore' : 'insertAfter'](contents, node);
                }
                partition(true);
                partition(false);
            },
            mapAll: function (range, map) {
                var nodes = [];
                new RangeIterator(range).traverse(function (node) {
                    var mapped = map(node);
                    if (mapped && $.inArray(mapped, nodes) < 0) {
                        nodes.push(mapped);
                    }
                });
                return nodes;
            },
            getAll: function (range, predicate) {
                var selector = predicate;
                if (typeof predicate == 'string') {
                    predicate = function (node) {
                        return dom.is(node, selector);
                    };
                }
                return RangeUtils.mapAll(range, function (node) {
                    if (predicate(node)) {
                        return node;
                    }
                });
            },
            getMarkers: function (range) {
                return RangeUtils.getAll(range, function (node) {
                    return node.className == 'k-marker';
                });
            },
            image: function (range) {
                var nodes = RangeUtils.getAll(range, 'img');
                if (nodes.length == 1) {
                    return nodes[0];
                }
            },
            isStartOf: function (originalRange, node) {
                if (originalRange.startOffset !== 0) {
                    return false;
                }
                var range = originalRange.cloneRange();
                while (range.startOffset === 0 && range.startContainer != node) {
                    var index = dom.findNodeIndex(range.startContainer);
                    var parent = range.startContainer.parentNode;
                    while (index > 0 && parent[index - 1] && dom.insignificant(parent[index - 1])) {
                        index--;
                    }
                    range.setStart(parent, index);
                }
                return range.startOffset === 0 && range.startContainer == node;
            },
            isEndOf: function (originalRange, node) {
                var range = originalRange.cloneRange();
                range.collapse(false);
                var start = range.startContainer;
                if (dom.isDataNode(start) && range.startOffset == dom.getNodeLength(start)) {
                    range.setStart(start.parentNode, dom.findNodeIndex(start) + 1);
                    range.collapse(true);
                }
                range.setEnd(node, dom.getNodeLength(node));
                var nodes = [];
                function visit(node) {
                    if (!dom.insignificant(node)) {
                        nodes.push(node);
                    }
                }
                new RangeIterator(range).traverse(visit);
                return !nodes.length;
            },
            wrapSelectedElements: function (range) {
                var startEditable = dom.editableParent(range.startContainer);
                var endEditable = dom.editableParent(range.endContainer);
                while (range.startOffset === 0 && range.startContainer != startEditable) {
                    range.setStart(range.startContainer.parentNode, dom.findNodeIndex(range.startContainer));
                }
                function isEnd(offset, container) {
                    var length = dom.getNodeLength(container);
                    if (offset == length) {
                        return true;
                    }
                    for (var i = offset; i < length; i++) {
                        if (!dom.insignificant(container.childNodes[i])) {
                            return false;
                        }
                    }
                    return true;
                }
                while (isEnd(range.endOffset, range.endContainer) && range.endContainer != endEditable) {
                    range.setEnd(range.endContainer.parentNode, dom.findNodeIndex(range.endContainer) + 1);
                }
                return range;
            },
            expand: function (range) {
                var result = range.cloneRange();
                var startContainer = result.startContainer.childNodes[result.startOffset === 0 ? 0 : result.startOffset - 1];
                var endContainer = result.endContainer.childNodes[result.endOffset];
                if (!isDataNode(startContainer) || !isDataNode(endContainer)) {
                    return result;
                }
                var beforeCaret = startContainer.nodeValue;
                var afterCaret = endContainer.nodeValue;
                if (!beforeCaret || !afterCaret) {
                    return result;
                }
                var startOffset = beforeCaret.split('').reverse().join('').search(boundary);
                var endOffset = afterCaret.search(boundary);
                if (!startOffset || !endOffset) {
                    return result;
                }
                endOffset = endOffset == -1 ? afterCaret.length : endOffset;
                startOffset = startOffset == -1 ? 0 : beforeCaret.length - startOffset;
                result.setStart(startContainer, startOffset);
                result.setEnd(endContainer, endOffset);
                return result;
            },
            isExpandable: function (range) {
                var node = range.startContainer;
                var rangeDocument = RangeUtils.documentFromRange(range);
                if (node == rangeDocument || node == rangeDocument.body) {
                    return false;
                }
                var result = range.cloneRange();
                var value = node.nodeValue;
                if (!value) {
                    return false;
                }
                var beforeCaret = value.substring(0, result.startOffset);
                var afterCaret = value.substring(result.startOffset);
                var startOffset = 0, endOffset = 0;
                if (beforeCaret) {
                    startOffset = beforeCaret.split('').reverse().join('').search(boundary);
                }
                if (afterCaret) {
                    endOffset = afterCaret.search(boundary);
                }
                return startOffset && endOffset;
            }
        };
        extend(Editor, {
            SelectionUtils: SelectionUtils,
            W3CRange: W3CRange,
            RangeIterator: RangeIterator,
            W3CSelection: W3CSelection,
            RangeEnumerator: RangeEnumerator,
            RestorePoint: RestorePoint,
            Marker: Marker,
            RangeUtils: RangeUtils
        });
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/system', ['editor/range'], f);
}(function () {
    (function ($) {
        var kendo = window.kendo, Class = kendo.Class, editorNS = kendo.ui.editor, EditorUtils = editorNS.EditorUtils, registerTool = EditorUtils.registerTool, dom = editorNS.Dom, Tool = editorNS.Tool, ToolTemplate = editorNS.ToolTemplate, RestorePoint = editorNS.RestorePoint, Marker = editorNS.Marker, extend = $.extend;
        function finishUpdate(editor, startRestorePoint) {
            var endRestorePoint = editor.selectionRestorePoint = new RestorePoint(editor.getRange());
            var command = new GenericCommand(startRestorePoint, endRestorePoint);
            command.editor = editor;
            editor.undoRedoStack.push(command);
            return endRestorePoint;
        }
        var Command = Class.extend({
            init: function (options) {
                this.options = options;
                this.restorePoint = new RestorePoint(options.range);
                this.marker = new Marker();
                this.formatter = options.formatter;
            },
            getRange: function () {
                return this.restorePoint.toRange();
            },
            lockRange: function (expand) {
                return this.marker.add(this.getRange(), expand);
            },
            releaseRange: function (range) {
                this.marker.remove(range);
                this.editor.selectRange(range);
            },
            undo: function () {
                var point = this.restorePoint;
                point.restoreHtml();
                this.editor.selectRange(point.toRange());
            },
            redo: function () {
                this.exec();
            },
            createDialog: function (content, options) {
                var editor = this.editor;
                return $(content).appendTo(document.body).kendoWindow(extend({}, editor.options.dialogOptions, options)).closest('.k-window').toggleClass('k-rtl', kendo.support.isRtl(editor.wrapper)).end();
            },
            exec: function () {
                var range = this.lockRange(true);
                this.formatter.editor = this.editor;
                this.formatter.toggle(range);
                this.releaseRange(range);
            }
        });
        var GenericCommand = Class.extend({
            init: function (startRestorePoint, endRestorePoint) {
                this.body = startRestorePoint.body;
                this.startRestorePoint = startRestorePoint;
                this.endRestorePoint = endRestorePoint;
            },
            redo: function () {
                this.body.innerHTML = this.endRestorePoint.html;
                this.editor.selectRange(this.endRestorePoint.toRange());
            },
            undo: function () {
                this.body.innerHTML = this.startRestorePoint.html;
                this.editor.selectRange(this.startRestorePoint.toRange());
            }
        });
        var InsertHtmlCommand = Command.extend({
            init: function (options) {
                Command.fn.init.call(this, options);
                this.managesUndoRedo = true;
            },
            exec: function () {
                var editor = this.editor;
                var options = this.options;
                var range = options.range;
                var body = editor.body;
                var startRestorePoint = new RestorePoint(range, body);
                var html = options.html || options.value || '';
                editor.selectRange(range);
                editor.clipboard.paste(html, options);
                if (options.postProcess) {
                    options.postProcess(editor, editor.getRange());
                }
                var genericCommand = new GenericCommand(startRestorePoint, new RestorePoint(editor.getRange(), body));
                genericCommand.editor = editor;
                editor.undoRedoStack.push(genericCommand);
                editor.focus();
            }
        });
        var InsertHtmlTool = Tool.extend({
            initialize: function (ui, initOptions) {
                var editor = initOptions.editor, options = this.options, dataSource = options.items ? options.items : editor.options.insertHtml;
                this._selectBox = new editorNS.SelectBox(ui, {
                    dataSource: dataSource,
                    dataTextField: 'text',
                    dataValueField: 'value',
                    change: function () {
                        Tool.exec(editor, 'insertHtml', this.value());
                    },
                    title: editor.options.messages.insertHtml,
                    highlightFirst: false
                });
            },
            command: function (commandArguments) {
                return new InsertHtmlCommand(commandArguments);
            },
            update: function (ui) {
                var selectbox = ui.data('kendoSelectBox') || ui.find('select').data('kendoSelectBox');
                selectbox.close();
                selectbox.value(selectbox.options.title);
            }
        });
        var TypingHandler = Class.extend({
            init: function (editor) {
                this.editor = editor;
            },
            keydown: function (e) {
                var that = this, editor = that.editor, keyboard = editor.keyboard, isTypingKey = keyboard.isTypingKey(e), evt = extend($.Event(), e);
                that.editor.trigger('keydown', evt);
                if (evt.isDefaultPrevented()) {
                    e.preventDefault();
                    return true;
                }
                if (!evt.isDefaultPrevented() && isTypingKey && !keyboard.isTypingInProgress()) {
                    var range = editor.getRange();
                    that.startRestorePoint = new RestorePoint(range);
                    keyboard.startTyping(function () {
                        that.endRestorePoint = finishUpdate(editor, that.startRestorePoint);
                    });
                    return true;
                }
                return false;
            },
            keyup: function (e) {
                var keyboard = this.editor.keyboard;
                this.editor.trigger('keyup', e);
                if (keyboard.isTypingInProgress()) {
                    keyboard.endTyping();
                    return true;
                }
                return false;
            }
        });
        var BackspaceHandler = Class.extend({
            init: function (editor) {
                this.editor = editor;
            },
            _addCaret: function (container) {
                var caret = dom.create(this.editor.document, 'a');
                dom.insertAt(container, caret, 0);
                return caret;
            },
            _restoreCaret: function (caret) {
                var range = this.editor.createRange();
                range.setStartAfter(caret);
                range.collapse(true);
                this.editor.selectRange(range);
                dom.remove(caret);
            },
            _handleDelete: function (range) {
                var node = range.endContainer;
                var block = dom.closestEditableOfType(node, dom.blockElements);
                if (block && editorNS.RangeUtils.isEndOf(range, block)) {
                    var next = dom.next(block);
                    if (!next || dom.name(next) != 'p') {
                        return false;
                    }
                    var caret = this._addCaret(next);
                    this._merge(block, next);
                    this._restoreCaret(caret);
                    return true;
                }
                return false;
            },
            _cleanBomBefore: function (range) {
                var offset = range.startOffset;
                var node = range.startContainer;
                var text = node.nodeValue;
                var count = 0;
                while (offset - count >= 0 && text[offset - count - 1] == '\uFEFF') {
                    count++;
                }
                if (count > 0) {
                    node.deleteData(offset - count, count);
                    range.setStart(node, Math.max(0, offset - count));
                    range.collapse(true);
                    this.editor.selectRange(range);
                }
            },
            _handleBackspace: function (range) {
                var node = range.startContainer;
                var li = dom.closestEditableOfType(node, ['li']);
                var block = dom.closestEditableOfType(node, 'p,h1,h2,h3,h4,h5,h6'.split(','));
                if (dom.isDataNode(node)) {
                    this._cleanBomBefore(range);
                }
                if (block && block.previousSibling && editorNS.RangeUtils.isStartOf(range, block)) {
                    var prev = block.previousSibling;
                    var caret = this._addCaret(block);
                    this._merge(prev, block);
                    this._restoreCaret(caret);
                    return true;
                }
                if (li && editorNS.RangeUtils.isStartOf(range, li)) {
                    var child = li.firstChild;
                    if (!child) {
                        li.innerHTML = editorNS.emptyElementContent;
                        child = li.firstChild;
                    }
                    var formatter = new editorNS.ListFormatter(dom.name(li.parentNode), 'p');
                    range.selectNodeContents(li);
                    formatter.toggle(range);
                    if (dom.insignificant(child)) {
                        range.setStartBefore(child);
                    } else {
                        range.setStart(child, 0);
                    }
                    this.editor.selectRange(range);
                    return true;
                }
                return false;
            },
            _handleSelection: function (range) {
                var ancestor = range.commonAncestorContainer;
                var table = dom.closest(ancestor, 'table');
                var emptyParagraphContent = editorNS.emptyElementContent;
                if (/t(able|body)/i.test(dom.name(ancestor))) {
                    range.selectNode(table);
                }
                var marker = new Marker();
                marker.add(range, false);
                range.setStartAfter(marker.start);
                range.setEndBefore(marker.end);
                var start = range.startContainer;
                var end = range.endContainer;
                range.deleteContents();
                if (table && $(table).text() === '') {
                    range.selectNode(table);
                    range.deleteContents();
                }
                ancestor = range.commonAncestorContainer;
                if (dom.name(ancestor) === 'p' && ancestor.innerHTML === '') {
                    ancestor.innerHTML = emptyParagraphContent;
                    range.setStart(ancestor, 0);
                }
                this._join(start, end);
                dom.insertAfter(this.editor.document.createTextNode('\uFEFF'), marker.start);
                marker.remove(range);
                start = range.startContainer;
                if (dom.name(start) == 'tr') {
                    start = start.childNodes[Math.max(0, range.startOffset - 1)];
                    range.setStart(start, dom.getNodeLength(start));
                }
                range.collapse(true);
                this.editor.selectRange(range);
                return true;
            },
            _root: function (node) {
                while (node && node.parentNode && dom.name(node.parentNode) != 'body') {
                    node = node.parentNode;
                }
                return node;
            },
            _join: function (start, end) {
                start = this._root(start);
                end = this._root(end);
                if (start != end && dom.is(end, 'p')) {
                    this._merge(start, end);
                }
            },
            _merge: function (dest, src) {
                dom.removeTrailingBreak(dest);
                while (src.firstChild) {
                    if (dest.nodeType == 1) {
                        dest.appendChild(src.firstChild);
                    } else {
                        dest.parentNode.appendChild(src.firstChild);
                    }
                }
                dom.remove(src);
            },
            keydown: function (e) {
                var method, startRestorePoint;
                var range = this.editor.getRange();
                var keyCode = e.keyCode;
                var keys = kendo.keys;
                var backspace = keyCode === keys.BACKSPACE;
                var del = keyCode == keys.DELETE;
                if ((backspace || del) && !range.collapsed) {
                    method = '_handleSelection';
                } else if (backspace) {
                    method = '_handleBackspace';
                } else if (del) {
                    method = '_handleDelete';
                }
                if (!method) {
                    return;
                }
                startRestorePoint = new RestorePoint(range);
                if (this[method](range)) {
                    e.preventDefault();
                    finishUpdate(this.editor, startRestorePoint);
                }
            },
            keyup: $.noop
        });
        var SystemHandler = Class.extend({
            init: function (editor) {
                this.editor = editor;
                this.systemCommandIsInProgress = false;
            },
            createUndoCommand: function () {
                this.startRestorePoint = this.endRestorePoint = finishUpdate(this.editor, this.startRestorePoint);
            },
            changed: function () {
                if (this.startRestorePoint) {
                    return this.startRestorePoint.html != this.editor.body.innerHTML;
                }
                return false;
            },
            keydown: function (e) {
                var that = this, editor = that.editor, keyboard = editor.keyboard;
                if (keyboard.isModifierKey(e)) {
                    if (keyboard.isTypingInProgress()) {
                        keyboard.endTyping(true);
                    }
                    that.startRestorePoint = new RestorePoint(editor.getRange());
                    return true;
                }
                if (keyboard.isSystem(e)) {
                    that.systemCommandIsInProgress = true;
                    if (that.changed()) {
                        that.systemCommandIsInProgress = false;
                        that.createUndoCommand();
                    }
                    return true;
                }
                return false;
            },
            keyup: function () {
                var that = this;
                if (that.systemCommandIsInProgress && that.changed()) {
                    that.systemCommandIsInProgress = false;
                    that.createUndoCommand();
                    return true;
                }
                return false;
            }
        });
        var Keyboard = Class.extend({
            init: function (handlers) {
                this.handlers = handlers;
                this.typingInProgress = false;
            },
            isCharacter: function (keyCode) {
                return keyCode >= 48 && keyCode <= 90 || keyCode >= 96 && keyCode <= 111 || keyCode >= 186 && keyCode <= 192 || keyCode >= 219 && keyCode <= 222 || keyCode == 229;
            },
            toolFromShortcut: function (tools, e) {
                var key = String.fromCharCode(e.keyCode), toolName, toolOptions;
                for (toolName in tools) {
                    toolOptions = $.extend({
                        ctrl: false,
                        alt: false,
                        shift: false
                    }, tools[toolName].options);
                    if ((toolOptions.key == key || toolOptions.key == e.keyCode) && toolOptions.ctrl == e.ctrlKey && toolOptions.alt == e.altKey && toolOptions.shift == e.shiftKey) {
                        return toolName;
                    }
                }
            },
            toolsFromShortcut: function (tools, e) {
                var key = String.fromCharCode(e.keyCode), toolName, o, matchesKey, found = [];
                var matchKey = function (toolKey) {
                    return toolKey == key || toolKey == e.keyCode;
                };
                for (toolName in tools) {
                    o = $.extend({
                        ctrl: false,
                        alt: false,
                        shift: false
                    }, tools[toolName].options);
                    matchesKey = $.isArray(o.key) ? $.grep(o.key, matchKey).length > 0 : matchKey(o.key);
                    if (matchesKey && o.ctrl == e.ctrlKey && o.alt == e.altKey && o.shift == e.shiftKey) {
                        found.push(tools[toolName]);
                    }
                }
                return found;
            },
            isTypingKey: function (e) {
                var keyCode = e.keyCode;
                return this.isCharacter(keyCode) && !e.ctrlKey && !e.altKey || keyCode == 32 || keyCode == 13 || keyCode == 8 || keyCode == 46 && !e.shiftKey && !e.ctrlKey && !e.altKey;
            },
            isModifierKey: function (e) {
                var keyCode = e.keyCode;
                return keyCode == 17 && !e.shiftKey && !e.altKey || keyCode == 16 && !e.ctrlKey && !e.altKey || keyCode == 18 && !e.ctrlKey && !e.shiftKey;
            },
            isSystem: function (e) {
                return e.keyCode == 46 && e.ctrlKey && !e.altKey && !e.shiftKey;
            },
            startTyping: function (callback) {
                this.onEndTyping = callback;
                this.typingInProgress = true;
            },
            stopTyping: function () {
                if (this.typingInProgress && this.onEndTyping) {
                    this.onEndTyping();
                }
                this.typingInProgress = false;
            },
            endTyping: function (force) {
                var that = this;
                that.clearTimeout();
                if (force) {
                    that.stopTyping();
                } else {
                    that.timeout = window.setTimeout($.proxy(that.stopTyping, that), 1000);
                }
            },
            isTypingInProgress: function () {
                return this.typingInProgress;
            },
            clearTimeout: function () {
                window.clearTimeout(this.timeout);
            },
            notify: function (e, what) {
                var i, handlers = this.handlers;
                for (i = 0; i < handlers.length; i++) {
                    if (handlers[i][what](e)) {
                        break;
                    }
                }
            },
            keydown: function (e) {
                this.notify(e, 'keydown');
            },
            keyup: function (e) {
                this.notify(e, 'keyup');
            }
        });
        var Clipboard = Class.extend({
            init: function (editor) {
                this.editor = editor;
                var pasteCleanup = editor.options.pasteCleanup;
                this.cleaners = [
                    new ScriptCleaner(pasteCleanup),
                    new TabCleaner(pasteCleanup),
                    new MSWordFormatCleaner(pasteCleanup),
                    new WebkitFormatCleaner(pasteCleanup),
                    new HtmlTagsCleaner(pasteCleanup),
                    new HtmlAttrCleaner(pasteCleanup),
                    new HtmlContentCleaner(pasteCleanup),
                    new CustomCleaner(pasteCleanup)
                ];
            },
            htmlToFragment: function (html) {
                var editor = this.editor, doc = editor.document, container = dom.create(doc, 'div'), fragment = doc.createDocumentFragment();
                container.innerHTML = html;
                while (container.firstChild) {
                    fragment.appendChild(container.firstChild);
                }
                return fragment;
            },
            isBlock: function (html) {
                return /<(div|p|ul|ol|table|h[1-6])/i.test(html);
            },
            _startModification: function () {
                var range;
                var restorePoint;
                var editor = this.editor;
                if (this._inProgress) {
                    return;
                }
                this._inProgress = true;
                range = editor.getRange();
                restorePoint = new RestorePoint(range);
                dom.persistScrollTop(editor.document);
                return {
                    range: range,
                    restorePoint: restorePoint
                };
            },
            _endModification: function (modificationInfo) {
                finishUpdate(this.editor, modificationInfo.restorePoint);
                this.editor._selectionChange();
                this._inProgress = false;
            },
            _contentModification: function (before, after) {
                var that = this;
                var editor = that.editor;
                var modificationInfo = that._startModification();
                if (!modificationInfo) {
                    return;
                }
                before.call(that, editor, modificationInfo.range);
                setTimeout(function () {
                    after.call(that, editor, modificationInfo.range);
                    that._endModification(modificationInfo);
                });
            },
            _removeBomNodes: function (range) {
                var nodes = editorNS.RangeUtils.textNodes(range);
                for (var i = 0; i < nodes.length; i++) {
                    nodes[i].nodeValue = dom.stripBom(nodes[i].nodeValue);
                }
            },
            _onBeforeCopy: function (range) {
                var marker = new Marker();
                marker.add(range);
                this._removeBomNodes(range);
                marker.remove(range);
                this.editor.selectRange(range);
            },
            oncopy: function () {
                this._onBeforeCopy(this.editor.getRange());
            },
            oncut: function () {
                this._onBeforeCopy(this.editor.getRange());
                this._contentModification($.noop, $.noop);
            },
            _fileToDataURL: function (blob) {
                var deferred = $.Deferred();
                var reader = new FileReader();
                if (!(blob instanceof window.File) && blob.getAsFile) {
                    blob = blob.getAsFile();
                }
                reader.onload = $.proxy(deferred.resolve, deferred);
                reader.readAsDataURL(blob);
                return deferred.promise();
            },
            _triggerPaste: function (html, options) {
                var args = { html: html || '' };
                args.html = args.html.replace(/\ufeff/g, '');
                this.editor.trigger('paste', args);
                this.paste(args.html, options || {});
            },
            _handleImagePaste: function (e) {
                if (!('FileReader' in window)) {
                    return;
                }
                var clipboardData = e.clipboardData || e.originalEvent.clipboardData || window.clipboardData || {};
                var items = clipboardData.items || clipboardData.files;
                if (!items) {
                    return;
                }
                var images = $.grep(items, function (item) {
                    return /^image\//i.test(item.type);
                });
                var html = $.grep(items, function (item) {
                    return /^text\/html/i.test(item.type);
                });
                if (html.length || !images.length) {
                    return;
                }
                var modificationInfo = this._startModification();
                if (!modificationInfo) {
                    return;
                }
                $.when.apply($, $.map(images, this._fileToDataURL)).done($.proxy(function () {
                    var results = Array.prototype.slice.call(arguments);
                    var html = $.map(results, function (e) {
                        return '<img src="' + e.target.result + '" />';
                    }).join('');
                    this._triggerPaste(html);
                    this._endModification(modificationInfo);
                }, this));
                return true;
            },
            onpaste: function (e) {
                if (this._handleImagePaste(e)) {
                    e.preventDefault();
                    return;
                }
                this._contentModification(function beforePaste(editor, range) {
                    var clipboardNode = dom.create(editor.document, 'div', {
                        className: 'k-paste-container',
                        innerHTML: '\uFEFF'
                    });
                    var browser = kendo.support.browser;
                    editor.body.appendChild(clipboardNode);
                    if (browser.msie && browser.version < 11) {
                        e.preventDefault();
                        var r = editor.createRange();
                        r.selectNodeContents(clipboardNode);
                        editor.selectRange(r);
                        var textRange = editor.document.body.createTextRange();
                        textRange.moveToElementText(clipboardNode);
                        $(editor.body).unbind('paste');
                        textRange.execCommand('Paste');
                        $(editor.body).bind('paste', $.proxy(this.onpaste, this));
                    } else {
                        var clipboardRange = editor.createRange();
                        clipboardRange.selectNodeContents(clipboardNode);
                        editor.selectRange(clipboardRange);
                    }
                    range.deleteContents();
                }, function afterPaste(editor, range) {
                    var html = '', containers;
                    editor.selectRange(range);
                    containers = $(editor.body).children('.k-paste-container');
                    containers.each(function () {
                        var lastChild = this.lastChild;
                        if (lastChild && dom.is(lastChild, 'br')) {
                            dom.remove(lastChild);
                        }
                        html += this.innerHTML;
                    });
                    containers.remove();
                    this._triggerPaste(html, { clean: true });
                });
            },
            splittableParent: function (block, node) {
                var parentNode, body;
                if (block) {
                    return dom.closestEditableOfType(node, [
                        'p',
                        'ul',
                        'ol'
                    ]) || node.parentNode;
                }
                parentNode = node.parentNode;
                body = node.ownerDocument.body;
                if (dom.isInline(parentNode)) {
                    while (parentNode.parentNode != body && !dom.isBlock(parentNode.parentNode)) {
                        parentNode = parentNode.parentNode;
                    }
                }
                return parentNode;
            },
            paste: function (html, options) {
                var editor = this.editor, i, l;
                options = extend({
                    clean: false,
                    split: true
                }, options);
                for (i = 0, l = this.cleaners.length; i < l; i++) {
                    if (this.cleaners[i].applicable(html)) {
                        html = this.cleaners[i].clean(html);
                    }
                }
                if (options.clean) {
                    html = html.replace(/(<br>(\s|&nbsp;)*)+(<\/?(div|p|li|col|t))/gi, '$3');
                    html = html.replace(/<(a|span)[^>]*><\/\1>/gi, '');
                }
                html = html.replace(/^<li/i, '<ul><li').replace(/li>$/g, 'li></ul>');
                var block = this.isBlock(html);
                editor.focus();
                var range = editor.getRange();
                range.deleteContents();
                if (range.startContainer == editor.document) {
                    range.selectNodeContents(editor.body);
                }
                var marker = new Marker();
                var caret = marker.addCaret(range);
                var parent = this.splittableParent(block, caret);
                var unwrap = false;
                var splittable = parent != editor.body && !dom.is(parent, 'td');
                if (options.split && splittable && (block || dom.isInline(parent))) {
                    range.selectNode(caret);
                    editorNS.RangeUtils.split(range, parent, true);
                    unwrap = true;
                }
                var fragment = this.htmlToFragment(html);
                if (fragment.firstChild && fragment.firstChild.className === 'k-paste-container') {
                    var fragmentsHtml = [];
                    for (i = 0, l = fragment.childNodes.length; i < l; i++) {
                        fragmentsHtml.push(fragment.childNodes[i].innerHTML);
                    }
                    fragment = this.htmlToFragment(fragmentsHtml.join('<br />'));
                }
                $(fragment.childNodes).filter('table').addClass('k-table').end().find('table').addClass('k-table');
                range.insertNode(fragment);
                parent = this.splittableParent(block, caret);
                if (unwrap) {
                    while (caret.parentNode != parent) {
                        dom.unwrap(caret.parentNode);
                    }
                    dom.unwrap(caret.parentNode);
                }
                dom.normalize(range.commonAncestorContainer);
                caret.style.display = 'inline';
                dom.restoreScrollTop(editor.document);
                dom.scrollTo(caret);
                marker.removeCaret(range);
                var rangeEnd = range.commonAncestorContainer.parentNode;
                if (range.collapsed && dom.name(rangeEnd) == 'tbody') {
                    range.setStartAfter($(rangeEnd).closest('table')[0]);
                    range.collapse(true);
                }
                editor.selectRange(range);
            }
        });
        var Cleaner = Class.extend({
            init: function (options) {
                this.options = options || {};
                this.replacements = [];
            },
            clean: function (html, customReplacements) {
                var that = this, replacements = customReplacements || that.replacements, i, l;
                for (i = 0, l = replacements.length; i < l; i += 2) {
                    html = html.replace(replacements[i], replacements[i + 1]);
                }
                return html;
            }
        });
        var ScriptCleaner = Cleaner.extend({
            init: function (options) {
                Cleaner.fn.init.call(this, options);
                this.replacements = [
                    /<(\/?)script([^>]*)>/i,
                    '<$1telerik:script$2>'
                ];
            },
            applicable: function (html) {
                return !this.options.none && /<script[^>]*>/i.test(html);
            }
        });
        var TabCleaner = Cleaner.extend({
            init: function (options) {
                Cleaner.fn.init.call(this, options);
                var replacement = ' ';
                this.replacements = [
                    /<span\s+class="Apple-tab-span"[^>]*>\s*<\/span>/gi,
                    replacement,
                    /\t/gi,
                    replacement,
                    /&nbsp;&nbsp; &nbsp;/gi,
                    replacement
                ];
            },
            applicable: function (html) {
                return /&nbsp;&nbsp; &nbsp;|class="?Apple-tab-span/i.test(html);
            }
        });
        var MSWordFormatCleaner = Cleaner.extend({
            init: function (options) {
                Cleaner.fn.init.call(this, options);
                this.junkReplacements = [
                    /<\?xml[^>]*>/gi,
                    '',
                    /<!--(.|\n)*?-->/g,
                    '',
                    /&quot;/g,
                    '\'',
                    /<o:p>&nbsp;<\/o:p>/gi,
                    '&nbsp;',
                    /<\/?(meta|link|style|o:|v:|x:)[^>]*>((?:.|\n)*?<\/(meta|link|style|o:|v:|x:)[^>]*>)?/gi,
                    '',
                    /<\/o>/g,
                    ''
                ];
                this.replacements = this.junkReplacements.concat([
                    /(?:<br>&nbsp;[\s\r\n]+|<br>)*(<\/?(h[1-6]|hr|p|div|table|tbody|thead|tfoot|th|tr|td|li|ol|ul|caption|address|pre|form|blockquote|dl|dt|dd|dir|fieldset)[^>]*>)(?:<br>&nbsp;[\s\r\n]+|<br>)*/g,
                    '$1',
                    /<br><br>/g,
                    '<BR><BR>',
                    /<br>(?!\n)/g,
                    ' ',
                    /<table([^>]*)>(\s|&nbsp;)+<t/gi,
                    '<table$1><t',
                    /<tr[^>]*>(\s|&nbsp;)*<\/tr>/gi,
                    '',
                    /<tbody[^>]*>(\s|&nbsp;)*<\/tbody>/gi,
                    '',
                    /<table[^>]*>(\s|&nbsp;)*<\/table>/gi,
                    '',
                    /<BR><BR>/g,
                    '<br>',
                    /^\s*(&nbsp;)+/gi,
                    '',
                    /(&nbsp;|<br[^>]*>)+\s*$/gi,
                    '',
                    /mso-[^;"]*;?/gi,
                    '',
                    /<(\/?)b(\s[^>]*)?>/gi,
                    '<$1strong$2>',
                    /<(\/?)font(\s[^>]*)?>/gi,
                    this.convertFontMatch,
                    /<(\/?)i(\s[^>]*)?>/gi,
                    '<$1em$2>',
                    /style=(["|'])\s*\1/g,
                    '',
                    /(<br[^>]*>)?\n/g,
                    function ($0, $1) {
                        return $1 ? $0 : ' ';
                    }
                ]);
            },
            convertFontMatch: function (match, closing, args) {
                var faceRe = /face=['"]([^'"]+)['"]/i;
                var face = faceRe.exec(args);
                var family = args && face && face[1];
                if (closing) {
                    return '</span>';
                } else if (family) {
                    return '<span style="font-family:' + family + '">';
                } else {
                    return '<span>';
                }
            },
            applicable: function (html) {
                return /class="?Mso/i.test(html) || /style="[^"]*mso-/i.test(html) || /urn:schemas-microsoft-com:office/.test(html);
            },
            stripEmptyAnchors: function (html) {
                return html.replace(/<a([^>]*)>\s*<\/a>/gi, function (a, attributes) {
                    if (!attributes || attributes.indexOf('href') < 0) {
                        return '';
                    }
                    return a;
                });
            },
            listType: function (p, listData) {
                var html = p.innerHTML;
                var text = dom.innerText(p);
                var startingSymbol;
                var matchSymbol = html.match(/^(?:<span [^>]*texhtml[^>]*>)?<span [^>]*(?:Symbol|Wingdings)[^>]*>([^<]+)/i);
                var symbol = matchSymbol && matchSymbol[1];
                var isNumber = /^[a-z\d]/i.test(symbol);
                var trimStartText = function (text) {
                    return text.replace(/^(?:&nbsp;|[\u00a0\n\r\s])+/, '');
                };
                if (matchSymbol) {
                    startingSymbol = true;
                }
                html = html.replace(/<\/?\w+[^>]*>/g, '').replace(/&nbsp;/g, '\xA0');
                if (!startingSymbol && /^[\u2022\u00b7\u00a7\u00d8o]\u00a0+/.test(html) || startingSymbol && /^.\u00a0+/.test(html) || symbol && !isNumber && listData) {
                    return {
                        tag: 'ul',
                        style: this._guessUnorderedListStyle(trimStartText(text))
                    };
                }
                if (/^\s*\w+[\.\)][\u00a0 ]{2,}/.test(html)) {
                    return {
                        tag: 'ol',
                        style: this._guessOrderedListStyle(trimStartText(text))
                    };
                }
            },
            _convertToLi: function (p) {
                var content;
                if (p.childNodes.length == 1) {
                    content = p.firstChild.innerHTML.replace(/^\w+[\.\)](&nbsp;)+ /, '');
                } else {
                    dom.remove(p.firstChild);
                    if (p.firstChild.nodeType == 3) {
                        if (/^[ivxlcdm]+\.$/i.test(p.firstChild.nodeValue)) {
                            dom.remove(p.firstChild);
                        }
                    }
                    if (/^(&nbsp;|\s)+$/i.test(p.firstChild.innerHTML)) {
                        dom.remove(p.firstChild);
                    }
                    content = p.innerHTML;
                }
                dom.remove(p);
                return dom.create(document, 'li', { innerHTML: content });
            },
            _guessUnorderedListStyle: function (symbol) {
                if (/^[\u2022\u00b7\u00FC\u00D8\u002dv-]/.test(symbol)) {
                    return null;
                } else if (/^o/.test(symbol)) {
                    return 'circle';
                } else {
                    return 'square';
                }
            },
            _guessOrderedListStyle: function (symbol) {
                var listType = null;
                if (!/^\d/.test(symbol)) {
                    listType = (/^[a-z]/.test(symbol) ? 'lower-' : 'upper-') + (/^[ivxlcdm]/i.test(symbol) ? 'roman' : 'alpha');
                }
                return listType;
            },
            extractListLevels: function (html) {
                var msoListRegExp = /style=['"]?[^'"]*?mso-list:\s?[a-zA-Z]+(\d+)\s[a-zA-Z]+(\d+)\s(\w+)/gi;
                html = html.replace(msoListRegExp, function (match, list, level) {
                    return kendo.format('data-list="{0}" data-level="{1}" {2}', list, level, match);
                });
                return html;
            },
            lists: function (placeholder) {
                var blockChildren = $(placeholder).find(dom.blockElements.join(',')), lastMargin = -1, name, levels = {}, li = placeholder, rootMargin, listContainer, i, p, type, margin, list, listData;
                for (i = 0; i < blockChildren.length; i++) {
                    p = blockChildren[i];
                    listData = $(p).data();
                    var listIndex = listData.list;
                    name = dom.name(p);
                    if (name == 'td') {
                        continue;
                    }
                    var listType = this.listType(p, listData);
                    type = listType && listType.tag;
                    if (!type || name != 'p') {
                        if (!p.innerHTML) {
                            dom.remove(p);
                        } else {
                            lastMargin = -1;
                            li = placeholder;
                        }
                        continue;
                    }
                    margin = parseFloat(p.style.marginLeft || 0);
                    if (rootMargin === undefined) {
                        rootMargin = margin;
                    }
                    var levelType = type + listIndex;
                    if (!levels[margin]) {
                        levels[margin] = {};
                    }
                    list = levels[margin][levelType];
                    if (margin > lastMargin || !list) {
                        list = dom.create(document, type, { style: { listStyleType: listType.style } });
                        if (li == placeholder || margin <= lastMargin) {
                            if (listContainer && rootMargin !== margin) {
                                listContainer.appendChild(list);
                            } else {
                                dom.insertBefore(list, p);
                            }
                            levels[margin] = {};
                        } else {
                            listContainer = li;
                            li.appendChild(list);
                        }
                        levels[margin][levelType] = list;
                    }
                    li = this._convertToLi(p);
                    list.appendChild(li);
                    lastMargin = margin;
                }
            },
            removeAttributes: function (element) {
                var attributes = element.attributes, i = attributes.length;
                while (i--) {
                    if (dom.name(attributes[i]) != 'colspan') {
                        element.removeAttributeNode(attributes[i]);
                    }
                }
            },
            createColGroup: function (row) {
                var cells = row.cells;
                var table = $(row).closest('table');
                var colgroup = table.children('colgroup');
                if (cells.length < 2) {
                    return;
                } else if (colgroup.length) {
                    cells = colgroup.children();
                    colgroup[0].parentNode.removeChild(colgroup[0]);
                }
                colgroup = $($.map(cells, function (cell) {
                    var width = cell.width;
                    if (width && parseInt(width, 10) !== 0) {
                        return kendo.format('<col style="width:{0}px;"/>', width);
                    }
                    return '<col />';
                }).join(''));
                if (!colgroup.is('colgroup')) {
                    colgroup = $('<colgroup/>').append(colgroup);
                }
                colgroup.prependTo(table);
            },
            convertHeaders: function (row) {
                var cells = row.cells, i, boldedCells = $.map(cells, function (cell) {
                        var child = $(cell).children('p').children('strong')[0];
                        if (child && dom.name(child) == 'strong') {
                            return child;
                        }
                    });
                if (boldedCells.length == cells.length) {
                    for (i = 0; i < boldedCells.length; i++) {
                        dom.unwrap(boldedCells[i]);
                    }
                    $(row).closest('table').find('colgroup').after('<thead></thead>').end().find('thead').append(row);
                    for (i = 0; i < cells.length; i++) {
                        dom.changeTag(cells[i], 'th');
                    }
                }
            },
            removeParagraphs: function (cells) {
                var i, j, len, cell, paragraphs;
                for (i = 0; i < cells.length; i++) {
                    this.removeAttributes(cells[i]);
                    cell = $(cells[i]);
                    paragraphs = cell.children('p');
                    for (j = 0, len = paragraphs.length; j < len; j++) {
                        if (j < len - 1) {
                            dom.insertAfter(dom.create(document, 'br'), paragraphs[j]);
                        }
                        dom.unwrap(paragraphs[j]);
                    }
                }
            },
            removeDefaultColors: function (spans) {
                for (var i = 0; i < spans.length; i++) {
                    if (/^\s*color:\s*[^;]*;?$/i.test(spans[i].style.cssText)) {
                        dom.unwrap(spans[i]);
                    }
                }
            },
            tables: function (placeholder) {
                var tables = $(placeholder).find('table'), that = this, rows, firstRow, longestRow, i, j;
                for (i = 0; i < tables.length; i++) {
                    rows = tables[i].rows;
                    longestRow = firstRow = rows[0];
                    for (j = 1; j < rows.length; j++) {
                        if (rows[j].cells.length > longestRow.cells.length) {
                            longestRow = rows[j];
                        }
                    }
                    that.createColGroup(longestRow);
                    that.convertHeaders(firstRow);
                    that.removeAttributes(tables[i]);
                    that.removeParagraphs(tables.eq(i).find('td,th'));
                    that.removeDefaultColors(tables.eq(i).find('span'));
                }
            },
            headers: function (placeholder) {
                var titles = $(placeholder).find('p.MsoTitle');
                for (var i = 0; i < titles.length; i++) {
                    dom.changeTag(titles[i], 'h1');
                }
            },
            removeFormatting: function (placeholder) {
                $(placeholder).find('*').each(function () {
                    $(this).css({
                        fontSize: '',
                        fontFamily: ''
                    });
                    if (!this.getAttribute('style') && !this.style.cssText) {
                        this.removeAttribute('style');
                    }
                });
            },
            clean: function (html) {
                var that = this, placeholder;
                var filters = this.options;
                if (filters.none) {
                    html = Cleaner.fn.clean.call(that, html, this.junkReplacements);
                    html = that.stripEmptyAnchors(html);
                } else {
                    html = this.extractListLevels(html);
                    html = Cleaner.fn.clean.call(that, html);
                    html = that.stripEmptyAnchors(html);
                    placeholder = dom.create(document, 'div', { innerHTML: html });
                    that.headers(placeholder);
                    if (filters.msConvertLists) {
                        that.lists(placeholder);
                    }
                    that.tables(placeholder);
                    if (filters.msAllFormatting) {
                        that.removeFormatting(placeholder);
                    }
                    html = placeholder.innerHTML.replace(/(<[^>]*)\s+class="?[^"\s>]*"?/gi, '$1');
                }
                return html;
            }
        });
        var WebkitFormatCleaner = Cleaner.extend({
            init: function (options) {
                Cleaner.fn.init.call(this, options);
                this.replacements = [
                    /\s+class="Apple-style-span[^"]*"/gi,
                    '',
                    /<(div|p|h[1-6])\s+style="[^"]*"/gi,
                    '<$1',
                    /^<div>(.*)<\/div>$/,
                    '$1'
                ];
            },
            applicable: function (html) {
                return /class="?Apple-style-span|style="[^"]*-webkit-nbsp-mode/i.test(html);
            }
        });
        var DomCleaner = Cleaner.extend({
            clean: function (html) {
                var container = dom.create(document, 'div', { innerHTML: html });
                container = this.cleanDom(container);
                return container.innerHTML;
            },
            cleanDom: function (container) {
                return container;
            }
        });
        var HtmlTagsCleaner = DomCleaner.extend({
            cleanDom: function (container) {
                var tags = this.collectTags();
                $(container).find(tags).each(function () {
                    dom.unwrap(this);
                });
                return container;
            },
            collectTags: function () {
                if (this.options.span) {
                    return 'span';
                }
            },
            applicable: function () {
                return this.options.span;
            }
        });
        var HtmlAttrCleaner = DomCleaner.extend({
            cleanDom: function (container) {
                var attributes = this.collectAttr();
                var nodes = $(container).find('[' + attributes.join('],[') + ']');
                nodes.removeAttr(attributes.join(' '));
                return container;
            },
            collectAttr: function () {
                if (this.options.css) {
                    return [
                        'class',
                        'style'
                    ];
                }
                return [];
            },
            applicable: function () {
                return this.options.css;
            }
        });
        var TextContainer = function () {
            this.text = '';
            this.add = function (text) {
                this.text += text;
            };
        };
        var HtmlTextLines = Class.extend({
            init: function (separators) {
                this.separators = separators || {
                    text: ' ',
                    line: '<br/>'
                };
                this.lines = [];
                this.inlineBlockText = [];
                this.resetLine();
            },
            appendText: function (text) {
                if (text.nodeType === 3) {
                    text = text.nodeValue;
                }
                this.textContainer.add(text);
            },
            appendInlineBlockText: function (text) {
                this.inlineBlockText.push(text);
            },
            flashInlineBlockText: function () {
                if (this.inlineBlockText.length) {
                    this.appendText(this.inlineBlockText.join(' '));
                    this.inlineBlockText = [];
                }
            },
            endLine: function () {
                this.flashInlineBlockText();
                this.resetLine();
            },
            html: function () {
                var separators = this.separators;
                var result = '';
                var lines = this.lines;
                this.flashInlineBlockText();
                for (var i = 0, il = lines.length, il1 = il - 1; i < il; i++) {
                    var line = lines[i];
                    for (var j = 0, jl = line.length, jl1 = jl - 1; j < jl; j++) {
                        var text = line[j].text;
                        result += text;
                        if (j !== jl1) {
                            result += separators.text;
                        }
                    }
                    if (i !== il1) {
                        result += separators.line;
                    }
                }
                return result;
            },
            resetLine: function () {
                this.textContainer = new TextContainer();
                this.line = [];
                this.line.push(this.textContainer);
                this.lines.push(this.line);
            }
        });
        var DomEnumerator = Class.extend({
            init: function (callback) {
                this.callback = callback;
            },
            enumerate: function (node) {
                if (!node) {
                    return;
                }
                var preventDown = this.callback(node);
                var child = node.firstChild;
                if (!preventDown && child) {
                    this.enumerate(child);
                }
                this.enumerate(node.nextSibling);
            }
        });
        var HtmlContentCleaner = Cleaner.extend({
            init: function (options) {
                Cleaner.fn.init.call(this, options);
                this.hasText = false;
                this.enumerator = new DomEnumerator($.proxy(this.buildText, this));
            },
            clean: function (html) {
                var container = dom.create(document, 'div', { innerHTML: html });
                return this.cleanDom(container);
            },
            cleanDom: function (container) {
                this.separators = this.getDefaultSeparators();
                this.htmlLines = new HtmlTextLines(this.separators);
                this.enumerator.enumerate(container.firstChild);
                this.hasText = false;
                return this.htmlLines.html();
            },
            buildText: function (node) {
                if (dom.isDataNode(node)) {
                    if (dom.isEmptyspace(node)) {
                        return;
                    }
                    this.htmlLines.appendText(node.nodeValue.replace('\n', this.separators.line));
                    this.hasText = true;
                } else if (dom.isBlock(node) && this.hasText) {
                    var action = this.actions[dom.name(node)] || this.actions.block;
                    return action(this, node);
                }
            },
            applicable: function () {
                var o = this.options;
                return o.all || o.keepNewLines;
            },
            getDefaultSeparators: function () {
                if (this.options.all) {
                    return {
                        text: ' ',
                        line: ' '
                    };
                } else {
                    return {
                        text: ' ',
                        line: '<br/>'
                    };
                }
            },
            actions: {
                ul: $.noop,
                ol: $.noop,
                table: $.noop,
                thead: $.noop,
                tbody: $.noop,
                td: function (cleaner, node) {
                    var tdCleaner = new HtmlContentCleaner({ all: true });
                    var cellText = tdCleaner.cleanDom(node);
                    cleaner.htmlLines.appendInlineBlockText(cellText);
                    return true;
                },
                block: function (cleaner) {
                    cleaner.htmlLines.endLine();
                }
            }
        });
        var CustomCleaner = Cleaner.extend({
            clean: function (html) {
                return this.options.custom(html);
            },
            applicable: function () {
                return typeof this.options.custom === 'function';
            }
        });
        var PrintCommand = Command.extend({
            init: function (options) {
                Command.fn.init.call(this, options);
                this.managesUndoRedo = true;
            },
            exec: function () {
                var editor = this.editor;
                if (kendo.support.browser.msie) {
                    editor.document.execCommand('print', false, null);
                } else if (editor.window.print) {
                    editor.window.print();
                }
            }
        });
        var ExportPdfCommand = Command.extend({
            init: function (options) {
                this.async = true;
                Command.fn.init.call(this, options);
            },
            exec: function () {
                var that = this;
                var range = this.lockRange(true);
                this.editor.saveAsPDF().then(function () {
                    that.releaseRange(range);
                });
            }
        });
        extend(editorNS, {
            _finishUpdate: finishUpdate,
            Command: Command,
            GenericCommand: GenericCommand,
            InsertHtmlCommand: InsertHtmlCommand,
            InsertHtmlTool: InsertHtmlTool,
            TypingHandler: TypingHandler,
            SystemHandler: SystemHandler,
            BackspaceHandler: BackspaceHandler,
            Keyboard: Keyboard,
            Clipboard: Clipboard,
            Cleaner: Cleaner,
            ScriptCleaner: ScriptCleaner,
            TabCleaner: TabCleaner,
            MSWordFormatCleaner: MSWordFormatCleaner,
            WebkitFormatCleaner: WebkitFormatCleaner,
            HtmlTagsCleaner: HtmlTagsCleaner,
            HtmlAttrCleaner: HtmlAttrCleaner,
            HtmlContentCleaner: HtmlContentCleaner,
            HtmlTextLines: HtmlTextLines,
            CustomCleaner: CustomCleaner,
            PrintCommand: PrintCommand,
            ExportPdfCommand: ExportPdfCommand
        });
        registerTool('insertHtml', new InsertHtmlTool({
            template: new ToolTemplate({
                template: EditorUtils.dropDownListTemplate,
                title: 'Insert HTML',
                initialValue: 'Insert HTML'
            })
        }));
        registerTool('print', new Tool({
            command: PrintCommand,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Print'
            })
        }));
        registerTool('pdf', new Tool({
            command: ExportPdfCommand,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Export PDF'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/inlineformat', ['editor/system'], f);
}(function () {
    (function ($) {
        var kendo = window.kendo, Class = kendo.Class, Editor = kendo.ui.editor, formats = kendo.ui.Editor.fn.options.formats, EditorUtils = Editor.EditorUtils, Tool = Editor.Tool, ToolTemplate = Editor.ToolTemplate, FormatTool = Editor.FormatTool, dom = Editor.Dom, RangeUtils = Editor.RangeUtils, extend = $.extend, registerTool = Editor.EditorUtils.registerTool, registerFormat = Editor.EditorUtils.registerFormat, KMARKER = 'k-marker';
        var InlineFormatFinder = Class.extend({
            init: function (format) {
                this.format = format;
            },
            numberOfSiblings: function (referenceNode) {
                var textNodesCount = 0, elementNodesCount = 0, markerCount = 0, parentNode = referenceNode.parentNode, node;
                for (node = parentNode.firstChild; node; node = node.nextSibling) {
                    if (node != referenceNode) {
                        if (node.className == KMARKER) {
                            markerCount++;
                        } else if (node.nodeType == 3) {
                            textNodesCount++;
                        } else {
                            elementNodesCount++;
                        }
                    }
                }
                if (markerCount > 1 && parentNode.firstChild.className == KMARKER && parentNode.lastChild.className == KMARKER) {
                    return 0;
                } else {
                    return elementNodesCount + textNodesCount;
                }
            },
            findSuitable: function (sourceNode, skip) {
                if (!skip && this.numberOfSiblings(sourceNode) > 0) {
                    return null;
                }
                var node = sourceNode.parentNode;
                var tags = this.format[0].tags;
                while (!dom.ofType(node, tags)) {
                    if (this.numberOfSiblings(node) > 0) {
                        return null;
                    }
                    node = node.parentNode;
                }
                return node;
            },
            findFormat: function (sourceNode) {
                var format = this.format, attrEquals = dom.attrEquals, i, len, node, tags, attributes;
                for (i = 0, len = format.length; i < len; i++) {
                    node = sourceNode;
                    tags = format[i].tags;
                    attributes = format[i].attr;
                    if (node && dom.ofType(node, tags) && attrEquals(node, attributes)) {
                        return node;
                    }
                    while (node) {
                        node = dom.parentOfType(node, tags);
                        if (node && attrEquals(node, attributes)) {
                            return node;
                        }
                    }
                }
                return null;
            },
            isFormatted: function (nodes) {
                var i, len;
                for (i = 0, len = nodes.length; i < len; i++) {
                    if (this.findFormat(nodes[i])) {
                        return true;
                    }
                }
                return false;
            }
        });
        var InlineFormatter = Class.extend({
            init: function (format, values) {
                this.finder = new InlineFormatFinder(format);
                this.attributes = extend({}, format[0].attr, values);
                this.tag = format[0].tags[0];
            },
            wrap: function (node) {
                return dom.wrap(node, dom.create(node.ownerDocument, this.tag, this.attributes));
            },
            activate: function (range, nodes) {
                if (this.finder.isFormatted(nodes)) {
                    this.split(range);
                    this.remove(nodes);
                } else {
                    this.apply(nodes);
                }
            },
            toggle: function (range) {
                var nodes = RangeUtils.textNodes(range);
                if (nodes.length > 0) {
                    this.activate(range, nodes);
                }
            },
            apply: function (nodes) {
                var formatNodes = [];
                var i, l, node, formatNode;
                for (i = 0, l = nodes.length; i < l; i++) {
                    node = nodes[i];
                    formatNode = this.finder.findSuitable(node);
                    if (formatNode) {
                        dom.attr(formatNode, this.attributes);
                    } else {
                        while (!dom.isBlock(node.parentNode) && node.parentNode.childNodes.length == 1 && node.parentNode.contentEditable !== 'true') {
                            node = node.parentNode;
                        }
                        formatNode = this.wrap(node);
                    }
                    formatNodes.push(formatNode);
                }
                this.consolidate(formatNodes);
            },
            remove: function (nodes) {
                var i, l, formatNode;
                for (i = 0, l = nodes.length; i < l; i++) {
                    formatNode = this.finder.findFormat(nodes[i]);
                    if (formatNode) {
                        if (this.attributes && this.attributes.style) {
                            dom.unstyle(formatNode, this.attributes.style);
                            if (!formatNode.style.cssText && !formatNode.attributes['class']) {
                                dom.unwrap(formatNode);
                            }
                        } else {
                            dom.unwrap(formatNode);
                        }
                    }
                }
            },
            split: function (range) {
                var nodes = RangeUtils.textNodes(range);
                var l = nodes.length;
                var i, formatNode;
                if (l > 0) {
                    for (i = 0; i < l; i++) {
                        formatNode = this.finder.findFormat(nodes[i]);
                        if (formatNode) {
                            RangeUtils.split(range, formatNode, true);
                        }
                    }
                }
            },
            consolidate: function (nodes) {
                var node, last;
                while (nodes.length > 1) {
                    node = nodes.pop();
                    last = nodes[nodes.length - 1];
                    if (node.previousSibling && node.previousSibling.className == KMARKER) {
                        last.appendChild(node.previousSibling);
                    }
                    if (node.tagName == last.tagName && node.previousSibling == last && node.style.cssText == last.style.cssText) {
                        while (node.firstChild) {
                            last.appendChild(node.firstChild);
                        }
                        dom.remove(node);
                    }
                }
            }
        });
        var GreedyInlineFormatFinder = InlineFormatFinder.extend({
            init: function (format, greedyProperty) {
                this.format = format;
                this.greedyProperty = greedyProperty;
                InlineFormatFinder.fn.init.call(this, format);
            },
            getInlineCssValue: function (node) {
                var attributes = node.attributes;
                var trim = $.trim;
                var i, l, attribute, name, attributeValue, css, pair, cssIndex, len;
                var propertyAndValue, property, value;
                if (!attributes) {
                    return;
                }
                for (i = 0, l = attributes.length; i < l; i++) {
                    attribute = attributes[i];
                    name = attribute.nodeName;
                    attributeValue = attribute.nodeValue;
                    if (attribute.specified && name == 'style') {
                        css = trim(attributeValue || node.style.cssText).split(';');
                        for (cssIndex = 0, len = css.length; cssIndex < len; cssIndex++) {
                            pair = css[cssIndex];
                            if (pair.length) {
                                propertyAndValue = pair.split(':');
                                property = trim(propertyAndValue[0].toLowerCase());
                                value = trim(propertyAndValue[1]);
                                if (property != this.greedyProperty) {
                                    continue;
                                }
                                return property.indexOf('color') >= 0 ? dom.toHex(value) : value;
                            }
                        }
                    }
                }
            },
            getFormatInner: function (node) {
                var $node = $(dom.isDataNode(node) ? node.parentNode : node);
                var parents = $node.parentsUntil('[contentEditable]').addBack().toArray().reverse();
                var i, len, value;
                for (i = 0, len = parents.length; i < len; i++) {
                    value = this.greedyProperty == 'className' ? parents[i].className : this.getInlineCssValue(parents[i]);
                    if (value) {
                        return value;
                    }
                }
                return 'inherit';
            },
            getFormat: function (nodes) {
                var result = this.getFormatInner(nodes[0]), i, len;
                for (i = 1, len = nodes.length; i < len; i++) {
                    if (result != this.getFormatInner(nodes[i])) {
                        return '';
                    }
                }
                return result;
            },
            isFormatted: function (nodes) {
                return this.getFormat(nodes) !== '';
            }
        });
        var GreedyInlineFormatter = InlineFormatter.extend({
            init: function (format, values, greedyProperty) {
                InlineFormatter.fn.init.call(this, format, values);
                this.values = values;
                this.finder = new GreedyInlineFormatFinder(format, greedyProperty);
                if (greedyProperty) {
                    this.greedyProperty = kendo.toCamelCase(greedyProperty);
                }
            },
            activate: function (range, nodes) {
                var greedyProperty = this.greedyProperty;
                var action = 'apply';
                this.split(range);
                if (greedyProperty && this.values.style[greedyProperty] == 'inherit') {
                    action = 'remove';
                }
                this[action](nodes);
            }
        });
        var InlineFormatTool = FormatTool.extend({
            init: function (options) {
                FormatTool.fn.init.call(this, extend(options, {
                    finder: new InlineFormatFinder(options.format),
                    formatter: function () {
                        return new InlineFormatter(options.format);
                    }
                }));
            }
        });
        var DelayedExecutionTool = Tool.extend({
            update: function (ui, nodes) {
                var list = ui.data(this.type);
                list.close();
                list.value(this.finder.getFormat(nodes));
            }
        });
        var FontTool = DelayedExecutionTool.extend({
            init: function (options) {
                Tool.fn.init.call(this, options);
                this.type = kendo.support.browser.msie || kendo.support.touch ? 'kendoDropDownList' : 'kendoComboBox';
                this.format = [{ tags: ['span'] }];
                this.finder = new GreedyInlineFormatFinder(this.format, options.cssAttr);
            },
            command: function (commandArguments) {
                var options = this.options, format = this.format, style = {};
                return new Editor.FormatCommand(extend(commandArguments, {
                    formatter: function () {
                        style[options.domAttr] = commandArguments.value;
                        return new GreedyInlineFormatter(format, { style: style }, options.cssAttr);
                    }
                }));
            },
            initialize: function (ui, initOptions) {
                var editor = initOptions.editor, options = this.options, toolName = options.name, dataSource, defaultValue = [];
                if (options.defaultValue) {
                    defaultValue = [{
                            text: editor.options.messages[options.defaultValue[0].text],
                            value: options.defaultValue[0].value
                        }];
                }
                dataSource = defaultValue.concat(options.items ? options.items : editor.options[toolName] || []);
                ui.attr({ title: initOptions.title });
                ui[this.type]({
                    dataTextField: 'text',
                    dataValueField: 'value',
                    dataSource: dataSource,
                    change: function () {
                        Tool.exec(editor, toolName, this.value());
                    },
                    highlightFirst: false
                });
                ui.closest('.k-widget').removeClass('k-' + toolName).find('*').addBack().attr('unselectable', 'on');
                ui.data(this.type).value('inherit');
            }
        });
        var ColorTool = Tool.extend({
            init: function (options) {
                Tool.fn.init.call(this, options);
                this.format = [{ tags: ['span'] }];
                this.finder = new GreedyInlineFormatFinder(this.format, options.cssAttr);
            },
            options: { palette: 'websafe' },
            update: function () {
                this._widget.close();
            },
            command: function (commandArguments) {
                var options = this.options, format = this.format, style = {};
                return new Editor.FormatCommand(extend(commandArguments, {
                    formatter: function () {
                        style[options.domAttr] = commandArguments.value;
                        return new GreedyInlineFormatter(format, { style: style }, options.cssAttr);
                    }
                }));
            },
            initialize: function (ui, initOptions) {
                var editor = initOptions.editor, toolName = this.name, options = extend({}, ColorTool.fn.options, this.options), palette = options.palette;
                ui = this._widget = new kendo.ui.ColorPicker(ui, {
                    toolIcon: 'k-' + options.name,
                    palette: palette,
                    change: function () {
                        var color = ui.value();
                        if (color) {
                            Tool.exec(editor, toolName, color);
                        }
                        editor.focus();
                    },
                    activate: function (e) {
                        e.preventDefault();
                        ui.trigger('change');
                    }
                });
                ui.wrapper.attr({
                    title: initOptions.title,
                    unselectable: 'on'
                }).find('*').attr('unselectable', 'on');
            }
        });
        extend(Editor, {
            InlineFormatFinder: InlineFormatFinder,
            InlineFormatter: InlineFormatter,
            DelayedExecutionTool: DelayedExecutionTool,
            GreedyInlineFormatFinder: GreedyInlineFormatFinder,
            GreedyInlineFormatter: GreedyInlineFormatter,
            InlineFormatTool: InlineFormatTool,
            FontTool: FontTool,
            ColorTool: ColorTool
        });
        registerFormat('bold', [
            {
                tags: [
                    'strong',
                    'b'
                ]
            },
            {
                tags: ['span'],
                attr: { style: { fontWeight: 'bold' } }
            }
        ]);
        registerTool('bold', new InlineFormatTool({
            key: 'B',
            ctrl: true,
            format: formats.bold,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Bold'
            })
        }));
        registerFormat('italic', [
            {
                tags: [
                    'em',
                    'i'
                ]
            },
            {
                tags: ['span'],
                attr: { style: { fontStyle: 'italic' } }
            }
        ]);
        registerTool('italic', new InlineFormatTool({
            key: 'I',
            ctrl: true,
            format: formats.italic,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Italic'
            })
        }));
        registerFormat('underline', [
            {
                tags: ['span'],
                attr: { style: { textDecoration: 'underline' } }
            },
            { tags: ['u'] }
        ]);
        registerTool('underline', new InlineFormatTool({
            key: 'U',
            ctrl: true,
            format: formats.underline,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Underline'
            })
        }));
        registerFormat('strikethrough', [
            {
                tags: [
                    'del',
                    'strike'
                ]
            },
            {
                tags: ['span'],
                attr: { style: { textDecoration: 'line-through' } }
            }
        ]);
        registerTool('strikethrough', new InlineFormatTool({
            format: formats.strikethrough,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Strikethrough'
            })
        }));
        registerFormat('superscript', [{ tags: ['sup'] }]);
        registerTool('superscript', new InlineFormatTool({
            format: formats.superscript,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Superscript'
            })
        }));
        registerFormat('subscript', [{ tags: ['sub'] }]);
        registerTool('subscript', new InlineFormatTool({
            format: formats.subscript,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Subscript'
            })
        }));
        registerTool('foreColor', new ColorTool({
            cssAttr: 'color',
            domAttr: 'color',
            name: 'foreColor',
            template: new ToolTemplate({
                template: EditorUtils.colorPickerTemplate,
                title: 'Color'
            })
        }));
        registerTool('backColor', new ColorTool({
            cssAttr: 'background-color',
            domAttr: 'backgroundColor',
            name: 'backColor',
            template: new ToolTemplate({
                template: EditorUtils.colorPickerTemplate,
                title: 'Background Color'
            })
        }));
        registerTool('fontName', new FontTool({
            cssAttr: 'font-family',
            domAttr: 'fontFamily',
            name: 'fontName',
            defaultValue: [{
                    text: 'fontNameInherit',
                    value: 'inherit'
                }],
            template: new ToolTemplate({
                template: EditorUtils.comboBoxTemplate,
                title: 'Font Name'
            })
        }));
        registerTool('fontSize', new FontTool({
            cssAttr: 'font-size',
            domAttr: 'fontSize',
            name: 'fontSize',
            defaultValue: [{
                    text: 'fontSizeInherit',
                    value: 'inherit'
                }],
            template: new ToolTemplate({
                template: EditorUtils.comboBoxTemplate,
                title: 'Font Size'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/formatblock', ['editor/inlineformat'], f);
}(function () {
    (function ($) {
        var kendo = window.kendo, Class = kendo.Class, extend = $.extend, Editor = kendo.ui.editor, formats = kendo.ui.Editor.fn.options.formats, dom = Editor.Dom, Command = Editor.Command, ToolTemplate = Editor.ToolTemplate, FormatTool = Editor.FormatTool, EditorUtils = Editor.EditorUtils, registerTool = EditorUtils.registerTool, registerFormat = EditorUtils.registerFormat, RangeUtils = Editor.RangeUtils;
        var BlockFormatFinder = Class.extend({
            init: function (format) {
                this.format = format;
            },
            contains: function (node, children) {
                var i, len, child;
                for (i = 0, len = children.length; i < len; i++) {
                    child = children[i];
                    if (!child || !dom.isAncestorOrSelf(node, child)) {
                        return false;
                    }
                }
                return true;
            },
            findSuitable: function (nodes) {
                var format = this.format, suitable = [], i, len, candidate;
                for (i = 0, len = nodes.length; i < len; i++) {
                    for (var f = format.length - 1; f >= 0; f--) {
                        candidate = dom.ofType(nodes[i], format[f].tags) ? nodes[i] : dom.closestEditableOfType(nodes[i], format[f].tags);
                        if (candidate) {
                            break;
                        }
                    }
                    if (!candidate || candidate.contentEditable === 'true') {
                        return [];
                    }
                    if ($.inArray(candidate, suitable) < 0) {
                        suitable.push(candidate);
                    }
                }
                for (i = 0, len = suitable.length; i < len; i++) {
                    if (this.contains(suitable[i], suitable)) {
                        return [suitable[i]];
                    }
                }
                return suitable;
            },
            findFormat: function (sourceNode) {
                var format = this.format, i, len, node, tags, attributes;
                var editableParent = dom.editableParent(sourceNode);
                for (i = 0, len = format.length; i < len; i++) {
                    node = sourceNode;
                    tags = format[i].tags;
                    attributes = format[i].attr;
                    while (node && dom.isAncestorOf(editableParent, node)) {
                        if (dom.ofType(node, tags) && dom.attrEquals(node, attributes)) {
                            return node;
                        }
                        node = node.parentNode;
                    }
                }
                return null;
            },
            getFormat: function (nodes) {
                var that = this, findFormat = function (node) {
                        return that.findFormat(dom.isDataNode(node) ? node.parentNode : node);
                    }, result = findFormat(nodes[0]), i, len;
                if (!result) {
                    return '';
                }
                for (i = 1, len = nodes.length; i < len; i++) {
                    if (result != findFormat(nodes[i])) {
                        return '';
                    }
                }
                return result.nodeName.toLowerCase();
            },
            isFormatted: function (nodes) {
                for (var i = 0, len = nodes.length; i < len; i++) {
                    if (!this.findFormat(nodes[i])) {
                        return false;
                    }
                }
                return true;
            }
        });
        var BlockFormatter = Class.extend({
            init: function (format, values) {
                this.format = format;
                this.values = values;
                this.finder = new BlockFormatFinder(format);
            },
            wrap: function (tag, attributes, nodes) {
                var commonAncestor = nodes.length == 1 ? dom.blockParentOrBody(nodes[0]) : dom.commonAncestor.apply(null, nodes);
                if (dom.isInline(commonAncestor)) {
                    commonAncestor = dom.blockParentOrBody(commonAncestor);
                }
                var ancestors = dom.significantChildNodes(commonAncestor), position = dom.findNodeIndex(ancestors[0]), wrapper = dom.create(commonAncestor.ownerDocument, tag, attributes), i, ancestor;
                for (i = 0; i < ancestors.length; i++) {
                    ancestor = ancestors[i];
                    if (dom.isBlock(ancestor)) {
                        dom.attr(ancestor, attributes);
                        if (wrapper.childNodes.length) {
                            dom.insertBefore(wrapper, ancestor);
                            wrapper = wrapper.cloneNode(false);
                        }
                        position = dom.findNodeIndex(ancestor) + 1;
                        continue;
                    }
                    wrapper.appendChild(ancestor);
                }
                if (wrapper.firstChild) {
                    dom.insertAt(commonAncestor, wrapper, position);
                }
            },
            apply: function (nodes) {
                var format, values = this.values;
                function attributes(format) {
                    return extend({}, format && format.attr, values);
                }
                var images = dom.filter('img', nodes);
                var imageFormat = EditorUtils.formatByName('img', this.format);
                var imageAttributes = attributes(imageFormat);
                $.each(images, function () {
                    dom.attr(this, imageAttributes);
                });
                if (images.length == nodes.length) {
                    return;
                }
                var nonImages = dom.filter('img', nodes, true);
                var formatNodes = this.finder.findSuitable(nonImages);
                if (formatNodes.length) {
                    for (var i = 0, len = formatNodes.length; i < len; i++) {
                        format = EditorUtils.formatByName(dom.name(formatNodes[i]), this.format);
                        dom.attr(formatNodes[i], attributes(format));
                    }
                } else {
                    format = this.format[0];
                    this.wrap(format.tags[0], attributes(format), nonImages);
                }
            },
            remove: function (nodes) {
                var i, l, formatNode, namedFormat, name;
                for (i = 0, l = nodes.length; i < l; i++) {
                    formatNode = this.finder.findFormat(nodes[i]);
                    if (formatNode) {
                        name = dom.name(formatNode);
                        if (name == 'div' && !formatNode.getAttribute('class')) {
                            dom.unwrap(formatNode);
                        } else {
                            namedFormat = EditorUtils.formatByName(name, this.format);
                            if (namedFormat.attr.style) {
                                dom.unstyle(formatNode, namedFormat.attr.style);
                            }
                            if (namedFormat.attr.className) {
                                dom.removeClass(formatNode, namedFormat.attr.className);
                            }
                        }
                    }
                }
            },
            toggle: function (range) {
                var that = this, nodes = RangeUtils.nodes(range);
                if (that.finder.isFormatted(nodes)) {
                    that.remove(nodes);
                } else {
                    that.apply(nodes);
                }
            }
        });
        var GreedyBlockFormatter = Class.extend({
            init: function (format, values) {
                var that = this;
                that.format = format;
                that.values = values;
                that.finder = new BlockFormatFinder(format);
            },
            apply: function (nodes) {
                var format = this.format;
                var blocks = dom.blockParents(nodes);
                var formatTag = format[0].tags[0];
                var i, len, list, formatter, range;
                var element;
                var tagName;
                if (blocks.length) {
                    for (i = 0, len = blocks.length; i < len; i++) {
                        tagName = dom.name(blocks[i]);
                        if (tagName == 'li') {
                            list = blocks[i].parentNode;
                            formatter = new Editor.ListFormatter(list.nodeName.toLowerCase(), formatTag);
                            range = this.editor.createRange();
                            range.selectNode(blocks[i]);
                            formatter.toggle(range);
                        } else if (formatTag && (tagName == 'td' || blocks[i].attributes.contentEditable)) {
                            new BlockFormatter(format, this.values).apply(blocks[i].childNodes);
                        } else {
                            element = dom.changeTag(blocks[i], formatTag);
                            dom.attr(element, format[0].attr);
                        }
                    }
                } else {
                    new BlockFormatter(format, this.values).apply(nodes);
                }
            },
            toggle: function (range) {
                var nodes = RangeUtils.textNodes(range);
                if (!nodes.length) {
                    range.selectNodeContents(range.commonAncestorContainer);
                    nodes = RangeUtils.textNodes(range);
                    if (!nodes.length) {
                        nodes = dom.significantChildNodes(range.commonAncestorContainer);
                    }
                }
                this.apply(nodes);
            }
        });
        var FormatCommand = Command.extend({
            init: function (options) {
                options.formatter = options.formatter();
                Command.fn.init.call(this, options);
            }
        });
        var BlockFormatTool = FormatTool.extend({
            init: function (options) {
                FormatTool.fn.init.call(this, extend(options, {
                    finder: new BlockFormatFinder(options.format),
                    formatter: function () {
                        return new BlockFormatter(options.format);
                    }
                }));
            }
        });
        extend(Editor, {
            BlockFormatFinder: BlockFormatFinder,
            BlockFormatter: BlockFormatter,
            GreedyBlockFormatter: GreedyBlockFormatter,
            FormatCommand: FormatCommand,
            BlockFormatTool: BlockFormatTool
        });
        var listElements = [
            'ul',
            'ol',
            'li'
        ];
        registerFormat('justifyLeft', [
            {
                tags: dom.nonListBlockElements,
                attr: { style: { textAlign: 'left' } }
            },
            {
                tags: ['img'],
                attr: {
                    style: {
                        'float': 'left',
                        display: '',
                        marginLeft: '',
                        marginRight: ''
                    }
                }
            },
            {
                tags: listElements,
                attr: {
                    style: {
                        textAlign: 'left',
                        listStylePosition: ''
                    }
                }
            }
        ]);
        registerTool('justifyLeft', new BlockFormatTool({
            format: formats.justifyLeft,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Justify Left'
            })
        }));
        registerFormat('justifyCenter', [
            {
                tags: dom.nonListBlockElements,
                attr: { style: { textAlign: 'center' } }
            },
            {
                tags: ['img'],
                attr: {
                    style: {
                        display: 'block',
                        marginLeft: 'auto',
                        marginRight: 'auto',
                        'float': ''
                    }
                }
            },
            {
                tags: listElements,
                attr: {
                    style: {
                        textAlign: 'center',
                        listStylePosition: 'inside'
                    }
                }
            }
        ]);
        registerTool('justifyCenter', new BlockFormatTool({
            format: formats.justifyCenter,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Justify Center'
            })
        }));
        registerFormat('justifyRight', [
            {
                tags: dom.nonListBlockElements,
                attr: { style: { textAlign: 'right' } }
            },
            {
                tags: ['img'],
                attr: {
                    style: {
                        'float': 'right',
                        display: '',
                        marginLeft: '',
                        marginRight: ''
                    }
                }
            },
            {
                tags: listElements,
                attr: {
                    style: {
                        textAlign: 'right',
                        listStylePosition: 'inside'
                    }
                }
            }
        ]);
        registerTool('justifyRight', new BlockFormatTool({
            format: formats.justifyRight,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Justify Right'
            })
        }));
        registerFormat('justifyFull', [
            {
                tags: dom.nonListBlockElements,
                attr: { style: { textAlign: 'justify' } }
            },
            {
                tags: ['img'],
                attr: {
                    style: {
                        display: 'block',
                        marginLeft: 'auto',
                        marginRight: 'auto',
                        'float': ''
                    }
                }
            },
            {
                tags: listElements,
                attr: {
                    style: {
                        textAlign: 'justify',
                        listStylePosition: ''
                    }
                }
            }
        ]);
        registerTool('justifyFull', new BlockFormatTool({
            format: formats.justifyFull,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Justify Full'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/linebreak', ['editor/formatblock'], f);
}(function () {
    (function ($) {
        var kendo = window.kendo, extend = $.extend, editorNS = kendo.ui.editor, dom = editorNS.Dom, Command = editorNS.Command, Tool = editorNS.Tool, BlockFormatter = editorNS.BlockFormatter, normalize = dom.normalize, RangeUtils = editorNS.RangeUtils, registerTool = editorNS.EditorUtils.registerTool;
        var ParagraphCommand = Command.extend({
            init: function (options) {
                this.options = options;
                Command.fn.init.call(this, options);
            },
            _insertMarker: function (doc, range) {
                var marker = dom.create(doc, 'a'), container;
                marker.className = 'k-marker';
                range.insertNode(marker);
                if (!marker.parentNode) {
                    container = range.commonAncestorContainer;
                    container.innerHTML = '';
                    container.appendChild(marker);
                }
                normalize(marker.parentNode);
                return marker;
            },
            _moveFocus: function (range, candidate) {
                if (dom.isEmpty(candidate)) {
                    range.setStartBefore(candidate);
                } else {
                    range.selectNodeContents(candidate);
                    var focusNode = RangeUtils.textNodes(range)[0];
                    if (!focusNode) {
                        while (candidate.childNodes.length && !dom.is(candidate.firstChild, 'br')) {
                            candidate = candidate.firstChild;
                        }
                        focusNode = candidate;
                    }
                    if (dom.isEmpty(focusNode)) {
                        range.setStartBefore(focusNode);
                    } else {
                        if (dom.emptyNode(focusNode)) {
                            focusNode.innerHTML = '\uFEFF';
                        }
                        range.setStartBefore(focusNode.firstChild || focusNode);
                    }
                }
            },
            shouldTrim: function (range) {
                var blocks = 'p,h1,h2,h3,h4,h5,h6'.split(','), startInBlock = dom.parentOfType(range.startContainer, blocks), endInBlock = dom.parentOfType(range.endContainer, blocks);
                return startInBlock && !endInBlock || !startInBlock && endInBlock;
            },
            _blankAfter: function (node) {
                while (node && (dom.isMarker(node) || dom.stripBom(node.nodeValue) === '')) {
                    node = node.nextSibling;
                }
                return !node;
            },
            exec: function () {
                var range = this.getRange(), doc = RangeUtils.documentFromRange(range), parent, previous, next, emptyParagraphContent = editorNS.emptyElementContent, paragraph, marker, li, heading, rng, shouldTrim = this.shouldTrim(range);
                range.deleteContents();
                marker = this._insertMarker(doc, range);
                dom.stripBomNode(marker.previousSibling);
                dom.stripBomNode(marker.nextSibling);
                li = dom.closestEditableOfType(marker, ['li']);
                heading = dom.closestEditableOfType(marker, 'h1,h2,h3,h4,h5,h6'.split(','));
                if (li) {
                    if (dom.emptyNode(li)) {
                        paragraph = dom.create(doc, 'p');
                        if (li.nextSibling) {
                            rng = range.cloneRange();
                            rng.selectNode(li);
                            RangeUtils.split(rng, li.parentNode);
                        }
                        dom.insertAfter(paragraph, li.parentNode);
                        dom.remove(li.parentNode.childNodes.length == 1 ? li.parentNode : li);
                        paragraph.innerHTML = emptyParagraphContent;
                        next = paragraph;
                    }
                } else if (heading && this._blankAfter(marker)) {
                    paragraph = dom.create(doc, 'p');
                    dom.insertAfter(paragraph, heading);
                    paragraph.innerHTML = emptyParagraphContent;
                    dom.remove(marker);
                    next = paragraph;
                }
                if (!next) {
                    if (!(li || heading)) {
                        new BlockFormatter([{ tags: ['p'] }]).apply([marker]);
                    }
                    range.selectNode(marker);
                    parent = dom.parentOfType(marker, [li ? 'li' : heading ? dom.name(heading) : 'p']);
                    RangeUtils.split(range, parent, shouldTrim);
                    previous = parent.previousSibling;
                    if (dom.is(previous, 'li') && previous.firstChild && !dom.is(previous.firstChild, 'br')) {
                        previous = previous.firstChild;
                    }
                    next = parent.nextSibling;
                    this.clean(previous);
                    this.clean(next, { links: true });
                    if (dom.is(next, 'li') && next.firstChild && !dom.is(next.firstChild, 'br')) {
                        next = next.firstChild;
                    }
                    dom.remove(parent);
                    normalize(previous);
                }
                normalize(next);
                this._moveFocus(range, next);
                range.collapse(true);
                dom.scrollTo(next);
                RangeUtils.selectRange(range);
            },
            clean: function (node, options) {
                var root = node;
                if (node.firstChild && dom.is(node.firstChild, 'br')) {
                    dom.remove(node.firstChild);
                }
                if (dom.isDataNode(node) && !node.nodeValue) {
                    node = node.parentNode;
                }
                if (node) {
                    var siblings = false;
                    while (node.firstChild && node.firstChild.nodeType == 1) {
                        siblings = siblings || dom.significantNodes(node.childNodes).length > 1;
                        node = node.firstChild;
                    }
                    if (!dom.isEmpty(node) && /^\s*$/.test(node.innerHTML) && !siblings) {
                        $(root).find('.k-br').remove();
                        node.innerHTML = editorNS.emptyElementContent;
                    }
                    if (options && options.links) {
                        while (node != root) {
                            if (dom.is(node, 'a') && dom.emptyNode(node)) {
                                dom.unwrap(node);
                                break;
                            }
                            node = node.parentNode;
                        }
                    }
                }
            }
        });
        var NewLineCommand = Command.extend({
            init: function (options) {
                this.options = options;
                Command.fn.init.call(this, options);
            },
            exec: function () {
                var range = this.getRange();
                var br = dom.create(RangeUtils.documentFromRange(range), 'br');
                var filler;
                var browser = kendo.support.browser;
                var oldIE = browser.msie && browser.version < 11;
                range.deleteContents();
                range.insertNode(br);
                normalize(br.parentNode);
                if (!oldIE && (!br.nextSibling || dom.isWhitespace(br.nextSibling))) {
                    filler = br.cloneNode(true);
                    filler.className = 'k-br';
                    dom.insertAfter(filler, br);
                }
                range.setStartAfter(br);
                range.collapse(true);
                dom.scrollTo(br.nextSibling || br);
                RangeUtils.selectRange(range);
            }
        });
        extend(editorNS, {
            ParagraphCommand: ParagraphCommand,
            NewLineCommand: NewLineCommand
        });
        registerTool('insertLineBreak', new Tool({
            key: 13,
            shift: true,
            command: NewLineCommand
        }));
        registerTool('insertParagraph', new Tool({
            key: 13,
            command: ParagraphCommand
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/lists', ['editor/linebreak'], f);
}(function () {
    (function ($) {
        var kendo = window.kendo, Class = kendo.Class, extend = $.extend, Editor = kendo.ui.editor, dom = Editor.Dom, RangeUtils = Editor.RangeUtils, EditorUtils = Editor.EditorUtils, Command = Editor.Command, ToolTemplate = Editor.ToolTemplate, FormatTool = Editor.FormatTool, BlockFormatFinder = Editor.BlockFormatFinder, textNodes = RangeUtils.textNodes, registerTool = Editor.EditorUtils.registerTool;
        var ListFormatFinder = BlockFormatFinder.extend({
            init: function (tag) {
                this.tag = tag;
                var tags = this.tags = [
                    tag == 'ul' ? 'ol' : 'ul',
                    tag
                ];
                BlockFormatFinder.fn.init.call(this, [{ tags: tags }]);
            },
            isFormatted: function (nodes) {
                var formatNodes = [];
                var formatNode, i;
                for (i = 0; i < nodes.length; i++) {
                    formatNode = this.findFormat(nodes[i]);
                    if (formatNode && dom.name(formatNode) == this.tag) {
                        formatNodes.push(formatNode);
                    }
                }
                if (formatNodes.length < 1) {
                    return false;
                }
                if (formatNodes.length != nodes.length) {
                    return false;
                }
                for (i = 0; i < formatNodes.length; i++) {
                    if (formatNodes[i].parentNode != formatNode.parentNode) {
                        break;
                    }
                    if (formatNodes[i] != formatNode) {
                        return false;
                    }
                }
                return true;
            },
            findSuitable: function (nodes) {
                var candidate = this.findFormat(nodes[0]);
                if (candidate && dom.name(candidate) == this.tag) {
                    return candidate;
                }
                return null;
            }
        });
        var ListFormatter = Class.extend({
            init: function (tag, unwrapTag) {
                var that = this;
                that.finder = new ListFormatFinder(tag);
                that.tag = tag;
                that.unwrapTag = unwrapTag;
            },
            isList: function (node) {
                var name = dom.name(node);
                return name == 'ul' || name == 'ol' || name == 'dl';
            },
            wrap: function (list, nodes) {
                var li = dom.create(list.ownerDocument, 'li'), i, node;
                for (i = 0; i < nodes.length; i++) {
                    node = nodes[i];
                    if (dom.is(node, 'li')) {
                        list.appendChild(node);
                        continue;
                    }
                    if (this.isList(node)) {
                        while (node.firstChild) {
                            list.appendChild(node.firstChild);
                        }
                        continue;
                    }
                    if (dom.is(node, 'td')) {
                        while (node.firstChild) {
                            li.appendChild(node.firstChild);
                        }
                        list.appendChild(li);
                        node.appendChild(list);
                        list = list.cloneNode(false);
                        li = li.cloneNode(false);
                        continue;
                    }
                    li.appendChild(node);
                    if (dom.isBlock(node)) {
                        list.appendChild(li);
                        dom.unwrap(node);
                        li = li.cloneNode(false);
                    }
                }
                if (li.firstChild) {
                    list.appendChild(li);
                }
            },
            containsAny: function (parent, nodes) {
                for (var i = 0; i < nodes.length; i++) {
                    if (dom.isAncestorOrSelf(parent, nodes[i])) {
                        return true;
                    }
                }
                return false;
            },
            suitable: function (candidate, nodes) {
                if (candidate.className == 'k-marker') {
                    var sibling = candidate.nextSibling;
                    if (sibling && dom.isBlock(sibling)) {
                        return false;
                    }
                    sibling = candidate.previousSibling;
                    if (sibling && dom.isBlock(sibling)) {
                        return false;
                    }
                }
                return this.containsAny(candidate, nodes) || dom.isInline(candidate) || candidate.nodeType == 3;
            },
            _parentLists: function (node) {
                var editable = dom.closestEditable(node);
                return $(node).parentsUntil(editable, 'ul,ol');
            },
            split: function (range) {
                var nodes = textNodes(range);
                var start, end, parents;
                if (nodes.length) {
                    start = dom.parentOfType(nodes[0], ['li']);
                    end = dom.parentOfType(nodes[nodes.length - 1], ['li']);
                    range.setStartBefore(start);
                    range.setEndAfter(end);
                    for (var i = 0, l = nodes.length; i < l; i++) {
                        var formatNode = this.finder.findFormat(nodes[i]);
                        if (formatNode) {
                            parents = this._parentLists(formatNode);
                            if (parents.length) {
                                RangeUtils.split(range, parents.last()[0], true);
                            } else {
                                RangeUtils.split(range, formatNode, true);
                            }
                        }
                    }
                }
            },
            merge: function (tag, formatNode) {
                var prev = formatNode.previousSibling, next;
                while (prev && (prev.className == 'k-marker' || prev.nodeType == 3 && dom.isWhitespace(prev))) {
                    prev = prev.previousSibling;
                }
                if (prev && dom.name(prev) == tag) {
                    while (formatNode.firstChild) {
                        prev.appendChild(formatNode.firstChild);
                    }
                    dom.remove(formatNode);
                    formatNode = prev;
                }
                next = formatNode.nextSibling;
                while (next && (next.className == 'k-marker' || next.nodeType == 3 && dom.isWhitespace(next))) {
                    next = next.nextSibling;
                }
                if (next && dom.name(next) == tag) {
                    while (formatNode.lastChild) {
                        next.insertBefore(formatNode.lastChild, next.firstChild);
                    }
                    dom.remove(formatNode);
                }
            },
            breakable: function (node) {
                return node != node.ownerDocument.body && !/table|tbody|tr|td/.test(dom.name(node)) && !node.attributes.contentEditable;
            },
            applyOnSection: function (section, nodes) {
                var tag = this.tag;
                var commonAncestor = dom.closestSplittableParent(nodes);
                var ancestors = [];
                var formatNode = this.finder.findSuitable(nodes);
                if (!formatNode) {
                    formatNode = new ListFormatFinder(tag == 'ul' ? 'ol' : 'ul').findSuitable(nodes);
                }
                var childNodes;
                if (/table|tbody/.test(dom.name(commonAncestor))) {
                    childNodes = $.map(nodes, function (node) {
                        return dom.parentOfType(node, ['td']);
                    });
                } else {
                    childNodes = dom.significantChildNodes(commonAncestor);
                    if ($.grep(childNodes, dom.isBlock).length) {
                        childNodes = $.grep(childNodes, $.proxy(function (node) {
                            return this.containsAny(node, nodes);
                        }, this));
                    }
                    if (!childNodes.length) {
                        childNodes = nodes;
                    }
                }
                function pushAncestor() {
                    ancestors.push(this);
                }
                for (var i = 0; i < childNodes.length; i++) {
                    var child = childNodes[i];
                    var suitable = (!formatNode || !dom.isAncestorOrSelf(formatNode, child)) && this.suitable(child, nodes);
                    if (!suitable) {
                        continue;
                    }
                    if (formatNode && this.isList(child)) {
                        $.each(child.childNodes, pushAncestor);
                        dom.remove(child);
                    } else {
                        ancestors.push(child);
                    }
                }
                if (ancestors.length == childNodes.length && this.breakable(commonAncestor)) {
                    ancestors = [commonAncestor];
                }
                if (!formatNode) {
                    formatNode = dom.create(commonAncestor.ownerDocument, tag);
                    dom.insertBefore(formatNode, ancestors[0]);
                }
                this.wrap(formatNode, ancestors);
                if (!dom.is(formatNode, tag)) {
                    dom.changeTag(formatNode, tag);
                }
                this.merge(tag, formatNode);
            },
            apply: function (nodes) {
                var i = 0, sections = [], lastSection, lastNodes, section;
                do {
                    section = dom.closestEditable(nodes[i], [
                        'td',
                        'body'
                    ]);
                    if (!lastSection || section != lastSection) {
                        if (lastSection) {
                            sections.push({
                                section: lastSection,
                                nodes: lastNodes
                            });
                        }
                        lastNodes = [nodes[i]];
                        lastSection = section;
                    } else {
                        lastNodes.push(nodes[i]);
                    }
                    i++;
                } while (i < nodes.length);
                sections.push({
                    section: lastSection,
                    nodes: lastNodes
                });
                for (i = 0; i < sections.length; i++) {
                    this.applyOnSection(sections[i].section, sections[i].nodes);
                }
            },
            unwrap: function (ul) {
                var fragment = ul.ownerDocument.createDocumentFragment(), unwrapTag = this.unwrapTag, parents, li, p, child;
                for (li = ul.firstChild; li; li = li.nextSibling) {
                    p = dom.create(ul.ownerDocument, unwrapTag || 'p');
                    while (li.firstChild) {
                        child = li.firstChild;
                        if (dom.isBlock(child)) {
                            if (p.firstChild) {
                                fragment.appendChild(p);
                                p = dom.create(ul.ownerDocument, unwrapTag || 'p');
                            }
                            fragment.appendChild(child);
                        } else {
                            p.appendChild(child);
                        }
                    }
                    if (p.firstChild) {
                        fragment.appendChild(p);
                    }
                }
                parents = this._parentLists(ul);
                if (parents[0]) {
                    dom.insertAfter(fragment, parents.last()[0]);
                    parents.last().remove();
                } else {
                    dom.insertAfter(fragment, ul);
                }
                dom.remove(ul);
            },
            remove: function (nodes) {
                var formatNode;
                for (var i = 0, l = nodes.length; i < l; i++) {
                    formatNode = this.finder.findFormat(nodes[i]);
                    if (formatNode) {
                        this.unwrap(formatNode);
                    }
                }
            },
            toggle: function (range) {
                var that = this, nodes = textNodes(range), ancestor = range.commonAncestorContainer;
                if (!nodes.length) {
                    range.selectNodeContents(ancestor);
                    nodes = textNodes(range);
                    if (!nodes.length) {
                        var text = ancestor.ownerDocument.createTextNode('');
                        range.startContainer.appendChild(text);
                        nodes = [text];
                        range.selectNode(text.parentNode);
                    }
                }
                if (that.finder.isFormatted(nodes)) {
                    that.split(range);
                    that.remove(nodes);
                } else {
                    that.apply(nodes);
                }
            }
        });
        var ListCommand = Command.extend({
            init: function (options) {
                options.formatter = new ListFormatter(options.tag);
                Command.fn.init.call(this, options);
            }
        });
        var ListTool = FormatTool.extend({
            init: function (options) {
                this.options = options;
                FormatTool.fn.init.call(this, extend(options, { finder: new ListFormatFinder(options.tag) }));
            },
            command: function (commandArguments) {
                return new ListCommand(extend(commandArguments, { tag: this.options.tag }));
            }
        });
        extend(Editor, {
            ListFormatFinder: ListFormatFinder,
            ListFormatter: ListFormatter,
            ListCommand: ListCommand,
            ListTool: ListTool
        });
        registerTool('insertUnorderedList', new ListTool({
            tag: 'ul',
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Insert unordered list'
            })
        }));
        registerTool('insertOrderedList', new ListTool({
            tag: 'ol',
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Insert ordered list'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/link', ['editor/lists'], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo, Class = kendo.Class, extend = $.extend, proxy = $.proxy, Editor = kendo.ui.editor, dom = Editor.Dom, RangeUtils = Editor.RangeUtils, EditorUtils = Editor.EditorUtils, Command = Editor.Command, Tool = Editor.Tool, ToolTemplate = Editor.ToolTemplate, InlineFormatter = Editor.InlineFormatter, InlineFormatFinder = Editor.InlineFormatFinder, textNodes = RangeUtils.textNodes, registerTool = Editor.EditorUtils.registerTool, keys = kendo.keys;
        var HTTP_PROTOCOL = 'http://';
        var protocolRegExp = /^\w*:\/\//;
        var endLinkCharsRegExp = /[\w\/\$\-_\*\?]/i;
        var LinkFormatFinder = Class.extend({
            findSuitable: function (sourceNode) {
                return dom.parentOfType(sourceNode, ['a']);
            }
        });
        var LinkFormatter = Class.extend({
            init: function () {
                this.finder = new LinkFormatFinder();
            },
            apply: function (range, attributes) {
                var nodes = textNodes(range);
                var markers, doc, formatter, a, parent;
                if (attributes.innerHTML) {
                    doc = RangeUtils.documentFromRange(range);
                    markers = RangeUtils.getMarkers(range);
                    range.deleteContents();
                    a = dom.create(doc, 'a', attributes);
                    range.insertNode(a);
                    parent = a.parentNode;
                    if (dom.name(parent) == 'a') {
                        dom.insertAfter(a, parent);
                    }
                    if (dom.emptyNode(parent)) {
                        dom.remove(parent);
                    }
                    var ref = a;
                    for (var i = 0; i < markers.length; i++) {
                        dom.insertAfter(markers[i], ref);
                        ref = markers[i];
                    }
                    if (markers.length) {
                        dom.insertBefore(doc.createTextNode('\uFEFF'), markers[1]);
                        dom.insertAfter(doc.createTextNode('\uFEFF'), markers[1]);
                        range.setStartBefore(markers[0]);
                        range.setEndAfter(markers[markers.length - 1]);
                    }
                } else {
                    formatter = new InlineFormatter([{ tags: ['a'] }], attributes);
                    formatter.finder = this.finder;
                    formatter.apply(nodes);
                }
            }
        });
        var UnlinkCommand = Command.extend({
            init: function (options) {
                options.formatter = {
                    toggle: function (range) {
                        new InlineFormatter([{ tags: ['a'] }]).remove(textNodes(range));
                    }
                };
                this.options = options;
                Command.fn.init.call(this, options);
            }
        });
        var LinkCommand = Command.extend({
            init: function (options) {
                this.options = options;
                Command.fn.init.call(this, options);
                this.formatter = new LinkFormatter();
                if (!options.url) {
                    this.attributes = null;
                    this.async = true;
                } else {
                    this.exec = function () {
                        this.formatter.apply(options.range, {
                            href: options.url,
                            innerHTML: options.text || options.url,
                            target: options.target
                        });
                    };
                }
            },
            _dialogTemplate: function () {
                return kendo.template('<div class="k-editor-dialog k-popup-edit-form k-edit-form-container">' + '<div class=\'k-edit-label\'>' + '<label for=\'k-editor-link-url\'>#: messages.linkWebAddress #</label>' + '</div>' + '<div class=\'k-edit-field\'>' + '<input type=\'text\' class=\'k-input k-textbox\' id=\'k-editor-link-url\'>' + '</div>' + '<div class=\'k-edit-label k-editor-link-text-row\'>' + '<label for=\'k-editor-link-text\'>#: messages.linkText #</label>' + '</div>' + '<div class=\'k-edit-field k-editor-link-text-row\'>' + '<input type=\'text\' class=\'k-input k-textbox\' id=\'k-editor-link-text\'>' + '</div>' + '<div class=\'k-edit-label\'>' + '<label for=\'k-editor-link-title\'>#: messages.linkToolTip #</label>' + '</div>' + '<div class=\'k-edit-field\'>' + '<input type=\'text\' class=\'k-input k-textbox\' id=\'k-editor-link-title\'>' + '</div>' + '<div class=\'k-edit-label\'></div>' + '<div class=\'k-edit-field\'>' + '<input type=\'checkbox\' class=\'k-checkbox\' id=\'k-editor-link-target\'>' + '<label for=\'k-editor-link-target\' class=\'k-checkbox-label\'>#: messages.linkOpenInNewWindow #</label>' + '</div>' + '<div class=\'k-edit-buttons k-state-default\'>' + '<button class="k-dialog-insert k-button k-primary">#: messages.dialogInsert #</button>' + '<button class="k-dialog-close k-button">#: messages.dialogCancel #</button>' + '</div>' + '</div>')({ messages: this.editor.options.messages });
            },
            exec: function () {
                var messages = this.editor.options.messages;
                this._initialText = '';
                this._range = this.lockRange(true);
                var nodes = textNodes(this._range);
                var a = nodes.length ? this.formatter.finder.findSuitable(nodes[0]) : null;
                var img = nodes.length && dom.name(nodes[0]) == 'img';
                var dialog = this.createDialog(this._dialogTemplate(), {
                    title: messages.createLink,
                    close: proxy(this._close, this),
                    visible: false
                });
                if (a) {
                    this._range.selectNodeContents(a);
                    nodes = textNodes(this._range);
                }
                this._initialText = this.linkText(nodes);
                dialog.find('.k-dialog-insert').click(proxy(this._apply, this)).end().find('.k-dialog-close').click(proxy(this._close, this)).end().find('.k-edit-field input').keydown(proxy(this._keydown, this)).end().find('#k-editor-link-url').val(this.linkUrl(a)).end().find('#k-editor-link-text').val(this._initialText).end().find('#k-editor-link-title').val(a ? a.title : '').end().find('#k-editor-link-target').attr('checked', a ? a.target == '_blank' : false).end().find('.k-editor-link-text-row').toggle(!img);
                this._dialog = dialog.data('kendoWindow').center().open();
                $('#k-editor-link-url', dialog).focus().select();
            },
            _keydown: function (e) {
                var keys = kendo.keys;
                if (e.keyCode == keys.ENTER) {
                    this._apply(e);
                } else if (e.keyCode == keys.ESC) {
                    this._close(e);
                }
            },
            _apply: function (e) {
                var element = this._dialog.element;
                var href = $('#k-editor-link-url', element).val();
                var title, text, target;
                var textInput = $('#k-editor-link-text', element);
                if (href && href != HTTP_PROTOCOL) {
                    if (href.indexOf('@') > 0 && !/^(\w+:)|(\/\/)/i.test(href)) {
                        href = 'mailto:' + href;
                    }
                    this.attributes = { href: href };
                    title = $('#k-editor-link-title', element).val();
                    if (title) {
                        this.attributes.title = title;
                    }
                    if (textInput.is(':visible')) {
                        text = textInput.val();
                        if (!text && !this._initialText) {
                            this.attributes.innerHTML = href;
                        } else if (text && text !== this._initialText) {
                            this.attributes.innerHTML = dom.stripBom(text);
                        }
                    }
                    target = $('#k-editor-link-target', element).is(':checked');
                    this.attributes.target = target ? '_blank' : null;
                    this.formatter.apply(this._range, this.attributes);
                }
                this._close(e);
                if (this.change) {
                    this.change();
                }
            },
            _close: function (e) {
                e.preventDefault();
                this._dialog.destroy();
                dom.windowFromDocument(RangeUtils.documentFromRange(this._range)).focus();
                this.releaseRange(this._range);
            },
            linkUrl: function (anchor) {
                if (anchor) {
                    return anchor.getAttribute('href', 2);
                }
                return HTTP_PROTOCOL;
            },
            linkText: function (nodes) {
                var text = '';
                var i;
                for (i = 0; i < nodes.length; i++) {
                    text += nodes[i].nodeValue;
                }
                return dom.stripBom(text || '');
            },
            redo: function () {
                var range = this.lockRange(true);
                this.formatter.apply(range, this.attributes);
                this.releaseRange(range);
            }
        });
        var AutoLinkCommand = Command.extend({
            init: function (options) {
                Command.fn.init.call(this, options);
                this.formatter = new LinkFormatter();
            },
            exec: function () {
                var detectedLink = this.detectLink();
                if (!detectedLink) {
                    return;
                }
                var range = this.getRange();
                var linkMarker = new kendo.ui.editor.Marker();
                var linkRange = range.cloneRange();
                linkRange.setStart(detectedLink.start.node, detectedLink.start.offset);
                linkRange.setEnd(detectedLink.end.node, detectedLink.end.offset);
                range = this.lockRange();
                linkMarker.add(linkRange);
                this.formatter.apply(linkRange, { href: this._ensureWebProtocol(detectedLink.text) });
                linkMarker.remove(linkRange);
                this.releaseRange(range);
            },
            detectLink: function () {
                var range = this.getRange();
                var traverser = new LeftDomTextTraverser({
                    node: range.startContainer,
                    offset: range.startOffset,
                    cancelAtNode: function (node) {
                        return node && dom.name(node) === 'a';
                    }
                });
                var detection = new DomTextLinkDetection(traverser);
                return detection.detectLink();
            },
            changesContent: function () {
                return !!this.detectLink();
            },
            _ensureWebProtocol: function (linkText) {
                var hasProtocol = this._hasProtocolPrefix(linkText);
                return hasProtocol ? linkText : this._prefixWithWebProtocol(linkText);
            },
            _hasProtocolPrefix: function (linkText) {
                return protocolRegExp.test(linkText);
            },
            _prefixWithWebProtocol: function (linkText) {
                return HTTP_PROTOCOL + linkText;
            }
        });
        var UnlinkTool = Tool.extend({
            init: function (options) {
                this.options = options;
                this.finder = new InlineFormatFinder([{ tags: ['a'] }]);
                Tool.fn.init.call(this, $.extend(options, { command: UnlinkCommand }));
            },
            initialize: function (ui, options) {
                Tool.fn.initialize.call(this, ui, options);
                ui.addClass('k-state-disabled');
            },
            update: function (ui, nodes) {
                ui.toggleClass('k-state-disabled', !this.finder.isFormatted(nodes)).removeClass('k-state-hover');
            }
        });
        var DomTextLinkDetection = Class.extend({
            init: function (traverser) {
                this.traverser = traverser;
                this.start = DomPos();
                this.end = DomPos();
                this.text = '';
            },
            detectLink: function () {
                var node = this.traverser.node;
                var offset = this.traverser.offset;
                if (dom.isDataNode(node)) {
                    var text = node.data.substring(0, offset);
                    if (/\s{2}$/.test(dom.stripBom(text))) {
                        return;
                    }
                } else if (offset === 0) {
                    var p = dom.closestEditableOfType(node, dom.blockElements);
                    if (p && p.previousSibling) {
                        this.traverser.init({ node: p.previousSibling });
                    }
                }
                this.traverser.traverse($.proxy(this._detectEnd, this));
                if (!this.end.blank()) {
                    this.traverser = this.traverser.clone(this.end);
                    this.traverser.traverse($.proxy(this._detectStart, this));
                    if (!this._isLinkDetected()) {
                        var puntuationOptions = this.traverser.extendOptions(this.start);
                        var puntuationTraverser = new RightDomTextTraverser(puntuationOptions);
                        puntuationTraverser.traverse($.proxy(this._skipStartPuntuation, this));
                        if (!this._isLinkDetected()) {
                            this.start = DomPos();
                        }
                    }
                }
                if (this.start.blank()) {
                    return null;
                } else {
                    return {
                        start: this.start,
                        end: this.end,
                        text: this.text
                    };
                }
            },
            _isLinkDetected: function () {
                return protocolRegExp.test(this.text) || /^w{3}\./i.test(this.text);
            },
            _detectEnd: function (text, node) {
                var i = lastIndexOfRegExp(text, endLinkCharsRegExp);
                if (i > -1) {
                    this.end.node = node;
                    this.end.offset = i + 1;
                    return false;
                }
            },
            _detectStart: function (text, node) {
                var i = lastIndexOfRegExp(text, /\s/);
                var ii = i + 1;
                this.text = text.substring(ii) + this.text;
                this.start.node = node;
                this.start.offset = ii;
                if (i > -1) {
                    return false;
                }
            },
            _skipStartPuntuation: function (text, node, offset) {
                var i = indexOfRegExp(text, /\w/);
                var ii = i;
                if (i === -1) {
                    ii = text.length;
                }
                this.text = this.text.substring(ii);
                this.start.node = node;
                this.start.offset = ii + (offset | 0);
                if (i > -1) {
                    return false;
                }
            }
        });
        function lastIndexOfRegExp(str, search) {
            var i = str.length;
            while (i-- && !search.test(str[i])) {
            }
            return i;
        }
        function indexOfRegExp(str, search) {
            var r = search.exec(str);
            return r ? r.index : -1;
        }
        var DomPos = function () {
            return {
                node: null,
                offset: null,
                blank: function () {
                    return this.node === null && this.offset === null;
                }
            };
        };
        var DomTextTraverser = Class.extend({
            init: function (options) {
                this.node = options.node;
                this.offset = options.offset === undefined ? dom.isDataNode(this.node) && this.node.length || 0 : options.offset;
                this.cancelAtNode = options.cancelAtNode || this.cancelAtNode || $.noop;
            },
            traverse: function (callback) {
                if (!callback) {
                    return;
                }
                this.cancel = false;
                this._traverse(callback, this.node, this.offset);
            },
            _traverse: function (callback, node, offset) {
                if (!node || this.cancel) {
                    return;
                }
                if (node.nodeType === 3) {
                    var text = node.data;
                    if (offset !== undefined) {
                        text = this.subText(text, offset);
                    }
                    this.cancel = callback(text, node, offset) === false;
                } else {
                    var edgeNode = this.edgeNode(node);
                    this.cancel = this.cancel || this.cancelAtNode(edgeNode);
                    return this._traverse(callback, edgeNode);
                }
                var next = this.next(node);
                if (!next) {
                    var parent = node.parentNode;
                    while (!next && dom.isInline(parent)) {
                        next = this.next(parent);
                        parent = parent.parentNode;
                    }
                }
                this.cancel = this.cancel || this.cancelAtNode(next);
                this._traverse(callback, next);
            },
            extendOptions: function (o) {
                return $.extend({
                    node: this.node,
                    offset: this.offset,
                    cancelAtNode: this.cancelAtNode
                }, o || {});
            },
            edgeNode: function (node) {
            },
            next: function (node) {
            },
            subText: function (text, offset) {
            }
        });
        var LeftDomTextTraverser = DomTextTraverser.extend({
            subText: function (text, splitIndex) {
                return text.substring(0, splitIndex);
            },
            next: function (node) {
                return node.previousSibling;
            },
            edgeNode: function (node) {
                return node.lastChild;
            },
            clone: function (options) {
                var o = this.extendOptions(options);
                return new LeftDomTextTraverser(o);
            }
        });
        var RightDomTextTraverser = DomTextTraverser.extend({
            subText: function (text, splitIndex) {
                return text.substring(splitIndex);
            },
            next: function (node) {
                return node.nextSibling;
            },
            edgeNode: function (node) {
                return node.firstChild;
            },
            clone: function (options) {
                var o = this.extendOptions(options);
                return new RightDomTextTraverser(o);
            }
        });
        extend(kendo.ui.editor, {
            LinkFormatFinder: LinkFormatFinder,
            LinkFormatter: LinkFormatter,
            UnlinkCommand: UnlinkCommand,
            LinkCommand: LinkCommand,
            AutoLinkCommand: AutoLinkCommand,
            UnlinkTool: UnlinkTool,
            DomTextLinkDetection: DomTextLinkDetection,
            LeftDomTextTraverser: LeftDomTextTraverser,
            RightDomTextTraverser: RightDomTextTraverser
        });
        registerTool('createLink', new Tool({
            key: 'K',
            ctrl: true,
            command: LinkCommand,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Create Link'
            })
        }));
        registerTool('unlink', new UnlinkTool({
            key: 'K',
            ctrl: true,
            shift: true,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Remove Link'
            })
        }));
        registerTool('autoLink', new Tool({
            key: [
                keys.ENTER,
                keys.SPACEBAR
            ],
            keyPressCommand: true,
            command: AutoLinkCommand
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/file', [
        'kendo.filebrowser',
        'editor/link'
    ], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo, extend = $.extend, Editor = kendo.ui.editor, EditorUtils = Editor.EditorUtils, dom = Editor.Dom, registerTool = EditorUtils.registerTool, ToolTemplate = Editor.ToolTemplate, RangeUtils = Editor.RangeUtils, Command = Editor.Command, LinkFormatter = Editor.LinkFormatter, textNodes = RangeUtils.textNodes, keys = kendo.keys, KEDITORFILEURL = '#k-editor-file-url', KEDITORFILETITLE = '#k-editor-file-title';
        var FileCommand = Command.extend({
            init: function (options) {
                var that = this;
                Command.fn.init.call(that, options);
                that.formatter = new LinkFormatter();
                that.async = true;
                that.attributes = {};
            },
            insertFile: function (file, range) {
                var attributes = this.attributes;
                var doc = RangeUtils.documentFromRange(range);
                if (attributes.href && attributes.href != 'http://') {
                    if (!file) {
                        file = dom.create(doc, 'a', { href: attributes.href });
                        file.innerHTML = attributes.innerHTML;
                        range.deleteContents();
                        range.insertNode(file);
                        if (!file.nextSibling) {
                            dom.insertAfter(doc.createTextNode('\uFEFF'), file);
                        }
                        range.setStartAfter(file);
                        range.setEndAfter(file);
                        RangeUtils.selectRange(range);
                        return true;
                    } else {
                        dom.attr(file, attributes);
                    }
                }
                return false;
            },
            _dialogTemplate: function (showBrowser) {
                return kendo.template('<div class="k-editor-dialog k-popup-edit-form k-edit-form-container">' + '# if (showBrowser) { #' + '<div class="k-filebrowser"></div>' + '# } #' + '<div class=\'k-edit-label\'>' + '<label for="k-editor-file-url">#: messages.fileWebAddress #</label>' + '</div>' + '<div class=\'k-edit-field\'>' + '<input type="text" class="k-input k-textbox" id="k-editor-file-url">' + '</div>' + '<div class=\'k-edit-label\'>' + '<label for="k-editor-file-title">#: messages.fileTitle #</label>' + '</div>' + '<div class=\'k-edit-field\'>' + '<input type="text" class="k-input k-textbox" id="k-editor-file-title">' + '</div>' + '<div class="k-edit-buttons k-state-default">' + '<button class="k-dialog-insert k-button k-primary">#: messages.dialogInsert #</button>' + '<button class="k-dialog-close k-button">#: messages.dialogCancel #</button>' + '</div>' + '</div>')({
                    messages: this.editor.options.messages,
                    showBrowser: showBrowser
                });
            },
            redo: function () {
                var that = this, range = that.lockRange();
                this.formatter.apply(range, this.attributes);
                that.releaseRange(range);
            },
            exec: function () {
                var that = this, range = that.lockRange(), nodes = textNodes(range), applied = false, file = nodes.length ? this.formatter.finder.findSuitable(nodes[0]) : null, dialog, options = that.editor.options, messages = options.messages, fileBrowser = options.fileBrowser, showBrowser = !!(kendo.ui.FileBrowser && fileBrowser && fileBrowser.transport && fileBrowser.transport.read !== undefined), dialogOptions = {
                        title: messages.insertFile,
                        visible: false,
                        resizable: showBrowser
                    };
                function apply(e) {
                    var element = dialog.element, href = element.find(KEDITORFILEURL).val().replace(/ /g, '%20'), innerHTML = element.find(KEDITORFILETITLE).val();
                    that.attributes = {
                        href: href,
                        innerHTML: innerHTML !== '' ? innerHTML : href
                    };
                    applied = that.insertFile(file, range);
                    close(e);
                    if (that.change) {
                        that.change();
                    }
                }
                function close(e) {
                    e.preventDefault();
                    dialog.destroy();
                    dom.windowFromDocument(RangeUtils.documentFromRange(range)).focus();
                    if (!applied) {
                        that.releaseRange(range);
                    }
                }
                function keyDown(e) {
                    if (e.keyCode == keys.ENTER) {
                        apply(e);
                    } else if (e.keyCode == keys.ESC) {
                        close(e);
                    }
                }
                dialogOptions.close = close;
                if (showBrowser) {
                    dialogOptions.width = 750;
                }
                dialog = this.createDialog(that._dialogTemplate(showBrowser), dialogOptions).toggleClass('k-filebrowser-dialog', showBrowser).find('.k-dialog-insert').click(apply).end().find('.k-dialog-close').click(close).end().find('.k-edit-field input').keydown(keyDown).end().find(KEDITORFILEURL).val(file ? file.getAttribute('href', 2) : 'http://').end().find(KEDITORFILETITLE).val(file ? file.title : '').end().data('kendoWindow');
                if (showBrowser) {
                    that._fileBrowser = new kendo.ui.FileBrowser(dialog.element.find('.k-filebrowser'), extend({}, fileBrowser, {
                        change: function () {
                            dialog.element.find(KEDITORFILEURL).val(this.value());
                        },
                        apply: apply
                    }));
                }
                dialog.center().open();
                dialog.element.find(KEDITORFILEURL).focus().select();
            }
        });
        kendo.ui.editor.FileCommand = FileCommand;
        registerTool('insertFile', new Editor.Tool({
            command: FileCommand,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Insert File'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/image', [
        'kendo.imagebrowser',
        'editor/link'
    ], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo, extend = $.extend, Editor = kendo.ui.editor, EditorUtils = Editor.EditorUtils, dom = Editor.Dom, registerTool = EditorUtils.registerTool, ToolTemplate = Editor.ToolTemplate, RangeUtils = Editor.RangeUtils, Command = Editor.Command, keys = kendo.keys, KEDITORIMAGEURL = '#k-editor-image-url', KEDITORIMAGETITLE = '#k-editor-image-title', KEDITORIMAGEWIDTH = '#k-editor-image-width', KEDITORIMAGEHEIGHT = '#k-editor-image-height';
        var ImageCommand = Command.extend({
            init: function (options) {
                var that = this;
                Command.fn.init.call(that, options);
                that.async = true;
                that.attributes = {};
            },
            insertImage: function (img, range) {
                var attributes = this.attributes;
                var doc = RangeUtils.documentFromRange(range);
                if (attributes.src && attributes.src != 'http://') {
                    var removeIEAttributes = function () {
                        setTimeout(function () {
                            if (!attributes.width) {
                                img.removeAttribute('width');
                            }
                            if (!attributes.height) {
                                img.removeAttribute('height');
                            }
                            img.removeAttribute('complete');
                        });
                    };
                    if (!img) {
                        img = dom.create(doc, 'img', attributes);
                        img.onload = img.onerror = removeIEAttributes;
                        range.deleteContents();
                        range.insertNode(img);
                        if (!img.nextSibling) {
                            dom.insertAfter(doc.createTextNode('\uFEFF'), img);
                        }
                        removeIEAttributes();
                        range.setStartAfter(img);
                        range.setEndAfter(img);
                        RangeUtils.selectRange(range);
                        return true;
                    } else {
                        img.onload = img.onerror = removeIEAttributes;
                        dom.attr(img, attributes);
                        removeIEAttributes();
                    }
                }
                return false;
            },
            _dialogTemplate: function (showBrowser) {
                return kendo.template('<div class="k-editor-dialog k-popup-edit-form k-edit-form-container">' + '# if (showBrowser) { #' + '<div class="k-filebrowser k-imagebrowser"></div>' + '# } #' + '<div class=\'k-edit-label\'>' + '<label for="k-editor-image-url">#: messages.imageWebAddress #</label>' + '</div>' + '<div class=\'k-edit-field\'>' + '<input type="text" class="k-input k-textbox" id="k-editor-image-url">' + '</div>' + '<div class=\'k-edit-label\'>' + '<label for="k-editor-image-title">#: messages.imageAltText #</label>' + '</div>' + '<div class=\'k-edit-field\'>' + '<input type="text" class="k-input k-textbox" id="k-editor-image-title">' + '</div>' + '<div class=\'k-edit-label\'>' + '<label for="k-editor-image-width">#: messages.imageWidth #</label>' + '</div>' + '<div class=\'k-edit-field\'>' + '<input type="text" class="k-input k-textbox" id="k-editor-image-width">' + '</div>' + '<div class=\'k-edit-label\'>' + '<label for="k-editor-image-height">#: messages.imageHeight #</label>' + '</div>' + '<div class=\'k-edit-field\'>' + '<input type="text" class="k-input k-textbox" id="k-editor-image-height">' + '</div>' + '<div class="k-edit-buttons k-state-default">' + '<button class="k-dialog-insert k-button k-primary">#: messages.dialogInsert #</button>' + '<button class="k-dialog-close k-button">#: messages.dialogCancel #</button>' + '</div>' + '</div>')({
                    messages: this.editor.options.messages,
                    showBrowser: showBrowser
                });
            },
            redo: function () {
                var that = this, range = that.lockRange();
                if (!that.insertImage(RangeUtils.image(range), range)) {
                    that.releaseRange(range);
                }
            },
            exec: function () {
                var that = this, range = that.lockRange(), applied = false, img = RangeUtils.image(range), imageWidth = img && img.getAttribute('width') || '', imageHeight = img && img.getAttribute('height') || '', dialog, options = that.editor.options, messages = options.messages, imageBrowser = options.imageBrowser, showBrowser = !!(kendo.ui.ImageBrowser && imageBrowser && imageBrowser.transport && imageBrowser.transport.read !== undefined), dialogOptions = {
                        title: messages.insertImage,
                        visible: false,
                        resizable: showBrowser
                    };
                function apply(e) {
                    var element = dialog.element, w = parseInt(element.find(KEDITORIMAGEWIDTH).val(), 10), h = parseInt(element.find(KEDITORIMAGEHEIGHT).val(), 10);
                    that.attributes = {
                        src: element.find(KEDITORIMAGEURL).val().replace(/ /g, '%20'),
                        alt: element.find(KEDITORIMAGETITLE).val()
                    };
                    that.attributes.width = null;
                    that.attributes.height = null;
                    if (!isNaN(w) && w > 0) {
                        that.attributes.width = w;
                    }
                    if (!isNaN(h) && h > 0) {
                        that.attributes.height = h;
                    }
                    applied = that.insertImage(img, range);
                    close(e);
                    if (that.change) {
                        that.change();
                    }
                }
                function close(e) {
                    e.preventDefault();
                    dialog.destroy();
                    dom.windowFromDocument(RangeUtils.documentFromRange(range)).focus();
                    if (!applied) {
                        that.releaseRange(range);
                    }
                }
                function keyDown(e) {
                    if (e.keyCode == keys.ENTER) {
                        apply(e);
                    } else if (e.keyCode == keys.ESC) {
                        close(e);
                    }
                }
                dialogOptions.close = close;
                if (showBrowser) {
                    dialogOptions.width = 750;
                }
                dialog = this.createDialog(that._dialogTemplate(showBrowser), dialogOptions).toggleClass('k-filebrowser-dialog', showBrowser).find('.k-dialog-insert').click(apply).end().find('.k-dialog-close').click(close).end().find('.k-edit-field input').keydown(keyDown).end().find(KEDITORIMAGEURL).val(img ? img.getAttribute('src', 2) : 'http://').end().find(KEDITORIMAGETITLE).val(img ? img.alt : '').end().find(KEDITORIMAGEWIDTH).val(imageWidth).end().find(KEDITORIMAGEHEIGHT).val(imageHeight).end().data('kendoWindow');
                if (showBrowser) {
                    this._imageBrowser = new kendo.ui.ImageBrowser(dialog.element.find('.k-imagebrowser'), extend({}, imageBrowser, {
                        change: function () {
                            dialog.element.find(KEDITORIMAGEURL).val(this.value());
                        },
                        apply: apply
                    }));
                }
                dialog.center().open();
                dialog.element.find(KEDITORIMAGEURL).focus().select();
            }
        });
        kendo.ui.editor.ImageCommand = ImageCommand;
        registerTool('insertImage', new Editor.Tool({
            command: ImageCommand,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Insert Image'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/components', ['editor/image'], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo, DropDownList = kendo.ui.DropDownList, dom = kendo.ui.editor.Dom;
        var SelectBox = DropDownList.extend({
            init: function (element, options) {
                var that = this;
                DropDownList.fn.init.call(that, element, options);
                if (kendo.support.mobileOS.ios) {
                    this._initSelectOverlay();
                    this.bind('dataBound', $.proxy(this._initSelectOverlay, this));
                }
                that.text(that.options.title);
                that.bind('open', function () {
                    if (that.options.autoSize) {
                        var list = that.list, listWidth;
                        list.css({
                            whiteSpace: 'nowrap',
                            width: 'auto'
                        });
                        listWidth = list.width();
                        if (listWidth) {
                            listWidth += 20;
                        } else {
                            listWidth = that._listWidth;
                        }
                        list.css('width', listWidth + kendo.support.scrollbar());
                        that._listWidth = listWidth;
                    }
                });
            },
            options: {
                name: 'SelectBox',
                index: -1
            },
            _initSelectOverlay: function () {
                var selectBox = this;
                var value = selectBox.value();
                var view = this.dataSource.view();
                var item;
                var html = '';
                var encode = kendo.htmlEncode;
                for (var i = 0; i < view.length; i++) {
                    item = view[i];
                    html += '<option value=\'' + encode(item.value) + '\'';
                    if (item.value == value) {
                        html += ' selected';
                    }
                    html += '>' + encode(item.text) + '</option>';
                }
                var select = $('<select class=\'k-select-overlay\'>' + html + '</select>');
                var wrapper = $(this.element).closest('.k-widget');
                wrapper.next('.k-select-overlay').remove();
                select.insertAfter(wrapper);
                select.on('change', function () {
                    selectBox.value(this.value);
                    selectBox.trigger('change');
                });
            },
            value: function (value) {
                var that = this, result = DropDownList.fn.value.call(that, value);
                if (value === undefined) {
                    return result;
                }
                if (!DropDownList.fn.value.call(that)) {
                    that.text(that.options.title);
                }
            },
            decorate: function (body) {
                var that = this, dataSource = that.dataSource, items = dataSource.data(), i, tag, className, style;
                if (body) {
                    that.list.css('background-color', dom.getEffectiveBackground($(body)));
                }
                for (i = 0; i < items.length; i++) {
                    tag = items[i].tag || 'span';
                    className = items[i].className;
                    style = dom.inlineStyle(body, tag, { className: className });
                    style = style.replace(/"/g, '\'');
                    items[i].style = style + ';display:inline-block';
                }
                dataSource.trigger('change');
            }
        });
        kendo.ui.plugin(SelectBox);
        kendo.ui.editor.SelectBox = SelectBox;
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/indent', ['editor/components'], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo, Class = kendo.Class, extend = $.extend, Editor = kendo.ui.editor, dom = Editor.Dom, EditorUtils = Editor.EditorUtils, registerTool = EditorUtils.registerTool, Command = Editor.Command, Tool = Editor.Tool, ToolTemplate = Editor.ToolTemplate, RangeUtils = Editor.RangeUtils, blockElements = dom.blockElements, BlockFormatFinder = Editor.BlockFormatFinder, BlockFormatter = Editor.BlockFormatter;
        function indent(node, value) {
            var isRtl = $(node).css('direction') == 'rtl', indentDirection = isRtl ? 'Right' : 'Left', property = dom.name(node) != 'td' ? 'margin' + indentDirection : 'padding' + indentDirection;
            if (value === undefined) {
                return node.style[property] || 0;
            } else {
                if (value > 0) {
                    node.style[property] = value + 'px';
                } else {
                    node.style[property] = '';
                    if (!node.style.cssText) {
                        node.removeAttribute('style');
                    }
                }
            }
        }
        var IndentFormatter = Class.extend({
            init: function () {
                this.finder = new BlockFormatFinder([{ tags: dom.blockElements }]);
            },
            apply: function (nodes) {
                var formatNodes = this.finder.findSuitable(nodes), targets = [], i, len, formatNode, parentList, sibling;
                if (formatNodes.length) {
                    for (i = 0, len = formatNodes.length; i < len; i++) {
                        if (dom.is(formatNodes[i], 'li')) {
                            if (!$(formatNodes[i]).index()) {
                                targets.push(formatNodes[i].parentNode);
                            } else if ($.inArray(formatNodes[i].parentNode, targets) < 0) {
                                targets.push(formatNodes[i]);
                            }
                        } else {
                            targets.push(formatNodes[i]);
                        }
                    }
                    while (targets.length) {
                        formatNode = targets.shift();
                        if (dom.is(formatNode, 'li')) {
                            parentList = formatNode.parentNode;
                            sibling = $(formatNode).prev('li');
                            var siblingList = sibling.find('ul,ol').last();
                            var nestedList = $(formatNode).children('ul,ol')[0];
                            if (nestedList && sibling[0]) {
                                if (siblingList[0]) {
                                    siblingList.append(formatNode);
                                    siblingList.append($(nestedList).children());
                                    dom.remove(nestedList);
                                } else {
                                    sibling.append(nestedList);
                                    nestedList.insertBefore(formatNode, nestedList.firstChild);
                                }
                            } else {
                                nestedList = sibling.children('ul,ol')[0];
                                if (!nestedList) {
                                    nestedList = dom.create(formatNode.ownerDocument, dom.name(parentList));
                                    sibling.append(nestedList);
                                }
                                while (formatNode && formatNode.parentNode == parentList) {
                                    nestedList.appendChild(formatNode);
                                    formatNode = targets.shift();
                                }
                            }
                        } else {
                            var marginLeft = parseInt(indent(formatNode), 10) + 30;
                            indent(formatNode, marginLeft);
                            for (var targetIndex = 0; targetIndex < targets.length; targetIndex++) {
                                if ($.contains(formatNode, targets[targetIndex])) {
                                    targets.splice(targetIndex, 1);
                                }
                            }
                        }
                    }
                } else {
                    var formatter = new BlockFormatter([{ tags: ['p'] }], { style: { marginLeft: 30 } });
                    formatter.apply(nodes);
                }
            },
            remove: function (nodes) {
                var formatNodes = this.finder.findSuitable(nodes), targetNode, i, len, list, listParent, siblings, formatNode, marginLeft;
                for (i = 0, len = formatNodes.length; i < len; i++) {
                    formatNode = $(formatNodes[i]);
                    if (formatNode.is('li')) {
                        list = formatNode.parent();
                        listParent = list.parent();
                        if (listParent.is('li,ul,ol') && !indent(list[0])) {
                            if (targetNode && $.contains(targetNode, listParent[0])) {
                                continue;
                            }
                            siblings = formatNode.nextAll('li');
                            if (siblings.length) {
                                $(list[0].cloneNode(false)).appendTo(formatNode).append(siblings);
                            }
                            if (listParent.is('li')) {
                                formatNode.insertAfter(listParent);
                            } else {
                                formatNode.appendTo(listParent);
                            }
                            if (!list.children('li').length) {
                                list.remove();
                            }
                            continue;
                        } else {
                            if (targetNode == list[0]) {
                                continue;
                            }
                            targetNode = list[0];
                        }
                    } else {
                        targetNode = formatNodes[i];
                    }
                    marginLeft = parseInt(indent(targetNode), 10) - 30;
                    indent(targetNode, marginLeft);
                }
            }
        });
        var IndentCommand = Command.extend({
            init: function (options) {
                options.formatter = {
                    toggle: function (range) {
                        new IndentFormatter().apply(RangeUtils.nodes(range));
                    }
                };
                Command.fn.init.call(this, options);
            }
        });
        var OutdentCommand = Command.extend({
            init: function (options) {
                options.formatter = {
                    toggle: function (range) {
                        new IndentFormatter().remove(RangeUtils.nodes(range));
                    }
                };
                Command.fn.init.call(this, options);
            }
        });
        var OutdentTool = Tool.extend({
            init: function (options) {
                Tool.fn.init.call(this, options);
                this.finder = new BlockFormatFinder([{ tags: blockElements }]);
            },
            initialize: function (ui, options) {
                Tool.fn.initialize.call(this, ui, options);
                ui.addClass('k-state-disabled');
            },
            update: function (ui, nodes) {
                var suitable = this.finder.findSuitable(nodes), isOutdentable, listParentsCount, i, len;
                for (i = 0, len = suitable.length; i < len; i++) {
                    isOutdentable = indent(suitable[i]);
                    if (!isOutdentable) {
                        listParentsCount = $(suitable[i]).parents('ul,ol').length;
                        isOutdentable = dom.is(suitable[i], 'li') && (listParentsCount > 1 || indent(suitable[i].parentNode)) || dom.ofType(suitable[i], [
                            'ul',
                            'ol'
                        ]) && listParentsCount > 0;
                    }
                    if (isOutdentable) {
                        ui.removeClass('k-state-disabled');
                        return;
                    }
                }
                ui.addClass('k-state-disabled').removeClass('k-state-hover');
            }
        });
        extend(Editor, {
            IndentFormatter: IndentFormatter,
            IndentCommand: IndentCommand,
            OutdentCommand: OutdentCommand,
            OutdentTool: OutdentTool
        });
        registerTool('indent', new Tool({
            command: IndentCommand,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Indent'
            })
        }));
        registerTool('outdent', new OutdentTool({
            command: OutdentCommand,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Outdent'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/viewhtml', ['editor/indent'], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo, extend = $.extend, Editor = kendo.ui.editor, EditorUtils = Editor.EditorUtils, Command = Editor.Command, Tool = Editor.Tool, ToolTemplate = Editor.ToolTemplate;
        var ViewHtmlCommand = Command.extend({
            init: function (options) {
                var cmd = this;
                cmd.options = options;
                Command.fn.init.call(cmd, options);
                cmd.attributes = null;
                cmd.async = true;
            },
            exec: function () {
                var that = this, editor = that.editor, messages = editor.options.messages, dialog = $(kendo.template(ViewHtmlCommand.template)(messages)).appendTo(document.body), content = ViewHtmlCommand.indent(editor.value()), textarea = '.k-editor-textarea';
                function apply(e) {
                    editor.value(dialog.find(textarea).val());
                    close(e);
                    if (that.change) {
                        that.change();
                    }
                    editor.trigger('change');
                }
                function close(e) {
                    e.preventDefault();
                    dialog.data('kendoWindow').destroy();
                    editor.focus();
                }
                this.createDialog(dialog, {
                    title: messages.viewHtml,
                    close: close,
                    visible: false
                }).find(textarea).val(content).end().find('.k-dialog-update').click(apply).end().find('.k-dialog-close').click(close).end().data('kendoWindow').center().open();
                dialog.find(textarea).focus();
            }
        });
        extend(ViewHtmlCommand, {
            template: '<div class=\'k-editor-dialog k-popup-edit-form k-edit-form-container k-viewhtml-dialog\'>' + '<textarea class=\'k-editor-textarea k-input\'></textarea>' + '<div class=\'k-edit-buttons k-state-default\'>' + '<button class=\'k-dialog-update k-button k-primary\'>#: dialogUpdate #</button>' + '<button class=\'k-dialog-close k-button\'>#: dialogCancel #</button>' + '</div>' + '</div>',
            indent: function (content) {
                return content.replace(/<\/(p|li|ul|ol|h[1-6]|table|tr|td|th)>/gi, '</$1>\n').replace(/<(ul|ol)([^>]*)><li/gi, '<$1$2>\n<li').replace(/<br \/>/gi, '<br />\n').replace(/\n$/, '');
            }
        });
        kendo.ui.editor.ViewHtmlCommand = ViewHtmlCommand;
        Editor.EditorUtils.registerTool('viewHtml', new Tool({
            command: ViewHtmlCommand,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'View HTML'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/formatting', ['editor/viewhtml'], f);
}(function () {
    (function ($) {
        var kendo = window.kendo, Editor = kendo.ui.editor, Tool = Editor.Tool, ToolTemplate = Editor.ToolTemplate, DelayedExecutionTool = Editor.DelayedExecutionTool, Command = Editor.Command, dom = Editor.Dom, EditorUtils = Editor.EditorUtils, RangeUtils = Editor.RangeUtils, registerTool = EditorUtils.registerTool;
        var FormattingTool = DelayedExecutionTool.extend({
            init: function (options) {
                var that = this;
                Tool.fn.init.call(that, kendo.deepExtend({}, that.options, options));
                that.type = 'kendoSelectBox';
                that.finder = {
                    getFormat: function () {
                        return '';
                    }
                };
            },
            options: {
                items: [
                    {
                        text: 'Paragraph',
                        value: 'p'
                    },
                    {
                        text: 'Quotation',
                        value: 'blockquote'
                    },
                    {
                        text: 'Heading 1',
                        value: 'h1'
                    },
                    {
                        text: 'Heading 2',
                        value: 'h2'
                    },
                    {
                        text: 'Heading 3',
                        value: 'h3'
                    },
                    {
                        text: 'Heading 4',
                        value: 'h4'
                    },
                    {
                        text: 'Heading 5',
                        value: 'h5'
                    },
                    {
                        text: 'Heading 6',
                        value: 'h6'
                    }
                ],
                width: 110
            },
            toFormattingItem: function (item) {
                var value = item.value;
                if (!value) {
                    return item;
                }
                if (item.tag || item.className) {
                    return item;
                }
                var dot = value.indexOf('.');
                if (dot === 0) {
                    item.className = value.substring(1);
                } else if (dot == -1) {
                    item.tag = value;
                } else {
                    item.tag = value.substring(0, dot);
                    item.className = value.substring(dot + 1);
                }
                return item;
            },
            command: function (args) {
                var item = args.value;
                item = this.toFormattingItem(item);
                return new Editor.FormatCommand({
                    range: args.range,
                    formatter: function () {
                        var formatter, tags = (item.tag || item.context || 'span').split(','), format = [{
                                    tags: tags,
                                    attr: { className: item.className || '' }
                                }];
                        if ($.inArray(tags[0], dom.inlineElements) >= 0) {
                            formatter = new Editor.GreedyInlineFormatter(format);
                        } else {
                            formatter = new Editor.GreedyBlockFormatter(format);
                        }
                        return formatter;
                    }
                });
            },
            initialize: function (ui, initOptions) {
                var editor = initOptions.editor;
                var options = this.options;
                var toolName = options.name;
                var that = this;
                ui.width(options.width);
                ui.kendoSelectBox({
                    dataTextField: 'text',
                    dataValueField: 'value',
                    dataSource: options.items || editor.options[toolName],
                    title: editor.options.messages[toolName],
                    autoSize: true,
                    change: function () {
                        var dataItem = this.dataItem();
                        if (dataItem) {
                            Tool.exec(editor, toolName, dataItem.toJSON());
                        }
                    },
                    dataBound: function () {
                        var i, items = this.dataSource.data();
                        for (i = 0; i < items.length; i++) {
                            items[i] = that.toFormattingItem(items[i]);
                        }
                    },
                    highlightFirst: false,
                    template: kendo.template('<span unselectable="on" style="display:block;#=(data.style||"")#">#:data.text#</span>')
                });
                ui.addClass('k-decorated').closest('.k-widget').removeClass('k-' + toolName).find('*').addBack().attr('unselectable', 'on');
            },
            getFormattingValue: function (items, nodes) {
                for (var i = 0; i < items.length; i++) {
                    var item = items[i];
                    var tag = item.tag || item.context || '';
                    var className = item.className ? '.' + item.className : '';
                    var selector = tag + className;
                    var element = $(nodes[0]).closest(selector)[0];
                    if (!element) {
                        continue;
                    }
                    if (nodes.length == 1) {
                        return item.value;
                    }
                    for (var n = 1; n < nodes.length; n++) {
                        if (!$(nodes[n]).closest(selector)[0]) {
                            break;
                        } else if (n == nodes.length - 1) {
                            return item.value;
                        }
                    }
                }
                return '';
            },
            update: function (ui, nodes) {
                var selectBox = $(ui).data(this.type);
                if (!selectBox) {
                    return;
                }
                var dataSource = selectBox.dataSource, items = dataSource.data(), i, context, ancestor = dom.commonAncestor.apply(null, nodes);
                if (ancestor != dom.closestEditable(ancestor) && this._ancestor == ancestor) {
                    return;
                } else {
                    this._ancestor = ancestor;
                }
                for (i = 0; i < items.length; i++) {
                    context = items[i].context;
                    items[i].visible = !context || !!$(ancestor).closest(context).length;
                }
                dataSource.filter([{
                        field: 'visible',
                        operator: 'eq',
                        value: true
                    }]);
                DelayedExecutionTool.fn.update.call(this, ui, nodes);
                selectBox.value(this.getFormattingValue(dataSource.view(), nodes));
                selectBox.wrapper.toggleClass('k-state-disabled', !dataSource.view().length);
            },
            destroy: function () {
                this._ancestor = null;
            }
        });
        var CleanFormatCommand = Command.extend({
            exec: function () {
                var range = this.lockRange(true);
                this.tagsToClean = this.options.remove || 'strong,em,span,sup,sub,del,b,i,u,font'.split(',');
                RangeUtils.wrapSelectedElements(range);
                var nodes = RangeUtils.mapAll(range, function (node) {
                    return node;
                });
                for (var c = nodes.length - 1; c >= 0; c--) {
                    this.clean(nodes[c]);
                }
                this.releaseRange(range);
            },
            clean: function (node) {
                if (!node || dom.isMarker(node)) {
                    return;
                }
                var name = dom.name(node);
                if (name == 'ul' || name == 'ol') {
                    var listFormatter = new Editor.ListFormatter(name);
                    var prev = node.previousSibling;
                    var next = node.nextSibling;
                    listFormatter.unwrap(node);
                    for (; prev && prev != next; prev = prev.nextSibling) {
                        this.clean(prev);
                    }
                } else if (name == 'blockquote') {
                    dom.changeTag(node, 'p');
                } else if (node.nodeType == 1 && !dom.insignificant(node)) {
                    for (var i = node.childNodes.length - 1; i >= 0; i--) {
                        this.clean(node.childNodes[i]);
                    }
                    node.removeAttribute('style');
                    node.removeAttribute('class');
                } else {
                    unwrapListItem(node);
                }
                if ($.inArray(name, this.tagsToClean) > -1) {
                    dom.unwrap(node);
                }
            }
        });
        function unwrapListItem(node) {
            var li = dom.closestEditableOfType(node, ['li']);
            if (li) {
                var listFormatter = new Editor.ListFormatter(dom.name(li.parentNode));
                var range = kendo.ui.editor.W3CRange.fromNode(node);
                range.selectNode(li);
                listFormatter.toggle(range);
            }
        }
        $.extend(Editor, {
            FormattingTool: FormattingTool,
            CleanFormatCommand: CleanFormatCommand
        });
        registerTool('formatting', new FormattingTool({
            template: new ToolTemplate({
                template: EditorUtils.dropDownListTemplate,
                title: 'Format'
            })
        }));
        registerTool('cleanFormatting', new Tool({
            command: CleanFormatCommand,
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Clean formatting'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/toolbar', ['editor/formatting'], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo;
        var ui = kendo.ui;
        var editorNS = ui.editor;
        var Widget = ui.Widget;
        var extend = $.extend;
        var proxy = $.proxy;
        var keys = kendo.keys;
        var NS = '.kendoEditor';
        var EditorUtils = kendo.ui.editor.EditorUtils;
        var ToolTemplate = kendo.ui.editor.ToolTemplate;
        var Tool = kendo.ui.editor.Tool;
        var OVERFLOWANCHOR = 'overflowAnchor';
        var focusable = '.k-tool-group:visible a.k-tool:not(.k-state-disabled),' + '.k-tool.k-overflow-anchor,' + '.k-tool-group:visible .k-widget.k-colorpicker,' + '.k-tool-group:visible .k-selectbox,' + '.k-tool-group:visible .k-dropdown,' + '.k-tool-group:visible .k-combobox .k-input';
        var OverflowAnchorTool = Tool.extend({
            initialize: function (ui, options) {
                ui.attr({ unselectable: 'on' });
                var toolbar = options.editor.toolbar;
                ui.on('click', $.proxy(function () {
                    this.overflowPopup.toggle();
                }, toolbar));
            },
            options: { name: OVERFLOWANCHOR },
            command: $.noop,
            update: $.noop,
            destroy: $.noop
        });
        EditorUtils.registerTool(OVERFLOWANCHOR, new OverflowAnchorTool({
            key: '',
            ctrl: true,
            template: new ToolTemplate({ template: EditorUtils.overflowAnchorTemplate })
        }));
        var Toolbar = Widget.extend({
            init: function (element, options) {
                var that = this;
                options = extend({}, options, { name: 'EditorToolbar' });
                Widget.fn.init.call(that, element, options);
                if (options.popup) {
                    that._initPopup();
                }
                if (options.resizable && options.resizable.toolbar) {
                    that._resizeHandler = kendo.onResize(function () {
                        that.resize();
                    });
                    that.element.addClass('k-toolbar-resizable');
                }
            },
            events: ['execute'],
            groups: {
                basic: [
                    'bold',
                    'italic',
                    'underline',
                    'strikethrough'
                ],
                scripts: [
                    'subscript',
                    'superscript'
                ],
                alignment: [
                    'justifyLeft',
                    'justifyCenter',
                    'justifyRight',
                    'justifyFull'
                ],
                links: [
                    'insertImage',
                    'insertFile',
                    'createLink',
                    'unlink'
                ],
                lists: [
                    'insertUnorderedList',
                    'insertOrderedList',
                    'indent',
                    'outdent'
                ],
                tables: [
                    'createTable',
                    'addColumnLeft',
                    'addColumnRight',
                    'addRowAbove',
                    'addRowBelow',
                    'deleteRow',
                    'deleteColumn'
                ],
                advanced: [
                    'viewHtml',
                    'cleanFormatting',
                    'print',
                    'pdf'
                ],
                fonts: [
                    'fontName',
                    'fontSize'
                ],
                colors: [
                    'foreColor',
                    'backColor'
                ]
            },
            overflowFlaseTools: [
                'formatting',
                'fontName',
                'fontSize',
                'foreColor',
                'backColor',
                'insertHtml'
            ],
            _initPopup: function () {
                this.window = $(this.element).wrap('<div class=\'editorToolbarWindow k-header\' />').parent().prepend('<button class=\'k-button k-button-bare k-editortoolbar-dragHandle\'><span class=\'k-icon k-i-move\' /></button>').kendoWindow({
                    title: false,
                    resizable: false,
                    draggable: { dragHandle: '.k-editortoolbar-dragHandle' },
                    animation: {
                        open: { effects: 'fade:in' },
                        close: { effects: 'fade:out' }
                    },
                    minHeight: 42,
                    visible: false,
                    autoFocus: false,
                    actions: [],
                    dragend: function () {
                        this._moved = true;
                    }
                }).on('mousedown', function (e) {
                    if (!$(e.target).is('.k-icon')) {
                        e.preventDefault();
                    }
                }).data('kendoWindow');
            },
            _toggleOverflowStyles: function (element, show) {
                element.find('li').toggleClass('k-item k-state-default', show).find('.k-tool:not(.k-state-disabled),.k-overflow-button').toggleClass('k-overflow-button k-button', show);
            },
            _initOverflowPopup: function (ui) {
                var that = this;
                var popupTemplate = '<ul class=\'k-editor-overflow-popup k-overflow-container k-list-container\'></ul>';
                that.overflowPopup = $(popupTemplate).appendTo('body').kendoPopup({
                    anchor: ui,
                    origin: 'bottom right',
                    position: 'top right',
                    copyAnchorStyles: false,
                    open: function (e) {
                        if (this.element.is(':empty')) {
                            e.preventDefault();
                        }
                        that._toggleOverflowStyles(this.element, true);
                    },
                    activate: proxy(that.focusOverflowPopup, that)
                }).data('kendoPopup');
            },
            items: function () {
                var isResizable = this.options.resizable && this.options.resizable.toolbar, popup, result;
                result = this.element.children().find('> *, select');
                if (isResizable) {
                    popup = this.overflowPopup;
                    result = result.add(popup.element.children().find('> *'));
                }
                return result;
            },
            focused: function () {
                return this.element.find('.k-state-focused').length > 0;
            },
            toolById: function (name) {
                var id, tools = this.tools;
                for (id in tools) {
                    if (id.toLowerCase() == name) {
                        return tools[id];
                    }
                }
            },
            toolGroupFor: function (toolName) {
                var i, groups = this.groups;
                if (this.isCustomTool(toolName)) {
                    return 'custom';
                }
                for (i in groups) {
                    if ($.inArray(toolName, groups[i]) >= 0) {
                        return i;
                    }
                }
            },
            bindTo: function (editor) {
                var that = this, window = that.window;
                if (that._editor) {
                    that._editor.unbind('select', proxy(that.resize, that));
                }
                that._editor = editor;
                if (that.options.resizable && that.options.resizable.toolbar) {
                    editor.options.tools.push(OVERFLOWANCHOR);
                }
                that.tools = that.expandTools(editor.options.tools);
                that.render();
                that.element.find('.k-combobox .k-input').keydown(function (e) {
                    var combobox = $(this).closest('.k-combobox').data('kendoComboBox'), key = e.keyCode;
                    if (key == keys.RIGHT || key == keys.LEFT) {
                        combobox.close();
                    } else if (key == keys.DOWN) {
                        if (!combobox.dropDown.isOpened()) {
                            e.stopImmediatePropagation();
                            combobox.open();
                        }
                    }
                });
                that._attachEvents();
                that.items().each(function initializeTool() {
                    var toolName = that._toolName(this), tool = toolName !== 'more' ? that.tools[toolName] : that.tools.overflowAnchor, options = tool && tool.options, messages = editor.options.messages, description = options && options.tooltip || messages[toolName], ui = $(this);
                    if (!tool || !tool.initialize) {
                        return;
                    }
                    if (toolName == 'fontSize' || toolName == 'fontName') {
                        var inheritText = messages[toolName + 'Inherit'];
                        ui.find('input').val(inheritText).end().find('span.k-input').text(inheritText).end();
                    }
                    tool.initialize(ui, {
                        title: that._appendShortcutSequence(description, tool),
                        editor: that._editor
                    });
                    ui.closest('.k-widget', that.element).addClass('k-editor-widget');
                    ui.closest('.k-colorpicker', that.element).next('.k-colorpicker').addClass('k-editor-widget');
                });
                editor.bind('select', proxy(that.resize, that));
                that.update();
                if (window) {
                    window.wrapper.css({
                        top: '',
                        left: '',
                        width: ''
                    });
                }
            },
            show: function () {
                var that = this, window = that.window, editorOptions = that.options.editor, wrapper, editorElement, editorOffset;
                if (window) {
                    wrapper = window.wrapper;
                    editorElement = editorOptions.element;
                    if (!wrapper.is(':visible') || !that.window.options.visible) {
                        if (!wrapper[0].style.width) {
                            wrapper.width(editorElement.outerWidth() - parseInt(wrapper.css('border-left-width'), 10) - parseInt(wrapper.css('border-right-width'), 10));
                        }
                        if (!window._moved) {
                            editorOffset = editorElement.offset();
                            wrapper.css({
                                top: Math.max(0, parseInt(editorOffset.top, 10) - wrapper.outerHeight() - parseInt(that.window.element.css('padding-bottom'), 10)),
                                left: Math.max(0, parseInt(editorOffset.left, 10))
                            });
                        }
                        window.open();
                    }
                }
            },
            hide: function () {
                if (this.window) {
                    this.window.close();
                }
            },
            focus: function () {
                var TABINDEX = 'tabIndex';
                var element = this.element;
                var tabIndex = this._editor.element.attr(TABINDEX);
                element.attr(TABINDEX, tabIndex || 0).focus().find(focusable).first().focus();
                if (!tabIndex && tabIndex !== 0) {
                    element.removeAttr(TABINDEX);
                }
            },
            focusOverflowPopup: function () {
                var TABINDEX = 'tabIndex';
                var element = this.overflowPopup.element;
                var tabIndex = this._editor.element.attr(TABINDEX);
                element.closest('.k-animation-container').addClass('k-overflow-wrapper');
                element.attr(TABINDEX, tabIndex || 0).find(focusable).first().focus();
                if (!tabIndex && tabIndex !== 0) {
                    element.removeAttr(TABINDEX);
                }
            },
            _appendShortcutSequence: function (localizedText, tool) {
                if (!tool.key) {
                    return localizedText;
                }
                var res = localizedText + ' (';
                if (tool.ctrl) {
                    res += 'Ctrl + ';
                }
                if (tool.shift) {
                    res += 'Shift + ';
                }
                if (tool.alt) {
                    res += 'Alt + ';
                }
                res += tool.key + ')';
                return res;
            },
            _nativeTools: [
                'insertLineBreak',
                'insertParagraph',
                'redo',
                'undo',
                'autoLink'
            ],
            tools: {},
            isCustomTool: function (toolName) {
                return !(toolName in kendo.ui.Editor.defaultTools);
            },
            expandTools: function (tools) {
                var currentTool, i, nativeTools = this._nativeTools, options, defaultTools = kendo.deepExtend({}, kendo.ui.Editor.defaultTools), result = {}, name;
                for (i = 0; i < tools.length; i++) {
                    currentTool = tools[i];
                    name = currentTool.name;
                    if ($.isPlainObject(currentTool)) {
                        if (name && defaultTools[name]) {
                            result[name] = extend({}, defaultTools[name]);
                            extend(result[name].options, currentTool);
                        } else {
                            options = extend({
                                cssClass: 'k-i-custom',
                                type: 'button',
                                title: ''
                            }, currentTool);
                            if (!options.name) {
                                options.name = 'custom';
                            }
                            options.cssClass = 'k-' + (options.name == 'custom' ? 'i-custom' : options.name);
                            if (!options.template && options.type == 'button') {
                                options.template = editorNS.EditorUtils.buttonTemplate;
                                options.title = options.title || options.tooltip;
                            }
                            result[name] = { options: options };
                        }
                    } else if (defaultTools[currentTool]) {
                        result[currentTool] = defaultTools[currentTool];
                    }
                }
                for (i = 0; i < nativeTools.length; i++) {
                    if (!result[nativeTools[i]]) {
                        result[nativeTools[i]] = defaultTools[nativeTools[i]];
                    }
                }
                return result;
            },
            render: function () {
                var that = this, tools = that.tools, options, template, toolElement, toolName, editorElement = that._editor.element, element = that.element.empty(), groupName, newGroupName, toolConfig = that._editor.options.tools, browser = kendo.support.browser, group, i, groupPosition = 0, resizable = that.options.resizable && that.options.resizable.toolbar, overflowFlaseTools = this.overflowFlaseTools;
                function stringify(template) {
                    var result;
                    if (template.getHtml) {
                        result = template.getHtml();
                    } else {
                        if (!$.isFunction(template)) {
                            template = kendo.template(template);
                        }
                        result = template(options);
                    }
                    return $.trim(result);
                }
                function endGroup() {
                    if (group.children().length) {
                        if (resizable) {
                            group.data('position', groupPosition);
                            groupPosition++;
                        }
                        group.appendTo(element);
                    }
                }
                function startGroup(toolName) {
                    if (toolName !== OVERFLOWANCHOR) {
                        group = $('<li class=\'k-tool-group\' role=\'presentation\' />');
                        group.data('overflow', $.inArray(toolName, overflowFlaseTools) === -1 ? true : false);
                    } else {
                        group = $('<li class=\'k-overflow-tools\' />');
                    }
                }
                element.empty();
                if (toolConfig.length) {
                    toolName = toolConfig[0].name || toolConfig[0];
                }
                startGroup(toolName, overflowFlaseTools);
                for (i = 0; i < toolConfig.length; i++) {
                    toolName = toolConfig[i].name || toolConfig[i];
                    options = tools[toolName] && tools[toolName].options;
                    if (!options && $.isPlainObject(toolName)) {
                        options = toolName;
                    }
                    template = options && options.template;
                    if (toolName == 'break') {
                        endGroup();
                        $('<li class=\'k-row-break\' />').appendTo(that.element);
                        startGroup(toolName, overflowFlaseTools);
                    }
                    if (!template) {
                        continue;
                    }
                    newGroupName = that.toolGroupFor(toolName);
                    if (groupName != newGroupName || toolName == OVERFLOWANCHOR) {
                        endGroup();
                        startGroup(toolName, overflowFlaseTools);
                        groupName = newGroupName;
                    }
                    template = stringify(template);
                    toolElement = $(template).appendTo(group);
                    if (newGroupName == 'custom') {
                        endGroup();
                        startGroup(toolName, overflowFlaseTools);
                    }
                    if (options.exec && toolElement.hasClass('k-tool')) {
                        toolElement.click(proxy(options.exec, editorElement[0]));
                    }
                }
                endGroup();
                $(that.element).children(':has(> .k-tool)').addClass('k-button-group');
                if (that.options.popup && browser.msie && browser.version < 9) {
                    that.window.wrapper.find('*').attr('unselectable', 'on');
                }
                that.updateGroups();
                if (resizable) {
                    that._initOverflowPopup(that.element.find('.k-overflow-anchor'));
                }
                that.angular('compile', function () {
                    return { elements: that.element };
                });
            },
            updateGroups: function () {
                $(this.element).children().each(function () {
                    $(this).children().filter(function () {
                        return !$(this).hasClass('k-state-disabled');
                    }).removeClass('k-group-end').first().addClass('k-group-start').end().last().addClass('k-group-end').end();
                });
            },
            decorateFrom: function (body) {
                this.items().filter('.k-decorated').each(function () {
                    var selectBox = $(this).data('kendoSelectBox');
                    if (selectBox) {
                        selectBox.decorate(body);
                    }
                });
            },
            destroy: function () {
                Widget.fn.destroy.call(this);
                var id, tools = this.tools;
                for (id in tools) {
                    if (tools[id].destroy) {
                        tools[id].destroy();
                    }
                }
                if (this.window) {
                    this.window.destroy();
                }
                if (this._resizeHandler) {
                    kendo.unbindResize(this._resizeHandler);
                }
                if (this.overflowPopup) {
                    this.overflowPopup.destroy();
                }
            },
            _attachEvents: function () {
                var that = this, buttons = '[role=button].k-tool', enabledButtons = buttons + ':not(.k-state-disabled)', disabledButtons = buttons + '.k-state-disabled', popupElement = that.overflowPopup ? that.overflowPopup.element : $([]);
                that.element.add(popupElement).off(NS).on('mouseenter' + NS, enabledButtons, function () {
                    $(this).addClass('k-state-hover');
                }).on('mouseleave' + NS, enabledButtons, function () {
                    $(this).removeClass('k-state-hover');
                }).on('mousedown' + NS, buttons, function (e) {
                    e.preventDefault();
                }).on('keydown' + NS, focusable, function (e) {
                    var current = this;
                    var resizable = that.options.resizable && that.options.resizable.toolbar;
                    var focusElement, currentContainer, keyCode = e.keyCode;
                    function move(direction, container, constrain) {
                        var tools = container.find(focusable);
                        var index = tools.index(current) + direction;
                        if (constrain) {
                            index = Math.max(0, Math.min(tools.length - 1, index));
                        }
                        return tools[index];
                    }
                    if (keyCode == keys.RIGHT || keyCode == keys.LEFT) {
                        if (!$(current).hasClass('.k-dropdown')) {
                            focusElement = move(keyCode == keys.RIGHT ? 1 : -1, that.element, true);
                        }
                    } else if (resizable && (keyCode == keys.UP || keyCode == keys.DOWN)) {
                        focusElement = move(keyCode == keys.DOWN ? 1 : -1, that.overflowPopup.element, true);
                    } else if (keyCode == keys.ESC) {
                        if (that.overflowPopup.visible()) {
                            that.overflowPopup.close();
                        }
                        focusElement = that._editor;
                    } else if (keyCode == keys.TAB && !(e.ctrlKey || e.altKey)) {
                        if (resizable) {
                            currentContainer = $(current.parentElement).hasClass('k-overflow-tool-group') ? that.overflowPopup.element : that.element;
                        } else {
                            currentContainer = that.element;
                        }
                        if (e.shiftKey) {
                            focusElement = move(-1, currentContainer);
                        } else {
                            focusElement = move(1, currentContainer);
                            if (!focusElement) {
                                focusElement = that._editor;
                            }
                        }
                    }
                    if (focusElement) {
                        e.preventDefault();
                        focusElement.focus();
                    }
                }).on('click' + NS, enabledButtons, function (e) {
                    var button = $(this);
                    e.preventDefault();
                    e.stopPropagation();
                    button.removeClass('k-state-hover');
                    if (!button.is('[data-popup]')) {
                        that._editor.exec(that._toolName(this));
                    }
                }).on('click' + NS, disabledButtons, function (e) {
                    e.preventDefault();
                });
            },
            _toolName: function (element) {
                if (!element) {
                    return;
                }
                var className = element.className;
                if (/k-tool\b/i.test(className)) {
                    className = element.firstChild.className;
                }
                var tool = $.grep(className.split(' '), function (x) {
                    return !/^k-(widget|tool|tool-icon|icon|state-hover|header|combobox|dropdown|selectbox|colorpicker)$/i.test(x);
                });
                return tool[0] ? tool[0].substring(tool[0].lastIndexOf('-') + 1) : 'custom';
            },
            refreshTools: function () {
                var that = this, editor = that._editor, range = editor.getRange(), nodes = kendo.ui.editor.RangeUtils.textNodes(range);
                if (!nodes.length) {
                    nodes = [range.startContainer];
                }
                that.items().each(function () {
                    var tool = that.tools[that._toolName(this)];
                    if (tool && tool.update) {
                        tool.update($(this), nodes);
                    }
                });
                this.update();
            },
            update: function () {
                this.updateGroups();
            },
            _resize: function (e) {
                var containerWidth = e.width;
                var resizable = this.options.resizable && this.options.resizable.toolbar;
                var popup = this.overflowPopup;
                this.refreshTools();
                if (!resizable) {
                    return;
                }
                if (popup.visible()) {
                    popup.close(true);
                }
                this._refreshWidths();
                this._shrink(containerWidth);
                this._stretch(containerWidth);
                this._toggleOverflowStyles(this.element, false);
                this._toggleOverflowStyles(this.overflowPopup.element, true);
                this.element.children('li.k-overflow-tools').css('visibility', popup.element.is(':empty') ? 'hidden' : 'visible');
            },
            _refreshWidths: function () {
                this.element.children('li').each(function (idx, element) {
                    var group = $(element);
                    group.data('outerWidth', group.outerWidth(true));
                });
            },
            _shrink: function (width) {
                var group, visibleGroups;
                if (width < this._groupsWidth()) {
                    visibleGroups = this._visibleGroups().filter(':not(.k-overflow-tools)');
                    for (var i = visibleGroups.length - 1; i >= 0; i--) {
                        group = visibleGroups.eq(i);
                        if (width > this._groupsWidth()) {
                            break;
                        } else {
                            this._hideGroup(group);
                        }
                    }
                }
            },
            _stretch: function (width) {
                var group, hiddenGroups;
                if (width > this._groupsWidth()) {
                    hiddenGroups = this._hiddenGroups();
                    for (var i = 0; i < hiddenGroups.length; i++) {
                        group = hiddenGroups.eq(i);
                        if (width < this._groupsWidth() || !this._showGroup(group, width)) {
                            break;
                        }
                    }
                }
            },
            _hiddenGroups: function () {
                var popup = this.overflowPopup;
                var hiddenGroups = this.element.children('li.k-tool-group').filter(':hidden');
                hiddenGroups = hiddenGroups.add(popup.element.children('li'));
                hiddenGroups.sort(function (a, b) {
                    return $(a).data('position') > $(b).data('position') ? 1 : -1;
                });
                return hiddenGroups;
            },
            _visibleGroups: function () {
                return this.element.children('li.k-tool-group, li.k-overflow-tools').filter(':visible');
            },
            _groupsWidth: function () {
                var width = 0;
                this._visibleGroups().each(function () {
                    width += $(this).data('outerWidth');
                });
                return Math.ceil(width);
            },
            _hideGroup: function (group) {
                if (group.data('overflow')) {
                    var popup = this.overflowPopup;
                    group.detach().prependTo(popup.element).addClass('k-overflow-tool-group');
                } else {
                    group.hide();
                }
            },
            _showGroup: function (group, width) {
                var position, previous;
                if (group.length && width > this._groupsWidth() + group.data('outerWidth')) {
                    if (group.hasClass('k-overflow-tool-group')) {
                        position = group.data('position');
                        if (position === 0) {
                            group.detach().prependTo(this.element);
                        } else {
                            previous = this.element.children().filter(function (idx, element) {
                                return $(element).data('position') === position - 1;
                            });
                            group.detach().insertAfter(previous);
                        }
                        group.removeClass('k-overflow-tool-group');
                    } else {
                        group.show();
                    }
                    return true;
                }
                return false;
            }
        });
        $.extend(editorNS, { Toolbar: Toolbar });
    }(window.jQuery || window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('editor/tables', ['editor/toolbar'], f);
}(function () {
    (function ($, undefined) {
        var kendo = window.kendo, extend = $.extend, proxy = $.proxy, Editor = kendo.ui.editor, dom = Editor.Dom, EditorUtils = Editor.EditorUtils, RangeUtils = Editor.RangeUtils, Command = Editor.Command, NS = '.kendoEditor', ACTIVESTATE = 'k-state-active', SELECTEDSTATE = 'k-state-selected', Tool = Editor.Tool, ToolTemplate = Editor.ToolTemplate, InsertHtmlCommand = Editor.InsertHtmlCommand, BlockFormatFinder = Editor.BlockFormatFinder, registerTool = Editor.EditorUtils.registerTool;
        var editableCell = '<td>' + Editor.emptyElementContent + '</td>';
        var tableFormatFinder = new BlockFormatFinder([{ tags: ['table'] }]);
        var TableCommand = InsertHtmlCommand.extend({
            _tableHtml: function (rows, columns) {
                rows = rows || 1;
                columns = columns || 1;
                return '<table class=\'k-table\' data-last>' + new Array(rows + 1).join('<tr>' + new Array(columns + 1).join(editableCell) + '</tr>') + '</table>';
            },
            postProcess: function (editor, range) {
                var insertedTable = $('table[data-last]', editor.document).removeAttr('data-last');
                range.setStart(insertedTable.find('td')[0], 0);
                range.collapse(true);
                editor.selectRange(range);
            },
            exec: function () {
                var options = this.options;
                options.html = this._tableHtml(options.rows, options.columns);
                options.postProcess = this.postProcess;
                InsertHtmlCommand.fn.exec.call(this);
            }
        });
        var PopupTool = Tool.extend({
            initialize: function (ui, options) {
                Tool.fn.initialize.call(this, ui, options);
                var popup = $(this.options.popupTemplate).appendTo('body').kendoPopup({
                    anchor: ui,
                    copyAnchorStyles: false,
                    open: proxy(this._open, this),
                    activate: proxy(this._activate, this),
                    close: proxy(this._close, this)
                }).data('kendoPopup');
                ui.click(proxy(this._toggle, this)).keydown(proxy(this._keydown, this));
                this._editor = options.editor;
                this._popup = popup;
            },
            popup: function () {
                return this._popup;
            },
            _activate: $.noop,
            _open: function () {
                this._popup.options.anchor.addClass(ACTIVESTATE);
            },
            _close: function () {
                this._popup.options.anchor.removeClass(ACTIVESTATE);
            },
            _keydown: function (e) {
                var keys = kendo.keys;
                var key = e.keyCode;
                if (key == keys.DOWN && e.altKey) {
                    this._popup.open();
                } else if (key == keys.ESC) {
                    this._popup.close();
                }
            },
            _toggle: function (e) {
                var button = $(e.target).closest('.k-tool');
                if (!button.hasClass('k-state-disabled')) {
                    this.popup().toggle();
                }
            },
            update: function (ui) {
                var popup = this.popup();
                if (popup.wrapper && popup.wrapper.css('display') == 'block') {
                    popup.close();
                }
                ui.removeClass('k-state-hover');
            },
            destroy: function () {
                this._popup.destroy();
            }
        });
        var InsertTableTool = PopupTool.extend({
            init: function (options) {
                this.cols = 8;
                this.rows = 6;
                PopupTool.fn.init.call(this, $.extend(options, {
                    command: TableCommand,
                    popupTemplate: '<div class=\'k-ct-popup\'>' + new Array(this.cols * this.rows + 1).join('<span class=\'k-ct-cell k-state-disabled\' />') + '<div class=\'k-status\'></div>' + '</div>'
                }));
            },
            _activate: function () {
                var that = this, element = that._popup.element, cells = element.find('.k-ct-cell'), firstCell = cells.eq(0), lastCell = cells.eq(cells.length - 1), start = kendo.getOffset(firstCell), end = kendo.getOffset(lastCell), cols = that.cols, rows = that.rows, cellWidth, cellHeight;
                element.find('*').addBack().attr('unselectable', 'on');
                end.left += lastCell[0].offsetWidth;
                end.top += lastCell[0].offsetHeight;
                cellWidth = (end.left - start.left) / cols;
                cellHeight = (end.top - start.top) / rows;
                function tableFromLocation(e) {
                    var w = $(window);
                    return {
                        row: Math.floor((e.clientY + w.scrollTop() - start.top) / cellHeight) + 1,
                        col: Math.floor((e.clientX + w.scrollLeft() - start.left) / cellWidth) + 1
                    };
                }
                element.on('mousemove' + NS, function (e) {
                    that._setTableSize(tableFromLocation(e));
                }).on('mouseleave' + NS, function () {
                    that._setTableSize();
                }).on('mouseup' + NS, function (e) {
                    that._exec(tableFromLocation(e));
                });
            },
            _valid: function (size) {
                return size && size.row > 0 && size.col > 0 && size.row <= this.rows && size.col <= this.cols;
            },
            _exec: function (size) {
                if (this._valid(size)) {
                    this._editor.exec('createTable', {
                        rows: size.row,
                        columns: size.col
                    });
                    this._popup.close();
                }
            },
            _setTableSize: function (size) {
                var element = this._popup.element;
                var status = element.find('.k-status');
                var cells = element.find('.k-ct-cell');
                var cols = this.cols;
                var messages = this._editor.options.messages;
                if (this._valid(size)) {
                    status.text(kendo.format(messages.createTableHint, size.row, size.col));
                    cells.each(function (i) {
                        $(this).toggleClass(SELECTEDSTATE, i % cols < size.col && i / cols < size.row);
                    });
                } else {
                    status.text(messages.dialogCancel);
                    cells.removeClass(SELECTEDSTATE);
                }
            },
            _keydown: function (e) {
                PopupTool.fn._keydown.call(this, e);
                if (!this._popup.visible()) {
                    return;
                }
                var keys = kendo.keys;
                var key = e.keyCode;
                var cells = this._popup.element.find('.k-ct-cell');
                var focus = Math.max(cells.filter('.k-state-selected').last().index(), 0);
                var selectedRows = Math.floor(focus / this.cols);
                var selectedColumns = focus % this.cols;
                var changed = false;
                if (key == keys.DOWN && !e.altKey) {
                    changed = true;
                    selectedRows++;
                } else if (key == keys.UP) {
                    changed = true;
                    selectedRows--;
                } else if (key == keys.RIGHT) {
                    changed = true;
                    selectedColumns++;
                } else if (key == keys.LEFT) {
                    changed = true;
                    selectedColumns--;
                }
                var tableSize = {
                    row: Math.max(1, Math.min(this.rows, selectedRows + 1)),
                    col: Math.max(1, Math.min(this.cols, selectedColumns + 1))
                };
                if (key == keys.ENTER) {
                    this._exec(tableSize);
                } else {
                    this._setTableSize(tableSize);
                }
                if (changed) {
                    e.preventDefault();
                    e.stopImmediatePropagation();
                }
            },
            _open: function () {
                var messages = this._editor.options.messages;
                PopupTool.fn._open.call(this);
                this.popup().element.find('.k-status').text(messages.dialogCancel).end().find('.k-ct-cell').removeClass(SELECTEDSTATE);
            },
            _close: function () {
                PopupTool.fn._close.call(this);
                this.popup().element.off(NS);
            },
            update: function (ui, nodes) {
                var isFormatted;
                PopupTool.fn.update.call(this, ui);
                isFormatted = tableFormatFinder.isFormatted(nodes);
                ui.toggleClass('k-state-disabled', isFormatted);
            }
        });
        var InsertRowCommand = Command.extend({
            exec: function () {
                var range = this.lockRange(true), td = range.endContainer, cellCount, row, newRow;
                while (dom.name(td) != 'td') {
                    td = td.parentNode;
                }
                row = td.parentNode;
                cellCount = row.children.length;
                newRow = row.cloneNode(true);
                for (var i = 0; i < row.cells.length; i++) {
                    newRow.cells[i].innerHTML = Editor.emptyElementContent;
                }
                if (this.options.position == 'before') {
                    dom.insertBefore(newRow, row);
                } else {
                    dom.insertAfter(newRow, row);
                }
                this.releaseRange(range);
            }
        });
        var InsertColumnCommand = Command.extend({
            exec: function () {
                var range = this.lockRange(true), td = dom.closest(range.endContainer, 'td'), table = dom.closest(td, 'table'), columnIndex, i, rows = table.rows, cell, newCell, position = this.options.position;
                columnIndex = dom.findNodeIndex(td, true);
                for (i = 0; i < rows.length; i++) {
                    cell = rows[i].cells[columnIndex];
                    newCell = cell.cloneNode();
                    newCell.innerHTML = Editor.emptyElementContent;
                    if (position == 'before') {
                        dom.insertBefore(newCell, cell);
                    } else {
                        dom.insertAfter(newCell, cell);
                    }
                }
                this.releaseRange(range);
            }
        });
        var DeleteRowCommand = Command.extend({
            exec: function () {
                var range = this.lockRange();
                var rows = RangeUtils.mapAll(range, function (node) {
                    return $(node).closest('tr')[0];
                });
                var table = dom.closest(rows[0], 'table');
                var focusElement;
                if (table.rows.length <= rows.length) {
                    focusElement = dom.next(table);
                    if (!focusElement || dom.insignificant(focusElement)) {
                        focusElement = dom.prev(table);
                    }
                    dom.remove(table);
                } else {
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        dom.removeTextSiblings(row);
                        focusElement = dom.next(row) || dom.prev(row);
                        focusElement = focusElement.cells[0];
                        dom.remove(row);
                    }
                }
                if (focusElement) {
                    range.setStart(focusElement, 0);
                    range.collapse(true);
                    this.editor.selectRange(range);
                }
            }
        });
        var DeleteColumnCommand = Command.extend({
            exec: function () {
                var range = this.lockRange(), td = dom.closest(range.endContainer, 'td'), table = dom.closest(td, 'table'), rows = table.rows, columnIndex = dom.findNodeIndex(td, true), columnCount = rows[0].cells.length, focusElement, i;
                if (columnCount == 1) {
                    focusElement = dom.next(table);
                    if (!focusElement || dom.insignificant(focusElement)) {
                        focusElement = dom.prev(table);
                    }
                    dom.remove(table);
                } else {
                    dom.removeTextSiblings(td);
                    focusElement = dom.next(td) || dom.prev(td);
                    for (i = 0; i < rows.length; i++) {
                        dom.remove(rows[i].cells[columnIndex]);
                    }
                }
                if (focusElement) {
                    range.setStart(focusElement, 0);
                    range.collapse(true);
                    this.editor.selectRange(range);
                }
            }
        });
        var TableModificationTool = Tool.extend({
            command: function (options) {
                options = extend(options, this.options);
                if (options.action == 'delete') {
                    if (options.type == 'row') {
                        return new DeleteRowCommand(options);
                    } else {
                        return new DeleteColumnCommand(options);
                    }
                } else {
                    if (options.type == 'row') {
                        return new InsertRowCommand(options);
                    } else {
                        return new InsertColumnCommand(options);
                    }
                }
            },
            initialize: function (ui, options) {
                Tool.fn.initialize.call(this, ui, options);
                ui.addClass('k-state-disabled');
            },
            update: function (ui, nodes) {
                var isFormatted = !tableFormatFinder.isFormatted(nodes);
                ui.toggleClass('k-state-disabled', isFormatted);
            }
        });
        extend(kendo.ui.editor, {
            PopupTool: PopupTool,
            TableCommand: TableCommand,
            InsertTableTool: InsertTableTool,
            TableModificationTool: TableModificationTool,
            InsertRowCommand: InsertRowCommand,
            InsertColumnCommand: InsertColumnCommand,
            DeleteRowCommand: DeleteRowCommand,
            DeleteColumnCommand: DeleteColumnCommand
        });
        registerTool('createTable', new InsertTableTool({
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                popup: true,
                title: 'Create table'
            })
        }));
        registerTool('addColumnLeft', new TableModificationTool({
            type: 'column',
            position: 'before',
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Add column on the left'
            })
        }));
        registerTool('addColumnRight', new TableModificationTool({
            type: 'column',
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Add column on the right'
            })
        }));
        registerTool('addRowAbove', new TableModificationTool({
            type: 'row',
            position: 'before',
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Add row above'
            })
        }));
        registerTool('addRowBelow', new TableModificationTool({
            type: 'row',
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Add row below'
            })
        }));
        registerTool('deleteRow', new TableModificationTool({
            type: 'row',
            action: 'delete',
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Delete row'
            })
        }));
        registerTool('deleteColumn', new TableModificationTool({
            type: 'column',
            action: 'delete',
            template: new ToolTemplate({
                template: EditorUtils.buttonTemplate,
                title: 'Delete column'
            })
        }));
    }(window.kendo.jQuery));
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));
(function (f, define) {
    define('kendo.editor', [
        'kendo.combobox',
        'kendo.dropdownlist',
        'kendo.resizable',
        'kendo.window',
        'kendo.colorpicker',
        'kendo.imagebrowser',
        'util/undoredostack',
        'editor/main',
        'editor/dom',
        'editor/serializer',
        'editor/range',
        'editor/system',
        'editor/inlineformat',
        'editor/formatblock',
        'editor/linebreak',
        'editor/lists',
        'editor/link',
        'editor/file',
        'editor/image',
        'editor/components',
        'editor/indent',
        'editor/viewhtml',
        'editor/formatting',
        'editor/toolbar',
        'editor/tables'
    ], f);
}(function () {
    var __meta__ = {
        id: 'editor',
        name: 'Editor',
        category: 'web',
        description: 'Rich text editor component',
        depends: [
            'combobox',
            'dropdownlist',
            'window',
            'colorpicker'
        ],
        features: [
            {
                id: 'editor-imagebrowser',
                name: 'Image Browser',
                description: 'Support for uploading and inserting images',
                depends: ['imagebrowser']
            },
            {
                id: 'editor-resizable',
                name: 'Resize handle',
                description: 'Support for resizing the content area via a resize handle',
                depends: ['resizable']
            },
            {
                id: 'editor-pdf-export',
                name: 'PDF export',
                description: 'Export Editor content as PDF',
                depends: [
                    'pdf',
                    'drawing'
                ]
            }
        ]
    };
}, typeof define == 'function' && define.amd ? define : function (a1, a2, a3) {
    (a3 || a2)();
}));