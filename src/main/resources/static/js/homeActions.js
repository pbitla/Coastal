var baseAppUrl = window.location.origin;
ons.ready(function() {
	$.ajax({
		url : baseAppUrl+"/getAllCards"
	}).then(function(data) {
		var infiniteList = document.getElementById('infinite-list');

		document.getElementById('userName').innerHTML = "Hello "+data[0].cardHolderName;
		
		infiniteList.delegate = {
			createItemContent : function(i) {
				var accountDisplayName = data[i].cardType;
				var accountDisplayNumber = data[i].cardNumber ;
				var checker ='\''+accountDisplayName+'-'+accountDisplayNumber+'\'';
				return ons.createElement('<ons-list-item>'+accountDisplayName+' - '+accountDisplayNumber
						+ '<div class="right"> <ons-button modifier="quiet" style="background: none;" onclick="showModal('+checker+')"> <ons-icon icon="ion-navicon, material:md-menu"></ons-icon> </ons-button> </div> </ons-list-item> ');				
			},
			countItems : function() {
				return data.length; 
			}
		};
	});
	
	document.getElementById('NewCardToggler').addEventListener('change', function() {
		toggleCardReportSegment();
		getCardRelatedReportingSettings();
		
	});
	document.getElementById('cardBlockerSwitch').addEventListener('change', function() {
		lockOrUnlockToggleSubmitEvent();
	});
});

function getCardRelatedReportingSettings() {	
	var accountId = $('#modalTitle').text();
	var data = {};
	data["accountId"] = accountId;
	$.ajax({
			type: "POST",
			url : baseAppUrl+"/CardInfo/",
			data: JSON.stringify(data),
			dataType: "text",
			contentType: 'application/json',
			 success: function (data) {		           
	             console.log("SUCCESS: ", data);
	             document.querySelector('ons-modal').show();
	             var data1 = JSON.parse(data);
	             setReportCardPanelData(data1);
	         },
	         error: function (e) {
	             console.log("ERROR: ", e);
	         }
	});	
}

function submitNewCardRequest (accountId, newCardReasonCode, comments) {
	
   var data = {};
     data["accountId"] = accountId;
     data["newCardReasonCode"] = newCardReasonCode;
     data["comments"] = comments;
	$.ajax({
			type: "POST",
			url : baseAppUrl+"/cardcontrols/reportcardissue",
			data: JSON.stringify(data),
			dataType: "text",
			contentType: 'application/json',
			 success: function (data) {
	             document.querySelector('ons-modal').hide();
	             showToast(data);
	         },
	         error: function (e) {
	             console.log("ERROR: ", e);
	         }
	});
}

function lockOrUnlockToggleSubmit (accountId, freezeCardFlag) {
	
   var data = {};
     data["accountId"] = accountId;
     data["freezeCardFlag"] = freezeCardFlag;
	$.ajax({
			type: "POST",
			url : baseAppUrl+"/Card/OnOff",
			data: JSON.stringify(data),
			dataType: "text",
			contentType: 'application/json',
			 success: function (data) {
				 var msg = "SUCCESS: "+ data;
	             showToast(msg);
	         },
	         error: function (e) {
	        	 Alert("Error Occurred");
	             console.log("ERROR: ", e);
	         }
	});
}

// called when account is selected
function getCardAccountSettings(accountId) {
	 var data = {};
     data["accountId"] = accountId;
	$.ajax({
			type: "POST",
			url : baseAppUrl+"/CardInfo/",
			data: JSON.stringify(data),
			dataType: "text",
			contentType: 'application/json',
			 success: function (data) {		           
	             console.log("SUCCESS: ", data);
	             document.querySelector('ons-modal').show();
	         	setAccountInfo(data);
	         },
	         error: function (e) {
	             console.log("ERROR: ", e);
	         }
	});	
}

function setAccountInfo(data1) {
	var data = JSON.parse(data1);
    var cardLabel = data.cardType+" - "+data.cardNumber;
	 document.getElementById('modalTitle').innerHTML = cardLabel;
	$('#cardBlockerSwitch').prop( "checked", data.cardFreezed);
	setReportCardPanelData(data);
	$('#NewCardToggler').prop( "checked", data.cardOrdered );
	 if(data.cardOrdered) {		 
		 enabeNewCardPanel();
	 } else {
		 resetNewCardPanel();
	 }
}
function setReportCardPanelData(data) {
	if(data.cardOrderedReason !=''){		
		$('#newCardReasonCode').val(data.cardOrderedReason);
	} else {		
		$('#newCardReasonCode').val("select");
	}

	$( "#comment" ).val(data.cardOrderedComment );
}



