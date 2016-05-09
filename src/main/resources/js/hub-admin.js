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
var statusMessageFieldId = "aui-hub-message-field";
var statusMessageTitleId = "aui-hub-message-title";
var statusMessageTitleTextId = "aui-hub-message-title-text";
var statusMessageTextId = "aui-hub-message-text";

var errorStatus = "error";
var successStatus = "success";

var spinning = false;

function updateConfig() {
		  AJS.$.ajax({
			    url: AJS.contextPath() + "/rest/hub-integration/1.0/",
			    type: "PUT",
			    dataType: "json",
			    contentType: "application/json",
			    data: '{ "hubUrl": "' + encodeURI(AJS.$("#hubServerUrl").val())
			    + '", "timeout": "' + encodeURI(AJS.$("#hubTimeout").val())
			    + '", "username": "' + encodeURI(AJS.$("#hubUsername").val())
			    + '", "password": "' + encodeURI(AJS.$("#hubPassword").val())
			    + '", "hubProxyHost": "' + encodeURI(AJS.$("#proxyHost").val())
			    + '", "hubProxyPort": "' + encodeURI(AJS.$("#proxyPort").val())
			    + '", "hubNoProxyHosts": "' + encodeURI(AJS.$("#noProxyHost").val())
			    + '", "hubProxyUser": "' + encodeURI(AJS.$("#proxyUsername").val())
			    + '", "hubProxyPassword": "' + encodeURI(AJS.$("#proxyPassword").val())
			    + '"}',
			    processData: false,
			    success: function() {
			    	hideError('hubServerUrlError');
			    	hideError('hubTimeoutError');
			    	hideError('hubUsernameError');
			    	hideError('hubPasswordError');
			    	hideError('proxyHostError');
			    	hideError('proxyPortError');
				    hideError('proxyUsernameError');
				    hideError('proxyPasswordError');
				    hideError('noProxyHostError');
				      
				    showStatusMessage(successStatus, 'Success!', 'Save successful.');
				    stopProgressSpinner();
			    },
			    error: function(response){
			    	var config = JSON.parse(response.responseText);
			    	handleError('hubServerUrlError', config.hubUrlError);
				    handleError('hubTimeoutError', config.timeoutError);
				    handleError('hubUsernameError', config.usernameError);
				    handleError('hubPasswordError', config.passwordError);
				    handleError('proxyHostError', config.hubProxyHostError);
				    handleError('proxyPortError', config.hubProxyPortError);
				    handleError('proxyUsernameError', config.hubProxyUserError);
				    handleError('proxyPasswordError', config.hubProxyPasswordError);
				    handleError('noProxyHostError', config.hubNoProxyHostsError);
				    
				    showStatusMessage(errorStatus, 'ERROR!', 'The configuration is not valid.');
				    stopProgressSpinner();
			    }
			  });
	}
function populateForm() {
	  AJS.$.ajax({
	    url: AJS.contextPath() + "/rest/hub-integration/1.0/",
	    dataType: "json",
	    success: function(config) {
	      updateValue("hubServerUrl", config.hubUrl);
	      updateValue("hubTimeout", config.timeout);
	      updateValue("hubUsername", config.username);
	      updateValue("hubPassword", config.password);
	      updateValue("proxyHost", config.hubProxyHost);
	      updateValue("proxyPort", config.hubProxyPort);
	      updateValue("proxyUsername", config.hubProxyUser);
	      updateValue("proxyPassword", config.hubProxyPassword);
	      updateValue("noProxyHost", config.hubNoProxyHosts);
	      
	      handleError('hubServerUrlError', config.hubUrlError);
	      handleError('hubTimeoutError', config.timeoutError);
	      handleError('hubUsernameError', config.usernameError);
	      handleError('hubPasswordError', config.passwordError);
	      handleError('proxyHostError', config.hubProxyHostError);
	      handleError('proxyPortError', config.hubProxyPortError);
	      handleError('proxyUsernameError', config.hubProxyUserError);
	      handleError('proxyPasswordError', config.hubProxyPasswordError);
	      handleError('noProxyHostError', config.hubNoProxyHostsError);
	    }
	  });
	}

function updateValue(fieldId, configField) {
	if(configField){
		 AJS.$("#" + fieldId).val(decodeURI(configField));
    }
}

function handleError(fieldId, configField) {
	if(configField){
		showError(fieldId, configField);
    } else{
    	hideError(fieldId);
    }
}

function showError(fieldId, configField) {
	  AJS.$("#" + fieldId).text(decodeURI(configField));
  	  removeClassFromField(fieldId, 'hidden');
}

function hideError(fieldId) {
	  AJS.$("#" + fieldId).text('');
  	  addClassToField(fieldId, 'hidden');
}

function showStatusMessage(status, statusTitle, message) {
	resetStatusMessage();
	if(status == errorStatus){
		addClassToField(statusMessageFieldId, 'error');
		addClassToField(statusMessageTitleId, 'icon-error');
	} else if (status == successStatus){
		addClassToField(statusMessageFieldId, 'success');
		addClassToField(statusMessageTitleId, 'icon-success');
	}
	AJS.$("#" + statusMessageTitleTextId).text(statusTitle);
	AJS.$("#" + statusMessageTextId).text(message);
	removeClassFromField(statusMessageFieldId, 'hidden');
}

function resetStatusMessage() {
	removeClassFromField(statusMessageFieldId,'error');
	removeClassFromField(statusMessageFieldId,'success');
	removeClassFromField(statusMessageTitleId,'icon-error');
	removeClassFromField(statusMessageTitleId,'icon-success');
	AJS.$("#" + statusMessageTitleTextId).text('');
	AJS.$("#" + statusMessageTextId).text('');
	addClassToField(statusMessageFieldId, 'hidden');
}

function addClassToField(fieldId, cssClass){
	if(!AJS.$("#" + fieldId).hasClass(cssClass)){
		AJS.$("#" + fieldId).addClass(cssClass);
	}
}

function removeClassFromField(fieldId, cssClass){
	if(AJS.$("#" + fieldId).hasClass(cssClass)){
		AJS.$("#" + fieldId).removeClass(cssClass);
	}
}

function startProgressSpinner(){
	 if (!spinning) {
		 AJS.$('.button-spinner').spin();
		 spinning = true;
	 }
}

function stopProgressSpinner(){
	 if (spinning) {
		 AJS.$('.button-spinner').spinStop();
         spinning = false;
	 }
}

(function ($) {
	populateForm();
})(AJS.$ || jQuery);

