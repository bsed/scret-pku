/**
 * TermFinder.js
 * 
 * 定义TermFinder类，用于在预先指定的术语中查找pattern
 * 
 */

/* TermFinder.js -- Define TermFinder class
 * 查找术语（Term）
 * 术语分为两类：Role和Data，为构造函数传入一个对象数组，每个对象有两个属性: value和type，以此区分类型
 */
// TODO: 重构（创建package）

/**
 * 构造函数
 * @param {Object} arrayOfTerms
 *     每一个数组项包含两个属性（Value和Type），其中Type == role或data
 */
var TermFinder = function(arrayOfTerms){
    this.terms = [];
    for (var i = 0; arrayOfTerms && i < arrayOfTerms.length; ++i) {
        this.terms.push(arrayOfTerms[i]);
    }
};


TermFinder.prototype = {

    toString: function(){
        var str = "[";
        for (var i = 0; i < this.terms.length; ++i) {
            str += " [ " + this.terms[i].value + ", " + this.terms[i].type + " ] ";
        }
        str += "]";
        return str;
    },

    getTerms: function(){
        return this.terms;
    },
    
	//供外界调用的术语查找接口
    find: function(strWord){
        var result = [];
        if (strWord.replace(/\s/g, "") == "") {
			//跳过全部由空格组成的字符串
            return result;
        }

        this._findSimilarTerms(strWord, result);
        return result;
    },

    addTerm: function(oTerm){
        for (var i = 0; i < this.terms.length; ++i) {
            if (oTerm.value == this.terms[i].value) {
                return;
            }
        }
        this.terms.push(oTerm);
    },

    removeTerm: function(strTerm){
        var existed = false;
        var i = 0;
        for (; i < this.terms.length; ++i) {
            if (strTerm == this.terms[i].value) {
                existed = true;
                break;
            }
        }
        if (existed) {
            this.terms.splice(i, 1);
        }
    },

    _findSimilarTerms: function(strTerm, result){
		//忽略大小写，并且按前缀匹配，例如：“书”可匹配“书籍”、“书本”，但不可匹配“专业书”
        for (var i = 0; i < this.terms.length; ++i) {
            if (this.terms[i].value.toLowerCase().indexOf(strTerm.toLowerCase()) == 0) {
                result.push(this.terms[i]);
            }
        }
    }

};


