Ext.namespace("scret.util");

scret.util.copyObject = function(dest, source) {
	for (var n in source) {
		if (typeof source[n] == 'object') {
			if (!dest[n])
				dest[n] = {};
			scret.util.copyObject(dest[n], source[n]);
		} else if (!dest.hasOwnProperty(n)) {
			dest[n] = source[n];
		}
	}
};