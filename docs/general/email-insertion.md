A recipe for feeding emails into the archive
============================================

The idea is to fetch incoming email from one or more IMAP accounts,
and store each email in the archive into a file used as temporary
storage.  Emails in this file is processed, either manually or
automatically, and moved into its more final resting place in the
archive (for example a case file) or deleted (if it is spam).

One setup proposal is to use fetchmail to download the email from the
IMAP account, and procmail to store the emails in a mbox folder, and
import it using import-mbox.  This will create one file (or case file)
per email thread.

Another setup proposal is to use fetchmail with something like
archive-file that can read incoming email from stdin and store it in
the archive.

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

This script fetches email via IMAP and pass it on to import-email.
The password is in the fetchmailrc file.  The password can also be
stored in ~/.netrc.  If the password is stored neiter places, the
fetchmail program will interactively ask for a password and fail to
work if not run interactively.

```
#!/bin/sh

fileid=mappesystemid

fetchmailrc=$(tempfile) || exit 1
trap "rm -f -- $tmpfile" EXIT

# Make sure file is unreadable for others as it contain a password
printf "" > $fetchmailrc
chmod 700 $fetchmailrc

cat <<EOF >> $fetchmailrc
set postmaster "somelocaluser"
set daemon 120
poll imap.example.com with proto IMAP
  port 993
  user imapuser
  pass secretpwd
  mda "/usr/bin/import-email --mda --storageurl https://url/to/storage/object"
  fetchall
  ssl
EOF

fetchmail -f $fetchmailrc
```
