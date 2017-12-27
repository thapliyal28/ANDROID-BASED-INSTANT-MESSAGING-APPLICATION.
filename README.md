# ANDROID-BASED-INSTANT-MESSAGING-APPLICATION.
The objective of this project is to implement an instant messaging (IM) app for Android users.

SOFTWARE USED:- 

The various software tools which have been used are:-

Java development kit (JDK) and Java Runtime Environment (JRE):- Since android applications are written in java. 
So we requires JDK and JRE to be installed in our computer for compilation purpose.

Android Studio:-Android studio is one of the software which is used for development of android applications. 
We need to install JDK first before installing android studio to our system. 

Google Firebase Cloud Messenger:- Firebase is used here as a server. 
The steps for connecting firebase to android project is given below.
1-Open Firebase.
2- Click on new Project.
3-Provide your Android project package name.
4-A Google Service json file  will be automatically get created, add this file to your android project module root directory.
5-Modify your build.gradle files of the project as per given steps and click on sync now then.
Project-level build.gradle .
buildscript {
  dependencies {
Add this line
    classpath 'com.google.gms:google-services:3.0.0'
  }}
App-level build.gradle .
 Add to the bottom of the file
apply plugin: 'com.google.gms.google-services'
includes Firebase Analytics by default help_outline

IMPLEMENTED FUNCTIONS.

USER JOIN: - The user join provides a joning mechanism for a user. User login is designed in such a way that even if we close an application one time. The next time whenever joined user will login he/she will be provided access to the application.

INTERNET CONNECTIVITY:- The application is capable of checking whether it is connected to internet or not, In case of no internet connectivity, message is shown while opening the application that internet is not available.

ADD GROUP:-User can add as many groups as he wants.And every new created group get stored in the firebase database.

DELETE:-User can quit or delete any group which he wants.

JOIN GROUP:-User can join any of the created group by just clicking on that group name.

POP UP MESSAGES:- POP up messages are implemented whenever a new group is created by a user, which says him that group is created.

SERVER:- We have used firebase cloud messaging service as a server here. It is a cross-platform solution for messages and notifications for Android, iOS, and web applications.

MESSAGE NOTIFICATION:-Whenever user receive an message in the group, notification window pop out showing the message he has received. Similarly when user send any message ,notification window shows the message send.

MESSAGE TIME:-This shows the particular time at which user sends the message as well as receives the message .
