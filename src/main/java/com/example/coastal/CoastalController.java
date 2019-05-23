package com.example.coastal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class CoastalController {

	String baseUrl = "https://anypoint.mulesoft.com/mocking/api/v1/links/2107a7ca-f0f9-4894-93f3-a6f18e9c9f63";

	Map<String, Card> inMemoryCardsInfo = new HashMap<>();

	@RequestMapping(value = {"/","/home","/index"}, method = RequestMethod.GET)
	public RedirectView index() {
		return new RedirectView("/home.html");
	}

	@GetMapping("/getAllCards")
	public List<Card> allCardsInfo() {
		final String url = baseUrl + "/cardInfo/012345";

		HttpMethod get = HttpMethod.GET;
		ResponseEntity<String> result = callCoastalService(url, get, null);

		List<Card> cards = new ArrayList<>();
		if (result != null) {
			HttpStatus statusCode = result.getStatusCode();
			int value = statusCode.value();
			if (200 == value) {

				String json = result.getBody().toString();
				JsonParser parser = JsonParserFactory.getJsonParser();
				Map<String, Object> map = parser.parseMap(json);

				String cardHolderName = (String) map.get("cardHolder");
				List<Map<String, String>> list = (ArrayList<Map<String, String>>) map.get("cards");

				saveAllCardsInfoIntoMemory(cards, cardHolderName, list);
				return cards;
			}
		}
		return cards;
	}

	@PostMapping(value = "/Card/OnOff")
	@ResponseBody
	public String cardOnOffSettings(@RequestBody CardSettings cardSettings) {

		Card card = getCardBasedonCardNumber(cardSettings.getAccountId());

		String cardNumber = card.getCardNumber();
		final String url = baseUrl + "/cardcontrols/onoff/" + cardNumber;

		HttpMethod get = HttpMethod.POST;
		ResponseEntity<String> result = callCoastalService(url, get, null);

		if (result != null) {
			HttpStatus statusCode = result.getStatusCode();
			int value = statusCode.value();
			if (200 == value) {
				return saveCardLockOrUnLockSettingsInMemory(cardSettings, card);
			}
		}
		return "FAILED : Card Lock/Unlocked Failed.";
	}

	@PostMapping(value = "/CardInfo/")
	@ResponseBody
	public Card allCardsInfo(@RequestBody CardSettings cardSettings) {

		Card card = getCardBasedonCardNumber(cardSettings.getAccountId());
		return card;
	}

	@PostMapping(value = "/cardcontrols/reportcardissue")
	@ResponseBody
	public String cardsSettings(@RequestBody CardSettings cardSettings) {

		Card card = getCardBasedonCardNumber(cardSettings.getAccountId());
	
		Map<String,String> map = new HashMap<>();
		map.put("cardId", card.getCardNumber());
		map.put("cardStatus", cardSettings.getNewCardReasonCode());
		map.put("comment", cardSettings.getComments());

		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		
		final String url = baseUrl + "/cardcontrols/reportcardissue";
		HttpMethod get = HttpMethod.POST;
		ResponseEntity<String> result = callCoastalService(url, get, jsonObject.toJSONString());

		if (result != null) {
			HttpStatus statusCode = result.getStatusCode();
			int value = statusCode.value();
			if (200 == value) {
				return saveNewCardSettingsForAccountInMemory(cardSettings, card);
			}
		}
		return "FAILED : Something went Wrong.";
	}

	private void saveAllCardsInfoIntoMemory(List<Card> cards, String cardHolderName, List<Map<String, String>> list) {
		for (Map<String, String> ele : list) {

			Card card = new Card();

			String cardId = ele.get("cardId");
			String cardName = ele.get("cardName");
			String maskedCardNumber = ele.get("maskedCardNumber");

			card.setCardId(cardId);
			card.setCardHolderName(cardHolderName);
			card.setCardNumber(maskedCardNumber);
			card.setCardType(cardName);
			card.setCardFreezed(false);
			card.setCardOrderedReason("");
			card.setCardOrdered(false);
			card.setCardOrderedComment("");

			cards.add(card);
			inMemoryCardsInfo.put(maskedCardNumber, card);
		}
	}

	private ResponseEntity<String> callCoastalService(final String url, HttpMethod httpMethod, String requestJson) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("API-Key", "C5F5A63C-E604-47AA-A7CC-B01F95FFBF09");
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);
		ResponseEntity<String> result = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
		return result;
	}

	private String saveNewCardSettingsForAccountInMemory(CardSettings cardSettings, Card card) {
		if (!cardSettings.getNewCardReasonCode().isEmpty()
				&& !cardSettings.getNewCardReasonCode().equalsIgnoreCase("select")) {
			card.setCardOrderedReason(cardSettings.getNewCardReasonCode());
			card.setCardOrderedComment(cardSettings.getComments());
			card.setCardOrdered(true);
			return "OK : Recieved New Card Request.";
		} else {
			return "OK : Something wrong in New Card Request.";
		}
	}

	private String saveCardLockOrUnLockSettingsInMemory(CardSettings cardSettings, Card card) {
		boolean freezeCardFlag = cardSettings.getFreezeCardFlag();
		card.setCardFreezed(freezeCardFlag);
		if (freezeCardFlag) {
			return "OK : Card locked.";
		} else {
			return "OK : Card is Unlocked.";
		}
	}

	private Card getCardBasedonCardNumber(String accountlLabel) {
		String accountID = (accountlLabel.split("-")[1]).trim();
		Card card = inMemoryCardsInfo.get(accountID);
		return card;
	}
}
