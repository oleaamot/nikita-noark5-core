# nikita-noark5-core

[![Build Status](https://travis-ci.org/HiOA-ABI/nikita-noark5-core.svg?branch=master)](https://travis-ci.org/HiOA-ABI/nikita-noark5-core)

This is an open source Noark 5v4 core.  This implement a REST based web API for storing av retrieving documents and metadata based on the Norwegian archive standard NOARK 5.

The core is very much under development and should be seen as a alpha product until releases closer to 0.6. The current version is 0.1 and has implemented the arkivstruktur interface of Noark 5v4.  

Read [INSTALL](docs/general/Install.md) to get started.

## Contributing

You are encouraged to contribute. You can either hack on the code and send pull requests or join us on [IRC](http://webchat.freenode.net?randomnick=1&channels=%23nikita&uio=d4) for discussions. Feedback is greatly appreciated. The project has an open ethos and welcomes all forms of feedback. The project maintains a mailing list (https://lists.nuug.no/mailman/listinfo/nikita-noark) and issues can be raised via github (https://github.com/HiOA-ABI/nikita-noark5-core/issues). Look at [Starter bugs](./docs/general/Starter-bugs.md) for some suggestions on things to do.

## Miscellaneous

For configuration purposes, take a look at the resources directory of core-webapp for application-*.yml files for
setting properties in the various profiles.

Currently we are using AGPLv3 license, but the code should be published as APLv2 when finished (depending on integrated libraries).

We are skipping tests as there currently is an issue identifying the logged-in user when running tests. I am assuming the security context will have the default anonymous user, but it is in fact null. This causes the tests to fail. Currently there is no point running tests.

Thanks to IntelliJ for an idea license (https://www.jetbrains.com/idea/)
