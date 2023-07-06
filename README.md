# Weather App

An weather application

## Project Overview

The goal is create app that gets and shows current Hanoi's weather and forecast its weather within
next 5 days

## API

- Use Open Weather API: "https://api.openweathermap.org"

## How to set up

- Step 1. Get your API key in https://home.openweathermap.org/api_keys
- Step 2. Go to "local.properties" and paste
```groovy
    API_URL="https://api.openweathermap.org"
    API_KEY="<YOUR_API_KEY>"
```

## Library used

- Dependency injection: Hilt - dagger
- Call API: Retrofit
- Kotlin coroutine
- Load image: Coil

## Screen short
<div style="display:flex;">
    <img src="https://raw.githubusercontent.com/SanRyoo/Weather/master/screenshots/image1.png" style="width:32%;">
    <img src="https://raw.githubusercontent.com/SanRyoo/Weather/master/screenshots/image2.png" style="width:32%;">
    <img src="https://raw.githubusercontent.com/SanRyoo/Weather/master/screenshots/image3.png" style="width:32%;"> 
</div>