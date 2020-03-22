/*-------------------------------------------------*\
|          Cross browser tree widget 0.8           |
|--------------------------------------------------|
|           Created by forbes 2005-4-1             |
|           (http://www.51forbes.net)              |
|           Copyright(C) 2005 Forbes Pu            |
|  You are not allowed to copy or modify the codes.|
|  Commercial use requires messages above.         |
|--------------------------------------------------|
|  Tree wedget works in IE5+, Firefox 1.0          |
\*-------------------------------------------------*/


var $tree=[];
function dTree(n,useCheckBox,contextPath) {

	this.index = $tree.length;
	this.id = 0;
	this.all = [];
	this.all[this.id] = this;
	this.children = [];
	this.parent = null;
	this.root = this;
	this.depth = -1;
	this._selected = null;
	this.name = n||("dTree" + this.index);
	this.config = {
		icon		: {
			root		: contextPath +"/dtree/img/root.gif",
			folder		: contextPath +"/dtree/img/folder.gif",
			folderopen	: contextPath+"/dtree/img/folderopen.gif",
			file		: contextPath+"/dtree/img/file.gif",
			line		: contextPath+"/dtree/img/I.gif",
			blank		: contextPath+"/dtree/img/blank.gif",
			join		: contextPath+"/dtree/img/T.gif",
			joinbottom	: contextPath+"/dtree/img/L.gif",
			plus		: contextPath+"/dtree/img/Tplus.gif",
			plusbottom	: contextPath+"/dtree/img/Lplus.gif",
			minus		: contextPath+"/dtree/img/Tminus.gif",
			minusbottom	: contextPath+"/dtree/img/Lminus.gif",
			expand		: contextPath+"/dtree/img/plus.gif",
			collapse	: contextPath+"/dtree/img/minus.gif"
		},
		text			: "New",
		uri				: "javascript:void(0)",
		target			: "_blank",
		useLine			: true,
		useSameLevel	: false,
		useAutoFix		: false,
		useCheckbox		: useCheckBox,
		useAutoCheck	: true,
		useIcon :true
	};
	$tree[this.index] = this;
};
dTree.prototype.add = function (id, parentId, text, uri, target, title, icon, openedIcon) {
	var newItem = new dTreeItem(id, text, uri, target, title, icon, openedIcon);
	var parentItem = this.getTreeItem(parentId);
	if (!parentItem) {
//		window.alert("Treeitem not found: " + parentId);
		return;
	}
	var _count = parentItem.children.length;
	if (_count) parentItem.children[_count-1]._last = false;
	newItem.parent = parentItem;
	newItem.depth = parentItem.depth + 1;
	newItem.root = parentItem.root;
	parentItem.children[_count] = newItem;
	this.all[newItem.id] = newItem;
};
dTree.prototype.dispose = function() {
	var d=new Date();
	for (var i=0; i<this.children.length;i++)
		this.children[i].dispose();
	this._selected = null;
	this.config = null;
	this.children = null;
	this.parent = null;
	this.root = null;
	this.all[this.id] = null;
	$tree[this.index] = null;
};
dTree.prototype.setSelected = function (curId) {
	var sd = this._selected;
	if (sd != null) 
		document.getElementById("a_" + sd.index).className = "label";
	var cur = this.getTreeItem(curId);
	if (cur != null) {
		var a = document.getElementById("a_" + cur.index);
		a.className = "label selected";
		if (this.config.useAutoFix)
			this.setCookie(this.index+"_s", cur.id);
		this._selected = cur;
		try { a.focus() } catch (e){}
	}
};
dTree.prototype.getSelected = function(){
	return this._selected;
};
dTree.prototype.contextmenu = function(e, treeItem){};
dTree.prototype.openAll = function () {
	for (var i=0; i<this.children.length;i++)
		_openAll(this.children[i]);
	
	function _openAll (it) {
		it.open();
		for (var i=0; i<it.children.length; i++)
			_openAll(it.children[i]);
	}
};
dTree.prototype.closeAll = function () {
	for (var i=0; i<this.children.length;i++)
		_closeAll(this.children[i]);
	
	function _closeAll (it) {
		it.close();
		for (var i=0; i<it.children.length; i++)
			_closeAll(it.children[i]);
	}	
};
dTree.prototype.getTreeItem = function (treeId) {
	if (treeId >= 0 && treeId < this.all.length)
		return this.all[treeId];
	return null;
};
dTree.prototype.write = function (o) {
	var html = [];
	var self = this;
	var df;
	this._loadIcons();
	for (var i=0; i<this.children.length; i++) 
		html[i] = this.children[i].toString();
	o.innerHTML = ("<div class=\"dTree\">" + html.join("") + "</div>");
	for (var i=0; i<this.children.length; i++)
		this.children[i].open();

	df = this.getCookie(this.index + "_s");
	if (this.config.useAutoFix&&df != null && !isNaN(df)) this.openTo(parseInt(df,10));

	if (!document.body)return;
	if (document.body.attachEvent) {
		document.body.attachEvent("onkeydown", function(e){ return self.move(e) });
	} else if (document.body.addEventListener) {
		document.body.addEventListener("keydown", function(e){ return self.move(e) }, false);
	}
};
dTree.prototype._loadIcons = function () {
	var icon = this.config.icon;
	for (var i in icon) {
		var src = icon[i];
		var img = new Image();
		img.src = src;
		icon[i] = img.src;
	}
};
dTree.prototype.move = function (e) {
	e = e || window.event;
	var code = e.which || e.keyCode;
	var o = this._selected, cur = null;
	if (o == null) return;

	switch (code) {
		case 37:
			cur = o.getLeft();break;
		case 38:
			cur = o.getPrevious();break;
		case 39:
			cur = o.getRight();break;
		case 40:
			if (o.children.length&&o.opened)
				cur = o.getFirst();
			else 
				cur = o.getNext();
			break;
	};
	if (code == 13) o.toggle();
	if (cur != null) {
		this.setSelected(cur.id);
		cur.scrollIntoView();
		return false;
	}
	return true;
};
dTree.prototype.openTo = function (id) {
	var item = this.getTreeItem(id);
	if (!item){	  
	 	return false;
	}
	var _item = item;
	var queue = [],q=0;
	while (_item.depth>-1) {
		queue[q++] = _item;
		_item = _item.parent;
	};
	while (--q>0) 
		queue[q].open();

	if(item.depth>-1) {
		this.setSelected(item.id);
		item.openUri();
	}
};
dTree.prototype.getCookie = function(name){
	var re = new RegExp("(^|; )("+escape(name)+")\\=([^;]*)(;|$)","i");
	var m = document.cookie.match(re);
	return m?unescape(m[3]):null;
};
dTree.prototype.setCookie = function (name,value,offset) {
	var cookie = escape(name)+"="+escape(value);
	if (offset!=null){
		var d = new Date();
		d.setSeconds(d.getSeconds()+offset);
		cookie+=";expires="+d.toGMTString();
	}
	cookie+=";path=/";
	document.cookie = cookie;
};
function dTreeItem(id, text, uri, target, title, icon, openedIcon) {
	this.index = id;
	this.id = id;
	this.text = text;
	this.uri = uri;
	this.target = target;
	this.title = title;
	this.icon = icon;
	this.openedIcon = openedIcon;
	this.children = [];
	this.parent = null;
	this.root = null;
	this.depth = 0;
	this.opened = false;
	this._checked = false;
	this._last = true;
	this._loaded = false;
};
dTreeItem.prototype.updateStatus = function () {
	var i_icon = document.getElementById("i_" + this.index);
	var o_icon = document.getElementById("o_" + this.index);
	var o_div = document.getElementById("d_" + this.index);
	if (i_icon) i_icon.src = this.getIcon(0);
	if (o_icon) o_icon.src = this.getIcon(1);
	if (o_div) o_div.style.display = this.opened ? "block" : "none";
};
dTreeItem.prototype.toggle = function () {
	if (!this.children.length)return;
	if (this.opened) {
		this.close();
	} else {
		this.open();
	}
};
dTreeItem.prototype.open = function () {
	if (!this.children.length)return;
	this.opened = true;
	this.updateStatus();
	if (!this._loaded) this.load();
	if (this.root.config.useSameLevel) {
		var pC = this.parent.children;
		for (var i=0; i<pC.length;i++)
			if (pC[i].id != this.id)
				pC[i].close()
	}
};
dTreeItem.prototype.close = function () {
	if (!this.children.length)return;
	this.opened = false;
	this.updateStatus();
	if (this.root.config.useAutoFix)
		this.root.setSelected(this.id)
};
dTreeItem.prototype.getIcon = function (b) {
	
	var cfg = this.root.config;
	var icon = cfg.icon;
	var ct = this.children.length;
	switch (b) {
		case 0: 
			return ct?(cfg.useLine ? (this._last ? (this.opened?icon.minusbottom:icon.plusbottom) : (this.opened?icon.minus:icon.plus)) : (this.opened?icon.collapse:icon.expand)):cfg.useLine?(this._last?icon.joinbottom:icon.join):icon.blank;
		case 1: 
			return this.parent.id==this.root.id?icon.root:(ct ? (this.opened ? (this.openedIcon||this.icon||icon.folderopen):(this.icon||icon.folder)) : (this.icon||icon.file));
		case 2: 
			return (cfg.useLine ? (this._last ? icon.blank : icon.line) : icon.blank);
		default:
			return "";
	}
};
dTreeItem.prototype.load = function () {
	var html = [];
	var div = document.getElementById("d_" + this.index);
	for (var i=0; i<this.children.length; i++)
		html[i] = this.children[i].toString();
	if (div) div.innerHTML = html.join("");
	this._loaded = true;
};
dTreeItem.prototype.toString = function () {
	var html = "<div nowrap=\"true\"><nobr>";
	var cfg = this.root.config;
	var id = this.index = this.root.index + "_" + this.id;
	var pfx = "$tree[" + this.root.index + "].all[" + this.id + "]";
	if (this.depth > 0) {
		var cur = this.parent;
		var sIndent = "";
		while (cur.depth>0) {
			sIndent = "<img src=\"" 
					+ cur.getIcon(2)
					+ "\" align=\"absmiddle\" />" + sIndent;
			cur = cur.parent;
		}
		html += sIndent;
		if (this.children.length) {
			html += "<a href=\"javascript:void(0)\" onclick=\"" 
					+ pfx
					+ ".toggle();return(false)\"><img id=\"i_" 
					+ id
					+ "\" border=\"0\" src=\"" 
					+ this.getIcon(0)
					+ "\" align=\"absmiddle\" /></a>";
		} else {
			html += "<img border=\"0\" src=\"" 
					+ this.getIcon(0)
					+ "\" align=\"absmiddle\" />";
		}
	}
	
	if(cfg.useIcon){
		html += "<img id=\"o_" 
			+ id
			+ "\" src=\""
			+ this.getIcon(1)
			+ "\" align=\"absmiddle\" />";
	}
	
	if (cfg.useCheckbox) {
		html += '<input type="checkbox" class="checkbox" id="c_' 
				+ id
				+ '" name="' 
				+ this.root.name 
				+ '" onclick="'
				+ pfx
				+ '.stateChanged(' 
				+ 'this.checked)" value="'
				+ this.id
				+ '" '
				+ (this._checked?'checked="true"':'')
				+ ' hidefocus="true"/>';
	} 
	html += "<a id=\"a_" 
			+ id
			+ "\" onclick=\"$tree[" 
			+ this.root.index 
			+ "].setSelected(" 
			+ this.id
			+ ")\" oncontextmenu=\"return $tree["
			+ this.root.index
			+ "].contextmenu(event,"
			+ pfx
			+ ")\" href=\"" 
			+ (this.uri||cfg.uri) 
			+ "\" target=\"" 
			+ (this.uri?(this.target || cfg.target):"_self") 
			+ "\" title=\""
			+ (this.title||this.text||"")
			+ "\" class=\"label\">"
			+ (this.text || cfg.text).replace(/</g, '&lt;').replace(/>/g, '&gt;')
			+ "</a></nobr><div id=\"d_" 
			+ id
			+ "\" style=\"display:none\"></div></div>";
	return html;	
};
dTreeItem.prototype.stateChanged=function(b){
	if (this.root.config.useCheckbox && this.root.config.useAutoCheck){
		this.checkAllChildren(b);
		this.checkParent();
	}
	this._checked = b;
};
dTreeItem.prototype.openUri = function(){
	var uri = this.uri;
	if (typeof uri == "string") {
		window.open(uri, this.target||this.root.config.target);
	}
};
dTreeItem.prototype.getFirst = function(){
	if (this.children.length>0 && this.opened)
		return this.children[0];
	return this;
};
dTreeItem.prototype.getLast = function(){
	if (this.children.length>0 && this.opened)
		return this.children[this.children.length-1].getLast();
	return this;
};
dTreeItem.prototype.getLeft = function(){
	if (this.children.length && this.opened) {
		this.close();
		return this;
	} else if (this.depth>0){
		return this.parent;
	} else {
		return this;
	}
};
dTreeItem.prototype.getRight = function(){
	if (this.children.length && !this.opened) {
		this.open();
		return this;
	} else if (this.children.length){
		return this.getFirst();
	} else {
		return this;
	}
};
dTreeItem.prototype.getPrevious = function(){
	if (this.depth<=0) return null;
	for (var i=0;i<this.parent.children.length;i++)
		if (this.parent.children[i] == this) break;
	if (i == 0) 
		return this.parent;
	else
		return this.parent.children[i-1].getLast();
};
dTreeItem.prototype.getNext = function(){
	if (this.depth<=0) return null;
	for (var i=0;i<this.parent.children.length;i++)
		if (this.parent.children[i] == this)break;
	if (i == this.parent.children.length-1)
		return this.parent.getNext();
	else
		return this.parent.children[i+1];
};
dTreeItem.prototype.scrollIntoView = function () {
	var a = document.getElementById("a_" + this.index);
	var x = a.offsetLeft, y = a.offsetTop;
	while (a = a.offsetParent) {
		x += a.offsetLeft;
		y += a.offsetTop;
	}
	y = y - (document.body.clientHeight||window.innerHeight||0)/2 - 15;
   	x = x - 15;
	window.scrollTo(x,y);
};
dTreeItem.prototype.setChecked = function(b){
	var el = document.getElementById("c_" + this.index);
	if (el) el.checked = b;
	this._checked = b;
};
dTreeItem.prototype.getChecked = function(){
	var el = document.getElementById("c_" + this.index);
	if (el.checked != this._checked) 
		this._checked = el.checked;
	return this._checked;
};
dTreeItem.prototype.checkAllChildren=function(b){
	for (var i=0;i<this.children.length;i++){
		this.children[i].setChecked(b);
		this.children[i].checkAllChildren(b);
	}
};
dTreeItem.prototype.checkParent = function(){
	if (this.depth==0)return;
	var p = this.parent.children;
	for (var i=0; i<p.length; i++)
		if (!p[i].getChecked())break;
	//	this.parent.setChecked(i==p.length);
  this.parent.setChecked(true);
	this.parent.checkParent();
};
dTreeItem.prototype.dispose = function() {
	for (var i=0; i<this.children.length;i++)
		this.children[i].dispose();
	this.children = null;
	this.parent = null;
	this.root = null;
};
