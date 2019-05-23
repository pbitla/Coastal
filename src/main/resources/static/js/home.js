
function showModal(cardType) {
	getCardAccountSettings(cardType);
}

function submitModal() {
	var accountId = document.getElementById('modalTitle').innerHTML;		
	var newCardReasonCode = document.getElementById('newCardReasonCode').value;
	var comments = document.getElementById('comment').value;			
	
	if(newCardReasonCode !== "select") {		
		submitNewCardRequest (accountId, newCardReasonCode, comments);
	} else {
		  ons.notification.alert('Must select Reason !!!.');
	}
}

function lockOrUnlockToggleSubmitEvent() {
	var accountId = document.getElementById('modalTitle').innerHTML;
	var freezeCardFlag = document.getElementById('cardBlockerSwitch').checked ;
	lockOrUnlockToggleSubmit(accountId, freezeCardFlag);
}

var showToast = function(data) {
  ons.notification.toast(data, {
    timeout: 1000
  });
};

var createAlertDialog = function() {
  var dialog = document.getElementById('my-alert-dialog');

  if (dialog) {
    dialog.show();
  } else {
    ons.createElement('alert-dialog-fun', { append: true })
      .then(function(dialog) {
        dialog.show();
      });
  }
};

function closeModal() {
	  document.querySelector('ons-modal').hide();
	  showToast('No changes applied.');
/*	  $('#NewCardToggler').prop( "checked", false );
	  $('#cardBlockerSwitch').prop( "disabled", false );
	  resetCardBlockerOriginalValue();
	  resetNewCardPanel();
	  $('#NewCardToggler').prop( "disabled", false );*/
}

function toggleCardReportSegment() {
	var disabled = $('#newCardOptions').css( "display");
	if(disabled == "block") {
		 resetNewCardPanel();
	} else {
		enabeNewCardPanel();
	}
}
function enabeNewCardPanel() {
	$('#closeNewCardButton').css( "display", "block" );
	$('#openNewCardButton').css( "display", "none" );
	$('#newCardOptions').css( "display", "block" );
	$('#newCardOptionsButtons').css( "display", "block" );
	//$('#NewCardToggler').prop( "disabled", true );
}
function resetNewCardPanel() {
	$('#closeNewCardButton').css( "display", "none" );
	$('#openNewCardButton').css( "display", "block" );
	$('#newCardOptions').css( "display", "none" );
	$('#newCardOptionsButtons').css( "display", "none" );
//	$('#newCardReasonCode').val("select");
}

