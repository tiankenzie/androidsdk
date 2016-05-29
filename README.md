# **Veritrans SDK**
### *We are deprecating this SDK in favour of [the latest](https://github.com/veritrans/veritrans-android)*

The newest sdk is a major update on this implementation, it's likely most interfaces have stayed the same, but we've added a lot of features and updates in the [the latest](https://github.com/veritrans/veritrans-android) update.

## 1 Overview
### 1.1 Description 
Veritrans SDK is an android library project which supports to perform transaction using variouse payment methods supported by veritrans payment gateway. 

**Before using this SDK, please familiarize yourself with our [documentation](http://docs.veritrans.co.id/en/welcome/index.html)**

Please keep in mind that VTAndroidLib a different library than the Veritrans SDK.

Veritrans SDK supports following payment methods -
1) Credit/Debit - helps to perform transaction using credit/debit card with 1 and 2 click support.
2) Mandiri ClickPay - 
3) CIMB Clicks -
4) ePay BRI  - 
5) BBM Money - 
6) Indosat Dompetku-
7) Mandiri e-Cash  - 
8) Bank Transfer   - suport payment using Permata Virtual Account. 
9) Mandiri Bill Payment - 
10) Indomaret -
11) Offers - supports offers like BIN, Discount etc.

Instantiate sdk after application finishes launching processes.

Following are  configurable parameters of sdk that can be used while performing transaction -
1) Server Endpoint- url of server to which transaction data will be sent.
2) Transaction details - contains payment information like amount, order Id, payment method etc.


## 2 Payment Flow

### 2.1 Types of Payment Flow

Veritrans SDK has 2 types of payment flows first is using default ui proived by sdk and second is using core flow.

1) **Payment flow  using default UI**

    In this flow sdk provides an UI to take required information from user to execute transaction.  
    To perform transaction using this, follow the steps given below:  
     I) Set transaction request information to sdk.  
     II) Register a **Broadcast Receiver** to handle payment response.  
     III) Call the startPaymentUiFlow().
  
     
**Note** don't call any payment specific method in this flow, Sdk provides an UI to user with all available methods.
    
here in this flow just set transaction request information to sdk and start payment flow using following code-

```
    if (transactionRequest != null && mVeritransSDK != null) {

                    // create transaction request information as shown above.
                    //set transaction information to sdk
                    mVeritransSDK.setTransactionRequest(transactionRequest);

                    // start ui flow
                    mVeritransSDK.startPaymentUiFlow();

                }
        
```

This will start Payment flow if all information is valid.


2) **Payment flow  using core structure**
    
    In this flow we are assuming that you have created ui to take required information from user to execute transaction.  
    To perform transaction using core, follow the steps given below:  
     I) Set transaction request information to sdk.  
     II) Implement  TransactionCallback  to handle payment response.  
     III) Call the implemented method of the desired payment mode.
     
     **Note** don't call  mVeritransSDK.startPaymentUiFlow(); for core.

    The main difference between the core and ui flow is that
    I) Get payment response -  
        In Core Flow - Use TransactionCallback.
        In UI Flow - Use Broadcast Receiver.
        
    II) Payment Selection Method -  
        In Core Flow - Call paymentUsingXXX().
        In UI Flow - Call startPaymentUiFlow() to see all payment methods.
        
        
### 2.2 Deciding which flow to use 
When payment method is already known and have all required information to execute transaction then go for core flow, else use Ui flow and let user choose the payment method.

### 2.3 Making Payments
In case of core flow use following information to do transactions -

1) Credit/Debit - 
    For credit/debit card transaction follow the steps given bellow:
    *1) Add card type information in transaction request object.
    *2) Get token using getToken() of sdk. 
    *3) If secure flow is enabled then we will get redirect_url which will take user to web page for 3d secure validation. 
    *4) After successful validation do charge api call using paymentUsingCard().

#### 2.3.1 Card Type details -
* There are **3 types** of flow for **card payments**.  
**1. _One click_**  
**2. _Two click_**  
**3. _Normal_**  
We will go in detail about these flows ahead.

* For **normal flow** we can do secure and Insecure way.  

        Secure : With 3d secure validation.
        Insecure : Without 3d secure validation.

* **One click** and **two click** use **secure** flow only.  

