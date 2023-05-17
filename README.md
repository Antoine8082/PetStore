# Kalismart Ecma Protocol Design

*Revision 1.2.0*


## Generality


A connected device could be controlled by commands written in its specific language, or/and could emits responses in the same language.
One needs to write a program to convert that specific language to Kalicore inner language.
Those messages (commands and responses) are transported by several means:

- serial port
- tcp socket
- udp socket
- os process
- http
- websocket

A device protocol always defines a delimitation system, to ensure that successive messages are not mixed.
Each device has a message handling rate. Usually 300 ms is fine. Some devices are slower.

The messages deals with properties and values. The commands can be separate in read and write command.
Read the current property value; write a new property value.


Some devices responds globally: all its properties are defined in the response data.

Some devices aknowledge the sent command by emiting a stateless response:
the response itself contains the property and the value (or all the properties/values).


Some devices emit a simple OK aknowledge since their protocol is statefull.
Kalicore is an asynchronous system: it requires a fully qualified response.
The statefull protocol responses are thus not handled by Kalicore.


Some devices offers a read command that is used to retrieve a property value: the device is property readable.
If the protocol is not stateless but readable, Kalicore automatically retrieve the new values after a command.


Some devices - like sensors - are only readable.
Some devices - like IR controlled devices - are only writable.


## Kalicore object structure and methods


Kalicore deals with 4 classes:


1. DEVICECOMMAND
2. PROTOCOLCOMMAND
3. RAWRESPONSE
4. PROTOCOLRESPONSE






The purpose of an Ecma Protocol is to forge native device protocol command
and to decode native devioce protocol response.


This is achieved with encodeCommand(DEVICECOMMAND, PROTOCOLCOMMAND) function and
decodeResponse(RAWRESPONSE, PROTOCOLRESPONSE) function.


Those two functions deals with objects passed by reference.



### function  encodeCommand(DEVICECOMMAND, PROTOCOLCOMMAND)


This function is called to create one or more Command, NetworkRequest, ProxyCommand or VirtualResponse from a DEVICECOMMAND
and to append it/them to the PROTOCOLCOMMAND.



### function  decodeResponse(RAWRESPONSE, PROTOCOLRESPONSE)


This function is called to create one or more Response, ProxyResponse or DeviceInterfaceCommand from a RAWRESPONSE
and to append it/them to the PROTOCOLRESPONSE.


### 1 - DEVICECOMMAND


A DEVICECOMMAND describes the command from the Kalicore point of view.
It could have been sent by Kalismart or by Kalicore itself, through a direct user interaction, the scene system or the protocol system


Kalicore caches the device properties for performance.


The DEVICECOMMAND.mode() contains one of the 3 fours *mode*:


- GET: retrieve a property value from cache (no communication with the physical device)
- READ: retrieve a property value from the physical device
- WRITE: write a new property value to the physical device


The DEVICECOMMAND.operation() usually contains *get* or *set* word, but can embed anything since it will be parsed
in the Ecma protocol.


The DEVICECOMMAND.propertyName() contains the property name.
The DEVICECOMMAND.propertyValue() contains the property value.
The DEVICECOMMAND.propertyVirtual() returns a boolean value. 


Some protocols needs properties that are retrieve with those methods:
DEVICECOMMAND.hostIpAddress()
DEVICECOMMAND.hostPort()
DEVICECOMMAND.wanHostIpAddress()
DEVICECOMMAND.wanHostPort()
DEVICECOMMAND.username()
DEVICECOMMAND.password()


A dataBinding structure is a pairs (key:value) container that is embedded in the DEVICECOMMAND.


DEVICECOMMAND.dataBinding(key) retrieve a value.
DEVICECOMMAND.containsDataBinding(key) check if a key exists.
DEVICECOMMAND.dataBindingToString() is used for logging.




DEVICECOMMAND.deviceProperty(propertyName) returns the cached property value.
DEVICECOMMAND.containsDeviceProperty(propertyName) checks if a device property exists.
DEVICECOMMAND.devicePropertiesToString() is used for logging.


