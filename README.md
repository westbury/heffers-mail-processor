# heffers-mail-processor
A small program that extracts book orders from mail received from Heffers bookshop in Cambridge

If you get e-mail from Heffers, in which they list books you have ordered, and if you need to keep track
of your book orders, then you are probably a librarian.  You also may be interested in this utility.

Simply copy the contents of the e-mail to the clipboard, then run this program.  The book order will
be converted to CSV format and placed back in the clipboard.

# Building the program

gradlew.bat

# Running the program

- copy the contents of the mail to the clipboard.
- java -jar build\libs\mailprocessor-all.jar
- paste the contents of the clipboard into your spreadsheet

# Debugging the program

create the Eclipse .project etc files:

gradlew.bat eclipse