To enable secure transacion 
```
transactionRequest.setCardPaymentInfo(CARD_CLRDICK_TYPE, isSecure);

where - 
CARD_CLRDICK_TYPE -  type of card use Constants, 
        Constants.CARD_CLICK_TYPE_ONE_CLICK - for one click
        Constants.CARD_CLICK_TYPE_TWO_CLICK - for two click
        Constants.CARD_CLICK_TYPE_NONE  - for normal transaction

isSecure - set it to true for secure transaction.
```


#### 2.3.2 Get token
```
    veritransSDK.getToken(activity, cardTokenRequest, tokenCallBack);  
```

***CardTokenRequest*** class contains card details required for getting token.  

##### TokenCallBack Interface
It contains two methods onSuccess and onFailure -  

    @Override  
    public void onSuccess(TokenDetailsResponse tokenDetailsResponse) {  
        // get called in case of success
        //write your code here to save this token
    }    
    
    @Override  
    public void onFailure(String errorMessage, TokenDetailsResponse tokenDetailsResponse) {  
        // handle error here
    }
    On success of get token api call, it will return token id and other parameters in TokenDetailsResponse. 

If secure flow is available then it will return redirect_url which will take user to web page for 3d secure validation.

After successful validation do charge api call.










## 3 SDK Instalation

To use Veritrans SDK  in your android application perform following steps.

### 3.1 Install Veritrans SDK library

**Step 1 -**  Add the library as a gradle dependency:
       
you can simply declare it as dependency in  **build.gradle** file as follow
```sh
       dependencies {
         compile 'id.co.veritrans:androidsdk:0.9.2@aar' // Add the android sdk as a dependency.
        }

    repositories {
        jcenter()
        maven { url "http://dl.bintray.com/pt-midtrans/maven" } // Add the midtrans repository into the list of repositories
    }

```

**Step 2 -** Add internet permissions to  **_AndroidManifest.xml_**  
  These permissions are required to allow the application to send
  events.
  ```
  <uses-permission
    android:name="android.permission.INTERNET" />

  <uses-permission
    android:name="android.permission.ACCESS_NETWORK_STATE" />
    
    <!--GCM permissions-->
    <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="id.co.veritrans.sdk.example.permission.C2D_MESSAGE"/>

 ```
 
**Note** - To add this library project in eclipse follow the instructions given on following link 
 http://developer.android.com/tools/projects/projects-eclipse.html

 That's it! now you are ready to use Veritrans Sdk in your application.

### 3.2 Initializing the library
Once you've setup your build system or IDE to use the Veritrans sdk library. Veritrans SDK requires a merchant key and server key, to get these keys register to veritrans server at https://my.veritrans.co.id/register . 

Now initialize sdk
```
// sdk initialization process
        VeritransBuilder veritransBuilder = new
                VeritransBuilder(context, VT_CLIENT_KEY);
        veritransBuilder.enableLog(true);   // enable logs for debugging purpose.
    VeritransSDK mVeritransSDK = veritransBuilder.buildSDK();

/*
where -
VeritransBuilder - builder class which helps to create sdk instance. 
Context - application context.
VT_CLIENT_KEY - merchant/client key received from veritrans server.
*/
```

Instance of Veritrans SDK is singleton. In order to perform transactions set appropriate details to it using variouse setter methods, finally invoke payment method to perform transaction. You can perform only one transaction at a time.

You can also access already created sdk instance using following static method -
```
VeritransSDK.getVeritransSDK();
```




## 4 Important Informations

### 4.1 Common request information 
Customer details , item details , billing and shipping  address these are common paramteres which requires in every type of transaction.  

1) Set customer details -

**CustomerDetails** class holds information about customer. To set information about customer in TransactionRequest use following code -

```
CustomerDetails customer = new CustomerDetails(String firstName, String lastName, String email, String phone);
transactionRequest.setCustomerDetails(customer);
```



2) Set Item details 

**ItemDetails** class holds information about item purchased by user. TransactionRequest takes an array list of item details. To set this in TransactionRequest use following code -

