package generator

class RandomValuesGenerator {

  /**
   * Generates a string of random digits with the specified length.
   *
   * @param digitsLength The length of the string of random digits to generate.
   * @return A string containing randomly generated digits of the specified length.
   */
  fun randomDigits(digitsLength: Int): String {
    var generatedDigits = ""
    val rangeList = { (0..9).random() }
    while (generatedDigits.length < digitsLength) {
      generatedDigits += rangeList().toString()
    }
    return generatedDigits
  }
}