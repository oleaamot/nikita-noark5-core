Major changes in Nikita Noark 5 Core releases
=============================================

These are the highlevel changes.  For details, see the git history.

Release 0.2 UNRELEASED
----------------------

 * Corrected and completed HATEOAS links to make sure entire API is
   available via URLs in \_links.
 * Corrected all relation URLs to use trailing slash.
 * Add initial support for storing data in ElasticSearch.
 * Now able to receive and store uploaded files in the archive.
 * Changed JSON output for object lists to have relations in \_links.
 * Improve JSON output for empty object lists.
 * Now uses correct MIME type application/vnd.noark5-v4+json.
 * Added support for docker container images
 * Added simple API browser implemented in JavaScript/Angular.
 * Started on archive client implemented in JavaScript/Angular.
 * Started on prototype to show the public mail journal.
 * Improved performance by disabling Sprint FileWatcher.
 * Added support for 'arkivskaper', 'saksmappe' and 'journalpost'.
 * ...

Release 0.1 2017-01-31 (commit 6ec4acb9c1d5b72fd4bf58074769233e78483bb4)
-----------------------
 * Able to store archive metadata.