```
ItemDetails itemDetails1 = new CustomerDetailsItemDetails(String id, double price, double quantity, String name);
ItemDetails itemDetails2 = new CustomerDetailsItemDetails(String id, double price, double quantity, String name);
.
.
//todo create array list and add above item details in it and then set it to transaction request. 

transactionRequest.setItemDetails(ArrayList<ItemDetails>);
```


3) Set Billing Address -
**BillingAddress** class holds information about billing. TransactionRequest takes an array list of billing details. To set this in TransactionRequest use following code - 

```

BillingAddress billingAddress1 = new BillingAddress(String firstName, String lastName, String address, String city, String postalCode, String phone, String countryCode);

BillingAddress billingAddress2 =new BillingAddress(String firstName, String lastName, String address, String city, String postalCode, String phone, String countryCode);
.
.
//todo create array list and add above billing details in it and then set it to transaction request. 

transactionRequest.setItemDetails(ArrayList<BillingAddress>);

```



4) Set Shipping Address -
**ShippingAddress** class holds information about billing. TransactionRequest takes an array list of shipping details. To set this in TransactionRequest use following code -

```

ShippingAddress shippingAddress1 = new ShippingAddress(String firstName, String lastName, String address, String city, String postalCode, String phone, String countryCode);

ShippingAddress shippingAddress2 =new ShippingAddress(String firstName, String lastName, String address, String city, String postalCode, String phone, String countryCode);
.
.
//todo create array list and add above shipping details in it and then set it to transaction request. 

transactionRequest.setItemDetails(ArrayList<ShippingAddress>);

```

### Setting Transaction Details
Set required transaction information to sdk instance.
TransactionRequest contains following information -
- Order Id - _unique order id to identify this transaction_.
- Amount - _amount to charge_.
- Customer details - _holds an information about customer like name , email and phone_.
- Item details - _ holds an information about purchased item, like item id, price etc_.
- Billing Address - _holds an address information like city , country, zip code etc_.
- Shipping Address - _holds an address information like city , country, zip code etc_.
- Bill information - _contains custom details in key-value that you want to display on bill print. 

All of the above parameters are not nesseccessary to set, It depends on which type of payment method you want to execute. To get more information about required paramter see http://docs.veritrans.co.id/en/api/methods.html#Charge .
Sdk will throw error message in case you missed some information right before starting an payment flow.

Create instance of **TransactionRequest** class. 
```
TransactionRequest transactionRequest =
                new TransactionRequest(activity, order id, amount);

/*
where -
activity - instance of current activity class. 
order id - unique order id for this transaction.
amount - amount to charge.
*/
```

## 4.2 Registering Credit Cards

You can register credit cards to be used at a later time by using the method registerCard below on the VeritransSDK object. 

```
public static void registerCard(final Activity activity, CardTokenRequest cardTokenRequest,
                                final String userId, final TransactionCallback callBack) 

```

Upon initializing the SDK, make sure you include your server address.

```
    VeritransBuilder veritransBuilder = new VeritransBuilder(activity, CLIENT_KEY, SERVER_ADDRESS);
    mVeritransSDK = veritransBuilder.buildSDK();

```

Upon recieving a response from our server, the SDK will send the following GET request to SERVER_ADDRESS/creditcard

```
Header:

    Content-Type: application/json
    Accept: application/json

Body request:

    {
      "masked_card": "xxxxxx-xxxx",
      "saved_token_id": "xxxxxxxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
      "status_code": "xxx",
      "transaction_id": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
      "user_id": "xxxxxxx"
    }

All body response from server will be ignored.
```
The **saved_token_id** may then be used for a getToken() request similar to a **two click** token. After that, you can use the token for charging.

```
    CardTokenRequest cardTokenRequest = new CardTokenRequest();
    cardTokenRequest.setIsSaved(true);
    cardTokenRequest.setTwoClick(true);
    cardTokenRequest.setSavedTokenId(SAVED_TOKEN_ID);
    cardTokenRequest.setCardCVV(123);
    cardTokenRequest.setClientKey(CLIENT_KEY);
```





