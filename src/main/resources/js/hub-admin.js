/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *******************************************************************************/
function updateConfig() {
		  AJS.$.ajax({
			    url: AJS.contextPath() + "/rest/hub-integration/1.0/",
			    type: "PUT",
			    contentType: "application/json",
			    data: '{ "hubUrl": "' + AJS.$("#hubServerUrl").val()
			    + '", "timeout": "' + AJS.$("#hubTimeout").val()
			    + '", "username": "' + AJS.$("#hubUsername").val()
			    + '", "password": "' + AJS.$("#hubPassword").val()
			    + '", "hubProxyHost": "' + AJS.$("#proxyHost").val()
			    + '", "hubProxyPort": "' + AJS.$("#proxyPort").val()
			    + '", "hubNoProxyHosts": "' + AJS.$("#noProxyHost").val()
			    + '", "hubProxyUser": "' + AJS.$("#proxyUsername").val()
			    + '", "hubProxyPassword": "' + AJS.$("#proxyPassword").val()
			    + '"}',
			    processData: false
			  });
	}
function populateForm() {
	  AJS.$.ajax({
	    url: AJS.contextPath() + "/rest/hub-integration/1.0/",
	    dataType: "json",
	    success: function(config) {
	      AJS.$("#hubServerUrl").val(config.hubUrl);
	      AJS.$("#hubTimeout").val(config.timeout);
	      AJS.$("#hubUsername").val(config.username);
	      AJS.$("#hubPassword").val(config.password);
	      AJS.$("#proxyHost").val(config.hubProxyHost);
	      AJS.$("#proxyPort").val(config.hubProxyPort);
	      AJS.$("#proxyUsername").val(config.hubProxyUser);
	      AJS.$("#proxyPassword").val(config.hubProxyPassword);
	      AJS.$("#noProxyHost").val(config.hubNoProxyHosts);
	    }
	  });
	}

(function ($) {
	populateForm();
})(AJS.$ || jQuery);

