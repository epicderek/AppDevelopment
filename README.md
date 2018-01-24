# AppDevelopment
A repository dedicated to the development of POCKET.

  # AppDevelopment

 This package is dedicated to the development of Personally Oriented Collective Knowledge through Efficient Tracking offers a preliminary experimentation and implementation of it. The interface collectively parses JSON data for specific applications and automatic storage as native objects.
  The main classes included are AbsLocation, Profile, PhyLocation, Event, Message, Email, Query and Interpretor, in which the six in the middle represent personal data. 
  An AbsLocation is an abstract location that represents a source in the client's software space which comes in handy in the description of events that happen in the software space; which is the parallel of PhyLocation, a physical location; as well as a general tagging of data. It is contained in Event, Message, Email, and Query.
  An AbsLocation can be intantiated given the name of the source, the time, and optionally the possible events associated with it. 
                     AbsLocation sor = AbsLocation.processAbsLocation(String ros, Time time);
  As a general note, in attempt to avoid duplicity in object storage, all objects are to be created from static methods, in this, the processAbsLocation method, which automatically searches the sources computed and stored and return either an existent equivalent AbsLocation object, or the new AbsLocation object after storage.
  A Profile is a detailed representation of a person, it contains the name of the person, the email, the phone, and optionally the address. Comparison for Profiles are solely based on the names of the persons.
  It can be instantiated this way.
                    Profile pro = Profile.processProfile(String name, String[] email, String[] phone);
  And in case of a lack of specific information, it can be instatiated this way.
                    Profile pro = Profile.processProfile(String name);
  According to a statistics of the contact informations scanned from a user in a day and their resultant storage as Profile Objects, it is shown that 
                                        Inputed Profile Information 45917 pieces
                                           Outputed Profile Objects 1902 pieces
                                                     Difference is 44015.
  This is due to the plausible duplicity in scanning as well as the actual redundant storage which proclaims the promises for POCKET. A standard Profile object printed is of the following format:
                                        Profile of Qirong Ho
                                        email [qho@andrew.cmu.edu], dial [],
where part of the information may be missing.
   A PhyLocation object is a comprehensive representation of a geographical position in terms of a coordinate with the times accessed, people related, and events involved. Locations of different associated information but identical coordinates are stored as one location object. Relevent geographic information is derived by invoking the helper method in reverse geocoding in the Interpretor class. The specific information garnered is contained in a map whose keys are enumerated types in accord with the types in the api, STREET_NUM,ROUTE,NEIGHBORHOOD,LOCALITY,ADMINISTRATIVE1,ADMINISTRATIVE2,COUNTRY,POSTAL_CODE. The PhyLocation object also contains the type of the location in an array of strings.
   A PhyLocation can be created this way.
                    PhyLocation loc = PhyLocation.getLocation(Coordinate coor, Time time);
   The Coordinate are the longitute and latitude of the location, and time is when this object is created. 
   According to a statistics of the record of locations a user occasions at instants in a day and their resultant storage as Profile Objects, it is shown that
                                        Inputed Location Information 3331 pieces
                                           Outputed Location Objects 252 pieces
                                                      Difference is 3079.
  It is true that the act of the person being somewhere may be considered as a piece of Event, but it nevertheless a too raw and deficient data in a much more meaningful structure. And recording both the time and the coordinate fully compensate the overhead while saving the memory.
  A PhyLocation is printed in this manner:
                                       Location
                                       Type as ["premise"]
                                       902 Brinton Rd, Pittsburgh, PA 15221, USA
                                       at Times [2080841175, 2080964707, 2081105825, 2081264646, 2081415416, 2081565481, 2081715565,                                            2081865620, 2082015629].
 

 
  Each of these classes are designed by composition of other sub-classes as Coordinate, Time, etc. whose detailed information may be garnered from a consult to the class Sub.
  The supported data formats for Interpretor are browser search, browser query, notification, logs, and location. Specifically, the Interpretor class has a static method which takes in the latitude and longitude along with a map to write in to generate all relevant information about the given coordiate and record them in the given map. A specific enumerated type about locations are created and included in PhyLocation to describe various types of location information. 