### 4.3 Implementation of GCM  
 Please visit following link for installtion steps of GCM
 * https://developers.google.com/cloud-messaging/android/client  
 *  Create configuration file and add to your root of your project.

 Add the following dependency to your project-level build.gradle:
 ``` 
 classpath 'com.google.gms:google-services:1.5.0-beta2' 
 ```
 Add the plugin to your app-level build.gradle:
 ```
 apply plugin: 'com.google.gms.google-services'
 ```
  Add following permissions to manifest file  
 ```
 <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
 <uses-permission android:name="android.permission.WAKE_LOCK" />
 <uses-permission android:name="<your app's package name>.permission.C2D_MESSAGE"/>  
 
 ```
 Add following receivers and services for receiving push notification and registeration purpose
 
 ```  
 
 <receiver
            android:name="com.google.android.gms.gcm.GcmReceiver"
            android:exported="true"
            android:permission="com.google.android.c2dm.permission.SEND" >
            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
                <category android:name="<your app's package name>" />
            </intent-filter>
        </receiver>
        <service
            android:name="id.co.veritrans.sdk.example.gcmutils.VeritransGcmListenerService"
            android:exported="false" >
            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
            </intent-filter>
        </service>
        <!-- [END gcm_listener] -->
        <!-- [START instanceId_listener] -->
        <service
            android:name="id.co.veritrans.sdk.example.gcmutils.VeritransInstanceIDListenerService"
            android:exported="false">
            <intent-filter>
                <action android:name="com.google.android.gms.iid.InstanceID"/>
            </intent-filter>
        </service>
        <!-- [END instanceId_listener] -->
        <service
            android:name="id.co.veritrans.sdk.example.gcmutils.RegistrationIntentService"
            android:exported="false">
        </service>
 ```  
 * Now  start registeration service in first activity or application class. Check availability of GoogleApi in device.
 Here is code for checking availability of code
```
private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
```  
For registeration functionality you can refer RegistrationIntentService from sdk example app.
After registeration we get token from google services, we are sending this token to merchant server.

**VeritransGcmListenerService** will catch the push sent by gcm. In push we will receive detaile of payments which are pending.
 
### 4.4 Offers for credit cards  
####  Get offers from merchant server  
 * We have implemented offers for credit and debit card transaction.  
 * The flow of credit and debit card is same with inclusion of offer functionality.  
 * Call function **getOffersList** from  **VeritransSDK** class - pass **activity** and **GetOffersCallback** interface.  
 ``` 
  new GetOffersCallback() {
     @Override
     public void onSuccess(GetOffersResponseModel getOffersResponseModel) {
         //logic to populate offers
     }
    @Override
    public void onFailure(String errorMessage, GetOffersResponseModel getOffersResponseModel) {
         //handle error here   
    }
 }
 ```  
 * Like credit card flow get saved cards using **veritransSDK.getSavedCard()**. Pass activity and savedCardCallback object to function.  
 * There are two types of offers  
 **1. Normal offers**  
 **2. Installments**  
 Here is response format for offers  
 ```  
 {
    "message": "success",
    "offers": {
        "binpromo": [
            {
                "offer name": "Get 10% cashback",
                "bins": [
                    "48111111",
                    "3111",
                    "5",
                    "bni",
                    "mandiri"
                ]
            }
        ],
        "installments": [
             {
                "offer name": "MasterCard promo",
                "duration": [
                    3,
                    6,
                    12
                ],
                "bins": [
                    "48111111",
                    "3111",
                    "5",
                    "bni",
                    "mandiri"
                ]
            }
        ]
    }
 }
 ```  
 * We can validate offers with comparing bin numbers with first characters of credit card.
 * If card number matches then offer valid for given card number.
 * When we are applying the offer for normal flow make function call **getToken** from  **VeritransSdk** class as same as credit card flow.
 * Pass **activity**, **cardTokenRequest** and **tokenCallback** params to function.
 * After successful call do charge api call with bins string array as new param. 
 * Here is code for setting the bins value.  
 ``` 
 cardPaymentDetails.setBinsArray(cardTokenRequest.getBins());  
 ```  


## 5 Payment Types

