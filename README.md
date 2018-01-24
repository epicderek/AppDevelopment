# AppDevelopment
A repository dedicated to the development of POCKET.

  The main classes included are Profile, Location, Event, Message, Email, Query and Interpretor, which processes data formatted in json into objects of the aforementioned classes. Each of these classes are designed by composition of other sub-classes as Coordinate, Time, etc. which are contained in the class Sub. 
  The supported data formats for Interpretor are browser search, browser query, notification, logs, and location. Specifically, the Interpretor class has a static method which takes in the latitude and longitude along with a map to write in to generate all relevant information about the given coordiate and record them in the given map. A specific enumerated type about locations are created and included in PhyLocation to describe various types of location information. 
  
