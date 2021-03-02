# SnilsGenerator
Generates snils base on generation rules

## SNILS checksum calculation rules

Allowed characters are numbers, space, hyphen.
Checked for validity with a check number.
SNILS has the form "XXX-XXX-XXX YY", where XXX-XXX-XXX is a number, and YY is a check number.

#### SNILS control number calculation:
* Each digit of SNILS is multiplied by its position number (positions are counted from the end);
* The received works are summed up;
* If the amount is less than 100, then the check number is equal to the amount itself;
* If the sum is 100 or 101, then the check number is 00;
* If the amount is more than 101, then the amount is divided entirely by 101, and the check number is determined
  the remainder of the division is similar to the previous two paragraphs.

#### Additional verification:

* Number XXX-XXX-XXX cannot contain the same digit three times in a row. Hyphens are ignored during this 
  check, i.e. all the following examples of SNILS will be incorrect:
  XXX-222-XXX YY
  XX2-22X-XXX YY 

