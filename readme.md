![](demo.gif)

Please refer to the demo video for core features.

I am using the https://sandbox.iexapis.com/stable/stock/market/batch end point for fetching quote prices, because I encountered some issue with the production endpoint. While I was testing the feature "update price by every 5s", I have exceeded the free allcated credit and was not able to fetch more request.

I was trying to compare the latest price with open price to dynamically change the color of symbols, however the open price is null. So if the open price is null, we are comparing to the bidprice.

Forgot to show in the video, you can swipde symbol from right to left to delete the symbol from a watchlist.