### 5.1 Charge using Credit/Debit card -
For doing charge api call we have to call following function from VeritransSDK.

     veritransSDK.paymentUsingCard(activity, cardTransfer, new
                TransactionCallback() {

                    @Override
                    public void onSuccess(TransactionResponse transactionResponse) {
                        Toast.makeText(context, "Transaction success:  " +
                                transactionResponse.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String errorMessage, TransactionResponse
                            transactionResponse) {

                        Toast.makeText(context, "Transaction failed: " + errorMessage,
                                Toast.LENGTH_SHORT).show();

                    }
                });

In above code we have to supply ** _CardTransfer_ ** class object, and implemented interface TransactionCallback which have functions onSuccess and onFailure.

    @Override
    public void onSuccess(TransactionResponse cardPaymentResponse) {
        //write code after success
    }  
    @Override
    public void onFailure(String errorMessage, TransactionResponse transactionResponse) {    
        //write code after failure
    }

    
after successful transaction card information will be get saved if user permits. In TransactionResponse we get ***saved_token_id*** which is used in future transaction.






### 5.2 Bank Transfer

###### To perform transaction using bank transfer method follow the steps given below:
* Create the instance of veritrans library using **VeritransBuilder** class.
* Create instance of  **TransactionRequest** and add required fields like item details, 
shipping address etc.
* Then add Transaction details in previously created veritrans object using 

        mVeritransSDK.setTransactionRequest(TransactionRequest);
    
* Then execute Transaction using following method and add **TransactionCallback** to get response back.


```

  
       mVeritransSDK.paymentUsingPermataBank(activity, new TransactionCallback() {
                            
            @Override
            public void onFailure(String errorMessage, TransactionResponse transactionResponse) {
                    Log.d(" Transaction failed ", ""+errorMessage);
                    // write your code here to take appropriate action 
                }
    
            @Override
            public void onSuccess(TransactionResponse transactionResponse) {
                    Log.d(" Transaction status ", ""+transactionResponse.getStatusMessage());
                     // write your code here to handle success.
                    }
        });

      
```

* Take appropriate action in onFailure() or  onSuccess() of TransactionCallback.
  

### 5.3 Mandiri bill payment

###### To perform transaction using mandiri bill payment method follow the steps given below:
* Create the instance of veritrans library using **VeritransBuilder** class.
* Create instance of **TransactionRequest** and add required fields like item details, 
   shipping address etc.
* Then add Transaction details in previously created veritrans object using 

```

        mVeritransSDK.setTransactionRequest(TransactionRequest);

```

* Then execute Transaction using following method and add **TransactionCallback** to get response back

```

        mVeritransSDK.paymentUsingMandiriBillPay(activity, new TransactionCallback() {
   
                            
            @Override
            public void onFailure(String errorMessage, TransactionResponse transactionResponse) {
                    Log.d(" Transaction failed ", ""+errorMessage);
                    // write your code here to take appropriate action 
                }
        
                @Override
                public void onSuccess(TransactionResponse transactionResponse) {
                    Log.d(" Transaction status ", ""+transactionResponse.getStatusMessage());
                    // write your code here.
                }
        });
        
```

* Take appropriate action in onFailure() or onSuccess() of TransactionCallback.



### 5.4 Indosat Dompetku 

###### To perform transaction using indosat dompetku payment method follow the steps given below:
* Create the instance of veritrans library using **VeritransBuilder** class.
* Create instance of **TransactionRequest** and add required fields like item details, 
   shipping address etc. This payment method requires **MSISDN** number which is registered mobile number of user.
* Then add Transaction details in previously created veritrans object using 

```

        mVeritransSDK.setTransactionRequest(TransactionRequest);

```

* Then execute Transaction using following method and add **TransactionCallback** to get response back

```

        mVeritransSDK.paymentUsingIndosatDompetku(activity , MSISDN, new TransactionCallback() {
   
                            
            @Override
            public void onFailure(String errorMessage, TransactionResponse transactionResponse) {
                    Log.d(" Transaction failed ", ""+errorMessage);
                    // write your code here to take appropriate action 
                }
        
                @Override
                public void onSuccess(TransactionResponse transactionResponse) {
                    Log.d(" Transaction status ", ""+transactionResponse.getStatusMessage());
                    // write your code here.
                }
        });
        
        
        where - msisdn  is registered user mobile number, must be less than 12. for debug you can use 08123456789
        
```

* Take appropriate action in onFailure() or onSuccess() of TransactionCallback.





### 5.5 Indomaret 

###### To perform transaction using indomaret payment method follow the steps given below:
* Create the instance of veritrans library using **VeritransBuilder** class.
* Create instance of **TransactionRequest** and add required fields like item details, 
   shipping address etc. This payment method requires **Cstore** details.
* Then add Transaction details in previously created veritrans object using 

```
// add cstore deatils
IndomaretRequestModel.CstoreEntity cstoreEntity = new IndomaretRequestModel.CstoreEntity();
        cstoreEntity.setMessage("message_here");
        cstoreEntity.setStore("indomaret");


        mVeritransSDK.setTransactionRequest(TransactionRequest);

```

* Then execute Transaction using following method and add **TransactionCallback** to get response back

```

        mVeritransSDK.paymentUsingIndomaret(activity, cstoreEntity, new TransactionCallback() {
   
                            
            @Override
            public void onFailure(String errorMessage, TransactionResponse transactionResponse) {
                    Log.d(" Transaction failed ", ""+errorMessage);
                    // write your code here to take appropriate action 
                }
        
                @Override
                public void onSuccess(TransactionResponse transactionResponse) {
                    Log.d(" Transaction status ", ""+transactionResponse.getStatusMessage());
                    // write your code here.
                }
        });
        
```

* Take appropriate action in onFailure() or onSuccess() of TransactionCallback.




### 5.6 Mandiri ClickPay

###### To perform transaction using mandiri click pay payment method follow the steps given below:
* Create the instance of veritrans library using **VeritransBuilder** class.
* Create instance of **TransactionRequest** and add required fields like item details, 
   shipping address etc. This payment method also requires details about **Mandiri click pay**.
* Then add Transaction details in previously created veritrans object using 

```


mVeritransSDK.setTransactionRequest(TransactionRequest);

// add Mandiri ClickPay Model deatils
MandiriClickPayModel mandiriClickPayModel = new MandiriClickPayModel();
                    mandiriClickPayModel.setCardNumber(CardNumber);
                    mandiriClickPayModel.setInput1(INPUT1);
                    mandiriClickPayModel.setInput2(INPUT2);
                    mandiriClickPayModel.setInput3(INPUT3);
                    mandiriClickPayModel.setToken(CHALLENGE_TOKEN);


where 
CardNumber String Required -    Mandiri debit Card number.
INPUT1 -    Last 10 digit of card_number.
INPUT2 -    Transaction gross_amount.
INPUT3 -    5-digits random number which will be given to the customer.
CHALLENGE_TOKEN -   Number received by customer's physical token after input1, input2, and input3 are entered.

        

```

* Then execute Transaction using following method and add **TransactionCallback** to get response back.

```

        mVeritransSDK.paymentUsingMandiriClickPay(activity.this,
                mandiriClickPayModel, new TransactionCallback() {
   
                            
            @Override
            public void onFailure(String errorMessage, TransactionResponse transactionResponse) {
                    Log.d(" Transaction failed ", ""+errorMessage);
                    // write your code here to take appropriate action 
                }
        
                @Override
                public void onSuccess(TransactionResponse transactionResponse) {
                    Log.d(" Transaction status ", ""+transactionResponse.getStatusMessage());
                    // write your code here.
                }
        });
        
```

* Take appropriate action in onFailure() or onSuccess() of TransactionCallback.


### 5.7 ePay BRI

###### To perform transaction using ePay BRI payment method follow the steps given below:
* Create the instance of veritrans library using **VeritransBuilder** class.
* Create instance of **TransactionRequest** and add required fields like item details, 
   shipping address etc. 
* Then add Transaction details in previously created veritrans object using 

```
mVeritransSDK.setTransactionRequest(TransactionRequest);
```

* Then execute Transaction using following method and add **TransactionCallback** to get response back. In onSuccess() of callback you will get redirect url from server, start webview to handle the redirect url. 

```

        mVeritransSDK.paymentUsingEpayBri(activity,
                new TransactionCallback() {
   
                            
            @Override
            public void onFailure(String errorMessage, TransactionResponse transactionResponse) {
                    Log.d(" Transaction failed ", ""+errorMessage);
                    // write your code here to take appropriate action 
                }
        
                @Override
                public void onSuccess(TransactionResponse transactionResponse) {
                    Log.d(" Transaction status ", ""+transactionResponse.getStatusMessage());
                    //here you will get url
                    
                    if (transactionResponse != null &&
                                !TextUtils.isEmpty(transactionResponse.getRedirectUrl())) {
                            
                            // create Web Activity  to handle redirect url. start web activity for result 
                        }
                }
        });
       
       
       
       
       
        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data!=null ) {
        // get data from intent.
            String responseStr = data.getStringExtra(Constants.PAYMENT_RESPONSE);
             Gson gson = new Gson();
            TransactionResponse transactionResponse = gson.fromJson(responseStr, TransactionResponse.class);
            // handle response here
            
      }
      } 
```

* Create Web View  Activity to handle  redirect url and set following setting to webView      
    - Enable java script.
    - setLoadWithOverviewMode() to true
    - add javaScript interface to intercept the request, to get the status of transaction.
    e.g - 
```
private void initwebview() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
       
       // java script interface to intercept the request and get paymet status data from server.
        webView.addJavascriptInterface(new JsInterface(), Constants.VERITRANS_RESPONSE);
       
       // don't change the second paramter. It is constant used in sdk to find response status.
    }

    public void webviewBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.contains(Constants.CALLBACK_STRING)) {
                Intent returnIntent = new Intent();
                getActivity().setResult(getActivity().RESULT_OK, returnIntent);
                getActivity().finish();
            } 
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }



/**
Use this interface in your web view to get response back. 
**/
    public class JsInterface {

        /**
         * java script code written on merchant server (redirect url)
         * doctype html
         html
         head
         title= title
         script(type='text/javascript').
         function paymentStatus(data) {
         Android.paymentResponse(data);
         }

         body(onload="paymentStatus('" + paymentStatus + "')")
         h1 Success.
         * @param data
         */
        @JavascriptInterface
        public void paymentResponse(String data) {
            // finish activity and return payment response.
            Intent intent = new Intent();
            intent.putExtra(Constants.PAYMENT_RESPONSE, data);
            getActivity().setResult(getActivity().RESULT_OK, intent);
            getActivity().finish();
        }

    }
    
    }//end of WebviewActivity class
```

### 5.8 CIMB Clicks

###### To perform transaction using CIMB Clicks payment method follow the steps given below:
* Create the instance of veritrans library using **VeritransBuilder** class.
* Create instance of **TransactionRequest** and add required fields like item details, 
   shipping address etc. This payment method requires **Description Model**.
* Then add Transaction details in previously created veritrans object using 

```
mVeritransSDK.setTransactionRequest(TransactionRequest);
```

* Then execute Transaction using following method and add **TransactionCallback** to get response back. In onSuccess() of callback you will get redirect url from server, start webview to handle the redirect url.
* Handle the further process and response same as performed in ePay BRI.

```
        //add Description for transaction
        DescriptionModel cimbDescription = new DescriptionModel("Any Description"); 

        mVeritransSDK.paymentUsingCIMBClickPay(CIMBClickPayActivity.this, cimbDescription, new TransactionCallback() {
                            
            @Override
            public void onFailure(String errorMessage, TransactionResponse transactionResponse) {
                    Log.d(" Transaction failed ", ""+errorMessage);
                    // write your code here to take appropriate action 
                }
        
                @Override
                public void onSuccess(TransactionResponse transactionResponse) {
                    Log.d(" Transaction status ", ""+transactionResponse.getStatusMessage());
                  // handle redirect url same as e-Pay BRI. 
                }
        });
        
```



### 5.9 BBM money - 

###### To perform transaction using BBM money app follow the steps given below:
* Check Whether BBM moeny app is installed or not on device using following code : -
```
    SdkUtil.isBBMMoneyInstalled(activity)- it will return true if app is installed on device. 

```
* Create the instance of veritrans library using **VeritransBuilder** class.
* Create instance of **TransactionRequest** and add required fields like item details, 
   shipping address etc. This requires BBM callback url  call bas
* Then add Transaction details in previously created veritrans object using 

```
                    BBMCallBackUrl bbmCallBackUrl = new BBMCallBackUrl(Constants.CHECK_STATUS,
                            Constants.BEFORE_PAYMENT_ERROR, Constants.USER_CANCEL);
                    //start transaction
                    mVeritransSDK.setTransactionRequest(transactionRequest);
                    mVeritransSDK.setBBMCallBackUrl(bbmCallBackUrl);

```

* Then execute Transaction using following method and add **TransactionCallback** to get response back. In onSuccess() of callback you will get Permata Virtual Account number (Payment code).
* Handle the further process and response same as performed in ePay BRI.

```
        //add Description for transaction
        DescriptionModel cimbDescription = new DescriptionModel("Any Description"); 

        mVeritransSDK.paymentUsingBBMMoney(activity, description, new TransactionCallback()  {
                            
            @Override
            public void onFailure(String errorMessage, TransactionResponse transactionResponse) {
                    Log.d(" Transaction failed ", ""+errorMessage);
                    // write your code here to take appropriate action 
                  
                }
        
                @Override
                public void onSuccess(TransactionResponse transactionResponse) {
                    Log.d(" Transaction status ", ""+transactionResponse.getStatusMessage());
                    // get permata VA number  
                    String PERMATA_VA = transactionResponse.getPermataVANumber();
                }
        });
       
```

* Create encode Url using Permata virtual account number and callback url in the following format -
    URL- bbmmoney://api/payment/imp?data=URL encoded JSON data

Now created encoded url using following json format- 
``` 
{
  "reference":"8744000000000091", // permata_va_number

  "callback_url": {
    "check_status":"https://example.com/order/check_status.html",
    "before_payment_error":"https://example.com/order/unfinish.html",
    "user_cancel":"https://example.com/order/cancel.html"
    }
}
```
**_You can use following static method from sdk to create encoded string_** - 
```
SdkUtil.createEncodedUrl(String permataVA, String checkStatus, String
            beforePaymentError, String userCancel);
```



* Now start bbm money app using following code :
```
String  BBM_PREFIX_URL = "bbmmoney://api/payment/imp?data=";

String  encodedUrl = SdkUtil.createEncodedUrl(PERMATA_VA,
                        checkStatus,
                        beforePaymentError,
                        userCancel);

                String feedUrl = BBM_PREFIX_URL + encodedUrl;

                if (SdkUtil.isBBMMoneyInstalled(BBMMoneyActivity.this)) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(feedUrl)));
                }
          
```


### 5.10 Mandiri e-Cash - 

###### To perform transaction using Mandiri e-Cash payment method follow the steps given below:
* Create the instance of veritrans library using **VeritransBuilder** class.
* Create instance of **TransactionRequest** and add required fields like item details, 
   shipping address etc. This payment method requires **Description Model**.
* Then add Transaction details in previously created veritrans object using 

```
mVeritransSDK.setTransactionRequest(TransactionRequest);
```

* Then execute Transaction using following method and add **TransactionCallback** to get response back. In onSuccess() of callback you will get redirect url from server, start webview to handle the redirect url.
* Handle the further process and response same as performed in ePay BRI.

```
        //add Description for transaction
        DescriptionModel cimbDescription = new DescriptionModel("Any Description"); 

        mVeritransSDK.paymentUsingMandiriECash(activity, description, new TransactionCallback()  {
                            
            @Override
            public void onFailure(String errorMessage, TransactionResponse transactionResponse) {
                    Log.d(" Transaction failed ", ""+errorMessage);
                    // write your code here to take appropriate action 
                  
                }
        
                @Override
                public void onSuccess(TransactionResponse transactionResponse) {
                    Log.d(" Transaction status ", ""+transactionResponse.getStatusMessage());
                    // handle redirect url same as e-Pay BRI. 
                }
        });
        
```

### 5.11 Payment using Installments Flow
 * Make api call for **get token** with 2 new params 
 ```
  'installment' : true, 
  'installment_term' : 12,  
  ```  
  * In **cardTokenRequest** object set this 2 param
 ```  
 //for installment flow
 cardTokenRequest.setInstalment(true); 
 cardTokenRequest.setInstalmentTerm(instalmentTerm);  
 ```  
 * When we are doing **charge api** call we have to set 2 param   
 ```
 'installment_term' : 12,
 'bins' : ["mandiri","48111111", "3111", "5"]
 ```
 * You can find this code in **payUsingCard** function on OffersActivity in VeritransSDK.
 * If offer is valid then payment will take place else respective error is given.
