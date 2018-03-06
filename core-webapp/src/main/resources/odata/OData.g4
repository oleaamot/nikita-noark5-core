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
'/' resource odataCommand;

scheme          :  ('http' | 'https');

host            : string;

slash           : '/';

contextPath     : '/' string;

api             : '/' string;

functionality   : '/' string;

resource        : string;

port            : DIGITS;

odataCommand    : '?' filter  top?  skip? orderby?;

filter          : '$filter=' filterCommand;

search          : '$search=' searchCommand;

top             : '$top=' number;

skip            : '$skip=' number;

orderby         : '$orderby=' attribute sortOrder? (attribute sortOrder?)*;

searchCommand   : string;

filterCommand   : (command | (attribute comparator
 '\'' value '\'')) (operator filterCommand)? ;


command        : (contains | startsWith);

contains        : 'contains' leftCurlyBracket attribute ',' '\'' value '\''
                             rightCurlyBracket;

startsWith      : 'startsWith' leftCurlyBracket attribute ',' '\'' value '\''
                               rightCurlyBracket;

attribute       : string;


value           : string;

sortOrder       : asc | desc;


comparator      : eq | gt | lt | ge | le;
operator        : (and | or);

WHITESPACE       : [ \n\t\r]+ -> skip ;

leftCurlyBracket        : '(';
rightCurlyBracket       : ')';
and             : AND;
or              : OR;

eq              : EQ;
gt              : GT;
lt              : LT;
ge              : GE;
le              : LE;
string          : STRING;
number          : DIGITS;

asc             : ASC;
desc            : DESC;

EQ              : 'eq';
GT              : 'gt';
LT              : 'lt';
GE              : 'ge';
LE              : 'le';
AND             : 'and';
OR              : 'or';
ASC             : 'asc';
DESC            : 'desc';
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
