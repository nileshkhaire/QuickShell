QuickShell is a Eclipse plugin to use java jshell inside Eclipse, even though you are not using JDK 9+ for running your eclipse. So without disturbing your existng project setup, you can use QuickShell. 

## How to setup :
- Download plugin JAR file and put into your Eclipse plugin directory "$ECLIPSE_HOME/plugins" . Restart eclipse.
- Go to Eclipse menu, Window -> Preferences -> QuickShell Settings and click on browse to select jshell executable present in JDK installatioin directory.(JDK 9+) ( jshell.exe in Windows)
> Note : Even thought you are using older JDK for running eclipse, you can still use this plugin by using external JDK . For this you can download JDK9+ from [ https://jdk.java.net/archive/ ]( https://jdk.java.net/archive/) and unzip it somewhere and select path of jshell executable in setting specified above. 

## How to use :
There are multiple ways to use QuickShell
- Click on eclipse menu QuickShell -> Start JShell in Eclipse Terminal . This will open jshell in eclipse terminal.
- Click on eclipse menu QuickShell -> Start JShell Executable . This will call jshell executable in separate window. (Note: This is platform dependent feature, if my not be supported on your operating system or it may not work on your operating system) . I have tested on Windows and it is working as expected. I am trying to support other OS in next releases. 
- Create a file with extension `.jsh`. for example `test.jsh` and right click on it and click on pop up menu QuickShell -> Run JShell Script. 
- Select your existing code in java ediotr and click on right click menu QuickShell -> Run as JShell script. 
