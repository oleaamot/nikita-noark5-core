grammar OData;

/*
created by tsodring 18/02/2018
Basic grammar to handle OData syntax for nikita. Note this grammar will require
a lot more work to be complete.

Grammar portion describing HTTP adapted from
https://github.com/antlr/grammars-v4/blob/master/url/url.g4. (C) as per BSD
declaration
*/


odataURL: scheme SEPERATOR host (COLON port)? contextPath api functionality
resource odataCommand;

scheme          :  ('http' | 'https');

host            : string;

slash           : '/';

contextPath     : '/' string;

api             : '/' string;

functionality   : '/' string;

resource        : '/' string;

port            : DIGITS;

odataCommand    : '?' filter ;

filter          : '$filter=' filterCommand;

search          : '$search=' searchCommand;

searchCommand   : string;

filterCommand   : (startsWith | contains | (attribute comparator
 '\'' value '\'')) (operator filterCommand)? ;


startsWith      : 'startsWith(' attribute ',' '\'' value '\'' ')';

contains        : 'contains(' attribute ',' '\'' value '\'' ')';

attribute       : string;

value           : string;

comparator      : eq | gt | lt | ge | le;
operator        : (and | or);

WHITESPACE       : ' ' -> skip ;

and             : AND;
or              : OR;

eq              : EQ;
gt              : GT;
lt              : LT;
ge              : GE;
le              : LE;
string          : STRING;

EQ              : 'eq';
GT              : 'gt';
LT              : 'lt';
GE              : 'ge';
LE              : 'le';
AND             : 'and';
OR              : 'or';
TOP             : 'top';
SKIP_            : 'skip';
COUNT           : 'count';
ORDERBY         : 'orderby';
WS              : ' '+;
DIGITS          : [0-9] +;
HEX             : ('%' [a-fA-F0-9] [a-fA-F0-9]) +;
STRING          : ([a-zA-Z~0-9])+;
COLON           : ':';
SEPERATOR       : '://';
