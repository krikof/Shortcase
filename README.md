# Shortcase
By Kristian Kofoed

A small MvP comic app, as a part of a coding challenge for Shortcut.

I started of by conceptualizing of how I wanted the app to look.
This helped me understand what features I had to try cover and how to do it. 

https://xd.adobe.com/view/cc3a6b75-6011-458e-9a8a-64c67db5818b-9969/

#HOME

#ComicView

I tried to focus on creating a ComicView that would interact with the HomeFragment. It works for now, but it is messy, and could have been better.
The buttons are all clickable, but the actual functions do not work.

Explanation was supposed to take the current comic and open a WebView/browsers with the num and title parsed correctly.
Favorite works, but it's missing a toggle state, allowing you to unfavorite.
Share is missing the data, but the share dialogue works. 

If you long press the share button, you'll simulate a notification. It is also missing data, but it serves to prove that I know how to set up a notification.

The buttons, margin, padding and so forth is a little messy, colors, they are a litte messy.

#SEARCH

Search turned into a simple WebView. This is because I wasn't sure how to work with this API as it wasn't the typical REST API.
It also did not work at the time of development.
It seemed like the best solution for both the search and explanation endpoint would be to data scrape the website, which seemed a little too much.

#FAVORITES

Favorites work and is persistent. The objects and images are stored as bitmaps in the database to work offline.

#TESTING

Just like real projects, testing sadly comes last. This hasn't been implemented. 

<img src="https://preview.redd.it/hc9h5tkmd3m41.jpg?width=960&crop=smart&auto=webp&s=eba888722d85d94ca85cb30efeef0d90055c45b6" height="200" width="200" >
