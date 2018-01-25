# AppDevelopment

This package, dedicated to the development of Personally Oriented Collective Knowledge through Efficient Tracking, offers a preliminary experimentation and implementation of it. The interface collectively parses JSON data for specific applications and automatic storage as native objects. This description includes statistics and analysis of the parsing of personal information and storage as native objects. 
 
The main classes included are AbsLocation, Profile, PhyLocation, Event, Message, Email, Query and Interpreter, in which the six in the middle represent personal data. 

An AbsLocation is an abstract location that represents a source in the client's software space which comes in handy in the description of events that happen in the software space; which is the parallel of PhyLocation, a physical location; as well as a general tagging of data. It is contained in Event, Message, Email, and Query.

An AbsLocation can be instantiated given the name of the source, the time, and optionally the possible events associated with it. 

                     AbsLocation sor = AbsLocation.processAbsLocation(String ros, Time time);

 As a general note, in attempt to avoid duplicity in object storage, all objects are to be created from static methods, in this, the processAbsLocation method, which automatically searches the sources computed and stored and return either an existent equivalent AbsLocation object, or the new AbsLocation object after storage.
 
A Profile is a detailed representation of a person, it contains the name of the person, the email, the phone, and optionally the address. Comparison for Profiles are solely based on the names of the persons. 

It can be instantiated this way.

                    Profile pro = Profile.processProfile(String name, String[] email, String[] phone);

And in case of a lack of specific information, it can be instantiated this way.

                    Profile pro = Profile.processProfile(String name);

According to a statistics of the contact informations scanned from a user in a day and their resultant storage as Profile Objects, it is shown that 

                                        Inputed Profile Information 45917 pieces
                                           Outputed Profile Objects 1902 pieces
                                                     Difference is 44015.

This is due to the plausible duplicity in scanning as well as the actual redundant storage which proclaims the promises for POCKET. A standard Profile object printed is of the following format:

                                        Profile of Qirong Ho
                                        email [\\\.@gmail.com], dial [],
where part of the information may be missing.
 
A PhyLocation object is a comprehensive representation of a geographical position in terms of a coordinate with the times accessed, people related, and events involved. Locations of different associated information but identical coordinates are stored as one location object. Relevant geographic information is derived by invoking the helper method in reverse geocoding in the Interpreter class. The specific information garnered is contained in a map whose keys are enumerated types in accord with the types in the api, STREET_NUM,ROUTE,NEIGHBORHOOD,LOCALITY,ADMINISTRATIVE1,ADMINISTRATIVE2,COUNTRY,POSTAL_CODE. The PhyLocation object also contains the type of the location in an array of strings.

A PhyLocation can be created this way.

                    PhyLocation loc = PhyLocation.getLocation(Coordinate coor, Time time);

The Coordinate are the longitude and latitude of the location, and time is when this object is created. 

The specific information of a location may be inquired by invoking the method getInfo(LocationType type). For example, if we were to inquire of the zip-code of a location, we make the call as such.

       String zip = loc.getInfo(LocationType.ZIP);

According to a statistics of the record of locations a user occasions at instants in a day and their resultant storage as Profile Objects, it is shown that

                                        Inputed Location Information 3331 pieces
                                           Outputted Location Objects 252 pieces
                                                      Difference is 3079.

It is true that the act of the person being somewhere may be considered as a piece of Event, but it nevertheless a too raw and deficient data in a much more meaningful structure. And recording both the time and the coordinate fully compensate the overhead while saving the memory.
 
A PhyLocation is printed in this manner:

                                       Location
                                       Type as ["premise"]
                                       902 Brinton Rd, Pittsburgh, PA 15221, USA
                                       at Times [2080841175, 2080964707, 2081105825, 2081264646,
                                       2081415416, 2081565481, 2081715565,2081865620,     
                                       2082015629].  

An Event object accounts for a general event in which an action is affiliated with a location, either concrete or abstract, at a time, along with possible people involved. It retains the abstraction to delineate any event and serve as the superclass of more specific designs. It can be created this way.
	
                 Event eve = Event.processEvent(Time time, Location loc, Description des);

The Description Object is currently just a String representation of the action or detail of the event, which will eventually evolve into a more complicated design. 

The Message and Email classes are derived from the Event class and bear enough resemblance to be addressed collectively. They both demand a time, a sender and a recipient, the abstract location in the software space, and the content of the communication. The Message object is representative of the “msg” information contained in Notification files. 

They be instantiated as such. 

                 Message mes = Message.processMessage(Time time, Profile from, Profile to, 
	                                String cont, AbsLocation space);
                 Email em = Email.processEmail(Time time, Profile from, Profile to, String cont,
                                    AbsLocation space);

According to the statistics of Message objects of a user per day,

                                  Inputed Message Information 150 pieces
                                 Outputed Message Objects 150 pieces
                                                     Difference is 0,
of which a standard printed version adopts the form of

			Message from Steven Dang to User
at Time 970097405
in com.Slack
of "where di u heara bout it?".

Note that in the creation of Message objects, Profile Objects are created to accommodate the people involved.

As a general note, all constructed personal oriented objects are stored statically in its class to avoid duplicity and enable efficient searching, all Profile objects in the Profile class, etc. Though the Event class is the superclass of Message and Email, the Message and Email objects created are stored separately in their own classes instead of in the Event class. 

Now, to actually process the data stored in JSON files, we apply the Interpretor class. Without exhausting query into its design, an Interpretor object targets a JSON file and parses the file and store the information as native objects. 

To create an interpretor, use the constructor with the file as an argument. 

                 Interpretor inte = new Interpretor(File file);

And to reset the interpreter to target a different file, do this.

                 inte.set(File file2);
	
The mode of interpretation has to be chosen manually in accord with the file to be processed. For example, to process a Notification file, do this.
	
	     Event[] eve = inte.processNote();
	
To start the static counter, which keeps track of the number of pieces of information consumed, do this.

     Profile[] pros = inte.processLog(true);

The counter needs to be resetted at the desired time, possibly after running a specific type of files. Also, the above assumes the file is being resetted correctly to a Log file. 

The method returns an array of possibly duplicated Event and Message objects; nevertheless, unless specially interested, all the objects are processed are stored automatically into each class. Such, to access the Message objects generated, adopt this syntax.
     
    Collection<Message> = Message.mess.values();


	
