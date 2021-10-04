# nv-zd

A script to help convert notes originaly created in NotationalVelocity to zetteldeft format. Conversion involves changing the file from a .txt file to a .org file which I think will lose the creation date. 

**Zetteldeft** usually (it's configurable) stores the creation date of a note in the filename. Using **zetteldeft-file-rename** will create the ID that contains the date and use it as the start of the filename if it is missing. This will be the create time of the new org file though, so adding the original create time to the filename in the correct format before running **zetteldeft-file-rename** is necessary in order to keep the original creation time.

That is version 1, version 2 will do the rename and add the TITLE line to the file so subsequent use of zetteldeft-file-rename is unnecessary.
