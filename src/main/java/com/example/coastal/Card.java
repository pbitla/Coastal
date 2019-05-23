package com.example.coastal;

public class Card {

	private String cardId;
	private String cardHolderName;
	private String cardNumber;
	private String cardType;
	private boolean cardFreezed;
	private boolean cardOrdered;
	private String cardOrderedReason;
	private String cardOrderedComment;

	public Card() {

	}

	public Card(String cardId, String cardHolderName, String cardNumber, String cardType, boolean cardFreezed,
			boolean cardOrdered, String cardOrderedReason, String cardOrderedComment) {
		this.cardId = cardId;
		this.cardHolderName = cardHolderName;
		this.cardNumber = cardNumber;
		this.cardType = cardType;
		this.cardFreezed = cardFreezed;
		this.cardOrdered = cardOrdered;
		this.cardOrderedReason = cardOrderedReason;
		this.cardOrderedComment = cardOrderedComment;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public boolean isCardFreezed() {
		return cardFreezed;
	}

	public void setCardFreezed(boolean cardFreezed) {
		this.cardFreezed = cardFreezed;
	}

	public boolean isCardOrdered() {
		return cardOrdered;
	}

	public void setCardOrdered(boolean cardOrdered) {
		this.cardOrdered = cardOrdered;
	}

	public String getCardOrderedReason() {
		return cardOrderedReason;
	}

	public void setCardOrderedReason(String cardOrderedReason) {
		this.cardOrderedReason = cardOrderedReason;
	}

	public String getCardOrderedComment() {
		return cardOrderedComment;
	}

	public void setCardOrderedComment(String cardOrderedComment) {
		this.cardOrderedComment = cardOrderedComment;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

}
