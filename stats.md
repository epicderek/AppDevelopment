Readme for POCKET Statistics

At the header of each document, the overall statistics is listed. The statistics includes the inputted JSON Objects corresponding to the types Contact (Profile), Message, Notification, Device Event, Browser Visit or Search, Location; and subsequently, the corresponding to native Objects Profile, AbsLocation (sources), Message, Event, Query, PhyLocation.  
An example follows as such:

Inputted Profile Information 7025 pieces
Inputted Message Information 60 pieces
Inputted Notification Information 2975 pieces
Inputted Device Event Information 389 pieces
Inputted Browsing Information 32 pieces
Inputted Location Information 9439 pieces
				
				
Stored Profile Objects 614 pieces	
Stored AbsLocation Objects 26 pieces
Stored Message Objects 60 pieces	
Stored Event Objects 2975 pieces	
Stored Query Objects 32 pieces	
Stored PhyLocaiton Objects 236 pieces

As seen from the comparison of data listed above, Profile and Location information constitues possible duplicity. As the recorded contact information combined with the automatic creation of native Profile objects from message data contained in Notification files poses duplicity. As for locations, the user may commonly advent the same location multiple times, while from the perspective of physical location, this situation constitues but one PhyLocaiton object. The times of advention make simple scenarios, which are accounted later in this documentation. 

The specific information of each stored native object is then listed in its corresponding properties and capacity; as for Profile, it is name, dial, and email; for Message, it is from, to, content, source; for Event, it is source/location, time, participants, description; as for query, it is the url, the content of search, and the time; as for location, it is delineated as in the enumerated type included in the source code of PhyLocation class. The following are the examples of each native object type printed in an csv file.

Profile:
Name	Dial	Email
Sam Somebody	[123456789]	[samsome@somebody.com]
		
AbsLocation:
Source
www.google.com 

Message:
From	To	Time	Content	Source
Sam Somebody	User	Mon Dec 22 02:37:11 CST 2017	com.google.android.talk

Event:
Description	Time	Source	
posted"Tap to learn more and install"by Android System Update	Sun Dec 07 22:19:05 CST 2017	com.google.android.gms

Location:
STREET_NUM	ROUTE	NEIGHBORHOOD	LOCALITY	AD1	AD2	COUNTRY	POSTAL_CODE	Facility Type
1527	Asbury Pl	Squirrel Hill North	Pittsburgh	PA	Allegheny County	US	15217	["premise"]

Below each block of statistics of native objects the average per day statistics is included, ex. 10 pieces per day. The situation for location is peculiar, as the user may occasion the same location at different times, but the stored object is but one that includes all the times visited. In this, the statistics included the average data per day in the sense of event, which disregards duplicity in the actual location, and the average data per day in terms of the generation of native PhyLocation objects, which adopts this shape

337 pieces of advented per day
8 native objects per day.	



