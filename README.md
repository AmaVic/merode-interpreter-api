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
* **Lower Software Size**: The size of the software (interpreter) does increase with the size of the model.

It also has several potential **drawbacks**:
* **Increased Memory Usage**: As the Merode Interpreter interprets the model at runtime, the memory usage increases with the size of the model. This is not necessarily the case of generated code.
* **Lower Performance**: As the Merode model is interpreted at runtime for event handling and object instantiation, the interpreter may require more computational resources than generated code.

These are general trade-offs between _interpretation_ vs. _code generation_ approaches. The performance and memory trade-offs depend among others on the implementation of the interpreter and the of the generated code, respectively. We did not yet evaluate or compare the performmance of the REST APIs generated from Merode models vs. REST APIs using the Merode Interpreter.

# REST API Endpoints
## Submit Business Events
```
POST /event
```

