A recipe for feeding emails into the archive
============================================

The idea is to fetch incoming email from one or more IMAP accounts,
and store each email in the archive into a file used as temporary
storage.  Emails in this file is processed, either manually or
automatically, and moved into its more final resting place in the
archive (for example a case file) or deleted (if it is spam).

This setup use fetchmail to download the email from the IMAP account,
and procmail to store the emails in a Maildir folder.

FIXME Dokumenter hvordan sende epost direkte inn i archive-file

~/.fetchmailrc

```
set postmaster "somelocaluser"
set daemon 120
poll imap.example.com with proto IMAP
  port 993
  user imapuser
  mda "/usr/bin/procmail -f-"
  fetchall
  ssl
```

~/.procmailrc

```
PATH=/bin:/usr/bin:/usr/sbin
MAILDIR=$HOME/Mail                # you'd better make sure it exists
DEFAULT=$MAILDIR/spool-inbox/     # completely optional
LOGFILE=$MAILDIR/spool-inbox-from # recommended
```