DEVICECOMMAND.deviceId() returns the Kalicore Device ID.
DEVICECOMMAND.proxyDeviceId() returns the Kalicore Device Proxy ID.


DEVICECOMMAND.toConsole() is used for logging.






### 2 - PROTOCOLCOMMAND


A PROTOCOLCOMMAND contains one or more Command, NetworkRequest, ProxyCommand or VirtualResponse.


PROTOCOLCOMMAND.addCommand(hexEncodedRawCommand, delay): add one hex encoded string (the native protocol frame) with an optional delay (ms).


PROTOCOLCOMMAND.addNetworkRequest(method, url, sslErrors, hexEncodedBody, headers, delay): add a network request.
method: get | put | post | soap
url: MUST be created by forgeUrl(userName, password, hostAddress, hostPort, pathQuery, scheme) function
headers: MUST be created by  forgeHeaders(headerName, headerValue, headers) function
delay: optional delay (ms)


PROTOCOLCOMMAND.addProxyCommand(DEVICECOMMAND): a ProxyCommand is re-emitted to the Proxy Device.


PROTOCOLCOMMAND.addVirtualResponse(propertyName, propertyValue, delay): create a virtual (or fake) response



### 3 - RAWRESPONSE


A RAWRESPONSE contains either hex encoded or plain data string.
If the protocol has a Http transport, the RAWRESPONSE also contains url, headers and errorString.


RAWRESPONSE.toConsole() is used for logging.
RAWRESPONSE.rawData() returns the raw data coming from the physical device. WARNING: the delimiter has been removed.


RAWRESPONSE.externalUuid() returns the external Uuid used with Proxy Devices (Gateway + Slaves).
RAWRESPONSE.setExternalUuid: set the external Uuid for a ProxyResponse (see below).


RAWRESPONSE.rawHeaders() returns the headers of a network request (JSON).
RAWRESPONSE.url() returns the url of a network request.
RAWRESPONSE.errorString() returns the error string of a network request.


RAWRESPONSE.setRawResponseData() allows to change the data.




### 4 - PROTOCOLRESPONSE


A PROTOCOLRESPONSE can contains one or more Response, ProxyResponse or DeviceInterfaceCommand.


A Response is a propety name / property value pair.
A Response will be emitted as a DeviceNotifications (if the property value is different that the cached one).


A ProxyResponse will be transfered to the Proxy device.


A DeviceInterfaceCommand will be handled by Kalicore and processed in encodeCommand().


PROTOCOLRESPONSE.toConsole(index = 0) is used for logging.
PROTOCOLRESPONSE.addResponse(propertyName, propertyValue)
PROTOCOLRESPONSE.addProxyResponse(RAWRESPONSE)




*Look at ECMAHTTPFSAPI.js protocol for a use case.*


PROTOCOLRESPONSE.addDeviceInterfaceCommand(DEVICEINTERFACECOMMAND):


DEVICEINTERFACECOMMAND is an EcmaScript class with only one method: its constructor.
DEVICEINTERFACECOMMAND(deviceId, mode, operation, propertyName, propertyValue)







## About EcmaScript


Kalismart uses an EcmaScript engine version 7 (2016).
All EcmaScript functions prior to version 7 are available.


https://www.ecma-international.org/ecma-262/7.0/


One could easily check if a method/function is available on the website w3schools.com for the 7 EcmaScript version.


E.g. https://www.w3schools.com/jsref/jsref_startswith.asp


> Technical Details
> Return Value:   A Boolean. Returns true if the string starts with the value, otherwise it returns false
> JavaScript Version:   ECMAScript 6




## Kalicore EcmaScript function library


Some functions/classes are automatically prepended to any protocol script. Those functions should be reused rather than recode a similar function.


### Functions/Classes list


