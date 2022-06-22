# Web Drop-In Android SDK

The Web Drop-In Android SDK is a native wrapper to use the Smartfields Web solution to enable 
credit card tokenization for merchants that require the lowest level of PCI compliance. 
Lightweight library with only `35KB` AAR file size.

## Table of Contents

1. [ Requirements ](#markdown-header-requirements)
2. [ App permissions ](#markdown-header-app-permissions)
3. [ Installation ](#markdown-header-installation)
4. [ How to use ](#markdown-header-how-to-use)
5. [ Additional customization ](#markdown-header-additional-customization)
6. [ Testing the integration ](#markdown-header-testing-the-integration)
7. [ Sample App ](#markdown-header-sample-app)
8. [ Report Issues ](#markdown-header-report-issues)
9. [ License ](#markdown-header-license)

## Requirements

- Supports API versions from 23 and higher.

## App permissions

```
android.permission.INTERNET (Required)
```

## Installation

New releases of the Web Drop-In Android SDK are published via [Maven Repository](https://mvnrepository.com/artifact/com.dlocal.android/data-collector).
The latest version is available via `mavenCentral()`.

Add `mavenCentral()` to the project level [build.gradle](https://bitbucket.org/dlocal-public/web-drop-in-sdk-android/src/master/build.gradle#lines-5) file's repositories section, if you don't have it already:
```groovy

repositories {
    mavenCentral()
    ...
}

```

Add Web Drop-In SDK dependency to the application's [build.gradle](https://bitbucket.org/dlocal-public/web-drop-in-sdk-android/src/master/app/build.gradle#lines-38) file:
```groovy

dependencies {
    ...
    implementation 'com.dlocal.android:web-drop-in:0.0.1'
    ...
}

```

## How to use

### 1) Configure input options

Before initializing the tokenize flow you must create an object with all the input options for the SDK:

```kotlin
import com.dlocal.webdropin.DLTokenizeInput

val inputOptions = DLTokenizeInput(apiKey = "API KEY", country = "US", locale = "ES", testMode = true)
```

Replace `apiKey` with your key and `country` with the two letter [ISO 3166](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) 
country code, for example "UY" for "Uruguay", or "US" for "United States". 
And `locale` with the two letter [ISO 639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) for example "ES" for "Spanish", or "EN" for "English".

### 2) Register a callback for Activity Result

Create a `DLTokenizeContract` and handle the two different results in the callback and pass it to `registerForActivityResult`.

```kotlin
import com.dlocal.webdropin.DLTokenizeContract
import com.dlocal.webdropin.DLTokenizeInput
import com.dlocal.webdropin.DLTokenizeResponse

class MainActivity : AppCompatActivity() {

    private val tokenizeCardActivity = registerForActivityResult(DLTokenizeContract()) { result ->
        when (result) {
            is DLTokenizeResponse.Success -> {
                // Called when the tokenization process has successfully completed
                // Parameter will contain data about the card (including token) and installments data (if required)
            }
            is DLTokenizeResponse.Error -> {
                // Called when the tokenization process ends with an error
                // You are in charge of dealing with this error and presenting it to the user
            }
        }
    }
}
```

### 3) Launch from your Activity/Fragment

In your Activity call the `launch` to start the tokenize flow and pass the input options you have created:

```kotlin
import com.dlocal.webdropin.DLTokenizeContract
import com.dlocal.webdropin.DLTokenizeInput
import com.dlocal.webdropin.DLTokenizeResponse

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputOptions = DLTokenizeInput(
            apiKey = "API KEY",
            country = "US",
            locale = "ES",
            testMode = true
        )

        findViewById<Button>(R.id.startButton)?.setOnClickListener {
            tokenizeCardActivity.launch(inputOptions)
        }
    }
}
```

See the [Android docs](https://developer.android.com/training/basics/intents/result) for more details on how to register a callback for an Activity Result. 

## Additional customization

You can customize the tokenization form interface through the `DLTokenizeInput` parameters as follows:

```kotlin
import com.dlocal.webdropin.DLTokenizeContract
import com.dlocal.webdropin.DLTokenizeInput
import com.dlocal.webdropin.DLTokenizeResponse

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add the installments options, by default is null
        val installments = DLInstallments(
            amount = 800.0, // Total amount to pay with installments
            currency = "USD", // Currency of the installments
        )

        // Customize some styles of the form, if it is null takes the default styles
        val styles = DLStyle(
            darkMode = true, // Sets dark mode on/off (default is off)
            buttonText = "Pagar", // Sets the button text
            buttonTextColor = "#ffffff", // Sets button text color (default is white)
            buttonBackgroundColor = "#2b6be9", // Sets button background color (default is blue background)
            cardAnimation = false // Sets card animation on/off (default is on)
        )

        val inputOptions = DLTokenizeInput(
            apiKey = "API KEY",
            country = "US",
            locale = "ES",
            testMode = true,
            installments = installments, // Optional
            styles = styles // Optional
        )
    }
}
```

## Testing the integration

We strongly **recommend that you use the `SANDBOX` environment when testing**, and only use `PRODUCTION` in production ready builds.

To do so, you can use the `DLTokenizeInput` and set `testMode = true` and replacing the `"API KEY"` with yours for each environment, i.e:

```kotlin
val inputOptions = DLTokenizeInput(apiKey = "API KEY", country = "US", locale = "ES", testMode = true)
```

## Sample App

In this repository there's a [sample app](https://bitbucket.org/dlocal-public/web-drop-in-sdk-android/src/master/app/) to showcase how to use the SDK, please refer to the code for more detailed examples.

## Report Issues

If you have a problem or find an issue with the SDK please contact us at [mobile-dev@dlocal.com](mailto:mobile-dev@dlocal.com).

## License

```text
    MIT License

    Copyright (c) 2022 DLOCAL

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
```