# regurgitator-extensions

regurgitator is a lightweight, modular, extendable java framework that you configure to 'regurgitate' canned or clever responses to incoming requests; useful for quickly mocking or prototyping services without writing any code. simply configure, deploy and run.

start your reading here: [regurgitator-all](http://github.com/talmeym/regurgitator-all#regurgitator)

## steps

regurgitator-extensions provides the following steps:
- ``json-parameter`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#json-parameter), [json](https://github.com/talmeym/regurgitator-extensions-json#json-parameter), [yml](https://github.com/talmeym/regurgitator-extensions-yml#json-parameter)) create a parameter, extracting its value using json-path
- ``xml-parameter`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#xml-parameter), [json](https://github.com/talmeym/regurgitator-extensions-json#xml-parameter), [yml](https://github.com/talmeym/regurgitator-extensions-yml#xml-parameter)) create a parameter, extracting its value using xpath

## constructs

regurgitator-extensions provides the following constructs:
#### value builders
- ``freemarker-builder`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#freemarker-builder), [json](https://github.com/talmeym/regurgitator-extensions-json#freemarker-builder), [yml](https://github.com/talmeym/regurgitator-extensions-yml#freemarker-builder)) build a parameter value using a freemarker template
- ``velocity-builder`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#velocity-builder), [json](https://github.com/talmeym/regurgitator-extensions-json#velocity-builder), [yml](https://github.com/talmeym/regurgitator-extensions-yml#velocity-builder)) build a parameter value using a velocity template

#### value processors
- ``json-path-processor`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#json-path-processor), [json](https://github.com/talmeym/regurgitator-extensions-json#json-path-processor), [yml](https://github.com/talmeym/regurgitator-extensions-yml#json-path-processor)) process a parameter value, extracting from it using json-path
- ``xpath-processor`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#xpath-processor), [json](https://github.com/talmeym/regurgitator-extensions-json#xpath-processor), [yml](https://github.com/talmeym/regurgitator-extensions-yml#xpath-processor)) process a parameter value, extracting from it using xpath
- ``freemarker-processor`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#freemarker-processor), [json](https://github.com/talmeym/regurgitator-extensions-json#freemarker-processor), [yml](https://github.com/talmeym/regurgitator-extensions-yml#freemarker-processor)) process a parameter value, formatting the value using a freemarker template
- ``velocity-processor`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#velocity-processor), [json](https://github.com/talmeym/regurgitator-extensions-json#velocity-processor), [yml](https://github.com/talmeym/regurgitator-extensions-yml#velocity-processor)) process a parameter value, formatting the value using a velocity template
- ``xml-schema-validator`` ([xml](https://github.com/talmeym/regurgitator-extensions-xml#xml-schema-validator), [json](https://github.com/talmeym/regurgitator-extensions-json#xml-schema-validator), [yml](https://github.com/talmeym/regurgitator-extensions-yml#xml-schema-validator)) process a parameter value, validating it against an xml schema