- function prefixHex(string)
- function asciiToHex(string)
- function valueToPercentage(value, minValue, maxValue)
- function percentageToValue(percentage, minValue, maxValue)
- function stringReplaceAt(str, index, replacement)
- function stringReverse(str)
- function stringHexFormat(str)
- function hexToAscii(hex)
- function hexToBin(hex)
- function hexToBytes(hex)
- function bytesToHex(bytes)
- function forgeUrl(userName, password, hostAddress, hostPort, pathQuery, scheme)
- function forgeHeaders(headerName, headerValue, headers)
- function fromXML(dataString)
- class DEVICEINTERFACECOMMAND(deviceId, mode, operation, propertyName, propertyValue)




## Ecma Protocol Structure


Some variables are mandatory and they are designed as the protocol header.
Functions encodeCommand(DEVICECOMMAND, PROTOCOLCOMMAND) and decodeResponse(RAWRESPONSE, PROTOCOLRESPONSE) are mandatory.
The file name itself has a naming convention.

### File name


The file name must be Protocol name + js extension.




### Header




#### protocolVersion


At this time, protocolVersion = 2




#### protocolName


Protocol type: PROXY | HTTP | SERIAL | TCP | UDP | WS | PROCESS


Protocol name must start with ECMA + [Protocol Type] + [specific name]




#### protocolMimeType


Text Protocol:


- text/plain
- text/html
- application/xml
- application/soap+xml
- application/json


Binary Protocol:


- application/octet-stream
- application/x-dmap-tagged




#### protocolDelimiterType


- fixedSize: requires protocolFrameSize
- endDelimiter: requires protocolEndDelimiter
- startEndDelimiters: requires protocolStartDelimiter and protocolEndDelimiter
- regexDelimiter (the pattern will be handled in C++: name captured groups are available [they are not in Ecma 7])
- startHeader: requires protocolHeaderSize, protocolBodySizePosition and protocolBodySizeLength
- varintHeader
- noDelimiter: for Proxy protocol only


#### protocolStartDelimiter


hex encoded string


#### protocolEndDelimiter


hex encoded string




#### protocolRegexDelimiter


C++ regexp patterm


#### protocolHeaderSize


int
#### protocolBodySizePosition


int


#### protocolBodySizeLength


int


#### protocolFrameSize


int




#### protocolFrameInterval


int (if null, kalicore will use 300 ms)






## Kalismart


1. create device protocol in protocol folder
2. launch python webserver in protocol folder: cd /Users/sylvain/Documents/KaliDev/KaliEcmaProtocols/serial; python -m SimpleHTTPServer 8000
3. in Kalismart: go to Protocol Designer
4. Press +
5. set the webserver parameters
6. export protocol to kalicore (MANDATORY)
7. create deviceClass in DeviceClass Designer




## Gateway and Proxy Devices


Some devices can be directly controlled by the Kalihub through sockets (TCP, UDP, websocket), serial ports (Usb serial converters) or Http.
Some devices live inside their own proprietary network (Knx, ZWave, Hue... ) and can't be controlled by the Kalihub: a special device called a gateway - directly accessible - is required. Each device on the specific network is identified by an uuid (The Knx is an exception since it
doesn't deal with devices but with functions [each function has a such uuid: its group address]). Those devices are called proxy devices in Kalismart.


Kalismart reproduces this scheme: one must create a gateway device and as much as necessary proxy devices. A new port type may be created to
let the device plugin system works correctly (the device plugin system assures the compatibility between gateway and proxy devices).


See Kalismart Device Design documentation.


The purpose of the Gateway/Proxy Kalismart architecture is to ensure that messages will be transmitted to the right object. The specific uuid
must then be defined as a dataBinding named *externalUuid* by convention. Some protocols deals with multiples uuids: by convention, a number is appended to *externalUuid* (but not for the first one). E.g.  *externalUuid2*, *externalUuid3*. **This convention MUST be followed.**


According to the protocol, two implementations are possible:


**If possible, always choose the second one (it is more compact)**




