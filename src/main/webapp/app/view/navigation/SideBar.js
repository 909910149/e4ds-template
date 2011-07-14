Ext.define('E4ds.view.navigation.SideBar', {
	alias: 'widget.sidebar',
	extend: 'Ext.panel.Panel',

	title: 'Navigation',
	collapsible: true,
	animCollapse: true,
	layout: 'fit',
	minWidth: 100,
	maxWidth: 200,

	initComponent: function() {
		this.items = [ {
			xtype: 'treepanel',
	        store: 'Navigation',
	        rootVisible: false,
	        animate: false
		} ];

		this.callParent(arguments);

	}
});