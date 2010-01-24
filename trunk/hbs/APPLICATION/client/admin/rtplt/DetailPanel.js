Ext.namespace('ExtUx.widget');

ExtUx.widget.DetailPanel = function(config) {
	//alert(Ext.util.JSON.encode(config.layoutpanel))
	// 处理 config 信息
	ColumnPanelHelper.processConfig(config);
	ExtUx.widget.DetailPanel.superclass.constructor.call(this, config);
	// 因为 IE 会出问题，所以需要此方法进行转换，属于 ext 的 bug
	//this.on("afterrender", this.__setSyncSize__);
};

Ext.extend(ExtUx.widget.DetailPanel, Ext.form.FormPanel, {
	initComponent : function(){
		ExtUx.widget.DetailPanel.superclass.initComponent.call(this);
		
	},
	__setSyncSize__: function() {
		setTimeout("Ext.getCmp('" + this.id + "').syncSize()", 0);
		this.un("afterrender", this.__setSyncSize__);
	},
	syncSize: function () {
    var tw = this.getEl().dom.firstChild.offsetWidth;
		delete this.lastSize;
		this.setSize(this.autoWidth ? undefined : tw, this.autoHeight ? undefined : this.getResizeEl().getHeight());
    //this.setSize(this.autoWidth ? undefined : this.getResizeEl().getWidth(), this.autoHeight ? undefined : this.getResizeEl().getHeight());
    return this;
	},
	style         : "margin:5px 0px 0px 5px",
	autoHeight		: true
});
Ext.reg('detailpanel', ExtUx.widget.DetailPanel);
