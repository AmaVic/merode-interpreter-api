# Merode Interpretor
[![Build](https://github.com/AmaVic/merode/actions/workflows/build.yml/badge.svg)](https://github.com/AmaVic/merode/actions/workflows/build.yml) [![Test](https://github.com/AmaVic/merode/actions/workflows/test.yml/badge.svg)](https://github.com/AmaVic/merode/actions/workflows/test.yml) [![Doc](https://github.com/AmaVic/merode/actions/workflows/doc.yml/badge.svg)](https://github.com/AmaVic/merode/actions/workflows/doc.yml)
## Overview 
This tool enables the automated creation of [Merode](http://merode.econ.kuleuven.be)-based REST APIs and applications:
* You design a Merode model using the [Merlin](https://merlin-academic.com) tool, and download the model
* You start the Merode Interpreter providing the model file as input
* You get a fully functional REST API based on the model that enables
  * Submitting business events (this is how business objects are created, modified and ended)
  * Retrieving the list of valid events
  * Retrieving business objects

This is an easy way to implement Merode-based APIs or application back-ends.
Unlike the traditional Merode approach, this tool does not rely on code generation. Instead, the Merode model is interpreted at runtime, and the API is dynamically created.
This approach has several **benefits**:
* **Faster Development**: There is no need to re-generate a Merode application for each change in the model. Instead, you just need to restart the Merode Interpreter with the updated model file as input.
* **Easier Deployment**: Once your Merode Interpreter is deployed, there is no need to de-reploy it for each model change. 
* **Smaller Software Size**: The size of the software (interpreter) does increase with the size of the model.

It also has several potential **drawbacks**:
* **Increased Memory Usage**: As the Merode Interpreter interprets the model at runtime, the memory usage increases with the size of the model. This is not necessarily the case of generated code.
* **Lower Performance**: As the Merode model is interpreted at runtime for event handling and object instantiation, the interpreter may require more computational resources than generated code.

These are general trade-offs between _interpretation_ vs. _code generation_ approaches. The performance and memory trade-offs depend among others on the implementation of the interpreter and the of the generated code, respectively. We did not yet evaluate or compare the performance of the REST APIs generated from Merode models vs. REST APIs using the Merode Interpreter.

## Demonstration
The following [video](https://youtu.be/zL0SW_27Xxw) shows how to use the Merode Interpreter

# REST API Endpoints
## Submit Business Events
```
POST /event
```
The expected request body is a JSON specifying the name of the event type, and the properties used to set attribute values in the created/modified/ended business object. Example:
```json
{
    "type": "EVcrCustomer",
    "properties": {
        "email": "vic@gmail.com",
        "premium": false
    }
}
```

```json
{
    "type": "EVcrOrder",
    "properties": {
      "date": "2021-01-03"
    },
    "masters": {
      "Customer_Order": 0
    }
}
```

When the event is modifying or ending business objects, the id of the object must be specified as "objectId". For example, to ban Customer with 0, updating its premium status at the same time, the following body request can be sent:
```json
{
    "type": "EVendCustomer",
    "objectId": 0,
    "properties": {
        "premium": false
    }
}
```

The properties are optional. The following would just end the Customer with id 0, without updating its attributes values:
```json
{
    "type": "EVendCustomer",
    "objectId": 0
}
```

When an event is successfully handled and stored, the API answers with a list of affected business objects, in JSON format. Example of response for `EVcrOrder` event:
```json
[
    {
        "masters": {
            "Customer_Order": {
                "masters": {},
                "id": 0,
                "state": "orderInProgress",
                "type": "Customer",
                "properties": {
                    "premium": false,
                    "email": "vic@gmail.com"
                }
            }
        },
        "id": 1,
        "state": "placed",
        "type": "Order",
        "properties": {
            "date": "2021-01-03"
        }
    },
    {
        "masters": {},
        "id": 0,
        "state": "orderInProgress",
        "type": "Customer",
        "properties": {
            "email": "vic@gmail.com",
            "premium": false
        }
    }
]
```

## Retrieve Valid Business Events
The successfully executed business events can be retrieved using:
```
GET /event
```

It returns a (possibly empty) list of events in a JSON format. Example:
```json
[
  {
    "eventId": 0,
    "masters": {},
    "type": "EVcrCustomer",
    "objectId": 0,
    "properties": {
      "premium": false,
      "email": "vic@gmail.com"
    }
  },
  {
    "eventId": 1,
    "masters": {
      "Customer_Order": 0
    },
    "type": "EVcrOrder",
    "objectId": 1,
    "properties": {
      "date": "2021-01-03"
    }
  }
]
```

## Retrieve Business Objects of a Given Type
To retrieve all the business objects of a given type (e.g. Customer), use the following endpoint:
```
GET /customer
```
The name of the business object type in the URL must start with a lower-case. Example of (successful) response:
```json
[
  {
    "masters": {},
    "id": 0,
    "state": "orderInProgress",
    "type": "Customer",
    "properties": {
      "email": "vic@gmail.com",
      "premium": false
    }
  },
  {
    "masters": {},
    "id": 2,
    "state": "registered",
    "type": "Customer",
    "properties": {
      "email": "george@gmail.com",
      "premium": true
    }
  }
]
```

To retrieve a specific business object based on its id (e.g. 0), use the following endpoint:
```
GET /customer/0
```
Example of successful response:
```json
{
   "id": 0,
   "state": "ended",
   "type": "Customer",
   "properties": {
      "email": "vic@gmail.com",
      "premium": false
   }
}
```

## Errors
In case a request sent to the API is not successfully handled, it will respond with a list of (Merode)Error messages.

# Getting Started
1. Clone this repository
```shell
git clone git@github.com:AmaVic/merode.git
```
2. Get into the project directory
```shell
cd merode
```
3. Install the project distribution
```shell
./gradlew installDist
```
This will build the application in the `build/install/merode/bin` directory.
4. Get into the build directory
```shell
cd build/install/merode/bin
```
5. Run the application
```shell
./merode -m <path-to-mxp-file>
```
Where `<path-to-mxp-file>` is the path to the Merode model file (`.mxp`).

**Notes**:
* The Merode (.mxp) model file must be the XML file (not the .mxp archive that contains another .mxp (actually, xml) file).
* The .mxp files generated by Merlin cannot yet be used as-is. Before using a Merlin-generated .mxp, remove the `dependentrole` and `masterrole` attributes from the .mxp (.xml) file.
* The file [model2.mxp](https://github.com/AmaVic/merode/tree/main/src/test/resources/model2.mxp) can be used as an example of valid file for the Merode Interpreter.




