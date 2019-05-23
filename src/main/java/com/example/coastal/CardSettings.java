package com.example.coastal;

public class CardSettings {

	private String accountId;
	private boolean freezeCardFlag;
	private String newCardReasonCode;
	private String comments;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public boolean getFreezeCardFlag() {
		return freezeCardFlag;
	}

	public void setFreezeCardFlag(boolean freezeCardFlag) {
		this.freezeCardFlag = freezeCardFlag;
	}

	public String getNewCardReasonCode() {
		return newCardReasonCode;
	}

	public void setNewCardReasonCode(String newCardReasonCode) {
		this.newCardReasonCode = newCardReasonCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
