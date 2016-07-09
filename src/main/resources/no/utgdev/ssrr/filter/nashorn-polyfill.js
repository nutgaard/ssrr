var process = {env:{}};
var global = this;
global.onNashorn = true;

var console = {};
console.debug = print;
console.warn = print;
console.log = print;
console.error = print;
