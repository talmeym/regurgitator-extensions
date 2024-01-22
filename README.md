# regurgitator-extensions

regurgitator is a lightweight, modular, extendable java framework that you configure to 'regurgitate' canned or clever responses to incoming requests; useful for quickly mocking or prototyping services without writing any code. simply configure, deploy and run.

start your reading here: [regurgitator-all](https://talmeym.github.io/regurgitator-all#regurgitator)

[``apidocs``](https://regurgitator.emarte.uk/apidocs/regurgitator-extensions/0.1.3/)

## steps

regurgitator-extensions provides the following steps:
- ``json-parameter`` ([xml](https://talmeym.github.io/regurgitator-extensions-xml#json-parameter), [json](https://talmeym.github.io/regurgitator-extensions-json#json-parameter), [yml](https://talmeym.github.io/regurgitator-extensions-yml#json-parameter)) create a parameter, extracting its value using json-path
- ``xml-parameter`` ([xml](https://talmeym.github.io/regurgitator-extensions-xml#xml-parameter), [json](https://talmeym.github.io/regurgitator-extensions-json#xml-parameter), [yml](https://talmeym.github.io/regurgitator-extensions-yml#xml-parameter)) create a parameter, extracting its value using xpath

## constructs

regurgitator-extensions provides the following constructs:
#### value builders
- ``freemarker-builder`` ([xml](https://talmeym.github.io/regurgitator-extensions-xml#freemarker-builder), [json](https://talmeym.github.io/regurgitator-extensions-json#freemarker-builder), [yml](https://talmeym.github.io/regurgitator-extensions-yml#freemarker-builder)) build a parameter value using a freemarker template

#### value processors
- ``json-path-processor`` ([xml](https://talmeym.github.io/regurgitator-extensions-xml#json-path-processor), [json](https://talmeym.github.io/regurgitator-extensions-json#json-path-processor), [yml](https://talmeym.github.io/regurgitator-extensions-yml#json-path-processor)) process a parameter value, extracting from it using json-path
- ``xpath-processor`` ([xml](https://talmeym.github.io/regurgitator-extensions-xml#xpath-processor), [json](https://talmeym.github.io/regurgitator-extensions-json#xpath-processor), [yml](https://talmeym.github.io/regurgitator-extensions-yml#xpath-processor)) process a parameter value, extracting from it using xpath
- ``freemarker-processor`` ([xml](https://talmeym.github.io/regurgitator-extensions-xml#freemarker-processor), [json](https://talmeym.github.io/regurgitator-extensions-json#freemarker-processor), [yml](https://talmeym.github.io/regurgitator-extensions-yml#freemarker-processor)) process a parameter value, formatting the value using a freemarker template
- ``xml-schema-validator`` ([xml](https://talmeym.github.io/regurgitator-extensions-xml#xml-schema-validator), [json](https://talmeym.github.io/regurgitator-extensions-json#xml-schema-validator), [yml](https://talmeym.github.io/regurgitator-extensions-yml#xml-schema-validator)) process a parameter value, validating it against an xml schema

