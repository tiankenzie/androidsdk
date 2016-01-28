package id.co.veritrans.sdk.models;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * model class hold information required to execute get token api call.
 * <p/>
 * Created by shivam on 10/29/15.
 */

public class CardTokenRequest implements Serializable {

    //card_number=4811111111111114
    // &card_cvv=123
    // &card_exp_month=12
    // &card_exp_year=2020
    // &client_key=VT-client-Lre_JFh5klhfGefF
    // &secure=true
    // &gross_amount=10000
    // &bank=bni
    // &two_click=false

    private String cardNumber;
    private int cardCVV;
    private String cardExpiryMonth;
    private int cardExpiryYear;
    private boolean secure;
    private boolean twoClick;
    private String bank = null;
    private String cardType;
    private String savedTokenId;
    private double grossAmount;
    private boolean isSaved;
    private String clientKey;
    private boolean instalment;
    private int instalmentTerm;
    private ArrayList<String> bins;
    //private String cardHolderName;

    public CardTokenRequest() {
    }

    public CardTokenRequest(String cardNumber, int cardCVV,
                            String cardExpiryMonth, int cardExpiryYear, String clientKey) {

        this.cardNumber = cardNumber;
        this.cardCVV = cardCVV;
        this.cardExpiryMonth = cardExpiryMonth;
        this.cardExpiryYear = cardExpiryYear;
        this.clientKey = clientKey;
    }

    //card_cvv, token_id, two_click, bank, secure, gross_amount
   /* public CardTokenRequest(int cardCVV, String token, boolean isTwoClick, boolean isSecure,
   String bank, double grossAmount) {

        this.cardCVV = cardCVV;
        this.savedTokenId = token;
        this.twoClick = isTwoClick;
        this.secure = isSecure;
        this.bank = bank;
        this.grossAmount = grossAmount;
    }*/

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(int cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    public int getCardExpiryYear() {
        return cardExpiryYear;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    /**
     * if transaction is 3ds then it will return true.
     *
     * @return boolean value.
     */
    public boolean isSecure() {
        return secure;
    }

    /**
     * if 3ds transaction set it true.
     *
     * @param secure
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isTwoClick() {
        return twoClick;
    }

    public void setTwoClick(boolean twoClick) {
        this.twoClick = twoClick;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    /* public String getCardHolderName() {
         return cardHolderName;
     }

     public void setCardHolderName(String cardHolderName) {
         this.cardHolderName = cardHolderName;
     }
 */
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getFormatedCardNumber() {
        //String startString = "";
        String endString = "";

        if (!TextUtils.isEmpty(cardNumber) && cardNumber.length() == 16) {
            //  startString = cardNumber.substring(0,3);
            endString = cardNumber.substring(12, 16);
        }
        return "XXXX-XXXX-XXXX-" + endString;
    }

    public String getFormatedExpiryDate() {
        if (getCardExpiryYear() > 0) {
            return "XX/" + getCardExpiryYear();

        }
        return "XX/XX";
    }

    public String getSavedTokenId() {
        return savedTokenId;
    }

    public void setSavedTokenId(String savedTokenId) {
        this.savedTokenId = savedTokenId;
    }

    public String getString() {
        try {
            Gson gson = new Gson();
            return gson.toJson(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setCardExpiryMonth(String cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public void setCardExpiryYear(int cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    public boolean isInstalment() {
        return instalment;
    }

    public void setInstalment(boolean instalment) {
        this.instalment = instalment;
    }


    public int getInstalmentTerm() {
        return instalmentTerm;
    }

    public void setInstalmentTerm(int instalmentTerm) {
        this.instalmentTerm = instalmentTerm;
    }

    public String getFormattedInstalmentTerm() {
        return instalmentTerm + "";
    }

    public ArrayList<String> getBins() {
        return bins;
    }

    public void setBins(ArrayList<String> bins) {
        this.bins = bins;
    }
}