# regurgitator-extensions

regurgitator is a modular, light-weight, extendable java-based processing framework designed to 'regurgitate' canned or clever responses to incoming requests; useful for mocking or prototyping services.

start your reading here: [regurgitator-all](http://github.com/talmeym/regurgitator-all#regurgitator)

## steps

regurgitator-extensions provides the following steps:
- ``json-parameter`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#json-parameter), [json](https://github.com/talmeym/regurgitator-extensions-json#json-parameter)) create a parameter, extracting its value using json-path
- ``xml-parameter`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#xml-parameter), [json](https://github.com/talmeym/regurgitator-extensions-json#xml-parameter)) create a parameter, extracting its value using xpath

## constructs

regurgitator-extensions provides the following constructs:
#### value builders
- ``freemarker-builder`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#freemarker-builder), [json](https://github.com/talmeym/regurgitator-extensions-json#freemarker-builder)) build a parameter value using a freemarker template
- ``velocity-builder`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#velocity-builder), [json](https://github.com/talmeym/regurgitator-extensions-json#velocity-builder)) build a parameter value using a velocity template

#### value processors
- ``json-path-processor`` process a parameter value, extracting from it using json-path
- ``xpath-processor`` process a parameter value, extracting from it using xpath
- ``freemarker-processor`` process a parameter value, formatting the value using a freemarker template
- ``velocity-processor`` process a parameter value, formatting the value using a velocity template
- ``xml-schema-validator`` process a parameter value, validating it against an xml schema