(Sequence diagram generated by https://textart.io/sequence | https://github.com/weidagang/text-diagram)


### 1 - Main code in Gateway


~~~~
object Kalismart Kalicore ProxyDevice  Gateway
Kalismart-> Kalicore: a DeviceInterfaceCommand
Kalicore-> ProxyDevice: a DeviceCommand
ProxyDevice-> ProxyDevice: encodeData(addProxyCommand)
ProxyDevice-> Gateway: a ProxyCommand
note right of Gateway: protocol frame forging is here!
Gateway-> Gateway: encodeData(addCommand)
Gateway->Kalicore: a RawCommand
Kalicore->Kalicore: Listen raw data
Kalicore-> Gateway: a RawResponse
Gateway-> Gateway: decodeResponse(addProxyResponse)
Gateway-> ProxyDevice: a ProxyResponse with externalUuid
ProxyDevice-> ProxyDevice: check externalUuid
ProxyDevice-> ProxyDevice: decodeResponse(addResponse)
ProxyDevice-> Kalicore: a Response
Kalicore-> Kalismart: a DeviceNotification
~~~~


~~~~
+-----------+                    +-----------+           +-------------+                            +---------+                            
| Kalismart |                    | Kalicore  |           | ProxyDevice |                            | Gateway |                            
+-----------+                    +-----------+           +-------------+                            +---------+                            
     |                                |                        |                                        |                                 
     | a DeviceInterfaceCommand       |                        |                                        |                                 
     |------------------------------->|                        |                                        |                                 
     |                                |                        |                                        |                                 
     |                                | a DeviceCommand        |                                        |                                 
     |                                |----------------------->|                                        |                                 
     |                                |                        |                                        |                                 
     |                                |                        | encodeData(addProxyCommand)            |                                 
     |                                |                        |----------------------------            |                                 
     |                                |                        |                           |            |                                 
     |                                |                        |<---------------------------            |                                 
     |                                |                        |                                        |                                 
     |                                |                        | a ProxyCommand                         |                                 
     |                                |                        |--------------------------------------->|                                 
     |                                |                        |                                        | ----------------------------------\
     |                                |                        |                                        |-| protocol frame forging is here! |
     |                                |                        |                                        | |---------------------------------|
     |                                |                        |                                        |                                 
     |                                |                        |                                        | encodeData(addCommand)          
     |                                |                        |                                        |-----------------------          
     |                                |                        |                                        |                      |          
     |                                |                        |                                        |<----------------------          
     |                                |                        |                                        |                                 
     |                                |                        |                           a RawCommand |                                 
     |                                |<----------------------------------------------------------------|                                 
     |                                |                        |                                        |                                 
     |                                | Listen raw data        |                                        |                                 
     |                                |----------------        |                                        |                                 
     |                                |               |        |                                        |                                 
     |                                |<---------------        |                                        |                                 
     |                                |                        |                                        |                                 
     |                                | a RawResponse          |                                        |                                 
     |                                |---------------------------------------------------------------->|                                 
     |                                |                        |                                        |                                 
     |                                |                        |                                        | decodeResponse(addProxyResponse)
     |                                |                        |                                        |---------------------------------
     |                                |                        |                                        |                                |
     |                                |                        |                                        |<--------------------------------
     |                                |                        |                                        |                                 
     |                                |                        |      a ProxyResponse with externalUuid |                                 
     |                                |                        |<---------------------------------------|                                 
     |                                |                        |                                        |                                 
     |                                |                        | check externalUuid                     |                                 
     |                                |                        |-------------------                     |                                 
     |                                |                        |                  |                     |                                 
     |                                |                        |<------------------                     |                                 
     |                                |                        |                                        |                                 
     |                                |                        | decodeResponse(addResponse)            |                                 
     |                                |                        |-----------------------------           |                                 
     |                                |                        |                            |           |                                 
     |                                |                        |<----------------------------           |                                 
     |                                |                        |                                        |                                 
     |                                |             a Response |                                        |                                 
     |                                |<-----------------------|                                        |                                 
     |                                |                        |                                        |                                 
     |           a DeviceNotification |                        |                                        |                                 
     |<-------------------------------|                        |                                        |                                 
     |                                |                        |                                        | 
~~~~




### 2 -  Main code in Proxy device (Prefered implementation)


~~~~
object Kalismart Kalicore ProxyDevice  Gateway
Kalismart-> Kalicore: a DeviceInterfaceCommand
Kalicore-> ProxyDevice: a DeviceCommand
note right of ProxyDevice: protocol frame forging is here!
ProxyDevice-> ProxyDevice: encodeData(addCommand)
ProxyDevice-> Gateway: a ProxyRawCommand
Gateway->Kalicore: a RawCommand
Kalicore->Kalicore: Listen raw data
Kalicore-> Gateway: a RawResponse
Gateway-> Gateway: decodeResponse(addProxyResponse)
Gateway-> ProxyDevice: a ProxyResponse with externalUuid
ProxyDevice-> ProxyDevice: check externalUuid
ProxyDevice-> ProxyDevice: decodeResponse(addResponse)
ProxyDevice-> Kalicore: a Response
Kalicore-> Kalismart: a DeviceNotification
~~~~


~~~~
+-----------+                    +-----------+           +-------------+                            +---------+                            
| Kalismart |                    | Kalicore  |           | ProxyDevice |                            | Gateway |                            
+-----------+                    +-----------+           +-------------+                            +---------+                            
     |                                |                        |                                        |                                 
     | a DeviceInterfaceCommand       |                        |                                        |                                 
     |------------------------------->|                        |                                        |                                 
     |                                |                        |                                        |                                 
     |                                | a DeviceCommand        |                                        |                                 
     |                                |----------------------->|                                        |                                 
     |                                |                        | ----------------------------------\    |                                 
     |                                |                        |-| protocol frame forging is here! |    |                                 
     |                                |                        | |---------------------------------|    |                                 
     |                                |                        |                                        |                                 
     |                                |                        | encodeData(addCommand)                 |                                 
     |                                |                        |-----------------------                 |                                 
     |                                |                        |                      |                 |                                 
     |                                |                        |<----------------------                 |                                 
     |                                |                        |                                        |                                 
     |                                |                        | a ProxyRawCommand                      |                                 
     |                                |                        |--------------------------------------->|                                 
     |                                |                        |                                        |                                 
     |                                |                        |                           a RawCommand |                                 
     |                                |<----------------------------------------------------------------|                                 
     |                                |                        |                                        |                                 
     |                                | Listen raw data        |                                        |                                 
     |                                |----------------        |                                        |                                 
     |                                |               |        |                                        |                                 
     |                                |<---------------        |                                        |                                 
     |                                |                        |                                        |                                 
     |                                | a RawResponse          |                                        |                                 
     |                                |---------------------------------------------------------------->|                                 
     |                                |                        |                                        |                                 
     |                                |                        |                                        | decodeResponse(addProxyResponse)
     |                                |                        |                                        |---------------------------------
     |                                |                        |                                        |                                |
     |                                |                        |                                        |<--------------------------------
     |                                |                        |                                        |                                 
     |                                |                        |      a ProxyResponse with externalUuid |                                 
     |                                |                        |<---------------------------------------|                                 
     |                                |                        |                                        |                                 
     |                                |                        | check externalUuid                     |                                 
     |                                |                        |-------------------                     |                                 
     |                                |                        |                  |                     |                                 
     |                                |                        |<------------------                     |                                 
     |                                |                        |                                        |                                 
     |                                |                        | decodeResponse(addResponse)            |                                 
     |                                |                        |-----------------------------           |                                 
     |                                |                        |                            |           |                                 
     |                                |                        |<----------------------------           |                                 
     |                                |                        |                                        |                                 
     |                                |             a Response |                                        |                                 
     |                                |<-----------------------|                                        |                                 
     |                                |                        |                                        |                                 
     |           a DeviceNotification |                        |                                        |                                 
     |<-------------------------------|                        |                                        |                                 
     |                                |                        |                                        |                                  


~~~~





