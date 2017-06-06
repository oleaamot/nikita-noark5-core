A recipe for feeding emails into the archive
============================================

The idea is to fetch incoming email from one or more IMAP accounts,
and store each email in the archive into a file used as temporary
storage.  Emails in this file is processed, either manually or
automatically, and moved into its more final resting place in the
archive (for example a case file) or deleted (if it is spam).

One setup proposal is to use fetchmail to download the email from the
IMAP account, and procmail to store the emails in a mbox folder, and
import it using import-email.  This will require storing incoming
email in a temporary mbox file.

Another setup proposal is to let fetchmail call import-email --mda,
which will read the incoming email from stdin and store it in the
archive.

Using import-email will create one file (or case file) per email
thread.

This script instructs fetchmail to download email via IMAP and pass it
on to import-email.  The password is in the fetchmailrc file.  The
password can also be stored in ~/.netrc, see the fetchmail manual page
for details.  If the password is stored neither places, the fetchmail
program will interactively ask for a password and fail to work if not
run interactively.

```
#!/bin/sh

# The arkivdel URL to use (should handle mappe too?)
storageurl=https://url/to/storage/object"

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
  mda "/usr/bin/import-email --mda --storageurl $storageurl
  fetchall
  ssl
EOF

fetchmail -f $fetchmailrc
```
