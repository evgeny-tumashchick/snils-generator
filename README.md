# SnilsGenerator

A Kotlin-based utility for generating and validating SNILS (Russian social security numbers) based on official rules.

## Overview

The `SnilsGenerator` project provides functionality for generating valid SNILS numbers, ensuring that the generated
numbers adhere to the rules governing SNILS structure and validation. The project includes a core generator class and
utilities for random number generation and checksum calculation.

### Core Classes

- **`SnilsGenerator`**: The main class responsible for generating SNILS numbers. It ensures that the random SNILS part
  is valid and computes the correct check digit.
- **`RandomValuesGenerator`**: A utility class used by `SnilsGenerator` to generate random numeric strings that conform
  to SNILS generation rules.

### SNILS Structure and Validation Rules

SNILS (СНИЛС) numbers have the format `XXX-XXX-XXX YY`, where `XXX-XXX-XXX` is a nine-digit number and `YY` is the check
number.

#### Checksum Calculation Rules:

1. Each digit of the SNILS number (excluding the check digits) is multiplied by its positional value, starting from the
   end.
2. The products are summed up.
3. The check number is determined as follows:

- If the sum is less than 100, the check number is equal to the sum.
- If the sum is 100 or 101, the check number is `00`.
- If the sum is greater than 101, it is divided by 101, and the remainder is used as the check number. If the remainder
  is 100 or 101, the check number is `00`.

#### Additional Validation:

- The SNILS number cannot contain the same digit three times in a row, ignoring hyphens.  
For example, `XXX-222-XXX YY` and `XX2-22X-XXX YY` are invalid.

## Running Tests

To run the tests for the project, use the following Gradle command:

```sh
./gradlew test
```

This will execute all unit tests, including those for SNILS generation and validation, ensuring that the implementation
conforms to the specified rules.

## Author and Contact Information

**Author:** Evgeny Tumashchick

For any questions, issues, or contributions, you can reach out to me via:

- **Email:** [tumashchick@gmail.com](mailto:tumashchick@gmail.com)
- **LinkedIn:** [Evgeny Tumashchick](https://www.linkedin.com/in/tumashchick-yauhen/)
- **GitHub:** [evgeny-tumashchick](https://github.com/evgeny-tumashchick/evgeny-tumashchick)

Feel free to open an issue on the GitHub repository for bug reports, feature requests, or any other inquiries.